package com.zxsm.wsc.common.tencent.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.UtilsTools.DateUtils;
import com.zxsm.wsc.common.UtilsTools.QRCodeImageUtils;
import com.zxsm.wsc.common.tencent.common.Configure;
import com.zxsm.wsc.common.tencent.common.Signature;
import com.zxsm.wsc.common.tencent.common.Util;
import com.zxsm.wsc.common.tencent.entity.AccessTokenResponse;
import com.zxsm.wsc.common.tencent.entity.JsTicket;
import com.zxsm.wsc.common.tencent.entity.JsTicketResponse;
import com.zxsm.wsc.common.tencent.entity.UserInfoResponse;
import com.zxsm.wsc.entity.management.DjSystemConfig;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.management.DjSystemConfigService;
import com.zxsm.wsc.service.user.DjUserService;


@Service
public class DjUserQRcodeTools {

	//连接超时时间，默认10秒
	private static int socketTimeout = 10000;

	//传输超时时间，默认30秒
	private static int connectTimeout = 30000;

	//图片大小
	private static int pictureSize = 120;

	private final Logger logger = Logger.getLogger(DjUserQRcodeTools.class);

	@Autowired
	private DjSystemConfigService sysSvs;

	@Autowired
	private DjUserService userSvs;
	
	@Value("${imagePath}")
	private String rootPath;

	/**
	 * 获取有效的accessToken
	 * @return
	 */
//	@Test
	public String validAccessToken()
	{
		Date currenDate				= new Date();
		String accessToken;
		DjSystemConfig tokenConfig	= sysSvs.findByConfigKey(DjKeys.K_WECHAT_ACCESSTOKEN);
		Long timelimit = (long)7100 * (long)1000;
		if(tokenConfig == null)
		{
			accessToken	= getAccessToken();
			tokenConfig = new DjSystemConfig();
			tokenConfig.setConfigKey(DjKeys.K_WECHAT_ACCESSTOKEN);
			tokenConfig.setConfigType(1);
			tokenConfig.setDataType((byte) 1);
			tokenConfig.setConfigValue(accessToken);
			tokenConfig.setUpdateBy(0L);
			tokenConfig.setUpdateTime(currenDate);
			sysSvs.save(tokenConfig);
		}
		else if (StringUtils.isBlank(tokenConfig.getConfigValue()) || timelimit.compareTo(currenDate.getTime() - tokenConfig.getUpdateTime().getTime()) <0)
		{
			accessToken = getAccessToken();
			tokenConfig.setConfigValue(accessToken);
			tokenConfig.setUpdateTime(new Date());
			sysSvs.save(tokenConfig);

		}
		else
		{
			accessToken = tokenConfig.getConfigValue();
		}
		return accessToken;
	}
	
	/**
	 * 获取有效的ticket
	 * @return
	 */
	public String validJsTicket()
	{
		Date currenDate				= new Date();
		String jsTicket;
		DjSystemConfig ticketConfig	= sysSvs.findByConfigKey(DjKeys.K_WECHAT_JSTICKET);
		Long timelimit = (long)7100 * (long)1000;
		if(ticketConfig == null)
		{
			jsTicket	= getJsTicket();
			ticketConfig = new DjSystemConfig();
			ticketConfig.setConfigKey(DjKeys.K_WECHAT_JSTICKET);
			ticketConfig.setConfigType(1);
			ticketConfig.setDataType((byte) 1);
			ticketConfig.setConfigValue(jsTicket);
			ticketConfig.setUpdateBy(0L);
			ticketConfig.setUpdateTime(currenDate);
			sysSvs.save(ticketConfig);
		}
		else if (StringUtils.isBlank(ticketConfig.getConfigValue()) || timelimit.compareTo(currenDate.getTime() - ticketConfig.getUpdateTime().getTime()) <0)
		{
			jsTicket = getJsTicket();
			ticketConfig.setConfigValue(jsTicket);
			ticketConfig.setUpdateTime(new Date());
			sysSvs.save(ticketConfig);

		}
		else
		{
			jsTicket = ticketConfig.getConfigValue();
		}
		return jsTicket;
	}
	
	
	public String reLoadAccessToken()
	{
		String accessToken;
		DjSystemConfig tokenConfig	= sysSvs.findByConfigKey(DjKeys.K_WECHAT_ACCESSTOKEN);

		accessToken = getAccessToken();
		tokenConfig.setConfigValue(accessToken);
		tokenConfig.setUpdateTime(new Date());
		sysSvs.save(tokenConfig);
		return accessToken;
	}

	/**
	 * 获取AccessToken
	 * @return
	 */
	public static String getAccessToken()
	{
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Configure.getAppid()+ "&secret=" + Configure.getAppSecret();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(responseEntity.getBody());
		Gson gson = new Gson();
		AccessTokenResponse aTResponse = gson.fromJson(responseEntity.getBody(), AccessTokenResponse.class);
		return aTResponse.getAccess_token();
	}
	
