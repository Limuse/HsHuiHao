package com.huihao.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.common.HavaSdCard;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/7/29.
 * 个人设置
 */
public class PersonSet extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_phone, rl_name, rl_pwd, rl_img;
    private TextView tv_phone, tv_name, tv_num;
    private ImageView my_imgs;
    private Dialog dialog;
    private String logoBase;
    private String img;
    private static final String IMGURL = Environment
            .getExternalStorageDirectory() + "/Android/data/com.android.hshuihao/";


    private static final String IMAGE_FILE_NAME_TEMP = "hh_image.jpg";
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_person_set);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        View view = getLayoutInflater().inflate(R.layout.pic_show, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        initView();
        initDialog(view);


    }


    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("个人设置");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        tv_name = (TextView) findViewById(R.id.setting_name);
//        tv_num = (TextView) findViewById(R.id.setting_num);

        rl_img = (RelativeLayout) findViewById(R.id.rl_img);
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);
        rl_pwd = (RelativeLayout) findViewById(R.id.rl_pwd);

        my_imgs = (ImageView) findViewById(R.id.setting_img);

        rl_img.setOnClickListener(this);

        rl_name.setOnClickListener(this);

        rl_pwd.setOnClickListener(this);
        String ims=getIntent().getExtras().getString("img");
        img=ims;
        String names=getIntent().getExtras().getString("names");
        tv_name.setText(names);
        img();
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
        imageLoader.displayImage(img, my_imgs, options);
    }
    private void initDialog(View view) {
        Button btn_play = (Button) view.findViewById(R.id.btn_play);
        Button btn_pics = (Button) view.findViewById(R.id.btn_pics);
        Button btn_cncel = (Button) view.findViewById(R.id.btn_cncel);
        /**
         * 弹框点击事件
         *
         */

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("拍照");
                openCamera();

            }
        });
        btn_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("从相册中选择");
                openPhones();
            }
        });
        btn_cncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("取消");
                dialog.dismiss();
            }
        });
    }

    private void openPhones() {
        Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, 2);
    }

    private void openCamera() {
        // 打开相机
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用,存储缓存图片
        if (HavaSdCard.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(IMGURL + IMAGE_FILE_NAME_TEMP)));
        }
        startActivityForResult(intentFromCapture, 3);
    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 4);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 2:// 打开相册返回
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            case 3:
                if (resultCode == -1) {
                    File tempFile = new File(IMGURL + IMAGE_FILE_NAME_TEMP);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    T.ss("获取相机图片失败");
                }
                break;
            case 4:// 裁剪完成,删除照相机缓存的图片
                final File tempFile = new File(IMGURL + IMAGE_FILE_NAME_TEMP);
                if (tempFile.exists()) {
                    new Thread() {
                        public void run() {
                            tempFile.delete();
                        }
                    }.start();
                }
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        logoBase = Base64.encodeToString(bytes, Base64.DEFAULT);
                        my_imgs.setImageBitmap(photo);
                        img = logoBase;//当上传时可以上传img
                        picloade();

                    }
                }
                dialog.cancel();
        }
    }

    private void picloade() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("pictures", img);// 头像
        map.put("uuid", Token.get(this));
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/amendavatar/1/sign/aggregation/";
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
                T.ss("图片已上传");

            } else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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


    @Override
    public void onClick(View v) {
        int id = v.getId();
//        //绑定手机号
//        if (id == R.id.rl_phone) {
//            Intent intent = new Intent(this, Update_Phone.class);
//            startActivity(intent);
//        }
        //修改昵称
        if (id == R.id.rl_name) {
            Intent intent = new Intent(this, Update_Name.class);
            startActivity(intent);
        }

        //修改密码
        if (id == R.id.rl_pwd) {
            Intent intent = new Intent(this, Update_Pwd.class);
            startActivity(intent);
        }
        //上传头像
        if (id == R.id.rl_img) {
            showBuyDialog();
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
}
