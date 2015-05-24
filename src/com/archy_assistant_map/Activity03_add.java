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

		// 创建LocationManager对象
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 当定位成功时，播放声音；
		player = MediaPlayer.create(Activity03_add.this, R.raw.thunder);
		System.out.println("player is ======" + player);
		// 判断GPS是否开启；
		openGPSSeting();

		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {
				// TODO Auto-generated method stub
				if (checkId == R.id.activity03_add_radio0) {

					Toast.makeText(Activity03_add.this, "正在定位...",
							Toast.LENGTH_SHORT).show();

					Criteria criteria = new Criteria();
					criteria.setAccuracy(Criteria.ACCURACY_FINE);
					criteria.setAltitudeRequired(false);
					criteria.setBearingRequired(false);
					criteria.setCostAllowed(true);
					criteria.setPowerRequirement(Criteria.POWER_LOW);
					String provider = locationManager.getBestProvider(criteria,
							true);
					// 从GPS获取最近的定位信息；
					Location location = locationManager
							.getLastKnownLocation(provider);
					// 对于获取到的Location值进行判断；
					System.out.println("location == " + location);
					if (location == null) {
						// 为什么没有获取到经纬度信息？？？？
						// 采用多种方式获取GPS信息。
						Toast.makeText(Activity03_add.this, "获取经纬度失败！",
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
						// 报错prepareAsync called in state 8
						// 因为create方法已经帮你prepare了,所以你只需要start就可以了

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
					Toast.makeText(Activity03_add.this, "请将以上内容完善...",
							Toast.LENGTH_SHORT).show();

				} else {
					GeoPoint point = new GeoPoint((int) (Double
							.parseDouble(latitude_string) * 1e6), (int) (Double
							.parseDouble(longtitude_string) * 1e6));
					// 将GPS坐标转化成百度坐标；
					GeoPoint point1 = CoordinateConvert.fromWgs84ToBaidu(point);
					longtitude = (point1.getLongitudeE6() * 1.0) / 1e6;
					longtitude_string = longtitude.toString();
					latitude = (point1.getLatitudeE6() * 1.0) / 1e6;
					latitude_string = latitude.toString();
					Log.e(TAG, "点击确定信息");
					Builder builder = new Builder(Activity03_add.this);
					builder.setTitle("提示信息");
					builder.setMessage("是否保存?");
					builder.setPositiveButton("确定", new OnClickListener() {

						@SuppressLint("SdCardPath")
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Log.e(TAG, "确定的onclick方法");
							DB = new MyDataBaseHelper(
									Activity03_add.this,
									"/data/data/com.archy_assistant/files/location.db",
									null, 1);
							Log.e(TAG, "新建数据库" + DB.toString());
							if (Nofind(latitude, longtitude)) {
								insert();
								finish();
								Toast.makeText(Activity03_add.this, "插入数据成功",
										Toast.LENGTH_SHORT).show();

							} else {
								Toast.makeText(Activity03_add.this, "插入数据没成功",
										Toast.LENGTH_SHORT).show();
							}

						}
					});
					builder.setNegativeButton("取消", new OnClickListener() {

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
			Toast.makeText(Activity03_add.this, "请开启GPS模块", Toast.LENGTH_SHORT)
					.show();
			Builder builder = new Builder(Activity03_add.this);
			builder.setTitle("显示“我的位置”");
			builder.setMessage("是否开启GPS定位设置");
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder.setPositiveButton("设置", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(intent, 0);
				}
			});
			builder.create().show();

		} else {
			System.out.println("GPS模块已经开启！");
		}
	}

	public boolean Nofind(double latitude, double longtitude) {
		int i = 0;
		int number = 0;
		SQLiteDatabase db = DB.getReadableDatabase();
		Log.e(TAG, "SqliteDatabase新建" + db.toString());
		Cursor cursor = db
				.rawQuery("select * from location order by _id", null);
		Log.e(TAG, "cursor" + cursor.toString());
		number = cursor.getCount();
		Log.e(TAG, "游标查询到条目数" + number);
		flag = cursor.moveToFirst();
		Log.e(TAG, "游标移到第一个数" + flag);
		do {
			if (cursor.getDouble(2) == latitude
					&& cursor.getDouble(3) == longtitude) {
				Log.e(TAG, "找到相同的经纬度");
				return false;
			}
			i++;
			Log.e(TAG, "查询次数" + i);
		} while (cursor.moveToNext());
		return true;
	}

	public void insert() {

		SQLiteDatabase db = DB.getWritableDatabase();
		System.out.println("db is " + db);
		db.execSQL(
				"insert into location(Keywords,Latitude,Longitude,Details) values (?,?,?,?)",
				new Object[] { name, latitude, longtitude, note });
		// 注意数据库中表数据准确性；
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
