package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.OrdersNewsAdapter;
import com.huihao.entity.OrdersNewsEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 * 订单消息
 */
public class Fragment_OrderNews extends LFragment {
    private ListView listView;
    private OrdersNewsAdapter adapter;
    private List<OrdersNewsEntity> list = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ordersnews, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) getActivity().findViewById(R.id.lv_orders);
    }

    private void initData() {
        list = new ArrayList<OrdersNewsEntity>();
        OrdersNewsEntity entity = new OrdersNewsEntity();
        entity.state = 1;
        entity.time = "2015年6月25日 8:20:00";
        entity.tjtime = "2015年6月26日 8:20:00";
        entity.khinfo = "浙江省杭州市 张小江";
        entity.ordermoney = "11,900.00";
        entity.yjmoney = "100.00";
        list.add(entity);
        OrdersNewsEntity entity1 = new OrdersNewsEntity();
        entity1.state = 0;
        entity1.time = "2015年6月25日 8:20:00";
        entity1.tjtime = "2015年6月26日 8:20:00";
        entity1.khinfo = "浙江省杭州市 张小江";
        entity1.ordermoney = "11,900.00";
        entity1.yjmoney = "100.00";
        list.add(entity1);
        adapter = new OrdersNewsAdapter(getActivity(), list);
        listView.setAdapter(adapter);

    }

    public static Fragment_OrderNews newInstance() {
        Fragment_OrderNews fragment = new Fragment_OrderNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
