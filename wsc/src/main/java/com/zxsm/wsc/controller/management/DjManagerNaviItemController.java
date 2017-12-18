package com.zxsm.wsc.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.management.DjNaviItem;
import com.zxsm.wsc.service.management.DjNaviItemService;

/**
 * 导航栏管理控制器
 * 
 * @author Shawn
 */

@Controller
@RequestMapping(value="/management/navitem")
public class DjManagerNaviItemController extends DjAdminLevel
{
    
    @Autowired
    DjNaviItemService bmNavigationItemService;
    
    @RequestMapping(value="/list")
    public String articleList(DjReqestParam param, ModelMap map)
    {
    	 if (!isManagerLogin())
         {
         	return URL_RedirectLogin;
         }
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnSaveNavitem(param.listId, param.sortId);
        }
        // 删除文章
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteNavitem(param.listId, param.listChkIdx);
        }
        // 翻页
        else if ("btnPage".equalsIgnoreCase(param.EVENTTARGET))
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
        
        map.addAttribute("categoryId", param.categoryId);
        
        if (null == param.categoryId)
        {
        	if (null == param.keywords || param.keywords.isEmpty())
        	{
        		map.addAttribute("navitem_page", bmNavigationItemService.findItemAll(param.page, param.size));
        	}
        	else
        	{
        		map.addAttribute("navitem_page", bmNavigationItemService.searchItemAll(param.keywords, param.page, param.size));
        	}
        }
        else
        {
        	if (null == param.keywords || param.keywords.isEmpty())
        	{
        		map.addAttribute("navitem_page", bmNavigationItemService.findItemByCategoryId(param.categoryId, param.page, param.size));
        	}
        	else
        	{
        		map.addAttribute("navitem_page", bmNavigationItemService.searchItemByCategoryId(param.categoryId, param.keywords, param.page, param.size));
        	}
        }
        return "/management/manager/navitem_list";
    }
    
    @RequestMapping(value="/edit")
    public String brandEdit(Long id, String action,  ModelMap map)
    {
    	if (!isManagerLogin())
    	{
    		return URL_RedirectLogin;
    	}

    	if (null != id)
    	{
    		map.addAttribute("navitem", bmNavigationItemService.findItem(id));
    	}

    	map.addAttribute("action", action);

    	return "/management/manager/navitem_edit";
    }
    
    @ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,Model model) {
		if (null != id)
		{
			model.addAttribute("bmItem", bmNavigationItemService.findItem(id));
		}
	}
    
    @RequestMapping(value="/save")
    public String categorySave(DjNaviItem bmItem,String VIEWSTATE, ModelMap map)
    {
    	 if (!isManagerLogin())
         {
         	return URL_RedirectLogin;
         }
        
        bmNavigationItemService.saveItem(bmItem);
        
        return "redirect:/management/navitem/list";
    }
    
    /**
     * 保存导航栏排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveNavitem(Long[] ids, Double[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int idx=0; idx < ids.length; idx++)
        {
        	if (sortIds.length > idx)
        	{
        		DjNaviItem cat = bmNavigationItemService.findItem(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			bmNavigationItemService.saveItem(cat);
        		}
        	}
        }
    }
    
    /**
     * 删除导航栏
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteNavitem(Long[] ids, Integer[] chkIdx)
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
            	bmNavigationItemService.deleteItem(ids[idx]);
            }
        }
    }
}
