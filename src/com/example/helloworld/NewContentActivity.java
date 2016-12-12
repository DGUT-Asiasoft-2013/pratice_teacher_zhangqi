package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.api.Server;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class NewContentActivity extends Activity {
	EditText editTitle, editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_content);
		
		editTitle = (EditText) findViewById(R.id.title);
		editText = (EditText) findViewById(R.id.text);
		
		findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendContent();
			}
		});
	}
	
	void sendContent(){
		String text = editText.getText().toString();
		String title = editTitle.getText().toString();
		
		// check these value is not null
		
		MultipartBody body = new MultipartBody.Builder()
				.addFormDataPart("title", title)
				.addFormDataPart("text", text)
				.build();
		
		Request request = Server.requestBuilderWithApi("article")
				.post(body)
				.build();
		
		Server.getSharedClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String responseBody = arg1.body().string();
				
				runOnUiThread(new Runnable() {
					public void run() {
						NewContentActivity.this.onSucceed(responseBody);
					}
				});
			}
			
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {
					public void run() {
						NewContentActivity.this.onFailure(arg1);
					}
				});
			}
		});
	}
	
	void onSucceed(String text){
		new AlertDialog.Builder(this).setMessage(text)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
			}
		}).show();
	}
	
	void onFailure(Exception e){
		new AlertDialog.Builder(this).setMessage(e.getMessage()).show();
	}
}
