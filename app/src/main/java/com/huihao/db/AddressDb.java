package com.huihao.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
