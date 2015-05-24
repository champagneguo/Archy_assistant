package com.archy_assistant;

import java.util.ArrayList;
import java.util.List;
import com.archy_assistant_map.Activity03_add;
import com.archy_assistant_map.Activity03_add1;
import com.archy_assistant_search.MyDataBaseHelper;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class Activity03 extends Activity {

	// MKMapViewListener 用于处理地图事件回调
	MKMapViewListener mMapListener = null;
	// 用于截获屏坐标
	MKMapTouchListener mapTouchListener = null;

	private BMapManager mBMapMan = null;
	private MapView mMapView = null;
	private List<OverlayItem> list = new ArrayList<OverlayItem>();
	public View popView;
	private MyDataBaseHelper DB;
	private Cursor cursor;
	private String Keywords;
	private String Detail;
	private double mLat;
	private double mLon;
	private GeoPoint point1;
	private Toast mToast;
	private MyOverlay itemOverlay = null;
	boolean flag = true;

	private String TAG = "Activity03";
	/**
	 * 定位SDK的核心类
	 */
	private LocationClient mLocClient;
	/**
	 * 用户位置信息
	 */
	private LocationData mLocData;
	/**
	 * 我的位置图层
	 */
	private LocationOverlay myLocationOverlay = null;
	/**
	 * 弹出窗口图层
	 */
	private PopupOverlay mPopupOverlay = null;

	// 是否手动触发请求定位
	private boolean isRequest = false;
	// 是否首次定位
	private boolean isFirstLoc = true;

	/**
	 * 弹出窗口图层View
	 */
	private View viewCache;
	private BDLocation location;

	private MapController mMapController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 使用地图sdk前需先初始化BMapManager，这个必须在setContentView()先初始化
		mBMapMan = new BMapManager(this);

		// 第一个参数是API key,
		// 第二个参数是常用事件监听，用来处理通常的网络错误，授权验证错误等，你也可以不添加这个回调接口
		mBMapMan.init("fHd9n3XagL2bF51OqypVBOgb", new MKGeneralListenerImpl());
		setContentView(R.layout.activity_03);

		//

		// 点击按钮手动请求定位
		((Button) findViewById(R.id.activity03_bt))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						requestLocation();
					}
				});

		mMapView = (MapView) findViewById(R.id.bmapsView); // 获取百度地图控件实例
		mapTouchListener = new MKMapTouchListener() {

			@Override
			public void onMapLongClick(final GeoPoint point) {
				Log.e(TAG, "onMapLongClick:" + point);
				// TODO Auto-generated method stub
				Builder builder = new Builder(Activity03.this);
				builder.setTitle("考古点添加");
				builder.setMessage("是否添加地图当前位置为考古点？");
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(Activity03.this,
										Activity03_add1.class);
								Log.e(TAG,
										"onClick:" + "Latitude:"
												+ point.getLatitudeE6()
												+ "Longitude:"
												+ point.getLongitudeE6());
								intent.putExtra("Latitude",
										(point.getLatitudeE6() * 1.0) / 1e6);
								intent.putExtra("Longitude",
										(point.getLongitudeE6() * 1.0) / 1e6);
								startActivity(intent);
							}
						});
				builder.create().show();
			}

			@Override
			public void onMapDoubleClick(GeoPoint arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapClick(GeoPoint arg0) {
				// TODO Auto-generated method stub

			}
		};
		// 注册地图触碰事件
		mMapView.regMapTouchListner(mapTouchListener);

		mMapController = mMapView.getController(); // 获取地图控制器
		mMapController.enableClick(true); // 设置地图是否响应点击事件
		mMapController.setZoom(14); // 设置地图缩放级别
		mMapView.setBuiltInZoomControls(true); // 显示内置缩放控件

		viewCache = LayoutInflater.from(this).inflate(
				R.layout.activity_03_popup, null);
		mPopupOverlay = new PopupOverlay(mMapView, new PopupClickListener() {

			@Override
			public void onClickedPopup(int arg0) {
				mPopupOverlay.hidePop();
			}

		});

		mLocData = new LocationData();

		// 实例化定位服务，LocationClient类必须在主线程中声明
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(new BDLocationListenerImpl());// 注册定位监听接口

		/**
		 * 设置定位参数
		 */
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPRS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
		option.disableCache(false);// 禁止启用缓存定位
		// option.setPoiNumber(5); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息

		mLocClient.setLocOption(option);
		mLocClient.start(); // 调用此方法开始定位

		// 定位图层初始化
		myLocationOverlay = new LocationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(mLocData);

		myLocationOverlay.setMarker(getResources().getDrawable(
				R.drawable.location_arrows));

		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();

		// 添加长按图层；

		Log.e(TAG, "mMapView:" + mMapView.getHeight());

		// 修改定位数据后刷新图层生效
		mMapView.refresh();

	}

	/**
	 * 定位接口，需要实现两个方法
	 * 
	 */
	public class BDLocationListenerImpl implements BDLocationListener {

		/**
		 * 接收异步返回的定位结果，参数是BDLocation类型参数
		 */
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}

			Activity03.this.location = location;

			mLocData.latitude = location.getLatitude();
			mLocData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			mLocData.accuracy = location.getRadius();
			mLocData.direction = location.getDerect();

			// 将定位数据设置到定位图层里
			myLocationOverlay.setData(mLocData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();

			if (isFirstLoc || isRequest) {
				mMapController.animateTo(new GeoPoint((int) (location
						.getLatitude() * 1e6),
						(int) (location.getLongitude() * 1e6)));

				showPopupOverlay(location);

				isRequest = false;
			}

			isFirstLoc = false;
		}

		/**
		 * 接收异步返回的POI查询结果，参数是BDLocation类型参数
		 */
		@Override
		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	/**
	 * 常用事件监听，用来处理通常的网络错误，授权验证错误等
	 * 
	 */
	public class MKGeneralListenerImpl implements MKGeneralListener {

		/**
		 * 一些网络状态的错误处理回调函数
		 */
		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				showToast("您的网络出错啦！");
			}
		}

		/**
		 * 授权错误的时候调用的回调函数
		 */
		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				showToast("API KEY错误, 请检查！");
			}
		}
	}

	//
	private class LocationOverlay extends MyLocationOverlay {

		public LocationOverlay(MapView arg0) {
			super(arg0);
		}

		@Override
		protected boolean dispatchTap() {
			showPopupOverlay(location);
			return super.dispatchTap();
		}

		@Override
		public void setMarker(Drawable arg0) {
			super.setMarker(arg0);
		}

	}

	private void showPopupOverlay(BDLocation location) {
		TextView popText = ((TextView) viewCache
				.findViewById(R.id.activity03_popup_tv));
		popText.setText(location.getAddrStr());
		mPopupOverlay.showPopup(getBitmapFromView(popText),
				new GeoPoint((int) (location.getLatitude() * 1e6),
						(int) (location.getLongitude() * 1e6)), 10);
	}

	/**
	 * 手动请求定位的方法
	 */
	public void requestLocation() {
		isRequest = true;

		if (mLocClient != null && mLocClient.isStarted()) {
			showToast("正在定位......");
			mLocClient.requestLocation();
		} else {
			Log.d("LocSDK3", "locClient is null or not started");
		}
	}

	/**
	 * 显示Toast消息
	 * 
	 * @param msg
	 */
	private void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	/**
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	// 查找数据库中所有的元素；
	private Cursor FindAllElements() {
		SQLiteDatabase db = DB.getReadableDatabase();
		Cursor cursor1 = db.rawQuery("select * from location order by _id",
				null);
		return cursor1;
	}

	final public class MyOverlay extends ItemizedOverlay<OverlayItem> {

		public MyOverlay(Drawable marka, MapView mMapView) {
			super(marka, mMapView);

		}

		protected boolean onTap(int index) {
			// 此处处理item点击事件
			Log.e(TAG, "onTap" + index);
			String title = list.get(index).getTitle();
			String message = list.get(index).getSnippet();
			String Detail = "纬度："
					+ (double) (list.get(index).getPoint().getLatitudeE6())
					/ 1E6 + "\n经度："
					+ (double) (list.get(index).getPoint().getLongitudeE6())
					/ 1E6 + "\n" + message;
			Builder builder = new Builder(Activity03.this);
			builder.setTitle(title);
			builder.setMessage(Detail);
			builder.setPositiveButton("关闭",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// cursor.requery();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {

						}
					});
			builder.create().show();
			return true;
		}

		@Override
		public int size() {
			return super.size();
		}

		public boolean onTap(GeoPoint pt, MapView mapView) {
			// 在此处处理MapView的点击事件，当返回true时；
			super.onTap(pt, mapView);
			return false;
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add(groupId, itemId, order, title)
		menu.add(0, 0, 0, "	显示文物单位地点");
		menu.add(0, 1, 1, "	关闭文物单位地点");
		menu.add(0, 2, 2, "	添加文物标注");

		return super.onCreateOptionsMenu(menu);
	}

	// 为每个菜单添加点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == 0 && flag) {
			// display;
			Drawable marka = this.getResources().getDrawable(
					R.drawable.icon_geo);

			DB = new MyDataBaseHelper(Activity03.this,
					"/data/data/com.archy_assistant/files/location.db", null, 1);
			cursor = FindAllElements();
			cursor.moveToFirst();
			do {
				Keywords = cursor.getString(1);
				mLat = cursor.getDouble(2);
				mLon = cursor.getDouble(3);
				Detail = cursor.getString(4);
				point1 = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
				list.add(new OverlayItem(point1, Keywords, Detail));
			} while (cursor.moveToNext());
			list.get(0).setMarker(
					this.getResources().getDrawable(R.drawable.icon_marka));
			// 创建IteminizedOverlay
			itemOverlay = new MyOverlay(marka, mMapView);

			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(itemOverlay);

			// 现在所有准备工作已准备好，使用以下方法管理overlay.
			// 添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高
			itemOverlay.addItem(list);
			// 刷新地图
			mMapView.refresh();
			flag = false;
			// 删除overlay
			// itemOverlay.removeItem(itemOverlay.getItem(0));
			// mMapView.refresh();
			// 清除overlay
			// itemOverlay.removeAll();
		} else if (id == 1) {
			// close
			if (flag) {
				Toast.makeText(Activity03.this, "图层已经清除", Toast.LENGTH_SHORT)
						.show();
			} else {
				// mMapView.getOverlays().clear();
				mMapView.getOverlays().remove(itemOverlay);
				mMapView.refresh();
				flag = true;
			}
		} else if (id == 2) {
			// 启动添加考古遗迹功能；
			Intent intent = new Intent(Activity03.this, Activity03_add.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		if (DB != null) {
			DB.close();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {

		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}

		// 退出时销毁定位
		if (mLocClient != null) {
			mLocClient.stop();
		}

		super.onResume();
	}

}
