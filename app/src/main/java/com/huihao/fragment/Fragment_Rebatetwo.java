package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.activity.Extra_Record;
import com.huihao.activity.Extra_RecordCopy;
import com.huihao.activity.Extract_Rebate;
import com.huihao.activity.Rebate_Detail;
import com.huihao.custom.RiseNumberTextView;
import com.huihao.entity.UsErId;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huisou on 2015/8/30.
 */
public class Fragment_Rebatetwo extends LFragment implements View.OnClickListener {
    private Button btn_rb;
    private RiseNumberTextView numberTextView;
    private RelativeLayout rl_phone, rl_e, rl_f;
    private TextView tv1, tv2, tv3, tv4;
    private String amounta;//佣金总额
    private String unc;//d待确认
    private String w;
    private String rests;
    private String tran;
    private int d = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmetn_rebate,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {


        btn_rb = (Button) getActivity().findViewById(R.id.login_cancels);
        rl_phone = (RelativeLayout) getActivity().findViewById(R.id.rl_phones);
        rl_e = (RelativeLayout) getActivity().findViewById(R.id.rl_es);
        rl_f = (RelativeLayout) getActivity().findViewById(R.id.rl_fs);
        numberTextView = (RiseNumberTextView) getActivity().findViewById(R.id.numbertvs);
        tv1 = (TextView) getActivity().findViewById(R.id.tv_mey1s);
        tv2 = (TextView) getActivity().findViewById(R.id.tv_mey2s);
        tv3 = (TextView) getActivity().findViewById(R.id.tv_mey3s);
        tv4 = (TextView) getActivity().findViewById(R.id.tv_mey4s);
        btn_rb.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_e.setOnClickListener(this);
        rl_f.setOnClickListener(this);


    }

    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/profits/1/sign/aggregation/?uuid="+ UsErId.uuid;
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_Rebatetwo.this);
        handler.startLoadingData(entity, 1);
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonSubmits(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonSubmits(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject o = jsonObject.getJSONObject("list");
                JSONObject jo = o.getJSONObject("detail");
                amounta = jo.getString("amount_all");//佣金总额
                unc = jo.getString("amount_unconfirm");//d待确认
                w = jo.getString("amount_withdraw");
                rests = jo.getString("amount_rest");
                tran = jo.getString("amount_transfer");
                tv1.setText(unc);
                tv2.setText(tran);
                tv3.setText(w);
                tv4.setText(amounta);
                if (rests.equals(null) || rests == null || rests.equals("null")) {
                    d = 0;
                    numberTextView.withNumber(0.00f);
                } else {
                    d = Integer.parseInt(rests + "");
                    numberTextView.withNumber(d + 0f);
                }

                // 设置动画播放时间
                numberTextView.setDuration(1000);
                // 开始播放动画
                numberTextView.start();

            } else {

                T.ss(jsonObject.getString("info").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_cancels) {
            T.ss("提现");
            Intent intent = new Intent(getActivity(), Extract_Rebate.class);
            intent.putExtra("t", "2");
            startActivity(intent);
        }
        //提取中
        if (id == R.id.rl_phones) {
            Intent intent = new Intent(getActivity(), Extra_RecordCopy.class);
            intent.putExtra("gets", "0");
            startActivity(intent);
        }
        //已提现
        if (id == R.id.rl_es) {
            Intent intent = new Intent(getActivity(), Extra_RecordCopy.class);
            intent.putExtra("gets", "1");
            startActivity(intent);

        }
        //返利明细
        if (id == R.id.rl_fs) {
            Intent intent = new Intent(getActivity(), Rebate_Detail.class);
            intent.putExtra("s","2");
            startActivity(intent);
        }
    }

    public static Fragment_Rebatetwo newInstance() {
        Fragment_Rebatetwo fragment = new Fragment_Rebatetwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
