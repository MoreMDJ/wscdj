package com.zxsm.wsc.common.tencent.entity;

import java.net.URLEncoder;

public class DjAuthorizeParam {

	//应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	private String scope = "snsapi_base";

	//授权后重定向的回调链接地址，请使用urlEncode对链接进行处理
	private String redirect_uri = "http://m.cqzsm.com/wechat/authorize/code";

	//重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	private String state = "STATE";
	
	//code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。 
	private String code;

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRedirect_uri() {
		return URLEncoder.encode(redirect_uri);
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
