package com.example.fullscreen_reidisaki_test.data;

import java.util.List;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IGdataAdapter extends ArrayAdapter<InstagramData> {

	int layoutResourceId;
	Context mContext;
	List<InstagramData> data;
	public IGdataAdapter(Context context, int igRowId,List<InstagramData> objects) {
		super(context, igRowId, objects);
		this.layoutResourceId = igRowId;
		this.mContext = context;
		this.data = objects;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			//inflate the view
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		} 

		if(data.size() > 0 ) {
//			ImageView image = (ImageView)convertView.findViewById(R.id.ig_image);
//			TextView text = (TextView)convertView.findViewById(R.id.ig_text);
		}
		


		return convertView;
	}
}

