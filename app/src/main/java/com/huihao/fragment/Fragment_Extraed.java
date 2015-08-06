package com.huihao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.activity.ExR_State;
import com.huihao.adapter.ExtraRecodeAdapter;
import com.huihao.entity.ExtraReEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/3.
 */
public class Fragment_Extraed extends LFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ExtraRecodeAdapter adapter;
    List<ExtraReEntity> list = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extraed,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) getActivity().findViewById(R.id.lv_extraed);
    }

    private void initData() {
        list = new ArrayList<ExtraReEntity>();

        ExtraReEntity entity = new ExtraReEntity();
        entity.id = 1;
        entity.state = 3;
        entity.time = "2015-01-09";
        entity.money = "3000.00";
        ExtraReEntity entity2 = new ExtraReEntity();
        entity2.id = 2;
        entity2.state = 3;
        entity2.time = "2015-01-09";
        entity2.money = "3000.00";
        ExtraReEntity entity3 = new ExtraReEntity();
        entity3.id = 3;
        entity3.state = 3;
        entity3.time = "2015-01-09";
        entity3.money = "3000.00";
        list.add(entity);
        list.add(entity2);
        list.add(entity3);
        adapter = new ExtraRecodeAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public static Fragment_Extraed newInstance() {
        Fragment_Extraed fragment = new Fragment_Extraed();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExtraReEntity en = list.get(position);
        int t = en.state;
        Intent intent = new Intent(getActivity(), ExR_State.class);
        intent.putExtra("state", t + "");
        startActivity(intent);
    }
}
