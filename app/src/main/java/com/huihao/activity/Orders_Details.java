package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.BuysNumAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.AllOrderItemEntity;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

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
        itemlist = new ArrayList<AllOrderItemEntity>();
        AllOrderItemEntity iee = new AllOrderItemEntity();
//        iee.idss = 1;
//        iee.atitle = "洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水";
//        iee.acolor = "黑色";
//        iee.asize = "M";
//        iee.metails = "水晶";
//        iee.amoney = "222";
//        iee.oldm = "109";
//        iee.numss = "1";
//        itemlist.add(iee);

        iee.idss = 1;
        iee.atitle = "洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水";
        iee.acolor = "黑色";
        iee.asize = "M";
        iee.metails = "水晶";
        iee.amoney = "222";
        iee.oldm = "109";
        iee.numss = "1";
        itemlist.add(iee);
        AllOrderItemEntity iee1 = new AllOrderItemEntity();
        iee1.idss = 1;
        iee1.atitle = "洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水";
        iee1.acolor = "黑色";
        iee1.asize = "M";
        iee1.metails = "水晶";
        iee1.amoney = "222";
        iee1.oldm = "109";
        iee1.numss = "1";
        itemlist.add(iee1);
        adapter = new BuysNumAdapter(this, itemlist);
        listView.setAdapter(adapter);
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
