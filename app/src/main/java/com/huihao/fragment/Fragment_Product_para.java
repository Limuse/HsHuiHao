package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.huihao.common.Log;
import com.huihao.custom.NoScrollListview;
import com.huihao.R;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_Product_para extends LFragment {
    private View parentView;
    private NoScrollListview listView;

    private SimpleAdapter simpleAdapter;

    public static int addCount = 0;

    public static Fragment_Product_para context;

    private List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_product_para,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = Fragment_Product_para.this;
        initView();
    }

    public static void addItemH(int i) {
        addCount += i;
    }
    public static void clearItemH() {
        addCount =0;
    }

    public void InitData(List<Map<String, String>> list) {
        this.mlist = list;
        simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.fragment_product_para_item, new String[]{"title"}, new int[]{R.id.tv_title});
        listView.setFocusable(false);
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        setListViewHeight();
    }

    public void setListViewHeight() {
        setListViewHeightBasedOnChildren(listView);
    }

    private void initView() {
        listView = (NoScrollListview) parentView.findViewById(R.id.lv_product);
    }

    public static Fragment_Product_para newInstance() {
        Fragment_Product_para fragment = new Fragment_Product_para();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        totalHeight = listAdapter.getCount() * listItem.getMeasuredHeight()+(int)(addCount* listItem.getMeasuredHeight()*0.5);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        LSharePreference.getInstance(getActivity()).setInt("pager2", totalHeight);


//        ListAdapter listAdapter = listView.getAdapter();
//        int totalHeight = 0;
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            T.ss( listItem.getMeasuredHeight()+"");
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);

    }
}
