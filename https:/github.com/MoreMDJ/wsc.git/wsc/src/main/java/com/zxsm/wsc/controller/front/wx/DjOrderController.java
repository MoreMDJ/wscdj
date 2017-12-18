package com.zxsm.wsc.controller.front.wx;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.WebUtils;
import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.common.tencent.common.Configure;
import com.zxsm.wsc.common.tencent.common.Signature;
import com.zxsm.wsc.common.tencent.common.XMLParser;
import com.zxsm.wsc.common.tencent.protocol.pay_protocol.UnifiedOrderReqData;
import com.zxsm.wsc.common.tencent.protocol.pay_protocol.UnifiedOrderResData;
import com.zxsm.wsc.common.tencent.protocol.pay_protocol.WCPayRequestDate;
import com.zxsm.wsc.common.tencent.service.UnifiedOrderService;
import com.zxsm.wsc.common.unionpay.acp.demo.BackRcvResponse;
import com.zxsm.wsc.common.unionpay.acp.sdk.AcpService;
import com.zxsm.wsc.common.unionpay.acp.sdk.LogUtil;
import com.zxsm.wsc.common.unionpay.acp.sdk.SDKConstants;
import com.zxsm.wsc.common.unionpay.acp.sdk.UnionpayUtil;
import com.zxsm.wsc.entity.common.DjOUpdateParam;
import com.zxsm.wsc.entity.order.DjCartGoods;
import com.zxsm.wsc.entity.order.DjExpressType;
import com.zxsm.wsc.entity.order.DjOParam;
import com.zxsm.wsc.entity.order.DjOrder;
import com.zxsm.wsc.entity.order.DjPayType;
import com.zxsm.wsc.entity.order.DjShop;
import com.zxsm.wsc.entity.user.DjAddress;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.order.DjCartGoodsService;
import com.zxsm.wsc.service.order.DjExpressTypeService;
import com.zxsm.wsc.service.order.DjOrderService;
import com.zxsm.wsc.service.order.DjPayTypeService;
import com.zxsm.wsc.service.order.DjShopService;
import com.zxsm.wsc.service.user.DjAddressService;
import com.zxsm.wsc.service.user.DjUCashService;
import com.zxsm.wsc.service.user.DjUPointService;
import com.zxsm.wsc.service.user.DjUserService;


/*
               光标到处Bug灰飞烟灭

                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑    胸怀万码    永无BUG
 */

@Controller
@RequestMapping(value="/wx/order")
public class DjOrderController extends DjBaseController
{
	private final static Logger logger = Logger.getLogger(DjOrderController.class);
	
	@Autowired
	private DjUserService userSvs;

	@Autowired
	private DjAddressService addressSvs;

	@Autowired
	private DjCartGoodsService cGoodsSvs;
	
	@Autowired
	private DjPayTypeService paytypeSvs;
	
	@Autowired
    DjExpressTypeService expressTypeSvs;
	
	@Autowired
	DjShopService shopSvs;
	
	@Autowired
	DjOrderService orderSvs;
	
	@Autowired
	DjUPointService pointSvs;
	