	public String getJsTicket()
	{
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ validAccessToken() +"&type=jsapi";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(responseEntity.getBody());
		Gson gson = new Gson();
		JsTicketResponse aTResponse = gson.fromJson(responseEntity.getBody(), JsTicketResponse.class);
		return aTResponse.getTicket();
	}

	/**
	 * * 获取临时二维码的Url 30天有效期
	 * @param accessToken 微信凭证
	 * @param Uid 用户id
	 * @return 二维码Url
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public String getQRcodeUrl(String accessToken,Long Uid) throws JSONException, UnsupportedEncodingException
	{
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		url = url.replace("TOKEN", accessToken);
		
		String params = "{\"expire_seconds\": 2592000, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + Uid +"}}}";
		String ticketStr = sendPost(url, params);
		JSONObject resultJson	= new JSONObject(ticketStr);
		//		String ticket = resultJson.getString("ticket");
		if(ticketStr.contains("access_token is invalid"))
		{
			return this.getQRcodeUrl(this.reLoadAccessToken(), Uid);
		}
		String QRcodeUrl = resultJson.getString("url");
		return QRcodeUrl;
	}


	/**
	 ** 获取永久二维码Url
	 * @param accessToken 微信凭证
	 * @param Uid 用户id
	 * @return 二维码Url
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public String getLongQRcodeUrl(String accessToken,Long Uid) throws JSONException, UnsupportedEncodingException
	{
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		url = url.replace("TOKEN", accessToken);
		
		String params = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": " + Uid +"}}}";
		String ticketStr = sendPost(url, params);
		JSONObject resultJson	= new JSONObject(ticketStr);
		//		String ticket = resultJson.getString("ticket");
		if(ticketStr.contains("access_token is invalid"))
		{
			return this.getLongQRcodeUrl(this.reLoadAccessToken(), Uid);
		}
		String QRcodeUrl = resultJson.getString("url");
		return QRcodeUrl;
	}

	/**
	 * post请求
	 * @param url
	 * @param jsonStr
	 * @return 请求结果
	 * @throws UnsupportedEncodingException
	 */
	public String sendPost(String url,String jsonStr) throws UnsupportedEncodingException
	{
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		String result = null;

		HttpPost httpPost = new HttpPost(url);

		//得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(jsonStr, "UTF-8");
		httpPost.addHeader("Content-Type", "text/json");
		httpPost.setEntity(postEntity);

		//设置请求器的配置
		httpPost.setConfig(requestConfig);

		Util.log("executing request" + httpPost.getRequestLine());

		try {
			CloseableHttpClient httpClientCustom = HttpClients.custom().build();
			HttpResponse response = httpClientCustom.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (ConnectionPoolTimeoutException e) {
			logger.error("微信接口：http get throw ConnectionPoolTimeoutException(wait time out)");

		} catch (ConnectTimeoutException e) {
			logger.error("微信接口：http get throw ConnectTimeoutException");

		} catch (SocketTimeoutException e) {
			logger.error("微信接口：http get throw SocketTimeoutException");

		} catch (Exception e) {
			logger.error("微信接口：http get throw Exception\n" + e.getMessage());

		} finally {
			httpPost.abort();
		}

		return result;
	}

	/**
	 * 生成二维码
	 * @param qrCodeUrl url
	 * @param size 二维码大小，像素值
	 * @param response
	 */
	public void getQRCode(String qrCodeUrl, String title, DjUser user, int size, HttpServletRequest request,HttpServletResponse response) {

		//推广背景图片
		String targetImg = rootPath + "shareback.jpg";
		String defaultHeadImg = rootPath + DjKeys.DEFAULT_USER_AVATAR;

		//生成二维码图片
		BufferedImage image = QRCodeImageUtils.getQRCode(qrCodeUrl, 400);
		if(user.getuType() != null && user.getuType() == 2)
		{
			try
			{
				image = QRCodeImageUtils.getQRCodeAndTitle(qrCodeUrl, user.getRealName(), 400);
				ImageIO.write(image, "png", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		
		try {
			//过期时间
			Date expireDate = DateUtils.getAddDay(user.getQrcodeUpdate(), 29);
			String expire = DateUtils.convertDateToString(expireDate);
			//头像
			BufferedImage headimage = null;
			if(StringUtils.isBlank(user.getHeadImage())){
				user.setHeadImage("/images/" + DjKeys.DEFAULT_USER_AVATAR);
			}
			if(user.getHeadImage().indexOf("http:")==0){
				headimage = ImageIO.read(new URL(user.getHeadImage()));
			}else{
				try {
					headimage = ImageIO.read(new File(rootPath+user.getHeadImage().replace("/images/", "")));
				} catch (Exception e) {
					//					e.printStackTrace();
					headimage = ImageIO.read(new File(defaultHeadImg));
				}
			}
			//标题
			if(StringUtils.isBlank(title)){
				title = "真善美药房";
			}
			//生成推广图片
			BufferedImage spreadimage = QRCodeImageUtils.genernateQRCodeImage(headimage, image, targetImg, user.getNickname(), title, expire);
			//输出图片
			ImageIO.write(spreadimage, "png", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据Uid获取用户分享二维码Url
	 * @param uId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 */
	public String validQRcodeUrl(Long uId) throws UnsupportedEncodingException, JSONException
	{
		String qrcodeUrl = null;
		if(uId == null)
		{
			return null;
		}
		DjUser user = userSvs.findOne(uId);

		if(user == null)
		{
			return null;
		}
		else if(isValidDate(user.getQrcodeUpdate()))
		{
			return user.getQrcodeUrl();
		}
		else
		{
			qrcodeUrl = this.getQRcodeUrl(this.validAccessToken(), uId);
			user.setQrcodeUrl(qrcodeUrl);
			user.setQrcodeUpdate(new Date());
			userSvs.save(user);
		}
		return qrcodeUrl;
	}

	
	/**
	 * 获取二维码
	 * @param uId
	 * @param title
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 */
	public void QRcodeByUidAndResponse(Long uId, String title, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, JSONException
	{
//		    	String QRcodeUrl = this.validQRcodeUrl(uId);
		//    	this.getQRCode(QRcodeUrl, pictureSize, response);
		String qrcodeUrl = null;
		if(uId != null && uId >0)
		{
			DjUser user = userSvs.findOne(uId);
			if(user != null)
			{
				if(StringUtils.isBlank(user.getQrcodeUrl()) && user.getuType() != null && user.getuType() == 2)
				{
					qrcodeUrl = this.getLongQRcodeUrl(this.validAccessToken(), user.getId());
					user.setQrcodeUrl(qrcodeUrl);
					user.setQrcodeUpdate(new Date());
					userSvs.save(user);
				}
				
				if(isValidDate(user.getQrcodeUpdate()) || (StringUtils.isNotBlank(user.getQrcodeUrl()) && user.getuType() != null && user.getuType() == 2))
				{
					qrcodeUrl = user.getQrcodeUrl();
				}
				else
				{
					qrcodeUrl = this.getQRcodeUrl(this.validAccessToken(), uId);
					user.setQrcodeUrl(qrcodeUrl);
					user.setQrcodeUpdate(new Date());
					userSvs.save(user);
				}
				this.getQRCode(qrcodeUrl, title, user, pictureSize,request, response);
			}

		}

	}
	
	
	/**
	 * 通过openId 获取用户微信信息
	 * @param openId
	 * @return
	 */
	public UserInfoResponse GetUserInfoByOpenId(String openId)
	{
		if(StringUtils.isBlank(openId))
			return null;
//		DjUserQRcodeTools qrTools = new DjUserQRcodeTools();
		String accessToken = this.validAccessToken();
		if(StringUtils.isBlank(accessToken))
			return null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+ accessToken +"&openid="+ openId +"&lang=zh_CN";
		ResponseEntity<String> responseEntity = getResponseEntity(url);
		
		Gson gson = new Gson();
		String uft8_response = null;
		try {
			uft8_response =new String(responseEntity.getBody().getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return gson.fromJson(responseEntity.getBody(), UserInfoResponse.class);
		}
		return gson.fromJson(uft8_response, UserInfoResponse.class);
		
	}
	
	
	/**
	 * 
	 * 分享按钮所需的参数
	 * @param url 当前网页的URL，不包含#及其后面部分
	 * @return
	 * 
	 */
	public JsTicket getJsTicketBody(String url)
	{
		JsTicket ticketBody = new JsTicket();
		ModelMap returnsignmap = new ModelMap();
		returnsignmap.addAttribute("jsapi_ticket", validJsTicket());
		returnsignmap.addAttribute("noncestr", ticketBody.getNonceStr());
		returnsignmap.addAttribute("timestamp", ticketBody.getTimestamp());
		returnsignmap.addAttribute("url", url);
		ticketBody.setSignature(Signature.getJsTicketSign(returnsignmap));
		
		return ticketBody;
		
	}
	
	
	private ResponseEntity<String> getResponseEntity(String url)
	{
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate.getForEntity(url, String.class);
	}


	/**
	 * 时间是否有效 30天有效
	 * @param date
	 * @return
	 */
	public Boolean isValidDate(Date date)
	{
		if(date == null)
		{
			return false;
		}
		Date addDay = DateUtils.getAddDay(date, 29);
		
		Date currentDay = DateUtils.getNow();
		
		return currentDay.compareTo(addDay) < 0 ? true : false;
	}
}
