package com.huihao.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.huihao.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThreeStageAdapter extends BaseAdapter {

	private ArrayList<Map<String, Object>> nomalList;
	private Context context;

	public ThreeStageAdapter(ArrayList<Map<String, Object>> nomalList, Context context) {
		this.nomalList = nomalList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return nomalList.size();
	}

	@Override
	public Object getItem(int position) {
		return nomalList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView content;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = (RelativeLayout) LayoutInflater.from(context)
					.inflate(R.layout.filter_child_list_item, null);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content.setText(nomalList.get(position).get("name").toString());
		return convertView;
	}


}
