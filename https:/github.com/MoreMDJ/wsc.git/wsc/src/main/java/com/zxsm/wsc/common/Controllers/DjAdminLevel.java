package com.zxsm.wsc.common.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.DjEnums;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.management.DjManager;
import com.zxsm.wsc.service.management.DjManagerService;


/**
 * 管理员权限
 * @author maeing
 *
 */


public class DjAdminLevel
{
	
	@Autowired
	DjManagerService djManagerService;
	
	@Autowired
	HttpSession httpSession;
	
	protected static final String URL_RedirectLogin = "redirect:/management/login";
	protected static final String URL_Login			= "/management/login";
	protected static final String URL_NORight		= "/management/noright";
	
	
	@ModelAttribute  
    public void populateModel( DjReqestParam param, ModelMap map)
	{  
       map.addAttribute("param", param);  
    }
	
	/**
	 * 查看权限
	 * @param navName
	 * @param actionType
	 * @return
	 */
	public Boolean CheckAdminLevel(String navName,DjEnums.ActionEnum actionType)
	{
		if(actionType == null)
			return false;
		
		DjManager manager = getManagerInfo();
		if(manager == null)
			return false;
		
		return djManagerService.Exists(manager.getRoleId(), navName, actionType);
	}
	
	/**
	 * 检测是否登陆
	 * @return yes:登陆 no:没登录
	 * 
	 */
	public Boolean isManagerLogin()
	{
		DjManager manager = (DjManager)httpSession.getAttribute(DjKeys.SESSION_MANAGER_USERNAME);
		
		if(manager == null)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 从session中获取manager
	 * @return 
	 */
	public DjManager getManagerInfo()
	{
		return (DjManager)httpSession.getAttribute(DjKeys.SESSION_MANAGER_USERNAME);
	}
	
}
