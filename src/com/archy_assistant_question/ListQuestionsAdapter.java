package com.archy_assistant_question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.archy_assistant.R;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListQuestionsAdapter extends BaseAdapter {

	private ArrayList<Question01_Item> dataItem;
	private TextView questions, time, number;
	private ListQuestionsActivity mContext;

	public ListQuestionsAdapter(ListQuestionsActivity mContext,
			ArrayList<Question01_Item> dataItem) {
		this.dataItem = dataItem;
		this.mContext = mContext;

	}

	@Override
	public int getCount() {
		if (dataItem == null)
			return 0;
		return dataItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (dataItem == null)
			return null;
		return dataItem.get(arg0);
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.listquestions_listview, null);
		}
		questions = (TextView) convertView
				.findViewById(R.id.listquestions_listview_quetions);
		time = (TextView) convertView
				.findViewById(R.id.listquestions_listview_time);
		number = (TextView) convertView
				.findViewById(R.id.listquestions_listview_number);
		questions.setText(dataItem.get(position).getVoteQuestion());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time.setText(df.format(new Date()));
		number.setText(dataItem.get(position).getTotalNumber() + "»À≤Œ”Î");
		return convertView;
	}

}
