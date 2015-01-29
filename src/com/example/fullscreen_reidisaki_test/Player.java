package com.example.fullscreen_reidisaki_test;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Player extends Activity {

	public final static String VIDEO_URL = "videoUrl";
	//set video player crap here.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view);
		final VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		String videoUrl = getIntent().getExtras().getString(VIDEO_URL);
		videoView.setVideoPath(videoUrl);
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);
//		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
//			@Override
//			public void onPrepared(MediaPlayer mp) {
//			}
//		});

		videoView.start();

	}

}
