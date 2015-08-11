package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.AllOrderAdapter;
import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/7/31.
 * 全部订单
 */
public class Fragment_Orders extends LFragment {
    private ListView listView;
    private List<AllOrderEntity> list = null;
    private AllOrderAdapter adapter;
    private List<AllOrderItemEntity> itemlist = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {

        listView = (ListView) getView().findViewById(R.id.lv_showall);
    }

    private void initData() {
        list=new ArrayList<AllOrderEntity>();
        AllOrderEntity ee=new AllOrderEntity();
        ee.aid=1;
        ee.number="12965376343";
        ee.astate=0;
        ee.allmoney="98";
        list.add(ee);
        AllOrderEntity ee1=new AllOrderEntity();
        ee1.aid=2;
        ee1.number="12965376343";
        ee1.astate=1;
        ee1.allmoney="98";
        list.add(ee1);
        AllOrderEntity ee2=new AllOrderEntity();
        ee2.aid=3;
        ee2.number="12965376343";
        ee2.astate=2;
        ee2.allmoney="98";
        list.add(ee2);
        AllOrderEntity ee3=new AllOrderEntity();
        ee3.aid=3;
        ee3.number="12965376343";
        ee3.astate=3;
        ee3.allmoney="98";
        list.add(ee3);
        AllOrderEntity ee4=new AllOrderEntity();
        ee4.aid=4;
        ee4.number="12965376343";
        ee4.astate=4;
        ee4.allmoney="98";
        list.add(ee4);
        itemlist=new ArrayList<AllOrderItemEntity>();
        AllOrderItemEntity iee=new AllOrderItemEntity();
        iee.idss=1;
        iee.atitle="洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水";
        iee.acolor="黑色";
        iee.asize="M";
        iee.metails="水晶";
        iee.amoney="222";
        iee.oldm="109";
        iee.numss="1";
        itemlist.add(iee);
        AllOrderItemEntity iee1=new AllOrderItemEntity();
        iee1.idss=1;
        iee1.atitle="洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水洗发水";
        iee1.acolor="黑色";
        iee1.asize="M";
        iee1.metails="水晶";
        iee1.amoney="222";
        iee1.oldm="109";
        iee1.numss="1";
        itemlist.add(iee1);
        adapter=new AllOrderAdapter(getActivity(),list,itemlist);
        listView.setAdapter(adapter);
    }

    public static Fragment_Orders newInstance() {
        Fragment_Orders fragment = new Fragment_Orders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
