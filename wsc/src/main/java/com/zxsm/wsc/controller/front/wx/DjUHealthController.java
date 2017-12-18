package com.zxsm.wsc.controller.front.wx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.entity.health.DjHealth;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.health.DjHealthService;
import com.zxsm.wsc.service.user.DjUserService;


@Controller
@RequestMapping(value = "/wx/health")
public class DjUHealthController extends DjBaseController
{
	@Autowired
	private DjHealthService healthSvs;
	
	@Autowired
	private DjUserService userSvs;
	
	
	@RequestMapping(value = "/u")
	public String uIndex(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		DjUser user = getUserInfo();
		
		DjUser user2 = userSvs.findOne(user.getId());
		if(user2.getUsername().length() != 11 && !user2.getUsername().contains("MD"))
		{
			map.addAttribute("returnTo", "/wx/health/u");
			return "/wx/user/binding";
		}
		

//		map.addAttribute("user", user);
		if(param.huid == null)
			return "/wx/health/index_u";
		else
			return "/wx/health/index_d";
	}
	
	@RequestMapping(value = "/add/{type}")
	public String add(@PathVariable Integer type,DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;

		if(param.huid != null)
			map.addAttribute("huid", param.huid);
		
		map.addAttribute("date", new Date());
		
		String returnURL = "";
		if(type == null)
			return returnURL;
		switch (type) {
		case 1:
			returnURL = "/wx/health/bloodpressure_add";
			break;
		case 2:
			returnURL = "/wx/health/bloodsugar_add";
			break;
		case 3:
			returnURL = "/wx/health/weight_add";
			break;

		default:
			break;
		}
		return returnURL;

	}
	
	@RequestMapping(value = "/add/{type}", method=RequestMethod.POST)
	public String addSave(@PathVariable Integer type,DjHealth health,DjUParam param,ModelMap map)
	{
		
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		if(param.huid == null)
		{
			DjUser user = getUserInfo();
			health.setUid(user.getId());
			health.setUsername(user.getUsername());
		}
		else
		{
			DjUser user = getUserInfo();
			health.setLogUid(user.getId());
			DjUser user2 = userSvs.findOne(param.huid);
			if(user2 != null)
			{
			health.setUid(user2.getId());
			health.setUsername(user2.getUsername());
			}
			else
				return "redirect:/wx/u/distribution";
		}
		
		String returnURL = "";
		if(type == null)
			return returnURL;
		switch (type) {
		case 1: {
			health.setType(DjHealth.Type_pressure);
			health.setValue4(health.getValue1() - health.getValue2());
			health.setAttribute1(healthSvs.Pressure(health.getValue2() , health.getValue1()));
			break;
			}
		case 2: {
			health.setType(DjHealth.Type_sugar);
			health.setAttribute2(healthSvs.bloodSugar(health.getValue1(), health.getAttribute1()));
			break;
		}
		case 3: {
			health.setType(DjHealth.Type_weight);
			health.setValue3(healthSvs.healthBMI(health.getValue2(), health.getValue1()));
			health.setAttribute1(healthSvs.healthLevel(health.getValue3()));
			break;
		}

		default:
			break;
		}
		healthSvs.save(health);
		
		if(param.huid != null)
			returnURL="redirect:/wx/health/infoadd?huid="+param.huid;
		else 
			returnURL="redirect:/wx/health/u";
		return returnURL;

	}
	
	@RequestMapping(value = "/list/{type}", method=RequestMethod.GET)
	public String list(@PathVariable Integer type,DjHealth health,DjUParam param,ModelMap map,String beginDate,String endDate)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		Date startDate = null, endDate1 = null;
		if (null != beginDate) {
			try {
				cal.setTime(sdf.parse(beginDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			startDate = cal.getTime();
		}
		if (null != endDate) {
			try {
				cal.setTime(sdf.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			endDate1 = cal.getTime();
		}
		
		
		Long searchUid= null;
		if(param.huid != null)
		{
			searchUid= param.huid;
			map.addAttribute("huid", param.huid);
		}
		else
		{
		DjUser user = getUserInfo();
		searchUid = user.getId();
		}
		
		
		List<DjHealth> healthList = null;
		String returnURL = "";
		if(type == null)
			return returnURL;
		switch (type) {
		case 1: {
			health.setType(DjHealth.Type_pressure);
			healthList = healthSvs.findListByUidAndTime(searchUid,DjHealth.Type_pressure, startDate, endDate1);
			returnURL = "/wx/health/list_bp";
			break;
			}
		case 2: {
			health.setType(DjHealth.Type_sugar);
			healthList = healthSvs.findListByUidAndTime(searchUid,DjHealth.Type_sugar, startDate, endDate1);
			returnURL = "/wx/health/list_s";
			break;
		}
		case 3: {
			health.setType(DjHealth.Type_weight);
			healthList = healthSvs.findListByUidAndTime(searchUid,DjHealth.Type_weight, startDate, endDate1);
			returnURL = "/wx/health/list_w";
			break;
		}

		default:
			break;
		}
		map.addAttribute("list", healthList);
		map.addAttribute("beginDate", beginDate);
		map.addAttribute("endDate",endDate);
		return returnURL;
	}
	@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String search(DjUParam param,ModelMap map,String keywords)
	{
		if(StringUtils.isNotBlank(keywords)){
		List<DjUser> users = userSvs.findByUsernameContain(keywords);
		map.addAttribute("user", users);
		}
		if(param.code ==null)
			return "/wx/health/search";
		else
			return "/wx/health/index_d";
	}
	
	@RequestMapping(value = "/info", method=RequestMethod.GET)
	public String searchUser(DjUParam param,ModelMap map,Long uid)
	{
		DjUser user = userSvs.findOne(uid);
		map.addAttribute("user", user);
		return "/wx/health/userinfo";
	}
	
	
	@RequestMapping(value = "/infoadd", method=RequestMethod.GET)
	public String infoadd(DjUParam param,ModelMap map)
	{
		DjUser user = userSvs.findOne(param.huid);
		map.addAttribute("user", user);
		return "/wx/health/userinfo_add";
	}
}
