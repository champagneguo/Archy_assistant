package com.archy_assistant_question;

import java.util.ArrayList;
import com.archy_assistant.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Fragment01 extends Fragment implements OnClickListener,
		OnPageChangeListener {

	private String TAG = "Fragment01";
	// create a new object viewpager
	private ViewPager viewpager;
	// define a new adapter
	private Fragment01_ViewpagerAdapter myadapter;
	// Create an array to store views;
	private ArrayList<View> views;
	// create an array to store the keys of the pictures
	// 引导图片资源
	private static final int[] pics = { R.drawable.guide1, R.drawable.guide2,
			R.drawable.guide3 };
	// create an ImageView array to store the pictures on the bottom of the
	// 底部小点图片
	private ImageView[] points;
	// record the current position of the pictures;
	private int currentIndex;
	//
	private View parentView;

	//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fragment01, container, false);
		Log.e(TAG, "parentView:" + parentView);
		views = new ArrayList<View>();
		viewpager = (ViewPager) parentView
				.findViewById(R.id.fragment01_viewpager);
		Log.e(TAG, "this.getActivity():" + getActivity());
		myadapter = new Fragment01_ViewpagerAdapter(views, this.getActivity());
		Log.e(TAG, "myadapter:" + myadapter);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		// 图片导入到Views
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(getActivity());
			iv.setLayoutParams(params);
			iv.setImageResource(pics[i]);
			views.add(iv);
		}

		// input the data
		viewpager.setAdapter(myadapter);

		viewpager.setOnPageChangeListener(this);

		// init the points
		initpoints();

		return parentView;
	}

	public void initpoints() {
		Log.e(TAG, "initpoints()");
		LinearLayout linerlayout = (LinearLayout) parentView
				.findViewById(R.id.fragment01_linelayout);
		points = new ImageView[pics.length];
		Log.e(TAG, "points:" + points.length);

		for (int i = 0; i < pics.length; i++) {
			points[i] = (ImageView) linerlayout.getChildAt(i);
			points[i].setEnabled(true);
			points[i].setOnClickListener(this);
			points[i].setTag(i);
			Log.e(TAG, "points[i].setTag(i)" + i);
		}

		currentIndex = 0;
		Log.e(TAG, "points[currentIndex]:" + points[0]);
		points[currentIndex].setEnabled(false);
		Log.e(TAG, "points[currentIndex]");
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		setCutDot(position);

	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();
		Log.e(TAG, "click:" + position);
		setCutView(position);
		setCutDot(position);
	}

	// 设置图片的位置
	public void setCutView(int position) {
		Log.e(TAG, "setCutView" + position);
		if (position < 0 || position >= pics.length)
			return;
		viewpager.setCurrentItem(position);
	}

	// 设置当前小数点位置；
	public void setCutDot(int position) {
		Log.e(TAG, "setCutDot" + position);
		if (position < 0 || position >= pics.length || position == currentIndex)
			return;
		points[position].setEnabled(false);
		points[currentIndex].setEnabled(true);
		currentIndex = position;
	}

}
