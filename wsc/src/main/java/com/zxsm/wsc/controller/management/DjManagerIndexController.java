package com.zxsm.wsc.controller.management;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.management.DjManager;

/**
 * 后台首页控制器
 * 
 * @author maeing
 */ 
@Controller
@RequestMapping(value = "/management")
public class DjManagerIndexController extends DjAdminLevel
{
	@RequestMapping
	public String index(HttpSession session, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		DjManager manager = getManagerInfo();
		map.addAttribute("Manager", manager);
		return "/management/index";
	}

	@RequestMapping(value = "/center")
	public String role(HttpSession session, ModelMap map,HttpServletRequest req)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		Properties props = System.getProperties();

        map.addAttribute("os_name",props.getProperty("os.name") + " " + props.getProperty("os.version"));
        map.addAttribute("java_home", props.getProperty("java.home"));
        map.addAttribute("java_version", props.getProperty("java.version"));
        map.addAttribute("remote_ip", req.getRemoteAddr());
        map.addAttribute("server_name", req.getServerName());
        map.addAttribute("server_ip", req.getLocalAddr());
        map.addAttribute("server_port", req.getServerPort());
		
		return "/management/center";
	}
	
}
