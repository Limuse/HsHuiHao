package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
import com.huihao.R;
import com.huihao.adapter.AllOrderAdapter;
import com.leo.base.activity.fragment.LFragment;

import java.util.List;

/**
 * Created by huisou on 2015/7/31.
 * 待收货
 */
public class Fragment_reserve extends LFragment {
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
        return inflater.inflate(R.layout.fragment_reserve,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_reserve);
    }

    public static Fragment_reserve newInstance() {
        Fragment_reserve fragment = new Fragment_reserve();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
