package com.huihao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huihao.activity.MetailFlow_Detail;
import com.huihao.custom.IlistView;
import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
import com.huihao.R;

import java.util.List;

/**
 * Created by huisou on 2015/8/5.
 */
public class AllOrderAdapter extends BaseAdapter {
    private Context context;
    private List<AllOrderEntity> list = null;
    private List<AllOrderItemEntity> itemlist = null;

    public AllOrderAdapter(Context context, List<AllOrderEntity> list, List<AllOrderItemEntity> itemlist) {
        this.context = context;
        this.list = list;
        this.itemlist = itemlist;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orders, null);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_nnums);
            viewHolder.tv_states = (TextView) convertView.findViewById(R.id.tv_dstate);
            viewHolder.tv_allmoney = (TextView) convertView.findViewById(R.id.tv_hejs);
            viewHolder.listviews = (IlistView) convertView.findViewById(R.id.ilistvew);
            viewHolder.btn_see = (Button) convertView.findViewById(R.id.btn_dddd);
            viewHolder.btn_del = (Button) convertView.findViewById(R.id.btn_sed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.btn_see.setVisibility(View.GONE);
        viewHolder.btn_del.setVisibility(View.GONE);
        AllOrderEntity entity = list.get(position);
        viewHolder.tv_number.setText(entity.number);
        viewHolder.tv_allmoney.setText("￥"+entity.allmoney);
        /**
         * entit.astate的值判断订单状态/0交易成功/1待收货/2交易失败
         */
        if (entity.astate == 0) {
            viewHolder.tv_states.setText("交易成功");
            viewHolder.btn_del.setText("删除订单");
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        } else if (entity.astate == 1) {
            viewHolder.tv_states.setText("待收货");
            viewHolder.btn_del.setText("确认收货");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            viewHolder.btn_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, MetailFlow_Detail.class);
                    context.startActivity(intent);
                }
            });
        } else if (entity.astate == 2) {
            viewHolder.tv_states.setText("交易失败");
            viewHolder.btn_del.setText("删除订单");
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        }else if(entity.astate==3){
            viewHolder.tv_states.setText("待付款");
            viewHolder.btn_del.setText("付款");
            viewHolder.btn_see.setText("取消订单");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        }
        else if(entity.astate==4){
            viewHolder.tv_states.setText("退款中");
            viewHolder.btn_del.setText("完成退款");
            viewHolder.btn_see.setText("取消退款");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        }
        ItemAdapter adapter = new ItemAdapter();
        viewHolder.listviews.setAdapter(adapter);

        return convertView;
    }

    private class ViewHolder {
        public TextView tv_number;
        public TextView tv_states;
        public TextView tv_allmoney;
        public Button btn_see, btn_del;
        public IlistView listviews;
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

    private class ItemAdapter extends BaseAdapter {

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
            viewHolders.tv_nums.setText("x"+ient.numss);

            return convertView;
        }
    }


}
