package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.MetailflowDerailAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.MetailflowEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/7.
 * 物流详情
 */
public class MetailFlow_Detail extends LActivity {
    private TextView tv_mn, tv_mnum, tv_mstate;
    private ListView listView;
    private MetailflowDerailAdapter adapter;
    private ScrollView scrollView;
    private MetailflowEntity entity = new MetailflowEntity();
    private List<MetailflowEntity.ExressDetailEntity> list = new ArrayList<MetailflowEntity.ExressDetailEntity>();
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_metailflow_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("物流详情");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        scrollView = (ScrollView) findViewById(R.id.sc_ll);
        tv_mn = (TextView) findViewById(R.id.tv_kd);
        tv_mnum = (TextView) findViewById(R.id.tv_ydnum);
        tv_mstate = (TextView) findViewById(R.id.tv_wstate);
        listView = (ListView) findViewById(R.id.wlistvew);

    }

//    private void initDatas() {
//        list = new ArrayList<MetailflowEntity>();
//        MetailflowEntity entity1 = new MetailflowEntity();
//        entity1.idm = 1;
//        entity1.tv1 = "物件已经扫描，正在装运中";
//        entity1.tv2 = "2015-08-10 10:30:51";
//        MetailflowEntity entity2 = new MetailflowEntity();
//        entity2.idm = 2;
//        entity2.tv1 = "物件已经从广州物资站发往杭州，正在前往杭州中转站";
//        entity2.tv2 = "2015-08-10 12:30:51";
//        MetailflowEntity entity3 = new MetailflowEntity();
//        entity3.idm = 3;
//        entity3.tv1 = "物件正在派送中";
//        entity3.tv2 = "2015-08-10 18:30:51";
//        MetailflowEntity entity4 = new MetailflowEntity();
//        entity4.idm = 4;
//        entity4.tv1 = "正在前往杭州中转站";
//        entity4.tv2 = "2015-08-10 18:30:51";
//        list.add(entity3);
//        list.add(entity4);
//        list.add(entity2);
//        list.add(entity1);
//
//    }


    private void initData() {
        String ids = getIntent().getExtras().getString("id");
        //  http://huihaowfx.huisou.com//huihao/orders/courier/1/sign/aggregation/?uuid=6a35c1ed7255077d57d57be679048034&id=2008080793
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/courier/1/sign/aggregation/?uuid="+ UsErId.uuid+"&id=" + "2008080793";//ids
        LReqEntity entity = new LReqEntity(url);

        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }

    private void getJsonData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            //int code = jsonObject.getInt("status");
            if (jsonObject.length() > 1) {
                MetailflowEntity mfentity = new MetailflowEntity();
                mfentity.setTitle(jsonObject.getString("title"));
                mfentity.setExress_no(jsonObject.getString("exress_no"));
                mfentity.setStatus_name(jsonObject.getString("status_name"));
                tv_mn.setText(mfentity.getTitle());
                tv_mnum.setText(mfentity.getExress_no());
                tv_mstate.setText(mfentity.getStatus_name());

                JSONArray array = jsonObject.getJSONArray("exress_detail");
                for (int i = 0; i < array.length(); i++) {
                    MetailflowEntity.ExressDetailEntity exlist = new MetailflowEntity.ExressDetailEntity();
                    JSONObject object = array.getJSONObject(i);
                    exlist.setTime(object.getString("time"));
                    exlist.setContext(object.getString("context"));
                    exlist.setFtime(object.getString("ftime"));
                    list.add(exlist);
                }
                adapter = new MetailflowDerailAdapter(MetailFlow_Detail.this, list);
                listView.setAdapter(adapter);
                scrollView.post(new Runnable() {
                    //让scrollview跳转到顶部，必须放在runnable()方法中
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, 0);
                    }
                });

            } else {
                T.ss("获取数据失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
