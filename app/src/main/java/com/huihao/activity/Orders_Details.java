package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.BuysNumAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.AllOrderItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 * 订单详情
 */
public class Orders_Details extends LActivity implements View.OnClickListener {
    private LinearLayout ly_wl;
    private RelativeLayout rl_bm;
    private ListView listView;
    private TextView tv_state, tv_stime,
            tv_aname, tv_p, tv_addr,
            tv_talk, tv_yunf, tv_zj,
            tv_pay, tv_stateo, tv_odnum,
            tv_paytime, tv_opent, tv_desc;
    private Button btn_kf, btn_suok;
    private ScrollView scrollView;

    private BuysNumAdapter adapter;
    private List<AllOrderItemEntity> itemlist = null;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_orders_details);
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
        toolbar.setTitle("订单详情");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        scrollView = (ScrollView) findViewById(R.id.eeee);
        /**
         *物流状态判断，这个需要值判断是否隐藏
         */
        ly_wl = (LinearLayout) findViewById(R.id.ll_wl);//物流状态
        ly_wl.setOnClickListener(this);
        tv_state = (TextView) findViewById(R.id.tv_qqs);
        tv_stime = (TextView) findViewById(R.id.tv_qqt);

        /**
         * 收货地址
         */
        tv_aname = (TextView) findViewById(R.id.tv_amenn);//名字的要求tv_aname.setText("收货人"+);
        tv_p = (TextView) findViewById(R.id.tv_amp);//电话
        tv_addr = (TextView) findViewById(R.id.tv_addrsa);//收货地址要求tv_addr.setText("收货地址"+)
        /**
         * 买家留言
         */
        tv_talk = (TextView) findViewById(R.id.tv_maij);//买家留言

        listView = (ListView) findViewById(R.id.order_ilistvew);//物品件数

        tv_yunf = (TextView) findViewById(R.id.tv_ym);//运费
        tv_zj = (TextView) findViewById(R.id.tv_fj);//返利直减
        tv_pay = (TextView) findViewById(R.id.tv_yh);//实付款
        tv_stateo = (TextView) findViewById(R.id.tv_orderss);//订单状态
        tv_odnum = (TextView) findViewById(R.id.tv_ordernn);//订单编号
        tv_paytime = (TextView) findViewById(R.id.tv_ordertt);//付款时间
        /**
         * 在没有发货的状态下，发货时间和描述需要隐藏
         */
        tv_opent = (TextView) findViewById(R.id.tv_orderttt); //发货时间tv_orderttt
        tv_desc = (TextView) findViewById(R.id.tv_oms);//描述tv_oms

        /**
         * 底部判断，有时需要隐藏
         */
        rl_bm = (RelativeLayout) findViewById(R.id.bottom);//底部判断需要隐藏
        btn_kf = (Button) findViewById(R.id.btn_kf);//联系客服btn_kf
        btn_suok = (Button) findViewById(R.id.btn_quer);//确认收货btn_quer

        btn_kf.setOnClickListener(this);
        btn_suok.setOnClickListener(this);

    }

    private void initData() {
        String orderid = getIntent().getExtras().getString("orderid");
        itemlist = new ArrayList<AllOrderItemEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/orders/detail/1/sign/aggregation/?uuid=" + UsErId.uuid + "&id=" + "2008080882";//orderid;

        LReqEntity entitys = new LReqEntity(url);
        L.e(entitys + "");
        ActivityHandler handler = new ActivityHandler(Orders_Details.this);
        handler.startLoadingData(entitys, 1);
//        AllOrderItemEntity iee = new AllOrderItemEntity();
//        adapter = new BuysNumAdapter(this, itemlist);
//        listView.setAdapter(adapter);
//        scrollView.post(new Runnable() {
//            //让scrollview跳转到顶部，必须放在runnable()方法中
//            @Override
//            public void run() {
//                scrollView.scrollTo(0, 0);
//            }
//        });
    }

    private void getJsonData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject josno = jsonObject.getJSONObject("list");
                JSONObject jsd = josno.getJSONObject("order_base");
                String id = jsd.getString("id");
                String wid = jsd.getString("wid");
                String uid = jsd.getString("uid");
                String username = jsd.getString("username");
                tv_aname.setText("收货人:" + username);
                String address = jsd.getString("address");
                tv_addr.setText("收货地址:" + address);
                String phone = jsd.getString("phone");
                tv_p.setText(phone);//收货人电话
                String post_id = jsd.getString("post_id");
                String buy_message = jsd.getString("buy_message");
                tv_talk.setText(buy_message);
                String total_title = jsd.getString("total_title");
                String total_price = jsd.getString("total_price");
                String product_price = jsd.getString("product_price");
                String exress_weight = jsd.getString("exress_weight");
                String post_price = jsd.getString("post_price");
                String exress_id = jsd.getString("exress_id");
                String exress_no = jsd.getString("exress_no");
                String prefer_id = jsd.getString("prefer_id");
                String prefer_price = jsd.getString("prefer_price");
                String balance_price = jsd.getString("balance_price");
                String pay_price = jsd.getString("pay_price");
                String payment = jsd.getString("payment");//  payment": "3",
                String trade_no = jsd.getString("payment"); //  "trade_no": null,
                String openid = jsd.getString("openid");   //       "openid": null,
                String sale_message = jsd.getString("sale_message");   //      "sale_message": null,
                String addtime = jsd.getString("addtime");     //   "addtime": "1440750211",
                String paytime = jsd.getString("paytime");      // "paytime": "2015-08-28 16:23",
                String sendtime = jsd.getString("sendtime");       // "sendtime": "",
                String receivetime = jsd.getString("receivetime");      //   "receivetime": null,
                String ctime = jsd.getString("ctime");      //   "ctime": "1440750211",
                String etime = jsd.getString("etime");       //    "etime": "1440750211",
                String is_app = jsd.getString("is_app");       //    "is_app": "1",
                String duser_id = jsd.getString("duser_id");     //     "duser_id": "0",
                String coupon_arr = jsd.getString("coupon_arr");    //     "coupon_arr": "30.00",
                String statename = jsd.getString("statename");       //  "statename": "未支付",
                String payname = jsd.getString("payname");      //  "payname": "线下支付",
                String address_detail = jsd.getString("address_detail");      //   "address_detail": "﻿北京﻿北京﻿北京24567777",
                String suretime = jsd.getString("suretime");      //   "suretime": "",
                String exressinfo = jsd.getString("exressinfo");       //   "exressinfo": null
                T.ss("保存成功！");
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
            switch (requestId) {
                case 1:
                    getJsonData(msg.getStr());
                    break;
                default:

                    break;
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
        int id = v.getId();
        //物流签收状态
        if (id == R.id.ll_wl) {
            Intent intent = new Intent(this, MetailFlow_Detail.class);
            startActivity(intent);
        }
        //联系客服
        if (id == R.id.btn_kf) {
            T.ss("联系客服");
        }
        //确认收货
        if (id == R.id.btn_quer) {
            T.ss("确认收货");
        }
    }
}
