package com.huihao.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.huihao.R;
import com.leo.base.activity.LActivity;
import com.leo.base.util.LSharePreference;

public class WelcomeLoading extends LActivity {

    @Override
    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome_loading);
        init();
    }

    private void init() {
        final boolean isFirstIn = LSharePreference.getInstance(this)
                .getBoolean("first_pref", true);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (!isFirstIn) {
                    getJumpIntent(HomeMain.class);
                } else {
                    getJumpIntent(GuideLoading.class);
                }
            }
        }, 3000);

    }

    private void getJumpIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
        this.finish();
    }
}