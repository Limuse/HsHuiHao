package com.huihao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.activity.Choose_Address;
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
public class ChooseAddressAdapter extends LBaseAdapter implements AdapterView.OnItemClickListener {
    private Choose_Address context;
    private List<AddressItemEntity> list = null;
    private boolean fal;
    private AddressItemEntity items = new AddressItemEntity();
    private Map<Integer, Boolean> isCheckMap = new HashMap<>();
    private ListView listView;

    public ChooseAddressAdapter(Choose_Address context, List<AddressItemEntity> list, ListView listView) {
        super(context, list, true);
        this.context = context;
        this.list = list;
        this.listView = listView;
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
            int t = position;
            Integer.parseInt(viewHolder.cb_check.getTag().toString());
            items = null;
            items = list.get(t);
            String name = items.getUname();
            String phone = items.getUphone();
            String addd = items.getAddress();
            String province = items.getProvince();
            String city = items.getCity();
            String country = items.getCountry();
            String ids = items.getId();
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("ids", ids);
            intent.putExtra("phone", phone);
            intent.putExtra("addr", addd);
            intent.putExtra("province", province);
            intent.putExtra("city", city);
            intent.putExtra("counrty", country);
            context.setResult(0, intent);
            context.finish();

            fal = true;

        } else {
            viewHolder.cb_check.setChecked(false);
            fal = false;
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
        listView.setOnItemClickListener(this);

        return convertView;
    }

    public AddressItemEntity BaReturn() {
        if (items == null) {
            return null;
        } else {
            return items;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
        AddressItemEntity entitys = list.get(positions-1);
        if (entitys == null) {
            context.setResult(0, null);
        } else {

            CheckBox c=(CheckBox)view.findViewById(R.id.cb_check);
            c.setChecked(true);
            String name = entitys.getUname();
            String phone = entitys.getUphone();
            String addd = entitys.getAddress();
            String province = entitys.getProvince();
            String city = entitys.getCity();
            String country = entitys.getCountry();
            String ids = entitys.getId();
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("ids", ids);
            intent.putExtra("phone", phone);
            intent.putExtra("addr", addd);
            intent.putExtra("province", province);
            intent.putExtra("city", city);
            intent.putExtra("counrty", country);
           context.setResult(0, intent);
            context.finish();
        }
    }

    private class ViewHolder {
        private TextView tv_nanea, tv_phonea, tv_addra;
        private CheckBox cb_check;
    }

}
