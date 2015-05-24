package com.archy_assistant;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.archy_assistant_network.Login;
import com.archy_assistant_network.Registor;
import com.archy_assistant_software.BundleObject;
import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Activity_menu extends Activity {

	long waitTime = 2000;
	long touchTime = 0;
	public String Username = null;

	private int[] imgItemRes = { R.drawable.img_item0, R.drawable.img_item1,
			R.drawable.img_item2, R.drawable.img_item3, R.drawable.img_item4,
			R.drawable.img_item5, R.drawable.img_item6, R.drawable.img_item7,
			R.drawable.img_item8, R.drawable.img_item9, R.drawable.img_item10,
			R.drawable.img_item11, R.drawable.img_item12,
			R.drawable.img_item13, R.drawable.img_item14 };

	private String[] imgItemDesc = { "考古资讯", "野外调查", "考古笔记", "定位导航", "考古图片",
			"多媒体", "电子邮件", "通信平台", "通讯录", "网络", "考古图书", "考古通", "休闲娱乐", "设置向导",
			"工具" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		Intent intent = getIntent();
		BundleObject bundleObject = (BundleObject) intent
				.getSerializableExtra("Package_name");
		BundleObject bundleObject1 = (BundleObject) intent
				.getSerializableExtra("Package_name1");
		BundleObject bundleObject2 = (BundleObject) intent
				.getSerializableExtra("Package_name2");
		final String Package_name = bundleObject.getPkgName();
		final String Activity_name = bundleObject.getActivityName();
		final String Package_name1 = bundleObject1.getPkgName();
		final String Activity_name1 = bundleObject1.getActivityName();
		final String Package_name2 = bundleObject2.getPkgName();
		final String Activity_name2 = bundleObject2.getActivityName();

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imgItemDesc.length; ++i) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("icon", imgItemRes[i]);
			listItem.put("label", imgItemDesc[i]);
			listItems.add(listItem);
		}
		SimpleAdapter adapter = new SimpleAdapter(Activity_menu.this,
				listItems, R.layout.activity_menu_gv, new String[] { "icon",
						"label" }, new int[] { R.id.activity_menu_image,
						R.id.activity_menu_desc });
		GridView gv = (GridView) findViewById(R.id.activity_menu_gv);
		gv.setAdapter(adapter);
		// 为什么自定义的Adapter，当滑动滚动条时，图标会调换位置？？？？？
		// gv.setAdapter(new ImageItemAdapter(Activity_menu.this,
		// imgItemRes,imgItemDesc));

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 2) {
					if (Package_name1 == null && Package_name2 == null) {
						Toast.makeText(Activity_menu.this, "您的手机尚未安装记事本程序！",
								Toast.LENGTH_SHORT).show();
					} else {
						if (Package_name2 != null) {
							Intent intent = new Intent();
							intent.setComponent(new ComponentName(
									Package_name2, Activity_name2));
							startActivity(intent);
						} else {
							Intent intent = new Intent();
							intent.setComponent(new ComponentName(
									Package_name1, Activity_name1));
							startActivity(intent);
						}
					}
				} else if (position == 5) {
					// 如何调用系统的播放器功能
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(Package_name,
							Activity_name));
					startActivity(intent);
				} else if (position == 6) {
					// 调用系统邮件发送功能；
					// 直接调用第三方软件,需知道第三方软件的包名和软件程序入口；
					Intent intent = new Intent();
					intent.setComponent(new ComponentName("com.android.email",
							"com.android.email.activity.Welcome"));
					intent.setAction(Intent.ACTION_VIEW);
					startActivity(intent);
				} else if (position == 8) {
					// 调用系统的联系人列表
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(Contacts.People.CONTENT_URI);
					startActivity(intent);
				} else if (position == 9) {
					// 调用系统的google浏览器
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(
							"com.android.browser",
							"com.android.browser.BrowserActivity"));
					intent.setAction(Intent.ACTION_VIEW);
					startActivity(intent);
				} else if (position == 13) {
					// 调用系统的设置向导；
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(
							"com.android.settings",
							"com.android.settings.Settings"));
					intent.setAction(Intent.ACTION_VIEW);
					startActivity(intent);
				}

				else {
					// 利用反射机制实现从主Activity向其他Activity的跳转
					try {
						String className = "com.archy_assistant.Activity"
								+ (position <= 9 ? "0" + position : ""
										+ position);
						Class<?> clazz = Class.forName(className);
						Intent intent = new Intent(Activity_menu.this, clazz);
						startActivity(intent);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置
		 * mOptionalIconsVisible为true， 给菜单设置图标时才可见
		 */
		setIconEnable(menu, true);

		menu.add(Menu.NONE, 0, 0,"	登录").setIcon(android.R.drawable.ic_menu_agenda);
		menu.add(Menu.NONE, 1, 1,"	注册").setIcon(android.R.drawable.ic_menu_add);
		//menu.add(Menu.NONE, 2, 2,"	同步").setIcon(android.R.drawable.ic_menu_upload);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
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
			// 登录成功后，返回信息给主界面；
			Intent intent = new Intent(Activity_menu.this, Login.class);
			startActivityForResult(intent, 100);

		} else if (id == 1) {
			// 注册后，返回信息给主界面；
			Intent intent = new Intent(Activity_menu.this, Registor.class);
			startActivityForResult(intent, 200);

		} else if (id == 2) {
			// 同步
			Toast.makeText(Activity_menu.this, "同步功能正在完善", Toast.LENGTH_SHORT)
					.show();
		}
		return super.onOptionsItemSelected(item);
	}

	// 重写 onActivityResult方法；
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == 10) {
			Username = data.getExtras().getString("username_login");
			Toast.makeText(Activity_menu.this, "用户：" + Username + "\n登录成功！",
					Toast.LENGTH_LONG).show();
		}
		if (resultCode == 20) {
			// 登录后返回给
			Username = data.getExtras().getString("username_register");
			Toast.makeText(Activity_menu.this, "用户：" + Username + "\n登录成功！",
					Toast.LENGTH_LONG).show();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 实现再按一次推出系统。
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
