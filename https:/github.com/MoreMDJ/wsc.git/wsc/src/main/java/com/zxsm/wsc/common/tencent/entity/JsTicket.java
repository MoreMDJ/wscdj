package com.zxsm.wsc.common.tencent.entity;

import com.zxsm.wsc.common.tencent.common.Configure;
import com.zxsm.wsc.common.tencent.common.RandomStringGenerator;

public class JsTicket {
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	private String title;
	private String desc;
	private String link;
	private String imgUrl;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public JsTicket() {
		super();
		this.appId = Configure.getAppid();
		this.timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		this.nonceStr = RandomStringGenerator.getRandomStringByLength(32);
	}
}
