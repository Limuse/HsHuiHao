package com.huihao.common;

import com.leo.base.net.LNetwork;
import com.leo.base.net.LReqEntity;

public class MNetwork extends LNetwork {

	public MNetwork(LReqEntity entity, int requestId) {
		super(entity, requestId);
	}

	@Override
	public LoginState doLogin() {
		return LoginState.SUCCESS;
	}

}
