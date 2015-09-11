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
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
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
    private Toolbar toolbar;
    private ExpandableListView exp_lv_dierction;
    private ListView lv_direction;
    private List<Map<String, Object>> groupArray = new ArrayList<Map<String, Object>>();
    private List<List<Map<String, Object>>> childArray = new ArrayList<List<Map<String, Object>>>();
    private ExpandableLvAdapter adapter;
    private ThreeStageAdapter adapter_lv;
    private ArrayList<Map<String, Object>> nomalList = new ArrayList<>();
    //private String parentId;
    private int gPosition, cPosition;
    AddressDb DBADDR = new AddressDb(this, null, null, 16);
    SQLiteDatabase db = null;//= DBADDR.getWritableDatabase();

    protected void onLCreate(Bundle arg0) {
        setContentView(R.layout.activity_position_direction_filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        db = DBADDR.getWritableDatabase();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.close();

                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));


        //左边图标点击事件
        exp_lv_dierction = (ExpandableListView) findViewById(R.id.exp_lv);
        exp_lv_dierction.setGroupIndicator(null);
        lv_direction = (ListView) findViewById(R.id.lv_direction_three);
    }

    private void InitDate() {
        groupArray.clear();
        childArray.clear();
        String url = getResources().getString(R.string.app_service_url)
                + "/huihao/register/regions/1/sign/aggregation/";
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
        } else {
            T.ss("获取数据失败");
        }
    }


    private void getOneStageData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONArray categoryList = jsonObject.getJSONArray("list");
                for (int i = 0; i < categoryList.length(); i++) {
                    JSONObject item = categoryList.getJSONObject(i);
                    String name = item.getString("name");
                    int id = item.getInt("id");
                    int parent = item.getInt("parent");
                    if (parent == 0) {
                        db.execSQL("INSERT INTO " + DBADDR.PROVINCE + " (_ID,parent,name) VALUES (" + id + "," + parent + "," + "'" + name + "'" + ")");
                    }
                }

                for (int i = 0; i < categoryList.length(); i++) {
                    JSONObject item = categoryList.getJSONObject(i);
                    String name = item.getString("name");
                    int id = item.getInt("id");
                    int parent = item.getInt("parent");
                    Cursor cursor = db.rawQuery("select * from " + DBADDR.PROVINCE
                            + " where _ID=?", new String[]{parent + ""});
                    if (cursor != null) {
                        db.execSQL("INSERT INTO " + DBADDR.CITY + " (_ID,parent,name) VALUES (" + id + "," + parent + "," + "'" + name + "'" + ")");
                    }
                    cursor.close();
                }

                for (int i = 0; i < categoryList.length(); i++) {
                    JSONObject item = categoryList.getJSONObject(i);
                    String name = item.getString("name");
                    int id = item.getInt("id");
                    int parent = item.getInt("parent");
                    Cursor cursor = db.rawQuery("select * from " + DBADDR.CITY
                            + " where _ID=?", new String[]{parent + ""});
                    if (cursor != null) {
                        db.execSQL("INSERT INTO " + DBADDR.COUNTRY + " (_ID,parent,name) VALUES (" + id + "," + parent + "," + "'" + name + "'" + ")");
                    }
                    cursor.close();
                }
                T.ss("数据已装完");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData() {
        Cursor cursor = db.rawQuery("select * from " + DBADDR.PROVINCE
                + " where parent=?", new String[]{0 + ""});
        int s = cursor.getCount();
        if (cursor.moveToNext()) {
            T.ss(cursor.getInt(0));
        }
    }

}
