package com.archy_assistant_search;

import com.archy_assistant.Activity11;
import com.archy_assistant.R;
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

public class Activity11_add extends Activity {
	
	private MyDataBaseHelper DB;
	private EditText et1;
	private EditText et2;
	private Button bt;
	private String keywords;
	private String description;
			
	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_11_add);
		et1 = (EditText)findViewById(R.id.activity11_add_et1);
		et2 = (EditText)findViewById(R.id.activity11_add_et2);
		bt = (Button)findViewById(R.id.activity11_add_bt);
		
		DB = new MyDataBaseHelper(Activity11_add.this, "/data/data/com.archy_assistant/files/archy.db", null, 1);
		
		bt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {				
				keywords = et1.getText().toString();
				description = et2.getText().toString();
				AddItem(new Item(keywords,description));
				Toast.makeText(Activity11_add.this, "添加词条成功！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Activity11_add.this,Activity11.class);
				startActivity(intent);
				
			}
		});		
	}
		
	private void AddItem(Item item) {
		SQLiteDatabase db = DB.getWritableDatabase();
		db.execSQL("insert into dictionary(keywords,description) values(?,?)",
				new String[] { item.getKeywords(), item.getDescription() });
	}

}
