package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.AddressHoriSliseAdapter;
import com.huihao.adapter.ChooseAddressAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.AddressItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/8/7.
 */
public class Choose_Address extends LActivity implements View.OnClickListener {
    private Button btn_addr;
    private ListView listView;
    private ChooseAddressAdapter adapter;
    private List<AddressItemEntity> list = new ArrayList<AddressItemEntity>();
    private LinearLayout headview;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_choose_address);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("收货地址");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                adapter.notifyDataSetChanged();
//                AddressItemEntity iii = adapter.BaReturn();
//                if (iii==null) {
//                    setResult(0, null);
//                } else {
//                    String name = iii.getUname();
//                    String phone = iii.getUphone();
//                    String addd = iii.getAddress();
//                    String province = iii.getProvince();
//                    String city = iii.getCity();
//                    String country = iii.getCountry();
//                    String ids = iii.getId();
//                    Intent intent = new Intent();
//                    intent.putExtra("name", name);
//                    intent.putExtra("ids", ids);
//                    intent.putExtra("phone", phone);
//                    intent.putExtra("addr", addd);
//                    intent.putExtra("province", province);
//                    intent.putExtra("city", city);
//                    intent.putExtra("counrty", country);
//                    setResult(0, intent);
//                }
                finish();
            }
        });
        //右边图片点击事件
        toolbar.inflateMenu(R.menu.choose_addr);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    Intent intent = new Intent(Choose_Address.this, Address.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        listView = (ListView) findViewById(R.id.lv_cadd);

        headview = (LinearLayout) LayoutInflater.from(Choose_Address.this).inflate(R.layout.listview_head, null);
        listView.addHeaderView(headview);
        btn_addr = (Button) findViewById(R.id.btn_add_addrs);
        btn_addr.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/myaddress/1/sign/aggregation/?uuid="+ Token.get(this);
        LReqEntity entity = new LReqEntity(url);

        //http://huihaowfx.huisou.com//huihao/myaddress/1/sign/aggregation/?uuid=6a35c1ed7255077d57d57be679048034
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }


    private void getJsonData(String data) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject result = jsonObject.getJSONObject("list");
                JSONArray array = result.getJSONArray("address_list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    AddressItemEntity itementity = new AddressItemEntity();
                    itementity.setId(object.getString("id"));
                    itementity.setUname(object.getString("uname"));
                    itementity.setUphone(object.getString("uphone"));
                    itementity.setProvince(object.getString("province"));
                    itementity.setCity(object.getString("city"));
                    itementity.setCountry(object.getString("country"));
                    itementity.setAddress(object.getString("address"));
                    list.add(itementity);
                }
                adapter = new ChooseAddressAdapter(Choose_Address.this, list,listView);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listView.setAdapter(adapter);

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


    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
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



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_addrs) {
            Intent intent = new Intent(this, Add_Address.class);
            startActivity(intent);
        }
    }
}
