package com.huihao.adapter;

import java.util.List;
import java.util.Map;

import com.huihao.R;
import com.leo.base.util.T;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableLvAdapter extends BaseExpandableListAdapter{
	private List<Map<String, Object>> groupArray;
	private List<List<Map<String, Object>>> childArray;
	private Context context;
	public ExpandableLvAdapter(List<Map<String, Object>> groupArray,List<List<Map<String, Object>>> childArray,Context context){
		this.childArray = childArray;
		this.groupArray = groupArray;
		this.context = context;
	}
	@Override
	public int getGroupCount() {
		return groupArray.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childArray.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupArray.get(groupPosition).get("name");
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childArray.get(groupPosition).get(childPosition).get("childName");
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.filter_list_item, null);
		TextView title = (TextView) convertView.findViewById(R.id.content);
		title.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.filter_child_list_item, null);
		TextView title = (TextView) convertView.findViewById(R.id.content);
		title.setText(getChild(groupPosition, childPosition).toString());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
