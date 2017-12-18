package com.zxsm.wsc.controller.front.wx;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.common.UtilsTools.DjSMSUtils;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.common.tencent.common.RandomStringGenerator;
import com.zxsm.wsc.common.tencent.controller.DjUserQRcodeTools;
import com.zxsm.wsc.entity.ad.DjAd;
import com.zxsm.wsc.entity.common.DjOUpdateParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.order.DjOrder;
import com.zxsm.wsc.entity.user.DjAddress;
import com.zxsm.wsc.entity.user.DjUFeedback;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.goods.DjAdService;
import com.zxsm.wsc.service.order.DjOrderService;
import com.zxsm.wsc.service.user.DjAddressService;
import com.zxsm.wsc.service.user.DjMessageService;
import com.zxsm.wsc.service.user.DjUCashService;
import com.zxsm.wsc.service.user.DjUCollectService;
import com.zxsm.wsc.service.user.DjUCommentService;
import com.zxsm.wsc.service.user.DjUFeedbackService;
import com.zxsm.wsc.service.user.DjUPointService;
import com.zxsm.wsc.service.user.DjUScanService;
import com.zxsm.wsc.service.user.DjUserRelationsService;
import com.zxsm.wsc.service.user.DjUserService;

@Controller
@RequestMapping(value = "/wx/u")
public class DjUserController extends DjBaseController
{
	@Autowired
	private DjAdService adSvs;

	@Autowired
	private DjAddressService addressSvs;

	@Autowired
	private DjUScanService scanSvs;

	@Autowired
	private DjUCommentService commentSvs;

	@Autowired
	private DjUCollectService collectSvs;

	@Autowired
	private DjMessageService msgSvs;

	@Autowired
	private DjUFeedbackService feedSvs;

	@Autowired
	private DjUserService userSvs;
	
	@Autowired
	private DjUserRelationsService relationSvs;

	@Autowired
	private DjUPointService pointSvs;
	
	@Autowired
	private DjOrderService orderSvs;
	
	@Autowired
	private DjUCashService cashSvs;
	
	@Autowired
	private DjUserQRcodeTools qrcodeTools;


	@RequestMapping()
	public String index(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;

		DjUser user = getUserInfo();
		map.addAttribute("user", user);

		map.addAttribute("scanNo", scanSvs.countScansByUid(user.getId()));
		map.addAttribute("collectNo",collectSvs.countCollectsByUid(user.getId()));
		map.addAttribute("commentNo", commentSvs.countCommentByUid(user.getId()));

		map.addAttribute("msg", msgSvs.countMsgByUidAndType(user.getId(), 0));
		
		map.addAttribute("isSignPoint", pointSvs.isSignPoint(user.getId()));


		//用户中心底部-Touch
		List<DjAd> ads = adSvs.findByCategoryTitle("用户中心底部-Touch");
		if(ads != null && ads.size() > 0)
			map.addAttribute("ads", ads.get(0));

		return "/wx/user/ucenter";
	}

	//基本资料
	@RequestMapping(value="/profile")
	public String profile(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin;

		DjUser user = getUserInfo();
		
		map.addAttribute("user", userSvs.findOne(user.getId()));

		return "/wx/user/uprofile";
	}

	//基本资料
	@RequestMapping(value="/profile/save")
	@ResponseBody
	public Map<Object, Object> profileSave(DjUParam param,DjUser upUser,ModelMap map)
	{

		Map<Object, Object> res = new HashMap<Object, Object>();

		res.put("error", 1);

		if(!isLogin())
		{
			res.put("error", 2);
			res.put("msg", "请重新登录");
			return res;
		}
		DjUser user = getUserInfo();

		if (null == user)
		{
			res.put("error", 1);
			res.put("msg", "不存在该用户");
			return res;
		}

		user.setSex(upUser.getSex());
		user.setNickname(upUser.getNickname());
		user.setBirthday(upUser.getBirthday());
		user.setHeadImage(upUser.getHeadImage());

		userSvs.save(user);

		res.put("error", 0);
		res.put("msg", "保存成功");
		return res;
	}

	@RequestMapping(value="/address")
	public String address(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin;

		DjUser user = getUserInfo();

		List<DjAddress> addresses = addressSvs.findAddressByUid(user.getId());
		map.addAttribute("addresses", addresses);

		return "/wx/user/address";
	}

	@RequestMapping("/address/edit")
	public String editAddress(Long id, ModelMap map, HttpSession session)
	{

		if(!isLogin())
			return URL_RedirectLogin;

		if (null != id)
		{
			map.addAttribute("address", addressSvs.findAddress(id));
		}

		return "/wx/user/address_add";
	}
	@RequestMapping("/address/newdefault")
	@ResponseBody
	public Map<Object, Object> changeDefault(Long id, ModelMap map)
	{
		Map<Object, Object> res = new HashMap<Object, Object>();

		res.put("error", 1);

		if (null == id)
		{
			res.put("msg", "id不能为空");
			return res;
		}

		DjAddress address = addressSvs.findAddress(id);

		if (null == address)
		{
			res.put("msg", "改地址不存在");
			return null;
		}

		res.put("error", 0);

		address.setIsDefault(true);
		addressSvs.saveAddress(address);

		return res;
	}

