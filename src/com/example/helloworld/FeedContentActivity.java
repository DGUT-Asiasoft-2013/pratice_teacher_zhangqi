package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FeedContentActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_feed_content);
		
		String text = getIntent().getStringExtra("text");
		
		TextView textView = (TextView) findViewById(R.id.text);
		textView.setText(text);
	}
}
