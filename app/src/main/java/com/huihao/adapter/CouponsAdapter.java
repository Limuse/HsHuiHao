package com.huihao.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.CouponsEntity;

import java.util.List;

import static com.huihao.R.mipmap.no_u;

/**
 * Created by huisou on 2015/7/30.
 */
public class CouponsAdapter extends BaseAdapter {
    private Context context = null;
    private List<CouponsEntity> cpentity = null;

    public CouponsAdapter(Context context, List<CouponsEntity> list) {
        this.context = context;
        this.cpentity = list;

    }
    @Override
    public int getCount() {
        return cpentity.size();
    }

    @Override
    public Object getItem(int position) {
        return cpentity.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_conuponsitem, null);
            viewHolder.iv_cpimg = (ImageView) convertView.findViewById(R.id.cpimg);
            viewHolder.tv_cptitle = (TextView) convertView.findViewById(R.id.conupons_title);
            viewHolder.tv_cptime = (TextView) convertView.findViewById(R.id.conupons_time);
            viewHolder.tv_cpmoey = (TextView) convertView.findViewById(R.id.conupons_money);
            viewHolder.tv_cpuse = (TextView) convertView.findViewById(R.id.conupons_jian);
            viewHolder.ly_con = (LinearLayout) convertView.findViewById(R.id.item_conupons);
            viewHolder.iv_cpuuu = (ImageView) convertView.findViewById(R.id.coupons_uuu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CouponsEntity ce = cpentity.get(position);
        //viewHolder.iv_cpimg
        //viewHolder.tv_cptitle.setText(ce.cptitile);//没返回数据
        viewHolder.tv_cptime.setText(ce.cptime);
        viewHolder.tv_cpmoey.setText(ce.cpmoney);
       // viewHolder.tv_cpuse.setText(ce.cpuse);//没返回数据
        /**
         *   viewHolder.ly_con
         *   需要换背景.no_u
         *     viewHolder.ly_con.setBackground(context.getResources().getDrawable(R.mipmap.no_u));
         *     viewHolder.iv_cpuuu
         *     是否使用需要隐藏与显示
         *viewHolder.iv_cpuuu.setVisibility(View.VISIBLE);
         *
         */
        switch (ce.t) {
            case 3:
                viewHolder.ly_con.setBackground(context.getResources().getDrawable(R.mipmap.no_u));
                viewHolder.iv_cpuuu.setBackground(context.getResources().getDrawable(R.mipmap.used));
                viewHolder.tv_cptitle.setTextColor(context.getResources().getColor(R.color.app_conp_text));
                viewHolder.tv_cptime.setTextColor(context.getResources().getColor(R.color.app_conp_text));
                viewHolder.iv_cpuuu.setVisibility(View.VISIBLE);
                break;
            case 0:
                viewHolder.ly_con.setBackground(context.getResources().getDrawable(R.mipmap.no_u));
                viewHolder.iv_cpuuu.setBackground(context.getResources().getDrawable(R.mipmap.time_pss));
                viewHolder.tv_cptitle.setTextColor(context.getResources().getColor(R.color.app_conp_text));
                viewHolder.tv_cptime.setTextColor(context.getResources().getColor(R.color.app_conp_text));
                viewHolder.iv_cpuuu.setVisibility(View.VISIBLE);
                break;
            default:
                viewHolder.ly_con.setBackground(context.getResources().getDrawable(R.mipmap.can_u));
                viewHolder.iv_cpuuu.setVisibility(View.GONE);
                break;
        }


        return convertView;
    }

    private class ViewHolder {
        ImageView iv_cpimg;
        ImageView iv_cpuuu;
        TextView tv_cptitle;
        TextView tv_cptime;
        TextView tv_cpmoey;
        TextView tv_cpuse;
        LinearLayout ly_con;
    }


}
