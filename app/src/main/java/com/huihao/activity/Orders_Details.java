package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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
import android.widget.Toast;

import com.huihao.R;
import com.huihao.adapter.BuysNumAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.custom.CustomDialog;
import com.huihao.entity.AllOrderItemEntity;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/8/10.
 * 订单详情
 */
public class Orders_Details extends LActivity implements View.OnClickListener {
    private LinearLayout ly_wl, ll_send;
    private RelativeLayout rl_bm;
    private ListView listView;
    private TextView tv_state, tv_stime,
            tv_aname, tv_p, tv_addr, tv_waite,
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
     //   tv_stime = (TextView) findViewById(R.id.tv_qqt);

        tv_waite = (TextView) findViewById(R.id.tv_waite);
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
        ll_send = (LinearLayout) findViewById(R.id.ll_send);
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
        String url = res.getString(R.string.app_service_url) + "/huihao/orders/detail/1/sign/aggregation/?uuid=" + Token.get(this) + "&id=" + orderid;

        LReqEntity entitys = new LReqEntity(url);
       // L.e(entitys + "");
        ActivityHandler handler = new ActivityHandler(Orders_Details.this);
        handler.startLoadingData(entitys, 1);
    }

    private void getJsonData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject josno = jsonObject.getJSONObject("list");
                JSONObject jsd = josno.getJSONObject("order_base");
                String id = jsd.getString("id");//订单号
                tv_odnum.setText(id);//订单编号
                String state = jsd.getString("state");//状态值
                if (state.equals("1")) {
                    //待发货
                    tv_waite.setText("等待卖家发货");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.GONE);
                    ll_send.setVisibility(View.GONE);
                    tv_desc.setVisibility(View.GONE);
                } else if (state.equals("2")) {
                    //待收货
                    tv_waite.setText("卖家已发货");
                    ly_wl.setVisibility(View.VISIBLE);
                    rl_bm.setVisibility(View.VISIBLE);
                    ll_send.setVisibility(View.VISIBLE);
                    tv_desc.setVisibility(View.VISIBLE);
                } else if (state.equals("3")) {
                    tv_waite.setText("订单已完成");
                    ly_wl.setVisibility(View.VISIBLE);
                    rl_bm.setVisibility(View.GONE);
                    ll_send.setVisibility(View.VISIBLE);
                    tv_desc.setVisibility(View.VISIBLE);
                }
                String statename = jsd.getString("statename");//状态
                tv_stateo.setText(statename);
                String username = jsd.getString("username");
                tv_aname.setText("收货人:" + username);
                String address = jsd.getString("address_detail");
                tv_addr.setText("收货地址:" + address);
                String phone = jsd.getString("phone");
                tv_p.setText(phone);//收货人电话
                String buy_message = jsd.getString("buy_message");
                tv_talk.setText(buy_message);//买家留言
                String total_title = jsd.getString("total_title");
                String prefer_price = jsd.getString("prefer_price");//返利直减
                tv_zj.setText(prefer_price);
                String pay_price = jsd.getString("pay_price");//实付款
                tv_pay.setText(pay_price);
                String addtime = jsd.getString("addtime");     //   "addtime": "1440750211",
                String paytime = jsd.getString("paytime");      // "paytime": "2015-08-28 16:23",付款时间
                tv_paytime.setText(paytime);
                String sendtime = jsd.getString("sendtime");       // 发货时间
                tv_opent.setText(sendtime);
                String receivetime = jsd.getString("receivetime");      //   收货时间
                String payname = jsd.getString("payname");      //  付款方式
                String suretime = jsd.getString("您的订单已经发货，请在"+"suretime"+"前确认收货，超时订单将自动确认。");      //   确定收货时间
                String exressinfo = jsd.getString("exressinfo");       //  物流信息
                tv_state.setText(exressinfo);
                tv_desc.setText(suretime);
                JSONArray array = josno.getJSONArray("order_detail");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jo = array.getJSONObject(i);
                    AllOrderItemEntity iee = new AllOrderItemEntity();
                    iee.setId(jo.getString("id"));
                    iee.setTitle(jo.getString("title"));


                    iee.setSpec_1(jo.getString("spec_1"));
                    iee.setSpec_2(jo.getString("spec_2"));
                    if (jo.getString("spec_1").equals(null) || jo.getString("spec_1").equals("") || jo.getString("spec_1").equals("null")) {
                        iee.setTitle_1("");
                    } else {
                        iee.setTitle_1("规格1");
                    }
                    if (jo.getString("spec_2").equals(null) || jo.getString("spec_2").equals("") || jo.getString("spec_2").equals("null")) {
                        iee.setTitle_2("");
                    } else {
                        iee.setTitle_2("规格2");
                    }
                    iee.setSpec_id(jo.getString("spec_id"));
                    iee.setPicurl(jo.getString("picurl"));
                    iee.setBuynum(jo.getString("num"));
                    iee.setNprice(jo.getString("price"));
                    iee.setPreferential(jo.getString("newprice"));
                    itemlist.add(iee);
                }
                adapter = new BuysNumAdapter(this, itemlist);
                listView.setAdapter(adapter);
                scrollView.post(new Runnable() {
                    //让scrollview跳转到顶部，必须放在runnable()方法中
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, 0);
                    }
                });

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
                case 2:
                    getReData(msg.getStr());
                default:

                    break;
            }
        }
    }

    private void getReData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss("确认收货");
            } else {
                T.ss(jsonObject.getString("info").toString());
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String oids = tv_odnum.getText().toString();
        //物流签收状态
        if (id == R.id.ll_wl) {
            Intent intent = new Intent(this, MetailFlow_Detail.class);
            intent.putExtra("id", oids);
            startActivity(intent);
        }
        //联系客服
        if (id == R.id.btn_kf) {
            final CustomDialog alertDialog = new CustomDialog.Builder(this).
                    setMessage("确定联系客服吗？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "正在呼叫  " + "400-123-123",
                                    Toast.LENGTH_LONG)
                                    .show();
                            Uri uri = Uri.parse("tel:"
                                    +"400-123-123");
                            Intent intent = new Intent(
                                    Intent.ACTION_CALL, uri);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();


            alertDialog.show();


        }
        //确认收货
        if (id == R.id.btn_quer) {
            Resources res = getResources();
            String url = res.getString(R.string.app_service_url) + "/huihao/orders/receivegoods/1/sign/aggregation/";
            Map<String, String> map = new HashMap<String, String>();
            map.put("uuid", Token.get(this));
            map.put("id", oids);
            LReqEntity entitys = new LReqEntity(url);
            ActivityHandler handler = new ActivityHandler(Orders_Details.this);
            handler.startLoadingData(entitys, 2);
        }
    }
}
