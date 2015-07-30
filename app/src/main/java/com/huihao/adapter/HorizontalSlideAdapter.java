package com.huihao.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.entity.ShopItemEntity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;

public class HorizontalSlideAdapter extends ArrayAdapter<ShopItemEntity> {
    /**
     * 上下文对象
     */
    private Context context = null;

    private List<ShopItemEntity> entity = null;
    private int NUM;
    private String ALLMONEY;
    private Double MONEY;
    /**
     * 判断是否被选中
     */
    private Boolean isCheck;

    private List<ShopItemEntity> se = null;
    private Boolean FLG = true;
    /**
     * CheckBox 是否选择的存储集合,key 是 position , value 是该position是否选中
     */
    private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 删除按钮事件
     */
    private DeleteButtonOnclickImpl mDelOnclickImpl;
    /**
     * HorizontalScrollView左右滑动事件
     */
    private ScrollViewScrollImpl mScrollImpl;

    /**
     * 布局参数,动态让HorizontalScrollView中的TextView宽度包裹父容器
     */
    private LinearLayout.LayoutParams mParams;

    /**
     * 记录滑动出删除按钮的itemView
     */
    public HorizontalScrollView mScrollView;

    /**
     * touch事件锁定,如果已经有滑动出删除按钮的itemView,就屏蔽下一整次(down,move,up)的onTouch操作
     */
    public boolean mLockOnTouch = false;

    public HorizontalSlideAdapter(Context context, List<ShopItemEntity> entity) {
        super(context, 0, entity);
        this.context = context;
        this.entity = entity;
        // 获得到屏幕宽度
        Display defaultDisplay = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mParams = new LinearLayout.LayoutParams(mScreenWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        // 初始化删除按钮事件与item滑动事件
        mDelOnclickImpl = new DeleteButtonOnclickImpl();
        mScrollImpl = new ScrollViewScrollImpl();
        //初始化，都没有选中
        configCheckMap(false);
    }


    public void configCheckMap(Boolean bool) {
        for (int i = 0; i < entity.size(); i++) {
            isCheckMap.put(i, bool);
        }
    }

//    @Override
//    public int getCount() {
//        return entity == null ? 0 : entity.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return entity.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         ViewHolder holder = null;
        if (convertView == null) {
           holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_shopitem, null);
            holder.scrollView = (HorizontalScrollView) convertView;
            holder.scrollView.setOnTouchListener(mScrollImpl);
            holder.infoTextView = (TextView) convertView
                    .findViewById(R.id.tv_item_bgaswipe_title);
            // 设置item内容为match_parent的
            holder.lym = (LinearLayout) convertView.findViewById(R.id.ly_match);
            holder.lym.setLayoutParams(mParams);
            holder.et_num = (EditText) convertView.findViewById(R.id.et_num);
            holder.cb_checkb = (CheckBox) convertView.findViewById(R.id.cb_item_checkbox);
            holder.img_pic = (ImageView) convertView.findViewById(R.id.img_til);
            holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
            holder.tv_size = (TextView) convertView.findViewById(R.id.tv_sizes);
            holder.tv_material = (TextView) convertView.findViewById(R.id.tv_res);
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            holder.btn_add = (Button) convertView.findViewById(R.id.btn_jia);
            holder.btn_Redc = (Button) convertView.findViewById(R.id.btn_jian);
            holder.deleteButton = (Button) convertView
                    .findViewById(R.id.tv_item_bgaswipe_delete);

            holder.deleteButton.setOnClickListener(mDelOnclickImpl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShopItemEntity datas = getItem(position);
        holder.position = position;
        holder.deleteButton.setTag(holder);
        holder.infoTextView.setText(datas.getTitle());
        holder.et_num.setText(datas.getNum() + "");
        holder.tv_color.setText(datas.getColor());
        holder.tv_money.setText(datas.getMoney());
        MONEY = Double.parseDouble(holder.tv_money.getText() + "");
        holder.tv_size.setText(datas.getSize());
        holder.tv_material.setText(datas.getMaterial());
        /**
         * 图片需要处理
         */
        //holder.img_pic.setImageDrawable();
        holder.scrollView.scrollTo(0, 0);
        //标题的点击事件
        holder.infoTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("跳到订单页面");
            }
        });


        //订单增加的点击事件
        holder.btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                String num = (holder.et_num.getText().toString());
