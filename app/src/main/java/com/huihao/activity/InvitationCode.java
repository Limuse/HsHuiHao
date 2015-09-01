package com.huihao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.huihao.R;
import com.huihao.common.Bar;
import com.leo.base.activity.LActivity;

/**
 * Created by admin on 2015/8/11.
 */
public class InvitationCode extends LActivity {
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_invitation);
        initBar();
        initView();
    }

    public void initView() {
    }


    private void initBar() {
        Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_Invitation));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.inflateMenu(R.menu.invitation);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_invitation ){
                    finish();
                }
                return false;
            }
        });
    }

    public void ok(View v){

    }
}
