package com.huihao.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.common.CircleBitmapDisplayer;
import com.huihao.custom.CustomDialog;
import com.huihao.custom.SlideListView2;
import com.huihao.entity.Need;
import com.huihao.entity.ShopItemEntity;
import com.huihao.fragment.Fragment_shop;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class HorizontalSlideAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context context = null;

    private List<ShopItemEntity> entity = null;

    private List<Need> need = new ArrayList<Need>();
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 布局参数,动态让HorizontalScrollView中的TextView宽度包裹父容器
     */
    private LinearLayout.LayoutParams mParams;


    private SlideListView2 listView;

    /**
     * 回调函数
     */
    private OnNumChangeListener onNumChangeListener;

    private int flg = 0;
    private TakeAsyncTask task;
    private String ids;
    private View view = null;
    private float Danjia;

    private List<String> listName = new ArrayList<String>();
    private List<String> listNum = new ArrayList<String>();

    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    public HorizontalSlideAdapter(Context context, List<ShopItemEntity> entity, SlideListView2 listView) {
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


    public List<ShopItemEntity> getList() {
        return entity;
    }

    public void updateData(List<ShopItemEntity> list) {
        this.entity = list;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopitem, null);
            holder = new ViewHolder(convertView);
            holder.infoTextView = (TextView) convertView
                    .findViewById(R.id.tv_item_bgaswipe_title);
            // 设置item内容为match_parent的
            holder.lym = (LinearLayout) convertView.findViewById(R.id.ly_match);
            holder.lym.setLayoutParams(mParams);
            holder.llm = (LinearLayout) convertView.findViewById(R.id.ly_ma);
            holder.llm.setLayoutParams(mParams);
            //  holder.et_num = (Button) convertView.findViewById(R.id.et_num);
            // holder.cb_checkb = (CheckBox) convertView.findViewById(R.id.cb_item_checkbox);
            holder.img_pic = (ImageView) convertView.findViewById(R.id.img_til);
            holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
            holder.tv_size = (TextView) convertView.findViewById(R.id.tv_sizes);
            holder.tv_material = (TextView) convertView.findViewById(R.id.tv_res);
            //  holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            // holder.btn_add = (Button) convertView.findViewById(R.id.btn_jia);
            // holder.btn_Redc = (Button) convertView.findViewById(R.id.btn_jian);
            //  holder.deleteButton = (Button) convertView.findViewById(R.id.tv_item_bgaswipe_delete);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();


        final ShopItemEntity datas = entity.get(position);
        // holder.position = position;
//         holder.deleteButton.setTag(position);

        holder.infoTextView.setText(datas.getTitle());
        holder.et_num.setText(datas.getNum() + "");
        holder.tv_color.setText(datas.getColor());
        String moe = (datas.getDanjia() * datas.getNum()) + "";
        holder.tv_money.setText(moe);
        holder.tv_size.setText(datas.getSize());
        holder.tv_material.setText(datas.getMaterial());
        holder.cb_checkb.setChecked(datas.isCheck());
        holder.et_num.setTag(position);
        /**
         * 图片需要处理
         */
        ImageLoader imageLoader = null;

        // 图片
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.logo)
                .showImageForEmptyUri(R.mipmap.logo)
                .showImageOnFail(R.mipmap.logo)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        imageLoader.displayImage(datas.getPic(), holder.img_pic, options);


        //holder.img_pic.setImageDrawable();
        //标题的点击事件
        holder.infoTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("跳到物品详情页面");
            }
        });


        //删除
        holder.deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final CustomDialog alertDialog = new CustomDialog.Builder(context).
                        setMessage("您确定删除这项吗？").setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view = v;
                                ids = datas.getId();
                                Danjia = datas.getDanjia();
                                task = new TakeAsyncTask();
                                task.execute();
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

        //订单增加的点击事件
        holder.btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNumChangeListener != null) {

                    TextView tv_tt = (TextView) v.getTag();
                    TextView money = (TextView) tv_tt.getTag();
                    Button btn_num = (Button) tv_tt.getTag();

                    int tnum = Integer.parseInt(btn_num.getText().toString());
                    int num = Integer.parseInt(btn_num.getText().toString());
                    num++;

                    if (num > 999) {
                        return;
                    }

                    btn_num.setText(num + "");
                    int t = num - tnum;
                    float jiage = datas.getDanjia() * t;
                    money.setText(datas.getDanjia() * num + "");
                    int index = Integer.parseInt(btn_num.getTag() + "");
                    ShopItemEntity entity1 = entity.get(index);
                    entity1.setNum(num);
                    entity1.setMoney(datas.getDanjia() * num + "");
                    entity.set(index, entity1);

                    if (entity1.isCheck()) {
                        listName.add(datas.getSpecid());
                        listNum.add(num + "");
                        onNumChangeListener.OnNumJiaChange(jiage);
                    }
                }
                notifyDataSetChanged();


            }
        });


        //订单减少的点击事件
        holder.btn_Redc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNumChangeListener != null) {


                    TextView tv_tt = (TextView) v.getTag();
                    TextView money = (TextView) tv_tt.getTag();
                    Button btn_num = (Button) tv_tt.getTag();

                    int tnum = Integer.parseInt(btn_num.getText().toString());

                    int num = Integer.parseInt(btn_num.getText().toString());
                    if (num < 0 || num == 1) {
                        T.ss("不能再减了哦！");
                        return;
                    } else {
                        num--;
                    }

                    btn_num.setText(num + "");
                    int t = tnum - num;
                    float jiage = datas.getDanjia() * t;
                    money.setText(datas.getDanjia() * num + "");

                    int index = Integer.parseInt(btn_num.getTag() + "");
                    ShopItemEntity entity1 = entity.get(index);
                    entity1.setNum(num);
                    entity1.setMoney(datas.getDanjia() * num + "");
                    entity.set(index, entity1);


                    if (entity1.isCheck()) {
                        listName.remove(datas.getSpecid());
                        listNum.remove(num + "");
                        onNumChangeListener.OnNumJianChange(jiage);
                    }
                }
                notifyDataSetChanged();

            }
        });

        	/*
         * 设置单选按钮的选中
		 */
        holder.cb_checkb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNumChangeListener == null) return;
                boolean isChecked = ((CheckBox) v).isChecked();
                TextView tv_tt = (TextView) v.getTag();
                TextView money = (TextView) tv_tt.getTag();
                Button btn_num = (Button) tv_tt.getTag();
                //  float danjia = Float.parseFloat(money.getText().toString());
                float danjia = datas.getDanjia();
                //数量
                int num = Integer.parseInt(btn_num.getText().toString());

                float jiage = num * danjia;


                if (isChecked) {//选中　计算总价
                    listName.add(datas.getSpecid());
                    listNum.add(num + "");
                    onNumChangeListener.OnNumJiaChange(jiage);
//                    Need nn=new Need();
//                    nn.ids=datas.getSpecid();
//                    nn.nums=num+"";
//                    need.add(nn);

                } else {//未选中
                    listName.remove(datas.getSpecid());
                    listNum.remove(num + "");
                    onNumChangeListener.OnNumJianChange(jiage);

                }

                //item 的下标
                int index = Integer.parseInt(btn_num.getTag().toString());
                //获取当前item的数据
                ShopItemEntity entity1 = entity.get(index);
                //更新数据
                entity1.setIsCheck(isChecked);
                //更新list相应的数据
                entity.set(index, entity1);


            }
        });


        return convertView;
    }

    public String getRname() {
        String rName = "";
        if (listName.size() < 1) {
            rName = null;
            return rName;
        } else {

            for (int i = 0; i < listName.size(); i++) {
                rName = rName + listName.get(i) + ",";
            }
            rName = rName.substring(0, rName.lastIndexOf(','));

            return rName;
        }
    }

    public String getRnum() {
        String rNum = "";
        if (listNum.size() < 1) {
            rNum = null;
            return rNum;
        } else {
            for (int i = 0; i < listNum.size(); i++) {
                rNum = rNum + listNum.get(i) + ",";
            }
            rNum = rNum.substring(0, rNum.lastIndexOf(','));
            return rNum;
        }
    }

    static class ViewHolder {
        private TextView infoTextView;
        private TextView tv_money, tv_color, tv_size, tv_material, tv_tt;
        private CheckBox cb_checkb;
        private Button btn_add, btn_Redc;
        private Button et_num;
        private ImageView img_pic;
        private Button deleteButton;
        private LinearLayout lym;
        private LinearLayout llm;
        private int position;


        public ViewHolder(View view) {
            this.et_num = (Button) view.findViewById(R.id.et_num);//t2
            this.tv_money = (TextView) view.findViewById(R.id.tv_money);
            this.btn_add = (Button) view.findViewById(R.id.btn_jia);
            this.btn_Redc = (Button) view.findViewById(R.id.btn_jian);
            this.deleteButton = (Button) view.findViewById(R.id.tv_item_bgaswipe_delete);
            this.cb_checkb = (CheckBox) view.findViewById(R.id.cb_item_checkbox);
            this.tv_tt = (TextView) view.findViewById(R.id.tv_tt);
            tv_tt.setTag(et_num);
            tv_money.setTag(tv_tt);
            btn_add.setTag(tv_tt);
            btn_Redc.setTag(tv_tt);
            deleteButton.setTag(tv_tt);
            cb_checkb.setTag(tv_tt);
        }
    }

    private class TakeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            if (task.isCancelled() == true) {
                return null;
            }
            String result = null;

            Resources res = context.getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/huihao/cart/del/1/sign/aggregation/";

            try {

                HttpPost post = new HttpPost(url);
                List<NameValuePair> par = new ArrayList<NameValuePair>();

                par.add(new BasicNameValuePair("uuid", "6a35c1ed7255077d57d57be679048034"));
                par.add(new BasicNameValuePair("id", ids));

                HttpResponse httpResponse = null;
                post.setEntity(new UrlEncodedFormEntity(par, HTTP.UTF_8));
                HttpClient httpClient = new DefaultHttpClient();
                httpResponse = httpClient.execute(post);

                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(httpResponse.getEntity());
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("status");
                if (code == 1) {
                    T.ss("数据已删除");
                    TextView tv_tt = (TextView) view.getTag();
                    TextView money = (TextView) tv_tt.getTag();
                    Button btn_num = (Button) tv_tt.getTag();
                    if (onNumChangeListener != null) {
                        int num = Integer.parseInt(btn_num.getText().toString());
                        float jiage = Danjia;
                        jiage = num * jiage;

                        int index = Integer.parseInt(btn_num.getTag() + "");
                        ShopItemEntity entity1 = entity.get(index);
                        if (entity1.isCheck())
                            onNumChangeListener.OnNumJianChange(jiage);
                        listName.remove(entity1.getSpecid());
                        listNum.remove(num + "");
                    }
                    entity.remove(Integer.parseInt(btn_num.getTag() + ""));
                    listView.slideBack();
                    notifyDataSetChanged();
                } else {
                    T.ss("数据删除失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    public interface OnNumChangeListener {
        void OnNumJiaChange(float jiage);

        void OnNumJianChange(float jiage);
    }

}
