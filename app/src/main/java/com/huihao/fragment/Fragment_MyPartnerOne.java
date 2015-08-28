package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.MyPartnerAdapter;
import com.huihao.entity.MyPartnerEntity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class Fragment_MyPartnerOne extends LFragment {
    private TextView tv_money, tv_nums;
    private ListView listView;
    private MyPartnerAdapter adapter;
    private List<MyPartnerEntity> list = null;
    private LinearLayout lyms;
    private ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_partnerone,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        tv_money = (TextView) getActivity().findViewById(R.id.tv_prmoney);
        tv_nums = (TextView) getActivity().findViewById(R.id.tv_parnum);
        listView = (ListView) getActivity().findViewById(R.id.lv_backmoney);
        lyms = (LinearLayout) getActivity().findViewById(R.id.lyms);
        scrollView = (ScrollView) getActivity().findViewById(R.id.cccd);
    }


    private void initData() {
        list = new ArrayList<MyPartnerEntity>();
        for (int i = 0; i < 15; i++) {
            MyPartnerEntity en = new MyPartnerEntity();
            en.names = "张三";
            en.moneys = "1123";
            list.add(en);
        }
        adapter = new MyPartnerAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        Scrollto();
    }

    private void Scrollto() {
        scrollView.post(new Runnable() {
            //让scrollview跳转到顶部，必须放在runnable()方法中
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
        listView.setFocusable(false);
    }


    public static Fragment_MyPartnerOne newInstance() {
        Fragment_MyPartnerOne fragment = new Fragment_MyPartnerOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


}
