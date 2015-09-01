package com.huihao.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.custom.photoView.PhotoViewAttacher;
import com.huihao.R;
import com.huihao.custom.photoView.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by admin on 2015/8/21.
 */
public class ImageDetail extends Activity implements View.OnClickListener {
    private RelativeLayout rLayout_top;
    private RelativeLayout rLayout_bottom;
    private ViewPager vp;
    private LinearLayout lLayout_back;
    private ImageLoader imageLoader;
    private DisplayImageOptions imageOptions;
    private Button btn_download;
    private String curPicUrl;
    private TextView tv_imgPostion;

    private ArrayList<String> urls = new ArrayList<String>();

    private boolean isHide = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_detail);
        initView();
        Intent intent=getIntent();
        urls = 	(ArrayList<String>) intent.getSerializableExtra("ProjectImg");
        tv_imgPostion.setText("1/"+urls.size());
        imageLoader = ImageLoader.getInstance();
        imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
                .build();

        vp.setPageMargin(30);
        vp.setAdapter(new MyVpAdapter());
        vp.setCurrentItem(intent.getIntExtra("index",0));
    }

    private void initView() {
        tv_imgPostion = (TextView) findViewById(R.id.tv_imgPostion);

        rLayout_top = (RelativeLayout) findViewById(R.id.rLayout_top);
        rLayout_bottom = (RelativeLayout) findViewById(R.id.rLayout_bottom);
        vp = (ViewPager) findViewById(R.id.vp);
        lLayout_back = (LinearLayout) findViewById(R.id.lLayout_back);
        lLayout_back.setOnClickListener(this);
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        vp.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                curPicUrl = urls.get(position);// 标记当前图片地址
                int  p = position+1;
                int  all = urls.size();
                tv_imgPostion.setText(p+"/"+all);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLayout_back:
                finish();
                break;
            case R.id.btn_download:
                new AlertDialog.Builder(ImageDetail.this).setPositiveButton("保存至相册",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imageLoader.loadImage(curPicUrl, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view,
                                                                  Bitmap loadedImage) {
                                        String url = MediaStore.Images.Media.insertImage(
                                                getContentResolver(), loadedImage, "", "");
                                        Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
                                                .parse("file://"
                                                        + Environment.getExternalStorageState() + url));
                                        sendBroadcast(intent);
                                        Toast.makeText(ImageDetail.this, "已保存至系统相册",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onLoadingFailed(String imageUri, View view,
                                                                FailReason failReason) {
                                        Toast.makeText(ImageDetail.this, "图片加载异常",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    private class MyVpAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String picUrl = urls.get(position);
            View view = getLayoutInflater().inflate(R.layout.activity_image_detail_item, null);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.img_photo_view);
            final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                public void onPhotoTap(View view, float x, float y) {
                    Animation top_in = AnimationUtils.loadAnimation(ImageDetail.this,
                            R.anim.top_in);
                    top_in.setAnimationListener(new AnimationListener() {
                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                            lLayout_back.setEnabled(true);
                            btn_download.setEnabled(true);
                        }
                    });
                    Animation top_out = AnimationUtils.loadAnimation(ImageDetail.this,
                            R.anim.top_out);
                    top_out.setAnimationListener(new AnimationListener() {
                        public void onAnimationStart(Animation animation) {
                            lLayout_back.setEnabled(false);
                            btn_download.setEnabled(false);
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                        }
                    });
                    Animation bottom_in = AnimationUtils.loadAnimation(ImageDetail.this,
                            R.anim.bottom_in);
                    Animation bottom_out = AnimationUtils.loadAnimation(ImageDetail.this,
                            R.anim.bottom_out);
                    isHide = !isHide;
                    if (isHide) {
                        rLayout_top.startAnimation(top_out);
                    } else {
                        rLayout_top.startAnimation(top_in);
                    }
                }
            });

            imageLoader.loadImage(picUrl, imageOptions, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    Toast.makeText(ImageDetail.this, "图片加载异常", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    pb.setVisibility(View.GONE);
                    photoView.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
