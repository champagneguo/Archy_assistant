package com.archy_assistant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.archy_assistant.R.drawable;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Activity07 extends Activity {
	private GridView gv;
	private String Flag1 = "tencent.mm";
	private String Flag2 = "im.yixin";
	private String Flag3 = "android.babylon";
	public static String activityName_weixin = null;
	public static String activityName_yixin = null;
	public static String activityName_laiwang = null;
	public static String pkgName_weixin = null;
	public static String pkgName_yixin = null;
	public static String pkgName_laiwang = null;
	public ArrayList<Map<String, Object>> list;
	public String[] str = { "�� ��", "Zigbeeͨ��", "�� ��", "΢ ��", "�� ��", "����" };
	public int[] images = { R.drawable.activity7_sms,
			drawable.activity7_zigbee, R.drawable.activity7_blue,
			R.drawable.activity7_webchat, R.drawable.activity7_yixin,
			R.drawable.activity7_laiwang };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_07);
		gv = (GridView) findViewById(R.id.activity7_gridview);
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 6; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("images", images[i]);
			map.put("desc", str[i]);
			list.add(map);
		}
		SimpleAdapter simpleadapter = new SimpleAdapter(Activity07.this, list,
				R.layout.activity7_gv, new String[] { "images", "desc" },
				new int[] { R.id.activity7_gv_image,
						R.id.activity7_gv_describle });
		gv.setAdapter(simpleadapter);
		// ���ò�ѯ������Ӧ�ó����ܣ�
		queryAppInfo();
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					// ����ϵͳ�Ķ���Ϣ����
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setType("vnd.android-dir/mms-sms");
					// intent.setData(Uri.parse("content://mms-sms/conversations/"));//��Ϊ����
					startActivity(intent);
				} else if (position == 1) {
					// Zigbee����ͨ��
					Toast.makeText(Activity07.this, "Zigbee����ͨ����������...",
							Toast.LENGTH_SHORT).show();
				} else if (position == 2) {
					// �����������ܣ�
					Toast.makeText(Activity07.this, "����ͨ����������...",
							Toast.LENGTH_SHORT).show();
				} else if (position == 3) {
					// ����΢�Ź��ܣ�
					if (activityName_weixin == null) {
						Toast.makeText(Activity07.this, "�����ֻ���δ��װ΢��",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.e("΢�� ====", pkgName_weixin + "++++++"
								+ activityName_weixin);
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(pkgName_weixin,
								activityName_weixin));
						startActivity(intent);
					}
				} else if (position == 4) {
					// �������Ź��ܣ�
					if (activityName_weixin == null) {
						Toast.makeText(Activity07.this, "�����ֻ���δ��װ����",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.e("���� ====", pkgName_yixin + "++++++"
								+ activityName_yixin);
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(pkgName_yixin,
								activityName_yixin));
						startActivity(intent);
					}
				} else if (position == 5) {
					// ������������
					if (activityName_laiwang == null) {
						Toast.makeText(Activity07.this, "�����ֻ���δ��װ����",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.e("���� ====", pkgName_laiwang + "++++++"
								+ activityName_laiwang);
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(pkgName_laiwang,
								activityName_laiwang));
					}
				}
			}
		});

	}

	public void queryAppInfo() {
		// ���PackageManager�������ڹ��������
		PackageManager mPackageManager = this.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// ͨ����ѯ���������ResolveInfo����
		List<ResolveInfo> resolveInfos = mPackageManager.queryIntentActivities(
				mainIntent, 0);
		// ����ϵͳ���򣬸���name����
		// ���������Ҫ������ֻ����ʾϵͳӦ�ã��������г�������Ӧ�ó���
		Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(
				mPackageManager));

		for (ResolveInfo reInfo : resolveInfos) {
			// ��ø�Ӧ�ó��������Activity��Name��
			String activityName = reInfo.activityInfo.name;
			// ���Ӧ�ó���İ�����
			String pkgName = reInfo.activityInfo.packageName;
			if (activityName.contains(Flag1)) {
				activityName_weixin = activityName;
				pkgName_weixin = pkgName;
			}
			if (activityName.contains(Flag2)) {
				activityName_yixin = activityName;
				pkgName_yixin = pkgName;
			}
			if (activityName.contains(Flag3)) {
				activityName_laiwang = activityName;
				pkgName_laiwang = pkgName;
			}
		}
	}
}
