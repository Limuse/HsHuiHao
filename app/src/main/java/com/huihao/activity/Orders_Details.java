package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.huihao.R;
import com.huihao.adapter.BuysNumAdapter;
import com.huihao.common.PayResult;
import com.huihao.common.SignUtils;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.custom.CustomDialog;
import com.huihao.entity.AllOrderItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by huisou on 2015/8/10.
 * 订单详情
 */
public class Orders_Details extends LActivity implements View.OnClickListener {
    private LinearLayout ly_wl, ll_send;
    private RelativeLayout rl_bm;
    private ListView listView;
    private TextView tv_state, tv_stime,
            tv_aname, tv_p, tv_addr, tv_waite,
            tv_talk, tv_yunf, tv_zj,
            tv_pay, tv_stateo, tv_odnum,
            tv_paytime, tv_opent, tv_desc;
    private Button btn_kf, btn_suok;
    private ScrollView scrollView;

    private BuysNumAdapter adapter;
    private List<AllOrderItemEntity> itemlist = null;
    private String orderid = null;
    private int at = 1;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_orders_details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("订单详情");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        scrollView = (ScrollView) findViewById(R.id.eeee);
        /**
         *物流状态判断，这个需要值判断是否隐藏
         */
        ly_wl = (LinearLayout) findViewById(R.id.ll_wl);//物流状态
        ly_wl.setOnClickListener(this);
        tv_state = (TextView) findViewById(R.id.tv_qqs);
        //   tv_stime = (TextView) findViewById(R.id.tv_qqt);

        tv_waite = (TextView) findViewById(R.id.tv_waite);
        /**
         * 收货地址
         */
        tv_aname = (TextView) findViewById(R.id.tv_amenn);//名字的要求tv_aname.setText("收货人"+);
        tv_p = (TextView) findViewById(R.id.tv_amp);//电话
        tv_addr = (TextView) findViewById(R.id.tv_addrsa);//收货地址要求tv_addr.setText("收货地址"+)
        /**
         * 买家留言
         */
        tv_talk = (TextView) findViewById(R.id.tv_maij);//买家留言

        listView = (ListView) findViewById(R.id.order_ilistvew);//物品件数

        tv_yunf = (TextView) findViewById(R.id.tv_ym);//运费
        tv_zj = (TextView) findViewById(R.id.tv_fj);//返利直减
        tv_pay = (TextView) findViewById(R.id.tv_yh);//实付款
        tv_stateo = (TextView) findViewById(R.id.tv_orderss);//订单状态
        tv_odnum = (TextView) findViewById(R.id.tv_ordernn);//订单编号
        tv_paytime = (TextView) findViewById(R.id.tv_ordertt);//付款时间
        /**
         * 在没有发货的状态下，发货时间和描述需要隐藏
         */
        ll_send = (LinearLayout) findViewById(R.id.ll_send);
        tv_opent = (TextView) findViewById(R.id.tv_orderttt); //发货时间tv_orderttt
        tv_desc = (TextView) findViewById(R.id.tv_oms);//描述tv_oms

        /**
         * 底部判断，有时需要隐藏
         */
        rl_bm = (RelativeLayout) findViewById(R.id.bottom);//底部判断需要隐藏
        btn_kf = (Button) findViewById(R.id.btn_kf);//联系客服btn_kf
        btn_suok = (Button) findViewById(R.id.btn_quer);//确认收货btn_quer

