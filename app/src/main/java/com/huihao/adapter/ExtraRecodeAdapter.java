package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.ExtraReEntity;

import java.util.List;

/**
 * Created by huisou on 2015/8/3.
 */
public class ExtraRecodeAdapter extends BaseAdapter {

    private Context context = null;
    private List<ExtraReEntity> entity = null;

    public ExtraRecodeAdapter(Context context, List<ExtraReEntity> list) {
        this.context = context;
        this.entity = list;

    }
    @Override
    public int getCount() {
        return entity.size();
    }

    @Override
    public Object getItem(int position) {
        return entity.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_extar_recode, null);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_m3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ExtraReEntity en = entity.get(position);
        int t = en.state;
        if (t == 0) {
            viewHolder.tv_state.setText("待打款");
        }
        if (t == 1) {
            viewHolder.tv_state.setText("带确认");
        }
        if (t == 2) {
            viewHolder.tv_state.setText("已拒绝");
        }
        if (t == 3) {
            viewHolder.tv_state.setText("已完成");
        }

        viewHolder.tv_time.setText(en.time);
        viewHolder.tv_money.setText(en.money);

        return convertView;
    }

    private class ViewHolder {
        TextView tv_state, tv_time, tv_money;

    }
}
