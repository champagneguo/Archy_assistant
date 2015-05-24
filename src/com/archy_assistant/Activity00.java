package com.archy_assistant;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Activity00 extends Activity implements
		ExpandableListView.OnChildClickListener,
		ExpandableListView.OnGroupClickListener {
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private ExpandableListView expandList;
	private ArrayList<String> grouplist;
	private ArrayList<List<String>> childlist;
	private MyexpandableListAdapter adapter;
	private PopupWindow popupWindow;
	private String TAG = "Activity00";
	private int i = 0, j = 0, temp = 0;
	private String name, url;

	private int[] ImageArray = { R.drawable.image00, R.drawable.image01,
			R.drawable.image02, R.drawable.image03, R.drawable.image10,
			R.drawable.image11, R.drawable.image12, R.drawable.image13,
			R.drawable.image14, R.drawable.image20, R.drawable.image21,
			R.drawable.image22, R.drawable.image23 };
	private List<String> nameList, urlList;
	private int screenWidth = 0;
	private int screenHeight = 0;

	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_00);
		preferences = getSharedPreferences(TAG, MODE_APPEND);
		editor = preferences.edit();
		temp = preferences.getAll().size() / 2;
		expandList = (ExpandableListView) findViewById(R.id.activity_00_elv);
		grouplist = new ArrayList<String>();
		nameList = new ArrayList<String>();
		urlList = new ArrayList<String>();
		grouplist.add("���ڿ�����վ");
		grouplist.add("��ѧ����ѧԺ");
		grouplist.add("ʡ�в����");
		grouplist.add("�����ղ�");
		childlist = new ArrayList<List<String>>();
		for (int i = 0; i < grouplist.size(); i++) {
			ArrayList<String> childtemp = null;
			if (i == 0) {
				childtemp = new ArrayList<String>();
				childtemp.add("�й�������");
				childtemp.add("�й�������");
				childtemp.add("�й�ʯ��������");
				childtemp.add("�й�������Ϣ��");
			}
			if (i == 1) {
				childtemp = new ArrayList<String>();
				childtemp.add("������ѧ�����Ĳ�Ժ");
				childtemp.add("ɽ����ѧ���Ų����");
				childtemp.add("������ѧ�����");
				childtemp.add("�Ͽ���ѧ��ʷѧԺ");
				childtemp.add("���������ѧ���岩���");
			}
			if (i == 2) {
				childtemp = new ArrayList<String>();
				childtemp.add("�й����Ҳ����");
				childtemp.add("�ʹ�����Ժ");
				childtemp.add("�׶������");
				childtemp.add("��ʼ�Ƶ��격���");
			}
			if (i == 3) {
				childtemp = new ArrayList<String>();
				Log.e(TAG, "childtemp:" + childtemp);
				for (j = 0; j < temp; j++) {
					childtemp.add(preferences.getString(j + "", null));
					nameList.add(preferences.getString(j + "", null));
					urlList.add(preferences
							.getString(j + "" + j + "" + j, null));
				}

			}
			childlist.add(childtemp);
		}

	}

	@Override
	protected void onResume() {
		Log.e(TAG, "onResume:" + childlist.size());
		if (childlist.size() == 4) {
			Log.e(TAG, "nameList" + nameList.size());

		}
		adapter = new MyexpandableListAdapter(Activity00.this);
		expandList.setAdapter(adapter);
		expandList.setOnChildClickListener(this);
		expandList.setOnGroupClickListener(this);
		super.onResume();
	}

	class MyexpandableListAdapter extends BaseExpandableListAdapter {

		private LayoutInflater inflater;
		private int location;
		@SuppressWarnings("unused")
		private Context context;

		public MyexpandableListAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		// ���ظ��б����
		@Override
		public int getGroupCount() {
			return grouplist.size();
		}

		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			childlist.get(3).add(name);
			super.notifyDataSetChanged();
		}

		// �������б����
		@Override
		public int getChildrenCount(int groupPosition) {

			return childlist.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {

			return grouplist.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childlist.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {

			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupHolder groupHolder = null;
			if (convertView == null) {
				Log.e("getGroupView", "1 convertView is " + convertView);
				groupHolder = new GroupHolder();
				convertView = inflater
						.inflate(R.layout.activity_00_group, null);
				groupHolder.textView = (TextView) convertView
						.findViewById(R.id.activity00_group_text);
				groupHolder.number = (TextView) convertView
						.findViewById(R.id.activity00_group_text2);
				groupHolder.textView.setTextSize(20);
				groupHolder.number.setTextSize(16);
				convertView.setTag(groupHolder);

			} else {
				Log.e("getGroupView", "2 convertView is " + convertView);
				groupHolder = (GroupHolder) convertView.getTag();
			}

			groupHolder.textView.setText(getGroup(groupPosition).toString());
			groupHolder.number.setText("" + getChildrenCount(groupPosition));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			if (convertView == null) {
				Log.e("getChildView", "1 convertView is " + convertView);
				convertView = inflater.inflate(R.layout.activity_00_item, null);
			}
			Log.e("getChildView", "2 convertView is " + convertView);
			TextView textView = (TextView) convertView
					.findViewById(R.id.activity00_item);
			ImageView imageview = (ImageView) convertView
					.findViewById(R.id.activity00_group_image);
			textView.setTextSize(15);
			textView.setText(getChild(groupPosition, childPosition).toString());
			if (groupPosition == 0) {
				location = childPosition;
			} else if (groupPosition == 1) {
				location = getChildrenCount(0) + childPosition;
			} else if (groupPosition == 2) {
				location = getChildrenCount(0) + getChildrenCount(1)
						+ childPosition;
			} else if (groupPosition == 3) {
				location = 0;
			}
			imageview.setImageResource(ImageArray[location]);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	@Override
	public boolean onGroupClick(final ExpandableListView parent, final View v,
			int groupPosition, final long id) {
		Log.e("onGroupClick", "�����" + groupPosition);
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Log.e("onChildClick", "�����" + groupPosition + childPosition);
		if (groupPosition == 0) {
			if (childPosition == 0) {
				String url = "http://www.kaogu.cn/html/cn/";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 1) {
				String url = "http://www.beitie.org/Index.asp";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 2) {
				String url = "http://www.cavetemples.com/index.asp";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 3) {
				String url = "http://www.ccrnews.com.cn/index.html ";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			}
		} else if (groupPosition == 1) {
			if (childPosition == 0) {
				String url = "http://w3.pku.edu.cn/academic/archeology/index.htm ";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 1) {
				String url = "http://museum.sdu.edu.cn/ ";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 2) {
				String url = "http://bwg.nwu.edu.cn/";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 3) {
				String url = "http://history.nankai.edu.cn/";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 4) {
				String url = "http://bwg.muc.edu.cn/index.aspx";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			}
		} else if (groupPosition == 2) {
			if (childPosition == 0) {
				String url = "http://www.chnmuseum.cn/tabid/40/Default.aspx";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 1) {
				String url = "http://www.dpm.org.cn/shtml/1/@/9057.html";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 2) {
				String url = "http://www.capitalmuseum.org.cn/";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			} else if (childPosition == 3) {
				getPopupWindow();
				String url = "http://www.bmy.com.cn/";
				Uri uri = Uri.parse(url);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			}
		} else if (groupPosition == 3) {
			String url = "http://" + urlList.get(childPosition);
			Log.e(TAG, "url:" + url);
			Uri uri = Uri.parse(url);
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(it);
		}
		return false;
	}

	class GroupHolder {
		TextView textView;
		TextView number;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 1, "��Ӹ����ղ�");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();

		if (id == 0) {
			// ����popupWindow�������õ���λ�á�
			Log.e(TAG, "onOptionsItemSelected:" + id);
			getPopupWindow();
			Log.e(TAG, "getPopupWindow()" + popupWindow);
			// ������ʾλ�ã�
			popupWindow.showAtLocation(findViewById(R.id.activity_00_layout),
					Gravity.CENTER, 0, 0);
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("deprecation")
	public void getPopupWindow() {

		final EditText Name;
		final EditText Url;
		Button cancle;
		Button confirm;

		View popupWindow_view = getLayoutInflater().inflate(
				R.layout.activity_00_popup, null, false);
		Log.e(TAG, "getPopupWindow:" + popupWindow_view);
		screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		popupWindow = new PopupWindow(popupWindow_view, screenWidth - 40,
				screenHeight / 2, true);
		Name = (EditText) popupWindow_view
				.findViewById(R.id.activity00_popup_name);
		Url = (EditText) popupWindow_view
				.findViewById(R.id.activity00_popup_url);
		cancle = (Button) popupWindow_view
				.findViewById(R.id.activity00_popup_cancle);
		confirm = (Button) popupWindow_view
				.findViewById(R.id.activity00_popup_confirm);

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				name = Name.getText().toString().trim();
				url = Url.getText().toString().trim();
				if (name.equals("") || url.equals("")) {
					Toast.makeText(Activity00.this, "�������д", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(Activity00.this, "����ղأ�", Toast.LENGTH_SHORT)
							.show();
					editor.putString(i + "", name);
					editor.putString(i + "" + i + "" + i, url);
					editor.commit();
					i++;
					nameList.add(name);
					urlList.add(url);
					adapter.notifyDataSetChanged();
					popupWindow.dismiss();
				}
			}
		});
	}

	

}