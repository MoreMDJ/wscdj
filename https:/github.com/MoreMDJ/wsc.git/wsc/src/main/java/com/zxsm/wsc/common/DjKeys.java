package com.zxsm.wsc.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 系统常量
 * @author maeing
 *
 */
@Service
public class DjKeys
{
	public static final int pageSize = 20;
    
    public static final String templatePath = "src/main/resources/templates/client/";
    
    public static String backupPath;
    
//    @Value("${imagePath}")
//    public static String imagePath;
    
    @Value("${imagePath}")
    public String imagepath;

	static
    {
    	backupPath = "src/main/resources/backup/";
//    	imagePath = "src/main/resources/static/images/";
//    	backupPath = "D:/JAVA/wsc/images/";
//    	imagePath = "D:/JAVA/wsc/images/";
    }
    
    
    // URL
    public static String HOST = "http://m.zsmyao.com";
    
    // 默认值
    
    /**
     * 用户默认头像
     */
    public static final String DEFAULT_USER_AVATAR			= "user_head_default.png";	//用户默认头像
    
    public static final String K_WECHAT_ACCESSTOKEN			= "wechat_accessToken";		//微信accessToken
    public static final String K_WECHAT_JSTICKET			= "wechat_jsapiticket";		//微信accessToken
    
    public static final String K_WECHAT_OPENID				= "wechat_openId";			//微信openId
    public static final String K_WX_AUTOREPLY				= "wechat_autoreply";		//微信自动回复
    
    public static final String K_DISTRIBUTION_LEVELONE		= "distribution_levelone";	//分销 1级
    public static final String K_DISTRIBUTION_LEVELTWO		= "distribution_leveltwo";	//分销 2级
    public static final String K_DISTRIBUTION_LEVELTHREE	= "distribution_levelthree";//分销 3级
    public static final String K_DISTRIBUTION_LEVELONE_ONE	= "distribution_levelone_one";//分销 1级
    
    public static final String K_ORDER_NOTIFY_TEL			= "order_notify_tel";		//订单通知手机号
    
    
    /**
     * 密码
     */
    
    public static final String DefaultPassword = "123456";
    
    public static final String DefaultPasswordKey="maeing8f89jnK1jhBnMiRffyyDJ";
    
    
    /**
     * 常用key值
     * 
     */
    public static final String SESSION_MANAGER_USERNAME		= "manager";
    
    public static final String SESSION_USER_USERNAME    	= "USERNAME";
    
    public static final String SESSION_USER_SMSCODE			= "USERCODE";
    
    public static final String SESSION_USER_RESETPSW		= "USERRESETPSW";
    
    public static final String SESSION_ORDER_PARAM			= "ORDERPARAM";
    
    public static final String SESSION_ORDER_DELIVERYTYPE	= "ORDERDELIVERYTYPE";
    
    public static final String SESSION_ORDER_ADDRESS		= "ORDERADDRESS";
    
    public static final String SESSION_ORDER_SHOPID			= "ORDERSHOPID";
    
    public static final String SESSION_ORDER_WAITPAY		= "ORDERWAITPAY";
    
    
    //**
    //* 
    //*  返回值常量
    //*
    //**
    
    public static final Integer C_SUCCESS					= 0; // 成功
    public static final Integer C_FAILED					= 1; // 失败
    public static final Integer C_FAILED_UNKNOWN_PARAM		= 2; // 未知参数
    public static final Integer C_FAILED_NULLPARAM			= 3; // 参数为空
    public static final Integer C_FAILED_NOTONLINEPAY		= 4; // 非在线支付
    
    
    
    /** 返回页面  */
    
    //
    public static final String URL_RedirectLogin		= "redirect:/wx/login";
    
	public static final String URL_Login				= "/wx/login";
}
