package com.huihao.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.adapter.SystemNewsAdapter;
import com.huihao.adapter.SystemNewssAdapter;
import com.huihao.common.Token;
import com.huihao.entity.SystemNewsEntity;
import com.huihao.entity.SystemNewssEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 * 系统消息
 */
public class Fragment_SystemNews extends LFragment {
    private ListView listView;
    // private SystemNewsAdapter adapter;//新朋友
    private SystemNewssAdapter adapter;//带图片的
    private List<SystemNewsEntity> list = null;
    private RelativeLayout rl_neww, rl_nal;

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
        rl_neww = (RelativeLayout) getActivity().findViewById(R.id.rl_neww);
        rl_nal = (RelativeLayout) getActivity().findViewById(R.id.rl_nal);
        listView = (ListView) getActivity().findViewById(R.id.lv_systemd);
    }

    private void initData() {
//        uuid 用户token
//        t 1表示系统消息 2表示订单消息 3表示财富消息
//        /huihao/member/usermsg/1/sign/aggregation/
        list = new ArrayList<SystemNewsEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/member/usermsg/1/sign/aggregation/?t=1&uuid=" + Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        //L.e(url);
        FragmentHandler handler = new FragmentHandler(Fragment_SystemNews.this);
        handler.startLoadingData(entity, 1);

    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONArray json = jsonObject.getJSONArray("list");
                if(json.equals("")||json.equals(null)||json.length()<1){
                    rl_nal.setVisibility(View.GONE);
                    rl_neww.setVisibility(View.VISIBLE);
                }else{
                    rl_nal.setVisibility(View.VISIBLE);
                    rl_neww.setVisibility(View.GONE);

                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsb = json.getJSONObject(i);
                    SystemNewsEntity entity = new SystemNewsEntity();
                    entity.setIds(jsb.getString("id"));
                    entity.setTitle(jsb.getString("title"));
                    entity.setPicture(jsb.getString("picture"));
                    entity.setContent(jsb.getString("introduction"));
                    entity.setLinkurl(jsb.getString("linkurl"));
                    entity.setAdd_time(jsb.getString("add_time"));
                    entity.setUpdate_time(jsb.getString("update_time"));
                    entity.setStatus(jsb.getString("status"));
                    list.add(entity);
                }
                adapter = new SystemNewssAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            } }else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_nal.setVisibility(View.GONE);
            rl_neww.setVisibility(View.VISIBLE);
        }
    }

    public static Fragment_SystemNews newInstance() {
        Fragment_SystemNews fragment = new Fragment_SystemNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
