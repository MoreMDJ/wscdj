package com.zxsm.wsc.common.tencent.entity;

import java.util.Date;
import java.util.Map;

public class TextMessage extends BaseMessage
{
	private final static String KToUserName			= "ToUserName";
	private final static String KFromUserName		= "FromUserName";
	
	private static String messageXML = "<xml>"
			+ "<ToUserName><![CDATA[_toUser_]]></ToUserName>"
			+ "<FromUserName><![CDATA[_fromUser_]]></FromUserName>"
			+ "<CreateTime>_createTime_</CreateTime>"
			+ "<MsgType><![CDATA[text]]></MsgType>"
			+ "<Content><![CDATA[_content_]]></Content>"
			+ "</xml>";
	private String content = "Welcome！";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TextMessage(Map<String,Object> map) {
		super();
		String openId		= map.get(KFromUserName) == null ? "" : map.get(KFromUserName).toString();
		String WxId			= map.get(KToUserName) == null ? "" : map.get(KToUserName).toString();
		setToUserName(openId);
		setFromUserName(WxId);
	}

	public TextMessage() {
		super();
	}

	@Override
	public String toString() {
		return messageXML.replace("_toUser_", this.getToUserName())
				.replace("_fromUser_", this.getFromUserName())
				.replace("_createTime_", new Date().getTime() +"")
				.replace("_content_", this.content);
	}
	
}
