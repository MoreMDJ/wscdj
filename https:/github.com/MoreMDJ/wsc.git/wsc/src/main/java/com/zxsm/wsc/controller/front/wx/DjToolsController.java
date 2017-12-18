package com.zxsm.wsc.controller.front.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.entity.common.DjBaseRequestParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.goods.DjGoodsCategory;
import com.zxsm.wsc.service.goods.DjGoodsService;

@Controller
@RequestMapping(value = "/wx/tools")
public class DjToolsController extends DjBaseController
{
	@Autowired
	private DjGoodsService goodsSvs;
	
	@RequestMapping(value = "/more")
	public String goodsMore(DjBaseRequestParam param,ModelMap map)
	{
		switch (param.type)
		{
		case "0":
		{
			// 首页好货推荐
			Page<DjGoods> goodGoods = goodsSvs.findSaleTypeGoods(2,param.page,6);
			map.addAttribute("goodGoods",goodGoods);
			return "/wx/index_goods";
			
		}
		case "1":
		{
			// 燕窝馆好货推荐
			DjGoodsCategory goodsCategory = goodsSvs.findCategoryByTitle("燕窝馆");
			if(goodsCategory != null){
			Page<DjGoods> goodGoods = goodsSvs.findSaleTypeAndCateTree(2, goodsCategory.getId(), param.page, 6);
			map.addAttribute("goodGoods",goodGoods);
			}
			return "/wx/index_goods";
		}
		default:
			return "";
		}
	}

}
