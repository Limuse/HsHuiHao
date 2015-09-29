package com.huihao.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.activity.MetailFlow_Detail;
import com.huihao.activity.Orders_Details;
import com.huihao.common.PayResult;
import com.huihao.common.SignUtils;
import com.huihao.common.Token;
import com.huihao.custom.IlistView;
import com.huihao.entity.AllOrderEntity;
import com.huihao.entity.AllOrderItemEntity;
import com.huihao.entity.ShopItemEntity;
import com.huihao.entity.UsErId;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by huisou on 2015/8/5.
 */
public class AllOrderAdapter extends BaseAdapter {
    private LFragment context;
    private List<AllOrderEntity> list = null;
    private TakeAsyncTask task;
    private int tag;
    private String ordid;
    private int pid;

    public AllOrderAdapter(LFragment context, List<AllOrderEntity> list) {
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_orders, null);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_nnums);
            viewHolder.tv_states = (TextView) convertView.findViewById(R.id.tv_dstate);
            viewHolder.tv_allmoney = (TextView) convertView.findViewById(R.id.tv_hejs);
            viewHolder.listviews = (IlistView) convertView.findViewById(R.id.ilistvew);
            viewHolder.btn_see = (Button) convertView.findViewById(R.id.btn_dddd);
            viewHolder.btn_del = (Button) convertView.findViewById(R.id.btn_sed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        List<AllOrderEntity.ChildEntity> itemlist = null;
        viewHolder.btn_see.setVisibility(View.GONE);
        viewHolder.btn_del.setVisibility(View.GONE);
        final AllOrderEntity entity = list.get(position);
        itemlist = entity.get_child();
        viewHolder.tv_number.setText(entity.getId());
        viewHolder.tv_allmoney.setText("￥" + entity.getPay_price());
        /**
         * entit.astate的值判断订单状态/0交易成功/1待收货/2交易失败
         *
         * tag//网络请求判断、3删除、4确认收货，5申请退款、7付款、1完成退款、2取消退款
         */
        if (entity.getState().equals("3")) {
            viewHolder.tv_states.setText("已完成");
            viewHolder.btn_del.setText("删除订单");
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.GONE);
            viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pid = position;
                    tag = 3;
                    ordid = entity.getId();
                    task = new TakeAsyncTask();
                    task.execute();

                }
            });
        } else if (entity.getState().equals("2")) {
            ordid = entity.getId();
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            if (entity.isFlgs() == true) {
                viewHolder.tv_states.setText("已收货");
                viewHolder.btn_del.setText("已收货");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.ss("已收货");
                    }
                });
            } else {
                viewHolder.tv_states.setText("待收货");
                viewHolder.btn_del.setText("确认收货");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pid = position;
                        tag = 4;
                        ordid = entity.getId();
                        task = new TakeAsyncTask();
                        task.execute();

                    }
                });
            }

            viewHolder.btn_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看物流信息
                    Intent intent = new Intent(context.getActivity(), MetailFlow_Detail.class);
                    intent.putExtra("id", entity.getId().toString());
                    // L.e(entity.getId().toString());
                    context.startActivity(intent);
                }
            });


        } else if (entity.getState().equals("1")) {
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            if (entity.isFlgs() == true) {
                viewHolder.tv_states.setText("已退款");
                viewHolder.btn_del.setText("已申请退款");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.ss("已申请退款");
                    }
                });
            } else {
                viewHolder.tv_states.setText("待发货");
                viewHolder.btn_del.setText("申请退款");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //T.ss("申请退款");
                        pid = position;
                        tag = 5;
                        ordid = entity.getId();
                        task = new TakeAsyncTask();
                        task.execute();
                    }
                });
            }


        } else if (entity.getState().equals("5")) {
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_text_light));
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_out));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            viewHolder.tv_states.setText("已退款");
            viewHolder.btn_del.setText("删除订单");

            viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // T.ss("删除订单");
                    pid = position;
                    tag = 3;
                    ordid = entity.getId();
                    task = new TakeAsyncTask();
                    task.execute();

                }
            });
        } else if (entity.getState().equals("0")) {
            viewHolder.btn_see.setText("取消订单");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_see.setVisibility(View.VISIBLE);
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            if (entity.isFlgs() == true) {
                viewHolder.tv_states.setText("已付款");
                viewHolder.btn_del.setText("已付款");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.ss("已付款");
                    }
                });
            } else {
                viewHolder.tv_states.setText("待付款");
                viewHolder.btn_del.setText("付款");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //T.ss("付款");
                        pid = position;
                        tag = 7;
                        ordid = entity.getId();
                        task = new TakeAsyncTask();
                        task.execute();
                    }
                });
            }

            viewHolder.btn_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //T.ss("取消订单");
                    pid = position;
                    tag = 3;
                    ordid = entity.getId();
                    task = new TakeAsyncTask();
                    task.execute();
                }
            });
        } else if (entity.getState().equals("4")) {
            viewHolder.btn_see.setVisibility(View.GONE);
            viewHolder.btn_see.setText("取消退款");
            viewHolder.btn_del.setBackground(context.getResources().getDrawable(R.drawable.btn_add));
            viewHolder.btn_del.setTextColor(context.getResources().getColor(R.color.app_orange));
            viewHolder.btn_del.setVisibility(View.VISIBLE);
            if (entity.isFlgs() == true) {
                viewHolder.tv_states.setText("已退款");
                viewHolder.btn_del.setText("已退款");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.ss("已退款");
                    }
                });

            } else {
                viewHolder.tv_states.setText("退款中");
                viewHolder.btn_del.setText("完成退款");
                viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    T.ss("请联系客服退款");
                        // T.ss("完成退款");
                        pid = position;
                        tag = 1;
                        ordid = entity.getId();
                        task = new TakeAsyncTask();
                        task.execute();
                    }
                });
            }


            viewHolder.btn_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // T.ss("请联系客服取消退款");
