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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.ChooseCouponsAdapter;
import com.huihao.adapter.CouponsAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.CouponsEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/6.
 */
public class Choose_Couppons extends LActivity {
    private ListView listview;
    private ChooseCouponsAdapter adapter;
    private List<CouponsEntity> list = null;
    private LinearLayout headview;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_choose_coupons);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("我的优惠卷");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
        //右边图片点击事件
        toolbar.inflateMenu(R.menu.right_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    if (list.size()==0) {
                        T.ss("暂时没有优惠卷哦！");
                    } else {
                        L.e("kkkkkkkk"+adapter.rCid().toString()+"ddddddd");
                        String cids = adapter.rCid();
                        int mon = adapter.rMoneys();
                        if (cids == "null") {
                            T.ss("请选择优惠卷");
                            setResult(1, null);
                            finish();
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("cids", cids);
                            intent.putExtra("money", mon + "");
                            setResult(1, intent);
                            finish();
                        }
                    }
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        listview = (ListView) findViewById(R.id.lv_couponss);
        headview = (LinearLayout) LayoutInflater.from(Choose_Couppons.this).inflate(R.layout.listview_head, null);
        listview.addHeaderView(headview);
        initData();
    }

    private void initData() {
        list = new ArrayList<CouponsEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/coupon/1/sign/aggregation/?uuid=" + Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        //http://huihaowfx.huisou.com/uuid=6a35c1ed7255077d57d57be679048034
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
                if (jsonObject.getString("list").equals(null) || jsonObject.getString("list").equals("") || jsonObject.getString("list").equals("null")) {
                    T.ss("没有数据");

                } else {
                    JSONArray array = jsonObject.getJSONArray("list");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        CouponsEntity entity = new CouponsEntity();
                        entity.cpmoney = object.getString("money");
                        entity.cptitile = "汇好商城优惠卷";
                        String tme = object.getString("end_time").substring(0, 10);
                        entity.cptime = "使用期限" + tme + "前";
                        // entity.cpuse = "满800可用";
                        entity.cpid = object.getString("id");
                        entity.t = -1;
                        list.add(entity);
                    }
                    adapter = new ChooseCouponsAdapter(this, list);
                    listview.setAdapter(adapter);

                }
            } else {
                T.ss(jsonObject.getString("info").toString());
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
}
