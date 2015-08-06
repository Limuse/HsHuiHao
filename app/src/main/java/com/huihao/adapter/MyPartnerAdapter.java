package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.MyPartnerEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class MyPartnerAdapter extends BaseAdapter {
    private Context context = null;
    private List<MyPartnerEntity> list = null;

    public MyPartnerAdapter(Context context, List<MyPartnerEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mypartner, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_ppname);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_ppmoney);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyPartnerEntity entity = list.get(position);

        viewHolder.tv_money.setText(entity.moneys);
        viewHolder.tv_name.setText(entity.names);

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_money;
        private TextView tv_name;
    }
}
