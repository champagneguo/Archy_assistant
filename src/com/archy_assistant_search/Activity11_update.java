package com.archy_assistant_search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.archy_assistant.Activity11;
import com.archy_assistant.R;

public class Activity11_update extends Activity {
	private MyDataBaseHelper DB;
	private EditText et1;
	private EditText et2;
	private Button bt;
	private String id;
	private String keywords;
	private String description;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_11_update);
		et1 = (EditText) findViewById(R.id.activity11_update_et1);
		et2 = (EditText) findViewById(R.id.activity11_update_et2);
		bt = (Button) findViewById(R.id.activity11_update_bt);

		DB = new MyDataBaseHelper(Activity11_update.this, "/data/data/com.archy_assistant/files/archy.db", null, 1);

		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		keywords = bundle.getString("keys");
		description = bundle.getString("content");

		et1.setText(keywords);
		et2.setText(description);

		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				keywords = et1.getText().toString();
				description = et2.getText().toString();
				try {
					UpdateItem(Integer.parseInt(id),
							new Item(keywords, description));
					Toast.makeText(Activity11_update.this, "修改词条成功！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Activity11_update.this,
							Activity11.class);
					startActivity(intent);
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	private void UpdateItem(int id, Item item) {
		SQLiteDatabase db = DB.getWritableDatabase();
		db.execSQL("update dictionary set keywords = '" + item.getKeywords()
				+ "', description = '" + item.getDescription()
				+ "' where _id =" + id);
	}
}
