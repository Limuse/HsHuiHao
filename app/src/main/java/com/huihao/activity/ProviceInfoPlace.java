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
import com.huihao.db.AddressDb;
import com.huihao.db.PccDb;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private ExpandableListView exp_lv_dierction;
    private ListView lv_direction;
    private List<Map<String, Object>> groupArray = new ArrayList<Map<String, Object>>();
    private List<List<Map<String, Object>>> childArray = new ArrayList<List<Map<String, Object>>>();
    private ExpandableLvAdapter adapter;
    private ThreeStageAdapter adapter_lv;
    private ArrayList<Map<String, Object>> nomalList = new ArrayList<>();
    private String parentId;
    private int gPosition, cPosition;
    private String pPid, pPname;
    SQLiteDatabase database;
    private PccDb db = new PccDb();

    protected void onLCreate(Bundle arg0) {
        setContentView(R.layout.activity_position_direction_filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        database = db.openDatabase(this);
        InitView();
        InitDate();
        initClicks();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));


        //左边图标点击事件
        exp_lv_dierction = (ExpandableListView) findViewById(R.id.exp_lv);
        exp_lv_dierction.setGroupIndicator(null);
        lv_direction = (ListView) findViewById(R.id.lv_direction_three);
    }

    private void initClicks() {
        lv_direction.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Map<String, Object> map = nomalList.get(position);
                Intent intent = new Intent();
                intent.putExtra("name", pPname + "-" + childArray.get(gPosition)
                        .get(cPosition).get("childName")
                        + "-" + map.get("name") + "");
                intent.putExtra("pid", pPid);
                intent.putExtra("cityid", map.get("parentId") + "");
                intent.putExtra("countryid", map.get("id") + "");
                setResult(1, intent);
                finish();
            }
        });
    }


    private void InitDate() {
        groupArray.clear();
        childArray.clear();
        getOneStageData();

    }


    //省
    private void getOneStageData() {
        try {
            String sql = "select * from province where parent=?";
            Cursor cursor = database.rawQuery(sql, new String[]{0 + ""});
            while (cursor.moveToNext()) {
                List<Map<String, Object>> tempArray = new ArrayList<Map<String, Object>>();
                Map<String, Object> oneStageMap = new HashMap<String, Object>();
                Map<String, Object> twoStageMap = new HashMap<String, Object>();
                parentId = cursor.getString(0);
                String parent = cursor.getString(1);
                String name = cursor.getString(2);
                oneStageMap.put("parentId", parentId);
                oneStageMap.put("name", name);
                oneStageMap.put("id", parent);
                groupArray.add(oneStageMap);
                tempArray.add(twoStageMap);
                childArray.add(tempArray);
            }
            cursor.close();
            InitClick();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //市
    private void getTwoStageData(String data) {
        try {
            String sql = "select * from city where parent=?";
            Cursor cursor = database.rawQuery(sql, new String[]{data});
            List<Map<String, Object>> tempArray = new ArrayList<Map<String, Object>>();
            while (cursor.moveToNext()) {
                Map<String, Object> twoStageMap = new HashMap<String, Object>();
                parentId = cursor.getString(0);
                String parent = cursor.getString(1);
                String name = cursor.getString(2);
                twoStageMap.put("childId", parentId);
                twoStageMap.put("childName", name);
                twoStageMap.put("child_parentId", parent);
                tempArray.add(twoStageMap);
            }
            childArray.add(gPosition, tempArray);
            exp_lv_dierction.expandGroup(gPosition);
            cursor.close();

        } catch (Exception e) {
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
                exp_lv_dierction.setSelectedGroup(groupPosition);
                boolean expanded = parent.isGroupExpanded(groupPosition);
                gPosition = groupPosition;
                for (int i = 0, count = exp_lv_dierction
                        .getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {
                        exp_lv_dierction.collapseGroup(i);
                    }
                }
                if (!expanded) {
                    parentId = groupArray.get(groupPosition).get("parentId")
                            + "";
                    pPid = parentId;
                    pPname = groupArray.get(groupPosition).get("name") + "";
                    getTwoStageData(parentId);
                    exp_lv_dierction.setSelectedGroup(groupPosition);
                }else {
                    exp_lv_dierction.collapseGroup(groupPosition);
                }
                return true;
            }
        });

        exp_lv_dierction.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                cPosition = childPosition;
                nomalList.clear();
                String chidId = childArray.get(groupPosition)
                        .get(childPosition).get("childId").toString();

                getThreeStageData(chidId + "");
                return true;
            }
        });

    }

    //区
    private void getThreeStageData(String data) {
        try {
            String sql = "select * from country where parent=?";
            Cursor cursor = database.rawQuery(sql, new String[]{data});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Map<String, Object> map = new HashMap<>();
                    Map<String, Object> twoStageMap = new HashMap<String, Object>();
                    String id = cursor.getString(0);
                    parentId = cursor.getString(1);
                    String name = cursor.getString(2);
                    map.put("id", id);
                    map.put("parentId", parentId);
                    map.put("name", name);
                    nomalList.add(map);
                }

                adapter_lv = new ThreeStageAdapter(nomalList,
                        ProviceInfoPlace.this);
                lv_direction.setAdapter(adapter_lv);
                cursor.close();
            } else {
                Intent intent = new Intent();
                intent.putExtra("name", pPname + "-" + childArray.get(gPosition)
                                .get(cPosition).get("childName")
                );
                intent.putExtra("pid", pPid);
                intent.putExtra("cityid", childArray.get(gPosition).get(cPosition)
                        .get("childId")
                        + "");
                setResult(1, intent);
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

