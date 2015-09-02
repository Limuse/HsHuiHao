package com.huihao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.entity.AddressItemEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/8/7.
 */
public class ChooseAddressAdapter extends BaseAdapter {
    private Context context;
    private List<AddressItemEntity> list = null;

    Map<Integer, Boolean> isCheckMap =  new HashMap<Integer, Boolean>();
    public ChooseAddressAdapter(Context context, List<AddressItemEntity> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_address, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_nanea = (TextView) convertView.findViewById(R.id.tv_amenn);
            viewHolder.tv_phonea = (TextView) convertView.findViewById(R.id.tv_amp);
            viewHolder.tv_addra = (TextView) convertView.findViewById(R.id.tv_addrsa);
            viewHolder.cb_check = (CheckBox) convertView.findViewById(R.id.cb_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AddressItemEntity entity = list.get(position);
        viewHolder.tv_nanea.setText(entity.namea);
        viewHolder.tv_phonea.setText(entity.phonea);
        viewHolder.tv_addra.setText(entity.addra);
        viewHolder.cb_check.setTag(list.get(position).ida) ;//get("radioid").toString());


        if(isCheckMap!=null && isCheckMap.containsKey(position))
        {
            viewHolder.cb_check.setChecked(isCheckMap.get(position));
        }
        else
        {
            viewHolder.cb_check.setChecked(false);
        }
        viewHolder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int radiaoId = Integer.parseInt(buttonView.getTag().toString());
                if(isChecked)
                {
                    isCheckMap.clear();
                    notifyDataSetChanged();
                    //将选中的放入hashmap中
                    isCheckMap.put(radiaoId, isChecked);
                }
                else
                {
                    //取消选中的则剔除
                    isCheckMap.remove(radiaoId);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_nanea, tv_phonea, tv_addra;
        private CheckBox cb_check;
    }
}
