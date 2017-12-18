package com.zxsm.wsc.controller.front.wx;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.common.tencent.controller.DjWeChatAuthorizeController;
import com.zxsm.wsc.common.unionpay.acp.sdk.AcpService;
import com.zxsm.wsc.common.unionpay.acp.sdk.SDKConfig;
import com.zxsm.wsc.common.unionpay.acp.sdk.UnionpayBase;
import com.zxsm.wsc.entity.ad.DjAd;
import com.zxsm.wsc.entity.article.DjArticle;
import com.zxsm.wsc.entity.common.DjBaseRequestParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.goods.DjGoodsCategory;
import com.zxsm.wsc.entity.management.DjNaviItem;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.article.DjArticleService;
import com.zxsm.wsc.service.goods.DjAdService;
import com.zxsm.wsc.service.goods.DjGoodsService;
import com.zxsm.wsc.service.management.DjNaviItemService;
import com.zxsm.wsc.service.user.DjUserService;

@Controller
@RequestMapping(value = "/wx")
public class DjIndexController extends DjBaseController
{
	
	Logger logger = Logger.getLogger(DjIndexController.class);
	@Autowired
	private DjAdService adSvs;
	
	@Autowired
	private DjArticleService articleSvs;
	
	@Autowired
	private DjNaviItemService djNaviItemSvs;
	
	@Autowired
	private DjGoodsService goodsSvs;
	
	@Autowired
	private DjUserService userSvs;
	
	@RequestMapping
	public String index(HttpServletRequest req,ModelMap map,String code,HttpSession session,HttpServletResponse res)
	{
		if(StringUtils.isNotBlank(code))
		{
			System.out.println("获取OpenId");
			
			String openId = DjWeChatAuthorizeController.getOpenIdByCode(code);
			session.setAttribute(DjKeys.K_WECHAT_OPENID, openId);
			
			if(!isLogin() && StringUtils.isNotBlank(openId))
			{
				DjUser user = userSvs.findByOpenid(openId);
				if(user != null)
				{
					user.setLastLoginTime(new Date());
					userSvs.save(user);
					session.setAttribute(DjKeys.SESSION_USER_USERNAME, user);
					userLogin(user, res);
				}
			}
		}
		
		//首页轮播
		List<DjAd> ads = adSvs.findByCategoryTitle("首页轮播-Touch");
		map.addAttribute("ads", ads);
		
		//导航栏
		List<DjNaviItem> naviItems = djNaviItemSvs.findTo10();
		map.addAttribute("naviItems",naviItems);
		
		//9好动态
		List<DjArticle> articles = articleSvs.findByArticleCateTitle("9号动态");
		map.addAttribute("articles", articles);
		
		//有效的秒杀商品
		Page<DjGoods> goods = goodsSvs.findKillGoods(1,0, 6);
		map.addAttribute("goods", goods);
		
		// 分类展示-9
		List<DjGoodsCategory> guideCategory = goodsSvs.findGuideCategory();
		 Iterator<DjGoodsCategory> iter = guideCategory.iterator();
		 int i = 0;
		 List<String> cateTitleKey = new ArrayList<>();
		 while(iter.hasNext())
		 {
			 DjGoodsCategory cate = (DjGoodsCategory) iter.next();
			 List<DjGoods> cGoods = goodsSvs.findGoodsByCategoryId(cate.getId());
			 String cateTitle = cate.getTitle() + i++;
			 cateTitleKey.add(cateTitle);
			 map.addAttribute(cateTitle + "ImgUrl",cate.getImgUri());
			 map.addAttribute(cateTitle + "cId", cate.getId());
			 map.addAttribute(cateTitle, cGoods);
		 }
		 map.addAttribute("cateKey", cateTitleKey);
		 
		 //首页中部图-Touch
		 List<DjAd> midAds = adSvs.findByCategoryTitle("首页中部图-Touch");
		 if(midAds != null && midAds.size()  > 1)
		 map.addAttribute("midAds", midAds.get(0));
		 
		 return "/wx/index";
	}
	
	@RequestMapping(value = "/goodGoodsmore")
	public String goodsMore(DjBaseRequestParam param,ModelMap map)
	{
		// 好货推荐
		Page<DjGoods> goodGoods = goodsSvs.findSaleTypeGoods(2,param.page,6);
		map.addAttribute("goodGoods",goodGoods);
		return "/wx/index_goods";
	}
	
	@RequestMapping(value = "/test")
	public String TestUnionPay(HttpServletRequest req, HttpServletResponse res,ModelMap map) throws IOException
	{
//		logger.info("Static:" + DjKeys.imagePath); 
		DjKeys key = new DjKeys();
		logger.info("nStatic:" + key.imagepath);
		
		SDKConfig.getConfig().loadPropertiesFromSrc();
		
		res.setContentType("text/html; charset="+ UnionpayBase.encoding);
		
		String merId = req.getParameter("merId");
		String txnAmt = req.getParameter("txnAmt");
		
		merId = "802500059120503";
		txnAmt = "1100";
		
		Map<String, String> requestData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		requestData.put("version", UnionpayBase.version);   			  //版本号，全渠道默认值
		requestData.put("encoding", UnionpayBase.encoding); 			  //字符集编码，可以使用UTF-8,GBK两种方式
		requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		requestData.put("txnType", "01");               			  //交易类型 ，01：消费
		requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
		requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
		requestData.put("channelType", "07");           			  //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
		
		/***商户接入参数***/
		requestData.put("merId", merId);    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
		requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
		requestData.put("orderId",UnionpayBase.getOrderId());             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
		requestData.put("txnTime", UnionpayBase.getCurrentTime());        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
		requestData.put("txnAmt", txnAmt);             			      //交易金额，单位分，不要带小数点
		//requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
		
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		requestData.put("frontUrl", UnionpayBase.frontUrl);
		
		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		requestData.put("backUrl", UnionpayBase.backUrl);

		// 订单超时时间。
		// 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
		// 此时间建议取支付时的北京时间加15分钟。
		// 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
		requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
		
		//////////////////////////////////////////////////
		//
		//       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
		//
		//////////////////////////////////////////////////
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> submitFromData = AcpService.sign(requestData,UnionpayBase.encoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,UnionpayBase.encoding); 
		map.addAttribute("html", html);
//		res.getWriter().write(html);
		return "/wx/unionpay";
	}
	
	
}
