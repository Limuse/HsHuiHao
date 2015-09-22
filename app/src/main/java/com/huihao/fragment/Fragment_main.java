package com.huihao.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huihao.activity.News;
import com.huihao.activity.Product_details;
import com.huihao.common.Log;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.ImageCycleViewMain;
import com.huihao.custom.NoScrollGridView;
import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.adapter.MainGridAda;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */


public class Fragment_main extends LFragment {
    private View parentView;
    private ScrollView scrollView;
    private ImageCycleViewMain mAdView;
    private ArrayList<String> pageImageList = new ArrayList<String>();
    private ArrayList<String> pageImageId = new ArrayList<String>();
    private NoScrollGridView gridView;

    private List<Map<String, String>> mainProductList = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
    private MainGridAda myGridAda;
    private RelativeLayout r1, r2, r3, r4, r5, msg;
    private Button btn1, btn2;
    private TextView name1, name2, name3, name4, name5;
    private TextView price1, price2, price3, price4, price5;
    private ImageView image1, image2, image3, image4, image5;
    private TextView[] names, prices;
    private ImageView[] images;

    public static DisplayImageOptions options;
    public static ImageLoader imageLoader;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_main,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();

        initData();
        initView();
        initClick();
        initAda();
    }

    private void initClick() {
        r1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(0).get("id"));
                startActivityForResult(intent, 0);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(1).get("id"));
                startActivityForResult(intent, 0);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(2).get("id"));
                startActivityForResult(intent, 0);
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(3).get("id"));
                startActivityForResult(intent, 0);
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(4).get("id"));
                startActivityForResult(intent, 0);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(0).get("id"));
                intent.putExtra("isBuy", true);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", mainProductList.get(1).get("id"));
                intent.putExtra("isBuy", true);
                startActivity(intent);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MyApplication.isLogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), News.class);
                    startActivity(intent);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Product_details.class);
                intent.putExtra("id", gridList.get(position).get("id"));
                startActivityForResult(intent, 0);
            }
        });
    }


    private void initView() {
        scrollView = (ScrollView) parentView.findViewById(R.id.scrollView);
        scrollView.scrollTo(0, 0);

        r1 = (RelativeLayout) parentView.findViewById(R.id.product1);
        r2 = (RelativeLayout) parentView.findViewById(R.id.product2);
        r3 = (RelativeLayout) parentView.findViewById(R.id.product3);
        r4 = (RelativeLayout) parentView.findViewById(R.id.product4);
        r5 = (RelativeLayout) parentView.findViewById(R.id.product5);
        msg = (RelativeLayout) parentView.findViewById(R.id.btn_mess);

        btn1 = (Button) parentView.findViewById(R.id.tv_buy2);
        btn2 = (Button) parentView.findViewById(R.id.tv_buy1);

        name1 = (TextView) parentView.findViewById(R.id.tv_title2);
        name2 = (TextView) parentView.findViewById(R.id.tv_title1);
        name3 = (TextView) parentView.findViewById(R.id.tv_title3);
        name4 = (TextView) parentView.findViewById(R.id.tv_title4);
        name5 = (TextView) parentView.findViewById(R.id.tv_title5);

        price1 = (TextView) parentView.findViewById(R.id.tv_price2);
        price2 = (TextView) parentView.findViewById(R.id.tv_price1);
        price3 = (TextView) parentView.findViewById(R.id.tv_price3);
        price4 = (TextView) parentView.findViewById(R.id.tv_price4);
        price5 = (TextView) parentView.findViewById(R.id.tv_price5);

        image1 = (ImageView) parentView.findViewById(R.id.image2);
        image2 = (ImageView) parentView.findViewById(R.id.image1);
        image3 = (ImageView) parentView.findViewById(R.id.image3);
        image4 = (ImageView) parentView.findViewById(R.id.image4);
        image5 = (ImageView) parentView.findViewById(R.id.image5);

        names = new TextView[]{name1, name2, name3, name4, name5};
        prices = new TextView[]{price1, price2, price3, price4, price5};
        images = new ImageView[]{image1, image2, image3, image4, image5};

        mAdView = (ImageCycleViewMain) parentView.findViewById(R.id.ImageCycleView);
        gridView = (NoScrollGridView) parentView.findViewById(R.id.gridView);
    }

    private void initData() {
        String url = getResources().getString(R.string.app_service_url) + "/huihao/product/1/sign/aggregation/";
        FragmentHandler handler = new FragmentHandler(this);
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
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.optInt("status");
            if (status == 1) {
                JSONObject list = jsonObject.optJSONObject("list");
                JSONArray product_list = list.optJSONArray("product_list");
                for (int i = 0; i < product_list.length(); i++) {
                    JSONObject item = product_list.optJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    String id = item.optString("id");
                    String title = item.optString("title");
                    String nprice = item.optString("nprice");
                    String oprice = item.optString("oprice");
                    String pic_homepage = item.optString("pic_app");
                    String picurl = item.optString("picurl");
                    map.put("id", id);
                    map.put("title", title);
                    map.put("nprice", nprice);
                    map.put("oprice", oprice);
                    map.put("pic_homepage", pic_homepage);
                    map.put("picurl", picurl);
                    if (i <= 4) {
                        names[i].setText(title);
                        prices[i].setText("￥" + nprice);
                        imageLoader.displayImage(picurl, images[i], options);
                        mainProductList.add(map);
                    } else {
                        gridList.add(map);
                    }
                    if (!pic_homepage.equals("")) {
                        pageImageList.add(pic_homepage);
                        pageImageId.add(id);
                    }
                }
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
            } else {
                T.ss("数据获取失败");
            }
        } catch (Exception e) {
            T.ss("数据解析失败");
        }
        initAda();
    }

    private void initAda() {
        if (pageImageList.size() > 0 && pageImageList.size() > 0) {
            mAdView.setImageResources(pageImageList, pageImageId, mAdCycleViewListener);
        }
        myGridAda = new MainGridAda(getActivity(), gridList);
        gridView.setFocusable(false);
        gridView.setAdapter(myGridAda);
    }

    private ImageCycleViewMain.ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewMain.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
            Intent intent = new Intent(getActivity(), Product_details.class);
            intent.putExtra("id", pageImageId.get( ImageCycleViewMain.mImageIndex));
            startActivity(intent);
        }
    };
}
