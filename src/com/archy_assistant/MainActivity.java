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
		
		// 隐去标题栏（应用程序的名字）
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐去状态栏部分(电池等图标和一切修饰部分)
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		queryAppInfo();
		// 这是采用死目录的方法；有可能导致程序中断。通用性就差点。
		/**
		 * String dbDirPath = "/data/data/com.test/databases"; File dbDir = new
		 * File(dbDirPath); if(!dbDir.exists()) // 如果不存在该目录则创建 dbDir.mkdir(); //
		 * 打开静态数据库文件的输入流 InputStream is =
		 * context.getResources().openRawResource(R.raw.data); // 打开目标数据库文件的输出流
		 * FileOutputStream os = new FileOutputStream(dbDirPath+"/data.db");
		 * byte[] buffer = new byte[1024]; int count = 0; // 将静态数据库文件拷贝到目的地
		 * while ((count = is.read(buffer)) > 0) { os.write(buffer, 0, count); }
		 * is.close(); os.close();
		 * 
		 */
		// 打开静态数据库文件的输入流
		InputStream is1 = this.getResources().openRawResource(R.raw.archy);
		InputStream is2 = this.getResources().openRawResource(R.raw.location);
		// 通过Context类来打开目标数据库文件的输出流，这样可以避免将路径写死。
		try {
			//已追加的方式打开文件，应用程序可以向该文件中追加内容。
			FileOutputStream os1 = this.openFileOutput("archy1.db", MODE_APPEND);
			byte[] buffer = new byte[1024];
			int count = 0;
			// 将静态数据库文件拷贝到目的地
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
			//已追加的方式打开文件，应用程序可以向该文件中追加内容。
			FileOutputStream os2 = this.openFileOutput("location.db", MODE_APPEND);
			byte[] buffer = new byte[1024];
			int count = 0;
			// 将静态数据库文件拷贝到目的地
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

		// 让一个Activity过一段时间自动跳转到下一个Activity；
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
				if (activityName.contains(Flag1) | activityName.contains(Flag2)) {
					// 创建一个bundleObject对象并赋值；
					bundleObject.setPkgName(pkgName);
					bundleObject.setActivityName(activityName);					
		    }
				if (activityName.contains(Flag3) && !activityName.contains("evernote")) {
					// 创建一个bundleObject对象并赋值；
					bundleObject1.setPkgName(pkgName);
					bundleObject1.setActivityName(activityName);					
		    }
				if(activityName.equals(Flag4)){
					// 创建一个bundleObject对象并赋值；
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
