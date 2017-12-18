package com.zxsm.wsc.controller.front.wx;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.common.UtilsTools.DjSMSUtils;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.user.DjUserService;

@Controller
@RequestMapping(value = "/wx/join")
public class DjRegistController extends DjBaseController
{
	@Autowired
	DjUserService userSvr;
	
	@RequestMapping()
	public String join(DjUParam param,HttpSession session,ModelMap map,HttpServletResponse res)
	{
		if(StringUtils.isNotBlank(param.getMobile()) &&
				StringUtils.isNotBlank(param.getPassword()) &&
				StringUtils.isNotBlank(param.getCode()))
		{
			String mobileAndCode = (String)session.getAttribute(DjKeys.SESSION_USER_SMSCODE);
			if(mobileAndCode != null)
			{
				String[] strings = mobileAndCode.split("\\|");
				if(param.mobile.equalsIgnoreCase(strings[0]) &&param.code.equals(strings[1]))
				{
					DjUser user = userSvr.findByUsername(param.mobile);
					if(user != null)
					{
						map.addAttribute("msg", "该手机号已经注册");
						return "/wx/join";
					}
				 	user = new DjUser();
					user.setPassword(param.password);
					user.setUsername(param.mobile);
					DjUser newUser = userSvr.createUser(user);
					if(param.msgId != null && param.msgId == 12)
					{
						return "redirect:/wx/u/distribution";
					}
					session.setAttribute(DjKeys.SESSION_USER_USERNAME, newUser);
					userLogin(newUser, res);
					return "redirect:/wx/u";
					
				}
				map.addAttribute("msg", "验证码错误");
			}
			map.addAttribute("msg", "验证码错误");
		}
		
		return "/wx/join";
	}
	
	@RequestMapping(value = "/smsCode" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> smsCode(String mobile,HttpSession session)
	{
		if(StringUtils.isBlank(mobile))
			return DjTools.mapN("手机号不能为空");
		
		
		DjUser user = userSvr.findByUsername(mobile);
		if(user != null)
			return DjTools.mapN("该手机已注册，无需重复注册！");
		
		String verifyCode = DjSMSUtils.getVerifyCode();
		
		Map<String, String> resultMap = DjSMSUtils.sendSMS(DjSMSUtils.getContent(verifyCode), mobile);
		if(resultMap.get("status") == "n")
			return DjTools.mapN("发送失败，稍后再试！");
		
		System.out.println("SMS_CODE:" + verifyCode + "\nSMS_MSG:" + resultMap.get("info"));
		
		session.setAttribute(DjKeys.SESSION_USER_SMSCODE, mobile+"|"+ verifyCode);
		
		return DjTools.mapY("验证码已发送，请等待！");
	}
}
