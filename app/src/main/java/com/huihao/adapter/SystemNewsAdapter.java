package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huihao.entity.SystemNewsEntity;
import com.huihao.R;

import java.util.List;

/**
 * Created by huisou on 2015/8/11.
 */
public class SystemNewsAdapter extends BaseAdapter {
    private Context context;

    private List<SystemNewsEntity> list=null;
    public SystemNewsAdapter(Context context ,List<SystemNewsEntity> list){
        this.context=context;
        this.list=list;
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
        ViewHolders viewHolders=null;
        if(convertView==null){
            viewHolders=new ViewHolders();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_newpartner_news,null);
            viewHolders.time1=(TextView)convertView.findViewById(R.id.tv_ptimesz);
            viewHolders.name=(TextView)convertView.findViewById(R.id.tv_pname);
            convertView.setTag(viewHolders);
        }else {
            viewHolders=(ViewHolders)convertView.getTag();
        }
        SystemNewsEntity entity=list.get(position);
        viewHolders.time1.setText(entity.time1);
        viewHolders.name.setText(entity.names);

        return convertView;
    }
    public class ViewHolders{
        TextView time1;
        TextView name;
    }




}
