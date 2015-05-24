package com.archy_assistant_book;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.archy_assistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Listbook extends Activity {

	private ListView list;
	private ArrayList<Map<String, Object>> data;
	private String path;
	private String name;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity10_listbook);
		list = (ListView) findViewById(R.id.activity10_listbook_lv);
		path = getIntent().getStringExtra("path");
		data = new ArrayList<Map<String, Object>>();
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f : files) {
			if (!f.isDirectory()) {
				name = f.getName();
				if (name.substring(name.lastIndexOf(".") + 1, name.length())
						.toUpperCase().equals("TXT")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", f.getName());
					map.put("time", dateformat.format(f.lastModified()));
					map.put("size", f.length());
					map.put("path", f.getAbsoluteFile());
					data.add(map);
				}
			}
		}
		SimpleAdapter simpleadapter = new SimpleAdapter(Listbook.this, data,
				R.layout.activity10_listbook_lv, new String[] { "title",
						"time", "size" }, new int[] {
						R.id.activity10_listbook_lv_filename,
						R.id.activity10_listbook_lv_time,
						R.id.activity10_listbook_lv_size });
		list.setAdapter(simpleadapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Listbook.this, ReadBook_1.class);
				intent.putExtra("path", data.get(position).get("path")
						.toString());
				intent.putExtra("title", data.get(position).get("title")
						.toString());
				startActivity(intent);
			}
		});
	}
}
