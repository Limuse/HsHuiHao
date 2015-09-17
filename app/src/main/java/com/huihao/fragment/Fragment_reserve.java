package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.activity.All_Orders;
import com.huihao.activity.HomeMain;
import com.huihao.adapter.AllOrderAdapter;
import com.huihao.common.Token;
import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
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
 * Created by huisou on 2015/7/31.
 * 待发货
 */
public class Fragment_reserve extends LFragment {
    private ListView listView;
    private List<AllOrderEntity> list = new ArrayList<>();
    private AllOrderAdapter adapter;
    private List<AllOrderEntity.ChildEntity> itemlist = null;//new ArrayList<AllOrderEntity.ChildEntity>();
    private RelativeLayout rl_gssa;
    private Button btn_gsas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reserve,
                container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        rl_gssa = (RelativeLayout) getView().findViewById(R.id.rl_gssa);
        btn_gsas = (Button) getView().findViewById(R.id.btn_gsas);
        listView = (ListView) getView().findViewById(R.id.lv_reserve);
        btn_gsas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeMain.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                All_Orders.instance.finish();
                Fragment_my.instance.getActivity().finish();
            }
        });
    }

    private void initData() {
//t 订单状态（1未付款2待发货3待收货，不传则表示全部订单）
        list.clear();

        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/1/sign/aggregation/?t=2&uuid=" + Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_reserve.this);
        handler.startLoadingData(entity, 1);

    }


    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonSubmit(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonSubmit(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject o = jsonObject.getJSONObject("list");
                if (o.equals("") || o.equals(null) || o.equals("null") || o.length() < 1) {
                    rl_gssa.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    rl_gssa.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                    JSONArray jo = o.getJSONArray("orderlist");
                    for (int i = 0; i < jo.length(); i++) {
                        JSONObject ob = jo.getJSONObject(i);
                        AllOrderEntity ee = new AllOrderEntity();
                        ee.setState(ob.getString("state"));
                        ee.setId(ob.getString("id"));
                        ee.setTotal_price(ob.getString("total_price"));
                        ee.setPay_price(ob.getString("pay_price"));
                        JSONArray ja = ob.getJSONArray("_child");
                        itemlist = new ArrayList<>();
                        for (int j = 0; j < ja.length(); j++) {
                            JSONObject jt = ja.getJSONObject(j);
                            AllOrderEntity.ChildEntity ce = new AllOrderEntity.ChildEntity();
                            ce.setOrderid(jt.getString("orderid"));
                            ce.setTitle(jt.getString("title"));
                            ce.setSpec_1(jt.getString("spec_1"));
                            ce.setSpec_2(jt.getString("spec_2"));
                            ce.setPrice(jt.getString("price"));
                            ce.setNewprice(jt.getString("newprice"));
                            ce.setNum(jt.getString("num"));
                            ce.setPicurl(jt.getString("picurl"));
                            itemlist.add(ce);

                        }
                        ee.set_child(itemlist);
                        list.add(ee);

                    }

                    adapter = new AllOrderAdapter(Fragment_reserve.this, list);
                    listView.setAdapter(adapter);

                }
            } else {

                T.ss(jsonObject.getString("info").toString());
                rl_gssa.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_gssa.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }


    public static Fragment_reserve newInstance() {
        Fragment_reserve fragment = new Fragment_reserve();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
