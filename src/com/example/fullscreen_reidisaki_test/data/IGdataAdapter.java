package com.example.fullscreen_reidisaki_test.data;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fullscreen_reidisaki_test.R;

public class IGdataAdapter extends ArrayAdapter<InstagramData> {
	private LruCache<String, Bitmap> mMemoryCache;
	int layoutResourceId;
	Context mContext;
	List<InstagramData> data;
	public IGdataAdapter(Context context, int igRowId,List<InstagramData> objects) {
		super(context, igRowId, objects);
		this.layoutResourceId = igRowId;
		this.mContext = context;
		this.data = objects;
		
		int memClass = ( ( ActivityManager )context.getSystemService( Context.ACTIVITY_SERVICE ) ).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8;
		
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getByteCount() / 1024;
			}
		};
	}
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if(key != null && bitmap != null) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InstagramData currentData = data.get(position);

		if(convertView == null) {
			//inflate the view
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
		if(currentData.get_videoUrl() != null) {
			convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
		}
		if(data.size() > 0 ) {
			ImageView image = (ImageView)convertView.findViewById(R.id.ig_image);
			ImageView thumb = (ImageView)convertView.findViewById(R.id.ig_image_thumb);
			TextView text = (TextView)convertView.findViewById(R.id.ig_text);
			//			Log.i("reid",currentData.get_fullName());
			text.setText(currentData.get_fullName());
			//			Log.i("reid",currentData.get_standardImage());
			image.setTag(currentData.get_standardImage());
			thumb.setTag(currentData.get_ownerPicture());
			Bitmap b = getBitmapFromMemCache(currentData.get_standardImage());
			if(b== null) {
				image.setImageResource(R.drawable.download);
//				image.setImageBitmap(((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.download)).getBitmap());
				new DownloadImagesTask().execute(image);	
			} else {
				Log.i("reid","cache hit");
				image.setImageBitmap(b);
			}
			Bitmap b_thumb = getBitmapFromMemCache(currentData.get_ownerPicture());
			if(b_thumb == null) {
				new DownloadImagesTask().execute(thumb);	
			} else {
				Log.i("reid","cache hit thumb");
				thumb.setImageBitmap(b_thumb);
			}
		}

		return convertView;
	}

	public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

		ImageView imageView = null;
		String _url;
		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
			
			this.imageView = imageViews[0];
			_url = (String)imageView.getTag();
			Log.i("reid","downloading image " + _url);
			Bitmap result = download_Image((String)imageView.getTag());
			addBitmapToMemoryCache(_url,result);
			return result;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
		}

		private Bitmap download_Image(String url) {

			Bitmap bmp =null;
			try{
				URL ulrn = new URL(url);
				HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
				InputStream is = con.getInputStream();
				bmp = BitmapFactory.decodeStream(is);
				if (null != bmp)
					return bmp;

			}catch(Exception e){}
			return bmp;
		}
	}
}

