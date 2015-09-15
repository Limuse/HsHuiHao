package com.huihao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.activity.Address;
import com.huihao.activity.All_Orders;
import com.huihao.activity.ExtractActivity;
import com.huihao.activity.InvitationCode;
import com.huihao.activity.More;
import com.huihao.activity.My_Coupons;
import com.huihao.activity.My_Partner;
import com.huihao.activity.News;
import com.huihao.activity.PersonSet;
import com.huihao.activity.Rebate;
import com.huihao.activity.Update_Num;
import com.huihao.common.CircleBitmapDisplayer;
import com.huihao.common.Token;
import com.huihao.entity.MyEntiy;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_my extends LFragment implements View.OnClickListener {
    private View parentView;

    private ImageView person_image;
    private ImageView emails;
    private TextView tv_name;
    private TextView btn_1, btn_2, btn_3, tv_1, tv_2, tv_3, img_n;
    private RelativeLayout rl_news, rl_image, rl_order, rl_address, rl_account, rl_yaoqing, rl_share, rl_rebate, rl_partner, rl_more, rl_parper, rl_tv1, rl_tv2, rl_tv3;
    private ImageView myself_image;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private String img;
    private MyEntiy.StatusListEntity statusListEntity;
    public static Fragment_my instance = null;
    private List<MyEntiy.StatusListEntity> listEntities = new ArrayList<>();
    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_my,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance = Fragment_my.this;
        initView();
        Data();
    }

    private void initView() {
        myself_image = (ImageView) getActivity().findViewById(R.id.myself_image);
        emails = (ImageView) getActivity().findViewById(R.id.person_emlss);//消息
        rl_news = (RelativeLayout) getActivity().findViewById(R.id.person_emls);
        rl_image = (RelativeLayout) getActivity().findViewById(R.id.myself_image_rl);//头像部分
        tv_name = (TextView) getActivity().findViewById(R.id.myself_name);
        btn_1 = (TextView) getActivity().findViewById(R.id.person_no_pay);//待付款
        btn_2 = (TextView) getActivity().findViewById(R.id.person_rec);//待收货
        btn_3 = (TextView) getActivity().findViewById(R.id.person_back_money);//退款中
        tv_1 = (TextView) getActivity().findViewById(R.id.tv_1);
        tv_2 = (TextView) getActivity().findViewById(R.id.tv_2);
        tv_3 = (TextView) getActivity().findViewById(R.id.tv_3);
        img_n = (TextView) getActivity().findViewById(R.id.img_n);
        rl_order = (RelativeLayout) getActivity().findViewById(R.id.orders);
        rl_address = (RelativeLayout) getActivity().findViewById(R.id.address);
        rl_account = (RelativeLayout) getActivity().findViewById(R.id.account);
        rl_yaoqing = (RelativeLayout) getActivity().findViewById(R.id.yaoqing);
        rl_share = (RelativeLayout) getActivity().findViewById(R.id.share);
        rl_rebate = (RelativeLayout) getActivity().findViewById(R.id.rebate);
        rl_partner = (RelativeLayout) getActivity().findViewById(R.id.partner);
        rl_more = (RelativeLayout) getActivity().findViewById(R.id.more);
        rl_parper = (RelativeLayout) getActivity().findViewById(R.id.parper);
        rl_tv1 = (RelativeLayout) getActivity().findViewById(R.id.rl_tv1);
        rl_tv2 = (RelativeLayout) getActivity().findViewById(R.id.rl_tv2);
        rl_tv3 = (RelativeLayout) getActivity().findViewById(R.id.rl_tv3);
        myself_image.setOnClickListener(this);
        rl_news.setOnClickListener(this);
        rl_tv1.setOnClickListener(this);
        rl_tv2.setOnClickListener(this);
        rl_tv3.setOnClickListener(this);


        rl_order.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_rebate.setOnClickListener(this);
        rl_yaoqing.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_partner.setOnClickListener(this);
        rl_more.setOnClickListener(this);
        rl_image.setOnClickListener(this);
        rl_parper.setOnClickListener(this);
        tv_1.setVisibility(View.GONE);
        tv_2.setVisibility(View.GONE);
        tv_3.setVisibility(View.GONE);
    }

    private void img() {
        // 图片
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.title_img)
                .showImageForEmptyUri(R.mipmap.title_img)
                .showImageOnFail(R.mipmap.title_img).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                        //.displayer(new CircleBitmapDisplayer())
                        // .displayer(new RoundedBitmapDisplayer(99))
                .build();
        imageLoader.displayImage(img, myself_image, options);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //消息
        if (id == R.id.person_emls) {
            // T.ss("跳到消息的页面");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), News.class);
                getActivity().startActivity(intent);
                img_n.setVisibility(View.GONE);
            }

        }
        //头像
        if (id == R.id.myself_image) {
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), PersonSet.class);
                intent.putExtra("img", img);
                intent.putExtra("names", tv_name.getText());
                getActivity().startActivity(intent);
            }

        }

        //待付款
        if (id == R.id.rl_tv1) {
            // T.ss("待付款");
            //tv_1.setVisibility(View.VISIBLE);
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), All_Orders.class);
                intent.putExtra("gets", "1");
                getActivity().startActivity(intent);
                tv_1.setVisibility(View.GONE);
            }

        }
        //待发货
        if (id == R.id.rl_tv2) {
            //   T.ss("待发货");
            // tv_2.setVisibility(View.VISIBLE);
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), All_Orders.class);
                intent.putExtra("gets", "2");
                getActivity().startActivity(intent);
                tv_2.setVisibility(View.GONE);
            }
        }
        //待收货
        if (id == R.id.rl_tv3) {
            //  T.ss("待收货");
            //  tv_3.setVisibility(View.VISIBLE);
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), All_Orders.class);
                intent.putExtra("gets", "3");
                getActivity().startActivity(intent);
                tv_3.setVisibility(View.GONE);
            }
        }
        //全部订单
        if (id == R.id.orders) {
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), All_Orders.class);
                intent.putExtra("gets", "0");
                getActivity().startActivity(intent);
            }
        }
        //收货地址
        if (id == R.id.address) {
            // T.ss("收货地址");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), Address.class);
                getActivity().startActivity(intent);
            }
        }
        //我的优惠卷
        if (id == R.id.parper) {
            // T.ss("我的优惠卷");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), My_Coupons.class);
                getActivity().startActivity(intent);
            }
        }
        //提现账户
        if (id == R.id.account) {
            // T.ss("提现账户");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), ExtractActivity.class);
                getActivity().startActivity(intent);
            }
        }
        //优惠码
        if (id == R.id.yaoqing) {
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), Update_Num.class);
                getActivity().startActivity(intent);
            }
        }
        //分享
        if (id == R.id.share) {
            //  T.ss("分享");
            if (MyApplication.isLogin(getActivity())) {
                share();
            }

        }
        //我的返利
        if (id == R.id.rebate) {
            //  T.ss("我的返利");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), Rebate.class);
                getActivity().startActivity(intent);
            }
        }
        //我的伙伴
        if (id == R.id.partner) {
            //T.ss("我的伙伴");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), My_Partner.class);
                getActivity().startActivity(intent);
            }
        }
        //更多
        if (id == R.id.more) {
            // T.ss("更多");
            if (MyApplication.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), More.class);
                getActivity().startActivity(intent);
            }
        }
    }

    public void share() {
// 设置分享内容
        mController.setShareContent("汇好，汇聚天下好产品");
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(getActivity(),
                "http://tb.himg.baidu.com/sys/portrait/item/94edd7eed6d5c5c7bbb22924"));

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getActivity(), appID, appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
        mController.openShare(getActivity(), false);
    }


    @Override
    public void onResume() {
        super.onResume();
        Data();
    }

    private void Data() {

        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/1/sign/aggregation/?uuid=" + Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        FragmentHandler handler = new FragmentHandler(Fragment_my.this);
        handler.startLoadingData(entity, 1);

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
        listEntities.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject list = jsonObject.getJSONObject("list");
                JSONArray array = list.getJSONArray("status_list");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jso = array.getJSONObject(i);
                    statusListEntity = new MyEntiy.StatusListEntity();
                    statusListEntity.setState(jso.getString("state"));

                    statusListEntity.setTotal_num(jso.getString("total_num"));
                    listEntities.add(statusListEntity);
                }
                MyEntiy entitys = new MyEntiy();
                JSONObject jt = list.getJSONObject("user_arr");
                entitys.setHead_img(jt.getString("head_img"));
                String m = jt.getString("head_img");
                if (m.equals(null)) {
                    img = null;
                } else {
                    img = m;
                }
                entitys.setMeassage(jt.getString("meassage"));
                String n =jt.getString("meassage").toString();

                if (n.equals("0") || n.equals(null)) {
                    img_n.setVisibility(View.GONE);

                } else {
                    img_n.setVisibility(View.VISIBLE);
                    img_n.setText(n);
                }
                entitys.setUsername(jt.getString("username"));
                if (entitys.getUsername().equals(null)) {
                    tv_name.setText("汇好昵称");
                } else {
                    tv_name.setText(jt.getString("username"));
                }
                String account=jt.getString("amount");
                String names=jt.getString("name");
                LSharePreference.getInstance(getActivity())
                        .setString("account", account);
                LSharePreference.getInstance(getActivity())
                        .setString("accountname", names);

                img();//头像设置
                setDa();//所有消息的条数
            } else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDa() {
        for (int i = 0; i < listEntities.size(); i++) {
            String state = listEntities.get(i).getState();
            String num = null;
            if (Integer.parseInt(listEntities.get(i).getTotal_num()) > 99) {
                num = "99";
            } else {
                num = listEntities.get(i).getTotal_num();
            }


            if (state.equals("0")) {
                //待付款
                if (num.equals("0")) {
                    tv_1.setVisibility(View.GONE);
                } else {
                    tv_1.setVisibility(View.VISIBLE);

                    tv_1.setText(num);
                }
            } else if (state.equals("1")) {
                //待发货
                if (num.equals("0")) {
                    tv_2.setVisibility(View.GONE);
                } else {
                    tv_2.setVisibility(View.VISIBLE);
                    tv_2.setText(num);
                }
            } else if (state.equals("2")) {
                //待收货
                if (num.equals("0")) {
                    tv_3.setVisibility(View.GONE);
                } else {
                    tv_3.setVisibility(View.VISIBLE);
                    tv_3.setText(num);
                }
            }

        }
    }

}
