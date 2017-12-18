package com.zxsm.wsc.controller.management;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.order.DjExpressType;
import com.zxsm.wsc.service.order.DjExpressTypeService;

/**
 * 后台品牌管理控制器
 * 
 * @author Shawn
 */

@Controller
@RequestMapping(value="/management/express")
public class DjManagerExpressTypeController extends DjAdminLevel
{
    
    @Autowired
    DjExpressTypeService expressTypeSvs;
    
    @RequestMapping(value="/list")
    public String articleList(DjReqestParam param,ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnSaveExpressType(param.listId, param.sortId);
        }
        // 删除文章
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteExpressType(param.listId, param.listChkIdx);
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
        
        if (null == param.keywords || param.keywords.isEmpty())
    	{
    		map.addAttribute("express_type_page", expressTypeSvs.findExpressTypeAll(param.page, param.size));
    	}
    	else
    	{
    		map.addAttribute("express_type_page", expressTypeSvs.searchExpressTypeAll(param.keywords, param.page, param.size));
    	}
        
        return "/management/order/express_type_list";
    }
    
    @RequestMapping(value="/edit")
    public String brandEdit(Long id,
    						  String __EVENTTARGET,
	                          String __EVENTARGUMENT,
	                          String action,
	                          ModelMap map,
	                          HttpSession session){
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        if (null != id)
        {
        	map.addAttribute("express_type", expressTypeSvs.findExpressType(id));
        }
        
        map.addAttribute("action", action);
        
        return "/management/order/express_type_edit";
    }
    
    @ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id, Model model)
    {
		if (null != id)
		{
			model.addAttribute("bmExpressType", expressTypeSvs.findExpressType(id));
		}
	}
    
    @RequestMapping(value="/save")
    public String categorySave(DjExpressType bmExpressType,
					            String __VIEWSTATE,
					            ModelMap map,
					            HttpSession session){
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        expressTypeSvs.saveExpressType(bmExpressType);
        
        return "redirect:/management/express/list";
    }
    
    /**
     * 保存排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveExpressType(Long[] ids, Double[] sortIds)
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
        		DjExpressType cat = expressTypeSvs.findExpressType(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			expressTypeSvs.saveExpressType(cat);
        		}
        	}
        }
    }
    
    /**
     * 删除
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteExpressType(Long[] ids, Integer[] chkIdx)
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
            	expressTypeSvs.deleteExpressType(ids[idx]);
            }
        }
    }
}
