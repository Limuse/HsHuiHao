package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.activity.HomeMain;
import com.huihao.activity.Submit_Orders;
import com.huihao.adapter.HorizontalSlideAdapter;
import com.huihao.common.Token;
import com.huihao.custom.SlideListView2;
import com.huihao.entity.ShopItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_shop extends LFragment implements View.OnClickListener {
    private View parentView;
    private RelativeLayout rl_shops, rl_gshops;
    private SlideListView2 listview;
    private TextView tv_all_choose, tv_all_money;
    private CheckBox cb_all_cbx;
    private Button btn_all_js, btn_go;
    public static Fragment_shop instance = null;
    private List<ShopItemEntity> list = new ArrayList<ShopItemEntity>();

    private HorizontalSlideAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_shop,
                container, false);

        return parentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance = Fragment_shop.this;
        Toolbar toolbar = (Toolbar) parentView.findViewById(R.id.toolbar);
        toolbar.setTitle("购物车");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        initView();
        initData();


    }


    private void initView() {

        rl_shops = (RelativeLayout) getActivity().findViewById(R.id.rl_shops);
        rl_gshops = (RelativeLayout) getActivity().findViewById(R.id.rl_gshops);
        btn_go = (Button) getActivity().findViewById(R.id.btn_go);
        listview = (SlideListView2) getActivity().findViewById(R.id.lv_list_shop);
        listview.initSlideMode(SlideListView2.MOD_RIGHT);
        tv_all_choose = (TextView) getActivity().findViewById(R.id.tv_all_choose);
        tv_all_money = (TextView) getActivity().findViewById(R.id.tv_all_money);
        cb_all_cbx = (CheckBox) getActivity().findViewById(R.id.cb_all_checkbox);
        btn_all_js = (Button) getActivity().findViewById(R.id.btn_all_jesuan);
        // tv_all_choose.setOnClickListener(this);
        // cb_all_cbx.setOnClickListener(this);
        btn_all_js.setOnClickListener(this);
        tv_all_money.setText("0.00");
        btn_go.setOnClickListener(this);
    }

    private void Tsum() {

        adapter = new HorizontalSlideAdapter(getActivity(), list, listview);

        adapter.setOnNumChangeListener(new HorizontalSlideAdapter.OnNumChangeListener() {

            @Override
            public void OnNumJiaChange(float jiage) {
                if (tv_all_money.getText().equals(null)) {
                    tv_all_money.setText(jiage + "");
                } else {
                    float a = Float.parseFloat(tv_all_money.getText().toString());

                    tv_all_money.setText(a + jiage + "");
                }

            }

            @Override
            public void OnNumJianChange(float jiage) {
                float a = Float.parseFloat(tv_all_money.getText().toString());

                tv_all_money.setText(a - jiage + "");

            }

        });

        listview.setAdapter(adapter);

        cb_all_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float allMoney = 0;
                List<ShopItemEntity> newlist = new ArrayList<ShopItemEntity>();
                if (isChecked) {
                    for (ShopItemEntity e : adapter.getList()) {
                        e.setIsCheck(true);
                        allMoney += e.getNum() * e.getDanjia();
                        newlist.add(e);
                        tv_all_choose.setText("全不选");

                    }

                } else {
                    for (ShopItemEntity e : adapter.getList()) {
                        e.setIsCheck(false);
                        newlist.add(e);
                        // newlist.get(e).getId();
                        tv_all_choose.setText("全选");

                    }
                }

                adapter.updateData(newlist);
                tv_all_money.setText(allMoney + "");

            }
        });

    }


    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/cart/1/sign/aggregation/?uuid=" + Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        L.e(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        FragmentHandler handler = new FragmentHandler(this);
        handler.startLoadingData(entity, 1);

    }

    private void getJsonData(String data) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject result = jsonObject.getJSONObject("list");
                if (result.length() < 1) {
                    rl_shops.setVisibility(View.GONE);
                    rl_gshops.setVisibility(View.VISIBLE);
                } else {
                    rl_shops.setVisibility(View.VISIBLE);
                    rl_gshops.setVisibility(View.GONE);
                    JSONArray jsonArray = result.getJSONArray("cart_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ShopItemEntity entity = new ShopItemEntity();
                        entity.setId(object.getString("id"));
                        entity.setTitle(object.getString("title"));
                        entity.setPic(object.getString("picurl"));
                        entity.setNum(Integer.parseInt(object.getString("bnum")));
                        entity.setIsCheck(false);
                        entity.setSpecid(object.getString("spec_id"));
                        if (object.getString("nprice").equals(null) || object.getString("nprice").equals("") || object.getString("nprice").equals("null")) {
                            entity.setDanjia(0.00f);
                        } else {
                            entity.setDanjia(Float.parseFloat(object.getString("nprice")));
                        }
                        if (object.getString("oprice").equals(null) || object.getString("oprice").equals("") || object.getString("oprice").equals("null")) {
                            entity.setSale(0.00f);
                        } else {
                            entity.setSale(Float.parseFloat(object.getString("oprice")));
                        }
                        String spec1 = object.getString("title_1") + ":" + object.getString("spec_1");
                        if (spec1.length() > 2) {
                            entity.setColor(spec1);
                        }

                        String spec2 = object.getString("title_2") + ":" + object.getString("spec_2");
                        if (spec2.length() > 2) {
                            entity.setSize(spec2);
                        }
                        entity.setProduct_id(object.getString("product_id"));
                        list.add(entity);
                    }
                    Tsum();

                }
            } else {
                T.ss(jsonObject.getString("info").toString());
                rl_shops.setVisibility(View.GONE);
                rl_gshops.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_shops.setVisibility(View.GONE);
            rl_gshops.setVisibility(View.VISIBLE);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        initData();
        cb_all_cbx.setChecked(false);
        tv_all_choose.setText("全选");
        tv_all_money.setText("0.00");
    }

    @Override
    public void onClick(View v) {
//        /**
//         * 点击全选
//         */
//        if (v == tv_all_choose) {
//            if (cb_all_cbx.isChecked() == false) {
//                cb_all_cbx.setChecked(true);
//                adapter.configCheckMap(true);
//                float MONEY = adapter.getMoney();
//            //    T.ss(MONEY + "");
//                tv_all_money.setText(MONEY+"");
//                adapter.notifyDataSetChanged();
//                tv_all_choose.setText("全不选");
//
//
//            } else if (cb_all_cbx.isChecked() == true) {
//                cb_all_cbx.setChecked(false);
//                adapter.configCheckMap(false);
//                adapter.notifyDataSetChanged();
//                tv_all_money.setText("0.00");
//                tv_all_choose.setText("全选");
//
//            }
//        }
//        /**
//         * 点击全选的checkbox
//         */
//        if (v == cb_all_cbx) {
//            if (tv_all_choose.getText().toString().equals("全选")) {
//                cb_all_cbx.setChecked(true);
//                adapter.configCheckMap(true);
//                float MONEY = adapter.getMoney();
//                tv_all_money.setText(MONEY+"");
//                adapter.notifyDataSetChanged();
//                tv_all_choose.setText("全不选");
//
//            } else if (tv_all_choose.getText().toString().equals("全不选")) {
//                cb_all_cbx.setChecked(false);
//                adapter.configCheckMap(false);
//                tv_all_money.setText("0.00");
//                adapter.notifyDataSetChanged();
//                tv_all_choose.setText("全选");
//            }
//        }
        if (v == btn_go) {
            Intent intent = new Intent(getActivity(), HomeMain.class);
            startActivity(intent);
            getActivity().finish();
        }
        /**
         * 点击结算
         */
        if (v == btn_all_js) {
            /**
             * 跳转到结算的下个页面
             */


            String spec_id = adapter.getRname();
            String spec_num = adapter.getRnum();
            if (spec_id == null || spec_num == null) {
                T.ss("请选择商品！");
            } else {
                Intent intent = new Intent(getActivity(), Submit_Orders.class);
                intent.putExtra("spec_id", spec_id);
                intent.putExtra("spec_num", spec_num);
                getActivity().startActivity(intent);
            }


        }
    }


}
