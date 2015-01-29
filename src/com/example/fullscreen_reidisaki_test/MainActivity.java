package com.example.fullscreen_reidisaki_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.fullscreen_reidisaki_test.api.API;
import com.example.fullscreen_reidisaki_test.data.IGdataAdapter;
import com.example.fullscreen_reidisaki_test.data.InstagramData;

public class MainActivity extends ListActivity{
	public IGdataAdapter adapter;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dialog = new ProgressDialog(getApplicationContext());

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(adapter.getItem(position).get_videoUrl() != null) {
					//go to video playing page.. otherwise there is no video.
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	public class LoadInstagramFeedTask extends
			AsyncTask<String, Integer, List<InstagramData>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setTitle("Retrieving Instagram data feed");
			dialog.show();
		}
		@Override
		protected List<InstagramData> doInBackground(String... params) {
			return connect(getResources().getString(R.string.instagram_feed)
					+ getResources().getString(R.string.instagram_client_id));
		}

		@Override
		protected void onPostExecute(List<InstagramData> result) {
			dialog.hide();
			super.onPostExecute(result);
			if(adapter != null) {
				adapter = new IGdataAdapter(getApplicationContext(), R.layout.ig_row, result);
				setListAdapter(adapter);
				//update list
				adapter.notifyDataSetChanged();
			}

		}

	}

	public static List<InstagramData> connect(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		List<InstagramData> instagramDataList = new ArrayList<InstagramData>();
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				instagramDataList = API.parseData(result);
				instream.close();
			}
		} catch (Exception e) {
		}
		return instagramDataList;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
