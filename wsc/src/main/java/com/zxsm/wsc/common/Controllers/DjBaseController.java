package com.zxsm.wsc.common.Controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.UtilsTools.CookiesUtil;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.user.DjUserService;

public class DjBaseController
{

	@Autowired
	HttpSession httpSession;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	DjUserService userSvs;
	
//	@Autowired
//	HttpServletResponse res
	
	protected static final String URL_RedirectLogin = "redirect:/wx/login?returnTo=";
	protected static final String URL_Login			= "/wx/login";
	
	@ModelAttribute
    public void populateModel( DjUParam param, ModelMap map,HttpServletRequest request)
	{
//		if(isLogin())
//			throw new NullPointerException("1111222");
		param.setReqUrl(request.getRequestURI());
		map.addAttribute("param", param);
    }
	
	@ModelAttribute("reqUrl")
    public String populateModel(HttpServletRequest request)
	{
		return request.getRequestURI();
    }/**/
	
	/**
	 * 检测是否登陆
	 * @return yes:登陆 no:没登录
	 * 
	 */
	public Boolean isLogin()
	{
		DjUser user = (DjUser)httpSession.getAttribute(DjKeys.SESSION_USER_USERNAME);
		
		if(user == null)
		{
			Cookie username = CookiesUtil.getCookieByName(req, "username");
			Cookie password = CookiesUtil.getCookieByName(req, "password");
			if(username == null || password == null)
				return false;
			user = userSvs.findByUsernameAndPassword((String)username.getValue(), (String)password.getValue());
			if(user == null)
				return false;
			httpSession.setAttribute(DjKeys.SESSION_USER_USERNAME, user);
		}
		return true;
	}
	
	/**
	 * 从session中获取manager
	 * @return
	 */
	public DjUser getUserInfo()
	{
		return (DjUser)httpSession.getAttribute(DjKeys.SESSION_USER_USERNAME);
	}
	
	public void userLogin(DjUser user ,HttpServletResponse res)
	{
		httpSession.setAttribute(DjKeys.SESSION_USER_USERNAME, user);
		CookiesUtil.setCookie(res, "username", user.getUsername(), 24*60*60*100);
		CookiesUtil.setCookie(res, "password", user.getPassword(), 24*60*60*100);
	}
}
