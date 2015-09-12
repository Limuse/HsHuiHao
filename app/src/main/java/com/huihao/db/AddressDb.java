package com.huihao.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huihao.R;
import com.huihao.handle.ActivityHandler;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huisou on 2015/9/9.
 */
public class AddressDb extends SQLiteOpenHelper {
    public final static String NAME = "pcc.db";//数据库名称
    public final static int VERSIONS = 16;//版本号
    //三张表、省、市区
    public final static String PROVINCE = "province";//省
    public final static String CITY = "city";//市
    public final static String COUNTRY = "country";//区

    public AddressDb(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME, null, VERSIONS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String province = "create table " + PROVINCE + "( " + "_ID intrger not null," + "parent intrger not null," + "name String not null" + ")";
        String city = "create table " + CITY + "( " + "_ID intrger not null," + "parent intrger not null," + "name String not null" + ")";
        String country = "create table " + COUNTRY + "( " + "_ID intrger not null," + "parent intrger not null," + "name String not null" + ")";
        db.execSQL(province);
        db.execSQL(city);
        db.execSQL(country);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql1 = "DROP TABLE IF EXISTS " + PROVINCE;
        String sql2 = "DROP TABLE IF EXISTS " + CITY;
        String sql3 = "DROP TABLE IF EXISTS " + COUNTRY;
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        this.onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


//  数据库相关
//    AddressDb DBADDR = new AddressDb(this, null, null, 16);
//    SQLiteDatabase db = null;//= DBADDR.getWritableDatabase();
//    private void InitDate() {
//        groupArray.clear();
//        childArray.clear();
//        db = DBADDR.getWritableDatabase();
//        String url = getResources().getString(R.string.app_service_url)
//                + "/huihao/register/regions/1/sign/aggregation/";
//        LReqEntity entity = new LReqEntity(url);
//        ActivityHandler handler = new ActivityHandler(this);
//        handler.startLoadingData(entity, 1);
//    }
//
//    @Override
//    public void onResultHandler(LMessage msg, int requestId) {
//        super.onResultHandler(msg, requestId);
//        if (msg != null) {
//            if (requestId == 1) {
//                getOneStageData(msg.getStr());
//            }
//        } else {
//            T.ss("获取数据失败");
//        }
//    }
//
//
//    private void getOneStageData(String data) {
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            int code = jsonObject.getInt("status");
//            if (code == 1) {
//                JSONArray categoryList = jsonObject.getJSONArray("list");
//                for (int i = 0; i < categoryList.length(); i++) {
//                    JSONObject item = categoryList.getJSONObject(i);
//                    String name = item.getString("name");
//                    int id = item.getInt("id");
//                    int parent = item.getInt("parent");
//                    if (parent == 0) {
//                        db.execSQL("INSERT INTO " + DBADDR.PROVINCE + " (_ID,parent,name) VALUES (" + id + "," + parent + "," + "'" + name + "'" + ")");
//                    }
//                }
//
//                for (int i = 0; i < categoryList.length(); i++) {
//                    JSONObject item = categoryList.getJSONObject(i);
//                    String name = item.getString("name");
//                    int id = item.getInt("id");
//                    int parent = item.getInt("parent");
//                    Cursor cursor = db.rawQuery("select * from " + DBADDR.PROVINCE
//                            + " where _ID=?", new String[]{parent + ""});
//                    if (cursor != null) {
//                        db.execSQL("INSERT INTO " + DBADDR.CITY + " (_ID,parent,name) VALUES (" + id + "," + parent + "," + "'" + name + "'" + ")");
//                    }
//                    cursor.close();
//                }
//
//                for (int i = 0; i < categoryList.length(); i++) {
//                    JSONObject item = categoryList.getJSONObject(i);
//                    String name = item.getString("name");
//                    int id = item.getInt("id");
//                    int parent = item.getInt("parent");
//                    Cursor cursor = db.rawQuery("select * from " + DBADDR.CITY
//                            + " where _ID=?", new String[]{parent + ""});
//                    if (cursor != null) {
//                        db.execSQL("INSERT INTO " + DBADDR.COUNTRY + " (_ID,parent,name) VALUES (" + id + "," + parent + "," + "'" + name + "'" + ")");
//                    }
//                    cursor.close();
//                }
//                T.ss("数据已装完");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
