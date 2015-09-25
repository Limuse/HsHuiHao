package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.huihao.R;
import com.huihao.activity.All_Orders;
import com.huihao.activity.HomeMain;
import com.huihao.activity.LoginMain;
import com.huihao.activity.Pay_Successed;
import com.huihao.adapter.AllOrderAdapter;
import com.huihao.common.PayResult;
import com.huihao.common.SignUtils;
import com.huihao.common.Token;
import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by huisou on 2015/7/31.
 * 全部订单
 */
public class Fragment_Orders extends LFragment {
    private ListView listView;
    private List<AllOrderEntity> list = new ArrayList<>();
    private AllOrderAdapter adapter;
    private List<AllOrderEntity.ChildEntity> itemlist = null;//new ArrayList<AllOrderEntity.ChildEntity>();
    private RelativeLayout rl_gsss;
    private Button btn_gsa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initView() {
        rl_gsss = (RelativeLayout) getView().findViewById(R.id.rl_gsss);
        btn_gsa = (Button) getView().findViewById(R.id.btn_gsa);
        listView = (ListView) getView().findViewById(R.id.lv_showall);
        btn_gsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSharePreference.getInstance(getActivity()).setBoolean("isSwitchM", true);
                getActivity().finish();
//                All_Orders.instance.finish();
//                Fragment_my.instance.getActivity().finish();
            }
        });
    }


    private void initData() {
//t 订单状态（1未付款2待发货3待收货，不传则表示全部订单）
        list.clear();

        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/1/sign/aggregation/?uuid=" + Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_Orders.this);
        handler.startLoadingData(entity, 1);

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            list.clear();
//            initData();
//        }
//    }

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
                    rl_gsss.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    rl_gsss.setVisibility(View.GONE);
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

                    adapter = new AllOrderAdapter(Fragment_Orders.this, list);
                    listView.setAdapter(adapter);


                }
            } else {

                T.ss(jsonObject.getString("info").toString());
                String longs = jsonObject.getString("info");
                if (longs.equals("请先登录")) {
                    LSharePreference.getInstance(getActivity()).setBoolean("login", false);
                    Intent intent = new Intent(getActivity(), LoginMain.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_gsss.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }


    public static Fragment_Orders newInstance() {
        Fragment_Orders fragment = new Fragment_Orders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
