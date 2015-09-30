package com.huihao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.huihao.MyApplication;
import com.huihao.common.Token;
import com.huihao.common.UntilList;
import com.huihao.custom.CustomDialog;
import com.huihao.custom.ImageDialog;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_shop;
import com.huihao.fragment.Fragment_story;
import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.fragment.Fragment_my;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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

    public static HomeMain context;

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @InjectView(R.id.main)
    Button main;
    @InjectView(R.id.story)
    Button story;
    @InjectView(R.id.shop)
    Button shop;
    @InjectView(R.id.my)
    Button my;
    @InjectView(R.id.shop_num)
    Button shop_num;

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

    public Fragment_main fragment_main;
    public Fragment_shop fragment_shop;
    public Fragment_my fragment_my;
    public Fragment_story fragment_story;

    public static final String MAIN = "main";
    public static final String STORY = "story";
    public static final String SHOP = "shop";
    public static final String MY = "my";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;
    private String codes,url;


    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        context = this;
        JPushInterface.init(getApplicationContext());
        ButterKnife.inject(this);
        Bar.setWhite(this);
        Log.e(UntilList.getAppInfo(this));
        initView();
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (LSharePreference.getInstance(this).getBoolean("login")) {
            getShopNum();
        }
        if (LSharePreference.getInstance(this).getBoolean("isSwitchS")) {
            LSharePreference.getInstance(this).setBoolean("isSwitchS", false);
            if (hideTag.equals(SHOP))
                return;
            if (fragment_shop == null) {
                fragment_shop = new Fragment_shop();
            }
            setBg();
            shop.setBackgroundResource(R.mipmap.shopp);
            switchFragment(fragment_shop, SHOP);
        }
        if (LSharePreference.getInstance(this).getBoolean("isSwitchM")) {
            LSharePreference.getInstance(this).setBoolean("isSwitchM", false);
            if (hideTag.equals(MAIN))
                return;
            if (fragment_main == null) {
                fragment_main = new Fragment_main();
            }
            setBg();

            main.setBackgroundResource(R.mipmap.mainp);
            switchFragment(fragment_main, MAIN);
        }
    }


    public void getShopNum() {
        String url = getResources().getString(R.string.app_service_url) + "/huihao/cart/statistics/1/sign/aggregation/";
        ActivityHandler handler = new ActivityHandler(HomeMain.this);
        Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", Token.get(this));
        LReqEntity entity = new LReqEntity(url, map);
        handler.startLoadingData(entity, 1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getShopNumData(msg.getStr());
            } else if (requestId == 2) {
                getJsonnewData(msg.getStr());
            }else {
                T.ss("参数ID错误");
            }
        } else {
            T.ss("购物车数量获取失败");
        }
    }

    private void getShopNumData(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.optInt("status");
            if (status == 1) {
                String num = jsonObject.optString("total_num");
                if (Integer.parseInt(num) > 0 && Integer.parseInt(num) <= 99) {
                    shop_num.setVisibility(View.VISIBLE);
                    shop_num.setText(num);
                } else if (Integer.parseInt(num) == 0) {
                    shop_num.setVisibility(View.GONE);
                } else if (Integer.parseInt(num) > 99) {
                    shop_num.setVisibility(View.VISIBLE);
                    shop_num.setText("99");
                }
            } else {
            }
        } catch (Exception e) {
            T.ss("数据解析失败");
        }
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
        if (MyApplication.isLogin(HomeMain.this)) {
            if (hideTag.equals(SHOP))
                return;
            if (fragment_shop == null) {
                fragment_shop = new Fragment_shop();
            }
            setBg();
            shop.setBackgroundResource(R.mipmap.shopp);
            switchFragment(fragment_shop, SHOP);
        }
    }


    private class AscTest extends AsyncTask<String, Intent, String> {
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Intent... values) {
            super.onProgressUpdate(values);
        }
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
        fragment_main = new Fragment_main();
        main.setBackgroundResource(R.mipmap.mainp);
        switchFragment(fragment_main, MAIN);
    }

    public void switchFragment(Fragment fragment, String tag) {
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

    public void switchM() {
        if (hideTag.equals(MAIN))
            return;
        if (fragment_main == null) {
            fragment_main = new Fragment_main();
        }
        setBg();

        main.setBackgroundResource(R.mipmap.mainp);
        switchFragment(fragment_main, MAIN);
    }


    //版本更新
    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/member/appver/1/sign/aggregation/?uuid=" + Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        // L.e(url);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 2);
    }

    private void getJsonnewData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                codes = jsonObject.getString("info");
                String co=getVerName(this);
                if (codes.equals(co)) {

                } else {

                    doNewVersionUpdate(codes);
                }

            } else {
                T.ss(jsonObject.getString("info"));
                String longs = jsonObject.getString("info");
                url = jsonObject.getString("url");
                if (longs.equals("请先登录")) {
                    LSharePreference.getInstance(this).setBoolean("login", false);
                    Intent intent = new Intent(this, LoginMain.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
    //检查更新版本
    /**
     * Called when the activity is first created.
     */
//    String newVerName = "";//新版本名称
//    int newVerCode = -1;//新版本号
    ProgressDialog pd = null;
    String UPDATE_SERVERAPK = "ApkUpdateAndroid.apk";
    boolean isDownLoad = true;

    /**
     * 获得版本号
     */
    public int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo("com.android.hshuihao", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            L.e("版本号获取异常", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获得版本名称
     */
    public String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo("com.android.hshuihao", 0).versionName;
        } catch (Exception e) {
            L.e("版本名称获取异常", e.getMessage());
        }
        return verName;
    }


    /**
     * 不更新版本
     */
    public void notNewVersionUpdate(String verCode) {
        //  int verCode = this.getVerCode(this);
        // String verName = this.getVerName(this);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本：");
        //sb.append(verName);
        //sb.append(" Code:");
        sb.append(verCode);
        sb.append("\n已是最新版本，无需更新");
        CustomDialog dialog = new CustomDialog.Builder(this)
                .setTitle("软件更新")
                .setMessage(sb.toString())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        // finish();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 更新版本
     */
    public void doNewVersionUpdate(String verCode) {
        String vcode = getVerName(this);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本：");
//         sb.append(vcode);
        //sb.append(" Code:");
        sb.append(vcode);
        sb.append(",发现版本：");
        // sb.append(newVerName);
        // sb.append(" Code:");
        sb.append(verCode);
        sb.append(",是否更新");
        CustomDialog dialog = new CustomDialog.Builder(this)
                .setTitle("软件更新")
                .setMessage(sb.toString())
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        pd = new ProgressDialog(HomeMain.this);
                        pd.setTitle("正在下载");
                        pd.setMessage("请稍后。。。");
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pd.setIndeterminate(false);
                        pd.setCancelable(true);
                        //pd.setProgress(100);
                        downFile(url);
                    }
                })
                .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).create();
        //显示更新框
        dialog.show();
    }

    /**
     * 下载apk
     */
    public void downFile(final String url) {

        pd.show();
        new Thread() {
            public void run() {

                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();

                    long length = entity.getContentLength();

                    pd.setMax((int) length / 1024);
                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(Environment.getExternalStorageDirectory(), UPDATE_SERVERAPK);
                        fileOutputStream = new FileOutputStream(file);
                        byte[] b = new byte[1024];
                        int charb = -1;
                        int count = 0;

                        while ((charb = is.read(b)) != -1 && isDownLoad) {
                            fileOutputStream.write(b, 0, charb);
                            count += charb / 1024;
                            //当前下载量
                            pd.setProgress(count);


                        }


                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }

                    down();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            pd.cancel();
            update();
        }
    };

    /**
     * 下载完成，通过handler将下载对话框取消
     */
    public void down() {
        new Thread() {
            public void run() {
                Message message = handler.obtainMessage();
                handler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 安装应用
     */
    public void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), UPDATE_SERVERAPK))
                , "application/vnd.android.package-archive");
        startActivity(intent);
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK ) //监控/拦截/屏蔽返回键
//        {
//            if(conte==1){
//            showCancelDialog();
//            return true;
//        }else{
//                finish();
//            }
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    private void showCancelDialog() {
        final CustomDialog builer = new CustomDialog.Builder(this)
                .setTitle("确认退出")

                .setMessage("是否要放弃下载？")
                        //当点确定按钮时从服务器上下载 新的apk 然后安装
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        isDownLoad = false;
                        pd.cancel();
                        dialog.dismiss();
                    }
                })
                        //当点取消按钮时进行登录
                .setNegativeButton("继续", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        arg0.dismiss();
                    }
                }).create();
        builer.show();
    }
}