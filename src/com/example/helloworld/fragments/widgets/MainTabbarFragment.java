package com.example.helloworld.fragments.widgets;

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
		View view = inflater.inflate(R.layout.fragment_widget_main_tabbar, null);
		
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
		
		btnNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onNewClicked();
			}
		});
		
		return view;
	}
	
	public static interface OnTabSelectedListener {
		void onTabSelected(int index);
	}
	
	OnTabSelectedListener onTabSelectedListener;
	
	public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
		this.onTabSelectedListener = onTabSelectedListener;
	}
	
	public void setSelectedItem(int index){
		if(index>=0 && index<tabs.length){
			onTabClicked(tabs[index]);
		}
	}
	
	public int getSelectedIndex(){
		for(int i=0; i<tabs.length; i++){
			if(tabs[i].isSelected()) return i;
		}
		
		return -1;
	}
	
	void onTabClicked(View tab){
		int selectedIndex = -1;
		
		for(int i=0; i<tabs.length; i++){
			View otherTab = tabs[i];
			if(otherTab == tab){
				otherTab.setSelected(true);
				selectedIndex = i;
			}else{
				otherTab.setSelected(false);
			}
		}
		
		if(onTabSelectedListener!=null && selectedIndex>=0){
			onTabSelectedListener.onTabSelected(selectedIndex);
		}
	}
	
	public static interface OnNewClickedListener{
		void onNewClicked();
	}
	
	OnNewClickedListener onNewClickedListener;

	public void setOnNewClickedListener(OnNewClickedListener listener){
		this.onNewClickedListener = listener;
	}
	
	void onNewClicked(){
		if(onNewClickedListener!=null)
			onNewClickedListener.onNewClicked();
	}
}
