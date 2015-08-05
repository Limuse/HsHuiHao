package com.huihao.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.adapter.ProductGridAda;
import com.huihao.adapter.ProductPageAdapter;
import com.huihao.common.Bar;
import com.huihao.custom.CustomDialog;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.MyScrollView;
import com.huihao.custom.NoScrollGridView;
import com.huihao.custom.PagerSlidingTabStrip;
import com.huihao.custom.TagGroup;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/29.
 */
public class Product_details extends LActivity implements MyScrollView.ScrollViewListener {
    private View parentView;
    private MyScrollView scrollView;

    private NoScrollGridView gridView;
    private List<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
    private ProductGridAda myGridAda;

    private ArrayList<String> mImageUrl = null;
    private ArrayList<String> mImageName = null;
    private TextView tv_old;
    private ImageCycleView mAdView;

    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private ProductPageAdapter pageAdapter;

    private LinearLayout.LayoutParams linearParams;

    private RelativeLayout bgColor;

    private int ViewHight;
    private int PageHight;
    private float TabHitht;

    private int PageIndex = 0;

    private boolean isGetHight = false;
    private boolean isTopVisibility = false;

    private RelativeLayout toolbar;
    private LinearLayout Top1, Top2;

    private Dialog dialog;

    private TagGroup tagGroup;

    protected void onLCreate(Bundle bundle) {
        parentView = getLayoutInflater().inflate(R.layout.activity_product_details, null);
        setContentView(parentView);
        Bar.setWhite(this);
        initData();
        initView();
        initAda();
        initScroll();
    }

    public void buy(View v) {
        showBuyDialog();
    }

    public void add(View v) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("自定义的提示框");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    public void back(View v) {
        finish();
    }

    public void shopcart(View v) {

    }

    public void choose(View v) {

    }

    private void initAda() {
        myGridAda = new ProductGridAda(this, gridList);
        gridView.setAdapter(myGridAda);

        pageAdapter = new ProductPageAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        tabStrip.setViewPager(viewPager);
    }

    private void initView() {
        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        mAdView = (ImageCycleView) findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(mImageUrl, mImageName, mAdCycleViewListener);
        tv_old = (TextView) findViewById(R.id.tv_money_old);
        tv_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        gridView = (NoScrollGridView) findViewById(R.id.gridView);

        toolbar = (RelativeLayout) findViewById(R.id.toolbar);

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
//        tabStrip_top = (PagerSlidingTabStrip) findViewById(R.id.tab_strip_top);

        Top1 = (LinearLayout) findViewById(R.id.group_top1);
        Top2 = (LinearLayout) findViewById(R.id.group_top2);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        bgColor = (RelativeLayout) findViewById(R.id.bg_color);

        ViewTreeObserver vto = mAdView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (!isGetHight) {
                    isGetHight = true;
                    ViewHight = mAdView.getMeasuredHeight();
                    linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                    PageHight = ViewHight * 5;
                    linearParams.height = PageHight;
                    viewPager.setLayoutParams(linearParams);
                    TabHitht = Top2.getTop() - toolbar.getHeight();
                }
            }
        });


        View view=getLayoutInflater().inflate(R.layout.activity_product_details_dialog, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
        initDialog(view);
    }

    private void initDialog(View view) {
        tagGroup=(TagGroup)view.findViewById(R.id.tag_group);
        List<String> list = new ArrayList<String>();
        list.add("350ml");
        list.add("500ml");
        list.add("800ml");
        list.add("1000ml");
        list.add("2500ml");
        tagGroup.setTags(list);
    }

    private void initScroll() {
        scrollView.setScrollViewListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                PageIndex = position;
                if (position == 0) {
                    linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                    PageHight = ViewHight * 5;
                    linearParams.height = PageHight;
                    viewPager.setLayoutParams(linearParams);

                } else if (position == 1) {
                    linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                    PageHight = ViewHight * 10;
                    linearParams.height = PageHight;
                    viewPager.setLayoutParams(linearParams);
                }
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mImageUrl = new ArrayList<String>();
        mImageUrl.add("http://imgsrc.baidu.com/forum/w%3D580/sign=d2391eb40846f21fc9345e5bc6256b31/8381b4c379310a55b6ea8ebbb54543a9802610ed.jpg");
        mImageUrl.add("http://s5.tuanimg.com/deal/deal_image/3029/2612/medium/webp-23642600-7f0d-4478-8345-ddecfa91a2ce.jpg");
        mImageUrl.add("http://img2.duitang.com/uploads/item/201302/19/20130219115924_ZLNnS.thumb.600_0.jpeg");
        mImageName = new ArrayList<String>();
        mImageName.add("1");
        mImageName.add("2");
        mImageName.add("3");

        for (int i = 0; i < 4; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "不要钱");
            gridList.add(map);
        }
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            Toast.makeText(Product_details.this, position + "", Toast.LENGTH_SHORT).show();
        }
    };

    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        bgColor.setAlpha(Float.valueOf(y / TabHitht + "f"));
        if (y >= TabHitht) {
            isTopVisibility = true;

            if (tabStrip.getParent() != Top1) {
                Top2.removeView(tabStrip);
                Top1.addView(tabStrip);
            }
        } else {
            isTopVisibility = false;

            if (tabStrip.getParent() != Top2) {
                Top1.removeView(tabStrip);
                Top2.addView(tabStrip);
            }
        }
    }

    private void showBuyDialog() {
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.Dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
