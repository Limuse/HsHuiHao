package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.activity.ExR_State;
import com.huihao.activity.Extra_Record;
import com.huihao.activity.HomeMain;
import com.huihao.activity.LoginMain;
import com.huihao.activity.Rebate;
import com.huihao.adapter.ExtraRecodeAdapter;
import com.huihao.common.Token;
import com.huihao.entity.ExtraReEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/3.
 */
public class Fragment_Extraing extends LFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ExtraRecodeAdapter adapter;
    private List<ExtraReEntity> list=null;
    private RelativeLayout rl_accc;
    private Button btn_accc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extraing,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        rl_accc=(RelativeLayout)getActivity().findViewById(R.id.rl_accc);
        btn_accc=(Button)getActivity().findViewById(R.id.btn_accc);
        listView = (ListView) getActivity().findViewById(R.id.lv_extraing);
        btn_accc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeMain.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                Extra_Record.instance.finish();
                Rebate.instance.finish();
                Fragment_my.instance.getActivity().finish();

            }
        });
    }

    private void initData(){
        list = new ArrayList<ExtraReEntity>();
        Resources res=getResources();
        String url=res.getString(R.string.app_service_url)+"/huihao/member/commissionapply/1/sign/aggregation/?t=1&uuid="+ Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_Extraing.this);
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
                JSONArray array = jsonObject.getJSONArray("list");
                if(array.length()<1||array.equals("")||array.equals(null)){
                    rl_accc.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }else{
                    rl_accc.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                for (int i = 0; i < array.length(); i++) {
                    ExtraReEntity entity = new ExtraReEntity();
                    JSONObject jso = array.getJSONObject(i);
                    entity.id = jso.getString("id");
                    entity.state = jso.getString("status");
                    entity.time =jso.getString("ctime");
                    entity.money =jso.getString("amount");
                    list.add(entity);
                }
                adapter = new ExtraRecodeAdapter(getActivity(), list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this);
            }  } else {
                T.ss(jsonObject.getString("info"));
                String longs=jsonObject.getString("info");
                if(longs.equals("请先登录")){
                    LSharePreference.getInstance(getActivity()).setBoolean("login", false);
                    Intent intent = new Intent(getActivity(), LoginMain.class);
                    startActivity(intent);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_accc.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public static Fragment_Extraing newInstance() {
        Fragment_Extraing fragment = new Fragment_Extraing();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExtraReEntity entity=list.get(position);
        String ts=entity.id;
        Intent intent=new Intent(getActivity(), ExR_State.class);
        intent.putExtra("state",ts+"");
        startActivity(intent);
    }
}
