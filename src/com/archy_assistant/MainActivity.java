package com.archy_assistant;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import com.archy_assistant_software.BundleObject;

public class MainActivity extends Activity {
	private String Flag1 = "music";
	private String Flag2 = "storm";
	private String Flag3 = "note";
	private String Flag4 = "com.evernote.ui.HomeActivty";
	private BundleObject bundleObject = new BundleObject();
	private BundleObject bundleObject1 = new BundleObject();
	private BundleObject bundleObject2 = new BundleObject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ��ȥ��������Ӧ�ó�������֣�
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ȥ״̬������(��ص�ͼ���һ�����β���)
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		queryAppInfo();
		// ���ǲ�����Ŀ¼�ķ������п��ܵ��³����жϡ�ͨ���ԾͲ�㡣
		/**
		 * String dbDirPath = "/data/data/com.test/databases"; File dbDir = new
		 * File(dbDirPath); if(!dbDir.exists()) // ��������ڸ�Ŀ¼�򴴽� dbDir.mkdir(); //
		 * �򿪾�̬���ݿ��ļ��������� InputStream is =
		 * context.getResources().openRawResource(R.raw.data); // ��Ŀ�����ݿ��ļ��������
		 * FileOutputStream os = new FileOutputStream(dbDirPath+"/data.db");
		 * byte[] buffer = new byte[1024]; int count = 0; // ����̬���ݿ��ļ�������Ŀ�ĵ�
		 * while ((count = is.read(buffer)) > 0) { os.write(buffer, 0, count); }
		 * is.close(); os.close();
		 * 
		 */
		// �򿪾�̬���ݿ��ļ���������
		InputStream is1 = this.getResources().openRawResource(R.raw.archy);
		InputStream is2 = this.getResources().openRawResource(R.raw.location);
		// ͨ��Context������Ŀ�����ݿ��ļ�����������������Ա��⽫·��д����
		try {
			//��׷�ӵķ�ʽ���ļ���Ӧ�ó����������ļ���׷�����ݡ�
			FileOutputStream os1 = this.openFileOutput("archy1.db", MODE_APPEND);
			byte[] buffer = new byte[1024];
			int count = 0;
			// ����̬���ݿ��ļ�������Ŀ�ĵ�
			while ((count = is1.read(buffer)) > 0) {
				os1.write(buffer, 0, count);
			}
			is1.close();
			os1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//��׷�ӵķ�ʽ���ļ���Ӧ�ó����������ļ���׷�����ݡ�
			FileOutputStream os2 = this.openFileOutput("location.db", MODE_APPEND);
			byte[] buffer = new byte[1024];
			int count = 0;
			// ����̬���ݿ��ļ�������Ŀ�ĵ�
			while ((count = is2.read(buffer)) > 0) {
				os2.write(buffer, 0, count);
			}
			is2.close();
			os2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ��һ��Activity��һ��ʱ���Զ���ת����һ��Activity��
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(MainActivity.this,
						Activity_menu.class);			
				mainIntent.putExtra("Package_name",bundleObject);
				mainIntent.putExtra("Package_name1", bundleObject1);
				mainIntent.putExtra("Package_name2", bundleObject2);
				MainActivity.this.startActivity(mainIntent);
				MainActivity.this.finish();
				
				// overridePendingTransition(android.R.anim.fade_in,android.R.anim.slide_in_left);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		}, 2000);

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
			
			for (ResolveInfo reInfo : resolveInfos) {
				// ��ø�Ӧ�ó��������Activity��Name��
				String activityName = reInfo.activityInfo.name;
				// ���Ӧ�ó���İ�����
				String pkgName = reInfo.activityInfo.packageName;
				if (activityName.contains(Flag1) | activityName.contains(Flag2)) {
					// ����һ��bundleObject���󲢸�ֵ��
					bundleObject.setPkgName(pkgName);
					bundleObject.setActivityName(activityName);					
		    }
				if (activityName.contains(Flag3) && !activityName.contains("evernote")) {
					// ����һ��bundleObject���󲢸�ֵ��
					bundleObject1.setPkgName(pkgName);
					bundleObject1.setActivityName(activityName);					
		    }
				if(activityName.equals(Flag4)){
					// ����һ��bundleObject���󲢸�ֵ��
					bundleObject2.setPkgName(pkgName);
					bundleObject2.setActivityName(activityName);
				}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
