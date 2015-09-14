package com.huihao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.activity.WebActivity;
import com.huihao.entity.OrdersNewsEntity;
import com.leo.base.util.T;

import java.util.List;

/**
 * Created by huisou on 2015/8/11.
 */
public class OrdersNewsAdapter extends BaseAdapter {
    private Context context;
    private List<OrdersNewsEntity> list = null;


    public OrdersNewsAdapter(Context context, List<OrdersNewsEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ordersnews, null);
            viewHolder.tv_ntime = (TextView) convertView.findViewById(R.id.tv_times);
            viewHolder.tv_tjtime = (TextView) convertView.findViewById(R.id.tv_sutime);
            viewHolder.tv_khinfo = (TextView) convertView.findViewById(R.id.tv_khxi);
            viewHolder.tv_ordermoney = (TextView) convertView.findViewById(R.id.tv_odm);
            viewHolder.tv_hmoney = (TextView) convertView.findViewById(R.id.tv_yj);
            viewHolder.iv_mg = (ImageView) convertView.findViewById(R.id.tv_3s);
            viewHolder.rl_see = (RelativeLayout) convertView.findViewById(R.id.rl_xiangq);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.iv_mg.setVisibility(View.GONE);
        final OrdersNewsEntity entity = list.get(position);
        if (entity.getStatus().equals("1")) {
            viewHolder.iv_mg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_mg.setVisibility(View.GONE);
        }

        viewHolder.tv_ntime.setText(entity.getCtime());
        viewHolder.tv_tjtime.setText(entity.getCtime());
        viewHolder.tv_khinfo.setText(entity.getUser_info());
        viewHolder.tv_ordermoney.setText(entity.getTotal_price());
        viewHolder.tv_hmoney.setText(entity.getOverdue());
//        viewHolder.rl_see.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                T.ss("查看详情");
////               Intent intent=new Intent(context, WebActivity.class);
////
////                context.startActivity(intent);
//
//            }
//        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_ntime;
        TextView tv_tjtime;
        TextView tv_khinfo;
        TextView tv_ordermoney;
        TextView tv_hmoney;
        ImageView iv_mg;
        RelativeLayout rl_see;
    }
}
