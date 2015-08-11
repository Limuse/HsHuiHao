package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.MoneyNewsEntity;
import com.leo.base.util.T;

import java.util.List;

/**
 * Created by huisou on 2015/8/11.
 */
public class MoneyNewsAdapter extends BaseAdapter {
    private Context context;
    private List<MoneyNewsEntity> list = null;

    public MoneyNewsAdapter(Context context, List<MoneyNewsEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moneynews, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_timess);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_order_num);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.rl_ssee = (RelativeLayout) convertView.findViewById(R.id.rl_xiangqs);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MoneyNewsEntity entity = list.get(position);
        viewHolder.tv_time.setText(entity.time);
        viewHolder.tv_num.setText(entity.num);
        viewHolder.tv_money.setText(entity.money);
        viewHolder.rl_ssee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("查看财富");
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_time;
        TextView tv_num;
        TextView tv_money;
        RelativeLayout rl_ssee;
    }

}
