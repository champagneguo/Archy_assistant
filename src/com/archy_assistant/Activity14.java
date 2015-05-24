package com.archy_assistant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.archy_assistant_software.AppInfo;
import com.archy_assistant_software.ApplicationInfoAdapter;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class Activity14 extends Activity {
	private String Flag1 = "calculator";
	private String Flag2 = "calendar";
	private String Flag3 = "deskclock";
	private String Flag4 = "evernote";
	private String Flag5 = "wps";
	private ListView listview = null;
	private List<AppInfo> myApps;
	ApplicationInfoAdapter applicationInfoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_14);
		listview = (ListView) findViewById(R.id.activity14_lv);
		myApps = new ArrayList<AppInfo>();
		// 查询符合条件的
		queryAppInfo();
		listview.setAdapter(applicationInfoAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = myApps.get(position).getIntent();
				startActivity(intent);
			}
		});
	}

	void queryAppInfo() {
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

		if (myApps != null) {
			myApps.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				// 获得该应用程序的启动Activity的Name；
				String activityName = reInfo.activityInfo.name;
				// 获得应用程序的包名；
				String pkgName = reInfo.activityInfo.packageName;
				// 获得应用程序的Label
				String appLabel = (String) reInfo.loadLabel(mPackageManager);
				// 获得应用程序的程序图标；
				Drawable icon = reInfo.loadIcon(mPackageManager);
				if (pkgName.contains(Flag1) | pkgName.contains(Flag2)
						| pkgName.contains(Flag3) | pkgName.contains(Flag4)
						| pkgName.contains(Flag5)) {
					// 为应用程序启动Activity做准备；
					Intent lanchIntent = new Intent();
					lanchIntent.setComponent(new ComponentName(pkgName,
							activityName));
					// 创建一个AppInfo对象并赋值；
					AppInfo appInfo = new AppInfo();
					appInfo.setAppLabel(appLabel);
					appInfo.setPkgName(pkgName);
					appInfo.setAppIcon(icon);
					appInfo.setIntent(lanchIntent);
					myApps.add(appInfo);
				}
				applicationInfoAdapter = new ApplicationInfoAdapter(this,
						myApps);
			}
		}
	}
}
