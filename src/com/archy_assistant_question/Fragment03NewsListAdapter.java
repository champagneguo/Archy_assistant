package com.archy_assistant_question;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.archy_assistant.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment03NewsListAdapter extends BaseAdapter {

	private String TAG = "Fragment03NewsListAdapter";
	private List<Fragment03News> news;
	private Context context;
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();

	public Fragment03NewsListAdapter(Context context, List<Fragment03News> news) {
		super();
		this.context = context;
		if (news == null) {
			Log.e(TAG, "构造函数1" + news);
			this.news = new ArrayList<Fragment03News>();
			Log.e(TAG, "构造函数2" + this.news.size());
		} else {
			Log.e(TAG, "构造函数" + news.size());
			this.news = news;
		}
	}

	public void addNews(List<Fragment03News> newNewss) {
		Log.e(TAG, "addNews" + newNewss);
		news.addAll(newNewss);
	}

	public void removeNews() {
		news.clear();
	}

	public List<Fragment03News> getNewss() {
		return news;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e(TAG, "getCount()" + news.size());
		if (news == null) {
			return 0;
		}
		return news.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return news.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Fragment03News newItem = news.get(position);
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.fragment03_news_listview, null);
		}
		((TextView) convertView
				.findViewById(R.id.fragment03_news_listview_title))
				.setText(newItem.getTitle());
		((TextView) convertView
				.findViewById(R.id.fragment03_news_listview_source))
				.setText(newItem.getSource());
		((TextView) convertView
				.findViewById(R.id.fragment03_news_listview_time))
				.setText(newItem.getDate());

		// 异步处理图片
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.fragment03_news_listview_photo);
		imageView.setImageResource(R.drawable.img_temp);
		imageView.setTag(newItem.getPhotoUrl());
		if (TextUtils.isEmpty(newItem.getPhotoUrl())) {
			imageView.setVisibility(View.GONE);
		} else {
			imageView.setVisibility(View.VISIBLE);
			if (map.get(newItem.getPhotoUrl()) != null
					&& map.get(newItem.getPhotoUrl()).get() != null) {
				imageView.setImageBitmap(map.get(newItem.getPhotoUrl()).get());
			} else {
				MyAsyncTaskGetBitmap myAsyncTaskGetBitmap = new MyAsyncTaskGetBitmap();
				myAsyncTaskGetBitmap.targetUrl = newItem.getPhotoUrl();
				myAsyncTaskGetBitmap.imageView = imageView;
				myAsyncTaskGetBitmap.execute("");
			}
		}
		return convertView;
	}

	/**
	 * 异步获取新闻列表里面的图片
	 * 
	 */
	public class MyAsyncTaskGetBitmap extends AsyncTask<String, String, Bitmap> {
		ImageView imageView;
		// 照片标志，由于imageview的重用，可能被其他item占用，导致获取的imageview的图片和item不和。这里设一个标志如果tag和imageView.getTag()相同说明该imagevIEW还没有被重用
		String targetUrl;

		@Override
		protected Bitmap doInBackground(String... imageViews) {
			Bitmap bitmap = GetBitmapUtil.getBitmapByUrl(targetUrl);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			map.put(targetUrl, new SoftReference<Bitmap>(bitmap));
			if (imageView.getTag().equals(targetUrl)) {
				imageView.setImageBitmap(bitmap);
			}
		}

	}

}
