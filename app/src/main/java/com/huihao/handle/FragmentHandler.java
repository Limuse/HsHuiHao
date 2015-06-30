package com.huihao.handle;

import org.json.JSONException;

import com.huihao.common.MHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.T;

public class FragmentHandler extends MHandler {

    public FragmentHandler(LFragment fragment) {
        super(fragment);
    }


    protected void onNetWorkExc() {
        T.ss("网络请求发现异常");
    }

    protected void onParseExc() {
        T.ss("数据解析发现异常");
    }

    protected void onLoginError() {
        T.ss("自动登录错误异常");
    }

    protected void onLoginNone() {
        T.ss("用户并未存有登录帐号异常");
    }

    protected void onOtherExc() {
        T.ss("其它异常");
    }


    public LMessage onNetParse(String result, int requestId)
            throws JSONException, LLoginException {
        LMessage msg = new LMessage();
        msg.setStr(result);
        return msg;
    }

}
