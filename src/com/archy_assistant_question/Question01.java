package com.archy_assistant_question;

import java.util.ArrayList;
import java.util.List;
import com.archy_assistant.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Question01 extends Activity {

	public Question01_Viewpager viewpager;
	public Question01_ViewAdapter viewAdapter;
	public List<View> viewItems;
	public String TAG = "Question01";
	public ConstantArrayList constantArrayList;
	public ArrayList<Question01_Item> dataItem;
	public ArrayList<ArrayList<Question01_Item>> DataItems;
	public int position;

	public TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment01_question01);
		tv = (TextView) findViewById(R.id.question01_title);
		tv.setText("欢迎参与调查问卷");
		Log.e(TAG, "oncreate()");
		this.position = getIntent().getIntExtra("position", 0);
		dataItem = new ArrayList<Question01_Item>();
		constantArrayList = (ConstantArrayList) getApplication();
		DataItems = this.constantArrayList.getDataItems();
		dataItem = DataItems.get(position);
		init();

	}

	public void init() {
		Log.e(TAG, "onCreate():init()");
		viewItems = new ArrayList<View>();
		for (int i = 0; i < dataItem.size(); i++) {
			viewItems.add(getLayoutInflater().inflate(
					R.layout.question01_viewpager_item, null));
		}

		viewpager = (Question01_Viewpager) findViewById(R.id.question01_viewpager);
		viewAdapter = new Question01_ViewAdapter(this, viewItems, dataItem,position);
		viewpager.setAdapter(viewAdapter);
		viewpager.getParent().requestDisallowInterceptTouchEvent(false);

	}

	// 根据索引值切换界面
	public void setCurrentView(int index) {
		viewpager.setCurrentItem(index);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
