package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huisou on 2015/8/10.
 * 反馈
 */
public class FeedBack extends LActivity {
    private EditText et_desc, et_p;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_feedback);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("反馈");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        //右边图片点击事件
        toolbar.inflateMenu(R.menu.right_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    Resources res = getResources();
                    String url = res.getString(R.string.app_service_url)
                            + "/huihao/member/leaveword/1/sign/aggregation/?uuid=" +
                            Token.get(FeedBack.this) + "&content"
                            + et_desc.getText().toString() +
                            "&phone"+et_p.getText().toString();
                    LReqEntity entity = new LReqEntity(url);
                    ActivityHandler handler = new ActivityHandler(FeedBack.this);
                    handler.startLoadingData(entity, 1);
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        et_desc = (EditText) findViewById(R.id.et_backa);
        et_p = (EditText) findViewById(R.id.ed_fid);

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
                T.ss("提交成功！");

            } else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
