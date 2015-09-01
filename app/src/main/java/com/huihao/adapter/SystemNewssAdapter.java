package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.entity.SystemNewssEntity;
import com.huihao.R;
import com.leo.base.util.T;

import java.util.List;

/**
 * Created by huisou on 2015/8/11.
 */
public class SystemNewssAdapter extends BaseAdapter{
    private Context context;

    private List<SystemNewssEntity> list=null;
    public SystemNewssAdapter(Context context ,List<SystemNewssEntity> list){
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
        ViewHolder2 viewHolder2=null;
        if(convertView==null){
            viewHolder2=new ViewHolder2();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_system_news,null);
            viewHolder2.time2=(TextView)convertView.findViewById(R.id.tv_ptimes);
            viewHolder2.title=(TextView)convertView.findViewById(R.id.tv_titles);
            viewHolder2.desc=(TextView)convertView.findViewById(R.id.tv_desc);
            viewHolder2.image=(ImageView)convertView.findViewById(R.id.iv_imgs);
            viewHolder2.rl_tt=(RelativeLayout)convertView.findViewById(R.id.rl_yuanw);
            convertView.setTag(viewHolder2);
        }
        else{
            viewHolder2=(ViewHolder2)convertView.getTag();
        }
        SystemNewssEntity entity=list.get(position);
        viewHolder2.time2.setText(entity.time2);
        viewHolder2.title.setText(entity.title);
        viewHolder2.desc.setText(entity.desc);
        /**
         * 图片要经过处理的
         */
//        viewHolder2.image.setImageDrawable();
        viewHolder2.rl_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("查看原文");
            }
        });
        return convertView;
    }

    public class ViewHolder2{
        TextView time2;
        TextView title;
        TextView desc;
        ImageView image;
        RelativeLayout rl_tt;
    }
}
