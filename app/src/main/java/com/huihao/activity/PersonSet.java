package com.huihao.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Base64;

import com.huihao.common.SystemBarTintManager;
import com.huihao.R;
import com.huihao.common.HavaSdCard;
import com.leo.base.activity.LActivity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by huisou on 2015/7/29.
 * 个人设置
 */
public class PersonSet extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_phone, rl_name, rl_num, rl_pwd, rl_img;
    private TextView tv_phone, tv_name, tv_num;
    private ImageView my_imgs;
    private Dialog dialog;
    private String logoBase;
    private String img;
    private static final String IMGURL = Environment
            .getExternalStorageDirectory() + "/Android/data/com.android.hshuihao/";

    private static final String IMAGE_FILE_NAME_TEMP = "hui_image.jpg";

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
        dialog.setContentView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
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

        tv_phone = (TextView) findViewById(R.id.setting_phone);
        tv_name = (TextView) findViewById(R.id.setting_name);
        tv_num = (TextView) findViewById(R.id.setting_num);

        rl_img = (RelativeLayout) findViewById(R.id.rl_img);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);
        rl_num = (RelativeLayout) findViewById(R.id.rl_num);
        rl_pwd = (RelativeLayout) findViewById(R.id.rl_pwd);

        my_imgs = (ImageView) findViewById(R.id.setting_img);

        rl_img.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_num.setOnClickListener(this);
        rl_pwd.setOnClickListener(this);

    }

    private void initDialog(View view) {
        Button btn_play = (Button) view.findViewById(R.id.btn_play);
        Button btn_pics = (Button) view.findViewById(R.id.btn_pics);
        Button btn_cncel = (Button) view.findViewById(R.id.btn_cncel);
        /**
         * 弹框点击事件
         *
         */L.e(IMGURL + "路径");
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("拍照");
                openCamera();
                // dialog.dismiss();

            }
        });
        btn_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("从相册中选择");
                openPhones();
                // dialog.dismiss();
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

                        ;
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
                        img = logoBase.toString();//当上传时可以上传img
                        // picloade();
                    }
                }
                dialog.cancel();
        }
    }

    private void picloade() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("token", Token.get(this));
//        map.put("pictures", img);// 头像
//        Resources res = getResources();
//        String url = res.getString(R.string.app_service_url)
//                + "picture/upload.do";
//        LReqEntity entity = new LReqEntity(url, map);
//        ActivityHandler handler = new ActivityHandler(this);
//        handler.startLoadingData(entity, 2);

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
        //绑定手机号
        if (id == R.id.rl_phone) {
            Intent intent = new Intent(this, Update_Phone.class);
            startActivity(intent);
        }
        //修改昵称
        if (id == R.id.rl_name) {
            Intent intent = new Intent(this, Update_Name.class);
            startActivity(intent);
        }
        //邀请码
        if (id == R.id.rl_num) {
            Intent intent = new Intent(this, Update_Num.class);
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
