package com.huihao.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.adapter.ProductGridAda;
import com.huihao.adapter.ProductPageAdapter;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.common.UntilList;
import com.huihao.custom.CustomDialog;
import com.huihao.custom.DatePickerFragment;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.MyScrollView;
import com.huihao.custom.NoScrollGridView;
import com.huihao.custom.PagerSlidingTabStrip;
import com.huihao.custom.TagGroup;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

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
    private View dialogView;

    private TagGroup tagGroup;
    private String OKSTATE;

    private LinearLayout lin_choose;
    private LinearLayout lin_ok;

    private int choose_num = 1;

    private Button et_num;
    private boolean isAdd = true;
    private int index = 500;

    private ImageView imageAdd;

    private Button btn_shop, btn_num;
    private int num = 0;

    private AnimationSet animationSet;

    private boolean isCanAdd=true;


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
        lin_ok.setVisibility(View.VISIBLE);
        lin_choose.setVisibility(View.GONE);
        OKSTATE = "BUY";
        showBuyDialog();
    }

    public void add(View v) {
        lin_ok.setVisibility(View.VISIBLE);
        lin_choose.setVisibility(View.GONE);
        OKSTATE = "ADD";
        showBuyDialog();
    }

    public void back(View v) {
        finish();
    }

    public void shopcart(View v) {

    }

    public void choose(View v) {
        lin_choose.setVisibility(View.VISIBLE);
        lin_ok.setVisibility(View.GONE);
        showBuyDialog();
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
        btn_shop = (Button) findViewById(R.id.btn_shop);
        btn_num = (Button) findViewById(R.id.shop_num);

        imageAdd = (ImageView) findViewById(R.id.image_add);
        imageAdd.setImageResource(R.mipmap.image1);

        toolbar = (RelativeLayout) findViewById(R.id.toolbar);

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);

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


        dialogView = getLayoutInflater().inflate(R.layout.activity_product_details_dialog, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(dialogView, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
        initDialog(dialogView);
    }

    private void initDialog(View view) {
        RelativeLayout btn_close = (RelativeLayout) view.findViewById(R.id.btn_close);
        lin_choose = (LinearLayout) dialogView.findViewById(R.id.Lin_choose);
        lin_ok = (LinearLayout) dialogView.findViewById(R.id.Lin_ok);

        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        Button btn_buy = (Button) view.findViewById(R.id.btn_buy);
        Button btn_l = (Button) view.findViewById(R.id.btn_l);
        Button btn_r = (Button) view.findViewById(R.id.btn_r);
        et_num = (Button) view.findViewById(R.id.et_num);
        et_num.setText(choose_num + "");
        ImageView image_product = (ImageView) view.findViewById(R.id.image);

        InitAnima();

        btn_l.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        new Thread(new Runnable() {
                            public void run() {
                                isAdd = true;
                                index = 300;
                                while (isAdd) {
                                    try {
                                        handler.sendEmptyMessage(1);
                                        Thread.sleep(index);
                                        if (index > 20) {
                                            index -= 10;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                    case MotionEvent.ACTION_UP: {
                        isAdd = false;
                        index = 300;
                        break;
                    }
                }
                return false;
            }
        });

        btn_r.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        new Thread(new Runnable() {
                            public void run() {
                                isAdd = true;
                                index = 300;
                                while (isAdd) {
                                    try {
                                        handler.sendEmptyMessage(0);
                                        Thread.sleep(index);
                                        if (index > 20) {
                                            index -= 10;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                    case MotionEvent.ACTION_UP: {
                        isAdd = false;
                        index = 300;
                        break;
                    }
                }
                return false;
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isCanAdd) {
                    isCanAdd=false;
                    dialog.dismiss();
                    scrollView.invalidate();
                    imageAdd.startAnimation(animationSet);
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (OKSTATE.equals("ADD")) {
                    if(isCanAdd) {
                        isCanAdd=false;
                        dialog.dismiss();
                        scrollView.invalidate();
                        imageAdd.startAnimation(animationSet);
                    }
                } else if (OKSTATE.equals("BUY")) {

                } else {
                }
            }
        });

        tagGroup = (TagGroup) view.findViewById(R.id.tag_group);
        List<String> list = new ArrayList<String>();
        list.add("350ml");
        list.add("500ml");
        list.add("800ml");
        list.add("豪华精装3合一大礼包");
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

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (choose_num < 999) {
                    choose_num++;
                    et_num.setText(choose_num + "");
                }
            } else if (msg.what == 1) {
                if (choose_num > 1) {
                    choose_num--;
                    et_num.setText(choose_num + "");
                }
            }
            super.handleMessage(msg);
        }
    };

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

    private void InitAnima() {
        animationSet = new AnimationSet(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0f, 45f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(0);
        rotateAnimation.setFillAfter(true);
        animationSet.addAnimation(rotateAnimation);


        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.0f, 2.5f, 0.0f, 2.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(1200);
        scaleAnimation1.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation1);

        ScaleAnimation scaleAnimation2 = new ScaleAnimation(2.5f, 0.0f, 2.5f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(800);
        scaleAnimation2.setStartOffset(800);
        scaleAnimation2.setFillAfter(true);
        scaleAnimation2.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {

            }
            public void onAnimationEnd(Animation animation) {
                addProduct(Integer.parseInt(et_num.getText().toString()));
                isCanAdd=true;
            }
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.addAnimation(scaleAnimation2);

        Animation translateAnimation = new TranslateAnimation(0, UntilList.getWindosW(Product_details.this)+UntilList.dip2px(Product_details.this,32)+btn_shop.getWidth(), 0, -UntilList.getWindosH(Product_details.this)-UntilList.dip2px(Product_details.this,40));
        translateAnimation.setDuration(2200);
        translateAnimation.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);


        animationSet.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                imageAdd.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
                imageAdd.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.setStartOffset(0);
    }


    public void addProduct(int add) {
        num += add;
        if (num > 0) {
            btn_num.setText(num + "");
        }
        if (num > 99) {
            btn_num.setText(99 + "");
        }
        btn_num.setVisibility(View.VISIBLE);
    }
}
