package com.archy_assistant_map;

import com.archy_assistant.R;
import com.archy_assistant_search.MyDataBaseHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity03_add1 extends Activity {
	private String name;
	private Double longtitude;
	private Double latitude;
	private String latitude_string;
	private String longtitude_string;
	private String note;
	private EditText Name;
	private EditText Longtitude;
	private EditText Latitude;
	private EditText Note;
	private Button Confirm;
	private MyDataBaseHelper DB;
	private boolean flag = false;
	LocationManager locationManager;
	private String TAG = "Activity03_add1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_03_add1);

		Name = (EditText) findViewById(R.id.activity03_add1_name);
		Longtitude = (EditText) findViewById(R.id.activity03_add1_longtitude);
		Latitude = (EditText) findViewById(R.id.activity03_add1_latitude);
		Note = (EditText) findViewById(R.id.activity03_add1_note);
		Confirm = (Button) findViewById(R.id.activity03_add1_save);
		longtitude = getIntent().getDoubleExtra("Longitude", 0);
		latitude = getIntent().getDoubleExtra("Latitude", 0);
		Latitude.setText("" + latitude);
		Longtitude.setText("" + longtitude);

		Confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				name = Name.getText().toString();
				note = Note.getText().toString();
				longtitude_string = Longtitude.getText().toString();
				latitude_string = Latitude.getText().toString();
				if (name == null || name.equals("")
						|| longtitude_string == null
						|| longtitude_string.equals("")
						|| latitude_string == null
						|| latitude_string.equals("") || note == null
						|| note.equals("")) {
					Toast.makeText(Activity03_add1.this, "�뽫������������...",
							Toast.LENGTH_SHORT).show();

				} else {

					Builder builder = new Builder(Activity03_add1.this);
					builder.setTitle("��ʾ��Ϣ");
					builder.setMessage("�Ƿ񱣴�?");
					builder.setPositiveButton("ȷ��", new OnClickListener() {

						@SuppressLint("SdCardPath")
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Log.e(TAG, "ȷ����onclick����");
							DB = new MyDataBaseHelper(
									Activity03_add1.this,
									"/data/data/com.archy_assistant/files/location.db",
									null, 1);
							Log.e(TAG, "�½����ݿ�" + DB.toString());
							if (Nofind(latitude, longtitude)) {
								insert();
								finish();
								Toast.makeText(Activity03_add1.this, "�������ݳɹ�",
										Toast.LENGTH_SHORT).show();

							} else {
								Toast.makeText(Activity03_add1.this, "��������û�ɹ�",
										Toast.LENGTH_SHORT).show();
							}

						}
					});
					builder.setNegativeButton("ȡ��", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
					builder.create().show();

				}

			}
		});
	}

	public boolean Nofind(double latitude, double longtitude) {
		int i = 0;
		int number = 0;
		SQLiteDatabase db = DB.getReadableDatabase();
		Log.e(TAG, "SqliteDatabase�½�" + db.toString());
		Cursor cursor = db
				.rawQuery("select * from location order by _id", null);
		Log.e(TAG, "cursor" + cursor.toString());
		number = cursor.getCount();
		Log.e(TAG, "�α��ѯ����Ŀ��" + number);
		flag = cursor.moveToFirst();
		Log.e(TAG, "�α��Ƶ���һ����" + flag);
		do {
			if (cursor.getDouble(2) == latitude
					&& cursor.getDouble(3) == longtitude) {
				Log.e(TAG, "�ҵ���ͬ�ľ�γ��");
				return false;
			}
			i++;
			Log.e(TAG, "��ѯ����" + i);
		} while (cursor.moveToNext());
		return true;
	}

	public void insert() {

		SQLiteDatabase db = DB.getWritableDatabase();
		System.out.println("db is " + db);
		db.execSQL(
				"insert into location(Keywords,Latitude,Longitude,Details) values (?,?,?,?)",
				new Object[] { name, latitude, longtitude, note });
		// ע�����ݿ��б�����׼ȷ�ԣ�
		Log.e(TAG, "db.execSQL");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (DB != null) {
			DB.close();
		}
	}

}
