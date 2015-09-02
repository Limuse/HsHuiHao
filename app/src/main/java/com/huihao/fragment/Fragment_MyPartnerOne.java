package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.MyPartnerAdapter;
import com.huihao.entity.MyPartnerEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class Fragment_MyPartnerOne extends LFragment {
    private TextView tv_money, tv_nums;
    private ListView listView;
    private MyPartnerAdapter adapter;
<<<<<<< Updated upstream
    private List<MyPartnerEntity.ChildList> list =new ArrayList<MyPartnerEntity.ChildList>();
    private LinearLayout lyms;
    private ScrollView scrollView;
=======
    private List<MyPartnerEntity> list = null;

>>>>>>> Stashed changes
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

    }

    public static Fragment_MyPartnerOne newInstance() {
        Fragment_MyPartnerOne fragment = new Fragment_MyPartnerOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
