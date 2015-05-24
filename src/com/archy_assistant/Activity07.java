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
	public String[] str = { "短 信", "Zigbee通信", "蓝 牙", "微 信", "易 信", "来往" };
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
		// 调用查询第三方应用程序功能；
		queryAppInfo();
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					// 调用系统的短信息功能
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setType("vnd.android-dir/mms-sms");
					// intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码
					startActivity(intent);
				} else if (position == 1) {
					// Zigbee网络通信
					Toast.makeText(Activity07.this, "Zigbee网络通信正在完善...",
							Toast.LENGTH_SHORT).show();
				} else if (position == 2) {
					// 调用蓝牙功能；
					Toast.makeText(Activity07.this, "蓝牙通信正在完善...",
							Toast.LENGTH_SHORT).show();
				} else if (position == 3) {
					// 调用微信功能；
					if (activityName_weixin == null) {
						Toast.makeText(Activity07.this, "您的手机尚未安装微信",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.e("微信 ====", pkgName_weixin + "++++++"
								+ activityName_weixin);
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(pkgName_weixin,
								activityName_weixin));
						startActivity(intent);
					}
				} else if (position == 4) {
					// 调用易信功能；
					if (activityName_weixin == null) {
						Toast.makeText(Activity07.this, "您的手机尚未安装易信",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.e("易信 ====", pkgName_yixin + "++++++"
								+ activityName_yixin);
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(pkgName_yixin,
								activityName_yixin));
						startActivity(intent);
					}
				} else if (position == 5) {
					// 调用来往功能
					if (activityName_laiwang == null) {
						Toast.makeText(Activity07.this, "您的手机尚未安装来往",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.e("来往 ====", pkgName_laiwang + "++++++"
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
		// 获得PackageManager对象；用于管理软件；
		PackageManager mPackageManager = this.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 通过查询，获得所有ResolveInfo对象；
		List<ResolveInfo> resolveInfos = mPackageManager.queryIntentActivities(
				mainIntent, 0);
		// 调用系统排序，根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序；
		Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(
				mPackageManager));

		for (ResolveInfo reInfo : resolveInfos) {
			// 获得该应用程序的启动Activity的Name；
			String activityName = reInfo.activityInfo.name;
			// 获得应用程序的包名；
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
