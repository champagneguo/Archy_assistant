package com.archy_assistant_book;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.archy_assistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class InsertBook extends Activity {
	private ListView bookfolder;
	private File root;
	private ArrayList<Map<String, Object>> data;
	private String filename;
	private ProgressDialog pd;
	private Message msg;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0x123) {
				pd.dismiss();
				if (data != null) {
					SimpleAdapter simpleadapter = new SimpleAdapter(
							InsertBook.this, data,
							R.layout.activity10_insertbook_lv, new String[] {
									"coloum", "size", "path" }, new int[] {
									R.id.activity10_insertbook_lv_folder,
									R.id.activity10_insertbook_lv_size,
									R.id.activity10_insertbook_lv_path });
					bookfolder.setAdapter(simpleadapter);
					bookfolder
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(InsertBook.this,
											Listbook.class);
									Log.e("startactivity to ListBook", data
											.get(position).get("path")
											.toString());
									intent.putExtra("path", data.get(position)
											.get("path").toString());
									startActivity(intent);
								}
							});
				}
			} else {
				Toast.makeText(InsertBook.this, "内存卡中没有TXT文件",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity10_insertbook);
		bookfolder = (ListView) findViewById(R.id.activity10_insertbook_lv);
		root = Environment.getExternalStorageDirectory();
		data = new ArrayList<Map<String, Object>>();
		System.out.println("Insert book root ===> " + root);
		if (root != null) {
			pd = ProgressDialog.show(InsertBook.this, "请稍等...", "正在搜索图书");
			// msg对象要进行初始化；
			msg = new Message();
			msg.what = 0x123;
			System.out.println("start Thread======");
			new MyThread().start();
		} else {
			Toast.makeText(InsertBook.this, "手机尚未加载SD卡", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public class MyThread extends Thread {
		// 如何让子线程执行完程序后，自己主动停止。
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			System.out.println("start thread run()++++++++");
			GetAllFiles(root);
			handler.sendMessage(msg);
		}
	}

	@SuppressLint("DefaultLocale")
	public void GetAllFiles(File root) {
		System.out.println("GetAllFiles +++++++ " + root);
		File[] files = root.listFiles();
		int count = 0;
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					GetAllFiles(f);
				} else {
					filename = f.getName();
					System.out.println("filename is =====" + filename);
					if (filename.indexOf(".") > -1) {
						if (filename
								.substring(filename.lastIndexOf(".") + 1,
										filename.length()).toUpperCase()
								.equals("TXT")) {
							count++;
							System.out.println("count is ======" + count);
						}
					}
				}
			}
		}
		if (count != 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("coloum", root.getName());
			map.put("size", count);
			map.put("path", root.getPath());
			data.add(map);
			System.out.println("map data is count ======" + count);
		}
	}

}
