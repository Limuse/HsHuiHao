package com.huihao.handler;

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
		T.ss("�����������쳣");
	}

	protected void onParseExc() {
		T.ss("���ݽ��������쳣");
	}

	protected void onLoginError() {
		T.ss("�Զ���¼�����쳣");
	}

	protected void onLoginNone() {
		T.ss("�û���δ���е�¼�ʺ��쳣");
	}

	protected void onOtherExc() {
		T.ss("�����쳣");
	}


	public LMessage onNetParse(String result, int requestId)
			throws JSONException, LLoginException {
		LMessage msg = new LMessage();
		msg.setStr(result);
		return msg;
	}

}