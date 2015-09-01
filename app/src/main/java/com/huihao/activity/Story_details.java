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

import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.custom.MyViewPager;
import com.huihao.custom.TextViewVertical;
import com.huihao.R;
import com.huihao.fragment.Fragment_story;
import com.huihao.fragment.Fragment_story_details;
import com.huihao.fragment.Fragment_story_page;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/29.
 */
public class Story_details extends LActivity {
    private List<Map<String, String>> imageInfo = new ArrayList<Map<String, String>>();
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

    private String pageId;

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

        textViewVertical.setText("释放查看宝贝详情");
        textViewVertical.setTextSize(getResources().getDimension(R.dimen.app_text_big));
        textViewVertical.setTextColor(getResources().getColor(R.color.app_text_dark));

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

    public void back(View v) {
        finish();
    }

    private void initData() {
        Intent intent=getIntent();
        pageId=intent.getStringExtra("id");
        String url = getResources().getString(R.string.app_service_url) + "/huihao/product/specialdetail/1/sign/aggregation/?id="+pageId;
        Log.e(url);
        ActivityHandler handler = new ActivityHandler(this);
        LReqEntity entity = new LReqEntity(url);
        handler.startLoadingData(entity, 1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getSmJsonData(msg.getStr());
            } else {
                T.ss("参数ID错误");
            }
        } else {
            T.ss("数据获取失败");
        }
    }

    private void getSmJsonData(String str) {
        try {
            JSONObject object = new JSONObject(str);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject list = object.optJSONObject("list");
                JSONArray special_detail = list.optJSONArray("special_detail");
                for (int i = 0; i < special_detail.length(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("image", special_detail.opt(i).toString());
                    imageInfo.add(map);
                }
            } else {
                T.ss("数据获取失败");
            }
        } catch (Exception e) {
            T.ss("数据解析失败");
        }
        initPage();
    }

    private void initPage() {
        for (int i = 0; i < imageInfo.size(); i++) {
            Fragment_story_page fragmentStoryPage = new Fragment_story_page();
            fragmentStoryPage.getData(imageInfo.get(i));
            fragmentList.add(fragmentStoryPage);
        }

        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(0);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                                                             lp.setMargins(-dx / 3, 0, dx / 3, 0);
                                                             viewPager.setLayoutParams(lp);

                                                             lp = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                                                             lp.setMargins(-dx / 3, 0, 0, 0);
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
                                                             intent.putExtra("id",pageId);
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
