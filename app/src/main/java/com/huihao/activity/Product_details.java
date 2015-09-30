package com.huihao.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huihao.adapter.ProductGridAda;
import com.huihao.common.Log;
import com.huihao.common.Token;
import com.huihao.common.UntilList;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.TagGroup;
import com.huihao.fragment.Fragment_Product_info;
import com.huihao.fragment.Fragment_shop;
import com.huihao.handle.ActivityHandler;
import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.adapter.ProductPageAdapter;
import com.huihao.common.Bar;
import com.huihao.custom.MyScrollView;
import com.huihao.custom.NoScrollGridView;
import com.huihao.custom.PagerSlidingTabStrip;
import com.huihao.fragment.Fragment_Product_para;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/29.
 */
public class Product_details extends LActivity implements MyScrollView.ScrollViewListener {
    public static Product_details context;

    private String productId;
    private String spec_id;

    private View parentView;
    private MyScrollView scrollView;

    private NoScrollGridView gridView;
    private List<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
    private ProductGridAda myGridAda;

    private List<Map<String, String>> TagList = new ArrayList<Map<String, String>>();

    private ArrayList<String> mImageUrl = new ArrayList<String>();
    private ArrayList<String> mImageName = new ArrayList<String>();
    private ImageCycleView mAdView;

    private PagerSlidingTabStrip tabStrip;
    public ViewPager viewPager;
    private ProductPageAdapter pageAdapter;

    private LinearLayout.LayoutParams linearParams;

    private ScrollView tagScroll;

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

    private boolean isCanAdd = true;

    private TextView name, nprice, oprice, realsalenum;

    private TextView tv_price, tv_size, tv_num;

    private List<Map<String, String>> imageList = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();

    public static DisplayImageOptions options;
    public static ImageLoader imageLoader;
    private ImageView image_product;

    private int content;

    public static float WcH = 640f / 600f;

    private RelativeLayout rel_tag;
    private final static float TARGET_HEAP_UTILIZATION = 0.75f;
    protected void onLCreate(Bundle bundle) {

        parentView = getLayoutInflater().inflate(R.layout.activity_product_details, null);
        setContentView(parentView);
        Bar.setWhite(this);
        context = this;

        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();

        initView();

        Intent intent = getIntent();
        if (intent.hasExtra("isBuy")) {
            lin_ok.setVisibility(View.VISIBLE);
            lin_choose.setVisibility(View.GONE);
            OKSTATE = "BUY";
            showBuyDialog();
        }

        initData();
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
        if (MyApplication.isLogin(Product_details.this)) {
            LSharePreference.getInstance(this).setBoolean("isSwitchS",true);
            finish();
        }
    }

    public void choose(View v) {
        lin_choose.setVisibility(View.VISIBLE);
        lin_ok.setVisibility(View.GONE);
        showBuyDialog();
    }

    private void initAda() {
        //        mAdView.setImageResources(mImageUrl, mImageName, mAdCycleViewListener);
        myGridAda = new ProductGridAda(this, gridList);
        gridView.setAdapter(myGridAda);
    }

    private void initView() {
        name = (TextView) findViewById(R.id.tv_title);
        nprice = (TextView) findViewById(R.id.tv_money_now);
        oprice = (TextView) findViewById(R.id.tv_money_old);
        oprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        realsalenum = (TextView) findViewById(R.id.tv_num);

        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        mAdView = (ImageCycleView) findViewById(R.id.ImageCycleView);

        gridView = (NoScrollGridView) findViewById(R.id.gridView);
        btn_shop = (Button) findViewById(R.id.btn_shop);
        btn_num = (Button) findViewById(R.id.shop_num);

        imageAdd = (ImageView) findViewById(R.id.image_add);

        toolbar = (RelativeLayout) findViewById(R.id.toolbar);

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);

        Top1 = (LinearLayout) findViewById(R.id.group_top1);
        Top2 = (LinearLayout) findViewById(R.id.group_top2);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageAdapter = new ProductPageAdapter(this, getSupportFragmentManager());
        viewPager.setFocusable(false);
        viewPager.setAdapter(pageAdapter);
        tabStrip.setViewPager(viewPager);

