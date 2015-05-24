package com.archy_assistant_question;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.event.click.ArcPosition;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class ChartPie extends ChartTouchView implements Runnable {

	private String TAG = "ChartPie";
	private PieChart chart = new PieChart();
	private LinkedList<PieData> chartData = new LinkedList<PieData>();
	private ArrayList<Question01_Item> dataItems;
	private int position;

	public ChartPie(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ChartPie(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ChartPie(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public ChartPie(ChartActivity chartActivity,
			ArrayList<Question01_Item> dataItems, int position) {
		// TODO Auto-generated constructor stub
		super(chartActivity);
		this.dataItems = dataItems;
		this.position = position;
		initView();
	}

	private void initView() {
		chartDataSet();
		chartRender();
		new Thread(this).start();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 图所占范围大小
		chart.setChartRange(w, h);
	}

	private void chartRender() {
		try {

			// 设置绘图区默认缩进px值
			int[] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

			// 设置起始偏移角度(即第一个扇区从哪个角度开始绘制)
			// chart.setInitialAngle(90);

			// 标签显示(隐藏，显示在中间，显示在扇区外面)
			chart.setLabelPosition(XEnum.SliceLabelPosition.INNER);
			chart.getLabelPaint().setColor(Color.WHITE);

			// 标题
			chart.setTitle(dataItems.get(position).getTitle());

			chart.addSubtitle("(" + dataItems.get(position).getVoteQuestion()
					+ ")");
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.BOTTOM);

			// 显示图例
			chart.getPlotLegend().showLegend();
			chart.getPlotLegend().getLegendLabelPaint().setTextSize(22);
			// chart.showBorder();

			// chart.setDataSource(chartData);

			// 激活点击监听
			// chart.ActiveListenItemClick();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	/*
	 * private String percent(double dle){ NumberFormat
	 * nf=NumberFormat.getPercentInstance(); nf.setMinimumFractionDigits(0);
	 * return nf.format(dle); } private double towDigits(double dle){ BigDecimal
	 * bg = new BigDecimal(dle); return bg.setScale(2,
	 * BigDecimal.ROUND_HALF_UP).doubleValue();
	 * 
	 * }
	 */

	private void chartDataSet() {
		/*
		 * //设置图表数据源 chartData.add(new PieData("HP","20%",20,(int)Color.rgb(155,
		 * 187, 90))); chartData.add(new
		 * PieData("IBM","30%",30,(int)Color.rgb(191, 79, 75),false));
		 * chartData.add(new PieData("DELL","10%",10,(int)Color.rgb(242, 167,
		 * 69))); //将此比例块突出显示 chartData.add(new
		 * PieData("EMC","40%",40,(int)Color.rgb(60, 173, 213),false));
		 */

		int[] color = { (int) Color.rgb(155, 187, 90),
				(int) Color.rgb(191, 79, 75), (int) Color.rgb(242, 167, 69),
				(int) Color.rgb(60, 173, 213), (int) Color.rgb(90, 79, 88) };
		int total = dataItems.get(position).getTotalNumber();
		int[] count = dataItems.get(position).getCount();
		int k = dataItems.get(position).getVoteAnswers().size();
		int sum = 0;

		for (int i = 0; i < k; i++) {
			String title = dataItems.get(position).getVoteAnswers().get(i)
					+ "\n";
			double percent = (count[i] * 1.0) / total;
			Log.e(TAG, "total:" + total + "=count" + i + ":" + count[i]
					+ "=percent:" + percent);
			int temp = (int) Math.round(percent * 100);
			//通过数据进行平滑；
			sum = sum + temp;
			if (sum > 100) {
				temp = temp - (sum - 100);
			}
			if (i == k - 1) {
				if (sum < 100) {
					temp = temp + (100 - sum);
				}
			}

			Log.e(TAG, "temp:" + temp);
			if (i < color.length - 1) {
				chartData.add(new PieData(title, temp + "%", temp, color[i]));
			} else {
				chartData.add(new PieData(title, temp + "%", temp, color[4]));
			}
		}

		/*
		 * chartData.add(new PieData("closed","7%" ,
		 * (0.07*100),(int)Color.rgb(155, 187, 90))); chartData.add(new
		 * PieData("inspect","19%" , (0.19*100),(int)Color.rgb(191, 79, 75)));
		 * chartData.add(new PieData("open","74%" ,
		 * (0.74*100),(int)Color.rgb(242, 167, 69)));
		 * 
		 * 
		 * chartData.add(new PieData("closed","7%" ,
		 * (0.07*100),(int)Color.rgb(155, 187, 90))); chartData.add(new
		 * PieData("inspect","18%" , (0.18*100),(int)Color.rgb(191, 79, 75)));
		 * chartData.add(new PieData("open","74%" ,
		 * (0.74*100),(int)Color.rgb(242, 167, 69)));
		 * 
		 * chartData.add(new PieData("closed","36%" ,
		 * (0.36*100),(int)Color.rgb(155, 187, 90))); chartData.add(new
		 * PieData("inspect","16%" , (0.16*100),(int)Color.rgb(191, 79, 75)));
		 * chartData.add(new PieData("open","49%" ,
		 * (0.49*100),(int)Color.rgb(242, 167, 69)));
		 */
	}

	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);
		return lst;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			chartAnimation();
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	private void chartAnimation() {
		try {

			for (int i = 0; i < chartData.size(); i++) {
				Thread.sleep(100);
				LinkedList<PieData> animationData = new LinkedList<PieData>();

				int sum = 0;
				for (int j = 0; j <= i; j++) {
					animationData.add(chartData.get(j));
					sum += chartData.get(j).getPercentage();
				}

				animationData.add(new PieData("", "", 100 - sum, (int) Color
						.argb(1, 0, 0, 0)));
				chart.setDataSource(animationData);

				// 激活点击监听
				if (chartData.size() - 1 == i)
					chart.ActiveListenItemClick();

				postInvalidate();
			}

		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (chart.isPlotClickArea(event.getX(), event.getY())) {
				triggerClick(event.getX(), event.getY());
			}
		}
		return true;
	}

	// 触发监听
	private void triggerClick(float x, float y) {

		ArcPosition record = chart.getPositionRecord(x, y);
		if (null == record)
			return;

		PieData pData = chartData.get(record.getDataID());
		Toast.makeText(this.getContext(),
				"" + pData.getKey() + ":" + pData.getLabel(),
				Toast.LENGTH_SHORT).show();

	}
}
