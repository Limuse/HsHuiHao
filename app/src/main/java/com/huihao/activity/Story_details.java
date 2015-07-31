package com.huihao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.custom.MyViewPager;
import com.huihao.custom.TextViewVertical;
import com.huihao.fragment.Fragment_story_details;
import com.huihao.fragment.Fragment_story_page;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/7/29.
 */
public class Story_details extends LActivity {

    private MyViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private TextViewVertical textViewVertical;
    private LinearLayout viewgroup;
    private RelativeLayout textgroup;
    private int currentPage;
    private int lastX;
    private int dx;

    private boolean getWidthFlag = true;
    private int viewWidth;
    private int windosWidth;
    private View view;

    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_story_details);
        Bar.setTrans(this);
        initView();
        initData();
        initPage();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.back_circle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (MyViewPager) findViewById(R.id.viewpage);
        textgroup = (RelativeLayout) findViewById(R.id.textGroup);
        viewgroup = (LinearLayout) findViewById(R.id.viewGroup);
        textViewVertical = (TextViewVertical) findViewById(R.id.text);
        view = (View) findViewById(R.id.view);

        textViewVertical.setText("左划查看详情");
        textViewVertical.setTextSize(30);

        WindowManager wm = this.getWindowManager();
        windosWidth = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        linearParams.weight = windosWidth;
        viewPager.setLayoutParams(linearParams);

        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                viewWidth = view.getMeasuredWidth();
            }
        });
    }

    public void back(View v){
        finish();
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Fragment_story_details fragmentStoryPage = new Fragment_story_details();
            fragmentStoryPage.getData(i + "");
            fragmentList.add(fragmentStoryPage);
        }
    }

    private void initPage() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(0);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                currentPage = position;
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {
                                             if (currentPage == fragmentList.size() - 1) {
                                                 switch (event.getAction()) {
                                                     case MotionEvent.ACTION_DOWN: {
                                                         lastX = (int) event.getRawX();
                                                         break;
                                                     }
                                                     case MotionEvent.ACTION_MOVE: {
                                                         dx = lastX - (int) event.getRawX();
                                                         if (dx > 0) {
                                                             viewPager.setScrollble(false);
                                                             LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                             lp.setMargins(-dx / 2, 0, dx/2, 0);
                                                             viewPager.setLayoutParams(lp);

                                                             lp = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                                                             lp.setMargins(-dx/2 , 0, 0, 0);
                                                             textgroup.setLayoutParams(lp);
                                                         }
                                                         break;
                                                     }
                                                     case MotionEvent.ACTION_UP: {
                                                         viewPager.setScrollble(true);
                                                         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                         lp.setMargins(0, 0, 0, 0);
                                                         viewPager.setLayoutParams(lp);

                                                         lp = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                                                         lp.setMargins(viewWidth, 0, 0, 0);
                                                         textgroup.setLayoutParams(lp);

                                                         if (dx > windosWidth / 2) {
                                                             Intent intent = new Intent(Story_details.this, Product_details.class);
                                                             startActivity(intent);
                                                         }
                                                         break;
                                                     }
                                                     default:
                                                         break;
                                                 }
                                             }
                                             return false;
                                         }
                                     }
        );
    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        public int getCount() {
            return fragmentList.size();
        }
    }

}