	@RequestMapping()
	public String order(DjUParam param ,ModelMap map, DjOParam oParam,Long addressId,HttpSession session)
	{

		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";

		if(oParam == null || !oParam.verity())
			oParam = (DjOParam)session.getAttribute(DjKeys.SESSION_ORDER_PARAM); 
		else
			session.setAttribute(DjKeys.SESSION_ORDER_PARAM, oParam);

		if(oParam == null || !oParam.verity())
			return "redirect:/wx/u/order/2";

		DjUser user = getUserInfo();

		addressSvs.defaultAddress(user.getId(), addressId, map, session);
		
		//商品
		List<DjCartGoods> cGoodsList = cGoodsSvs.checkCartGoodsList(user.getId(),oParam);
		if(cGoodsList == null || cGoodsList.size() < 1)
			return "redirect:/wx/u/order/2";
		
		map.addAttribute("totalPrice", cGoodsSvs.getTotalPrice(cGoodsList));
		map.addAttribute("expressFee", cGoodsSvs.getTotalExpressFee(cGoodsList));
		map.addAttribute("goods", cGoodsList);
		
		// 支付方式
		Integer typeId = cGoodsList.get(0).getType() == null ? 2 : cGoodsList.get(0).getType();
		List<DjPayType> paytype = paytypeSvs.findByType(typeId);
		map.addAttribute("paytype", paytype);
		map.addAttribute("typeId", typeId);
		
		// 总积分
		map.addAttribute("totalPoint", pointSvs.countCurrentPointsByUserId(user.getId()));
		
		//配送方式
		String deliveryType = (String)session.getAttribute(DjKeys.SESSION_ORDER_DELIVERYTYPE);
		if(StringUtils.isNotBlank(deliveryType))
		{
			map.addAttribute("deliveryType",deliveryType);
			DjExpressType expressType = expressTypeSvs.findByTitle(deliveryType);
			if(expressType != null)
				map.addAttribute("deliveryType", expressType.getTitle());
			
			Long shopId = (Long)session.getAttribute(DjKeys.SESSION_ORDER_SHOPID);
			if(shopId != null)
				map.addAttribute("shop_choose", shopSvs.findShop(shopId));
		}
		
		return "/wx/order/order_confirm";
	}

	@RequestMapping(value="/address")
	public String orderAddress(DjUParam param,Long addressId,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin;

		DjUser user = getUserInfo();

		List<DjAddress> addresses = addressSvs.findAddressByUid(user.getId());
		map.addAttribute("addresses", addresses);

		if(addressId != null)
			map.addAttribute("addressId", addressId);

		return "/wx/order/order_address";
	}


	@RequestMapping("/address/edit")
	public String orderAddressEdit(Long id, ModelMap map, HttpSession session)
	{

		if(!isLogin())
			return URL_RedirectLogin;

		if (null != id)
		{
			map.addAttribute("address", addressSvs.findAddress(id));
		}

		return "/wx/order/order_address_add";
	}

	@RequestMapping(value = "/address/submit", method = RequestMethod.POST)
	public String orderAddressSubmit(DjAddress address, ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin;

		if (null == address)
		{
			return "/mobile/error_404";
		}

		DjUser user = getUserInfo();

		address.setUid(user.getId());

		// 保存地址
		address = addressSvs.saveAddress(address);

		return "redirect:/wx/order?addressId=" + address.getId();
	}

	@RequestMapping(value = "/goods")
	public String orderGoods(ModelMap map,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		
		DjUser user = getUserInfo();
		
		DjOParam oParam = (DjOParam)session.getAttribute(DjKeys.SESSION_ORDER_PARAM); 
		if(oParam == null || !oParam.verity())
			return "redirect:/wx";
		
		//商品
		List<DjCartGoods> cGoodsList = cGoodsSvs.updateByIdsAndUid(oParam.chooseIds(), user.getId());
		
		map.addAttribute("goods", cGoodsList);
		
		return "/wx/order/order_goods";
	}
	
	@RequestMapping(value = "/delivery")
	public String orderDelivery(ModelMap map,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		
		List<DjExpressType> express = expressTypeSvs.findEnbaleExpress();
		map.addAttribute("express", express);
		String deliveryType = (String)session.getAttribute(DjKeys.SESSION_ORDER_DELIVERYTYPE);
		if(StringUtils.isNotBlank(deliveryType))
			map.addAttribute("deliveryType", deliveryType);
		
		Long shopId = (Long)session.getAttribute(DjKeys.SESSION_ORDER_SHOPID);
		if(shopId != null)
			map.addAttribute("shop_choose", shopSvs.findShop(shopId));
		
		return "/wx/order/order_delivery_type";
	}
	
