package com.huihao.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.custom.CustomDialog;
import com.huihao.custom.SlideListView2;
import com.huihao.entity.AddressItemEntity;
import com.huihao.entity.ShopItemEntity;

import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class AddressHoriSliseAdapter extends BaseAdapter {

    /**
     * 上下文对象
     */
    private Context context = null;

    private List<AddressItemEntity> entity = null;


    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    private SlideListView2 listView;
    /**
     * 布局参数,动态让HorizontalScrollView中的TextView宽度包裹父容器
     */
    private LinearLayout.LayoutParams mParams;
    /**
     * 记录滑动出删除按钮的itemView
     */
    public HorizontalScrollView mScrollView;


    public AddressHoriSliseAdapter(Context context, List<AddressItemEntity> entity, SlideListView2 listView) {
        // super(context, 0, entity);
        this.context = context;
        this.entity = entity;
        this.listView = listView;
        // 获得到屏幕宽度
        Display defaultDisplay = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mParams = new LinearLayout.LayoutParams(mScreenWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_address, null);

            holder.lyma = (LinearLayout) convertView.findViewById(R.id.ordersss);
            holder.lyma.setLayoutParams(mParams);
            holder.tv_nanea = (TextView) convertView.findViewById(R.id.tv_amenn);
            holder.tv_phonea = (TextView) convertView.findViewById(R.id.tv_amp);
            holder.tv_addra = (TextView) convertView.findViewById(R.id.tv_addrsa);
            holder.deleteButton = (Button) convertView
                    .findViewById(R.id.tv_item_addr_delete);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AddressItemEntity datas = entity.get(position);
        holder.position = position;
        holder.tv_nanea.setText(datas.namea);
        holder.tv_phonea.setText(datas.phonea);
        holder.tv_addra.setText(datas.addra);
        holder.deleteButton.setTag(position);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final CustomDialog alertDialog = new CustomDialog.Builder(context).
                        setMessage("您确定删除这项吗？").setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                entity.remove(Integer.parseInt(v.getTag() + ""));
                                listView.slideBack();
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                        create();


                alertDialog.show();

            }


        });
        return convertView;
    }


    private class ViewHolder {
        private HorizontalScrollView scrollView;
        private TextView tv_nanea, tv_phonea, tv_addra;
        private LinearLayout lyma;
        private RelativeLayout rlddd;
        private Button deleteButton;
        private int position;
    }


}
