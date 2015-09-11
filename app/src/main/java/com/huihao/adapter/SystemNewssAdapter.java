package com.huihao.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.activity.WebActivity;
import com.huihao.entity.SystemNewsEntity;
import com.huihao.entity.SystemNewssEntity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

/**
 * Created by huisou on 2015/8/11.
 */
public class SystemNewssAdapter extends BaseAdapter {
    private Context context;

    private List<SystemNewsEntity> list = null;
    public SystemNewssAdapter(Context context, List<SystemNewsEntity> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder2 viewHolder2 = null;
        if (convertView == null) {
            viewHolder2 = new ViewHolder2();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_system_news, null);
            viewHolder2.time2 = (TextView) convertView.findViewById(R.id.tv_ptimes);
            viewHolder2.title = (TextView) convertView.findViewById(R.id.tv_titles);
            viewHolder2.desc = (TextView) convertView.findViewById(R.id.tv_desc);
            viewHolder2.image = (ImageView) convertView.findViewById(R.id.iv_imgs);
            viewHolder2.rl_tt = (RelativeLayout) convertView.findViewById(R.id.rl_yuanw);
            convertView.setTag(viewHolder2);
        } else {
            viewHolder2 = (ViewHolder2) convertView.getTag();
        }
        final SystemNewsEntity entity = list.get(position);
        viewHolder2.time2.setText(entity.getAdd_time());
        viewHolder2.title.setText(entity.getTitle());
        viewHolder2.desc.setText(entity.getContent());
        String imgs = entity.getPicture();
        final String url = entity.getLinkurl();
        /**
         * 图片要经过处理的
         */
        ImageLoader imageLoader = null;

        // 图片
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.logo)
                .showImageForEmptyUri(R.mipmap.logo)
                .showImageOnFail(R.mipmap.logo)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        imageLoader.displayImage(imgs, viewHolder2.image, options);
        viewHolder2.rl_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("查看原文");
              //  Uri uri = Uri.parse(url);
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url",entity.getLinkurl());
                intent.putExtra("title","系统消息");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder2 {
        TextView time2;
        TextView title;
        TextView desc;
        ImageView image;
        RelativeLayout rl_tt;
    }
}
