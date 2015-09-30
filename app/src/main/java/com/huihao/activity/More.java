package com.huihao.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.MutableByte;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.common.CacheUtill;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.custom.CustomDialog;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by huisou on 2015/8/5.
 * 更多
 */
public class More extends LActivity implements View.OnClickListener {

    private TextView tv_web, tv_kp, tv_clear, tv_ccnew, tv_cnew;
    private RelativeLayout rl_web, rl_kp, rl_ban, rl_advice, rl_p, rl_clear;
    private Button btn_outline;
    private Boolean tr = true;
    private String fils = Environment.getExternalStorageDirectory()
            + "/Android/data/com.android.hshuihao/cache";
    private File file1, file2;
    private boolean cleanFlag = false;
    private String codes = null;
    private int conte = 0;
    private String urlUpdate = null;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_more);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);

        Boolean bols = LSharePreference.getInstance(this).getBoolean("login");
        if (bols == true) {
            initView();
            getcache();
            initData();

        } else {
            Intent intent = new Intent(this, LoginMain.class);
            startActivity(intent);
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("更多");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
//        tv_web,tv_kp,tv_clear
//        rl_web,rl_kp,rl_ban,rl_advice,rl_p,rl_clear;
        tv_ccnew = (TextView) findViewById(R.id.tv_ccnew);
        tv_cnew = (TextView) findViewById(R.id.tv_cnew);
        tv_web = (TextView) findViewById(R.id.tv_web);
        tv_kp = (TextView) findViewById(R.id.tv_kp);
        tv_clear = (TextView) findViewById(R.id.tv_nc);
        rl_web = (RelativeLayout) findViewById(R.id.rl_web);
        rl_kp = (RelativeLayout) findViewById(R.id.rl_kp);
        rl_ban = (RelativeLayout) findViewById(R.id.rl_bx);
        rl_advice = (RelativeLayout) findViewById(R.id.rl_advice);
        rl_p = (RelativeLayout) findViewById(R.id.rl_pingf);
        rl_clear = (RelativeLayout) findViewById(R.id.rl_clear);
        btn_outline = (Button) findViewById(R.id.btn_outline);
        rl_web.setOnClickListener(this);
        rl_kp.setOnClickListener(this);
        rl_ban.setOnClickListener(this);
        rl_advice.setOnClickListener(this);
        rl_p.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        btn_outline.setOnClickListener(this);
        String co = getVerName(this);
        tv_ccnew.setText(co);
        tr = LSharePreference.getInstance(this).getBoolean("login");
        if (tr == false) {
            btn_outline.setText("登录");
        } else {
            btn_outline.setText("退出登录");
        }


    }

    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/member/appver/1/sign/aggregation/?uuid=" + Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        L.e(url);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 2);
    }


    private void getJsonnewData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                codes = jsonObject.getString("info");
                String newban = tv_ccnew.getText().toString();
                urlUpdate = jsonObject.getString("url");
                if (codes.equals(newban)) {
                    tv_cnew.setText("当前为最新版本");

                } else {

                    tv_cnew.setText("有新版本");
                    tv_cnew.setTextColor(getResources().getColor(R.color.app_green));

                }

            } else {
                T.ss(jsonObject.getString("info"));
                String longs = jsonObject.getString("info");

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

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean tts = LSharePreference.getInstance(this).getBoolean("login");
        if (tts == true) {
            btn_outline.setText("退出登录");
        } else {
            btn_outline.setText("登录");
        }
    }

    @Override
    public void onClick(View v) {
        int mid = v.getId();
        //官方网站
        if (mid == R.id.rl_web) {
            //T.ss("官方网站");
            // WebView.loadUrl("http://www.baidu.com/");
        }
        //客服电话
        if (mid == R.id.rl_kp) {
            final CustomDialog alertDialog = new CustomDialog.Builder(this).
                    setMessage("确定联系客服吗？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "正在呼叫  " + "4006806820",
                                    Toast.LENGTH_LONG)
                                    .show();
                            Uri uri = Uri.parse("tel:"
                                    + "4006806820");
                            Intent intent = new Intent(
                                    Intent.ACTION_CALL, uri);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();


            alertDialog.show();
        }
//        //版权信息
//        if (mid == R.id.rl_bx) {
//            //  T.ss("版权信息");
//
////            Intent intent = new Intent(this, CopyRight.class);
////            startActivity(intent);
//        }
        //意见反馈
        if (mid == R.id.rl_advice) {
            // T.ss("意见反馈");
            Intent intent = new Intent(this, FeedBack.class);
            startActivity(intent);

        }
        //检查更新
        if (mid == R.id.rl_pingf) {
            if (tv_ccnew.getText().equals(codes)) {
                // notNewVersionUpdate(codes);
                T.ss("已是最新版本，无需更新");
            } else {
                conte = 1;
                doNewVersionUpdate(codes);

            }

        }
        //清除缓存
        if (mid == R.id.rl_clear) {
            // T.ss("清除缓存");
            if (cleanFlag) {
                T.ss("已经很干净啦！");
            } else {
                final CustomDialog alertDialog = new CustomDialog.Builder(this).
                        setMessage("清除缓存吗？").
                        setNegativeButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    if (tv_clear.getText().toString().trim()
                                            .equals("0.0Byte")) {

                                        T.ss("暂无缓存");
                                    } else {
                                        boolean flag1 = CacheUtill.deleteFolderFile(file1.getPath(), true);
                                        boolean flag2 = CacheUtill.deleteFolderFile(file2.getPath(), true);
                                        if (flag1 && flag2) {
                                            getcache();
                                            T.ss("清除成功");
                                            tv_clear.setText("无缓存");
                                            cleanFlag = true;
                                        } else {
                                            T.ss("清除失败");
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                dialog.dismiss();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();


                alertDialog.show();
            }

        }
        //退出登录
        if (mid == R.id.btn_outline) {
            // T.ss("退出登录");
            if (tr == false) {
                btn_outline.setText("登录");
                loades();
            } else if (tr == true) {
                btn_outline.setText("退出登录");
                loades();
            }

        }

    }

    private void getcache() {
        try {
            file1 = new File(Environment.getExternalStorageDirectory(), "cache");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            long folderSize = CacheUtill.getFolderSize(file1);

            file2 = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/com.android.hshuihao/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            long folderSize2 = CacheUtill.getFolderSize(file2);
            String cacheSize = CacheUtill
                    .getFormatSize((folderSize + folderSize2));

            if (cacheSize.equals("0.0B")) {
                tv_clear.setText("无缓存");
            } else {
                tv_clear.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loades() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Token.get(this));
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/logout/1/sign/aggregation/";
        LReqEntity entity = new LReqEntity(url, map);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);

    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else if (requestId == 2) {
                getJsonnewData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss("退出成功！");
                tr = false;
                LSharePreference.getInstance(this).setBoolean("login", false);
                btn_outline.setText("登录");
            } else {
                T.ss(jsonObject.getString("info"));
                String longs = jsonObject.getString("info");
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
                        pd = new ProgressDialog(More.this);
                        pd.setTitle("正在下载");
                        pd.setMessage("请稍后。。。");
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pd.setIndeterminate(false);
                        pd.setCancelable(true);
                        //pd.setProgress(100);
                        downFile(urlUpdate);
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


