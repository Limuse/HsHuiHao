package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.AllOrderItemEntity;

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
            viewHolders.tv_size = (TextView) convertView.findViewById(R.id.tv_sizez);
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
        // viewHolderders.img

        viewHolders.tv_title.setText(ient.atitle);
        viewHolders.tv_metarils.setText(ient.metails);
        viewHolders.tv_colors.setText(ient.acolor);
        viewHolders.tv_size.setText(ient.asize);
        viewHolders.tv_moneys.setText(ient.amoney);
        viewHolders.tv_oldmoney.setText("￥" + ient.oldm);
        viewHolders.tv_nums.setText("x" + ient.numss);
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
