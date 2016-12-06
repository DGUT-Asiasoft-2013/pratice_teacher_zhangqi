package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainTabbarFragment extends Fragment {
	
	View btnNew, tabFeeds, tabNotes, tabSearch, tabMe;
	View[] tabs;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_tabbar, null);
		
		btnNew = view.findViewById(R.id.btn_new);
		tabFeeds = view.findViewById(R.id.tab_feeds);
		tabNotes = view.findViewById(R.id.tab_notes);
		tabSearch = view.findViewById(R.id.tab_search);
		tabMe = view.findViewById(R.id.tab_me);
		
		tabs = new View[] {
				tabFeeds, tabNotes, tabSearch, tabMe
		};
		
		for(final View tab : tabs){
			tab.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onTabClicked(tab);
				}
			});			
		}
		
		return view;
	}
	
	void onTabClicked(View tab){
		for(View otherTab : tabs){
			otherTab.setSelected(otherTab == tab);
		}
	}
}
