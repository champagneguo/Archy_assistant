package com.archy_assistant_question;

import java.util.List;
import com.archy_assistant.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment03 extends Fragment implements OnItemClickListener,
		OnScrollListener {

	private String TAG = "Fragment03";
	private Fragment03NewsListAdapter adapter;
	private ListView listView;
	private boolean isLoading = true;
	private FragmentActivity activity;
	private int page = 0;
	private EditText Keyword;
	private Button Search;
	private String keyword = "考古";
	private boolean flag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.fragment03, container,
				false);
		this.activity = getActivity();
		Log.e(TAG, "onCreateView");
		return parentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		Keyword = (EditText) activity.findViewById(R.id.fragment03_keyword);
		Search = (Button) activity.findViewById(R.id.fragment03_search);

		Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				keyword = Keyword.getText().toString().trim();
				Log.e(TAG, "onclick:" + keyword);
				page = 0;
				if (keyword.equals("")) {
					keyword = "考古";
				}
				adapter.removeNews();
				adapter.notifyDataSetChanged();
				new MyAsyncTaskGetNews().execute(page);
			}
		});

		listView = (ListView) activity
				.findViewById(R.id.fragment03_news_listview);
		adapter = new Fragment03NewsListAdapter(activity, null);
		listView.addFooterView(View.inflate(activity, R.layout.fragment03_foot,
				null));
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(this);
		new MyAsyncTaskGetNews().execute(page);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (view.getId() != R.id.foot_view) {
			Intent intent = new Intent(activity, NewsDetailsActivity.class);
			intent.putExtra("url", adapter.getNewss().get(position).getUrl());
			startActivity(intent);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	/**
	 * 滑动到底时自动加载更多
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			new MyAsyncTaskGetNews().execute(page);
		}
	}

	/**
	 * 异步获取新闻列表集合
	 * 
	 * 
	 */
	public class MyAsyncTaskGetNews extends
			AsyncTask<Integer, String, List<Fragment03News>> {
		@Override
		protected List<Fragment03News> doInBackground(Integer... pages) {
			Log.e(TAG, "MyAsyncTaskGetNews:" + keyword);

			List<Fragment03News> newss = GetNewsService.getNewsByPage(keyword,
					pages[0]);

			return newss;
		}

		@Override
		protected void onPostExecute(List<Fragment03News> newss) {

			Log.e(TAG, "onPostExecute");

			if (newss.size() > 0) {
				adapter.addNews(newss);
				adapter.notifyDataSetChanged();
				page++;
			}

			isLoading = false;
		}

	}

}