        bgColor = (RelativeLayout) findViewById(R.id.bg_color);

        ViewTreeObserver vto = mAdView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (!isGetHight) {
                    isGetHight = true;
                    ViewHight = mAdView.getMeasuredHeight();
                    linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
//                    PageHight = LSharePreference.getInstance(context).getInt("pager1");
//                    linearParams.height = PageHight;
//                    viewPager.setLayoutParams(linearParams);
                    TabHitht = Top2.getTop() - toolbar.getHeight();
//                    TabHitht = Top2.getTop();

//                    T.ss(LSharePreference.getInstance(context).getInt("pager1")+"-----"+LSharePreference.getInstance(context).getInt("pager2"));

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

        rel_tag = (RelativeLayout) dialogView.findViewById(R.id.rel_tag);

        tv_price = (TextView) dialogView.findViewById(R.id.tv_price);
        tv_size = (TextView) dialogView.findViewById(R.id.tv_size);
        tv_num = (TextView) dialogView.findViewById(R.id.tv_num);

        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        Button btn_buy = (Button) view.findViewById(R.id.btn_buy);
        Button btn_l = (Button) view.findViewById(R.id.btn_l);
        Button btn_r = (Button) view.findViewById(R.id.btn_r);
        et_num = (Button) view.findViewById(R.id.et_num);
        et_num.setText(choose_num + "");
        image_product = (ImageView) view.findViewById(R.id.image);
        tagGroup = (TagGroup) view.findViewById(R.id.tag_group);
        tagGroup.setVisibility(View.GONE);
        tagScroll = (ScrollView) view.findViewById(R.id.tag_scroll);
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
                if (MyApplication.isLogin(Product_details.this)) {
                    Intent intent = new Intent(Product_details.this, Submit_Orders.class);
                    intent.putExtra("spec_id", spec_id + "");
                    intent.putExtra("spec_num", choose_num + "");
                    startActivity(intent);
                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MyApplication.isLogin(Product_details.this)) {
                    if (isCanAdd) {
                        isCanAdd = false;
                        dialog.dismiss();
                        addForHttp();
                    }
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MyApplication.isLogin(Product_details.this)) {
                    if (OKSTATE.equals("ADD")) {
                        if (isCanAdd) {
                            isCanAdd = false;
                            dialog.dismiss();
                            addForHttp();
                        }
                    } else if (OKSTATE.equals("BUY")) {
                        Intent intent = new Intent(Product_details.this, Submit_Orders.class);
                        intent.putExtra("spec_id", spec_id + "");
                        intent.putExtra("spec_num", choose_num + "");
                        startActivity(intent);
                    } else {
                    }
                }
            }
        });
    }

    public void setTagInfo(int i) {
        tv_price.setText(TagList.get(i).get("nprice"));
        tv_size.setText(TagList.get(i).get("spec"));
        tv_num.setText(TagList.get(i).get("maxnum"));
        spec_id = TagList.get(i).get("spec_id");
    }

    public void setPageH() {
//        linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
//        PageHight = LSharePreference.getInstance(context).getInt("pager1");
//
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(100);
//                    if (PageHight > 100) {
//                        handler.sendEmptyMessage(10);
//                    } else {
//                        setPageH();
//                    }
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//        if (content > 10) {
        linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        linearParams.height = (int) ((UntilList.getWindosW(this) / WcH * content));
        viewPager.setLayoutParams(linearParams);
//        } else {
//            linearParams = (LinearLayout.Lay  outParams) viewPager.getLayoutParams();
//            linearParams.height = (int) (UntilList.getWindosW(this) / WcH * (content-1));
//            viewPager.setLayoutParams(linearParams);
//        }
    }

    public void addForHttp() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", Token.get(this));
        map.put("num", choose_num + "");
        map.put("id", spec_id);
        String url = getResources().getString(R.string.app_service_url) + "/huihao/cart/add/1/sign/aggregation/";
        LReqEntity entity = new LReqEntity(url, map);
        L.e(entity+"");
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 2);
    }


    private void initScroll() {
        scrollView.setScrollViewListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                PageIndex = position;
                if (position == 0) {
//                    linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
//                    PageHight = LSharePreference.getInstance(context).getInt("pager1");
//                    linearParams.height = PageHight;
//                    viewPager.setLayoutParams(linearParams);
                    setPageH();
                } else if (position == 1) {
                    linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                    PageHight = LSharePreference.getInstance(context).getInt("pager2");
                    linearParams.height = PageHight;
                    viewPager.setLayoutParams(linearParams);
                }
                scrollView.setScrollY((int)TabHitht);
            }


            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        productId = intent.getStringExtra("id");
        String url = getResources().getString(R.string.app_service_url) + "/huihao/product/detail/1/sign/aggregation/?id=" + productId;
        ActivityHandler handler = new ActivityHandler(this);
        LReqEntity entity = new LReqEntity(url);
        handler.startLoadingData(entity, 1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getSmJsonData(msg.getStr());
            } else if (requestId == 2) {
                getAddState(msg.getStr());
            } else if (requestId == 3) {
                getShopNumData(msg.getStr());
            }  else {
                T.ss("参数ID错误");
            }
        } else {
            T.ss("数据获取失败");
        }
    }

    private void getAddState(String str) {
        try {
            JSONObject object = new JSONObject(str);
            int status = object.optInt("status");
            if (status == 1) {
                T.ss("添加至购物车成功");
                scrollView.invalidate();
                imageAdd.startAnimation(animationSet);
            }
            else {
                T.ss("添加至购物车失败");
                isCanAdd = true;
            }
        } catch (Exception e) {
            isCanAdd = true;
        }
    }

    private void getSmJsonData(String str) {
        try {
            JSONObject object = new JSONObject(str);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject list = object.optJSONObject("list");
                JSONObject info = list.optJSONObject("info");
                name.setText(info.optString("title"));
                nprice.setText("￥" + info.optString("nprice"));
                oprice.setText("￥" + info.optString("oprice"));
                realsalenum.setText(info.optString("preferential").split("\\.")[0]);
                if (!info.opt("description").equals("")) {
                    JSONArray description = info.optJSONArray("description");
                    for (int i = 0; i < description.length(); i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", description.opt(i).toString());
                        gridList.add(map);
                    }
                    initAda();
                }
                if (!info.opt("infopic").equals("")) {
                    JSONArray infopic = info.optJSONArray("infopic");
                    if (infopic.length() > 0) {
                        for (int i = 0; i < infopic.length(); i++) {
                            String item = infopic.opt(i).toString();
                            mImageName.add(i + "");
                            mImageUrl.add(item);
                        }
                        mAdView.setImageResources(mImageUrl, mImageName, mAdCycleViewListener);
                        mAdView.stopImageTimerTask();
                    }
                }
                if (!list.opt("infospec").equals("")) {
                    JSONArray infospec = list.optJSONArray("infospec");
                    List<String> specList = new ArrayList<String>();
                    Map<String, String> map = new HashMap<String, String>();
                    if (infospec.length() > 0) {

                        linearParams = (LinearLayout.LayoutParams) tagScroll.getLayoutParams();

                        if (infospec.length() <= 3) {
                            linearParams.height =UntilList.dip2px(Product_details.this, 40)+(infospec.length())*UntilList.dip2px(Product_details.this, 40)/2;
                            tagScroll.setLayoutParams(linearParams);
                        } else {
                            linearParams.height = UntilList.dip2px(Product_details.this, 120);
                            tagScroll.setLayoutParams(linearParams);
                        }
                        for (int i = 0; i < infospec.length(); i++) {
                            map = new HashMap<String, String>();
                            JSONObject item = infospec.optJSONObject(i);
                            map.put("spec_id", item.opt("spec_id").toString());
                            map.put("title", item.opt("title_1").toString());
                            map.put("spec", item.opt("spec_1").toString());
                            map.put("maxnum", "库存" + item.opt("maxnum").toString() + "件");
                            map.put("nprice", "￥" + item.opt("nprice").toString());
                            TagList.add(map);
                            specList.add(item.opt("spec_1").toString());
                        }
                        spec_id = TagList.get(0).get("spec_id");
                    } else {
                        map.put("title", "暂无数据");
                        map.put("spec", "暂无数据");
                        map.put("maxnum", "暂无数据");
                        map.put("nprice", "暂无数据");
                        TagList.add(map);
                        specList.add("暂无");
                    }
                    tagGroup.setTags(specList);
                    tagGroup.getTagViewAt(0).setChecked(true);
                    setTagInfo(0);
                    tagGroup.setVisibility(View.VISIBLE);
                }

                if (!info.opt("parameter").equals("")) {
                    JSONArray parameter = info.optJSONArray("parameter");
                    for (int i = 0; i < parameter.length(); i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        String para = parameter.get(i).toString();
                        if (para.length() > 0) {
                            map.put("title", parameter.get(i).toString());
                            Fragment_Product_para.addItemH(para.length() / 25);
                        }
                        contentList.add(map);
                    }
                    if (contentList.size() > 0) {
                        Fragment_Product_para.context.InitData(contentList);
                    }
                }

                imageLoader.displayImage(info.optString("app_cartpic"), image_product, options);
                imageLoader.displayImage(info.optString("app_cartpic"), imageAdd, options);

                if (!info.opt("content").equals("")) {
                    JSONArray content = info.optJSONArray("content");
                    this.content = content.length();
                    setPageH();
                    for (int i = 0; i < content.length(); i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("image", content.opt(i).toString());
                        imageList.add(map);
                    }
                    if (imageList.size() > 0) {
                        Fragment_Product_info.context.InitData(imageList);
                    }
                }
            } else {
                T.ss("数据获取失败");
            }
        } catch (Exception e) {
            T.ss("数据解析失败");
//            T.ss(e.getMessage());
        }
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            if (mImageUrl.size() > 0) {
                Intent intent = new Intent(Product_details.this, ImageDetail.class);
                intent.putExtra("ProjectImg", mImageUrl);
                intent.putExtra("index", position);
                startActivity(intent);
            } else {
                T.ss("该产品暂无详情图");
            }
        }
    };

    public void ImageDetails(int position, ArrayList<String> ImageUrl) {
        Intent intent = new Intent(Product_details.this, ImageDetail.class);
        intent.putExtra("ProjectImg", ImageUrl);
        intent.putExtra("index", position);
        startActivity(intent);
    }


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

            if (msg.what == 10) {
                linearParams.height = PageHight;
                viewPager.setLayoutParams(linearParams);
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
                isCanAdd = true;
            }

            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.addAnimation(scaleAnimation2);

        Animation translateAnimation = new TranslateAnimation(0, UntilList.getWindosW(Product_details.this) + UntilList.dip2px(Product_details.this, 32) + btn_shop.getWidth(), 0, -UntilList.getWindosH(Product_details.this) - UntilList.dip2px(Product_details.this, 48));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fragment_Product_para.clearItemH();
        mAdView.stopImageTimerTask();
        imageLoader.clearMemoryCache();
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LSharePreference.getInstance(this).getBoolean("login")) {
            getShopNum();
        }
    }
    public void getShopNum() {
        String url = getResources().getString(R.string.app_service_url) + "/huihao/cart/statistics/1/sign/aggregation/";
        ActivityHandler handler = new ActivityHandler(Product_details.this);
        Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", Token.get(this));
        LReqEntity entity = new LReqEntity(url, map);
        handler.startLoadingData(entity, 3);
    }
    private void getShopNumData(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status=jsonObject.optInt("status");
            if(status==1){
                String num=jsonObject.optString("total_num");
                this.num=Integer.parseInt(num);
                if(Integer.parseInt(num)>0&&Integer.parseInt(num)<=99){
                    btn_num.setVisibility(View.VISIBLE);
                    btn_num.setText(num);
                }else if(Integer.parseInt(num)==0){
                    btn_num.setVisibility(View.GONE);
                }else if(Integer.parseInt(num)>99){
                    btn_num.setVisibility(View.VISIBLE);
                    btn_num.setText("99");
                }
            }else {
                T.ss("返回状态失败");
            }
        }
        catch(Exception e){
            T.ss("数据解析失败");
        }
    }
}