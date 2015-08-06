package com.huihao.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

import org.w3c.dom.Text;

/**
 * Created by huisou on 2015/8/3.
 * 提取返利
 */
public class Extract_Rebate extends LActivity implements View.OnClickListener {
    private TextView tv_acc,tv_na;
    private EditText et_mm;
    private Button btn_sure;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_extract_rebate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("提取返利");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(R.color.app_text_dark);

        tv_acc=(TextView)findViewById(R.id.tv_acca);
        tv_na=(TextView)findViewById(R.id.tv_nn);
        et_mm=(EditText)findViewById(R.id.et_mm);
        btn_sure=(Button)findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_sure){
            T.ss("确定");
        }
    }
}
