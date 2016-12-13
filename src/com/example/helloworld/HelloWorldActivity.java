package com.example.helloworld;

import com.example.helloworld.fragments.pages.FeedListFragment;
import com.example.helloworld.fragments.pages.MyProfileFragment;
import com.example.helloworld.fragments.pages.NoteListFragment;
import com.example.helloworld.fragments.pages.SearchPageFragment;
import com.example.helloworld.fragments.widgets.MainTabbarFragment;
import com.example.helloworld.fragments.widgets.MainTabbarFragment.OnNewClickedListener;
import com.example.helloworld.fragments.widgets.MainTabbarFragment.OnTabSelectedListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class HelloWorldActivity extends Activity {

	FeedListFragment contentFeedList = new FeedListFragment();
	NoteListFragment contentNoteList = new NoteListFragment();
	SearchPageFragment contentSearchPage = new SearchPageFragment();
	MyProfileFragment contentMyProfile = new MyProfileFragment();

	MainTabbarFragment tabbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_helloworld);

		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
		tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {

			@Override
			public void onTabSelected(int index) {
				changeContentFragment(index);
			}
		});
		
		tabbar.setOnNewClickedListener(new OnNewClickedListener() {
			
			@Override
			public void onNewClicked() {
				bringUpEditor();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		if(tabbar.getSelectedIndex()<0){
			tabbar.setSelectedItem(0);	
		}
	}

	void changeContentFragment(int index){
		Fragment newFrag = null;

		switch (index) {
		case 0: newFrag = contentFeedList; break;
		case 1: newFrag = contentNoteList; break;
		case 2: newFrag = contentSearchPage; break;
		case 3: newFrag = contentMyProfile; break;

		default:break;
		}

		if(newFrag==null) return;

		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content, newFrag)
		.commit();
	}
	
	void bringUpEditor(){
		Intent itnt = new Intent(this, NewContentActivity.class);
		startActivity(itnt);
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
	}
}
