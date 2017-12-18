package com.zxsm.wsc.common.tencent.entity;

public class BaseMessage {
	
	private String	toUserName;		//接收方帐号（收到的OpenID）
	
	private String	fromUserName;	//开发者微信号

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	
}
