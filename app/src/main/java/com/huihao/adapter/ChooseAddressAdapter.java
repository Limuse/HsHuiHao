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
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.util.L;
import com.leo.base.util.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/8/7.
 */
public class ChooseAddressAdapter extends LBaseAdapter {
    private Context context;
    private List<AddressItemEntity> list = null;
    private boolean fal=false;
    private AddressItemEntity items = new AddressItemEntity();
    Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();

    public ChooseAddressAdapter(Context context, List<AddressItemEntity> list) {
        super(context, list, true);
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
        viewHolder.tv_nanea.setText(entity.getUname());
        viewHolder.tv_phonea.setText(entity.getUphone());
        viewHolder.tv_addra.setText(entity.getAddress());
        viewHolder.cb_check.setTag(position);//get("radioid").toString());


        if (isCheckMap != null && isCheckMap.containsKey(position)) {
            viewHolder.cb_check.setChecked(isCheckMap.get(position));
            int t = Integer.parseInt(viewHolder.cb_check.getTag().toString());
            items = null;
            items = list.get(t);
            fal=true;

        } else {
            viewHolder.cb_check.setChecked(false);
            fal=false;
        }
        viewHolder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int radiaoId = Integer.parseInt(buttonView.getTag().toString());
                if (isChecked) {
                    isCheckMap.clear();
                    notifyDataSetChanged();
                    // 将选中的放入hashmap中
                    isCheckMap.put(radiaoId, isChecked);
                } else {
                    //取消选中的则剔除
                    isCheckMap.remove(radiaoId);
                }
                notifyDataSetChanged();

            }
        });


        return convertView;
    }

    public AddressItemEntity BaReturn() {
        if (fal==false) {
            return null;
        } else {
            return items;
        }

    }

    private class ViewHolder {
        private TextView tv_nanea, tv_phonea, tv_addra;
        private CheckBox cb_check;
    }
}
