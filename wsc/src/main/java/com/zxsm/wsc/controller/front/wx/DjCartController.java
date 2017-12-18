package com.zxsm.wsc.controller.front.wx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.entity.goods.DjGoodsParam;
import com.zxsm.wsc.entity.order.DjCartGoods;
import com.zxsm.wsc.entity.order.DjOParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.activity.DjActivityService;
import com.zxsm.wsc.service.order.DjCartGoodsService;
import com.zxsm.wsc.service.order.DjOrderService;

/**
 * 购物车管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value = "/wx/cart")
public class DjCartController extends DjBaseController
{

	@Autowired
	private DjCartGoodsService cGoodsSvs;
	
	@Autowired
	private DjOrderService orderSvs;
	
	@Autowired
	private DjActivityService activitySvs;
	
	@RequestMapping()
	public String cart(Integer ctype, DjGoodsParam param, ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + map.get("reqUrl");
		
		DjUser user = getUserInfo();
		
		List<DjCartGoods> cartGoodsList = cGoodsSvs.updateByUid(user.getId(),ctype);
		if(ctype != null && ctype == 1)
		{
			map.addAttribute("title","需求清单");
			map.addAttribute("payTitle", "提交预定");
		}
		else
		{
			map.addAttribute("title", "购物车");
			map.addAttribute("payTitle", "去结算");
		}
//		List<DjGoods> cartGoodsList = cGoodsSvs.findGoodsByUid(user.getId());
//		List<DjCartGoods> save = cGoodsSvs.save(cartGoodsList);
		map.addAttribute("goods", cartGoodsList);
		
		return "/wx/order/cart";
	}
	
	/**
	 * 添加到购物车
	 * 
	 * @param id
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Map<String, Object> cartAdd(Long id,Integer quantity, ModelMap map, HttpSession session)
	{
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("error", 1);
		if(!isLogin())
		{
			res.put("error", 2);
			res.put("msg", "请先登录");
			return res;
		}
		if (null == id ||quantity == null || quantity < 1)
		{
			res.put("msg", "参数不正确");
			return res;
		}
		// 查找这个商品
		DjCartGoods cartGoods = cGoodsSvs.findByGid(id);
		
		
		if (null == cartGoods)
		{
			res.put("msg", "该商品已下架");
			return res;
		}
		
		if (cartGoods.getLeftQuantity() < 1)
		{
			res.put("error", 3);
			res.put("msg", "库存不足");
			return res;
		}
		
		DjUser user = getUserInfo();
		
			
			
		DjCartGoods inCartGoods = cGoodsSvs.findByGidAndUid(id, user.getId());
		
		Integer limitQuantity = activitySvs.buyQuantityByUidAndGid(user.getId(), id);
		if(limitQuantity != null && limitQuantity - quantity - (inCartGoods != null ? inCartGoods.getQuantity() : 0) < 0)
		{
			res.put("msg", "商品超出限购数量");
			return res;
		}
		
		
		if(inCartGoods == null)
		{
			inCartGoods = cartGoods;
			inCartGoods.setUid(user.getId());
			
			
			
			res.put("number", 1);
		}
		else
		{
			int cQuantity = inCartGoods.getQuantity();
			// 库存不足
			if (cQuantity + quantity > cartGoods.getLeftQuantity() && cartGoods.getLeftQuantity() > 0)
			{
				inCartGoods.setQuantity(cartGoods.getLeftQuantity());
			}
			else if(cartGoods.getLeftQuantity().compareTo(cQuantity + quantity) >= 0)
			{
				inCartGoods.setQuantity(cQuantity + quantity);
			}
			inCartGoods.setLeftQuantity(cartGoods.getLeftQuantity());
		}
		cGoodsSvs.save(inCartGoods);
		res.put("msg", "成功加入购物车");
		res.put("cartGoodsId", inCartGoods.getId());
		res.put("error", 0);
		
		return res;
	}
	
	/*@RequestMapping(value="/submit", method=RequestMethod.POST)
	public String cart(BmBuyGoodsForm goodsForm, ModelMap map, HttpSession session) {
		
		String username = (String) session.getAttribute("username");
		
		if (null == username)
		{
			return "redirect:/mobile/login";
		}
		
		if (null == goodsForm)
		{
			map.addAttribute("error", "没有结算的商品");
			return "/mobile/error_404";
		}
		
		session.setAttribute("order_goods_list", orderSvs.findPreOrderGoodsList(goodsForm.getBuyGoods()));
		session.removeAttribute("select_address");
		
		return "redirect:/mobile/order/enter";
	}*/
	
	@RequestMapping(value="/del/{priceId}")
	public String cartDel(@PathVariable Long priceId, ModelMap map, HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<DjCartGoods> cartGoodsList = (List<DjCartGoods>) session.getAttribute("cartGoodsList");
		
		if (null != cartGoodsList && cartGoodsList.size()>0)
		{
			for (DjCartGoods g : cartGoodsList)
			{
				if (g.getGid().equals(priceId))
				{
					cartGoodsList.remove(g);
					break;
				}
			}
		}
		
		map.addAttribute("cart_goods_list", cartGoodsList);
		
		return "redirect:/mobile/cart";
	}
	
	@RequestMapping(value="/checklimit")
	@ResponseBody
	public Map<String, Object> checkLimit(DjOParam oParam,ModelMap map)
	{
		
		Map<String, Object> res = new HashMap<String, Object>();
		if(!isLogin())
		{
			res.put("status", 2);
			res.put("msg", "请先登录");
			return res;
		}
//		DjActivity activity = activitySvs.findOne(3L);
//		DjUserLimitGoods limitGoods = new DjUserLimitGoods();
//		limitGoods.setGid(904L);
//		limitGoods.setUid(1L);
//		limitGoods.setInitDate(new Date());
//		limitGoods.setModifyDate(new Date());
//		limitGoods.setNumber(2);
//		activitySvs.saveULG(limitGoods);
//		List<DjUserLimitGoods> buyUser = activity.getBuyUser();
//		if(buyUser == null)
//		{
//			buyUser = new ArrayList<DjUserLimitGoods>();
//			buyUser.add(limitGoods);
//		}
//		else
//			buyUser.add(limitGoods);
//		activity.setBuyUser(buyUser);
//		
//		activitySvs.save(activity);
		
		DjUser user = getUserInfo();
		String limitNumber = activitySvs.limitNumberByUid(user.getId(), oParam.chooseGids(), oParam.gidsAndQuantity());
		if(limitNumber != null)
		{
			res.put("status", 2);
			res.put("msg", "限购商品超出个数");
			return res;
		}
		
		res.put("status", 1);
		return res;
	}
}
