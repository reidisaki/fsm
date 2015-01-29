package com.example.fullscreen_reidisaki_test.data;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fullscreen_reidisaki_test.R;

public class IGdataAdapter extends ArrayAdapter<InstagramData> {

	int layoutResourceId;
	Context mContext;
	List<InstagramData> data;
	public IGdataAdapter(Context context, int igRowId,List<InstagramData> objects) {
		super(context, igRowId, objects);
		this.layoutResourceId = igRowId;
		this.mContext = context;
		this.data = objects;
		Log.i("reid",objects.size() +" size");
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
			Log.i("reid",currentData.get_fullName());
			text.setText(currentData.get_fullName());
			Log.i("reid",currentData.get_standardImage());
			image.setTag(currentData.get_standardImage());
			thumb.setTag(currentData.get_ownerPicture());
			new DownloadImagesTask().execute(image);
			new DownloadImagesTask().execute(thumb);
		}
		
		return convertView;
	}
	
	public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

	    ImageView imageView = null;

	    @Override
	    protected Bitmap doInBackground(ImageView... imageViews) {
	        this.imageView = imageViews[0];
	        return download_Image((String)imageView.getTag());
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

