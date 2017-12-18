package com.zxsm.wsc.controller.front.wx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.entity.ad.DjAd;
import com.zxsm.wsc.entity.article.DjArticle;
import com.zxsm.wsc.entity.article.DjArticleCategory;
import com.zxsm.wsc.service.article.DjArticleService;
import com.zxsm.wsc.service.goods.DjAdService;

@Controller
@RequestMapping(value="/wx/cubilose")
public class DjCubiloseController  extends DjBaseController
{
	
	@Autowired
	private DjArticleService articleSvs;
	
	@Autowired
	private DjAdService adSvs;
	
	@RequestMapping()
	public String index(ModelMap map)
	{
		
		/** 轮播 **/
		
		//首页轮播
		List<DjAd> ads_top = adSvs.findByCategoryTitle("燕窝馆顶部轮播");
		map.addAttribute("ads_top", ads_top);
		//首页轮播
		List<DjAd> ads_mid = adSvs.findByCategoryTitle("燕窝馆中部轮播");
		map.addAttribute("ads_mid", ads_mid);
		
		
		/** 文章 **/
		//燕窝馆类别
		List<DjArticle> articles_cat = articleSvs.findByArticleCateTitle("燕窝馆类别");
		map.addAttribute("articles_cat", articles_cat);
		
		//燕窝馆文章
		List<DjArticle> articles = articleSvs.findByArticleCateTitle("燕窝馆文章");
		map.addAttribute("articles", articles);
		
		/**  店铺环境  **/
		DjArticleCategory category = articleSvs.findTop1ByTitle("店铺环境");
		map.addAttribute("shop_cate", category);
		//店铺环境
		List<DjArticle> articles_shop = articleSvs.findByArticleCateTitle("店铺环境");
		map.addAttribute("shop_article", articles_shop);
		
		/**  商品 好货推荐  **/
		
		return "/wx/cubilose/cubilose";
	}
	
	@RequestMapping(value="/article/{id}")
	public String article(@PathVariable Long id,ModelMap map)
	{
		DjArticle article = articleSvs.findArticle(id);
		map.addAttribute("article", article);
		return "/wx/cubilose/article";
	}
}
