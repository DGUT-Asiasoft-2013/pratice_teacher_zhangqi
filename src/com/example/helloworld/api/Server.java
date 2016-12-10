package com.example.helloworld.api;

import java.io.Serializable;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Server {
	static OkHttpClient client;
	
	static {
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		
		client = new OkHttpClient.Builder()
				.cookieJar(new JavaNetCookieJar(cookieManager))
				.build();
	}
	
	public static OkHttpClient getSharedClient(){
		return client;
	}
	
	public static String serverAddress = "http://172.27.0.56:8080/membercenter/"; 
	
	public static Request.Builder requestBuilderWithApi(String api){
		return new Request.Builder()
		.url(serverAddress+"api/"+api);
	}
}
