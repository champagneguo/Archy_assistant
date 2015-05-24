package com.archy_assistant_question;

import java.util.ArrayList;
import java.util.List;
import com.archy_assistant.R;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

public class Question01_ViewAdapter extends PagerAdapter {

	public Question01 mcontext;
	public List<View> views;
	public ArrayList<Question01_Item> dataItem;
	public ViewHolder holder;
	public View convertView;
	public Question01_ListViewAdapter listViewAdapter;
	public String TAG = "Question01_ViewAdapter";
	public int Item_position;
	public int[] temp;
	public ConstantArrayList constantArrayList;
	public boolean flag = false;
	public int position = 0;

	public Question01_ViewAdapter(Question01 question01, List<View> viewItems,
			ArrayList<Question01_Item> dataItem, int position) {
		mcontext = question01;
		this.views = viewItems;
		this.dataItem = dataItem;
		this.position = position;
		temp = new int[dataItem.size()];
		Log.e(TAG, "���캯����temp��С" + temp.length);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (views == null)
			return 0;
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();
		convertView = views.get(position);
		holder.title = (TextView) convertView
				.findViewById(R.id.question01_title);
		holder.question = (TextView) convertView
				.findViewById(R.id.question01_question);
		holder.listView = (ListView) convertView
				.findViewById(R.id.question01_listview);
		holder.previousBtn = (LinearLayout) convertView
				.findViewById(R.id.question01_linerlayout_previous);
		holder.nextBtn = (LinearLayout) convertView
				.findViewById(R.id.question01_linerlayout_next);
		holder.nextImage = (ImageView) convertView
				.findViewById(R.id.question01_next_image);
		holder.nextText = (TextView) convertView
				.findViewById(R.id.question01_next_text);
		// ��ȡ�ʾ�ı��⣻
		holder.title.setText("<" + dataItem.get(position).getTitle() + ">");

		holder.question.setText(dataItem.get(position).getVoteQuestion());
		listViewAdapter = new Question01_ListViewAdapter(mcontext, dataItem
				.get(position).getVoteAnswers());
		holder.listView.setAdapter(listViewAdapter);

		holder.listView.setOnItemClickListener(new ListViewOnClickListener(
				listViewAdapter));

		// ��һҳ����"��һ��"��ť

		if (position == 0) {
			holder.previousBtn.setVisibility(View.GONE);
		} else {
			// ֻ������һ�⣻
			holder.previousBtn.setVisibility(View.GONE);
			holder.previousBtn.setOnClickListener(new LinearOnClickListener(
					position - 1));
		}
		// ���һҳ�޸�"��һ��"��ť����
		if (position == views.size() - 1) {
			holder.nextText.setText("�ύ");
			holder.nextImage.setImageResource(R.drawable.question01_finish);
		}
		holder.nextBtn.setOnClickListener(new LinearOnClickListener(
				position + 1));

		container.addView(views.get(position));
		return views.get(position);
	}

	// ʵ�ֶ�listView������
	class ListViewOnClickListener implements OnItemClickListener {

		private Question01_ListViewAdapter mylistViewAdapter;

		public ListViewOnClickListener(
				Question01_ListViewAdapter listViewAdapter) {
			mylistViewAdapter = listViewAdapter;
			flag = false;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.e(TAG, "onItemClick" + position);
			// ���ø���ѡ����ͼƬ���ı��仯??????
			mylistViewAdapter.updateIndex(position);
			Item_position = position;
			flag = true;
		}
	}

	//
	class LinearOnClickListener implements OnClickListener {

		private int myPosition;

		public LinearOnClickListener(int position) {
			myPosition = position;
		}

		@Override
		public void onClick(View v) {
			if (flag) {
				Log.e(TAG, "onClick" + myPosition);
				if (v.getId() == R.id.question01_linerlayout_next) {
					Log.e(TAG, "�����һ��" + Item_position);
					temp[myPosition - 1] = Item_position;
					Log.e(TAG, "temp[myPosition]:" + temp[myPosition - 1]);
				}
				if (myPosition == views.size()) {

					// ֻ�е��û��ύ����ʱ���޸�ȫ�ֱ�����ֵ��
					for (int i = 0; i < temp.length; i++) {
						Log.e(TAG, "�ύʱ" + temp[i]);
						dataItem.get(i).setCountNext(temp[i]);
						// ���ύ����ʱ���޸Ĳ���������
						dataItem.get(i).setTotalNumber();
					}
					// ���ύ����ʱ���޸Ĳ���������
					dataItem.get(myPosition - 1).setTotalNumber();
					Log.e(TAG, "LinearOnClickListener+onClick:" + mcontext);
					Toast.makeText(mcontext, "��л������ʾ����!", Toast.LENGTH_SHORT)
							.show();
					// ���޸Ĺ��������ٴμ��ص�ȫ�ֱ�����
					constantArrayList = (ConstantArrayList) mcontext
							.getApplication();
					constantArrayList.getDataItems().set(position, dataItem);
					Intent intent = new Intent();
					mcontext.setResult(100, intent);
					mcontext.finish();
				} else {
					mcontext.setCurrentView(myPosition);
				}
			} else {
				Toast.makeText(mcontext, "��������ѡ��", Toast.LENGTH_SHORT).show();
			}
		}
	}

	// �Զ�����ViewHolder
	class ViewHolder {
		ListView listView;
		TextView title;
		TextView question;
		TextView answer;
		LinearLayout previousBtn, nextBtn;
		TextView nextText;
		ImageView nextImage;
	}
}
