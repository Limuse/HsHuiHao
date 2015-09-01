package com.huihao.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.MyPartnerAdapter;
import com.huihao.entity.MyPartnerEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class Fragment_MyPartnerTwo extends LFragment {
    private TextView tv_money, tv_nums;
    private ListView listView;
    private MyPartnerAdapter adapter;
    private List<MyPartnerEntity.ChildList> list = new ArrayList<MyPartnerEntity.ChildList>();
    private LinearLayout lym;
    private int listHeight;
    private ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_partnertwo,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        tv_money = (TextView) getActivity().findViewById(R.id.tv_prmoneys);
        tv_nums = (TextView) getActivity().findViewById(R.id.tv_parnums);
        listView = (ListView) getActivity().findViewById(R.id.lv_backmoneys);
        lym = (LinearLayout) getActivity().findViewById(R.id.lym);
        scrollView = (ScrollView) getActivity().findViewById(R.id.scccs);

    }



    private void initData() {

        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/teamdetail/1/sign/aggregation/?uuid="+ UsErId.uuid;
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(this);
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
                JSONObject obejc=jsonObject.getJSONObject("list");
                JSONArray array=obejc.getJSONArray("second_list");
                for(int i=0;i<array.length();i++){
                    JSONObject o=array.getJSONObject(i);
                    MyPartnerEntity.ChildList cl=new MyPartnerEntity.ChildList();
                    cl.setUsername(o.getString("username"));
                    cl.setAmount(o.getString("total_amount"));
                    list.add(cl);
                }
                tv_money.setText(obejc.getString("second_total"));
                tv_nums.setText(obejc.getString("second_child"));
                adapter = new MyPartnerAdapter(getActivity(), list);
                listView.setAdapter(adapter);
                Scrollto();
            } else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


    public static Fragment_MyPartnerTwo newInstance() {
        Fragment_MyPartnerTwo fragment = new Fragment_MyPartnerTwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
