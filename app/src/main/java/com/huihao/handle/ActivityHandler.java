package com.huihao.handle;

import org.json.JSONException;

import com.huihao.common.MHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.T;

public class ActivityHandler extends MHandler {

	public ActivityHandler(LActivity activity) {		
		super(activity);
	}
	

	protected void onNetWorkExc() {
		T.ss("");
	}

	protected void onParseExc() {
		T.ss("");
	}

	protected void onLoginError() {
		T.ss("");
	}

	protected void onLoginNone() {
		T.ss("");
	}

	protected void onOtherExc() {
		T.ss("");
	}

	

	public LMessage onNetParse(String result, int requestId)
			throws JSONException, LLoginException {
		LMessage msg = new LMessage();
		msg.setStr(result);
		return msg;
	}

}
