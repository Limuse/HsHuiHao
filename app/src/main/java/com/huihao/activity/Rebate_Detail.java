package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.adapter.TebateDetailAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.RebateDetailEntity;
import com.huihao.entity.UsErId;
import com.huihao.fragment.Fragment_my;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
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
 * 我的返利----返利明细
 */
public class Rebate_Detail extends LActivity {
    private ListView listView;
    private TebateDetailAdapter adapter;
    private List<RebateDetailEntity> list = new ArrayList<RebateDetailEntity>();
    private RelativeLayout rl_rebt;
    private Button btn_rebt;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_rebate_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initDatas();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("返利明细");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        rl_rebt=(RelativeLayout)findViewById(R.id.rl_rebt);
        btn_rebt=(Button)findViewById(R.id.btn_rebt);
        listView = (ListView) findViewById(R.id.lv_redatil);
        btn_rebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Rebate_Detail.this,HomeMain.class);
                startActivity(intent);
                finish();
                Rebate.instance.finish();
                Fragment_my.instance.getActivity().finish();

            }
        });
    }


    private void initDatas() {
        Resources res = getResources();
        String url = null;
        String s = getIntent().getExtras().getString("s");
        if (s.equals("1")) {
            url = res.getString(R.string.app_service_url)
                    + "/huihao/member/commissiondetail/1/sign/aggregation/?uuid="+ Token.get(this);

        } else if (s.equals("2")) {
            url = res.getString(R.string.app_service_url)
                    + "/huihao/member/profitsdetail/1/sign/aggregation/?uuid="+Token.get(this);

        }
        LReqEntity entity = new LReqEntity(url);
        ActivityHandler handler = new ActivityHandler(Rebate_Detail.this);
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
                JSONArray o = jsonObject.getJSONArray("list");
                if(o.equals("")||o.equals(null)||o.length()<1){
                    rl_rebt.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }else{
                    rl_rebt.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                for (int i = 0; i < o.length(); i++) {
                    JSONObject js = o.getJSONObject(i);
                    RebateDetailEntity entity = new RebateDetailEntity();
                    entity.setOrderid(js.getString("orderid"));
                    entity.setTotal_price(js.getString("total_price"));
                    entity.setMoney(js.getString("money"));
                    entity.setUsername(js.getString("username"));
                    entity.setState(js.getString("state"));
                    list.add(entity);

                }
                adapter = new TebateDetailAdapter(this, list);
                listView.setAdapter(adapter);

            } }else {

                T.ss(jsonObject.getString("info").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_rebt.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
