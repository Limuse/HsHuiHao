package com.huihao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.huihao.R;
import com.huihao.activity.Address;
import com.huihao.activity.All_Orders;
import com.huihao.activity.ExtractActivity;
import com.huihao.activity.More;
import com.huihao.activity.My_Coupons;
import com.huihao.activity.My_Partner;
import com.huihao.activity.My_Rebate;
import com.huihao.activity.News;
import com.huihao.activity.PersonSet;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;
import com.readystatesoftware.viewbadger.BadgeView;

import org.w3c.dom.Text;

import static android.view.View.VISIBLE;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_my extends LFragment implements View.OnClickListener {
    private View parentView;

    private ImageView person_image;
    private ImageView emails;
    private TextView tv_name;
    private TextView btn_1, btn_2, btn_3, tv_1, tv_2, tv_3;
    private RelativeLayout rl_news,rl_image, rl_order, rl_address, rl_account, rl_rebate, rl_partner, rl_more, rl_parper, rl_tv1, rl_tv2, rl_tv3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_my,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        person_image = (ImageView) getActivity().findViewById(R.id.myself_image);
        emails = (ImageView) getActivity().findViewById(R.id.person_emlss);//消息
        rl_news=(RelativeLayout)getActivity().findViewById(R.id.person_emls);
        rl_image = (RelativeLayout) getActivity().findViewById(R.id.myself_image_rl);//头像部分
        tv_name = (TextView) getActivity().findViewById(R.id.myself_name);
        btn_1 = (TextView) getActivity().findViewById(R.id.person_no_pay);//待付款
        btn_2 = (TextView) getActivity().findViewById(R.id.person_rec);//待收货
        btn_3 = (TextView) getActivity().findViewById(R.id.person_back_money);//退款中
        tv_1 = (TextView) getActivity().findViewById(R.id.tv_1);
        tv_2 = (TextView) getActivity().findViewById(R.id.tv_2);
        tv_3 = (TextView) getActivity().findViewById(R.id.tv_3);
        rl_order = (RelativeLayout) getActivity().findViewById(R.id.orders);
        rl_address = (RelativeLayout) getActivity().findViewById(R.id.address);
        rl_account = (RelativeLayout) getActivity().findViewById(R.id.account);
        rl_rebate = (RelativeLayout) getActivity().findViewById(R.id.rebate);
        rl_partner = (RelativeLayout) getActivity().findViewById(R.id.partner);
        rl_more = (RelativeLayout) getActivity().findViewById(R.id.more);
        rl_parper = (RelativeLayout) getActivity().findViewById(R.id.parper);
        rl_tv1 = (RelativeLayout) getActivity().findViewById(R.id.rl_tv1);
        rl_tv2 = (RelativeLayout) getActivity().findViewById(R.id.rl_tv2);
        rl_tv3 = (RelativeLayout) getActivity().findViewById(R.id.rl_tv3);
        person_image.setOnClickListener(this);
        rl_news.setOnClickListener(this);
        rl_tv1.setOnClickListener(this);
        rl_tv2.setOnClickListener(this);
        rl_tv3.setOnClickListener(this);


        rl_order.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_rebate.setOnClickListener(this);
        rl_partner.setOnClickListener(this);
        rl_more.setOnClickListener(this);
        rl_image.setOnClickListener(this);
        rl_parper.setOnClickListener(this);
        tv_1.setVisibility(View.VISIBLE);
        tv_2.setVisibility(View.VISIBLE);
        tv_3.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //消息
        if (id == R.id.person_emls) {
            T.ss("跳到消息的页面");
            Intent intent = new Intent(getActivity(), News.class);
            getActivity().startActivity(intent);
        }
        //头像
        if (id == R.id.myself_image) {
            Intent intent = new Intent(getActivity(), PersonSet.class);
            getActivity().startActivity(intent);
        }

        //待付款
        if (id == R.id.rl_tv1) {
            T.ss("待付款");
            //tv_1.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), All_Orders.class);
            intent.putExtra("gets", "1");
            getActivity().startActivity(intent);
        }
        //待收货
        if (id == R.id.rl_tv2) {
            T.ss("待收货");
            // tv_2.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), All_Orders.class);
            intent.putExtra("gets", "2");
            getActivity().startActivity(intent);
        }
        //退款中
        if (id == R.id.rl_tv3) {
            T.ss("退款中");
            //  tv_3.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), All_Orders.class);
            intent.putExtra("gets", "3");
            getActivity().startActivity(intent);
        }
        //全部订单
        if (id == R.id.orders) {
            Intent intent = new Intent(getActivity(), All_Orders.class);
            intent.putExtra("gets", "0");
            getActivity().startActivity(intent);
        }
        //收货地址
        if (id == R.id.address) {
            // T.ss("收货地址");
            Intent intent = new Intent(getActivity(), Address.class);
            getActivity().startActivity(intent);
        }
        //我的优惠卷
        if (id == R.id.parper) {
            // T.ss("我的优惠卷");
            Intent intent = new Intent(getActivity(), My_Coupons.class);
            getActivity().startActivity(intent);
        }
        //提现账户
        if (id == R.id.account) {
            // T.ss("提现账户");
            Intent intent = new Intent(getActivity(), ExtractActivity.class);
            getActivity().startActivity(intent);
        }
        //我的返利
        if (id == R.id.rebate) {
            //  T.ss("我的返利");
            Intent intent = new Intent(getActivity(), My_Rebate.class);
            getActivity().startActivity(intent);
        }
        //我的伙伴
        if (id == R.id.partner) {
            //T.ss("我的伙伴");
            Intent intent = new Intent(getActivity(), My_Partner.class);
            getActivity().startActivity(intent);
        }
        //更多
        if (id == R.id.more) {
            // T.ss("更多");
            Intent intent = new Intent(getActivity(), More.class);
            getActivity().startActivity(intent);
        }
    }
}
