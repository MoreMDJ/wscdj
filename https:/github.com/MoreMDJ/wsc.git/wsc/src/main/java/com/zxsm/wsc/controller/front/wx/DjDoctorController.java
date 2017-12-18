package com.zxsm.wsc.controller.front.wx;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.zxsm.wsc.common.UtilsTools.CookiesUtil;
import com.zxsm.wsc.common.tencent.controller.DjUserQRcodeTools;
import com.zxsm.wsc.common.tencent.entity.JsTicket;
import com.zxsm.wsc.entity.ad.DjAd;
import com.zxsm.wsc.entity.common.DjOUpdateParam;
import com.zxsm.wsc.entity.doctor.DjDoctor;
import com.zxsm.wsc.entity.doctor.DjDoctorParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.management.DjNaviItem;
import com.zxsm.wsc.entity.order.DjOrder;
import com.zxsm.wsc.entity.user.DjAddress;
import com.zxsm.wsc.entity.user.DjUFeedback;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.doctor.DjDoctorService;
import com.zxsm.wsc.service.goods.DjAdService;
import com.zxsm.wsc.service.management.DjNaviItemService;
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
@RequestMapping(value = "/wx/doctor")
public class DjDoctorController extends DjBaseController
{
	@Autowired
	private DjDoctorService doctorSvs;
	
	@Autowired
	private DjAdService adSvs;
	
	@Autowired
	private DjNaviItemService djNaviItemSvs;
	
	@Autowired
	private DjUserQRcodeTools qrcodeTools;

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

	// 医生分类
	@RequestMapping("/cate")
	public String cate(DjDoctorParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin;
		
		//首页轮播
		List<DjAd> ads = adSvs.findByCategoryTitle("医生顶部轮播-Touch");
		map.addAttribute("ads", ads);
		
		Map<String ,Object>searchMap = new HashMap<String,Object>();
		searchMap.put(DjDoctor.sType, 1);
		searchMap.put(DjDoctor.sIsOnline, true);
		map.addAttribute("doctor_list", doctorSvs.find(searchMap));
		map.addAttribute("docnavi", djNaviItemSvs.findDoctorNavi());
		JsTicket jsTicket = qrcodeTools.getJsTicketBody("http://m.zsmyao.com/wx/doctor/cate");
		jsTicket.setTitle("名医咨询");
		jsTicket.setLink("http://m.zsmyao.com/wx/doctor/cate");
		map.addAttribute("share_param",jsTicket);
		
		return "/wx/doctor/doctor_cate_1";
	}

	// 医生详细
	@RequestMapping("/{did}")
	public String doctor(DjDoctorParam param,ModelMap map,@PathVariable Long did)
	{
		DjDoctor doctor = doctorSvs.findOne(did);
		map.addAttribute("doctor",doctor);
		JsTicket jsTicket = qrcodeTools.getJsTicketBody("http://m.zsmyao.com/wx/doctor/" + did);
		jsTicket.setTitle("名医咨询");
		jsTicket.setLink("http://m.zsmyao.com/wx/doctor/" + did);
		jsTicket.setImgUrl("http://m.zsmyao.com" + doctor.getHeadImgUrl());
		map.addAttribute("share_param",jsTicket);

		return "/wx/doctor/doctor_info";
	}

	// 医生列表
	@RequestMapping(value="/list")
	public String doctorList(DjDoctorParam param,ModelMap map,HttpServletRequest res)
	{
		if(!isLogin())
			return URL_RedirectLogin;

		DjUser user = getUserInfo();
		map.addAttribute("user", user);

		Map<String ,Object>searchMap = new HashMap<String,Object>();

		if(StringUtils.isNotBlank(param.keywords))
		{
			if(!res.getMethod().equalsIgnoreCase("POST"))
			{
				searchMap.put(DjDoctor.sCat, param.keywords);
				map.addAttribute("title", param.keywords);
				DjNaviItem docNavi = djNaviItemSvs.findDocItemByTitle(param.keywords);
				if(docNavi != null)
					map.addAttribute("summary", docNavi.getSummary());
			}
			else
				searchMap.put("keywords", param.keywords);
		}
		searchMap.put(DjDoctor.sStatus, 1);
		map.addAttribute("doctor_list", doctorSvs.find(searchMap,param.page, param.size));
		return "/wx/doctor/doctor_list";
	}


