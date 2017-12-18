package com.zxsm.wsc.common.tencent.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.tencent.common.DecriptUtils;
import com.zxsm.wsc.common.tencent.entity.TextMessage;
import com.zxsm.wsc.entity.management.DjSystemConfig;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.management.DjSystemConfigService;
import com.zxsm.wsc.service.user.DjUserService;



@Controller
@RequestMapping(value = "/wechat")
public class WechatNotify {
	
	private final static String KToUserName			= "ToUserName";
	private final static String KFromUserName		= "FromUserName";
	private final static String KMsgType			= "MsgType";
	private final static String KEvent				= "Event";
	private final static String KEventKey			= "EventKey";
	
	private final static String TOKEN				= "2rattfOUOHUWQkqRGxY8aBzk3jfi4MDJ";
	
	private final static String MSGTYPE_TEXT		= "text";
	
	/**
	 * 消息类型
	 * 1.subscribe：订阅
	 * 2.unsubscribe：取消订阅
	 * 3.SCAN：用户已关注时的事件推送
	 * 4.VIEW：点击菜单跳转链接时的事件推送
	 * 5.CLICK：点击菜单拉取消息时的事件推送
	 */
	private final static String MSGTYPE_EVENT		= "event";
	private final static String EVENTYPE_SUBSCRIBE	= "subscribe";		//订阅关注，带参数关注
	private final static String EVENTYPE_UNSUBSCRIBE= "unsubscribe";	//取消关注
	private final static String EVENTYPE_SCAN		= "scan";	 		//浏览
	private final static String EVENTYPE_VIEW		= "view";			//点击菜单跳转链接事件推送 eventKey: 事件key值，设置的跳转url
	private final static String EVENTYPE_CLICK		= "click";			//点击菜单拉取消息事件推送 evnetKey: 事件key值，自定义菜单接口中KEY值对应
	
	private final static String CONTENTMSG			= "欢迎关注真善美药房!\n";
	
	private static Integer INCREASENO				= 0;
	private static final Integer MAX_INCREASE_NO	= 9;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DjUserService userSvs;
	
	@Autowired
    DjSystemConfigService configSvs;
	
//	@Autowired
//	private TdUserIntegralService tdUserIntegralService;
//	
//	@Autowired
//	private TdUserAccountService tdUserAccountService;
	
	/**
	 * 验证
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @throws IOException
	 */
	@RequestMapping(value = "/notify",method = RequestMethod.GET)
	public void wxConform(HttpServletResponse response,String signature,String timestamp,String nonce,String echostr) throws IOException
	{
		
//		Map<String,Object> map = new HashMap<String,Object>();
//		eventypeClick(map, response);
		
		ArrayList<String> signStr = new ArrayList<String>();
		signStr.add(TOKEN);
		signStr.add(timestamp);
		signStr.add(nonce);
		Collections.sort(signStr);
		String resultSign = DecriptUtils.SHA1(signStr.get(0) + signStr.get(1) + signStr.get(2));
		
		logger.error("signature:" + signature +"|timestamp:" + timestamp+ "|nonce:" + nonce+"|echostr:" + echostr+"|resultSign:" + resultSign);
		if(StringUtils.isNotBlank(signature) && signature.equalsIgnoreCase(resultSign))
		{
			response.getWriter().print(echostr);
		}
//		response.getWriter().print(echostr);
	}
	
