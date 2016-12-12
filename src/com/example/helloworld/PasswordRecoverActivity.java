package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.api.Server;
import com.example.helloworld.fragments.PasswordRecoverStep1Fragment;
import com.example.helloworld.fragments.PasswordRecoverStep1Fragment.OnGoNextListener;
import com.example.helloworld.fragments.PasswordRecoverStep2Fragment;
import com.example.helloworld.fragments.PasswordRecoverStep2Fragment.OnSubmitClickedListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PasswordRecoverActivity extends Activity {

	PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();
	PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_password_recover);
		
 		step1.setOnGoNextListener(new OnGoNextListener() {
			
			@Override
			public void onGoNext() {
				goStep2();
			}
		});
 		
 		step2.setOnSubmitClickedListener(new OnSubmitClickedListener() {
			
			@Override
			public void onSubmitClicked() {
				goSubmit();
			}
		});
 		
 		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
	}
	
	void goStep2(){
		
		getFragmentManager()
		.beginTransaction()	
		.setCustomAnimations(
				R.animator.slide_in_right,
				R.animator.slide_out_left,
				R.animator.slide_in_left,
				R.animator.slide_out_right)
		.replace(R.id.container, step2)
		.addToBackStack(null)
		.commit();
	}
	
	void goSubmit(){
		OkHttpClient client = Server.getSharedClient();
		MultipartBody body = new MultipartBody.Builder()
				.addFormDataPart("email", step1.getText())
				.addFormDataPart("passwordHash", MD5.getMD5(step2.getText()))
				.build();
		Request request = Server.requestBuilderWithApi("passwordrecover").post(body).build();
				
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
