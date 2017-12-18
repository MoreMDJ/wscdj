package com.zxsm.wsc.controller.management;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.DjEnums;
import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.entity.management.DjNavigationMenu;
import com.zxsm.wsc.service.management.DjNavigationMenuService;

/**
 * 后台导航界面管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management/navigation")
public class DjManagerNavigationController extends DjAdminLevel
{
    
    @Autowired
    DjNavigationMenuService djNavigationMenuService;
    
    private String kNavName = "sys_navigation";
    
    @RequestMapping(value="/list")
    public String list(
    		Integer size,
    		Integer page,
    		String __EVENTTARGET,
    		String __EVENTARGUMENT,
    		String __VIEWSTATE,
    		Integer[] listChkIdx,
    		Long[] listId,
    		Double[] sortId,
    		ModelMap map,
    		HttpServletResponse response)
    {
        if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        // 保存
        if ("btnSave".equalsIgnoreCase(__EVENTTARGET)) 
        {
        	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Edit))
        		btnSaveMenu(listId, sortId);
        	else
        		return URL_NORight;
        }
        //删除
        else if ("btnDelete".equalsIgnoreCase(__EVENTTARGET))
        {
        	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Delete))
        		btnDeleteMenu(listId, listChkIdx);
        	else
        		return URL_NORight;
        }

        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("menu_list", djNavigationMenuService.findAllMenu());
        
        if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.View))
        	return "/management/manager/navigation_list";
    	else
    		return URL_NORight;
    }
    
    @RequestMapping(value="/edit")
    public String brandEdit(Long id,
    		Long parentId,
    		String __EVENTTARGET,
    		String __EVENTARGUMENT,
    		ModelMap map)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        if (null != id)
        {
        	map.addAttribute("navigation", djNavigationMenuService.findMenu(id));
        }
        
        if (null != parentId)
        {
        	map.addAttribute("parent", djNavigationMenuService.findMenu(parentId));
        }
        
        map.addAttribute("menu_list", djNavigationMenuService.findAllMenu());
        map.addAttribute("actionType",DjTools.AuthorDictionary());
        return "/management/manager/navigation_edit";
    }
    
    @RequestMapping(value="/save")
    public String save(DjNavigationMenu menu,
					            String __VIEWSTATE,
					            ModelMap map,
					            HttpSession session)
    {
        if (!isManagerLogin())
            return URL_RedirectLogin;
        
        if(menu.getId() == null)
        {
	        if(!CheckAdminLevel(kNavName, DjEnums.ActionEnum.Add))
	    		return URL_NORight;
        }
        else
        {
        	if(!CheckAdminLevel(kNavName, DjEnums.ActionEnum.Edit))
	    		return URL_NORight;
        }
        djNavigationMenuService.saveMenu(menu);
        
        return "redirect:/management/navigation/list";
    }
    
    private void btnSaveMenu(Long[] ids, Double[] sortIds)
    {
        if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int idx=0; idx < ids.length; idx++)
        {
        	if (sortIds.length > idx)
        	{
        		DjNavigationMenu cat = djNavigationMenuService.findMenu(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			djNavigationMenuService.saveMenu(cat);
        		}
        	}
        }
    }
    /**
     * 删除菜单
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteMenu(Long[] ids, Integer[] chkIdx)
    {
        if (null == ids || null == chkIdx
                || ids.length < 1 || chkIdx.length < 1)
        {
            return;
        }
        
        for (int idx : chkIdx)
        {
            if (idx >=0 && ids.length > idx)
            {
            	djNavigationMenuService.deleteMenu(ids[idx]);
            }
        }
    }
    
}
