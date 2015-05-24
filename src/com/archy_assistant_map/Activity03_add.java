package com.archy_assistant_map;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.archy_assistant.R;
import com.archy_assistant_search.MyDataBaseHelper;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class Activity03_add extends Activity {
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
	private RadioGroup radiogroup;
	private Button Confirm;
	private MyDataBaseHelper DB;
	private boolean flag = false;
	LocationManager locationManager;
	MediaPlayer player;
	private String TAG = "Activity03_add";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_03_add);

		Name = (EditText) findViewById(R.id.activity03_add_name);
		Longtitude = (EditText) findViewById(R.id.activity03_add_longtitude);
		Latitude = (EditText) findViewById(R.id.activity03_add_latitude);
		Note = (EditText) findViewById(R.id.activity03_add_note);
		radiogroup = (RadioGroup) findViewById(R.id.activity03_add_radioGroup1);
		Confirm = (Button) findViewById(R.id.activity03_add_save);

		// ����LocationManager����
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// ����λ�ɹ�ʱ������������
		player = MediaPlayer.create(Activity03_add.this, R.raw.thunder);
		System.out.println("player is ======" + player);
		// �ж�GPS�Ƿ�����
		openGPSSeting();

		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {
				// TODO Auto-generated method stub
				if (checkId == R.id.activity03_add_radio0) {

					Toast.makeText(Activity03_add.this, "���ڶ�λ...",
							Toast.LENGTH_SHORT).show();

					Criteria criteria = new Criteria();
					criteria.setAccuracy(Criteria.ACCURACY_FINE);
					criteria.setAltitudeRequired(false);
					criteria.setBearingRequired(false);
					criteria.setCostAllowed(true);
					criteria.setPowerRequirement(Criteria.POWER_LOW);
					String provider = locationManager.getBestProvider(criteria,
							true);
					// ��GPS��ȡ����Ķ�λ��Ϣ��
					Location location = locationManager
							.getLastKnownLocation(provider);
					// ���ڻ�ȡ����Locationֵ�����жϣ�
					System.out.println("location == " + location);
					if (location == null) {
						// Ϊʲôû�л�ȡ����γ����Ϣ��������
						// ���ö��ַ�ʽ��ȡGPS��Ϣ��
						Toast.makeText(Activity03_add.this, "��ȡ��γ��ʧ�ܣ�",
								Toast.LENGTH_SHORT).show();
					} else {
						longtitude_string = ""
								+ ((int) (location.getLongitude() * 1E6) * 1.0)
								/ 1E6;
						latitude_string = ""
								+ ((int) (location.getLatitude() * 1E6) * 1.0)
								/ 1E6;
						Longtitude.setText(longtitude_string);
						Latitude.setText(latitude_string);
						player.start();

						// player.prepare();
						// ����prepareAsync called in state 8
						// ��Ϊcreate�����Ѿ�����prepare��,������ֻ��Ҫstart�Ϳ�����

					}
				} else if (checkId == R.id.activity03_add_radio1) {

					Latitude.setText("");
					Longtitude.setText("");
				}

			}
		});

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
					Toast.makeText(Activity03_add.this, "�뽫������������...",
							Toast.LENGTH_SHORT).show();

				} else {
					GeoPoint point = new GeoPoint((int) (Double
							.parseDouble(latitude_string) * 1e6), (int) (Double
							.parseDouble(longtitude_string) * 1e6));
					// ��GPS����ת���ɰٶ����ꣻ
					GeoPoint point1 = CoordinateConvert.fromWgs84ToBaidu(point);
					longtitude = (point1.getLongitudeE6() * 1.0) / 1e6;
					longtitude_string = longtitude.toString();
					latitude = (point1.getLatitudeE6() * 1.0) / 1e6;
					latitude_string = latitude.toString();
					Log.e(TAG, "���ȷ����Ϣ");
					Builder builder = new Builder(Activity03_add.this);
					builder.setTitle("��ʾ��Ϣ");
					builder.setMessage("�Ƿ񱣴�?");
					builder.setPositiveButton("ȷ��", new OnClickListener() {

						@SuppressLint("SdCardPath")
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Log.e(TAG, "ȷ����onclick����");
							DB = new MyDataBaseHelper(
									Activity03_add.this,
									"/data/data/com.archy_assistant/files/location.db",
									null, 1);
							Log.e(TAG, "�½����ݿ�" + DB.toString());
							if (Nofind(latitude, longtitude)) {
								insert();
								finish();
								Toast.makeText(Activity03_add.this, "�������ݳɹ�",
										Toast.LENGTH_SHORT).show();

							} else {
								Toast.makeText(Activity03_add.this, "��������û�ɹ�",
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

	public void openGPSSeting() {
		if (!locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(Activity03_add.this, "�뿪��GPSģ��", Toast.LENGTH_SHORT)
					.show();
			Builder builder = new Builder(Activity03_add.this);
			builder.setTitle("��ʾ���ҵ�λ�á�");
			builder.setMessage("�Ƿ���GPS��λ����");
			builder.setNegativeButton("ȡ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder.setPositiveButton("����", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(intent, 0);
				}
			});
			builder.create().show();

		} else {
			System.out.println("GPSģ���Ѿ�������");
		}
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
		if (player != null) {
			player.release();
		}
		if (DB != null) {
			DB.close();
		}
	}

}
