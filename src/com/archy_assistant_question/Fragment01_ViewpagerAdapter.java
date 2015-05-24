package com.archy_assistant_question;

import java.util.ArrayList;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Fragment01_ViewpagerAdapter extends PagerAdapter {

	private ArrayList<View> views;
	private ViewPager viewPager;
	private FragmentActivity activity;
	private String TAG = "Fragment01_ViewpagerAdapter";
	private int list = 0;

	public Fragment01_ViewpagerAdapter(ArrayList<View> views,
			FragmentActivity activity) {
		// TODO Auto-generated constructor stub
		this.views = views;
		this.activity = activity;
		Log.e(TAG, "activity:" + activity);
	}

	// ��õ�ǰҳ����
	@Override
	public int getCount() {
		Log.e(TAG, "getCount()");
		if (views != null)
			return views.size();
		return 0;
	}

	// �ж϶���ҳ���Ƿ�����
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		// TODO Auto-generated method stub
		Log.e(TAG, "isViewFromObject");
		return (view == arg1);
	}

	@Override
	public Object instantiateItem(View view, final int position) {

		View viewItem = views.get(position);
		viewPager = ((ViewPager) view);
		viewPager.addView(viewItem, 0);
		viewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.e(TAG,
						"instantiateItem:onClick(View v)+startActivityForResult");
				Intent intent = new Intent(activity, Question01.class);
				intent.putExtra("position", position);
				// requestcode ������activity���Ǹ���������Ķ��������յ���Ϣ����������жϣ�
				activity.startActivityForResult(intent, 20);
			}
		});

		return viewItem;
	}

	@Override
	public void destroyItem(View view, int position, Object object) {
		// TODO Auto-generated method stub
		Log.e(TAG, "destroyItem");
		((ViewPager) view).removeView(views.get(position));
	}

	public void setPosition(int position) {
		this.list = position;
	}

	public int getPosition() {
		return list;
	}
}
