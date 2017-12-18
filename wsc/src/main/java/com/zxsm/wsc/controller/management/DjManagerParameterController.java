package com.zxsm.wsc.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.goods.DjParameter;
import com.zxsm.wsc.entity.goods.DjParameterCategory;
import com.zxsm.wsc.service.goods.DjParameterService;

/**
 * 后台参数控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management/parameter")
public class DjManagerParameterController extends DjAdminLevel
{
    
    @Autowired
    DjParameterService bmParameterService;
    
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
        	btnSaveParameter(param.listId, param.sortId);
        }
        // 删除文章
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteParameter(param.listId, param.listChkIdx);
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
        
        map.addAttribute("param", param);
        map.addAttribute("category_list", bmParameterService.findCategoryAll());
        
        if (null == param.categoryId)
        {
        	if (null == param.keywords || param.keywords.isEmpty())
        	{
        		map.addAttribute("parameter_page", bmParameterService.findParameterAll(param.page, param.size));
        	}
        	else
        	{
        		map.addAttribute("parameter_page", bmParameterService.searchParameterAll(param.keywords, param.page, param.size));
        	}
        }
        else
        {
        	if (null == param.keywords || param.keywords.isEmpty())
        	{
        		map.addAttribute("parameter_page", bmParameterService.findParameterByCategoryId(param.categoryId, param.page, param.size));
        	}
        	else
        	{
        		map.addAttribute("parameter_page", bmParameterService.searchParameterByCategoryId(param.categoryId, param.keywords, param.page, param.size));
        	}
        }
        return "/management/goods/parameter_list";
    }
    
    @RequestMapping(value="/edit")
    public String edit(Long id,DjReqestParam param,String action, ModelMap map)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        if (null != id)
        {
        	map.addAttribute("parameter", bmParameterService.findParameter(id));
        }
        
        map.addAttribute("action", action);
        map.addAttribute("category_list", bmParameterService.findCategoryAll());
        
        return "/management/goods/parameter_edit";
    }
    
    @ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id, Model model)
    {
		if (null != id) {
			model.addAttribute("bmParam", bmParameterService.findParameter(id));
		}
	}
    
    @RequestMapping(value="/save")
    public String categorySave(DjParameter bmParam, String __VIEWSTATE,ModelMap map)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        bmParameterService.saveParameter(bmParam);
        
        return "redirect:/management/parameter/list";
    }
    
    @RequestMapping(value="/category/list")
    public String categoryList(DjReqestParam param, ModelMap map)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnSaveCategory(param.listId, param.sortId);
        }
        // 删除分类
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteCategory(param.listId, param.listChkIdx);
        }
        
        map.addAttribute("category_list", bmParameterService.findCategoryAll());
        
        return "/management/goods/parameter_category_list";
    }
    
    @RequestMapping(value="/category/edit")
    public String categoryEdit(Long id, Long parentId, String __EVENTTARGET, String __EVENTARGUMENT,ModelMap map)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        if (null != id)
        {
        	map.addAttribute("category", bmParameterService.findCategory(id));
        }
        
        if (null != parentId)
        {
        	map.addAttribute("parentCat", bmParameterService.findCategory(parentId));
        }
        
        map.addAttribute("category_list", bmParameterService.findCategoryAll());
        
        return "/management/goods/parameter_category_edit";
    }
    
    @RequestMapping(value="/category/save", method=RequestMethod.POST)
    public String categoryEdit(DjParameterCategory paramCat, String __VIEWSTATE, ModelMap map)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        bmParameterService.saveCategory(paramCat);
        
        return "redirect:/management/parameter/category/list";
    }
    
    /**
     * 保存分类排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveCategory(Long[] ids, Double[] sortIds)
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
        		DjParameterCategory cat = bmParameterService.findCategory(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			bmParameterService.saveCategory(cat);
        		}
        	}
        }
    }
    
    /**
     * 保存参数排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveParameter(Long[] ids, Double[] sortIds)
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
        		DjParameter cat = bmParameterService.findParameter(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			bmParameterService.saveParameter(cat);
        		}
        	}
        }
    }
    
    /**
     * 删除分类及下面的所有子分类
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteCategory(Long[] ids, Integer[] chkIdx)
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
            	bmParameterService.deleteCategory(ids[idx]);
            }
        }
    }
    
    /**
     * 删除参数
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteParameter(Long[] ids, Integer[] chkIdx)
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
            	bmParameterService.deleteParameter(ids[idx]);
            }
        }
    }
}
