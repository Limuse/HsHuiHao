package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.entity.AllOrderItemEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

/**
 * Created by huisou on 2015/8/7.
 */
public class BuysNumAdapter extends BaseAdapter {
    private Context context;
    private List<AllOrderItemEntity> itemlist = null;

    public BuysNumAdapter(Context context, List<AllOrderItemEntity> list) {
        this.context = context;
        this.itemlist = list;
    }


    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return itemlist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewItemHolder viewHolders = null;
        if (convertView == null) {
            viewHolders = new ViewItemHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orders_item, null);
            viewHolders.img = (ImageView) convertView.findViewById(R.id.img_tilz);
            viewHolders.tv_title = (TextView) convertView.findViewById(R.id.tv_item_otitle);
            viewHolders.tv_metarils = (TextView) convertView.findViewById(R.id.tv_resa);
            viewHolders.tv_size = (TextView) convertView.findViewById(R.id.tv_sizes);
            viewHolders.tv_colors = (TextView) convertView.findViewById(R.id.tv_colorz);
            viewHolders.tv_moneys = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolders.tv_oldmoney = (TextView) convertView.findViewById(R.id.tv_oldm);
            viewHolders.tv_nums = (TextView) convertView.findViewById(R.id.tv_sssnum);
            convertView.setTag(viewHolders);
        } else {
            viewHolders = (ViewItemHolder) convertView.getTag();
        }
        AllOrderItemEntity ient = itemlist.get(position);
        /**
         * 图片需要另作处理
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
        imageLoader.displayImage(ient.getPicurl(), viewHolders.img, options);
        viewHolders.tv_title.setText(ient.getTitle());
        if (ient.getTitle_1().equals(null) || ient.getTitle_1().equals("null") || ient.getTitle_1().equals("")) {
            viewHolders.tv_colors.setText("");
        } else {
            String title1 = ient.getTitle_1() + ":" + ient.getSpec_1();
            viewHolders.tv_colors.setText(title1);
        }
        if (ient.getTitle_2().equals(null) || ient.getTitle_2().equals("null") || ient.getTitle_2().equals("")) {
            viewHolders.tv_size.setText("");
        } else {
            String title2 = ient.getTitle_2() + ":" + ient.getSpec_2();
            viewHolders.tv_size.setText(title2);
        }


        viewHolders.tv_moneys.setText(ient.getNprice());
        viewHolders.tv_oldmoney.setText("￥" + ient.getPreferential());
        viewHolders.tv_nums.setText("x" + ient.getBuynum());
        return convertView;
    }

    private class ViewItemHolder {
        public ImageView img;
        public TextView tv_title;
        public TextView tv_metarils;
        public TextView tv_size;
        public TextView tv_colors;
        public TextView tv_moneys;
        public TextView tv_oldmoney;
        public TextView tv_nums;
    }
}
