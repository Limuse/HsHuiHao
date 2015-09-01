package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/27.
 */
public class ProductParaImageAda extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> gridList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    public ProductParaImageAda(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.gridList = list;
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error).cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
//                .displayer(new CircleBitmapDisplayer())//切圆
                .build();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.fragment_product_para_item, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(gridList.get(position).get("image"), viewHolder.image, options);
        return convertView;
    }

    private class ViewHolder {
        ImageView image;
    }
}