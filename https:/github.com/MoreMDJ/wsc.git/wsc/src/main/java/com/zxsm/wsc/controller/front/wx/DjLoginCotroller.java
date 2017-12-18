package com.zxsm.wsc.controller.front.wx;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.common.UtilsTools.CookiesUtil;
import com.zxsm.wsc.common.UtilsTools.DjSMSUtils;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.common.tencent.common.RandomStringGenerator;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.user.DjUserService;

@Controller
@RequestMapping(value = "/wx/login")
public class DjLoginCotroller extends DjBaseController
{
	@Autowired
	DjUserService userSvs;
	
	@RequestMapping()
	public String login(DjUParam param,ModelMap map, HttpSession session,HttpServletResponse res)
	{
		System.out.println("\n用户openId:\n" + (String)session.getAttribute(DjKeys.K_WECHAT_OPENID));
		if(StringUtils.isNotBlank(param.getMobile()) && StringUtils.isNotBlank(param.getPassword()))
		{
			DjUser user = userSvs.findEnableUser(param.getMobile());
//			user.setUsername("o6DzCjjkdQ73dec1ZZBu8b3G_M1A");
			if(user != null && user.verityPassword(param.password))
			{
				user.setLastLoginTime(new Date());
				userSvs.save(user);
				userLogin(user, res);
				if(StringUtils.isNotBlank(param.returnTo) && !param.returnTo.equalsIgnoreCase("/wx/login"))
					return "redirect:" + param.returnTo;
				if(param.getMobile().contains("MD"))
					return "redirect:/wx/u/distribution";
				else
					return "redirect:/";
			}
			map.addAttribute("msg", "账户或密码无效");
		}
		
		return "/wx/login";
	}
	
	@RequestMapping(value = "/passwordReset")
	public String passwordResetCheck(DjUParam param)
	{
		return "/wx/psw_reset_check";
	}
	
	@RequestMapping(value = "/code")
	@ResponseBody
	public Map<String,String> pswCode(String mobile,HttpSession session)
	{
		DjUser user = userSvs.findByUsername(mobile);
		if(user == null)
			return DjTools.mapN("用户不存在");
		String verifyCode = DjSMSUtils.getVerifyCode();
		
		Map<String, String> resultMap = DjSMSUtils.sendSMS(DjSMSUtils.getContent(verifyCode), mobile);
		if(resultMap.get("status") == "n")
			return DjTools.mapN("发送失败，稍后再试！");
		
		System.out.println("SMS_CODE:" + verifyCode + "\nSMS_MSG:" + resultMap.get("info"));
		
		session.setAttribute(DjKeys.SESSION_USER_SMSCODE, mobile+"|"+ verifyCode);
		
		return DjTools.mapY("验证码已发送，请等待！");
	}
	
	
	
	@RequestMapping(value = "/codeCheck")
	@ResponseBody
	public Map<String,String> codeCheck(DjUParam param,HttpSession session)
	{
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
			String randomString = RandomStringGenerator.getRandomStringByLength(10);
			session.setAttribute(DjKeys.SESSION_USER_RESETPSW, randomString);
			return DjTools.mapY(randomString);
		}
		return  DjTools.mapN("验证码错误");
	}

	@RequestMapping(value = "/passwordNew")
	public String passwordNewCheck(DjUParam param,ModelMap map)
	{
		map.addAttribute("code",param.code);
		return "/wx/psw_reset_new";
	}
	
	@RequestMapping(value = "/passwordChange")
	public String passwordChange(DjUParam param,HttpSession session,ModelMap map)
	{
		String resetCode = (String)session.getAttribute(DjKeys.SESSION_USER_RESETPSW);
		if(StringUtils.isBlank(resetCode) || !resetCode.equals(param.code))
		{
			map.addAttribute("errorMsg","无效修改");
			return "/wx/psw_reset_new";
		}
		String mobileAndCode = (String)session.getAttribute(DjKeys.SESSION_USER_SMSCODE);
		if(StringUtils.isBlank(mobileAndCode))
		{
			map.addAttribute("errorMsg","无效修改");
			return "/wx/psw_reset_new";
		}
		String[] strings = mobileAndCode.split("\\|");
		if(strings.length != 2)
		{
			map.addAttribute("errorMsg","无效修改");
			return "/wx/psw_reset_new";
		}
		DjUser user = userSvs.findByUsername(strings[0]);
		if(user != null)
		{
			user.setPassword(param.password);
			user.encryptionPassword();
			userSvs.save(user);
			session.removeAttribute(DjKeys.SESSION_USER_SMSCODE);
		}
		
		return DjKeys.URL_RedirectLogin;
	}
	@RequestMapping(value = "/out")
	public String loginOut(HttpSession session,HttpServletResponse response,HttpServletRequest request)
	{
		session.invalidate();
		CookiesUtil.removeCookie(response, request, "username");
		CookiesUtil.removeCookie(response, request, "password");
		return "/wx/login";
	}
				
}
