package com.example.fullscreen_reidisaki_test.data;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class IGdataAdapter extends ArrayAdapter<InstagramData> {

	public IGdataAdapter(Context context, int resource, int textViewResourceId,
			List<InstagramData> objects) {
		super(context, resource, textViewResourceId, objects);

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			//inflate the view
		} else {
			// reuse old view save memory
		}
		
		return convertView;
	}
	
}
