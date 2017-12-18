package com.zxsm.wsc.controller.front.wx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.goods.DjGoodsCategory;
import com.zxsm.wsc.entity.goods.DjGoodsParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.activity.DjActivityService;
import com.zxsm.wsc.service.goods.DjGoodsService;
import com.zxsm.wsc.service.user.DjUCollectService;
import com.zxsm.wsc.service.user.DjUCommentService;
import com.zxsm.wsc.service.user.DjUScanService;
import com.zxsm.wsc.service.user.DjUserService;

@Controller
@RequestMapping(value = "/wx/goods")
public class DjGoodsController extends DjBaseController
{
	@Autowired
	private DjGoodsService goodsSvs;
	
	@Autowired
	private DjUserService userSvs;
	
	@Autowired
	private DjUScanService scanSvs;
	
	@Autowired
	private DjUCollectService collectSvs; 
	
	@Autowired
	private DjUCommentService commentSvs;
	
	@Autowired
	private DjActivityService activitySvs;
	
	@RequestMapping()
	public String Goods(DjGoodsParam param ,ModelMap map)
	{
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		searchMap.put(DjGoods.sStatus, 1);
		
		if(param.cId != null)
		{
			searchMap.put(DjGoods.sCategoryIdTree, param.cId);
			map.addAttribute("cId", param.cId);
		}
		if(StringUtils.isNotBlank(param.keywords))
		{
			searchMap.put(DjGoods.sKeywords, param.keywords);
			map.addAttribute("keywords", param.keywords);
		}
		
		Page<DjGoods> goodsAll = goodsSvs.findGoods(searchMap, param.page, param.size);
		
		map.addAttribute("goods", goodsAll);
		
		this.initCategory(map);
		
		return "/wx/goods/goods_list";
	}
	
	
	@RequestMapping(value= "/{gid}")
	public String GoodsDetail(@PathVariable Long gid,ModelMap map)
	{
		map.addAttribute("goods", goodsSvs.findGoods(gid));
		
		DjUser user = getUserInfo();
		if(user != null)
		{
			map.addAttribute("collect", collectSvs.findCollectByUidAndGid(user.getId(), gid));
			scanSvs.addScanByUid(user.getId(),gid);
		}
		
		map.addAttribute("comment_list", commentSvs.findEnableCommentByGid(gid));
		Integer limitNumber = activitySvs.limitNumberByGid(gid);	
		if(limitNumber != null)
			map.addAttribute("limitNumber", limitNumber);
		goodsSvs.updataViewCount(gid);
		
		return "/wx/goods/goods_detail";
	}
	@RequestMapping(value= "content/{gid}")
	public String GoodsContent(@PathVariable Long gid,ModelMap map)
	{
		map.addAttribute("goods", goodsSvs.findGoods(gid));
		
		DjUser user = getUserInfo();
		if(user != null)
		{
			map.addAttribute("collect", collectSvs.findCollectByUidAndGid(user.getId(), gid));
			scanSvs.addScanByUid(user.getId(),gid);
		}
		
		map.addAttribute("comment_list", commentSvs.findEnableCommentByGid(gid));
		
		return "/wx/goods/goods_detail_content";
	}
	
	@RequestMapping("/collect/toggle")
	@ResponseBody
	public Map<String, Object> goodsCollect(Long gid, ModelMap map)
	{

		Map<String, Object> res = new HashMap<String, Object>();
		
		res.put("error", 0);
		
		if(!isLogin())
		{
			res.put("error", 2);
			res.put("msg", "请先登录");
			return res;
		}
		DjUser user = getUserInfo();

		if (null == user) {
			res.put("error", 1);
			res.put("msg", "用户不存在");
			return res;
		}

		int code = collectSvs.toggleCollectByUserId(user.getId(), gid);

		if (1 == code) {
			res.put("msg", "收藏成功");
		} else {
			res.put("msg", "取消收藏成功");
		}

		return res;
	}
	
	@RequestMapping(value = "/list")
	public String goodsList(DjGoodsParam param ,ModelMap map)
	{
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		if(param.cId != null)
		{
			searchMap.put(DjGoods.sCategoryIdTree, param.cId);
			map.addAttribute("cId", param.cId);
		}
		if(StringUtils.isNotBlank(param.keywords))
		{
			searchMap.put(DjGoods.sKeywords, param.keywords);
			map.addAttribute("keywords", param.keywords);
		}
		
		Page<DjGoods> goodsAll = goodsSvs.findGoods(searchMap, param.page, param.size);
		
		map.addAttribute("goods", goodsAll);
		
		return "/wx/goods/goods_list_content";
	}
	
	@RequestMapping(value = "/category")
	public String category(DjGoodsParam param,ModelMap map)
	{
		this.initCategory(map);
		return "/wx/goods/category_list";
	}
	
	
	
	@RequestMapping(value = "/search")
	public String search(DjGoodsParam param,ModelMap map)
	{
		String keywords = param.keywords;
		goodsSvs.findByGoodsNo(keywords);
		
		this.initCategory(map);
		return "";
	}

	private void initCategory(ModelMap map)
	{
		List<DjGoodsCategory> parentCates = goodsSvs.findEnableParentCate();
		for (DjGoodsCategory parentCate : parentCates)
		{
			List<String> cateKeys = new ArrayList<>();
			int index = 0;
			List<DjGoodsCategory> cate1 = goodsSvs.findByEnableCateBydParentId(parentCate.getId());
			for (DjGoodsCategory cate : cate1)
			{
				String cateKey = parentCate.getTitle() + parentCate.getId() + index++;
				List<DjGoodsCategory> cate2 = goodsSvs.findByEnableCateBydParentId(cate.getId());
				if(cate2 != null && cate2.size() > 0)
				{
					cate2.add(0, cate);
					map.addAttribute(cateKey, cate2);
				}
				else
					map.addAttribute(cateKey, Arrays.asList(cate));
				cateKeys.add(cateKey);
			}
			map.addAttribute(parentCate.getTitle() + parentCate.getId(), cateKeys);
		}
		
		map.addAttribute("parentCate",parentCates);
	}
}
