package com.huihao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.huihao.R;
import com.huihao.activity.ExtractActivity;
import com.huihao.activity.My_Coupons;
import com.huihao.activity.PersonSet;
import com.huihao.common.Bar;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;
import com.readystatesoftware.viewbadger.BadgeView;

import org.w3c.dom.Text;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_my extends LFragment implements View.OnClickListener {
    private View parentView;

    private ImageView person_image;
    private ImageView emails;
    private TextView tv_name;
    private TextView btn_1, btn_2, btn_3;
    private RelativeLayout rl_image, rl_order, rl_address, rl_account, rl_rebate, rl_partner, rl_more, rl_parper;

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
        emails = (ImageView) getActivity().findViewById(R.id.person_emls);//消息
        rl_image = (RelativeLayout) getActivity().findViewById(R.id.myself_image_rl);//头像部分
        tv_name = (TextView) getActivity().findViewById(R.id.myself_name);
        btn_1 = (TextView) getActivity().findViewById(R.id.person_no_pay);//待付款
        btn_2 = (TextView) getActivity().findViewById(R.id.person_rec);//待收货
        btn_3 = (TextView) getActivity().findViewById(R.id.person_back_money);//退款中
        rl_order = (RelativeLayout) getActivity().findViewById(R.id.orders);
        rl_address = (RelativeLayout) getActivity().findViewById(R.id.address);
        rl_account = (RelativeLayout) getActivity().findViewById(R.id.account);
        rl_rebate = (RelativeLayout) getActivity().findViewById(R.id.rebate);
        rl_partner = (RelativeLayout) getActivity().findViewById(R.id.partner);
        rl_more = (RelativeLayout) getActivity().findViewById(R.id.more);
        rl_parper = (RelativeLayout) getActivity().findViewById(R.id.parper);

        person_image.setOnClickListener(this);
        emails.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        BadgeView badge1 = new BadgeView(getActivity(), btn_1);//附着在这个Button上面一个数字显示
        badge1.setText("1");
        badge1.setBadgeMargin(60, 10);
        badge1.toggle();//显示这个数字控件

        BadgeView badge2 = new BadgeView(getActivity(), btn_2);//附着在这个Button上面一个数字显示
        badge2.setText("1");
        badge2.setBadgeMargin(55, 8);
        badge2.toggle();//显示这个数字控件


        rl_order.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_rebate.setOnClickListener(this);
        rl_partner.setOnClickListener(this);
        rl_more.setOnClickListener(this);
        rl_image.setOnClickListener(this);
        rl_parper.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //消息
        if (id == R.id.person_emls) {
            T.ss("跳到消息的页面");
        }
        if (id == R.id.myself_image_rl) {

        }
        if (id == R.id.myself_image) {
            Intent intent = new Intent(getActivity(), PersonSet.class);
            getActivity().startActivity(intent);
        }
        if (id == R.id.person_no_pay) {
            T.ss("待付款");
        }
        if (id == R.id.person_rec) {
            T.ss("待收货");
        }
        if (id == R.id.person_back_money) {
            T.ss("退款中");
        }
        if (id == R.id.orders) {
            T.ss("全部订单");
        }
        if (id == R.id.address) {
            T.ss("收货地址");
        }
        if (id == R.id.parper) {
            T.ss("我的优惠卷");
            Intent intent = new Intent(getActivity(), My_Coupons.class);
            getActivity().startActivity(intent);
        }
        if (id == R.id.account) {
            T.ss("提现账户");
            Intent intent = new Intent(getActivity(), ExtractActivity.class);
            getActivity().startActivity(intent);
        }
        if (id == R.id.rebate) {
            T.ss("我的返利");
        }
        if (id == R.id.partner) {
            T.ss("我的伙伴");
        }
        if (id == R.id.more) {
            T.ss("更多");
        }
    }
}
