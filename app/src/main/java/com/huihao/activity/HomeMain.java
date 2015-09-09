package com.huihao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.huihao.common.UntilList;
import com.huihao.custom.ImageDialog;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_shop;
import com.huihao.fragment.Fragment_story;
import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.fragment.Fragment_my;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2015/6/26.
 */

/**
 * Tag
 */
public class HomeMain extends LActivity {

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @InjectView(R.id.main)
    Button main;
    @InjectView(R.id.story)
    Button story;
    @InjectView(R.id.shop)
    Button shop;
    @InjectView(R.id.my)
    Button my;

    @InjectView(R.id.relmain)
    RelativeLayout relmain;
    @InjectView(R.id.relstory)
    RelativeLayout relstory;
    @InjectView(R.id.relshop)
    RelativeLayout relshop;
    @InjectView(R.id.relmy)
    RelativeLayout relmy;

    @InjectView(R.id.main_view)
    LinearLayout linearLayout;


    private Fragment_main fragment_main;
    private Fragment_shop fragment_shop;
    private Fragment_my fragment_my;
    private Fragment_story fragment_story;

    public static final String MAIN = "main";
    public static final String STORY = "story";
    public static final String SHOP = "shop";
    public static final String MY = "my";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;

    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);

        JPushInterface.init(getApplicationContext());

        ButterKnife.inject(this);
        Bar.setWhite(this);
        Log.e(UntilList.getAppInfo(this));
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.relmain)
    void main() {
        if (hideTag.equals(MAIN))
            return;
        if (fragment_main == null) {
            fragment_main = new Fragment_main();
        }
        setBg();


        main.setBackgroundResource(R.mipmap.mainp);
        switchFragment(fragment_main, MAIN);

    }

    @OnClick(R.id.relstory)
    void story() {
        if (hideTag.equals(STORY))
            return;
        if (fragment_story == null) {
            fragment_story = new Fragment_story();
        }
        setBg();
        story.setBackgroundResource(R.mipmap.storyp);
        switchFragment(fragment_story, STORY);

        linearLayout.setFitsSystemWindows(true);
        linearLayout.setClipToPadding(true);
    }

    @OnClick(R.id.relshop)
    void shop() {
        if (hideTag.equals(SHOP))
            return;
        if (fragment_shop == null) {
            fragment_shop = new Fragment_shop();
        }
        setBg();

        shop.setBackgroundResource(R.mipmap.shopp);
        switchFragment(fragment_shop, SHOP);

    }



    @OnClick(R.id.relmy)
    void my() {
        if (hideTag.equals(MY))
            return;
        if (fragment_my == null) {
            fragment_my = new Fragment_my();
        }
        setBg();

        my.setBackgroundResource(R.mipmap.myp);
        switchFragment(fragment_my, MY);

    }

    public void setBg() {
        main.setBackgroundResource(R.mipmap.mainn);
        story.setBackgroundResource(R.mipmap.storyn);
        shop.setBackgroundResource(R.mipmap.shopn);
        my.setBackgroundResource(R.mipmap.myn);
    }

    private void initView() {

        ImageDialog dialog = new ImageDialog.Builder(this).setImage(R.mipmap.dialog_logo).setMessage("30").setInfo("20", "10").setPositiveButton("暂不分享", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("现在就去", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                share();
                dialog.dismiss();

            }
        }).create();
        dialog.show();


        fragment_main = new Fragment_main();
        main.setBackgroundResource(R.mipmap.mainp);
        switchFragment(fragment_main, MAIN);
    }

    private void switchFragment(Fragment fragment, String tag) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment tagFragment = mFragmentManager.findFragmentByTag(tag);

        if (tagFragment == null) {
            mFragmentTransaction.add(R.id.tabcontent, fragment, tag);
        } else {
            mFragmentTransaction.show(tagFragment);
        }

        tagFragment = mFragmentManager.findFragmentByTag(hideTag);

        if (tagFragment != null) {
            mFragmentTransaction.hide(tagFragment);
        }

        hideTag = tag;
        mFragmentTransaction.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            T.ss("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
    public void share(){
// 设置分享内容
        mController.setShareContent("汇好，汇聚天下好产品");
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(HomeMain.this,
                "http://tb.himg.baidu.com/sys/portrait/item/94edd7eed6d5c5c7bbb22924"));

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(HomeMain.this,appID,appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(HomeMain.this,appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(HomeMain.this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(HomeMain.this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
        mController.openShare(HomeMain.this, false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}