        btn_kf.setOnClickListener(this);
        btn_suok.setOnClickListener(this);

    }

    private void initData() {
        orderid = getIntent().getExtras().getString("orderid");
        itemlist = new ArrayList<AllOrderItemEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/orders/detail/1/sign/aggregation/?uuid=" + Token.get(this) + "&id=" + orderid;

        LReqEntity entitys = new LReqEntity(url);
        // L.e(url + "");
        ActivityHandler handler = new ActivityHandler(Orders_Details.this);
        handler.startLoadingData(entitys, 1);
    }

    private void getJsonData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject josno = jsonObject.getJSONObject("list");
                JSONObject jsd = josno.getJSONObject("order_base");
                String id = jsd.getString("id");//订单号
                tv_odnum.setText(id);//订单编号
                String state = jsd.getString("state");//状态值
                if (state.equals("1")) {
                    //待发货
                    tv_waite.setText("等待卖家发货");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.GONE);
                    ll_send.setVisibility(View.GONE);
                    tv_desc.setVisibility(View.GONE);
                } else if (state.equals("2")) {
                    //待收货
                    tv_waite.setText("卖家已发货");
                    ly_wl.setVisibility(View.VISIBLE);
                    rl_bm.setVisibility(View.VISIBLE);
                    ll_send.setVisibility(View.VISIBLE);
                    tv_desc.setVisibility(View.VISIBLE);
                } else if (state.equals("3")) {
                    tv_waite.setText("订单已完成");
                    ly_wl.setVisibility(View.VISIBLE);
                    rl_bm.setVisibility(View.GONE);
                    ll_send.setVisibility(View.VISIBLE);
                    tv_desc.setVisibility(View.VISIBLE);
                } else if (state.equals("0")) {
                    //待付款
                    tv_waite.setText("等待买家付款");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.VISIBLE);
                    ll_send.setVisibility(View.GONE);
                    tv_desc.setVisibility(View.GONE);
                    tv_opent.setVisibility(View.GONE);
                    btn_suok.setText("付款");
                    at = 2;
                } else if (state.equals("4")) {
                    //退款中
                    tv_waite.setText("等待卖家退款");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.GONE);
                } else if (state.equals("5")) {
                    //已退款
                    tv_waite.setText("卖家已退款");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.GONE);
                } else if (state.equals("6")) {
                    tv_waite.setText("等待卖家换货");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.GONE);
                } else if (state.equals("7")) {
                    tv_waite.setText("卖家已换货");
                    ly_wl.setVisibility(View.GONE);
                    rl_bm.setVisibility(View.GONE);
                }
                String statename = jsd.getString("statename");//状态
                tv_stateo.setText(statename);
                String username = jsd.getString("username");
                tv_aname.setText("收货人:" + username);
                String address = jsd.getString("address_detail");
                tv_addr.setText("收货地址:" + address);
                String phone = jsd.getString("phone");
                tv_p.setText(phone);//收货人电话
                String buy_message = jsd.getString("buy_message");
                tv_talk.setText(buy_message);//买家留言
                String total_title = jsd.getString("total_title");
                String prefer_price = jsd.getString("prefer_price");//返利直减
                tv_zj.setText(prefer_price);
                String pay_price = jsd.getString("pay_price");//实付款
                tv_pay.setText(pay_price);
                String addtime = jsd.getString("addtime");     //   "addtime": "1440750211",
                String paytime = jsd.getString("paytime");      // "paytime": "2015-08-28 16:23",付款时间
                tv_paytime.setText(paytime);
                String sendtime = jsd.getString("sendtime");       // 发货时间
                tv_opent.setText(sendtime);
                String receivetime = jsd.getString("receivetime");      //   收货时间
                String payname = jsd.getString("payname");      //  付款方式
                String suretime = jsd.getString("suretime");      //   确定收货时间
                String exressinfo = jsd.getString("exressinfo");       //  物流信息
                tv_state.setText(exressinfo);
                tv_desc.setText("您的订单已经发货，请在" + "suretime" + "前确认收货，超时订单将自动确认。");
                JSONArray array = josno.getJSONArray("order_detail");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jo = array.getJSONObject(i);
                    AllOrderItemEntity iee = new AllOrderItemEntity();
                    iee.setId(jo.getString("id"));
                    iee.setTitle(jo.getString("title"));
                    iee.setSpec_1(jo.getString("spec_1"));
                    iee.setSpec_2(jo.getString("spec_2"));
                    if (jo.getString("spec_1").equals(null) || jo.getString("spec_1").equals("") || jo.getString("spec_1").equals("null")) {
                        iee.setTitle_1("");
                    } else {
                        iee.setTitle_1("规格1");
                    }
                    if (jo.getString("spec_2").equals(null) || jo.getString("spec_2").equals("") || jo.getString("spec_2").equals("null")) {
                        iee.setTitle_2("");
                    } else {
                        iee.setTitle_2("规格2");
                    }
                    iee.setSpec_id(jo.getString("spec_id"));
                    iee.setPicurl(jo.getString("picurl"));
                    iee.setBuynum(jo.getString("num"));
                    iee.setNprice(jo.getString("newprice"));
                    iee.setPreferential(jo.getString("price"));
                    itemlist.add(iee);
                }
                adapter = new BuysNumAdapter(this, itemlist);
                listView.setAdapter(adapter);
                scrollView.post(new Runnable() {
                    //让scrollview跳转到顶部，必须放在runnable()方法中
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, 0);
                    }
                });

            } else {
                T.ss(jsonObject.getString("info").toString());
                String longs=jsonObject.getString("info");
                if(longs.equals("请先登录")){
                    LSharePreference.getInstance(this).setBoolean("login", false);
                    Intent intent = new Intent(this, LoginMain.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            switch (requestId) {
                case 1:
                    getJsonData(msg.getStr());
                    break;
                case 2:
                    getReData(msg.getStr());
                    break;
                case 3:
                    getPayData(msg.getStr());
                    break;
                default:

                    break;
            }
        }
    }

    private void getReData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss("确认收货");
            } else {
                T.ss(jsonObject.getString("info").toString());
                String longs=jsonObject.getString("info");
                if(longs.equals("请先登录")){
                    LSharePreference.getInstance(this).setBoolean("login", false);
                    Intent intent = new Intent(this, LoginMain.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String oids = tv_odnum.getText().toString();
        //物流签收状态
        if (id == R.id.ll_wl) {
            Intent intent = new Intent(this, MetailFlow_Detail.class);
            intent.putExtra("id", oids);
            startActivity(intent);
        }
        //联系客服
        if (id == R.id.btn_kf) {
            final CustomDialog alertDialog = new CustomDialog.Builder(this).
                    setMessage("确定联系客服吗？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "正在呼叫  " + "4006806820",
                                    Toast.LENGTH_LONG)
                                    .show();
                            Uri uri = Uri.parse("tel:"
                                    + "4006806820");
                            Intent intent = new Intent(
                                    Intent.ACTION_CALL, uri);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();


            alertDialog.show();


        }

        if (id == R.id.btn_quer) {
            Resources res = getResources();
            String url = null;
            if (at == 1) {
                //确认收货
                url = res.getString(R.string.app_service_url) + "/huihao/orders/receivegoods/1/sign/aggregation/";
                Map<String, String> map = new HashMap<String, String>();
                map.put("uuid", Token.get(this));
                map.put("id", oids);
                LReqEntity entitys = new LReqEntity(url, map);
                ActivityHandler handler = new ActivityHandler(Orders_Details.this);
                handler.startLoadingData(entitys, 2);
            } else if (at == 2) {
                //确认付款
                url = res.getString(R.string.app_service_url)
                        + "/huihao/orders/orderpay/1/sign/aggregation/";
                Map<String, String> map = new HashMap<String, String>();
                map.put("uuid", Token.get(this));
                map.put("id", orderid);
                LReqEntity entity = new LReqEntity(url, map);
                ActivityHandler handler = new ActivityHandler(Orders_Details.this);
                handler.startLoadingData(entity, 3);
            }
        }
    }

    private void getPayData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject list = jsonObject.getJSONObject("list");

                if (list.length() > 0 || !list.equals(null)) {
                    String title = list.getString("title");
                    String orderids = list.getString("orderid");
                    String price = list.getString("price");
                    /**
                     * 服务端返回支付宝的数据，然后支付成功后跳转-----支付成功界面
                     */
                    String orderInfo = getOrderInfo(title, orderids, price);
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
                            PayTask alipay = new PayTask(Orders_Details.this);
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
                        Toast.makeText(Orders_Details.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Orders_Details.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(Orders_Details.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(Orders_Details.this, "检查结果为：" + msg.obj,
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
        PayTask payTask = new PayTask(Orders_Details.this);
        String version = payTask.getVersion();
        Toast.makeText(Orders_Details.this, version, Toast.LENGTH_SHORT).show();
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
        orderInfo += "&notify_url=" + "\"" + getResources().getString(R.string.app_service_url)+"huihao/pay/success_back/1/sign/aggregation/"
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
