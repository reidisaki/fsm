package com.example.fullscreen_reidisaki_test.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.fullscreen_reidisaki_test.data.InstagramData;

public class API {

	final static  String NODE_DATA = "data";
	final static String NODE_TAGS = "tags";
	final static String NODE_CREATED_TIME = "created_time";
	final static String NODE_IMAGES = "images";
	final static String NODE_THUMBNAIL = "thumbnail";
	final static String NODE_STANDARD_IMAGE = "standard_resolution";
	final static String NODE_OWNER_NAME = "username";
	final static String NODE_USER = "user";
	final static String NODE_PROFILE_IMAGE = "profile_picture";
	final static String NODE_VIDEO_URL = "standard_resolution";
	final static String NODE_VIDEO = "video";
	final static String NODE_FULL_NAME= "full_name";


	public static List<InstagramData> parseData(String JSONString){
		List instagramDataList = new ArrayList<InstagramData>();
		try {

			JSONObject result = new JSONObject(JSONString);
			JSONArray data = new JSONArray(result.get(NODE_DATA));
			for(int  i =0; i < data.length(); i++) {
				JSONObject item = (JSONObject) data.get(i);
				JSONObject user = item.getJSONObject(NODE_USER);
				JSONObject videos = item.getJSONObject(NODE_VIDEO);
				JSONObject images = item.getJSONObject(NODE_IMAGES);

				InstagramData igData = new InstagramData();
				igData.set_created_time(item.getString(NODE_CREATED_TIME));
				igData.set_fullName(user.getString(NODE_FULL_NAME));
				igData.set_ownerName(user.getString(NODE_OWNER_NAME));
				igData.set_ownerPicture(user.getString(NODE_PROFILE_IMAGE));

				igData.set_standardImage(images.getString(NODE_STANDARD_IMAGE));
				igData.set_thumbnailImage(images.getString(NODE_THUMBNAIL));
				if(videos != null) {
					igData.set_videoUrl(videos.getString(NODE_VIDEO_URL));
				}
				instagramDataList.add(igData);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return instagramDataList;
	}
}
