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
import com.huihao.activity.Extract_Rebate;
import com.huihao.activity.Rebate_Detail;
import com.huihao.common.Token;
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
public class Fragment_Rebateone extends LFragment implements View.OnClickListener {
    private Button btn_rb;
    private RiseNumberTextView numberTextViews;
    private RelativeLayout rl_phone, rl_e, rl_f;
    private TextView tv1s, tv2s, tv3s, tv4s;
    private int d = 0;

    private String amounta;//佣金总额
    private String unc;//d待确认
    private String w;
    private String rests;
    private String tran;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_rebert,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initDatas();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initView() {


        btn_rb = (Button) getActivity().findViewById(R.id.login_cancel);
        rl_phone = (RelativeLayout) getActivity().findViewById(R.id.rl_phone);
        rl_e = (RelativeLayout) getActivity().findViewById(R.id.rl_e);
        rl_f = (RelativeLayout) getActivity().findViewById(R.id.rl_f);
        numberTextViews = (RiseNumberTextView) getActivity().findViewById(R.id.numbertv);
        tv1s = (TextView) getActivity().findViewById(R.id.tv_mey1);
        tv2s = (TextView) getActivity().findViewById(R.id.tv_mey2);
        tv3s = (TextView) getActivity().findViewById(R.id.tv_mey3);
        tv4s = (TextView) getActivity().findViewById(R.id.tv_mey4);
        btn_rb.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_e.setOnClickListener(this);
        rl_f.setOnClickListener(this);




    }

    private void initDatas() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/commission/1/sign/aggregation/?uuid="+ Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_Rebateone.this);
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
                JSONObject jo = o.getJSONObject("detail");
                amounta = jo.getString("amount_all");//佣金总额
                unc = jo.getString("amount_unconfirm");//d待确认
                w = jo.getString("amount_withdraw");
                rests = jo.getString("amount_rest");
                tran = jo.getString("amount_transfer");

                tv1s.setText(unc);
                tv2s.setText(tran);
                tv3s.setText(w);
                tv4s.setText(amounta);

                if (rests.equals(null)||rests==null||rests.equals("null")) {
                    d = 0;
                    numberTextViews.withNumber(0.00f);
                } else {
                    d = Integer.parseInt(rests + "");
                    numberTextViews.withNumber(d + 0f);
                }

                // 设置动画播放时间
                numberTextViews.setDuration(1000);
                // 开始播放动画
                numberTextViews.start();
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
        if (id == R.id.login_cancel) {
            //T.ss("提现");
            Intent intent = new Intent(getActivity(), Extract_Rebate.class);
            intent.putExtra("t", "1");
            startActivity(intent);
        }
        //提取中
        if (id == R.id.rl_phone) {
            Intent intent = new Intent(getActivity(), Extra_Record.class);
            intent.putExtra("gets", "0");
            startActivity(intent);
        }
        //已提现
        if (id == R.id.rl_e) {
            Intent intent = new Intent(getActivity(), Extra_Record.class);
            intent.putExtra("gets", "1");
            startActivity(intent);

        }
        //返利明细
        if (id == R.id.rl_f) {
            Intent intent = new Intent(getActivity(), Rebate_Detail.class);
            intent.putExtra("s","1");
            startActivity(intent);
        }
    }

    public static Fragment_Rebateone newInstance() {
        Fragment_Rebateone fragment = new Fragment_Rebateone();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
