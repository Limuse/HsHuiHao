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

import java.util.List;

/**
 * Created by huisou on 2015/7/31.
 * 待付款
 */
public class Fragment_pay_money extends LFragment {
    private ListView listView;
    private List<AllOrderEntity> list=null;
    private AllOrderAdapter adapter;
    private List<AllOrderItemEntity> itemlist=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paymoney,
                container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_paymoney);
    }


    public static Fragment_pay_money newInstance() {
        Fragment_pay_money fragment = new Fragment_pay_money();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
