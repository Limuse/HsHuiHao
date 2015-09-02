package com.huihao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huihao.R;
import com.huihao.adapter.ExpandableLvAdapter;
import com.huihao.adapter.ThreeStageAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ProviceInfoPlace extends LActivity {
	private Toolbar toolbar;
	private ExpandableListView exp_lv_dierction;
	private ListView lv_direction;
	private List<Map<String, Object>> groupArray = new ArrayList<Map<String, Object>>();
	private List<List<Map<String, Object>>> childArray = new ArrayList<List<Map<String, Object>>>();
	private ExpandableLvAdapter adapter;
	private ThreeStageAdapter adapter_lv;
	private ArrayList<Map<String, Object>> nomalList = new ArrayList<>();
	private String parentId;
	private int gPosition, cPosition;


	protected void onLCreate(Bundle arg0) {
		setContentView(R.layout.activity_position_direction_filter);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.app_white);

		InitView();
		InitDate();
		initClick();
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	private void InitView() {
		Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle("省市区");
		toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
		toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
		//左边图标点击事件
		exp_lv_dierction = (ExpandableListView) findViewById(R.id.exp_lv);
		exp_lv_dierction.setGroupIndicator(null);
		lv_direction = (ListView) findViewById(R.id.lv_direction_three);
	}

	private void InitDate() {
		groupArray.clear();
		childArray.clear();
		String url = getResources().getString(R.string.app_service_url)
				+ "/huihao/register/regions/1/sign/aggregation/?parentId=0";
		LReqEntity entity = new LReqEntity(url);
		ActivityHandler handler = new ActivityHandler(this);
		handler.startLoadingData(entity, 1);
	}

	private void initClick() {
		lv_direction.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, Object> map = nomalList.get(position);
				Intent intent = new Intent();
				intent.putExtra("name", childArray.get(gPosition)
						.get(cPosition).get("childName")
						+ "" + map.get("name") + "");
				intent.putExtra("id", map.get("id") + "");
				intent.putExtra("parentId", map.get("parentId") + "");
				setResult(1, intent);
				finish();
			}
		});
	}

	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		super.onResultHandler(msg, requestId);
		if (msg != null) {
			if (requestId == 1) {
				getOneStageData(msg.getStr());
			}
			if (requestId == 2) {
				getTwoStageData(msg.getStr());
			}
			if (requestId == 3) {
				getThreeStageData(msg.getStr());
			}
		} else {
			T.ss("获取数据失败");
		}
	}

	private void getTwoStageData(String str) {

		try {
			JSONObject jsonObject = new JSONObject(str);
			int code = jsonObject.getInt("code");
			if (code == 0) {
				JSONObject result = jsonObject.getJSONObject("result");
				JSONArray categoryList = result.getJSONArray("regionList");
				List<Map<String, Object>> tempArray = new ArrayList<Map<String, Object>>();
				// JSONObject item = categoryList.getJSONObject(i);
				// JSONArray ja = item.getJSONArray("child");
				// String name = item.getString("name");
				// int id = item.getInt("id");
				// int parentId = item.getInt("parentId");
				// oneStageMap.put("id", id);
				// oneStageMap.put("name", name);
				// oneStageMap.put("parentId", parentId);
				for (int j = 0; j < categoryList.length(); j++) {
					Map<String, Object> twoStageMap = new HashMap<String, Object>();
					JSONObject jo = categoryList.getJSONObject(j);
					String chidName = jo.getString("name");
					int chidId = jo.getInt("id");
					int chid_parentId = jo.getInt("parentId");
					twoStageMap.put("childId", chidId);
					twoStageMap.put("childName", chidName);
					twoStageMap.put("child_parentId", chid_parentId);
					tempArray.add(twoStageMap);
				}
				childArray.add(gPosition, tempArray);
				exp_lv_dierction.expandGroup(gPosition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getOneStageData(String data) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			int code = jsonObject.getInt("code");
			if (code == 0) {
				JSONObject result = jsonObject.getJSONObject("result");
				JSONArray categoryList = result.getJSONArray("regionList");
				for (int i = 0; i < categoryList.length(); i++) {
					List<Map<String, Object>> tempArray = new ArrayList<Map<String, Object>>();
					Map<String, Object> oneStageMap = new HashMap<String, Object>();
					JSONObject item = categoryList.getJSONObject(i);
					String name = item.getString("name");
					int id = item.getInt("id");
					parentId = item.getInt("id") + "";
					oneStageMap.put("id", id);
					oneStageMap.put("name", name);
					oneStageMap.put("parentId", parentId);
					groupArray.add(oneStageMap);
					Map<String, Object> twoStageMap = new HashMap<String, Object>();
					// JSONObject jo = ja.getJSONObject(j);
					// String chidName = jo.getString("name");
					// int chidId = jo.getInt("id");
					// int chid_parentId = jo.getInt("parentId");
					// twoStageMap.put("childId", chidId);
					// twoStageMap.put("childName", chidName);
					// twoStageMap.put("child_parentId", chid_parentId);
					tempArray.add(twoStageMap);
					childArray.add(tempArray);
				}
				InitClick();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void InitClick() {
		adapter = new ExpandableLvAdapter(groupArray, childArray,
				ProviceInfoPlace.this);
		adapter.notifyDataSetChanged();
		exp_lv_dierction.setAdapter(adapter);
		exp_lv_dierction.setSelectedGroup(gPosition);
		exp_lv_dierction.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				boolean expanded = parent.isGroupExpanded(groupPosition);
				gPosition = groupPosition;
				exp_lv_dierction.setSelectedGroup(gPosition);
				if (!expanded) {
					parentId = groupArray.get(groupPosition).get("parentId")
							+ "";
					String url = getResources().getString(R.string.app_service_url)
							+ "/huihao/register/regions/1/sign/aggregation/?parentId="
							+ parentId;
					LReqEntity entity = new LReqEntity(url);
					ActivityHandler handler = new ActivityHandler(
							ProviceInfoPlace.this);
					handler.startLoadingData(entity, 2);
					return true;
				}
				return false;
			}
		});

		exp_lv_dierction.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				cPosition = childPosition;

				nomalList.clear();

				int chidId = (int) childArray.get(groupPosition)
						.get(childPosition).get("childId");
				String url = getResources().getString(R.string.app_service_url)
						+ "/huihao/register/regions/1/sign/aggregation/?parentId="
						+ chidId;
				LReqEntity entity = new LReqEntity(url);
				ActivityHandler handler = new ActivityHandler(
						ProviceInfoPlace.this);
				handler.startLoadingData(entity, 3);
				return true;
			}
		});

	}

	private void getThreeStageData(String data) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			int code = jsonObject.getInt("code");
			if (code == 0) {
				JSONObject result = jsonObject.getJSONObject("result");
				JSONArray categoryList = result.getJSONArray("regionList");
				if (categoryList.length() > 0) {
					for (int i = 0; i < categoryList.length(); i++) {
						JSONObject jo = categoryList.getJSONObject(i);
						Map<String, Object> map = new HashMap<>();
						int id = jo.getInt("id");
						int parentId = jo.getInt("parentId");
						String name = jo.getString("name");
						map.put("id", id);
						map.put("parentId", parentId);
						map.put("name", name);
						nomalList.add(map);
					}
					adapter_lv = new ThreeStageAdapter(nomalList,
							ProviceInfoPlace.this);
					lv_direction.setAdapter(adapter_lv);
				} else {
					Intent intent = new Intent();
					intent.putExtra(
							"name",
							childArray.get(gPosition).get(cPosition)
									.get("childName")
									+ "");
					intent.putExtra(
							"id",
							childArray.get(gPosition).get(cPosition)
									.get("childId")
									+ "");
					setResult(1, intent);
					finish();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
