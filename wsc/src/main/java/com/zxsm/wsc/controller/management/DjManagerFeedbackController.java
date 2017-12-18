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
import com.zxsm.wsc.entity.user.DjUFeedback;
import com.zxsm.wsc.service.user.DjUFeedbackService;

/**
 * 后台申请管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management/feedback")
public class DjManagerFeedbackController extends DjAdminLevel {
    
    @Autowired
    DjUFeedbackService bmFeedbackService;
    
    @RequestMapping(value="/list")
    public String FeedbackList(DjReqestParam param, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnSaveFeedback(param.listId, param.sortId);
        }
        // 删除投诉
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteFeedback(param.listId, param.listChkIdx);
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
    		map.addAttribute("feedback_page", bmFeedbackService.findFeedbackAll(param.page, param.size));
    	}
    	else
    	{
    		map.addAttribute("feedback_page", bmFeedbackService.searchFeedbackAll(param.keywords, param.page, param.size));
    	}
    	
        return "/management/user/feedback_list";
    }
    
    @RequestMapping(value="/edit")
    public String categoryEdit(Long id,
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
        	map.addAttribute("feedback", bmFeedbackService.findFeedback(id));
        }
        
        map.addAttribute("action", action);
        
        return "/management/user/feedback_edit";
    }
    
    @ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			Model model) {
		if (null != id) {
			model.addAttribute("bmFeedback", bmFeedbackService.findFeedback(id));
		}
	}
    
    @RequestMapping(value="/save")
    public String categorySave(DjUFeedback bmFeedback,
					            String __VIEWSTATE,
					            ModelMap map,
					            HttpSession session){
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        bmFeedbackService.updateFeedback(bmFeedback);
        
        return "redirect:/management/feedback/list";
    }
    
    /**
     * 保存投诉排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveFeedback(Long[] ids, Double[] sortIds)
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
        		DjUFeedback cat = bmFeedbackService.findFeedback(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			bmFeedbackService.updateFeedback(cat);
        		}
        	}
        }
    }
    
    /**
     * 删除投诉
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteFeedback(Long[] ids, Integer[] chkIdx)
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
            	bmFeedbackService.deleteFeedback(ids[idx]);
            }
        }
    }
}