	/**
	 * 事件推送
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		response.setContentType("text/xml;charset=UTF-8");
		logger.error("WX:STEP-------------------->进入通知Controller!");
		Map<String,Object> map = this.parseXml(request);
		logger.error("WX:MAP-------------------->"+map);
//		String srt = "{=, =, =, =, =, =}";
//		Map<String,Object>map = new HashMap<>();
//		map.put("CreateTime", "1487385853");
//		map.put("EventKey", "last_trade_no_4004222001201702180363610679");
//		map.put("Event", "subscribe");
//		map.put("ToUserName", "gh_873bb9c013d0");
//		map.put("FromUserName", "o6DzCjiAYR_oe8yZYTEXAekWnpDo");
//		map.put("MsgType", "event");
		
		String event = null;
		if(map.get(KEvent)!= null)
			event = map.get(KEvent).toString();
		
		String msgType		= map.get(KMsgType).toString();
		
		if (MSGTYPE_EVENT.equalsIgnoreCase(msgType)) //事件类型
		{
			if (EVENTYPE_SUBSCRIBE.equalsIgnoreCase(event) && map.get(KEventKey) != null)//关注微信号
			{
				SubScribe(map,response);
			}
			else if(EVENTYPE_CLICK.equalsIgnoreCase(event))
			{
				eventypeClick(map, response);
			}
		}
		else if(msgType.equalsIgnoreCase(MSGTYPE_TEXT))//文字消息
		{
			TextMessage msg = new TextMessage(map);
			DjSystemConfig config = configSvs.findByConfigKey(DjKeys.K_WX_AUTOREPLY);
			if(config != null && StringUtils.isNotBlank(config.getConfigValue()))
				msg.setContent(config.getConfigValue());
			response.getWriter().println(msg.toString());
		}
		else
		{
			TextMessage msg = new TextMessage(map);
			DjSystemConfig config = configSvs.findByConfigKey(DjKeys.K_WX_AUTOREPLY);
			if(config != null && StringUtils.isNotBlank(config.getConfigValue()))
				msg.setContent(config.getConfigValue());
			response.getWriter().println(msg.toString());
		}
	}
	
	
	public void SubScribe(Map<String,Object> map,HttpServletResponse response) throws IOException
	{
		TextMessage msg	= new TextMessage();
		String openId	= map.get(KFromUserName).toString();
		String WxId		= map.get(KToUserName).toString();
		msg.setToUserName(openId);
		msg.setFromUserName(WxId);
		String eventKey = "";
		if(map.get(KEventKey) != null)
			eventKey = map.get(KEventKey).toString();
		if(StringUtils.isBlank(openId))
		{
			response.getWriter().println(msg.toString());
			return;
		}
		else if(StringUtils.isBlank(eventKey))
		{
			response.getWriter().println(msg.toString());
			return;
		}
		else if(eventKey.contains("qrscene_"))
		{
			String spreadStr = eventKey.replace("qrscene_", "");
			Long spreadId = this.parseLong(spreadStr);
			DjUser user = userSvs.createBySpreadId(spreadId, openId);
			if(user != null)
			{
				msg.setContent(CONTENTMSG + "你已扫码关注成功!\n快到商城个人中心查看吧 0.0\n您的默认密码：123456");
			}
		}
		response.getWriter().println(msg.toString());
	}
	
	public void eventypeClick(Map<String,Object> map,HttpServletResponse response) throws IOException
	{
		String eventKey	= "";
		if(map.get(KEventKey) != null)
				eventKey = map.get(KEventKey).toString();
		TextMessage msg = new TextMessage(map);
		DjSystemConfig config = configSvs.findByConfigKey(eventKey);
		if(config != null && StringUtils.isNotBlank(config.getConfigValue()))
			msg.setContent(config.getConfigValue());
		response.getWriter().print(msg.toString());
		
	}
	
	
	/**
	 * 验证用户有效性
	 * @param user
	 * @return
	 */
	public Boolean isVaildUser(DjUser user)
	{
		if(user == null)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Stirng 转 Integer
	 * @param intStr
	 * @return
	 */
	public Integer parseInt(String intStr)
	{
		if(StringUtils.isBlank(intStr))
		{
			return 0;
		}
		Integer resultInt = null;
		try {
			resultInt = Integer.parseInt(intStr);
		}
		catch (NumberFormatException e)
		{
			logger.error("WX:ERROR------>" + e.getMessage());
		}
		finally
		{
			if(resultInt == null)
			{
				resultInt = 0;
			}
		}
		return resultInt;
	}
	
	/**
	 * Stirng 转 Long
	 * @param intStr
	 * @return
	 */
	public Long parseLong(String intStr)
	{
		if(StringUtils.isBlank(intStr))
		{
			return null;
		}
		Long resultInt = null;
		try {
			resultInt = Long.parseLong(intStr);
		}
		catch (NumberFormatException e)
		{
			logger.error("WX:ERROR------>" + e.getMessage());
		}
		finally
		{
			if(resultInt == null)
			{
				resultInt = null;
			}
		}
		return resultInt;
	}
	
	
	/**
	 * 获取唯一字符串
	 * @return
	 */
	public synchronized String getUniqueNo()
	{
		INCREASENO++;
		if(INCREASENO > MAX_INCREASE_NO)
			INCREASENO = 0;
		return Long.toString(System.currentTimeMillis())+ Integer.toString(INCREASENO);
	}
	
	/**
	 * XML转MAP
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> parseXml(HttpServletRequest request) throws Exception {  
		
	    // 将解析结果存储在HashMap中  
	    Map<String,Object> map = new HashMap<String,Object>();  
	   
	    // 从request中取得输入流  
	    InputStream inputStream = request.getInputStream();  
	    // 读取输入流  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(inputStream);  
	    // 得到xml根元素  
	    Element root = document.getRootElement();  
	    // 得到根元素的所有子节点  
	    @SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();  
	   
	    // 遍历所有子节点  
	    for (Element e : elementList)  
	        map.put(e.getName(), e.getText());  
	   
	    // 释放资源  
	    inputStream.close();  
	    inputStream = null;  
	    return map;  
	}

}
