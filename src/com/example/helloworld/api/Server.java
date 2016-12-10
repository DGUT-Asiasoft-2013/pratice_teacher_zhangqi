package com.example.helloworld.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Server {
	static OkHttpClient client;
	
	static {
		CookieJar cookieJar = new CookieJar() {
			Map<HttpUrl, List<Cookie>> cookiemap = new HashMap<HttpUrl, List<Cookie>>();
			
			@Override
			public void saveFromResponse(HttpUrl key, List<Cookie> value) {
				cookiemap.put(key, value);
			}
			
			@Override
			public List<Cookie> loadForRequest(HttpUrl key) {
				List<Cookie> cookies = cookiemap.get(key);
				if(cookies==null){
					return new ArrayList<Cookie>();
				}else{
					return cookies;
				}
			}
		};
		
		client = new OkHttpClient.Builder()
				.cookieJar(cookieJar)
				.build();
	}
	
	public static OkHttpClient getSharedClient(){
		return client;
	}
	
	public static Request.Builder requestBuilderWithApi(String api){
		return new Request.Builder()
		.url("http://172.27.0.56:8080/membercenter/api/"+api);
	}
}