//                     T.ss("取消退款");
                    tag = 2;
                    ordid = entity.getId();
                    task = new TakeAsyncTask();
                    task.execute();
                }
            });
        }

        ItemAdapter adapter = new ItemAdapter(context.getActivity(), itemlist);
        viewHolder.listviews.setAdapter(adapter);

        return convertView;
    }

    private class ViewHolder {
        public TextView tv_number;
        public TextView tv_states;
        public TextView tv_allmoney;
        public Button btn_see, btn_del;
        public IlistView listviews;
    }

    private class ViewItemHolder {
        public ImageView img;
        public TextView tv_title;
        public TextView tv_metarils;
        public TextView tv_size;
        public TextView tv_colors;
        public TextView tv_moneys;
        public TextView tv_oldmoney;
        public TextView tv_nums;
    }

    private class ItemAdapter extends BaseAdapter {
        private Context con;
        private List<AllOrderEntity.ChildEntity> lists = null;

        public ItemAdapter(Context context, List<AllOrderEntity.ChildEntity> lists) {
            this.con = context;
            this.lists = lists;
            //itemlist this.itemlist = itemlist;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewItemHolder viewHolders = null;
            if (convertView == null) {
                viewHolders = new ViewItemHolder();
                convertView = LayoutInflater.from(con).inflate(R.layout.item_orders_item, null);
                viewHolders.img = (ImageView) convertView.findViewById(R.id.img_tilz);
                viewHolders.tv_title = (TextView) convertView.findViewById(R.id.tv_item_otitle);
                viewHolders.tv_metarils = (TextView) convertView.findViewById(R.id.tv_resa);
                viewHolders.tv_size = (TextView) convertView.findViewById(R.id.tv_sizes);
                viewHolders.tv_colors = (TextView) convertView.findViewById(R.id.tv_colorz);
                viewHolders.tv_moneys = (TextView) convertView.findViewById(R.id.tv_money);
                viewHolders.tv_oldmoney = (TextView) convertView.findViewById(R.id.tv_oldm);
                viewHolders.tv_nums = (TextView) convertView.findViewById(R.id.tv_sssnum);
                convertView.setTag(viewHolders);
            } else {
                viewHolders = (ViewItemHolder) convertView.getTag();
            }
            final AllOrderEntity.ChildEntity ient = lists.get(position);


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
            imageLoader.displayImage(ient.getPicurl(), viewHolders.img, options);

            viewHolders.tv_title.setText(ient.getTitle());
            if (ient.getSpec_1().equals(null) || ient.getSpec_1().equals("")) {
                viewHolders.tv_colors.setText(null);
            } else {
                viewHolders.tv_colors.setText("规格1:" + ient.getSpec_1() + ";");
            }
            if (ient.getSpec_2().equals(null) || ient.getSpec_2().equals("")) {
                viewHolders.tv_metarils.setText(null);
            } else {
                viewHolders.tv_metarils.setText("规格2:" + ient.getSpec_2() + ";");
            }

            viewHolders.tv_moneys.setText(ient.getNewprice());
            viewHolders.tv_oldmoney.setText("￥" + ient.getPrice());
            viewHolders.tv_nums.setText("x" + ient.getNum());
            viewHolders.tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getActivity(), Orders_Details.class);
                    intent.putExtra("orderid", ient.getOrderid());
                    context.startActivity(intent);
                }
            });
            return convertView;
        }
    }

    private class TakeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            // tag//网络请求判断、3删除、4确认收货，5申请退款、7付款、1完成退款、2取消退款
            if (task.isCancelled() == true) {
                return null;
            }
            String result = null;

            //删除订单
            Resources res = context.getResources();
            String url = null;
            switch (tag) {
                case 3://删除订单
                    url = res.getString(R.string.app_service_url)
                            + "/huihao/orders/del/1/sign/aggregation/";
                    break;
                case 4://确认收货
                    url = res.getString(R.string.app_service_url)
                            + "/huihao/orders/receivegoods/1/sign/aggregation/";
                    break;
                case 5://申请退款
                    url = res.getString(R.string.app_service_url)
                            + "/huihao/orders/refund/1/sign/aggregation/";
                    break;
                case 7:
                    //付款
                    url = res.getString(R.string.app_service_url)
                            + "/huihao/orders/orderpay/1/sign/aggregation/";
                    break;
                case 1:
                    //完成退款
                    url = res.getString(R.string.app_service_url) + "/huihao/orders/confirmrefund/1/sign/aggregation/";
                    break;
                case 2:
                    //取消退款

                    break;
                default:
                    break;

            }


            try {

                HttpPost post = new HttpPost(url);
                List<NameValuePair> par = new ArrayList<NameValuePair>();

                par.add(new BasicNameValuePair("uuid", Token.get(context.getActivity())));
                par.add(new BasicNameValuePair("id", ordid));
                L.e(par.toString());
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
            // tag//网络请求判断、3删除、4确认收货，5申请退款、7付款、1完成退款、2取消退款
            switch (tag) {
                case 3:
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("status");
                        if (code == 1) {
                            T.ss("数据已删除");
                            list.remove(pid);
                            notifyDataSetChanged();
                        } else {
                            T.ss("数据删除失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4://确认收货
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("status");
                        if (code == 1) {
                            T.ss("确认收货!");
                            list.get(pid).setFlgs(true);
                            notifyDataSetChanged();
                        } else {
                            T.ss("操作失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5://申请退款
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("status");
                        if (code == 1) {
                            T.ss("申请退款成功!");
                            list.get(pid).setFlgs(true);
                            notifyDataSetChanged();
                        } else {
                            T.ss("操作失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 7://付款
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("status");
                        if (code == 1) {
                            JSONObject list = jsonObject.getJSONObject("list");

                            if (list.length() > 0 || !list.equals(null)) {
                                String title = list.getString("title");
                                String orderid = list.getString("orderid");
                                String price = list.getString("price");
                                /**
                                 * 服务端返回支付宝的数据，然后支付成功后跳转-----支付成功界面
                                 */
                                String orderInfo = getOrderInfo(title, orderid, price);
                                // 对订单做RSA 签名
                                String sign = sign(orderInfo);
                                try {
                                    // 仅需对sign 做URL编码
                                    sign = URLEncoder.encode(sign, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                // 完整的符合支付宝参数规范的订单信息
                                final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                                        + getSignType();

                                Runnable payRunnable = new Runnable() {

                                    @Override
                                    public void run() {
                                        // 构造PayTask 对象
                                        PayTask alipay = new PayTask(context.getActivity());
                                        // 调用支付接口，获取支付结果
                                        String result = alipay.pay(payInfo);

                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };

                                // 必须异步调用
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            }
                        } else {
                            T.ss("操作失败");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1://完成退款
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("status");
                        if (code == 1) {
                            T.ss("已完成退款!");
                            list.get(pid).setFlgs(true);
                            notifyDataSetChanged();
                        } else {
                            T.ss(jsonObject.getString("info"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2://取消退款
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("status");
                        if (code == 1) {
                            T.ss("取消退款成功!");
                            list.get(pid).setFlgs(true);
                            notifyDataSetChanged();
                        } else {
                            T.ss("操作失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }


        }
    }


    /**
     * 支付宝相关
     *
     * @param bundle
     */

    //商户PID
    public static final String PARTNER = "2088911763752854";
    //商户收款账号
    public static final String SELLER = "huihao@huisou.com";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANjoFNM9Yo96VglpBSKi5Vb70Xn8WoiUJvsNRb4ehrnphdCECv8W6zltohFFsexbix37eb+6e/truaFBCmw0lsdVe0KNrN2XmGdfFBZlDyJ7X44RYw9kl1WE7v9J4eVl3Wif6jzRWq33PHZ6TFUzUDvWE3houC3hYGYAnbmsWsibAgMBAAECgYEAmP5KAh2gMXemzAhpeN7RSSNhw9s9uGxXemkIMmuxt9yBaGxanUb7L4ym7evs7bw8Si+g1p2g6dw4GQeZZG1Lk40dJRYZaJxBiaQ7LE/lFV1kmWNmtrllXLV54OfwphXbw/1EDcLaw4yiMZMO0CnphrYzimYeVWNvaGejdQ4lnBECQQD7HWysSOfs5W9qFqaUHvX1hwhCTJnPXPaOsgHeUJq5GF4Qs0xNRn/ZCSARRCL/7XDmoMeEQrQURsDwi+292ktNAkEA3SBLxxq1wpEtHLFocdfEN6Ni/zwPyzCcxcFYodet/aAdWYdmrlHh5WpLq7JIowArHCNqpnOHdY0JHiMG7LnfhwJBANEW4greNccKD2gNbix9Tx1ejyDtOVDxPhb43xdmlD40rPZI5OqfHgrwTzQxQNdKtKxECXz2MY2EkFh6mr1vSy0CQECmhaFD3Opy+aaO9AN82yCNQ49uJwv4PY3P9rLy1Sr3Gj2nycyjohEqH8+mQ3hsvy3t6OubkXo77vOBuC+UNJcCQQCeOtmGyUDkVyzFuMfbbjjdP9CatTvaGb3MvfRMZWtLE8At2A6Jk+Nxc9oA7hHijVgsbD2rS8anGRsqnZ5SiMc4";
    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDY6BTTPWKPelYJaQUiouVW+9F5/FqIlCb7DUW+Hoa56YXQhAr/Fus5baIRRbHsW4sd+3m/unv7a7mhQQpsNJbHVXtCjazdl5hnXxQWZQ8ie1+OEWMPZJdVhO7/SeHlZd1on+o80Vqt9zx2ekxVM1A71hN4aLgt4WBmAJ25rFrImwIDAQAB";


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(context.getActivity(), "支付成功",
                                Toast.LENGTH_SHORT).show();
                        list.get(pid).setFlgs(true);
                        notifyDataSetChanged();
//                        Intent intent = new Intent(Submit_Orders.this, Pay_Successed.class);
//                        intent.putExtra("price", price);
//                        intent.putExtra("orderid", orderid);
//                        intent.putExtra("addrname", adrname);
//                        intent.putExtra("addrphone", adrphone);
//                        intent.putExtra("addrs", adraddr);
//                        intent.putExtra("price", price);
//                        intent.putExtra("orderid",orderid);
//                        startActivity(intent);

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context.getActivity(), "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(context.getActivity(), "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(context.getActivity(), "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(context.getActivity());
        String version = payTask.getVersion();
        Toast.makeText(context.getActivity(), version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + body + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://huihaowfx.huisou.com/huihao/pay/success_back/1/sign/aggregation/"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
    //


}