//                NUM = Integer.parseInt(num);
//                NUM++;
//                holder.et_num.setText(NUM);
//                /**
//                 * 需要提供单件物品的售价
//                 */
//                //  MONEY=NUM*
//                holder.tv_money.setText(MONEY + "");
            }
        });


        //订单减少的点击事件
        holder.btn_Redc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                String num = (holder.et_num.getText().toString());
//                NUM = Integer.parseInt(num);
//                NUM--;
//                holder.et_num.setText(NUM);
//                /**
//                 * 需要提供单件物品的售价
//                 */
//                //  MONEY=NUM*
//                holder.tv_money.setText(MONEY + "");
//                if (NUM == 0) {
//                    FLG = false;
//
//
//                }
            }
        });

        	/*
         * 设置单选按钮的选中
		 */
        holder.cb_checkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                ViewHolder holder = new ViewHolder();
                /*
				 * 将选择项加载到map里面寄存
				 */
                isCheckMap.put(position, isChecked);
                //   ShopItemEntity itm=new ShopItemEntity(entity.get(position).getTitle(),);
//                entity.get(position).getTitle();
//                entity.get(position).getColor();
//                entity.get(position).getMaterial();
//                entity.get(position).setMoney(holder.tv_money.toString());
//                for (int i = 0; i < isCheckMap.size(); i++) {
//                    ALLMONEY = holder.tv_money.toString();
//                }
            }
        });


        if (isCheckMap.get(position) == null) {
            isCheckMap.put(position, false);
        }

        holder.cb_checkb.setChecked(isCheckMap.get(position));


        return convertView;
    }


    static class ViewHolder {
        private HorizontalScrollView scrollView;
        private TextView infoTextView;
        private TextView tv_money, tv_color, tv_size, tv_material;
        private CheckBox cb_checkb;
        private Button btn_add, btn_Redc;
        private EditText et_num;
        private ImageView img_pic;
        private Button deleteButton;
        private LinearLayout lym;
        private int position;
    }

    public Map<Integer, Boolean> getCheckMap() {
        return this.isCheckMap;
    }

    public String getMoney() {
        return ALLMONEY;
    }

    /**
     * HorizontalScrollView的滑动事件
     */
    private class ScrollViewScrollImpl implements OnTouchListener {
        /**
         * 记录开始时的坐标
         */
        private float startX = 0;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 如果有划出删除按钮的itemView,就让他滑回去并且锁定本次touch操作,解锁会在父组件的dispatchTouchEvent中进行
                    if (mScrollView != null) {
                        scrollView(mScrollView, HorizontalScrollView.FOCUS_LEFT);
                        mScrollView = null;
                        mLockOnTouch = true;
                        return true;
                    }

                    startX = event.getX();
//                    if (FLG == false) {
//                        /**
//                         * 因物品数量而触发的滑动删除事件
//                         */
//                        //当购物车中物件的数量减为0时就触发滑动删除事件
//                        HorizontalScrollView view = (HorizontalScrollView) v;
//                        startX = 0;// 因为公用一个事件处理对象,防止错乱,还原startX值
//                        scrollView(view, HorizontalScrollView.FOCUS_RIGHT);
//                        mScrollView = view;
//                    }
                    break;
                case MotionEvent.ACTION_UP:
                    HorizontalScrollView view = (HorizontalScrollView) v;
                    // 如果滑动了>50个像素,就显示出删除按钮
                    if (startX > event.getX() + 10) {
                        //startX = 0;// 因为公用一个事件处理对象,防止错乱,还原startX值
                        scrollView(view, HorizontalScrollView.FOCUS_RIGHT);
                        mScrollView = view;
                    } else {
                        scrollView(view, HorizontalScrollView.FOCUS_LEFT);
                    }
                    break;
            }
            return false;
        }
    }

    /**
     * HorizontalScrollView左右滑动
     */
    public void scrollView(final HorizontalScrollView view, final int parameter) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.pageScroll(parameter);
            }
        });
    }

    /**
     * 删除事件
     */
    private class DeleteButtonOnclickImpl implements OnClickListener {
        @Override
        public void onClick(View v) {
            final ViewHolder holder = (ViewHolder) v.getTag();
            Toast.makeText(getContext(), "删除第" + holder.position + "项",
                    Toast.LENGTH_SHORT).show();
            Animation animation = AnimationUtils.loadAnimation(getContext(),
                    R.anim.anim_item_delete);
            holder.scrollView.startAnimation(animation);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    remove(getItem(holder.position));
                }
            });

        }
    }
}
