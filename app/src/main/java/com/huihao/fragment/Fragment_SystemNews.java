package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.SystemNewsAdapter;
import com.huihao.adapter.SystemNewssAdapter;
import com.huihao.entity.SystemNewsEntity;
import com.huihao.entity.SystemNewssEntity;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 * 系统消息
 */
public class Fragment_SystemNews extends LFragment {
    private ListView listView1;
    private ListView listView2;
    private SystemNewsAdapter adapter;//新朋友
    private SystemNewssAdapter adapter2;//带图片的
    private List<SystemNewsEntity> list = null;
    private List<SystemNewssEntity> list2 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_systemnews, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        listView1 = (ListView) getActivity().findViewById(R.id.lv_systemd);
        listView2 = (ListView) getActivity().findViewById(R.id.lv_system);
    }

    private void initData() {
        list = new ArrayList<SystemNewsEntity>();
        list2=new ArrayList<SystemNewssEntity>();
        SystemNewsEntity entity = new SystemNewsEntity();
        entity.time1 = "2015年7月8日 10:20:30";
        entity.names = "张小江";
        list.add(entity);




        SystemNewssEntity entity2 = new SystemNewssEntity();
        entity2.time2 = "2015年7月8日 10:20:30";
        entity2.title = "杭州西湖西湖西湖西湖西湖西湖西湖";
        entity2.desc = "西湖三月美景西湖三月美景西湖三月美景西湖三月美景";
        list2.add(entity2);

        SystemNewssEntity entity3 = new SystemNewssEntity();
        entity3.time2 = "2015年7月8日 10:20:30";
        entity3.title = "杭州西湖西湖西湖西湖西湖西湖西湖";
        entity3.desc = "西湖三月美景西湖三月美景西湖三月美景西湖三月美景";
        list2.add(entity3);
        SystemNewssEntity entity4 = new SystemNewssEntity();
        entity4.time2 = "2015年7月8日 10:20:30";
        entity4.title = "杭州西湖西湖西湖西湖西湖西湖西湖";
        entity4.desc = "西湖三月美景西湖三月美景西湖三月美景西湖三月美景";
        list2.add(entity4);
        adapter = new SystemNewsAdapter(getActivity(), list);
        adapter2 = new SystemNewssAdapter(getActivity(), list2);
        listView1.setAdapter(adapter);
        listView2.setAdapter(adapter2);
    }

    public static Fragment_SystemNews newInstance() {
        Fragment_SystemNews fragment = new Fragment_SystemNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
