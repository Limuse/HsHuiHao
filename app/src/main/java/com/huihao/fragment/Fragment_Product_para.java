package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huihao.R;
import com.huihao.adapter.ProductParaImageAda;
import com.huihao.common.Log;
import com.huihao.custom.NoScrollListview;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_Product_para extends LFragment {
    private View parentView;
    private NoScrollListview listView;

    private static ProductParaImageAda imageAda;

    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

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
        Log.e("2");
        initView();
        initData();
        initAda();
    }

    private void initAda() {
        imageAda = new ProductParaImageAda(getActivity(), list);
        listView.setAdapter(imageAda);
    }

    private void initView() {
        listView = (NoScrollListview)parentView.findViewById(R.id.lv_product);
    }

    private void initData() {
        Map<String, String> map;
        for (int i = 0; i <10 ; i++) {
            map = new HashMap<String, String>();
            map.put("image", "http://img1.gamedog.cn/2013/01/22/24-1301220949510-50.jpg");
            list.add(map);
        }
    }
    public static void AdaNotif(){
        imageAda.notifyDataSetChanged();
    }
    public static Fragment_Product_para newInstance() {
        Fragment_Product_para fragment = new Fragment_Product_para();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
