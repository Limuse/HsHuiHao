package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.huihao.R;
import com.huihao.adapter.AllOrderAdapter;
import com.huihao.adapter.BuysNumAdapter;
import com.huihao.common.PayResult;
import com.huihao.common.SignUtils;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.AddressItemEntity;
import com.huihao.entity.AllOrderItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by huisou on 2015/8/7.
 * 提交订单
 */
public class Submit_Orders extends LActivity implements View.OnClickListener {
    private ListView listView;
    private LinearLayout ly_alladdr;
    private RelativeLayout rl_ano, rl_cu, rl_zf;
    private EditText et_please_num;
    private TextView tv_ym, tv_fj, tv_snum, tv_js, tv_azf, tv_yh, tv_name, tv_phone, tv_addrs;
    private Button btn_alljs;

    private BuysNumAdapter adapter;
    private List<AllOrderItemEntity> itemlist = null;
    public static Submit_Orders instance = null;
    private String title;
    private String orderid;
    private String price;

    private String provinces;
    private String citys;
    private String countrys;
    private String addridss = null;

    private String spec_id;
    private String spec_num;
    private String exress_id;
    private String cids;//优惠卷id
    //地址
    private String adrname = null;
    private String adrphone = null;
    private String adraddr = null;
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
                        Toast.makeText(Submit_Orders.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Submit_Orders.this, Pay_Successed.class);
                        intent.putExtra("price", price);
                        intent.putExtra("orderid", orderid);
                        intent.putExtra("addrname", adrname);
                        intent.putExtra("addrphone", adrphone);
                        intent.putExtra("addrs", adraddr);
                        intent.putExtra("price", price);
                       intent.putExtra("orderid",orderid);
                        startActivity(intent);

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(Submit_Orders.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(Submit_Orders.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(Submit_Orders.this, "检查结果为：" + msg.obj,
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
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
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
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
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


    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_submit_orders);

        instance = Submit_Orders.this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);

        initView();
        getInitData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("提交订单");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        ly_alladdr = (LinearLayout) findViewById(R.id.ordersss);//有地址
        tv_name = (TextView) findViewById(R.id.tv_amenn);
        tv_phone = (TextView) findViewById(R.id.tv_amp);
        tv_addrs = (TextView) findViewById(R.id.tv_addrsa);
        rl_ano = (RelativeLayout) findViewById(R.id.rl_a);//没有地址
        rl_cu = (RelativeLayout) findViewById(R.id.rl_cu);//优惠卷
        rl_zf = (RelativeLayout) findViewById(R.id.rl_zf);//支付方式
        et_please_num = (EditText) findViewById(R.id.et_please_num);//给卖家留言
        tv_ym = (TextView) findViewById(R.id.tv_ym);//运费
        tv_fj = (TextView) findViewById(R.id.tv_fj);//返利直减
        tv_yh = (TextView) findViewById(R.id.tv_yh);//可以优惠的钱
        tv_snum = (TextView) findViewById(R.id.tv_snus);//商品总件数
        tv_js = (TextView) findViewById(R.id.tv_js);//总费用
        tv_azf = (TextView) findViewById(R.id.tv_azf);//结算总费用
        btn_alljs = (Button) findViewById(R.id.btn_alljs);//结算
        listView = (ListView) findViewById(R.id.order_ilistvew);

        ly_alladdr.setOnClickListener(this);
        rl_ano.setOnClickListener(this);
        rl_cu.setOnClickListener(this);
        rl_zf.setOnClickListener(this);
        btn_alljs.setOnClickListener(this);


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
        //有地址
        if (id == R.id.ordersss) {
            Intent intent = new Intent(this, Choose_Address.class);
            startActivityForResult(intent, 0);

        }
        //没有地址
        if (id == R.id.rl_a) {
            Intent intent = new Intent(this, Choose_Address.class);
            startActivityForResult(intent, 0);

        }
        //优惠卷
        if (id == R.id.rl_cu) {
            Intent intent = new Intent(this, Choose_Couppons.class);
            startActivityForResult(intent, 1);
        }
        //支付方式
        if (id == R.id.rl_zf) {

        }
        //结算
        if (id == R.id.btn_alljs) {
//             http://huihaowfx.huisou.com/huihao/orders/addorder/1/sign/aggregation/uuid=6a35c1ed7255077d57d57be679048034&spec_id=463&spec_num=4&payment_id=3&address_id=3&exress_id=3
            if (addridss==null) {
                T.ss("请选择收货地址");
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uuid", Token.get(this));
                map.put("spec_id", spec_id);
                map.put("spec_num", spec_num);
                map.put("payment_id", "3");
                map.put("coupon_arr", cids);
                map.put("address_id", addridss);
                map.put("exress_id", exress_id);
                map.put("buy_message", et_please_num.getText().toString());
                Resources res = getResources();
                String url = res.getString(R.string.app_service_url)
                        + "/huihao/orders/addorder/1/sign/aggregation/";
                LReqEntity entity = new LReqEntity(url, map);
                ActivityHandler handler = new ActivityHandler(Submit_Orders.this);
                handler.startLoadingData(entity, 1);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:
                if (data == null) {
                    rl_ano.setVisibility(View.VISIBLE);
                    ly_alladdr.setVisibility(View.GONE);
                } else {
                    adrname = data.getExtras().getString("name");
                    adrphone = data.getExtras().getString("phone");
                    adraddr = data.getExtras().getString("addr");
                    addridss = data.getExtras().getString("ids");
                    if(addridss!=null){
                        rl_ano.setVisibility(View.GONE);
                        ly_alladdr.setVisibility(View.VISIBLE);
                        provinces = data.getExtras().getString("province");
                        citys = data.getExtras().getString("city");
                        countrys = data.getExtras().getString("country");
                        tv_name.setText(adrname);
                        tv_phone.setText(adrphone);
                        tv_addrs.setText(adraddr);
                    }else{
                        rl_ano.setVisibility(View.VISIBLE);
                        ly_alladdr.setVisibility(View.GONE);
                    }

                }

                break;
            case 1:
                if (data == null) {
                    cids = null;
                } else {
                    cids = data.getExtras().getString("cids");
                }
                break;
        }
    }


    private void getSmJsonData(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject list = jsonObject.getJSONObject("list");
                // employmentId = result.getString("employmentId");

                if (list.length() > 0 || !list.equals(null)) {
                    title = list.getString("title");
                    orderid = list.getString("orderid");
                    price = list.getString("price");

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
                            PayTask alipay = new PayTask(Submit_Orders.this);
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
                } else {
                    T.ss("商品信息不全");
                }
            } else {
                T.ss(jsonObject.getString("message"));
            }
        } catch (Exception e) {
        }

    }

    private void getInitData() {
        itemlist = new ArrayList<AllOrderItemEntity>();
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/confirmorder/1/sign/aggregation/";

        Map<String, String> map = new HashMap<String, String>();
        spec_id = getIntent().getExtras().getString("spec_id");
        spec_num = getIntent().getExtras().getString("spec_num");
        map.put("uuid", Token.get(this));
        map.put("spec_id", spec_id);
        map.put("spec_num", spec_num);

        LReqEntity entity = new LReqEntity(url, map);

        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(Submit_Orders.this);
        handler.startLoadingData(entity, 2);
    }

    private void getJsonInit(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject jsb = jsonObject.getJSONObject("list");
                String total_preferential = jsb.getString("total_preferential");
                String total_price = jsb.getString("total_price");
                tv_js.setText("￥" + total_price);
                tv_azf.setText("￥" + total_price);
                if (total_preferential.equals(null) || total_preferential.equals("")) {
                    tv_fj.setText("");
                } else {
                    tv_fj.setText(total_preferential + "元");
                }
                JSONArray ja = jsb.getJSONArray("exress_list");

                if (ja.length() == 1) {
                    JSONObject j = ja.getJSONObject(0);
                    exress_id = j.getString("id");
                    String kdtitle = j.getString("title");
                    tv_ym.setText(kdtitle);
                } else {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject j = ja.getJSONObject(i);
                        String id = j.getString("id");
                        String titless = j.getString("title");
                    }
                }
                JSONArray jar = jsb.getJSONArray("product_list");
                tv_snum.setText(jar.length() + "");
                for (int i = 0; i < jar.length(); i++) {
                    JSONObject jo = jar.getJSONObject(i);
                    AllOrderItemEntity iee = new AllOrderItemEntity();
                    iee.setBuynum(jo.getString("buynum"));
                    iee.setTitle_1(jo.getString("title_1"));
                    iee.setTitle_2(jo.getString("title_2"));
                    iee.setSpec_1(jo.getString("spec_1"));
                    iee.setNprice(jo.getString("nprice"));
                    iee.setPreferential(jo.getString("preferential"));
                    iee.setPicurl(jo.getString("picurl"));
                    iee.setSpec_2(jo.getString("spec_2"));
                    iee.setSpec_id(jo.getString("spec_id"));
                    iee.setId(jo.getString("id"));
                    iee.setTitle(jo.getString("title"));
                    iee.setBuymin(jo.getString("buymin"));
                    iee.setMaxnum(jo.getString("maxnum"));
                    iee.setBuymax(jo.getString("buymax"));
                    itemlist.add(iee);
                }

                adapter = new BuysNumAdapter(this, itemlist);
                listView.setAdapter(adapter);
            } else {
                T.ss("获取数据失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getSmJsonData(msg.getStr());
            } else if (requestId == 2) {
                getJsonInit(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }
}
