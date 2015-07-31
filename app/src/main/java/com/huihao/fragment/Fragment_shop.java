package com.huihao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.HorizontalSlideAdapter;
import com.huihao.common.Log;
import com.huihao.custom.HorizontalSlideDeleteListView;
import com.huihao.entity.ShopItemEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_shop extends LFragment implements View.OnClickListener {
    private View parentView;
    private ListView listview;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = (Toolbar) parentView.findViewById(R.id.toolbar);
        toolbar.setTitle("购物车");
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitleTextColor(R.color.app_text_dark);

        initData();
        initView();

    }


    private void initView() {

        listview = (ListView) getActivity().findViewById(R.id.lv_list_shop);
        tv_all_choose = (TextView) getActivity().findViewById(R.id.tv_all_choose);
        tv_all_money = (TextView) getActivity().findViewById(R.id.tv_all_money);
        cb_all_cbx = (CheckBox) getActivity().findViewById(R.id.cb_all_checkbox);
        btn_all_js = (Button) getActivity().findViewById(R.id.btn_all_jesuan);
        tv_all_choose.setOnClickListener(this);
        cb_all_cbx.setOnClickListener(this);
        btn_all_js.setOnClickListener(this);
        adapter = new HorizontalSlideAdapter(getActivity(), list);
        listview.setAdapter(adapter);

    }

    private void initData() {
        list = new ArrayList<ShopItemEntity>();

        for (int i = 0; i < 50; i++) {
            ShopItemEntity be = new ShopItemEntity
                    ("把根留住火麻茶麸洗发露洗发露" + i,
                    "",
                    "绿色" + i,
                    "M" + i,
                    "水晶" + i,
                   1,
                    "223");
            list.add(be);
        }


    }

    @Override
    public void onClick(View v) {
        /**
         * 点击全选
         */
        if (v == tv_all_choose) {
            if (cb_all_cbx.isChecked() == false) {
                cb_all_cbx.setChecked(true);
                adapter.configCheckMap(true);
                String MONEY=adapter.getMoney();
                tv_all_money.setText(MONEY);
                adapter.notifyDataSetChanged();
                tv_all_choose.setText("全不选");


            } else if (cb_all_cbx.isChecked() == true) {
                cb_all_cbx.setChecked(false);
                adapter.configCheckMap(false);
                adapter.notifyDataSetChanged();
                tv_all_choose.setText("全选");

            }
        }
        /**
         * 点击全选的checkbox
         */
        if (v == cb_all_cbx) {
            if (tv_all_choose.getText().toString().equals("全选")) {
                cb_all_cbx.setChecked(true);
                adapter.configCheckMap(true);
                String MONEY=adapter.getMoney();
                tv_all_money.setText(MONEY);
                adapter.notifyDataSetChanged();
                tv_all_choose.setText("全不选");

            } else if (tv_all_choose.getText().toString().equals("全不选")) {
                cb_all_cbx.setChecked(false);
                adapter.configCheckMap(false);
                adapter.notifyDataSetChanged();
                tv_all_choose.setText("全选");
            }
        }
        /**
         * 点击结算
         */
        if (v == btn_all_js) {
            /**
             * 跳转到结算的下个页面
             */
//            Intent intent=new Intent(getActivity(),);
//            getActivity().startActivity(intent);
        }
    }


}
