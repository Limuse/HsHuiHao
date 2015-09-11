package com.huihao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huihao.activity.Product_details;
import com.huihao.common.Log;
import com.huihao.common.UntilList;
import com.huihao.fragment.Fragment_Product_info;
import com.huihao.MyApplication;
import com.huihao.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/27.
 */
public class ProductInfoImageAda extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> gridList;
    private ArrayList<String> imageList = new ArrayList<String>();
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public ProductInfoImageAda(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.gridList = list;
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();


        for (int i = 0; i < gridList.size(); i++) {
            imageList.add(gridList.get(i).get("image"));
        }
    }

    @Override
    public int getCount() {
        return gridList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.fragment_product_info_item, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//            viewHolder.image.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Product_details.context.ImageDetails(position, imageList);
//                }
//            });
            imageLoader.displayImage(gridList.get(position).get("image"), viewHolder.image, options);
        return convertView;
    }

    private class ViewHolder {
        ImageView image;
    }
}
