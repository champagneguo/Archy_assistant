package com.archy_assistant_question;

import com.archy_assistant.R;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Fragment02_popupAdapter extends BaseAdapter {

	private FragmentActivity mContext;
	private String[] categority;
	private String TAG = "Fragment02_popupAdapter";

	public Fragment02_popupAdapter(FragmentActivity mContext,
			String[] categority) {
		this.mContext = mContext;
		this.categority = categority;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categority.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categority[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			Log.e(TAG, "getView:");
			convertView = View.inflate(mContext,
					R.layout.fragment02_popup_spinner, null);
			// convertView =
			// mContext.getLayoutInflater().inflate(R.layout.fragment02_popup_spinner,
			// null);
			Log.e(TAG, "getView:" + convertView);
		}
		TextView textview = (TextView) convertView
				.findViewById(R.id.fragment02_popup_spinner_tv);
		textview.setText(categority[position]);

		return convertView;
	}

}