	@RequestMapping(value = "/delivery/choose")
	public String orderDeliveryChoose(ModelMap map,String deliveryType,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		
		List<DjExpressType> express = expressTypeSvs.findEnbaleExpress();
		map.addAttribute("express", express);
		if(StringUtils.isNotBlank(deliveryType))
		{
			session.setAttribute(DjKeys.SESSION_ORDER_DELIVERYTYPE, deliveryType);
			if(!deliveryType.contains("自提"))
				session.removeAttribute(DjKeys.SESSION_ORDER_SHOPID);
		}
		
		return "redirect:/wx/order";
	}
	
	@RequestMapping(value = "/delivery/shop")
	public String OrderShop(ModelMap map,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		
		session.setAttribute(DjKeys.SESSION_ORDER_DELIVERYTYPE,"门店自提");
		Long shopId = (Long)session.getAttribute(DjKeys.SESSION_ORDER_SHOPID);
		if(shopId != null)
			map.addAttribute("shop_choose", shopSvs.findShop(shopId));
		
		List<DjShop> shops = shopSvs.findShopAll();
		map.addAttribute("shops", shops);
		
		return "/wx/order/order_shop_list";
	}
	
	@RequestMapping(value="/delivery/shop/action")
	public String orderDeliveryAction(DjOParam param,ModelMap map,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		if(param.shopId != null)
		{
			session.setAttribute(DjKeys.SESSION_ORDER_SHOPID, param.shopId);

			return "redirect:/wx/order";
		}
		Long shopId = (Long)session.getAttribute(DjKeys.SESSION_ORDER_SHOPID);
		if(shopId != null)
			map.addAttribute("shop_choose", shopSvs.findShop(shopId));
		return "";
	}
	
	@RequestMapping(value="/delivery/shop/search")
	public String orderDeliverySearch(DjOParam param,ModelMap map,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		
		if(StringUtils.isNotBlank(param.action) && param.action.equalsIgnoreCase("search"))
		{
			map.addAttribute("province", param.province);
			map.addAttribute("city", param.city);
			map.addAttribute("district", param.district);
			
			List<DjShop> shops = shopSvs.findShopByAdress(param.province, param.city, param.district, null);
			map.addAttribute("shops", shops);
			
			Long shopId = (Long)session.getAttribute(DjKeys.SESSION_ORDER_SHOPID);
			if(shopId != null)
				map.addAttribute("shop_choose", shopSvs.findShop(shopId));
			return "/wx/order/order_shop_list";
		}
		
		return "";
	}
	
	@RequestMapping(value = "/submit")
	@ResponseBody
	public Map<String, Object> orderSubmit(DjOParam oParam,HttpSession session, ModelMap map)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", DjKeys.C_FAILED);
		
		if(!isLogin())
		{
			res.put("msg","请登录");
			return res;
		}
		DjUser user = getUserInfo();
		
		if(!oParam.submitVerity())
		{
			res.put("msg", "请求参数错误:" + DjKeys.C_FAILED_UNKNOWN_PARAM);
			return res;
		}
		
		orderSvs.paramTwoToOne(oParam,session);
//		oParam.typeId = 2;//销售单
		
		DjOrder order = orderSvs.initOrder(oParam, session);
		if(order == null)
		{
			res.put("msg", "请求参数错误:" + DjKeys.C_FAILED_NULLPARAM);
			return res;
		}
		if(!orderSvs.clearOrderSessionAndCartGoods(oParam, session, user.getId()))
		{
			res.put("msg", "请求参数错误");
			return res;
		}
		if(order.getIsOnlinePay())
		{
			res.put("code", DjKeys.C_SUCCESS);
			session.setAttribute(DjKeys.SESSION_ORDER_WAITPAY, order);
		}
		else
			res.put("code", DjKeys.C_FAILED_NOTONLINEPAY);
		res.put("msg", "订单生成成功");
		
		//处方单提示
		if(order.getTypeId() == 1)
		{
			
			DjOUpdateParam param = new DjOUpdateParam();
			param.setOrderNo(order.getOrderNo());
			param.setRemark("abcd1234");
			orderSvs.orderNotify(param);
		}
		
