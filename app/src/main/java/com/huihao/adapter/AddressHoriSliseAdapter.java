package com.huihao.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.activity.Address;
import com.huihao.activity.Update_Address;
import com.huihao.custom.CustomDialog;
import com.huihao.custom.SlideListView2;
import com.huihao.entity.AddressItemEntity;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class AddressHoriSliseAdapter extends LBaseAdapter implements AdapterView.OnItemClickListener {

    /**
     * 上下文对象
     */
    private Address context;

    private List<AddressItemEntity> entity = null;
    private int flg = 0;
    private AddressHoriSliseAdapter adater = AddressHoriSliseAdapter.this;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    private SlideListView2 listView;
    /**
     * 布局参数,动态让HorizontalScrollView中的TextView宽度包裹父容器
     */
    private LinearLayout.LayoutParams mParams;
    private TakeAsyncTask task;
    private boolean falg = false;
    private String ids = null;
    private String ppp=null;
    public AddressHoriSliseAdapter(Address context, List<AddressItemEntity> entity, SlideListView2 listView) {
        super(context, entity, true);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address, null);
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

        holder.tv_nanea.setText(datas.getUname());
        holder.tv_phonea.setText(datas.getUphone());
        holder.tv_addra.setText(datas.getAddress());
        holder.deleteButton.setTag(position);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final CustomDialog alertDialog = new CustomDialog.Builder(context).
                        setMessage("您确定删除这项吗？").setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                Resources res = context.getResources();
//                                String url = res.getString(R.string.app_service_url)
//                                        + "/huihao/myaddress/del/1/sign/aggregation/";
//                                final Map<String, String> map = new HashMap<String, String>();
//                                map.put("uuid", "6a35c1ed7255077d57d57be679048034");
//                                map.put("id", ids);
//                                LReqEntity entitys = new LReqEntity(url, map);
//                                ActivityHandler handler = new ActivityHandler(context);
//                                handler.startLoadingData(entitys, 1);
//                                L.e("ddd", entitys.toString());
                                ids = entity.get(Integer.parseInt(v.getTag() + "")).getId();
                                ppp=v.getTag().toString();
                                task = new TakeAsyncTask();
                                task.execute();
//
//                                if (flg == 1) {
//                                    entity.remove(Integer.parseInt(v.getTag() + ""));
//                                    listView.slideBack();
//                                    adater.notifyDataSetChanged();
//                                } else {
//                                    adater.notifyDataSetChanged();
//                                }
                                adater.notifyDataSetChanged();
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
        listView.setOnItemClickListener(this);
        return convertView;
    }

//    private void getJsonData(String data) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            int code = jsonObject.getInt("status");
//            if (code == 1) {
//                T.ss("已删除！");
//                flg = 1;
//                adater.notifyDataSetChanged();
//            } else {
//                T.ss("数据删除失败");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // 返回获取的网络数据
//    public void onResultHandler(LMessage msg, int requestId) {
//        super.onResultHandler(msg, requestId);
//        if (msg != null) {
//            T.ss(requestId + "'" + "ddd");
//            switch (requestId) {
//                case 1:
//                    getJsonData(msg.getStr());
//                    break;
//                default:
//                    T.ss("获取数据失败");
//                    break;
//            }
//        }
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳到地址修改页面

        AddressItemEntity item = entity.get(position - 1);
        String itmid = item.getId();
        Intent intent = new Intent(context, Update_Address.class);
        intent.putExtra("itmid", itmid);
        context.startActivity(intent);
    }

    private class ViewHolder {
        private HorizontalScrollView scrollView;
        private TextView tv_nanea, tv_phonea, tv_addra;
        private LinearLayout lyma;
        private RelativeLayout rlddd;
        private Button deleteButton;
        private int position;
    }

    private class TakeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            if (task.isCancelled() == true) {
                return null;
            }
            String result = null;
            String url = null;
            Resources res = context.getResources();
            url = res.getString(R.string.app_service_url)
                    + "/huihao/myaddress/del/1/sign/aggregation/";
            HttpPost post = new HttpPost(url);
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("uuid", "6a35c1ed7255077d57d57be679048034"));
            par.add(new BasicNameValuePair("id", ids));
            try {
                post.setEntity(new UrlEncodedFormEntity(par, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(post);
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
                    flg = 1;
                    entity.remove(ppp);
                    listView.slideBack();
                    notifyDataSetChanged();
                } else {
                    T.ss(jsonObject.getString("info"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
