package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.huihao.R;
import com.huihao.common.PayResult;
import com.huihao.common.SignUtils;
import com.huihao.common.SystemBarTintManager;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_shop;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by huisou on 2015/8/7.
 */
public class Pay_Successed extends LActivity implements View.OnClickListener {
    private TextView tv_pay_name, tv_pay_phone, tv_pay_addr, tv_pay_money;
    private Button btn_xq, btn_bb;
    private String orderid;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_pay_successed);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
//        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("支付成功");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        tv_pay_name = (TextView) findViewById(R.id.tv_pay_name);
        tv_pay_phone = (TextView) findViewById(R.id.tv_pay_phone);
        tv_pay_addr = (TextView) findViewById(R.id.tv_pay_addr);
        tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
        btn_xq = (Button) findViewById(R.id.btn_xq);//查看详情
        btn_bb = (Button) findViewById(R.id.btn_bb);//返回首页
        btn_xq.setOnClickListener(this);
        btn_bb.setOnClickListener(this);

    }
    private void initData(){
        String name=getIntent().getExtras().getString("addrname");
        String phone=getIntent().getExtras().getString("addrphone");
        String addr=getIntent().getExtras().getString("addrs");
        String price=getIntent().getExtras().getString("price");
        orderid=getIntent().getExtras().getString("orderid");
        tv_pay_name.setText(name);
        tv_pay_phone.setText(phone);
        tv_pay_addr.setText(addr);
        tv_pay_money.setText(price);
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
        //查看详情--订单详情
        if (id == R.id.btn_xq) {
            //T.ss("订单详情");
            Intent intent = new Intent(this, Orders_Details.class);
            startActivity(intent);
        }
        //返回首页
        if (id == R.id.btn_bb) {
            T.ss("返回首页");
            Intent intent = new Intent(this, HomeMain.class);
            startActivity(intent);
            finish();
            Submit_Orders.instance.finish();
            Fragment_shop.instance.getActivity().finish();
        }
    }
}
