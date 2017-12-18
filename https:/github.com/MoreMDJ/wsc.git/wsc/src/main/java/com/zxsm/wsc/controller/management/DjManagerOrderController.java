package com.zxsm.wsc.controller.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.common.SerchTools.Criterion.Operator;
import com.zxsm.wsc.common.SerchTools.Search;
import com.zxsm.wsc.entity.common.DjOUpdateParam;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.order.DjOrder;
import com.zxsm.wsc.entity.user.DjUCash;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.management.DjSystemConfigService;
import com.zxsm.wsc.service.order.DjOrderService;
import com.zxsm.wsc.service.user.DjUCashService;
import com.zxsm.wsc.service.user.DjUserService;

/**
 * 后台订单管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management/order")
public class DjManagerOrderController extends DjAdminLevel
{
    
    @Autowired
    DjOrderService djOrderService;
    
    @Autowired
    DjSystemConfigService configSvs;
    
    @Autowired
    DjUCashService cashSvs;
    
    @Autowired
    DjUserService userSvs;
    
    @RequestMapping(value="/list")
    public String ordersList(DjReqestParam param, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
//		Map<String ,Object>searchMap = new HashMap<String,Object>();
		List<Search> searchs = new ArrayList<>();
        
        // 默认商品订单
        if (null == param.categoryId)
        {
        	param.categoryId = 1L;
        }
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnSaveOrder(param.listId, param.sortId);
        }
        // 删除订单
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteOrder(param.listId, param.listChkIdx);
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
        else if ("btnSearch".equalsIgnoreCase(param.EVENTTARGET) && StringUtils.isNotBlank(param.keywords))
        {
        	param.page = 0;
        	searchs.add(new Search(Operator.OR, DjOrder.sAddress, param.keywords,Operator.LIKE));
        	searchs.add(new Search(Operator.OR,DjOrder.sOrderNo,param.keywords,Operator.LIKE));
        	searchs.add(new Search(Operator.OR,DjOrder.sUsername,param.keywords,Operator.LIKE));
        	searchs.add(new Search(Operator.OR,DjOrder.sShopTitle,param.keywords,Operator.LIKE));
        }
        
        map.addAttribute("param", param);
        
        // 全部状态订单
        if (null != param.statusId)
        {
        	switch (param.statusId) {
			case 1://待确认
			{
				searchs.add(new Search(Operator.AND, DjOrder.sStatus, 1,Operator.EQ));
				break;
			}
			case 2://待付款
			{
				searchs.add(new Search(Operator.AND, DjOrder.sIsOnlinePay, true, Operator.EQ));
				searchs.add(new Search(Operator.AND, DjOrder.sPaymentStatus, 1, Operator.EQ));
				break;
			}
			case 3://待发货
			{
				searchs.add(new Search(Operator.AND, DjOrder.sStatus, 2,Operator.EQ));
				searchs.add(new Search(Operator.AND, DjOrder.sExpressStatus, 1, Operator.EQ));
				break;
			}
			case 4://待收货
			{
				searchs.add(new Search(Operator.AND, DjOrder.sStatus, 2,Operator.EQ));
				searchs.add(new Search(Operator.AND, DjOrder.sExpressStatus, 2, Operator.EQ));
				break;
			}
			case 5://待评价
			{
				searchs.add(new Search(Operator.AND, DjOrder.sStatus, 3,Operator.EQ));
				break;
			}
			case 6://完成
			{
				searchs.add(new Search(Operator.AND, DjOrder.sStatus, 3,Operator.EQ));
				break;
			}
			case 7://取消
			{
				searchs.add(new Search(Operator.AND, DjOrder.sStatus, 4,Operator.EQ));
				break;
			}

			default:
				break;
			}
        }
        else
        {
        	
        }
        searchs.add(new Search(Operator.AND,DjOrder.sTypeId,param.typeId,Operator.EQ));
        map.addAttribute("order_page", djOrderService.searchOrder(searchs, param.page, param.size));
        return "/management/order/order_list";
    }
    
    
    /**
     * 分销设置
     * @param param
     * @param map
     * @return
     */
    @RequestMapping(value = "/distribution" ,method = RequestMethod.GET)
    public String orderDistribution(DjReqestParam param,ModelMap map)
    {
    	map.addAttribute("level_one", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE).getConfigValue());
    	map.addAttribute("level_two", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELTWO).getConfigValue());
    	map.addAttribute("level_three", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELTHREE).getConfigValue());
    	if(configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE_ONE) !=null)
    		map.addAttribute("level_one_one", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE_ONE).getConfigValue());
    	return "/management/order/distribution";
    }
    
    @RequestMapping(value = "/distribution",method = RequestMethod.POST)
    public String orderDistributionPost(DjReqestParam param,ModelMap map,Double levelone,Double leveltwo,Double levelthree,Double leveloneOne)
    {
    	configSvs.savaDistribution(DjKeys.K_DISTRIBUTION_LEVELONE, levelone.toString());
    	configSvs.savaDistribution(DjKeys.K_DISTRIBUTION_LEVELTWO, leveltwo.toString());
    	configSvs.savaDistribution(DjKeys.K_DISTRIBUTION_LEVELTHREE, levelthree.toString());
    	configSvs.savaDistribution(DjKeys.K_DISTRIBUTION_LEVELONE_ONE, leveloneOne.toString());
    	
    	
    	map.addAttribute("level_one", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE).getConfigValue());
    	map.addAttribute("level_two", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELTWO).getConfigValue());
    	map.addAttribute("level_three", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELTHREE).getConfigValue());
    	map.addAttribute("level_one_one", configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE_ONE).getConfigValue());
    	return "/management/order/distribution";
    }
    
    @RequestMapping(value="/edit")
    public String orderEdit(Long id,DjReqestParam param, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        if (null != id)
        {
        	DjOrder order = djOrderService.findOrder(id);
        	if(order != null)
        	{
        		map.addAttribute("order", order);
        		Long userId = order.getUserId();
        		DjUCash cash = cashSvs.findByChildUidAndOrderNumber(userId, order.getOrderNo());
        		if(cash != null)
        		{
        			map.addAttribute("cash", cash);
        			DjUser user = userSvs.findOne(cash.getUid());
        			if(user != null)
        			{
        				map.addAttribute("distr_name",user.getRealName());
        				if(user.getuRole() != null && user.getuRole() == 2)
        				{
        					DjUser superior = userSvs.superiorByUid(user.getId());
        					if(superior != null)
        					{
        						map.addAttribute("superior", superior.getRealName());
        					}
        				}
        			}
        		}
        		
        	}
        	
        }
        
        return "/management/order/order_edit";
    }
    @RequestMapping(value="/edit/uInfo")
    public String uInfoEdit(ModelMap map,DjReqestParam param)
    {
    	if(!isManagerLogin())
    		return URL_RedirectLogin;
    	
    	map.addAttribute("orderNo",param.orderNo);
    	
    	return "/management/order/dialog_uInfo_edit";
    }
    @RequestMapping(value="/edit/express")
    public String orderExpressEdit(ModelMap map,DjReqestParam param)
    {
    	if(!isManagerLogin())
    		return URL_RedirectLogin;
    	
    	map.addAttribute("orderNo",param.orderNo);
    	
    	return "/management/order/dialog_order_express";
    }
    
    @RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> goodsList(String action, String orderNo,DjOUpdateParam updateParam, ModelMap map)
    {
		Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("status", 0);
        
        if (!isManagerLogin())
		{
        	res.put("msg", "请重新登录");
            return res;
		}

		if ("editStatusId".equalsIgnoreCase(action))
		{
			/*if ("cancel".equalsIgnoreCase(editType))
			{
				DjOrder order = djOrderService.findOrderByOrderNumber(orderNumber);
				djOrderService.cancelOrder(order);
			}
			else
			{
				DjOrder order = djOrderService.findOrderByOrderNumber(orderNumber);
				djOrderService.jumpToNextStatus(order, expressId, expressNumber);
			}*/
			res.put("status", 1);
	        return res;
		}
		else if("editUInfo".equalsIgnoreCase(action))
		{
			if(djOrderService.updateOrderUInfo(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}
		else if("orderRemark".equalsIgnoreCase(action))
		{
			if(djOrderService.updateOrderRemark(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}
		else if("orderExpressFee".equalsIgnoreCase(action))
		{
			if(djOrderService.updateOrderExpressFee(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}
		else if("orderCancel".equalsIgnoreCase(action))
		{
			if(djOrderService.updateOrderCancel(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}
		else if("orderConfirm".equalsIgnoreCase(action))//确认订单
		{
			if(djOrderService.updateOrderConfirm(updateParam))
			{
				res.put("status", 1);
				res.put("msg", "确认成功");
				return res;
			}
		}
		else if("orderPayment".equalsIgnoreCase(action))//确认付款
		{
			updateParam.setRemark("manager");
			if(djOrderService.updateOrderPayment(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}
		else if("orderExpress".equalsIgnoreCase(action))
		{
			if(djOrderService.updateOrderExpress(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}
		else if("orderReceive".equalsIgnoreCase(action)) //确认收货
		{
			if(djOrderService.updateOrderReceive(updateParam))
			{
				res.put("status", 1);
				return res;
			}
		}

        res.put("msg", "无法操作!");
        return res;
	}
    
    /**
     * 保存订单排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveOrder(Long[] ids, Double[] sortIds)
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
        		DjOrder e = djOrderService.findOrder(ids[idx]);
        		
        		if (null != e)
        		{
        			e.setSortId(sortIds[idx]);
        			djOrderService.saveOrder(e);
        		}
        	}
        }
    }
    
    /**
     * 删除订单
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteOrder(Long[] ids, Integer[] chkIdx)
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
            	DjOrder order = djOrderService.findOrder(ids[idx]);
            	
            	// 只能删除已取消订单
            	if (7 == order.getStatus())
            	{
            		order.setStatus(0);
            		djOrderService.saveOrder(order);
            	}
            }
        }
    }
}
