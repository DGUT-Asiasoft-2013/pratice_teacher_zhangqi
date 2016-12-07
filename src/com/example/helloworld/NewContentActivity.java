package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class NewContentActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_content);
		
		findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
			}
		});
	}
}
