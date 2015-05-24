package com.archy_assistant_question;

import java.util.ArrayList;
import com.archy_assistant.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

public class ChartActivity extends Activity {

	private ZoomControls mZoomControls;
	private ChartPie PieChartView;
	private FrameLayout framelayout;
	private String TAG = "ChartActivity";
	public ArrayList<Question01_Item> dataItem;
	private int position;
	private int list;
	public ConstantArrayList constantArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment02_piechart);
		Log.e(TAG, "onCreate");
		// 获取传递过来的标签；
		position = getIntent().getIntExtra("position", 0);
		list = getIntent().getIntExtra("list", 0);
		Log.e(TAG, "onCreate position" + position);
		constantArrayList = (ConstantArrayList) getApplication();

		dataItem = constantArrayList.getDataItems().get(list);

		framelayout = (FrameLayout) findViewById(R.id.fragment02_piechart_framelayout);
		PieChartView = new ChartPie(this, dataItem, position);

		// mZoomControls控件的布局
		FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		frameParm.gravity = Gravity.BOTTOM | Gravity.RIGHT;

		// 放大缩小控件；
		mZoomControls = new ZoomControls(this);
		mZoomControls.setIsZoomInEnabled(true);
		mZoomControls.setIsZoomOutEnabled(true);
		mZoomControls.setLayoutParams(frameParm);

		// 图表控件；
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int scrWidth = (int) (dm.widthPixels * 0.9);
		int scrHeigth = (int) (dm.heightPixels * 0.9);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				scrWidth, scrHeigth);
		// 居中显示；
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		final RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.addView(PieChartView, layoutParams);

		framelayout.addView(relativeLayout);
		// 添加放大缩小控件
		// framelayout.addView(mZoomControls);

		mZoomControls.setOnZoomInClickListener(new OnZoomInClickListenerImpl());

		mZoomControls
				.setOnZoomOutClickListener(new OnZoomOutClickListenerImpl());
	}

	public class OnZoomInClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PieChartView.zoomIn();
		}

	}

	public class OnZoomOutClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			PieChartView.zoomOut();
		}

	}

}
