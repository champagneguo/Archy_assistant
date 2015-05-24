package com.archy_assistant;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.archy_assistant_book.InsertBook;
import com.archy_assistant_book.ReadBook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;

public class Activity10 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_10);
		ExpandableListView expandlistview = (ExpandableListView) findViewById(R.id.activity10_elv);

		// �½�5��һ������
		Map<String, String> title1 = new HashMap<String, String>();
		Map<String, String> title2 = new HashMap<String, String>();
		Map<String, String> title3 = new HashMap<String, String>();
		Map<String, String> title4 = new HashMap<String, String>();
		Map<String, String> title5 = new HashMap<String, String>();
		title1.put("group", "  ���ű���");
		title2.put("group", "  �о�����");
		title3.put("group", "  ��ʷ����");
		title4.put("group", "  �������");
		title5.put("group", "  ������Ʒ");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(title1);
		list.add(title2);
		list.add(title3);
		list.add(title4);
		list.add(title5);

		// ����5���������⣻
		// ��һ����������
		Map<String, String> content0_0 = new HashMap<String, String>();
		Map<String, String> content0_1 = new HashMap<String, String>();
		Map<String, String> content0_2 = new HashMap<String, String>();
		Map<String, String> content0_3 = new HashMap<String, String>();
		Map<String, String> content0_4 = new HashMap<String, String>();
		content0_0.put("child", "����ѩ���꡷");
		content0_1.put("child", "���й���ʯ��ʱ����");
		content0_2.put("child", "�������ܶϴ����̡�");
		content0_3.put("child", "���й�����ѧͨ�ۡ�");
		content0_4.put("child", "��������й���");
		List<Map<String, String>> content0 = new ArrayList<Map<String, String>>();
		content0.add(content0_0);
		content0.add(content0_1);
		content0.add(content0_2);
		content0.add(content0_3);
		content0.add(content0_4);
		// �ڶ�����������
		Map<String, String> content1_0 = new HashMap<String, String>();
		Map<String, String> content1_1 = new HashMap<String, String>();
		Map<String, String> content1_2 = new HashMap<String, String>();
		Map<String, String> content1_3 = new HashMap<String, String>();
		Map<String, String> content1_4 = new HashMap<String, String>();
		content1_0.put("child", "������������");
		content1_1.put("child", "����ʽ�Ҿߡ�");
		content1_2.put("child", "���й������ѧ��");
		content1_3.put("child", "������ע�͡�");
		content1_4.put("child", "���й������Ų���¼��");
		List<Map<String, String>> content1 = new ArrayList<Map<String, String>>();
		content1.add(content1_0);
		content1.add(content1_1);
		content1.add(content1_2);
		content1.add(content1_3);
		content1.add(content1_4);
		// ��������������
		Map<String, String> content2_0 = new HashMap<String, String>();
		Map<String, String> content2_1 = new HashMap<String, String>();
		Map<String, String> content2_2 = new HashMap<String, String>();
		Map<String, String> content2_3 = new HashMap<String, String>();
		Map<String, String> content2_4 = new HashMap<String, String>();
		content2_0.put("child", "��Ӫ�취ʽ��");
		content2_1.put("child", "���й���ͳ�Ļ���");
		content2_2.put("child", "���й��Ŵ����������ġ�");
		content2_3.put("child", "���й�����ʷ��");
		content2_4.put("child", "�����￼�š�");
		List<Map<String, String>> content2 = new ArrayList<Map<String, String>>();
		content2.add(content2_0);
		content2.add(content2_1);
		content2.add(content2_2);
		content2.add(content2_3);
		content2.add(content2_4);
		// ���ĸ���������
		Map<String, String> content3_0 = new HashMap<String, String>();
		Map<String, String> content3_1 = new HashMap<String, String>();
		Map<String, String> content3_2 = new HashMap<String, String>();
		Map<String, String> content3_3 = new HashMap<String, String>();
		Map<String, String> content3_4 = new HashMap<String, String>();
		content3_0.put("child", "���Ǽǡ�");
		content3_1.put("child", "�����Ŵ��й����С�");
		content3_2.put("child", "����Ͽ����");
		content3_3.put("child", "��ȥ����ͥԺɢ����");
		content3_4.put("child", "���Ƴ���Խָ�ϡ�");
		List<Map<String, String>> content3 = new ArrayList<Map<String, String>>();
		content3.add(content3_0);
		content3.add(content3_1);
		content3.add(content3_2);
		content3.add(content3_3);
		content3.add(content3_4);
		// �������������
		Map<String, String> content4_0 = new HashMap<String, String>();
		Map<String, String> content4_1 = new HashMap<String, String>();
		Map<String, String> content4_2 = new HashMap<String, String>();
		Map<String, String> content4_3 = new HashMap<String, String>();
		Map<String, String> content4_4 = new HashMap<String, String>();
		content4_0.put("child", "�����űʼǡ�");
		content4_1.put("child", "�����ŵĹ��¡�");
		content4_2.put("child", "�������й���");
		content4_3.put("child", "����Ұ���Ź�̡�");
		content4_4.put("child", "�����࿼�ŷ��֡�");
		List<Map<String, String>> content4 = new ArrayList<Map<String, String>>();
		content4.add(content4_0);
		content4.add(content4_1);
		content4.add(content4_2);
		content4.add(content4_3);
		content4.add(content4_4);

		// ���5��һ������
		List<List<Map<String, String>>> groups = new ArrayList<List<Map<String, String>>>();
		groups.add(content0);
		groups.add(content1);
		groups.add(content2);
		groups.add(content3);
		groups.add(content4);

		SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
				Activity10.this, list, R.drawable.groups,
				new String[] { "group" }, new int[] { R.id.textGroup }, groups,
				R.drawable.childs, new String[] { "child" },
				new int[] { R.id.textChild });

		expandlistview.setAdapter(sela);
		expandlistview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent(Activity10.this, ReadBook.class);
				intent.putExtra("groupPosition", groupPosition);
				intent.putExtra("childPosition", childPosition);
				startActivity(intent);
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		/*
		 * ���÷�����Ƶ���MenuBuilder��setOptionalIconsVisible��������
		 * mOptionalIconsVisibleΪtrue�� ���˵�����ͼ��ʱ�ſɼ�
		 */
		setIconEnable(menu, true);
		menu.add(0, 0, 0, "�鿴ͼ��").setIcon(R.drawable.open_book);
		return super.onCreateOptionsMenu(menu);
	}

	// enableΪtrueʱ���˵����ͼ����Ч��enableΪfalseʱ��Ч��4.0ϵͳĬ����Ч
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);

			// MenuBuilderʵ��Menu�ӿڣ������˵�ʱ����������menu��ʵ����MenuBuilder����(java�Ķ�̬����)
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == 0) {
			Intent intent = new Intent(Activity10.this, InsertBook.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);

	}
}
