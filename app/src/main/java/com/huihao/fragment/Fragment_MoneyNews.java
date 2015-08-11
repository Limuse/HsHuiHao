package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.MoneyNewsAdapter;
import com.huihao.entity.MoneyNewsEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 * 财富消息
 */
public class Fragment_MoneyNews extends LFragment {
    private ListView listView;
    private MoneyNewsAdapter adapter;
    private List<MoneyNewsEntity> list = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moneynews, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) getActivity().findViewById(R.id.lv_money);
    }

    private void initData() {
        list = new ArrayList<MoneyNewsEntity>();
        MoneyNewsEntity entity = new MoneyNewsEntity();
        entity.time = "2015年5月12日 13:12:30";
        entity.num = "12345124531";
        entity.money = "45";
        list.add(entity);
        MoneyNewsEntity entity1 = new MoneyNewsEntity();
        entity1.time = "2015年5月12日 13:12:30";
        entity1.num = "12345124531";
        entity1.money = "45";
        list.add(entity1);
        MoneyNewsEntity entity2 = new MoneyNewsEntity();
        entity2.time = "2015年5月12日 13:12:30";
        entity2.num = "12345124531";
        entity2.money = "45";
        list.add(entity2);
        MoneyNewsEntity entity3 = new MoneyNewsEntity();
        entity3.time = "2015年5月12日 13:12:30";
        entity3.num = "12345124531";
        entity3.money = "45";
        list.add(entity3);
        MoneyNewsEntity entity4 = new MoneyNewsEntity();
        entity4.time = "2015年5月12日 13:12:30";
        entity4.num = "12345124531";
        entity4.money = "45";
        list.add(entity4);
        adapter = new MoneyNewsAdapter(getActivity(), list);
        listView.setAdapter(adapter);


    }


    public static Fragment_MoneyNews newInstance() {
        Fragment_MoneyNews fragment = new Fragment_MoneyNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
