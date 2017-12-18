package com.zxsm.wsc.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public class WebUtils {

	private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };
	
	
	public static Boolean isAjaxRequest(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With");
		if(StringUtils.isNotEmpty((requestType))){
			return true;
		}
		return false;
	}
	
	public static void responseJson(HttpServletResponse response, Map<String, String> map){
		Gson gson = new Gson();
		String json = gson.toJson(map);
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=utf-8");  
		PrintWriter out = null;  
		try {  
		    out = response.getWriter();  
		    out.append(json.toString());  
		} catch (IOException e) {  
		    e.printStackTrace();  
		} finally {  
		    if (out != null) {  
		        out.close();  
		    }  
		}  
		 
	}
	/**
	 * 加密替换字符串（字符替换为*）
	 * @param str
	 * @param start 开始角标
	 * @param size 替换长度
	 * @return
	 */
	public static String secretFliterString(String str, int start, int size){
	   StringBuffer sb = new StringBuffer();
	   if(StringUtils.isNotEmpty(str)){
		   int length = str.length();
		   if(length<=start || length <= (start+size)){
			   return str;
		   }
		   sb.append(str.substring(0,start-1));
		   for(int i=0;i<size;i++){
			   sb.append("*");
		   }
		   sb.append(str.substring(start+size-1));
	   }
	   return sb.toString();
   }
	
	/**
	 * 判断是否为移动端
	 * @author Max
	 */
	public static boolean checkAgentIsMobile(String ua) {
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) 
		{
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) 
			{
				for (String item : agent)
				{
					if (ua.contains(item)) 
					{
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否为微信断
	 * @author Max
	 */
	public static boolean isWechatClient(HttpServletRequest request){
		String ua = request.getHeader("user-agent") .toLowerCase();
		boolean flag = false;
		String ag = ua .toLowerCase();
		if (ag.indexOf("micromessenger") > 0) {// 是微信浏览器
			  flag =true;
		}
		return flag;
	}
}
