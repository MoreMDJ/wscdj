package com.zxsm.wsc.controller.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.WebUtils;
import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.common.tencent.common.Util;
import com.zxsm.wsc.common.tencent.controller.DjWeChatAuthorizeController;

@Controller
@RequestMapping(value = "/")
public class DjMainIndexController extends DjBaseController
{
	@RequestMapping()
	public String index(HttpServletRequest request ,String code, HttpSession session,HttpServletResponse httpServletResponse)
	{
		if(WebUtils.isWechatClient(request) && StringUtils.isBlank(code)&&session.getAttribute(DjKeys.K_WECHAT_OPENID) == null)
		{
			System.out.println("\n获取code");
			System.out.println(Util.getAuthorization(DjKeys.HOST + "/wx", null, "state"));
			System.out.println("\ncode end");
//			return Util.getAuthorization(DjKeys.HOST + "/wx", null, "123");
//			return "redirect:https://www.baidu.com";
//			httpServletResponse.setHeader("Location", Util.getAuthorization(DjKeys.HOST + "/wx", null, "123"));
//			return null;
		}
		else if(StringUtils.isNotBlank(code))
		{
			System.out.println("获取OpenId");
			String openId = DjWeChatAuthorizeController.getOpenIdByCode(code);
			if(StringUtils.isNotBlank(openId))
			{
				session.setAttribute(DjKeys.K_WECHAT_OPENID, openId);
			}
		}
		return "redirect:/wx";
		
	}
}
