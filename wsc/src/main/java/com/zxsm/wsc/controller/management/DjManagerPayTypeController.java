package com.zxsm.wsc.controller.management;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxsm.wsc.common.DjEnums;
import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.order.DjPayType;
import com.zxsm.wsc.entity.order.DjShop;
import com.zxsm.wsc.service.order.DjPayTypeService;
import com.zxsm.wsc.service.order.DjShopService;

/**
 * 后台品牌管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management/pay")
public class DjManagerPayTypeController extends DjAdminLevel
{
    
    @Autowired
    DjPayTypeService bmPayTypeService;
    
    @Autowired
	DjShopService djShopService;
    
    private static final String kNavName = "channel_order_paytype_list";
    
    @RequestMapping(value="/list")
    public String articleList(DjReqestParam param,ModelMap map ,HttpSession session)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Edit))
        		btnSavePayType(param.listId, param.sortId);
        	else
        		return URL_NORight;
        }
        // 删除文章
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Delete))
        		btnDeletePayType(param.listId, param.listChkIdx);
        	else
        		return URL_NORight;
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
        	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.View))
        		param.page = 0;
        	else
        		return URL_NORight;
        }
        
        map.addAttribute("param", param);
        
        if (null == param.keywords || param.keywords.isEmpty())
    	{
    		map.addAttribute("pay_type_page", bmPayTypeService.findPayTypeAll(param.page, param.size));
    	}
    	else
    	{
    		map.addAttribute("pay_type_page", bmPayTypeService.searchPayTypeAll(param.keywords, param.page, param.size));
    	}
        if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.View))
        	return "/management/order/pay_type_list";
    	else
    		return URL_NORight;
    }
    
    @RequestMapping(value="/edit")
    public String brandEdit(Long id,DjReqestParam param,ModelMap map)
    {
        if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        if (null != id)
        {
        	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Edit))
        		map.addAttribute("pay_type", bmPayTypeService.findPayType(id));
        	else
        		return URL_NORight;
        }
        if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Add))
        	return "/management/order/pay_type_edit";
    	else
    		return URL_NORight;
    }
    
    @ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,Model model)
    {
		if (null != id)
		{
			model.addAttribute("bmPayType", bmPayTypeService.findPayType(id));
		}
	}
    
    @RequestMapping(value="/save")
    public String categorySave(DjPayType djPayType,ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
    	if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.Edit))
    		bmPayTypeService.savePayType(djPayType);
    	else
    		return URL_NORight;
    	
        
        return "redirect:/management/pay/list";
    }
    
    @RequestMapping(value = "/shop/list")
	public String articleList(DjReqestParam param, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		// 保存排序号
		if ("btnSave".equalsIgnoreCase(param.EVENTTARGET)) {
			btnSaveShop(param.listId, param.sortId);
		}
		// 删除店铺
		else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET)) {
			btnDeleteShop(param.listId, param.listChkIdx);
		}
		// 翻页
		else if ("btnPage".equalsIgnoreCase(param.EVENTTARGET)) {
			if (null == param.EVENTARGUMENT || param.EVENTARGUMENT.isEmpty()) {
				param.EVENTARGUMENT = "0";
			}

			param.page = Integer.parseInt(param.EVENTARGUMENT);
		}
		// 查找
		else if ("btnSearch".equalsIgnoreCase(param.EVENTTARGET)) {
			param.page = 0;
		}
		
		map.addAttribute("param", param);
		
		map.addAttribute("shop_page", djShopService.findShopAll(param.page,param.size));

		return "/management/order/shop_list";
	}
    
    @RequestMapping(value = "/shop/edit")
	public String shopEdit(Long id, DjReqestParam param, String action, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != id) {
			map.addAttribute("shop", djShopService.findShop(id));
		}

		map.addAttribute("action", action);

		return "/management/order/shop_edit";
	}

	@RequestMapping(value = "/shop/save")
	public String categorySave(DjShop djShop, Long[] serviceId,
			String __VIEWSTATE, ModelMap map, HttpSession session) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		// 保存支持服务类型
		if (null != serviceId) {
			String sidStr = "";
			for (Long sid : serviceId) {
				sidStr += "[";
				sidStr += sid;
				sidStr += "],";
			}
			djShop.setServiceIdStr(sidStr);
		}

		djShopService.saveShop(djShop);

		return "redirect:/management/pay/shop/list";
	}
    
    
    /**
     * 保存排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSavePayType(Long[] ids, Double[] sortIds)
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
        		DjPayType cat = bmPayTypeService.findPayType(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			bmPayTypeService.savePayType(cat);
        		}
        	}
        }
    }
    
    /**
     * 删除
     * @param ids
     * @param chkIdx
     */
    private void btnDeletePayType(Long[] ids, Integer[] chkIdx)
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
            	bmPayTypeService.deletePayType(ids[idx]);
            }
        }
    }
    
    /**
	 * 保存排序号
	 * 
	 * @param ids
	 * @param chkIdx
	 * @param sortIds
	 */
	private void btnSaveShop(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1
				|| sortIds.length < 1) {
			return;
		}

		for (int idx = 0; idx < ids.length; idx++) {
			if (sortIds.length > idx) {
				DjShop cat = djShopService.findShop(ids[idx]);

				if (null != cat) {
					cat.setSortId(sortIds[idx]);
					djShopService.saveShop(cat);
				}
			}
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteShop(Long[] ids, Integer[] chkIdx) {
		if (null == ids || null == chkIdx || ids.length < 1
				|| chkIdx.length < 1) {
			return;
		}

		for (int idx : chkIdx) {
			if (idx >= 0 && ids.length > idx) {
				djShopService.deleteShop(ids[idx]);
			}
		}
	}
}
