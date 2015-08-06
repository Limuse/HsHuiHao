package com.huihao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * 退款中
 */
public class Fragment_back_money extends LFragment {
    private ListView listview;
    private List<AllOrderEntity> list=null;
    private AllOrderAdapter adapter;
    private List<AllOrderItemEntity> itemlist=null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_backmoney,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        listview = (ListView) getView().findViewById(R.id.lv_backmoney);
    }

    public static Fragment_back_money newInstance() {
        Fragment_back_money fragment = new Fragment_back_money();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
