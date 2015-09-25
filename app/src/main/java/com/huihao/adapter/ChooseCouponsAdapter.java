package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.CouponsEntity;
import com.leo.base.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/6.
 */
public class ChooseCouponsAdapter extends BaseAdapter {
    private Context context;
    private List<CouponsEntity> list = null;
    private List<String> listid = new ArrayList<>();
    private List<String> limoney = new ArrayList<>();

    public ChooseCouponsAdapter(Context context, List<CouponsEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_coupons, null);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cb_cb);
            viewHolder.tv_cptitle = (TextView) convertView.findViewById(R.id.conupons_title);
            viewHolder.tv_cptime = (TextView) convertView.findViewById(R.id.conupons_time);
            viewHolder.tv_cpmoey = (TextView) convertView.findViewById(R.id.conupons_money);
            viewHolder.tv_cpuse = (TextView) convertView.findViewById(R.id.conupons_jian);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CouponsEntity ce = list.get(position);
        viewHolder.tv_cptitle.setText(ce.cptitile);
        viewHolder.tv_cptime.setText(ce.cptime);
        viewHolder.tv_cpmoey.setText(ce.cpmoney);
        viewHolder.tv_cpuse.setText(ce.cpuse);
        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    notifyDataSetChanged();
                    // 将选中的放入hashmap中
                    listid.add(ce.cpid);
                    limoney.add(ce.cpmoney);
                } else {
                    //取消选中的则剔除
                    listid.remove(ce.cpid);
                    limoney.remove(ce.cpmoney);
                }
                notifyDataSetChanged();

            }
        });
        return convertView;

    }

    public String rCid() {
        String cid = "";
        float money = 0;
        if (listid.size()== 0) {
            cid = "null";
            return cid;
        } else {
            for (int i = 0; i < listid.size(); i++) {
                cid = cid + listid.get(i) + ",";
                money = money+Float.parseFloat(limoney.get(i).toString());
            }
            cid = cid.substring(0, cid.lastIndexOf(','));
            return cid;
        }
    }
    public float rMoneys(){
        float money = 0;
        if (list.size() == 0 || listid.size() == 0) {
            money = 0;

        } else {
            for (int i = 0; i < listid.size(); i++) {
                money = money+Float.parseFloat(limoney.get(i).toString());
            }
            return money;
        }
        return money;
    }

    private class ViewHolder {
        CheckBox cb;
        TextView tv_cptitle;
        TextView tv_cptime;
        TextView tv_cpmoey;
        TextView tv_cpuse;
    }
}