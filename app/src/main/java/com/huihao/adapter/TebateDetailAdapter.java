package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.RebateDetailEntity;

import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class TebateDetailAdapter extends BaseAdapter {
    private Context context = null;
    private List<RebateDetailEntity> list = null;

    public TebateDetailAdapter(Context context, List<RebateDetailEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rebate_detail, null);
            viewHolder.pnum = (TextView) convertView.findViewById(R.id.tv_nnum);
            viewHolder.state1 = (TextView) convertView.findViewById(R.id.tv_ds);
            viewHolder.pname = (TextView) convertView.findViewById(R.id.tv_pnam);
            viewHolder.pmoney = (TextView) convertView.findViewById(R.id.tv_mos);
            viewHolder.almoney = (TextView) convertView.findViewById(R.id.tv_mmmo);
            viewHolder.state2 = (TextView) convertView.findViewById(R.id.tv_mss);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.state1.setVisibility(View.GONE);
        viewHolder.state2.setVisibility(View.GONE);
        RebateDetailEntity rdes = list.get(position);
        viewHolder.pnum.setText(rdes.dnum);
        viewHolder.pname.setText(rdes.pname);
        viewHolder.pmoney.setText(rdes.dmoeny);
        viewHolder.almoney.setText(rdes.pmoney);
        if (rdes.states==0) {
            viewHolder.state1.setVisibility(View.VISIBLE);
            viewHolder.state1.setText("待收货");
        } else if (rdes.states==1) {
            viewHolder.state1.setVisibility(View.VISIBLE);
            viewHolder.state1.setText("待付款");
        }
        else if (rdes.states==2) {
            viewHolder.state2.setVisibility(View.VISIBLE);
            viewHolder.state2.setText("退款中");
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView pnum;
        private TextView state1;
        private TextView state2;
        private TextView pname;
        private TextView pmoney;
        private TextView almoney;
    }
}
