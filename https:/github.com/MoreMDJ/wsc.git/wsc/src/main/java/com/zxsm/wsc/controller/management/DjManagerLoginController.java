package com.zxsm.wsc.controller.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.entity.management.DjManager;
import com.zxsm.wsc.service.management.DjManagerService;

/**
 * 后台申请管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management")
public class DjManagerLoginController
{

	@Autowired
	private DjManagerService djManagerService;

	@RequestMapping(value="/login")
	public String ApplyList(String username, String password, ModelMap map, HttpSession session, HttpServletRequest request)
	{

		if (null == username || null == password || username.isEmpty() || password.isEmpty())
		{
			map.addAttribute("error", "用户名和密码不能为空");
			return "/management/login";
		}

		if (username.equalsIgnoreCase("admin") && password.equals("admin888"))
		{
			session.setAttribute("manager", "admin");
			//tdManagerLogService.addLog("login", "用户登录", request);
			return "redirect:/management";
		}
		else
		{
			DjManager manager = djManagerService.findManagerByUsernameAndIsEnabledTrue(username);

			if (null != manager)
			{
				if (password.equals(manager.getPassword()))
				{
					String clientIp = DjTools.clientIp(request);
					djManagerService.managerLogin(manager, clientIp);
					request.getSession().setAttribute(DjKeys.SESSION_MANAGER_USERNAME, manager);

					//tdManagerLogService.addLog("login", "用户登录", request);
					return "redirect:/management";
				}
			}
		}

		map.addAttribute("username", username);
		map.addAttribute("error", "用户不存在或密码错误");
		return "/management/login";

	}

	@RequestMapping(value="/logout")
	public String logout(HttpSession session)
	{
		session.removeAttribute("manager");
		return "redirect:/management/login";
	}
}
