package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.ProductInfoImageAda;
import com.huihao.custom.NoScrollListview;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.LSharePreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_Product_info extends LFragment {
    private View parentView;
    private NoScrollListview listView;
    public static Fragment_Product_info context;
    private static ProductInfoImageAda imageAda;

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
        context = Fragment_Product_info.this;
        initView();
    }

    public void InitData(List<Map<String, String>> list) {
        this.mlist = list;
        imageAda = new ProductInfoImageAda(getActivity(), mlist);
        listView.setFocusable(false);
        listView.setAdapter(imageAda);
        setListViewHeight();
//        Product_details.context.setPageH();
    }

    public void setListViewHeight() {
        setListViewHeightBasedOnChildren(listView);
    }

    private void initView() {
        listView = (NoScrollListview) parentView.findViewById(R.id.lv_product);
    }

    public static void AdaNotif() {
        imageAda.notifyDataSetChanged();
    }

    public static Fragment_Product_info newInstance() {
        Fragment_Product_info fragment = new Fragment_Product_info();
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

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        LSharePreference.getInstance(getActivity()).setInt("pager1", totalHeight);
    }
}