	@RequestMapping(value = "/login")
	public String doctorLogin(DjDoctorParam param,ModelMap map,HttpSession session ,HttpServletResponse res)
	{
		if(StringUtils.isNotBlank(param.username))
		{
			DjDoctor doctor = doctorSvs.findByUsernameAndPassword(param.username, param.password);
			if(doctor != null && doctor.getStatus() == 1)
			{
				session.setAttribute("doctor", doctor);
				if(param.cate != null)
				{
					CookiesUtil.setCookie(res, "pc_username", param.getUsername(), 24*60*60*100);
					CookiesUtil.setCookie(res, "pc_password", param.getPassword(), 24*60*60*100);
				}
				return "redirect:/wx/doctor/home";
			}
			map.addAttribute("username", param.username);
			map.addAttribute("error", "1");
		}

		return "/wx/doctor/doctor_login";
	}

	@RequestMapping(value="/home")
	public String doctorHome(DjDoctorParam param,ModelMap map,HttpServletRequest req,HttpSession session)
	{
		DjDoctor doctor = isLogin(session,req);
		if(doctor == null)
			return "redirect:/wx/doctor/login";
		DjDoctor djDoctor = doctorSvs.findOne(doctor.getId());
		map.addAttribute("doctor",djDoctor);
		map.addAttribute("number", djDoctor.getServeNo());
		
		return "/wx/doctor/doctor_home";
	}

	@RequestMapping(value="/number")
	@ResponseBody
	public Map<String, Object> doctorNumber(DjDoctorParam param,ModelMap map,HttpServletRequest req,HttpSession session)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("error", 0);
		
		DjDoctor doctor = isLogin(session,req);
		
