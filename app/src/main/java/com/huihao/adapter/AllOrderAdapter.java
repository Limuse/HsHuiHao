package com.huihao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.activity.MetailFlow_Detail;
import com.huihao.custom.IlistView;
import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

/**
 * Created by huisou on 2015/8/5.
 */
public class AllOrderAdapter extends BaseAdapter {
    private Context context;
    private List<AllOrderEntity> list = null;

    public AllOrderAdapter(Context context, List<AllOrderEntity> list) {
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
        List<AllOrderEntity.ChildEntity> itemlist = null;
        viewHolder.btn_see.setVisibility(View.GONE);
        viewHolder.btn_del.setVisibility(View.GONE);
     final    AllOrderEntity entity = list.get(position);
        itemlist =entity.get_child();
        viewHolder.tv_number.setText(entity.getId());
        viewHolder.tv_allmoney.setText("￥" + entity.getPay_price());
        /**
         * entit.astate的值判断订单状态/0交易成功/1待收货/2交易失败
         */
        if (entity.getState().equals("3")) {
            viewHolder.tv_states.setText("已完成");
            viewHolder.btn_del.setText("删除订单");
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        } else if (entity.getState().equals("2")) {
            viewHolder.tv_states.setText("待收货");
            viewHolder.btn_del.setText("确认收货");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            viewHolder.btn_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MetailFlow_Detail.class);
                    intent.putExtra("id",entity.getId().toString());
                    context.startActivity(intent);
                }
            });
        } else if (entity.getState().equals("1")) {
            viewHolder.tv_states.setText("待发货");
            viewHolder.btn_del.setText("申请退款");
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        } else if (entity.getState().equals("5")) {
            viewHolder.tv_states.setText("已退款");
            viewHolder.btn_del.setText("删除订单");
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        } else if (entity.getState().equals("0")) {
            viewHolder.tv_states.setText("待付款");
            viewHolder.btn_del.setText("付款");
            viewHolder.btn_see.setText("取消订单");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        } else if (entity.getState().equals("4")) {
            viewHolder.tv_states.setText("退款中");
            viewHolder.btn_del.setText("完成退款");
            viewHolder.btn_see.setText("取消退款");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
        }

        ItemAdapter adapter = new ItemAdapter(context,itemlist);
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
        private Context con;
        private List<AllOrderEntity.ChildEntity> lists = null;
        public ItemAdapter(Context context, List<AllOrderEntity.ChildEntity> list) {
            this.con = context;
            this.lists = list;
            //itemlist this.itemlist = itemlist;
        }
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
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
                convertView = LayoutInflater.from(con).inflate(R.layout.item_orders_item, null);
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
            AllOrderEntity.ChildEntity ient = lists.get(position);

            /**
             * 图片需要处理
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
            if(ient.getSpec_1().equals(null)||ient.getSpec_1().equals("")){
                viewHolders.tv_colors.setText(null);
            }else{
                viewHolders.tv_colors.setText("规格1:"+ient.getSpec_1()+";");
            }
            if(ient.getSpec_2().equals(null)||ient.getSpec_2().equals("")){
                viewHolders.tv_metarils.setText(null);
            }else{
                viewHolders.tv_metarils.setText("规格2:"+ient.getSpec_2()+";");
            }

            viewHolders.tv_moneys.setText(ient.getNewprice());
            viewHolders.tv_oldmoney.setText("￥" + ient.getPrice());
            viewHolders.tv_nums.setText("x"+ient.getNum());

            return convertView;
        }
    }


}
