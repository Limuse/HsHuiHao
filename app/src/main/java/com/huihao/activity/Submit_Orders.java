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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.AllOrderAdapter;
import com.huihao.adapter.BuysNumAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.AllOrderItemEntity;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/7.
 * 提交订单
 */
public class Submit_Orders extends LActivity implements View.OnClickListener {
    private ListView listView;
    private LinearLayout ly_alladdr;
    private RelativeLayout rl_ano, rl_cu, rl_zf;
    private EditText et_please_num;
    private TextView tv_ym, tv_fj, tv_snum, tv_js, tv_azf, tv_yh;
    private Button btn_alljs;

    private BuysNumAdapter adapter;
    private List<AllOrderItemEntity> itemlist = null;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_submit_orders);
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
        toolbar.setTitle("提交订单");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        ly_alladdr = (LinearLayout) findViewById(R.id.ordersss);//有地址
        rl_ano = (RelativeLayout) findViewById(R.id.rl_a);//没有地址
        rl_cu = (RelativeLayout) findViewById(R.id.rl_cu);//优惠卷
        rl_zf = (RelativeLayout) findViewById(R.id.rl_zf);//支付方式
        et_please_num = (EditText) findViewById(R.id.et_please_num);//给卖家留言
        tv_ym = (TextView) findViewById(R.id.tv_ym);//运费
        tv_fj = (TextView) findViewById(R.id.tv_fj);//返利直减
        tv_yh = (TextView) findViewById(R.id.tv_yh);//可以优惠的钱
        tv_snum = (TextView) findViewById(R.id.tv_snus);//商品总件数
        tv_js = (TextView) findViewById(R.id.tv_js);//总费用
        tv_azf = (TextView) findViewById(R.id.tv_azf);//结算总费用
        btn_alljs = (Button) findViewById(R.id.btn_alljs);//结算
        listView = (ListView) findViewById(R.id.order_ilistvew);

        ly_alladdr.setOnClickListener(this);
        rl_ano.setOnClickListener(this);
        rl_cu.setOnClickListener(this);
        rl_zf.setOnClickListener(this);
        btn_alljs.setOnClickListener(this);


    }

    private void initData() {
        itemlist = new ArrayList<AllOrderItemEntity>();
        AllOrderItemEntity iee = new AllOrderItemEntity();
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
        //有地址
        if (id == R.id.ordersss) {
            Intent intent = new Intent(this, Choose_Address.class);
            startActivity(intent);
        }
        //没有地址
        if (id == R.id.rl_a) {
            Intent intent = new Intent(this, Choose_Address.class);
            startActivity(intent);
        }

        //优惠卷
        if (id == R.id.rl_cu) {
            Intent intent = new Intent(this, Choose_Couppons.class);
            startActivity(intent);
        }
        //支付方式
        if (id == R.id.rl_zf) {

        }
        //结算
        if (id == R.id.btn_alljs) {
            Intent intent = new Intent(this, Pay_Successed.class);
            startActivity(intent);

        }
    }
}
