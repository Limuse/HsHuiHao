package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.activity.LoginMain;
import com.huihao.adapter.MoneyNewsAdapter;
import com.huihao.common.Token;
import com.huihao.entity.MoneyNewsEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 * 财富消息
 */
public class Fragment_MoneyNews extends LFragment {
    private ListView listView;
    private MoneyNewsAdapter adapter;
    private List<MoneyNewsEntity> list = null;
    private RelativeLayout rl_newwss;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moneynews, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        rl_newwss=(RelativeLayout)getActivity().findViewById(R.id.rl_newwss);
        listView = (ListView) getActivity().findViewById(R.id.lv_money);
    }

    private void initData() {
        list = new ArrayList<MoneyNewsEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/member/usermsg/1/sign/aggregation/?t=3&uuid=" + Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
       // L.e(url);
        FragmentHandler handler = new FragmentHandler(Fragment_MoneyNews.this);
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
                    rl_newwss.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }else{
                    rl_newwss.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                for (int i = 0; i < json.length(); i++) {
                    MoneyNewsEntity entity = new MoneyNewsEntity();
                    JSONObject jsb = json.getJSONObject(i);
                    entity.setContent(jsb.getString("content"));
                    entity.setCtime(jsb.getString("ctime"));
                    entity.setStatus(jsb.getString("status"));
                    list.add(entity);
                }
                adapter = new MoneyNewsAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            }  }else {
                T.ss(jsonObject.getString("info"));
//                String longs=jsonObject.getString("info");
//                if(longs.equals("请先登录")){
//                    LSharePreference.getInstance(getActivity()).setBoolean("login", false);
//                    Intent intent = new Intent(getActivity(), LoginMain.class);
//                    startActivity(intent);
//                }
                rl_newwss.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_newwss.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public static Fragment_MoneyNews newInstance() {
        Fragment_MoneyNews fragment = new Fragment_MoneyNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
