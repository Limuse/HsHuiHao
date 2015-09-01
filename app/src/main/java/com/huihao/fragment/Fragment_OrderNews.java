package com.huihao.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.OrdersNewsAdapter;
import com.huihao.entity.OrdersNewsEntity;
import com.huihao.entity.UsErId;
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
 * 订单消息
 */
public class Fragment_OrderNews extends LFragment {
    private ListView listView;
    private OrdersNewsAdapter adapter;
    private List<OrdersNewsEntity> list = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ordersnews, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) getActivity().findViewById(R.id.lv_orders);
    }

    private void initData() {
        list = new ArrayList<OrdersNewsEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/member/usermsg/1/sign/aggregation/?t=2&uuid=" + UsErId.uuid;
        LReqEntity entity = new LReqEntity(url);
        L.e(url);
        FragmentHandler handler = new FragmentHandler(Fragment_OrderNews.this);
        handler.startLoadingData(entity, 1);
//        list = new ArrayList<OrdersNewsEntity>();
//        OrdersNewsEntity entity = new OrdersNewsEntity();
//        entity.state = 1;
//        entity.time = "2015年6月25日 8:20:00";
//        entity.tjtime = "2015年6月26日 8:20:00";
//        entity.khinfo = "浙江省杭州市 张小江";
//        entity.ordermoney = "11,900.00";
//        entity.yjmoney = "100.00";
//        list.add(entity);
//        OrdersNewsEntity entity1 = new OrdersNewsEntity();

//        list.add(entity1);
//        adapter = new OrdersNewsAdapter(getActivity(), list);
//        listView.setAdapter(adapter);

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
                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsob = json.getJSONObject(i);
                    OrdersNewsEntity entity = new OrdersNewsEntity();
                    //        entity1.state = 0;
//        entity1.time = "2015年6月25日 8:20:00";
//        entity1.tjtime = "2015年6月26日 8:20:00";
//        entity1.khinfo = "浙江省杭州市 张小江";
//        entity1.ordermoney = "11,900.00";
//        entity1.yjmoney = "100.00";
                    entity.setId(jsob.getString("id"));
                    entity.setOrderid(jsob.getString("orderid"));
                    entity.setUser_info(jsob.getString("user_info"));
                    entity.setTotal_price(jsob.getString("total_price"));
                    entity.setOverdue(jsob.getString("overdue"));
                    entity.setCtime(jsob.getString("ctime"));
                    entity.setStatus(jsob.getString("status"));
                    list.add(entity);
                }

                adapter = new OrdersNewsAdapter(getActivity(), list);
                listView.setAdapter(adapter);

            } else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Fragment_OrderNews newInstance() {
        Fragment_OrderNews fragment = new Fragment_OrderNews();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
