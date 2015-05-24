package com.archy_assistant_question;

import java.util.ArrayList;

import com.archy_assistant.R;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Question01_ListViewAdapter extends BaseAdapter {

	public String TAG = "Question01_ListViewAdapter";
	public Question01 mcontext;
	public ArrayList<String> voteAnswers;
	// Ĭ��ѡ�е�һ�
	public int selected = -1;
	public ViewHolder holder;

	public Question01_ListViewAdapter(Question01 mcontext,
			ArrayList<String> voteAnswers) {
		this.mcontext = mcontext;
		this.voteAnswers = voteAnswers;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return voteAnswers.size();
	}

	// ����ѡ����λ��ˢ��adapter
	public void updateIndex(int position) {
		Log.e(TAG, "updateIndex:" + position);
		selected = position;
		// �����ٴε���getView������������������
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return voteAnswers.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// View�е�setTag��Onbect����ʾ��View���һ����������ݣ��Ժ������getTag()���������ȡ������

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = new ViewHolder();
		if (convertView == null) {
			convertView = mcontext.getLayoutInflater().inflate(
					R.layout.question01_listview_item, null);
			holder.select_image = (ImageView) convertView
					.findViewById(R.id.question01_select_image);
			holder.select_text = (TextView) convertView
					.findViewById(R.id.question01_select_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (selected == position) {
			Log.e(TAG, "getView:" + position);
			holder.select_image
					.setImageResource(R.drawable.question01_selected);
			holder.select_text.setTextColor(mcontext.getResources().getColor(
					R.color.vote_submit_orange));
		} else {
			holder.select_image.setImageResource(R.drawable.question01_normal);
			holder.select_text.setTextColor(mcontext.getResources().getColor(
					R.color.black));
		}
		holder.select_text.setText(voteAnswers.get(position));
		return convertView;
	}

	// �Զ�����
	class ViewHolder {
		ImageView select_image;
		TextView select_text;
	}

}
