package com.zxsm.wsc.common.UtilsTools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class DjSMSUtils
{
	public static Map<String,String> sendSMS(String content,String mobiles)
	{
		if(StringUtils.isBlank(content))
			return DjTools.mapN("内容不能为空");
		
		try
		{
			content = URLEncoder.encode(content, "GB2312");
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
			return DjTools.mapN("转码错误");
		}
		//String serviceUrl = "http://www.10086x.com/sends.asp";
		String USERNAME = "zsmyzm";
		String PASSWORD = "zsm123456";
		String url = "http://www.10086x.com/sends.asp?user="+ USERNAME +"&passw="+ PASSWORD +"&text="+ content +"&mobiles="+ mobiles +"&SEQ=1000";
		
		StringBuffer return_code = null;
		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			httpConn.setRequestProperty("Content-type", "text/html");
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			httpConn.setRequestProperty("contentType", "utf-8");
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;

			if (httpConn.getResponseCode() >= 300)
			{
				return DjTools.mapN("网络请求失败 code=" + httpConn.getResponseCode());
			}

			try
			{
				inputStream = httpConn.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);

				while ((tempLine = reader.readLine()) != null)
				{
					resultBuffer.append(tempLine);
				}
				return_code = resultBuffer;
				return resultVerity(return_code.toString());

			}
			finally
			{
				if (reader != null)
				{
					reader.close();
				}
				if (inputStreamReader != null)
				{
					inputStreamReader.close();
				}
				if (inputStream != null)
				{
					inputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DjTools.mapN("验证码发送失败");
		}
	}
	
	public static String getVerifyCode()
	{
		StringBuffer chkCode = new StringBuffer();
		//验证码的字符集，去掉了一些容易混淆的字符
//		char[] character1 = { '2', '3', '4', '5', '6', '8', '9', 'a', 'b', 'd', 'e', 'f', 'h', 'k', 'm', 'n', 'r', 'x', 'y'};
		char[] character = { '1','2', '3', '4', '5', '6','7', '8', '9','0'};
        Random rnd = new Random();
        //生成验证码字符串 
        for (int i = 0; i < 4; i++)
        {
            chkCode.append(character[rnd.nextInt(character.length)]);
        }
        return chkCode.toString();
	}
	
	/**
	 * 验证码内容
	 * @param verifyCode
	 * @return
	 */
	public static String getContent(String verifyCode)
	{
		return "\"" + verifyCode + "\" 是您的验证码，请勿告诉他人！";
	}
	
	/**
	 * 订单提醒内容
	 * @param orderNo
	 * @return
	 */
	public static String getOrderContent(String orderNo)
	{
		return "订单" + orderNo + "已付款,注意查看!";
	}
	
	public static String getOrderWait(String orderNo)
	{
		return "处方订单" + orderNo + "待确认";
	}
	
	public static Map<String ,String> resultVerity(String code)
	{
		switch (code)
		{
		case "0":
			return DjTools.mapY("发送成功");
		case "1":
			return DjTools.mapN("用户名或者密码错误");
		case "2":
			return DjTools.mapN("短信内容为空");
		case "3":
			return DjTools.mapN("手机号码为空");
		case "4":
			return DjTools.mapN("手机余额不足");
		case "5":
			return DjTools.mapN("超出每次发送500条限制");
		case "6":
			return DjTools.mapN("短信内容长度不能超过70字");
		case "50":
			return DjTools.mapN("配置参数错误");
		default:
			return DjTools.mapN("未知错误");
		}
	}
	
}