		return res;
	}
	@RequestMapping(value = "/buynow")
	public String orderBuynow(DjOParam oParam,Long addressId,HttpSession session)
	{
		if(!isLogin())
			return URL_RedirectLogin + "/wx/cart";
		
		return "/wx/order/order_confirm";
	}
	
	/**
	 * 在个人中心支付
	 * @param orderId
	 * @param session
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/user/pay")
	public String userOrderPay(Long orderId,HttpSession session, ModelMap map,HttpServletRequest req)
	{
		if(!isLogin())
		{
			map.addAttribute("msg", "请登录");
			return "/wx/order/wxpay_return";
		}
		DjUser user = getUserInfo();
			
		map.addAttribute("status",DjKeys.C_FAILED);
		
		
		if(orderId == null)
		{
			map.addAttribute("msg", "参数错误 -- 01");
			return "/wx/order/wxpay_return";
		}
		DjOrder order = orderSvs.findOrder(orderId);
		if(order == null || order.getUserId() == null || !order.getUserId().equals(user.getId()))
		{
			map.addAttribute("msg", "参数错误 -- 02");
			return "/wx/order/wxpay_return";
		}
		if(order.getPaymentTitle().equals("微信支付"))
		{
			if(!WebUtils.isWechatClient(req))
			{
				map.addAttribute("msg", "请在微信客户端打开完成支付");
				return "/wx/order/wxpay_return";
			}
			WCPayRequestDate wcPayRequestDate = wxPay(user,order,map,(String)session.getAttribute(DjKeys.K_WECHAT_OPENID));
			if(wcPayRequestDate != null)
			{
				map.addAttribute("wcPayData", wcPayRequestDate);
				map.addAttribute("status",DjKeys.C_SUCCESS);
				return "/wx/order/wxpay_return";
			}
		}
		else if(order.getPaymentTitle().equals("银联支付"))
		{
			String unionpayRequest = UnionpayUtil.unionpayRequest(order.getOrderNo(), order.getPayAmt());
			map.addAttribute("html", unionpayRequest);
			return "/wx/unionpay";
		}
		return "/wx/order/wxpay_return";
	}
	
	
	@RequestMapping(value = "/pay")
	public String orderPay(HttpSession session, ModelMap map,String code,HttpServletRequest req)
	{
		if(!isLogin())
			return URL_RedirectLogin;
		
		DjOrder order = (DjOrder)session.getAttribute(DjKeys.SESSION_ORDER_WAITPAY);
		map.addAttribute("status",DjKeys.C_FAILED);
		if(!orderSvs.isCanPayOrder(order))
			map.addAttribute("msg", "参数错误-未知订单-0");
		
		DjOrder payOrder = orderSvs.findOrder(order.getId());
		if(!orderSvs.isCanPayOrder(payOrder))
			map.addAttribute("msg", "参数错误-未知订单-1");
		
		if(order.getPaymentTitle().equals("微信支付"))
		{
			if(!WebUtils.isWechatClient(req))
			{
				map.addAttribute("msg", "请在微信客户端打开完成支付");
				return "/wx/order/wxpay_return";
			}
			
			WCPayRequestDate wcPayRequestDate = wxPay(getUserInfo(),payOrder,map,(String)session.getAttribute(DjKeys.K_WECHAT_OPENID));
			if(wcPayRequestDate != null)
			{
				map.addAttribute("wcPayData", wcPayRequestDate);
				map.addAttribute("status",DjKeys.C_SUCCESS);
				return "/wx/order/wxpay_return";
			}
		}
		else if(order.getPaymentTitle().equals("银联支付"))
		{
			String unionpayRequest = UnionpayUtil.unionpayRequest(order.getOrderNo(), order.getPayAmt());
			map.addAttribute("html", unionpayRequest);
			return "/wx/unionpay";
		}
		
		return "/wx/order/wxpay_return";
	}
	
	/**
	 * 微信支付 微信服务器后台通知
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxNotify")
	public void wxNotify(HttpServletRequest req,HttpServletResponse res) throws Exception
	{
		BufferedReader reader = req.getReader();
		String line = "";
		String xmlString = null;
		StringBuffer inputString = new StringBuffer();

		while ((line = reader.readLine()) != null) {
			inputString.append(line);
		}
		xmlString = inputString.toString();
		req.getReader().close();
		logger.info("\nWeChat pay notify :=-=-=-接收到的数据如下:\n" + xmlString + "\n");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap = XMLParser.getMapFromXML(xmlString);
		if(checkSign(returnMap))
		{
			//支付成功 
			if(returnMap.get("result_code").toString().equalsIgnoreCase("SUCCESS"))
			{
				logger.info("订单"+ returnMap.get("result_code") +"验证签名结果[成功].");
				DjOUpdateParam param = new DjOUpdateParam();
				param.setOrderNo(returnMap.get("out_trade_no").toString());
				param.setRemark("abcd123");
				orderSvs.updateOrderPayment(param);
				logger.info("订单"+ returnMap.get("result_code") +"更新订单结果[成功].");
			}
			else // 支付失败
			{
				logger.info("\nWeChat pay notify :" +returnMap.get("return_msg") + "\n");
			}
		}
		String content = "<xml>\n"
				+ "<result_code>SUCCESS</result_code>\n"
				+ "<return_code></return_code>\n"
				+ "</xml>\n";
		// 把xml字符串写入响应
		byte[] xmlData = content.getBytes();

		res.setContentType("text/xml");
		res.setContentLength(xmlData.length);

		ServletOutputStream os = res.getOutputStream();
		os.write(xmlData);

		os.flush();
		os.close();

	}
	
	@RequestMapping(value = "/unionNotify")
	public void unionNotify(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = BackRcvResponse.getAllRequestParam(req);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				valideData.put(key, value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (valideData == null || !AcpService.validate(valideData, encoding)) {
			logger.info("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			logger.info("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			
			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
			logger.info("orderId:"+orderId);
			DjOUpdateParam uParam = new DjOUpdateParam();
			uParam.setOrderNo(orderId);
			uParam.setRemark("abcd123");
			orderSvs.updateOrderPayment(uParam);
			
			String respCode = valideData.get("respCode"); //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
			logger.info("respCode:"+respCode);
		}
		logger.info("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		res.getWriter().print("ok");
	}
	
	private Boolean checkSign(Map<String, Object> map) {

        if(map == null)
        	return false;

        String signFromAPIResponse = map.get("sign").toString();

        if (signFromAPIResponse == "" || signFromAPIResponse == null) {

            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");

            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);

        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

        map.put("sign", "");

        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

        String signForAPIResponse = Signature.getSign(map);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {

            //签名验不过，表示这个API返回的数据有可能已经被篡改了

            System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);

            return false;

        }

        System.out.println("恭喜，API返回的数据签名验证通过!!!");

        return true;

    }
	
	private WCPayRequestDate wxPay(DjUser user,DjOrder order,ModelMap map,String openId)
	{
//		openId = "o6DzCjjkdQ73dec1ZZBu8b3G_M1A";
		if(user == null || order == null)
			return null;
		UnifiedOrderReqData orderReqData = new UnifiedOrderReqData("订单支付", null, null, order.getOrderNo(), "CNY", (int)Math.round(order.getOrderAmount().doubleValue()*100), "WEB", Configure.getIP(), null, null, Configure.getNotifyUrl(), "JSAPI", null, null, openId, null);
		try {
			UnifiedOrderService uoSvs = new UnifiedOrderService();
			try {
				String request = uoSvs.request(orderReqData);
				Map<String,Object> map1 = XMLParser.getMapFromXML(request);
				
				Gson gson = new Gson();
				UnifiedOrderResData resData = gson.fromJson(map1.toString(), UnifiedOrderResData.class);
				WCPayRequestDate wcPayRequestDate = new WCPayRequestDate(resData.getPrepay_id());
				return wcPayRequestDate;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
