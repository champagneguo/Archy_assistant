package com.archy_assistant_question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.archy_assistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment02 extends Fragment {

	private String TAG = "Fragment02";
	private FragmentActivity mContext;
	public ArrayList<ArrayList<Question01_Item>> DataItems;
	public ArrayList<Question01_Item> dataItem;
	public Question01_Item question01_Item;
	public ConstantArrayList constantArrayList;
	private ListView listview;
	private Button Add;
	private PopupWindow popupWindow;
	private Fragment02_popupAdapter myadapterspinner;
	private Fragment02_ListviewAdapter myAdapter;
	private String categority[] = { "考古热点", "考古博物馆", "考古历史常识" };
	private int location;
	private String title;
	private String time;
	private View parentView;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this.getActivity();
		DataItems = new ArrayList<ArrayList<Question01_Item>>();
		constantArrayList = (ConstantArrayList) mContext.getApplication();
		Log.e(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		DataItems = constantArrayList.getDataItems();
		parentView = inflater.inflate(R.layout.fragment02, container, false);
		Add = (Button) parentView.findViewById(R.id.fragment02_add);

		listview = (ListView) parentView.findViewById(R.id.fragment02_listview);
		Log.e(TAG, "mContext:" + mContext + ":dataItems:" + DataItems.size());
		myAdapter = new Fragment02_ListviewAdapter(mContext, DataItems);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						ListQuestionsActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}

		});
		Log.e(TAG, "onCreateView");
		// TODO Auto-generated method stub
		// 添加一套试卷；
		Add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getPopupWindow();
				popupWindow.showAtLocation(
						parentView.findViewById(R.id.fragment02_layout),
						Gravity.CENTER, 0, 0);
			}
		});
		return parentView;
	}

	// 初始化PopupWindow；
	@SuppressLint("SimpleDateFormat")
	public void getPopupWindow() {
		final EditText Name;
		Spinner spinner;
		TextView Time;
		Button cancle;
		Button save;

		View popupWindow_view = mContext.getLayoutInflater().inflate(
				R.layout.fragment02_popup, null);
		Name = (EditText) popupWindow_view
				.findViewById(R.id.fragment02_popup_name);
		Time = (TextView) popupWindow_view
				.findViewById(R.id.fragment02_popup_time);
		spinner = (Spinner) popupWindow_view
				.findViewById(R.id.fragment02_popup_spinner);
		cancle = (Button) popupWindow_view
				.findViewById(R.id.fragment02_popup_cancle);
		save = (Button) popupWindow_view
				.findViewById(R.id.fragment02_popup_confirm);
		myadapterspinner = new Fragment02_popupAdapter(mContext, categority);
		spinner.setAdapter(myadapterspinner);
		int screenWidth = mContext.getWindowManager().getDefaultDisplay()
				.getWidth();
		int screenHeight = mContext.getWindowManager().getDefaultDisplay()
				.getHeight();
		popupWindow = new PopupWindow(popupWindow_view, screenWidth - 40,
				(screenHeight * 5) / 8, true);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View parent,
					int position, long id) {
				// TODO Auto-generated method stub
				location = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Time.setText(df.format(new Date()));

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				title = Name.getText().toString().trim();
				// TODO Auto-generated method stub
				if (title == null | title.equals("")) {
					Toast.makeText(mContext, "请完成输入", Toast.LENGTH_SHORT)
							.show();
				} else {
					dataItem = new ArrayList<Question01_Item>();
					question01_Item = new Question01_Item();
					question01_Item.setTitle(title);
					question01_Item.setType(location);
					dataItem.add(question01_Item);
					DataItems.add(dataItem);
					myAdapter.notifyDataSetChanged();
					popupWindow.dismiss();
					Toast.makeText(mContext, "试卷添加成功！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

}
