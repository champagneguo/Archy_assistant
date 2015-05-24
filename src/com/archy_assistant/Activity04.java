package com.archy_assistant;

import java.lang.reflect.Method;

import com.archy_assistant_WebBrowser.WebBrowser;
import com.archy_assistant_gallerydemo.Gallery_demo;
import com.archy_assistant_gallerydemo.NewFolder;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Activity04 extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_04);
		// 获取该Activity中的TabHost组件
		TabHost tabHost = getTabHost();
		// 使用Intent添加第一个Tab页面
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("本地图库",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, Gallery_demo.class)));
		// 使用Intent添加第二个Tab页面
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("网络图库")
				.setContent(new Intent(this, WebBrowser.class)));
		/**
		 * String url = "http://image.baidu.com/"; Uri uri = Uri.parse(url);
		 * Intent it = new Intent(Intent.ACTION_VIEW, uri); startActivity(it);
		 **/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置
		 * mOptionalIconsVisible为true， 给菜单设置图标时才可见
		 */
		setIconEnable(menu, true);

		menu.add(0, 0, 0, "新建图片文件夹").setIcon(android.R.drawable.ic_input_add);
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
		int id = item.getItemId();
		if (id == 0) {

			Intent intent = new Intent(Activity04.this, NewFolder.class);
			// 需要重新刷新界面；
			startActivityForResult(intent, 100);

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 10) {
			String Name = data.getStringExtra("name");
			Intent intent = new Intent(Activity04.this, Activity04.class);
			startActivity(intent);
			Toast.makeText(Activity04.this, "创建文件夹:" + Name + "成功！",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
}
