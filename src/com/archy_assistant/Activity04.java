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
		// ��ȡ��Activity�е�TabHost���
		TabHost tabHost = getTabHost();
		// ʹ��Intent��ӵ�һ��Tabҳ��
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("����ͼ��",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, Gallery_demo.class)));
		// ʹ��Intent��ӵڶ���Tabҳ��
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("����ͼ��")
				.setContent(new Intent(this, WebBrowser.class)));
		/**
		 * String url = "http://image.baidu.com/"; Uri uri = Uri.parse(url);
		 * Intent it = new Intent(Intent.ACTION_VIEW, uri); startActivity(it);
		 **/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * ���÷�����Ƶ���MenuBuilder��setOptionalIconsVisible��������
		 * mOptionalIconsVisibleΪtrue�� ���˵�����ͼ��ʱ�ſɼ�
		 */
		setIconEnable(menu, true);

		menu.add(0, 0, 0, "�½�ͼƬ�ļ���").setIcon(android.R.drawable.ic_input_add);
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
		int id = item.getItemId();
		if (id == 0) {

			Intent intent = new Intent(Activity04.this, NewFolder.class);
			// ��Ҫ����ˢ�½��棻
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
			Toast.makeText(Activity04.this, "�����ļ���:" + Name + "�ɹ���",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
}
