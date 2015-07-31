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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/27.
 */
public class ProductGridAda extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> gridList;
    public ProductGridAda(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.gridList = list;
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
                    R.layout.activity_product_details_item, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(gridList.get(position).get("name"));
        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
    }
}