		if(doctor == null)
		{
			res.put("msg", "请登录！");
			return res;
		}
		DjDoctor djDoctor = doctorSvs.findOne(doctor.getId());
		if(param.number != null)
		{
			djDoctor.setServeNo(param.number);
			djDoctor.setServedNo(djDoctor.getServedNo() + param.number);
			doctorSvs.save(djDoctor);
			res.put("error", 1);
		}
		return res;
	}
	
	@RequestMapping(value="/status")
	public String doctorStatus(DjDoctorParam param,ModelMap map,HttpServletRequest req,HttpSession session)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("error", 0);
		
		DjDoctor doctor = isLogin(session,req);
		
		if(doctor == null)
		{
			return "redirect:/wx/doctor/login";
		}
		DjDoctor djDoctor = doctorSvs.findOne(doctor.getId());
		if(param.number != null)
		{
//			djDoctor.setServeNo(param.number);
//			djDoctor.setServedNo(djDoctor.getServedNo() + param.number);
			djDoctor.setIsOnline(param.number==1 ? true : false);
			doctorSvs.save(djDoctor);
			res.put("error", 1);
		}
		return "redirect:/wx/doctor/home";
	}


	public DjDoctor isLogin(HttpSession session,HttpServletRequest req)
	{
		DjDoctor user = (DjDoctor)session.getAttribute("doctor");

		if(user == null)
		{
			Cookie username = CookiesUtil.getCookieByName(req, "pc_username");
			Cookie password = CookiesUtil.getCookieByName(req, "pc_password");
			if(username == null || password == null)
				return null;
			user = doctorSvs.findByUsernameAndPassword((String)username.getValue(), (String)password.getValue());
			if(user == null)
				return null;
			session.setAttribute("doctor", user);
		}
		return user;
	}
	//
	//	//基本资料
	//	@RequestMapping(value="/profile/save")
	//	@ResponseBody
	//	public Map<Object, Object> profileSave(DjDoctroParam param,DjUser upUser,ModelMap map)
	//	{
	//
	//		Map<Object, Object> res = new HashMap<Object, Object>();
	//
	//		res.put("error", 1);
	//
	//		if(!isLogin())
	//		{
	//			res.put("error", 2);
	//			res.put("msg", "请重新登录");
	//			return res;
	//		}
	//		DjUser user = getUserInfo();
	//
	//		if (null == user)
	//		{
	//			res.put("error", 1);
	//			res.put("msg", "不存在该用户");
	//			return res;
	//		}
	//
	//		user.setSex(upUser.getSex());
	//		user.setNickname(upUser.getNickname());
	//		user.setBirthday(upUser.getBirthday());
	//		user.setHeadImage(upUser.getHeadImage());
	//
	//		userSvs.save(user);
	//
	//		res.put("error", 0);
	//		res.put("msg", "保存成功");
	//		return res;
	//	}
	//
	//	@RequestMapping(value="/address")
	//	public String address(DjDoctroParam param,ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin;
	//
	//		DjUser user = getUserInfo();
	//
	//		List<DjAddress> addresses = addressSvs.findAddressByUid(user.getId());
	//		map.addAttribute("addresses", addresses);
	//
	//		return "/wx/user/address";
	//	}
	//
	//	@RequestMapping("/address/edit")
	//	public String editAddress(Long id, ModelMap map, HttpSession session)
	//	{
	//
	//		if(!isLogin())
	//			return URL_RedirectLogin;
	//
	//		if (null != id)
	//		{
	//			map.addAttribute("address", addressSvs.findAddress(id));
	//		}
	//
	//		return "/wx/user/address_add";
	//	}
	//	@RequestMapping("/address/newdefault")
	//	@ResponseBody
	//	public Map<Object, Object> changeDefault(Long id, ModelMap map)
	//	{
	//		Map<Object, Object> res = new HashMap<Object, Object>();
	//
	//		res.put("error", 1);
	//
	//		if (null == id)
	//		{
	//			res.put("msg", "id不能为空");
	//			return res;
	//		}
	//
	//		DjAddress address = addressSvs.findAddress(id);
	//
	//		if (null == address)
	//		{
	//			res.put("msg", "改地址不存在");
	//			return null;
	//		}
	//
	//		res.put("error", 0);
	//
	//		address.setIsDefault(true);
	//		addressSvs.saveAddress(address);
	//
	//		return res;
	//	}
	//
	//	@RequestMapping(value = "/address/submit", method = RequestMethod.POST)
	//	public String submitAddress(DjAddress address, ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin;
	//
	//		if (null == address)
	//		{
	//			return "/mobile/error_404";
	//		}
	//
	//		DjUser user = getUserInfo();
	//
	//		address.setUid(user.getId());
	//
	//		// 保存地址
	//		address = addressSvs.saveAddress(address);
	//
	//		return "redirect:/wx/u/address";
	//	}
	//
	//	@RequestMapping("/address/del")
	//	public String delAddress(Long id, ModelMap map)
	//	{
	//
	//		if(!isLogin())
	//			return URL_RedirectLogin;
	//
	//		DjUser user = getUserInfo();
	//
	//		if (null != id)
	//		{
	//			DjAddress address = addressSvs.findAddress(id);
	//			if(address.getUid() == user.getId())
	//				addressSvs.deleteAddress(id);
	//		}
	//
	//		return "redirect:/wx/u/address";
	//	}
	//
	//	@RequestMapping(value="/collect")
	//	public String collect(DjDoctroParam param, ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		List<DjGoods> goods = collectSvs.findCollectByUserId(user.getId());
	//		map.addAttribute("goods", goods);
	//		return "/wx/user/collect";
	//	}
	//
	//	@RequestMapping(value="/collect/del")
	//	@ResponseBody
	//	public Map<String, Object> collectDel(DjDoctroParam param, ModelMap map)
	//	{
	//		Map<String, Object> res = new HashMap<String, Object>();
	//		res.put("error", 1);
	//		if(!isLogin())
	//		{
	//			res.put("error", 2);
	//			res.put("msg", "请先登录");
	//			return res;
	//		}
	//
	//		DjUser user = getUserInfo();
	//
	//		if(collectSvs.deleteByUserIdAndGid(user.getId(), param.msgId))
	//		{
	//			res.put("error", 0);
	//			res.put("msg", "删除成功");
	//			return res;
	//		}
	//		else
	//		{
	//			res.put("msg", "删除失败");
	//			return res;
	//		}
	//	}
	//
	//	@RequestMapping(value="/scan")
	//	public String scan(DjDoctroParam param, ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		List<DjGoods> goods = scanSvs.findScanByUid(user.getId());
	//		map.addAttribute("goods", goods);
	//		return "/wx/user/scan";
	//	}
	//
	//	@RequestMapping(value="/scan/del")
	//	@ResponseBody
	//	public Map<String, Object> scanDel(DjDoctroParam param, ModelMap map)
	//	{
	//		Map<String, Object> res = new HashMap<String, Object>();
	//		res.put("error", 1);
	//		if(!isLogin())
	//		{
	//			res.put("error", 2);
	//			res.put("msg", "请先登录");
	//			return res;
	//		}
	//
	//		DjUser user = getUserInfo();
	//
	//		if(scanSvs.deleteByUidAndGid(user.getId(), param.msgId))
	//		{
	//			res.put("error", 0);
	//			res.put("msg", "删除成功");
	//			return res;
	//		}
	//		else
	//		{
	//			res.put("msg", "删除失败");
	//			return res;
	//		}
	//	}
	//	@RequestMapping("/feedback")
	//	public String feedback(DjDoctroParam param,ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		if (null == user) {
	//			map.addAttribute("error", "该用户不存在");
	//			return "/mobile/error_404";
	//		}
	//
	//		return "/wx/user/feedback";
	//	}
	//
	//
	//	@RequestMapping("/feedback/submit")
	//	public String feedbackSubmit(DjUFeedback feedback,DjDoctroParam param, ModelMap map)
	//	{
	//
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		feedSvs.saveFeedback(feedback, user);
	//
	//		return "redirect:/wx/u";
	//	}
	//	
	//	/**
	//	 * 分销
	//	 * @param param
	//	 * @param map
	//	 * @return
	//	 */
	//	@RequestMapping(value="/distribution")
	//	public String distribution(DjDoctroParam param, ModelMap map)
	//	{
	//		if(!isLogin()) 
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//		if(user.getUsername().contains("MD"))
	//			map.addAttribute("shop", user.getRealName());
	//		map.addAttribute("cash", cashSvs.countCurrentCashByUserId(user.getId()));
	//		map.addAttribute("points", pointSvs.countCurrentPointsByUserId(user.getId()));
	////		try {
	////			map.addAttribute("qrcodeUrl", qrcodeTools.validQRcodeUrl(user.getId()));
	////		} catch (Exception e) {
	////			map.addAttribute("qrcodeUrl", e.getMessage());
	////		}
	//		map.addAttribute("distribution", relationSvs.findEnbaledByUid(user.getId()));
	//		map.addAttribute("number", (int)(Math.random()*100));
	//		return "/wx/user/distribution";
	//	}
	//	@RequestMapping(value = "/spreadimg/{id}")
	//	public void spreadImg(@PathVariable Integer id,DjDoctroParam param,String title, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, JSONException
	//	{
	//		if(!isLogin())
	//			return ;
	//
	//		DjUser user = getUserInfo();
	//		qrcodeTools.QRcodeByUidAndResponse(user.getId(), title, request, response);
	//	}
	//	
	//	/**
	//	 * 分销会员
	//	 * @param param
	//	 * @param map
	//	 * @return
	//	 */
	//	@RequestMapping(value="/spreaduser")
	//	public String spreadUser(DjDoctroParam param,ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		if (null == user)
	//		{
	//			map.addAttribute("error", "该用户不存在");
	//			return "/mobile/error_404";
	//		}
	//		
	////		map.addAttribute("points", userSvs.findByPid(user.getId()));
	//		map.addAttribute("point_list", userSvs.findByPid(user.getId()));
	//
	//		return "/wx/user/spread_user";
	//	}
	//	/**
	//	 * 余额明细
	//	 * @param param
	//	 * @param map
	//	 * @return
	//	 */
	//	@RequestMapping(value="/cash")
	//	public String cash(DjDoctroParam param,ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		if (null == user)
	//		{
	//			map.addAttribute("error", "该用户不存在");
	//			return "/mobile/error_404";
	//		}
	//
	//		map.addAttribute("points", cashSvs.countCurrentCashByUserId(user.getId()));
	//		map.addAttribute("point_list", cashSvs.findCashByUserId(user.getId()));
	//
	//		return "/wx/user/cash";
	//	}
	//
	//	/**
	//	 * 积分明细
	//	 * @param param
	//	 * @param map
	//	 * @return
	//	 */
	//	@RequestMapping("/point")
	//	public String point(DjDoctroParam param,ModelMap map)
	//	{
	//
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl ;
	//
	//		DjUser user = getUserInfo();
	//
	//		if (null == user)
	//		{
	//			map.addAttribute("error", "该用户不存在");
	//			return "/mobile/error_404";
	//		}
	//
	//		map.addAttribute("points", pointSvs.countCurrentPointsByUserId(user.getId()));
	//		map.addAttribute("spent_points", pointSvs.countSpentPointsByUserId(user.getId()));
	//		map.addAttribute("received_points", pointSvs.countReceivedPointsByUserId(user.getId()));
	//		map.addAttribute("point_list", pointSvs.findPointByUserId(user.getId()));
	//
	//		return "/wx/user/point";
	//	}
	//	@RequestMapping(value="/point/add", method = RequestMethod.POST)
	//	@ResponseBody
	//	public Map<Object, Object> pointAdd()
	//	{
	//		Map<Object, Object> res = new HashMap<Object, Object>();
	//
	//		res.put("status", DjKeys.C_FAILED);
	//
	//		if(!isLogin())
	//		{
	//			res.put("msg", "请重新登录!");
	//			return res;
	//		}
	//		
	//		res.put("status",	DjKeys.C_SUCCESS);
	//		DjUser user = getUserInfo();
	//		Boolean isAdd = pointSvs.signPoint(user);
	//		 if(isAdd)
	//			 res.put("msg", "成功获得20积分");
	//		 else
	//			 res.put("msg", "今天已经领取过");
	//		 return res;
	//	}
	//	
	//	@RequestMapping(value="/order/{status}")
	//	public String orderList(@PathVariable Integer status, DjDoctroParam param ,ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl;
	//		map.addAttribute("orderStatus", status);
	//		DjUser user = getUserInfo();
	//		
	//		List<DjOrder> order_list = orderSvs.findByUidAndStatus(user.getId(), status);
	//		
	//		map.addAttribute("order_list",order_list);
	//		
	//		return "/wx/user/order_list";
	//	}
	//	
	//	@RequestMapping(value="/order/detail/{orderId}")
	//	public String orderDetail(@PathVariable Integer orderId, DjDoctroParam param ,ModelMap map)
	//	{
	//		if(!isLogin())
	//			return URL_RedirectLogin + param.reqUrl;
	//		map.addAttribute("orderStatus", orderId);
	//		
	//		DjOrder order = orderSvs.findOrder(orderId);
	//		
	//		map.addAttribute("order",order);
	//		
	//		return "/wx/user/order_detail";
	//	}
	//	
	//	
	//	@RequestMapping(value="/order/receive")
	//	public String orderReceive(ModelMap map,Long orderId)
	//	{
	//		DjOrder order = orderSvs.findOrder(orderId);
	//		DjOUpdateParam param = new DjOUpdateParam();
	//		param.orderNo = order.getOrderNo();
	//		orderSvs.updateOrderReceive(param);
	//		map.addAttribute("order",order);
	//		return "/wx/user/order_detail";
	//	}

}
