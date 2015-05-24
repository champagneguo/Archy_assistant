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

	private String[] imgItemDesc = { "������Ѷ", "Ұ�����", "���űʼ�", "��λ����", "����ͼƬ",
			"��ý��", "�����ʼ�", "ͨ��ƽ̨", "ͨѶ¼", "����", "����ͼ��", "����ͨ", "��������", "������",
			"����" };

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
		// Ϊʲô�Զ����Adapter��������������ʱ��ͼ������λ�ã���������
		// gv.setAdapter(new ImageItemAdapter(Activity_menu.this,
		// imgItemRes,imgItemDesc));

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 2) {
					if (Package_name1 == null && Package_name2 == null) {
						Toast.makeText(Activity_menu.this, "�����ֻ���δ��װ���±�����",
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
					// ��ε���ϵͳ�Ĳ���������
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(Package_name,
							Activity_name));
					startActivity(intent);
				} else if (position == 6) {
					// ����ϵͳ�ʼ����͹��ܣ�
					// ֱ�ӵ��õ��������,��֪������������İ��������������ڣ�
					Intent intent = new Intent();
					intent.setComponent(new ComponentName("com.android.email",
							"com.android.email.activity.Welcome"));
					intent.setAction(Intent.ACTION_VIEW);
					startActivity(intent);
				} else if (position == 8) {
					// ����ϵͳ����ϵ���б�
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(Contacts.People.CONTENT_URI);
					startActivity(intent);
				} else if (position == 9) {
					// ����ϵͳ��google�����
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(
							"com.android.browser",
							"com.android.browser.BrowserActivity"));
					intent.setAction(Intent.ACTION_VIEW);
					startActivity(intent);
				} else if (position == 13) {
					// ����ϵͳ�������򵼣�
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(
							"com.android.settings",
							"com.android.settings.Settings"));
					intent.setAction(Intent.ACTION_VIEW);
					startActivity(intent);
				}

				else {
					// ���÷������ʵ�ִ���Activity������Activity����ת
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
		 * ���÷�����Ƶ���MenuBuilder��setOptionalIconsVisible��������
		 * mOptionalIconsVisibleΪtrue�� ���˵�����ͼ��ʱ�ſɼ�
		 */
		setIconEnable(menu, true);

		menu.add(Menu.NONE, 0, 0,"	��¼").setIcon(android.R.drawable.ic_menu_agenda);
		menu.add(Menu.NONE, 1, 1,"	ע��").setIcon(android.R.drawable.ic_menu_add);
		//menu.add(Menu.NONE, 2, 2,"	ͬ��").setIcon(android.R.drawable.ic_menu_upload);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
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
			// ��¼�ɹ��󣬷�����Ϣ�������棻
			Intent intent = new Intent(Activity_menu.this, Login.class);
			startActivityForResult(intent, 100);

		} else if (id == 1) {
			// ע��󣬷�����Ϣ�������棻
			Intent intent = new Intent(Activity_menu.this, Registor.class);
			startActivityForResult(intent, 200);

		} else if (id == 2) {
			// ͬ��
			Toast.makeText(Activity_menu.this, "ͬ��������������", Toast.LENGTH_SHORT)
					.show();
		}
		return super.onOptionsItemSelected(item);
	}

	// ��д onActivityResult������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == 10) {
			Username = data.getExtras().getString("username_login");
			Toast.makeText(Activity_menu.this, "�û���" + Username + "\n��¼�ɹ���",
					Toast.LENGTH_LONG).show();
		}
		if (resultCode == 20) {
			// ��¼�󷵻ظ�
			Username = data.getExtras().getString("username_register");
			Toast.makeText(Activity_menu.this, "�û���" + Username + "\n��¼�ɹ���",
					Toast.LENGTH_LONG).show();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// ʵ���ٰ�һ���Ƴ�ϵͳ��
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "�ٰ�һ���˳�", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
