package com.archy_assistant_software;

import java.util.List;
import com.archy_assistant.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplicationInfoAdapter extends BaseAdapter{
	
	private List<AppInfo> mylistAppInfo = null;
	
	LayoutInflater inflater = null;
	
	public ApplicationInfoAdapter(Context context,List <AppInfo> apps) {
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mylistAppInfo = apps;		
	}

	@Override
	public int getCount() {
		return mylistAppInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mylistAppInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView at " + position);
		View view = null;
		ViewHolder holder = null;
		
		if(convertView == null || convertView.getTag() == null) {
			view = inflater.inflate(R.layout.activity12_lv, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		else {
			view = convertView;
			holder = (ViewHolder)convertView.getTag();
		}
		
		AppInfo appinfo = (AppInfo)getItem(position);
		holder.appIcon.setImageDrawable(appinfo.getAppIcon());
		holder.tvAppLabel.setText(appinfo.getAppLabel());
		
		return view;
	}

	// listview加载性能优化;如果使用ViewHolder，那么就可以做到一次创建n次复用.
	class ViewHolder {
		ImageView appIcon;
		TextView tvAppLabel;
		
		public ViewHolder(View view) {
			this.appIcon = (ImageView)view.findViewById(R.id.activity12_lv_image);
			this.tvAppLabel = (TextView)view.findViewById(R.id.activity12_lv_tv);
		}
	}
}
