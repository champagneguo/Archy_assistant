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
		// ��ѯ����������
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

		if (myApps != null) {
			myApps.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				// ��ø�Ӧ�ó��������Activity��Name��
				String activityName = reInfo.activityInfo.name;
				// ���Ӧ�ó���İ�����
				String pkgName = reInfo.activityInfo.packageName;
				// ���Ӧ�ó����Label
				String appLabel = (String) reInfo.loadLabel(mPackageManager);
				// ���Ӧ�ó���ĳ���ͼ�ꣻ
				Drawable icon = reInfo.loadIcon(mPackageManager);
				if (pkgName.contains(Flag1) | pkgName.contains(Flag2)
						| pkgName.contains(Flag3) | pkgName.contains(Flag4)
						| pkgName.contains(Flag5)) {
					// ΪӦ�ó�������Activity��׼����
					Intent lanchIntent = new Intent();
					lanchIntent.setComponent(new ComponentName(pkgName,
							activityName));
					// ����һ��AppInfo���󲢸�ֵ��
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
