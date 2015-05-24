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

		// 新建5个一级标题
		Map<String, String> title1 = new HashMap<String, String>();
		Map<String, String> title2 = new HashMap<String, String>();
		Map<String, String> title3 = new HashMap<String, String>();
		Map<String, String> title4 = new HashMap<String, String>();
		Map<String, String> title5 = new HashMap<String, String>();
		title1.put("group", "  考古报告");
		title2.put("group", "  研究论著");
		title3.put("group", "  历史文献");
		title4.put("group", "  科普随笔");
		title5.put("group", "  其他作品");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(title1);
		list.add(title2);
		list.add(title3);
		list.add(title4);
		list.add(title5);

		// 创建5个二级标题；
		// 第一个二级标题
		Map<String, String> content0_0 = new HashMap<String, String>();
		Map<String, String> content0_1 = new HashMap<String, String>();
		Map<String, String> content0_2 = new HashMap<String, String>();
		Map<String, String> content0_3 = new HashMap<String, String>();
		Map<String, String> content0_4 = new HashMap<String, String>();
		content0_0.put("child", "《风雪定陵》");
		content0_1.put("child", "《中国新石器时代》");
		content0_2.put("child", "《夏商周断代工程》");
		content0_3.put("child", "《中国考古学通论》");
		content0_4.put("child", "《最早的中国》");
		List<Map<String, String>> content0 = new ArrayList<Map<String, String>>();
		content0.add(content0_0);
		content0.add(content0_1);
		content0.add(content0_2);
		content0.add(content0_3);
		content0.add(content0_4);
		// 第二个二级标题
		Map<String, String> content1_0 = new HashMap<String, String>();
		Map<String, String> content1_1 = new HashMap<String, String>();
		Map<String, String> content1_2 = new HashMap<String, String>();
		Map<String, String> content1_3 = new HashMap<String, String>();
		Map<String, String> content1_4 = new HashMap<String, String>();
		content1_0.put("child", "《呼吸艺术》");
		content1_1.put("child", "《明式家具》");
		content1_2.put("child", "《中国博物馆学》");
		content1_3.put("child", "《礼仪注释》");
		content1_4.put("child", "《中国物质遗产名录》");
		List<Map<String, String>> content1 = new ArrayList<Map<String, String>>();
		content1.add(content1_0);
		content1.add(content1_1);
		content1.add(content1_2);
		content1.add(content1_3);
		content1.add(content1_4);
		// 第三个二级标题
		Map<String, String> content2_0 = new HashMap<String, String>();
		Map<String, String> content2_1 = new HashMap<String, String>();
		Map<String, String> content2_2 = new HashMap<String, String>();
		Map<String, String> content2_3 = new HashMap<String, String>();
		Map<String, String> content2_4 = new HashMap<String, String>();
		content2_0.put("child", "《营造法式》");
		content2_1.put("child", "《中国传统文化》");
		content2_2.put("child", "《中国古代天文与人文》");
		content2_3.put("child", "《中国建筑史》");
		content2_4.put("child", "《文物考古》");
		List<Map<String, String>> content2 = new ArrayList<Map<String, String>>();
		content2.add(content2_0);
		content2.add(content2_1);
		content2.add(content2_2);
		content2.add(content2_3);
		content2.add(content2_4);
		// 第四个二级标题
		Map<String, String> content3_0 = new HashMap<String, String>();
		Map<String, String> content3_1 = new HashMap<String, String>();
		Map<String, String> content3_2 = new HashMap<String, String>();
		Map<String, String> content3_3 = new HashMap<String, String>();
		Map<String, String> content3_4 = new HashMap<String, String>();
		content3_0.put("child", "《城记》");
		content3_1.put("child", "《到古代中国旅行》");
		content3_2.put("child", "《古峡迷雾》");
		content3_3.put("child", "《去古人庭院散步》");
		content3_4.put("child", "《唐朝穿越指南》");
		List<Map<String, String>> content3 = new ArrayList<Map<String, String>>();
		content3.add(content3_0);
		content3.add(content3_1);
		content3.add(content3_2);
		content3.add(content3_3);
		content3.add(content3_4);
		// 第五个二级标题
		Map<String, String> content4_0 = new HashMap<String, String>();
		Map<String, String> content4_1 = new HashMap<String, String>();
		Map<String, String> content4_2 = new HashMap<String, String>();
		Map<String, String> content4_3 = new HashMap<String, String>();
		Map<String, String> content4_4 = new HashMap<String, String>();
		content4_0.put("child", "《考古笔记》");
		content4_1.put("child", "《考古的故事》");
		content4_2.put("child", "《考古中国》");
		content4_3.put("child", "《田野考古规程》");
		content4_4.put("child", "《人类考古发现》");
		List<Map<String, String>> content4 = new ArrayList<Map<String, String>>();
		content4.add(content4_0);
		content4.add(content4_1);
		content4.add(content4_2);
		content4.add(content4_3);
		content4.add(content4_4);

		// 存放5个一级标题
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
		 * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置
		 * mOptionalIconsVisible为true， 给菜单设置图标时才可见
		 */
		setIconEnable(menu, true);
		menu.add(0, 0, 0, "查看图书").setIcon(R.drawable.open_book);
		return super.onCreateOptionsMenu(menu);
	}

	// enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);

			// MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
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
