package com.huihao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.activity.Submit_Orders;
import com.huihao.adapter.HorizontalSlideAdapter;
import com.huihao.custom.SlideListView2;
import com.huihao.entity.ShopItemEntity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_shop extends LFragment implements View.OnClickListener {
    private View parentView;
    private SlideListView2 listview;
    private TextView tv_all_choose, tv_all_money;
    private CheckBox cb_all_cbx;
    private Button btn_all_js;

    private List<ShopItemEntity> list;// = new ArrayList<ShopItemEntity>();

    private HorizontalSlideAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_shop,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = (Toolbar) parentView.findViewById(R.id.toolbar);
        toolbar.setTitle("购物车");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        initData();
        initView();

    }


    private void initView() {

        listview = (SlideListView2) getActivity().findViewById(R.id.lv_list_shop);
        listview.initSlideMode(SlideListView2.MOD_BOTH);
        tv_all_choose = (TextView) getActivity().findViewById(R.id.tv_all_choose);
        tv_all_money = (TextView) getActivity().findViewById(R.id.tv_all_money);
        cb_all_cbx = (CheckBox) getActivity().findViewById(R.id.cb_all_checkbox);
        btn_all_js = (Button) getActivity().findViewById(R.id.btn_all_jesuan);
        // tv_all_choose.setOnClickListener(this);
        // cb_all_cbx.setOnClickListener(this);
        btn_all_js.setOnClickListener(this);
        tv_all_money.setText("0.00");
        adapter = new HorizontalSlideAdapter(getActivity(), list, listview);
        adapter.setOnNumChangeListener(new HorizontalSlideAdapter.OnNumChangeListener() {

            @Override
            public void OnNumJiaChange(float jiage) {
                if (tv_all_money.getText().equals(null)) {
                    tv_all_money.setText(jiage + "");
                } else {
                    float a = Float.parseFloat(tv_all_money.getText().toString());

                    tv_all_money.setText(a + jiage + "");
                }

            }

            @Override
            public void OnNumJianChange(float jiage) {
                float a = Float.parseFloat(tv_all_money.getText().toString());

                tv_all_money.setText(a - jiage + "");

            }

        });
        listview.setAdapter(adapter);


        cb_all_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float allMoney = 0;
                List<ShopItemEntity> newlist = new ArrayList<ShopItemEntity>();
                if (isChecked) {
                    for (ShopItemEntity e : adapter.getList()) {
                        e.setIsCheck(true);
                        allMoney += e.getNum() * e.getDanjia();
                        newlist.add(e);
                        tv_all_choose.setText("全不选");

                    }

                } else {
                    for (ShopItemEntity e : adapter.getList()) {
                        e.setIsCheck(false);
                        newlist.add(e);
                        tv_all_choose.setText("全选");

                    }
                }

                adapter.updateData(newlist);
                tv_all_money.setText(allMoney + "");

            }
        });

    }

    private void initData() {
        list = new ArrayList<ShopItemEntity>();

        for (int i = 0; i < 50; i++) {
            ShopItemEntity be = new ShopItemEntity
                    (i, "把根留住火麻茶麸洗发露洗发露" + i,
                            "",
                            "绿色" + i,
                            "M" + i,
                            "水晶" + i,
                            1,
                            "223", false, 223.0f);
            list.add(be);
        }


    }

    @Override
    public void onClick(View v) {
//        /**
//         * 点击全选
//         */
//        if (v == tv_all_choose) {
//            if (cb_all_cbx.isChecked() == false) {
//                cb_all_cbx.setChecked(true);
//                adapter.configCheckMap(true);
//                float MONEY = adapter.getMoney();
//            //    T.ss(MONEY + "");
//                tv_all_money.setText(MONEY+"");
//                adapter.notifyDataSetChanged();
//                tv_all_choose.setText("全不选");
//
//
//            } else if (cb_all_cbx.isChecked() == true) {
//                cb_all_cbx.setChecked(false);
//                adapter.configCheckMap(false);
//                adapter.notifyDataSetChanged();
//                tv_all_money.setText("0.00");
//                tv_all_choose.setText("全选");
//
//            }
//        }
//        /**
//         * 点击全选的checkbox
//         */
//        if (v == cb_all_cbx) {
//            if (tv_all_choose.getText().toString().equals("全选")) {
//                cb_all_cbx.setChecked(true);
//                adapter.configCheckMap(true);
//                float MONEY = adapter.getMoney();
//                tv_all_money.setText(MONEY+"");
//                adapter.notifyDataSetChanged();
//                tv_all_choose.setText("全不选");
//
//            } else if (tv_all_choose.getText().toString().equals("全不选")) {
//                cb_all_cbx.setChecked(false);
//                adapter.configCheckMap(false);
//                tv_all_money.setText("0.00");
//                adapter.notifyDataSetChanged();
//                tv_all_choose.setText("全选");
//            }
//        }
        /**
         * 点击结算
         */
        if (v == btn_all_js) {
            /**
             * 跳转到结算的下个页面
             */
            Intent intent = new Intent(getActivity(), Submit_Orders.class);
            getActivity().startActivity(intent);
        }
    }


}
