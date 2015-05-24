package com.archy_assistant_question;

import java.util.ArrayList;
import com.archy_assistant.R;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment02_ListviewAdapter extends BaseAdapter {

	private ImageView image;
	private TextView title, type;
	private TextView number;
	private FragmentActivity mContext;
	public ArrayList<ArrayList<Question01_Item>> DataItems;
	public String TAG = "Fragment02_ListviewAdapter";
	public int[] images = { R.drawable.list1, R.drawable.list2,
			R.drawable.list3 };
	public String Type[] = { "考古热点", "考古图书馆", "考古历史常识" };

	public Fragment02_ListviewAdapter(FragmentActivity mContext,
			ArrayList<ArrayList<Question01_Item>> DataItems) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.DataItems = DataItems;
		Log.e(TAG, "构造函数：" + this.DataItems.size());
	}

	@Override
	public int getCount() {
		if (DataItems == null) {
			Log.e(TAG, "getCount()" + 0);
			return 0;
		}
		// TODO Auto-generated method stub
		Log.e(TAG, "getCount()" + DataItems.size());
		return DataItems.size();
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (DataItems == null) {
			Log.e(TAG, "getItem()" + 0);
			return null;
		}
		Log.e(TAG, "getItem" + position);
		return DataItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Log.e(TAG, "getItemId" + position);
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			Log.e(TAG, "(1)getView:" + convertView);
			convertView = View.inflate(mContext, R.layout.fragment02_listview,
					null);
		}
		image = (ImageView) convertView
				.findViewById(R.id.fragment02_listview_image);

		image.setImageResource(images[DataItems.get(position).get(0).getType()]);

		title = (TextView) convertView
				.findViewById(R.id.fragment02_listview_title);
		title.setText(DataItems.get(position).get(0).getTitle());
		type = (TextView) convertView
				.findViewById(R.id.fragment02_listview_type);
		type.setText("类别：" + Type[DataItems.get(position).get(0).getType()]);
		number = (TextView) convertView
				.findViewById(R.id.fragment02_listview_number);
		// 获取参与调查人数；
		number.setText(DataItems.get(position).get(0).getTotalNumber() + "人参与");
		Log.e(TAG, "(2)getView:" + convertView);
		// image = (ImageView)convertView.findViewById(R.id.fragment02_);
		return convertView;
	}
}
