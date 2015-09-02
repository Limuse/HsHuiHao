package com.huihao.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.AllOrderAdapter;
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
 * 待付款
 */
public class Fragment_pay_money extends LFragment {
    private ListView listView;
    private List<AllOrderEntity> list = new ArrayList<AllOrderEntity>();
    private AllOrderAdapter adapter;
    private List<AllOrderEntity.ChildEntity> itemlist = null;//new ArrayList<AllOrderEntity.ChildEntity>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paymoney,
                container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_paymoney);
    }

    private void initData() {
//t 订单状态（1未付款2待发货3待收货，不传则表示全部订单）
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/1/sign/aggregation/?t=1&uuid="+ UsErId.uuid;
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_pay_money.this);
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
                JSONArray jo = o.getJSONArray("orderlist");
                for (int i = 0; i < jo.length(); i++) {
                    JSONObject ob = jo.getJSONObject(i);
                    AllOrderEntity ee = new AllOrderEntity();
                    ee.setState(ob.getString("state"));
                    ee.setId(ob.getString("id"));
                    ee.setTotal_price(ob.getString("total_price"));
                    ee.setPay_price(ob.getString("pay_price"));
                    JSONArray ja = ob.getJSONArray("_child");
                    itemlist = new ArrayList<AllOrderEntity.ChildEntity>();
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

                adapter = new AllOrderAdapter(getActivity(), list);
                listView.setAdapter(adapter);

            } else {

                T.ss(jsonObject.getString("info").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static Fragment_pay_money newInstance() {
        Fragment_pay_money fragment = new Fragment_pay_money();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
