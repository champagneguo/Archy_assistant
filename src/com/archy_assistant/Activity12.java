package com.archy_assistant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.archy_assistant_software.AppInfo;
import com.archy_assistant_software.ApplicationInfoAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class Activity12 extends Activity {
	private String Flag1 = "tencent";
	private String Flag2 = "baidu";
	private String Flag3 = "sina";
	private String Flag4 = "game";
	private String Flag5 = "alipay";
	private String Flag6 = "MobileTicket";
	private String Flag7 = "taobao";
	private String Flag8 = "alibaba";
	private ListView listview = null;
	private List<AppInfo> myApps;
	private ProgressDialog pd;
	ApplicationInfoAdapter applicationInfoAdapter;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x123) {
				listview.setAdapter(applicationInfoAdapter);
				pd.dismiss();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_12);

		listview = (ListView) findViewById(R.id.activity12_lv);
		myApps = new ArrayList<AppInfo>();
		final Message msg = new Message();
		msg.what = 0x123;
		pd = ProgressDialog.show(this, "���Ե� ...", "�����ռ������Ϣ", false, true);
		// ��ֹ���ռ������Ϣ���³��������
		new Thread() {
			public void run() {
				queryAppInfo();
				handler.sendMessage(msg);

			};
		}.start();

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
						| pkgName.contains(Flag5) | pkgName.contains(Flag6)
						| pkgName.contains(Flag7) | pkgName.contains(Flag8)) {
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
