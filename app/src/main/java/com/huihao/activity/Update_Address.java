package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.db.PccDb;
import com.huihao.entity.AddressItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/8/19.
 */
public class Update_Address extends LActivity {
    private EditText et_name, et_phone, et_shen, et_xiang;
    private ImageView iv_del;
    private String province;//省
    private String city;//市
    private String country;//区
    private String provinceID;// 省ID
    private String cityID;   //  市ID
    private String countryID; // 区ID
    private String itmid;
    SQLiteDatabase database;
    private PccDb db = new PccDb();

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_update_address);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        database = db.openDatabase(this);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initData();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("修改收货地址");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.right_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {

                    if (et_name.equals(null) || et_phone.equals(null) || et_shen.equals(null) || et_xiang.equals(null)) {
                        T.ss("信息不能为空！");
                    } else {
                        subm();
                    }
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        et_name = (EditText) findViewById(R.id.et_umen);
        et_phone = (EditText) findViewById(R.id.et_umenphone);
        et_shen = (EditText) findViewById(R.id.et_ushen);
        et_xiang = (EditText) findViewById(R.id.et_uxiang);
        iv_del = (ImageView) findViewById(R.id.iv_udel);


        /**
         *省市区需要弹框
         */
        et_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_Address.this, ProviceInfoPlace.class);
                startActivityForResult(intent, 1);
            }

        });

        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_xiang.setText(null);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1:
                if (data != null) {
                    String name = data.getExtras().getString("name");
                    et_shen.setText(name);
                    provinceID = data.getExtras().getString("pid"); // 省ID
                    cityID = data.getExtras().getString("cityid");   //  市ID
                    if (data.getExtras().getString("countryid") == null) {
                        countryID = null;
                    } else {
                        countryID = data.getExtras().getString("countryid"); // 区ID
                    }
                }
                break;
            default:
                break;
        }
    }

    private void initData() {
        itmid = getIntent().getExtras().getString("itmid");
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/myaddress/getinfo/1/sign/aggregation/?uuid=" + Token.get(this) + "&id=" + itmid;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }

    private void getJsonData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject object = jsonObject.getJSONObject("list");
                if (object.length() > 0) {
                    AddressItemEntity itementity = new AddressItemEntity();
                    itementity.setId(object.getString("id"));
                    itementity.setUname(object.getString("uname"));
                    itementity.setUphone(object.getString("uphone"));
                    itementity.setProvince(object.getString("province"));
                    itementity.setCity(object.getString("city"));
                    itementity.setCountry(object.getString("country"));
                    itementity.setAddress(object.getString("address"));
                    cityID = itementity.getCity();
                    provinceID = itementity.getProvince();
                    countryID = itementity.getCountry();

                    String sql = "select * from province where _ID=?";
                    String sql2 = "select * from city where _ID=?";
                    String sql3 = "select * from country where _ID=?";
                    Cursor cursor = database.rawQuery(sql, new String[]{provinceID + ""});
                    Cursor cursor1 = database.rawQuery(sql2, new String[]{cityID + ""});
                    Cursor cursor2 = database.rawQuery(sql3, new String[]{countryID + ""});
                    String name0 = null, name1 = null, name2 = null;
                    while (cursor.moveToNext() & cursor1.moveToNext() & cursor2.moveToNext()) {
                        name0 = cursor.getString(2);
                        name1 = cursor1.getString(2);
                        name2 = cursor2.getString(2);
                        String name = name0 + "-" + name1 + "-" + name2;
                        et_shen.setText(name);
                    }
                    cursor.close();
                    cursor1.close();
                    cursor2.close();

                    et_name.setText(itementity.getUname());
                    et_phone.setText(itementity.getUphone());
                    et_xiang.setText(itementity.getAddress());
                } else {
                    T.ss("没有数据");
                }
            } else {
                T.ss("获取数据失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else if (requestId == 2) {
                getJsonSubmit(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void subm() {
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String shen = et_shen.getText().toString();
        String addr = et_xiang.getText().toString();
//        provinceID = 1;// 省ID
//        cityID = 1;   //  市ID
//        countryID = 1;//
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/myaddress/edit/1/sign/aggregation/";
        final Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", Token.get(this));
        map.put("id", itmid);//地址id
        map.put("uname", name);//收货人名
        map.put("uphone", phone);// 收货人联系号码
        map.put("province", provinceID + "");// 省ID
        map.put("city", cityID + "");//     市ID
        map.put("country", countryID + "");//  区ID
        map.put("address", addr);//详细地址
        LReqEntity entity = new LReqEntity(url, map);
        L.e(entity + "");
        ActivityHandler handler = new ActivityHandler(Update_Address.this);
        handler.startLoadingData(entity, 2);
    }

    private void getJsonSubmit(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss("保存成功！");
            } else {

                T.ss(jsonObject.getString("info").toString());
                String longs=jsonObject.getString("info");
                if(longs.equals("请先登录")){
                    LSharePreference.getInstance(this).setBoolean("login", false);
                    Intent intent = new Intent(this, LoginMain.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


}
