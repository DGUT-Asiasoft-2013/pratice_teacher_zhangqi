package com.example.helloworld.fragments.pages;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.R;
import com.example.helloworld.api.Server;
import com.example.helloworld.api.entity.Article;
import com.example.helloworld.api.entity.Page;
import com.example.helloworld.fragments.widgets.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class FeedListFragment extends Fragment {

	View view;
	ListView listView;
	View btnLoadMore;
	TextView textLoadMore;

	List<Article> data;
	int page = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view==null){
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			btnLoadMore = inflater.inflate(R.layout.widget_load_more_button, null);
			textLoadMore = (TextView) btnLoadMore.findViewById(R.id.text);

			listView = (ListView) view.findViewById(R.id.list);
			listView.addFooterView(btnLoadMore);
			listView.setAdapter(listAdapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemClicked(position);
				}
			});
			
			btnLoadMore.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					loadmore();
				}
			});
		}

		return view;
	}

	BaseAdapter listAdapter = new BaseAdapter() {

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if(convertView==null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.widget_feed_item, null);	
			}else{
				view = convertView;
			}

			TextView textContent = (TextView) view.findViewById(R.id.text);
			TextView textTitle = (TextView) view.findViewById(R.id.title);
			TextView textAuthorName = (TextView)view.findViewById(R.id.username);
			TextView textDate = (TextView)view.findViewById(R.id.date);
			AvatarView avatar = (AvatarView)view.findViewById(R.id.avatar);
			
			Article article = data.get(position);

			textContent.setText(article.getText());
			textTitle.setText(article.getTitle());
			textAuthorName.setText(article.getAuthor().getName());
			avatar.load(article.getAuthor());
			
			String dateStr = DateFormat.format("yyyy-MM-dd hh:mm", article.getCreateDate()).toString();
			textDate.setText(dateStr);

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public int getCount() {
			return data==null ? 0 : data.size();
		}
	};

	void onItemClicked(int position){
		Article text = data.get(position);

		Intent itnt = new Intent(getActivity(), FeedContentActivity.class);
		itnt.putExtra("text", text);

		startActivity(itnt);
	}

	@Override
	public void onResume() {
		super.onResume();
		reload();
	}

	void reload(){
		Request request = Server.requestBuilderWithApi("feeds")
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{					
					final Page<Article> data = new ObjectMapper()
							.readValue(arg1.body().string(),
									new TypeReference<Page<Article>>(){});
					
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							FeedListFragment.this.page = data.getNumber();
							FeedListFragment.this.data = data.getContent();
							listAdapter.notifyDataSetInvalidated();
						}
					});
				}catch(final Exception e){
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							new AlertDialog.Builder(getActivity())
							.setMessage(e.getMessage())
							.show();
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new AlertDialog.Builder(getActivity())
						.setMessage(e.getMessage())
						.show();
					}
				});
			}
		});
	}

	void loadmore(){
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中…");
		
		Request request = Server.requestBuilderWithApi("feeds/"+(page+1)).get().build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("加载更多");
					}
				});
				
				try{
					final Page<Article> feeds = new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Article>>() {});
					if(feeds.getNumber()>page){
						
						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								if(data==null){
									data = feeds.getContent();
								}else{
									data.addAll(feeds.getContent());
								}
								page = feeds.getNumber();
								
								listAdapter.notifyDataSetChanged();
							}
						});
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("加载更多");
					}
				});
			}
		});
	}
}
