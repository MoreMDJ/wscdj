package com.zxsm.wsc.common.tencent.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 微信通过code换取网页授权access_token响应封装类
 * @author maeing
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeResponse {

	private String access_token;
	private Integer expires_in;
	private String refresh_token;
	private String openid;
	private String scope;
	private String errcode;
	private String errmsg;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	@Override
	public String toString() {
		return "WeChatCodeResponse [access_token=" + access_token + ", expires_in=" + expires_in + ", refresh_token="
				+ refresh_token + ", openid=" + openid + ", scope=" + scope + ", errcode=" + errcode + ", errmsg="
				+ errmsg + "]";
	}
	
	
}
