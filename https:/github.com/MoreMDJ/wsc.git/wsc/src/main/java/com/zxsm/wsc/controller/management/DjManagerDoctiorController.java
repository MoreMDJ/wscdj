package com.zxsm.wsc.controller.management;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.doctor.DjDoctor;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.service.doctor.DjDoctorService;
import com.zxsm.wsc.service.management.DjNaviItemService;


@Controller
@RequestMapping("/management/doctor")
public class DjManagerDoctiorController extends DjAdminLevel
{
	@Autowired
	DjDoctorService doctorSvs;
	
	@Autowired
	private DjNaviItemService djNaviItemSvs;
	
	/**
	 * 医生管理列表
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping("/list")
	public String doctorList(DjReqestParam param, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		Map<String ,Object>searchMap = new HashMap<String,Object>();
		
		if ("isOnline".equalsIgnoreCase(param.EVENTTARGET) && param.statusId != null)
		{
			searchMap.put(DjDoctor.sIsOnline, param.statusId==1 ? true : false);
		}
		// 查找
		else if ("btnSearch".equalsIgnoreCase(param.EVENTTARGET))
		{
			param.page = 0;
		}
		else if ("btnPage".equalsIgnoreCase(param.EVENTTARGET))
		{
			if (null == param.EVENTARGUMENT || param.EVENTARGUMENT.isEmpty())
			{
				param.EVENTARGUMENT = "0";
			}

			param.page = Integer.parseInt(param.EVENTARGUMENT);
		}
		
		if(StringUtils.isNotBlank(param.keywords))
			searchMap.put(DjGoods.sKeywords, param.keywords);
		
		// 参数注回
		map.addAttribute("param", param);
		map.addAttribute("doctor_page", doctorSvs.find(searchMap,param.page, param.size));
		
		return "/management/doctor/doctor_list";
	}
	
	
	/**
	 * 账号管理新增或修改
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String doctorEdit(DjReqestParam param, ModelMap map, Long id)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		if(id!= null)
			map.addAttribute("doctor", doctorSvs.findOne(id));
		
		map.addAttribute("docnavi", djNaviItemSvs.findDoctorNavi());
		return "/management/doctor/doctor_edit";
	}
	/**
	 * 账号管理新增或修改
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/del")
	public String doctorDel(DjReqestParam param, ModelMap map,Long id)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		doctorSvs.delete(id);
		return "redirect:/management/doctor/list";
	}
	
	
	@RequestMapping(value = "/save")
	public String doctorSave(DjReqestParam param, ModelMap map,DjDoctor doctor)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		// 参数注回
		map.addAttribute("param", param);
		map.addAttribute("doctor_page", doctorSvs.findAll(param.page, param.size));
		doctorSvs.save(doctor);
		return "redirect:/management/doctor/list";
	}
	
	@RequestMapping(value = "/account/edit")
	public String accountEdit(DjReqestParam param,ModelMap map,Long id)
	{
		map.addAttribute("doctor_page", doctorSvs.findAll(param.page, param.size));
		if(id!= null)
			map.addAttribute("doctor", doctorSvs.findOne(id));
		
		map.addAttribute("docnavi", djNaviItemSvs.findDoctorNavi());
		return "/management/doctor/account_edit";
	}
	
	@RequestMapping(value = "/account/list")
	public String accountList(DjReqestParam param,ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		Map<String ,Object>searchMap = new HashMap<String,Object>();
		
		if ("btnPage".equalsIgnoreCase(param.EVENTTARGET))
		{
			if (null == param.EVENTARGUMENT || param.EVENTARGUMENT.isEmpty())
			{
				param.EVENTARGUMENT = "0";
			}

			param.page = Integer.parseInt(param.EVENTARGUMENT);
		}
		// 查找
		else if ("btnSearch".equalsIgnoreCase(param.EVENTTARGET))
		{
			param.page = 0;
		}
		
		if(StringUtils.isNotBlank(param.keywords))
			searchMap.put(DjGoods.sKeywords, param.keywords);
		
		map.addAttribute("doctor_page", doctorSvs.find(searchMap,param.page, param.size));
		return "/management/doctor/account_list";
	}
	
	@RequestMapping(value = "/account/save")
	public String accountSave(DjReqestParam param, ModelMap map,DjDoctor doctor)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		// 参数注回
		map.addAttribute("param", param);
		doctorSvs.save(doctor);
		
		
		map.addAttribute("doctor_page", doctorSvs.findAll(param.page, param.size));
		return "redirect:/management/doctor/account/list";
	}

	
	
}