	@RequestMapping(value = "/address/submit", method = RequestMethod.POST)
	public String submitAddress(DjAddress address, ModelMap map)
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

		return "redirect:/wx/u/address";
	}

	@RequestMapping("/address/del")
	public String delAddress(Long id, ModelMap map)
	{

		if(!isLogin())
			return URL_RedirectLogin;

		DjUser user = getUserInfo();

		if (null != id)
		{
			DjAddress address = addressSvs.findAddress(id);
			if(address.getUid() == user.getId())
				addressSvs.deleteAddress(id);
		}

		return "redirect:/wx/u/address";
	}

	@RequestMapping(value="/collect")
	public String collect(DjUParam param, ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		List<DjGoods> goods = collectSvs.findCollectByUserId(user.getId());
		map.addAttribute("goods", goods);
		return "/wx/user/collect";
	}

	@RequestMapping(value="/collect/del")
	@ResponseBody
	public Map<String, Object> collectDel(DjUParam param, ModelMap map)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("error", 1);
		if(!isLogin())
		{
			res.put("error", 2);
			res.put("msg", "请先登录");
			return res;
		}

		DjUser user = getUserInfo();

		if(collectSvs.deleteByUserIdAndGid(user.getId(), param.msgId))
		{
			res.put("error", 0);
			res.put("msg", "删除成功");
			return res;
		}
		else
		{
			res.put("msg", "删除失败");
			return res;
		}
	}

	@RequestMapping(value="/scan")
	public String scan(DjUParam param, ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		List<DjGoods> goods = scanSvs.findScanByUid(user.getId());
		map.addAttribute("goods", goods);
		return "/wx/user/scan";
	}

	@RequestMapping(value="/scan/del")
	@ResponseBody
	public Map<String, Object> scanDel(DjUParam param, ModelMap map)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("error", 1);
		if(!isLogin())
		{
			res.put("error", 2);
			res.put("msg", "请先登录");
			return res;
		}

		DjUser user = getUserInfo();

		if(scanSvs.deleteByUidAndGid(user.getId(), param.msgId))
		{
			res.put("error", 0);
			res.put("msg", "删除成功");
			return res;
		}
		else
		{
			res.put("msg", "删除失败");
			return res;
		}
	}
	@RequestMapping("/feedback")
	public String feedback(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		if (null == user) {
			map.addAttribute("error", "该用户不存在");
			return "/mobile/error_404";
		}

		return "/wx/user/feedback";
	}


	@RequestMapping("/feedback/submit")
	public String feedbackSubmit(DjUFeedback feedback,DjUParam param, ModelMap map)
	{

		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		feedSvs.saveFeedback(feedback, user);

		return "redirect:/wx/u";
	}
	
	/**
	 * 分销
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/distribution")
	public String distribution(DjUParam param, ModelMap map)
	{
		if(!isLogin()) 
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();
		if(user.getUsername().contains("MD"))
			map.addAttribute("shop", user.getRealName());
		map.addAttribute("cash", cashSvs.countCurrentCashByUserId(user.getId()));
		map.addAttribute("points", pointSvs.countCurrentPointsByUserId(user.getId()));
//		try {
//			map.addAttribute("qrcodeUrl", qrcodeTools.validQRcodeUrl(user.getId()));
//		} catch (Exception e) {
//			map.addAttribute("qrcodeUrl", e.getMessage());
//		}
		map.addAttribute("distribution", relationSvs.findEnbaledByUid(user.getId()));
		map.addAttribute("number", (int)(Math.random()*100));
		return "/wx/user/distribution";
	}
	@RequestMapping(value = "/spreadimg/{id}")
	public void spreadImg(@PathVariable Integer id,DjUParam param,String title, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, JSONException
	{
		if(!isLogin())
			return ;

		DjUser user = getUserInfo();
		qrcodeTools.QRcodeByUidAndResponse(user.getId(), title, request, response);
	}
	
	/**
	 * 分销会员
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/spreaduser")
	public String spreadUser(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		if (null == user)
		{
			map.addAttribute("error", "该用户不存在");
			return "/mobile/error_404";
		}
		
//		map.addAttribute("points", userSvs.findByPid(user.getId()));
		map.addAttribute("point_list", userSvs.findByPid(user.getId()));

		return "/wx/user/spread_user";
	}
	/**
	 * 余额明细
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/cash")
	public String cash(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		if (null == user)
		{
			map.addAttribute("error", "该用户不存在");
			return "/mobile/error_404";
		}

		map.addAttribute("points", cashSvs.countCurrentCashByUserId(user.getId()));
		map.addAttribute("point_list", cashSvs.findCashByUserId(user.getId()));

		return "/wx/user/cash";
	}

	/**
	 * 积分明细
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping("/point")
	public String point(DjUParam param,ModelMap map)
	{

		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl ;

		DjUser user = getUserInfo();

		if (null == user)
		{
			map.addAttribute("error", "该用户不存在");
			return "/mobile/error_404";
		}

		map.addAttribute("points", pointSvs.countCurrentPointsByUserId(user.getId()));
		map.addAttribute("spent_points", pointSvs.countSpentPointsByUserId(user.getId()));
		map.addAttribute("received_points", pointSvs.countReceivedPointsByUserId(user.getId()));
		map.addAttribute("point_list", pointSvs.findPointByUserId(user.getId()));

		return "/wx/user/point";
	}
	@RequestMapping(value="/point/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> pointAdd()
	{
		Map<Object, Object> res = new HashMap<Object, Object>();

		res.put("status", DjKeys.C_FAILED);

		if(!isLogin())
		{
			res.put("msg", "请重新登录!");
			return res;
		}
		
		res.put("status",	DjKeys.C_SUCCESS);
		DjUser user = getUserInfo();
		Boolean isAdd = pointSvs.signPoint(user);
		 if(isAdd)
			 res.put("msg", "成功获得20积分");
		 else
			 res.put("msg", "今天已经领取过");
		 return res;
	}
	
	@RequestMapping(value="/order/{status}")
	public String orderList(@PathVariable Integer status, DjUParam param ,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		map.addAttribute("orderStatus", status);
		DjUser user = getUserInfo();
		
		List<DjOrder> order_list = orderSvs.findByUidAndStatus(user.getId(), status);
		
		map.addAttribute("order_list",order_list);
		
		return "/wx/user/order_list";
	}
	
	@RequestMapping(value="/order/detail/{orderId}")
	public String orderDetail(@PathVariable Integer orderId, DjUParam param ,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		map.addAttribute("orderStatus", orderId);
		
		DjOrder order = orderSvs.findOrder(orderId);
		
		map.addAttribute("order",order);
		
		return "/wx/user/order_detail";
	}
	
	@RequestMapping(value="/order/receive")
	public String orderReceive(ModelMap map,Long orderId)
	{
		if(!isLogin())
			return URL_RedirectLogin;
		DjOrder order = orderSvs.findOrder(orderId);
		DjOUpdateParam param = new DjOUpdateParam();
		param.orderNo = order.getOrderNo();
		orderSvs.updateOrderReceive(param);
		map.addAttribute("order",order);
		return "/wx/user/order_detail";
	}
	
	@RequestMapping(value="/binding")
	public String userBinding(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		
		return "/wx/user/binding";
	}
	
	@RequestMapping(value="/binding/code")
	@ResponseBody
	public Map<String,String> userBindingCode(DjUParam param,ModelMap map,HttpSession session)
	{
//		DjUser user2 = getUserInfo();
//		if(user2.getUsername().length() == 11)
//			return DjTools.mapN("当前账号不需要绑定");
		DjUser user = userSvs.findByUsername(param.mobile);
		if(user != null)
			return DjTools.mapN("该手机号已注册");
		String verifyCode = DjSMSUtils.getVerifyCode();
		
		Map<String, String> resultMap = DjSMSUtils.sendSMS(DjSMSUtils.getContent(verifyCode), param.mobile);
		if(resultMap.get("status") == "n")
			return DjTools.mapN("发送失败，稍后再试！");
		
		System.out.println("SMS_CODE:" + verifyCode + "\nSMS_MSG:" + resultMap.get("info"));
		
		session.setAttribute(DjKeys.SESSION_USER_SMSCODE, param.mobile+"|"+ verifyCode);
		
		return DjTools.mapY("验证码已发送，请等待！");
	}
	@RequestMapping(value = "/binding/codeCheck")
	@ResponseBody
	public Map<String,String> codeCheck(DjUParam param,HttpSession session)
	{
		
		DjUser user2 = getUserInfo();
		
		if(StringUtils.isBlank(param.getMobile()))
			return DjTools.mapN("参数错误！");
		if(StringUtils.isBlank(param.getCode()))
			return  DjTools.mapN("参数错误！");

		String mobileAndCode = (String)session.getAttribute(DjKeys.SESSION_USER_SMSCODE);
		if(StringUtils.isBlank(mobileAndCode))
			return  DjTools.mapN("请重新发送验证码");
		String[] strings = mobileAndCode.split("\\|");
		if(strings.length != 2)
			return  DjTools.mapN("请重新发送验证码");
		if(param.mobile.equalsIgnoreCase(strings[0]) &&param.code.equalsIgnoreCase(strings[1]))
		{
			userSvs.bindingUsername(user2.getId(), param.mobile);
			Map<String, String> res = new HashMap<String, String>();
			res.put("status", "y");
			
			if(StringUtils.isNoneBlank(param.returnTo))
				res.put("urlStr", param.returnTo);
			else
				res.put("urlStr", "/wx/u");
			return res;
		}
		return  DjTools.mapN("验证码错误");
	}
	
	
}
