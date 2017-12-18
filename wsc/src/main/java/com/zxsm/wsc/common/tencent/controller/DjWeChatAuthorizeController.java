package com.zxsm.wsc.common.tencent.controller;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.zxsm.wsc.common.tencent.common.Configure;
import com.zxsm.wsc.common.tencent.entity.CodeResponse;
import com.zxsm.wsc.common.tencent.entity.DjAuthorizeParam;
import com.zxsm.wsc.common.tencent.entity.UserInfoResponse;

@Controller
@RequestMapping(value="/wechat/authorize")
public class DjWeChatAuthorizeController
{
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	DjUserQRcodeTools qrTools;
	
	/**
	 * 获取code
	 * @param param
	 * @return
	 */
	@RequestMapping()
	public String authorize(DjAuthorizeParam param)
	{
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"+
				"appid=" + Configure.getAppid() + 
				"&redirect_uri=" + param.getRedirect_uri() + 
				"&response_type=code&" +
				"scope=" + param.getScope() + 
				"&state=" + param.getState() + 
				"#wechat_redirect";
		return "redirect:" + url;
	}
	
	/**
	 * code的回调函数，参数里面有code
	 */
	@RequestMapping(value ="/code")
	public String Code(DjAuthorizeParam param)
	{
		if(StringUtils.isBlank(param.getCode()))
		{
			logger.error("code 为空 -- 错误");
			return "";
		}
		else
		{
			System.out.println("\n-------------code start----------\n");
			System.out.println(param.getCode());
			System.out.println("\n-------------code end----------\n");
		}
		
		String accessUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"+
				"appid="	+ Configure.getAppid() +
				"&secret="	+ Configure.getAppSecret() +
				"&code="	+ param.getCode() +
				"&grant_type=authorization_code";
		
		CodeResponse codeResponse = getFromUrl(accessUrl);
		if(StringUtils.isNotBlank(codeResponse.getErrcode()))
		{
			logger.error("\nget Code Error:\nerrorCode:" + codeResponse.getErrcode() + "\nerrormsg:" +codeResponse.getErrmsg() );
			return "redirect:/wx/u";
		}
//		codeResponse.setOpenid("o6DzCjjkdQ73dec1ZZBu8b3G_M1A");
		UserInfoResponse userInfo = GetUserInfoByOpenId(codeResponse.getOpenid());
		
		
		logger.error(codeResponse);
		return "";
	}
	
	public static String getAuthorization(String redirectUrl,String scope,String state)
	{
		if(StringUtils.isBlank(redirectUrl))
			return null;
		if(StringUtils.isBlank(scope))
			scope="snsapi_base";
		if(StringUtils.isBlank(state))
			state="";
		
		redirectUrl = URLEncoder.encode(redirectUrl);
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"+
				"appid=" + Configure.getAppid() + 
				"&redirect_uri=" + redirectUrl + 
				"&response_type=code" +
				"&scope=" + scope + 
				"&state=" + state + 
				"#wechat_redirect";
		return url;
	}
	
	public static String getOpenIdByCode(String code)
	{
		if(StringUtils.isBlank(code))
		{
//			logger.error("code 为空 -- 错误");
			System.out.println("code 为空 -- 错误");
			return null;
		}
		String accessUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"+
				"appid="	+ Configure.getAppid() +
				"&secret="	+ Configure.getAppSecret() +
				"&code="	+ code +
				"&grant_type=authorization_code";
		
		CodeResponse codeResponse = getFromUrl(accessUrl);
		System.out.println(codeResponse);
		if(StringUtils.isNotBlank(codeResponse.getErrcode()))
		{
//			logger.error("\nget Code Error:\nerrorCode:" + codeResponse.getErrcode() + "\nerrormsg:" +codeResponse.getErrmsg() );
			System.out.println("\nget Code Error:\nerrorCode:" + codeResponse.getErrcode() + "\nerrormsg:" +codeResponse.getErrmsg() );
			return null;
		}
		return codeResponse.getOpenid();
	}
	
	private static CodeResponse getFromUrl(String url)
	{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		Gson gson = new Gson();
		return gson.fromJson(responseEntity.getBody(), CodeResponse.class);
	}
	
	private UserInfoResponse GetUserInfoByOpenId(String openId)
	{
		if(StringUtils.isBlank(openId))
			return null;
//		DjUserQRcodeTools qrTools = new DjUserQRcodeTools();
		String accessToken = qrTools.validAccessToken();
		if(StringUtils.isBlank(accessToken))
			return null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+ accessToken +"&openid="+ openId +"&lang=zh_CN";
		ResponseEntity<String> responseEntity = getResponseEntity(url);
		
		Gson gson = new Gson();
		return gson.fromJson(responseEntity.getBody(), UserInfoResponse.class);
		
	}
	
	private ResponseEntity<String> getResponseEntity(String url)
	{
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForEntity(url, String.class);
	}
	
	
}
