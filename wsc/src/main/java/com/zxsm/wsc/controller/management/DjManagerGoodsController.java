package com.zxsm.wsc.controller.management;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.DjEnums;
import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.goods.DjGoodsCategory;
import com.zxsm.wsc.service.goods.DjGoodsService;
import com.zxsm.wsc.service.goods.DjParameterService;

/**
 * 后台商品管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value = "/management/goods")
public class DjManagerGoodsController extends DjAdminLevel
{

	@Autowired
	private DjGoodsService djGoodsService;
	
	@Autowired
	private DjParameterService djParameterService;
	
	private static final String kNavName = "channel_goods_list";

	@RequestMapping(value = "/list")
	public String goodsList(DjReqestParam param,Long categoryId, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		Map<String ,Object>searchMap = new HashMap<String,Object>();
		if(param != null && param.statusId != null)
			searchMap.put(DjGoods.sStatus, param.statusId);
		
		// 保存排序号
		if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
		{
			btnSaveGoods(param.listId, param.sortId);
		}
		// 删除文章
		else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
		{
			btnDeleteGoods(param.listId, param.listChkIdx);
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
			if(categoryId != null)
				searchMap.put(DjGoods.sCategoryIdTree, categoryId);
			searchMap.put(DjGoods.sKeywords, param.keywords);
		}
		// 切换视图模式
		else if ("btnViewImg".equalsIgnoreCase(param.EVENTTARGET) || "btnViewTxt".equalsIgnoreCase(param.EVENTTARGET))
		{
			param.VIEWSTATE = param.EVENTTARGET;
		}
		// 类别
		else if("btnCategory".equalsIgnoreCase(param.EVENTTARGET))
		{
			if(categoryId != null)
			{
				searchMap.put(DjGoods.sCategoryIdTree, categoryId);
			}
		}

		map.addAttribute("goods_page",djGoodsService.findGoods(searchMap,param.page, param.size));
		map.addAttribute("goods_category_list", djGoodsService.findCategoryAll());
		map.addAttribute("service_category_list", djGoodsService.findCategoryAll());
		map.addAttribute("categoryId", categoryId);

		if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.View))
		{
			if ("btnViewTxt".equalsIgnoreCase(param.VIEWSTATE))
			{
				return "/management/goods/goods_txt_list";
			}

			return "/management/goods/goods_img_list";
		}
		else
			return URL_NORight;
	}

	@RequestMapping(value = "/edit")
	public String goodsEdit(Long id,DjReqestParam param, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		int typeId = 0;

		if (null != id) {
			DjGoods goods = djGoodsService.findGoods(id);
			map.addAttribute("goods", goods);

			DjGoodsCategory bpc = djGoodsService.findCategory(goods.getCategoryId());

			if (null != bpc)
			{
				if (null != bpc.getTypeId())
				{
					typeId = bpc.getTypeId();
				}
				Long paramCatId = bpc.getParamCategoryId();

				if (null != paramCatId) {
					map.addAttribute("param_list", djParameterService
							.findParameterByCategoryId(paramCatId));

//					// 查找产品列表
//					map.addAttribute("product_list", djProductService
//							.findProductByCategoryId(goods.getCategoryId()));
//
//					// 查找品牌
//					map.addAttribute("brand_list", djBrandService
//							.findBrandByCategoryId(goods.getCategoryId()));
				}
			}
		}

		map.addAttribute("typeId", typeId);
		map.addAttribute("category_list", djGoodsService.findCategoryAll());

		return "/management/goods/goods_edit";
	}

	@RequestMapping(value = "/save")
	public String goodsSave(DjGoods goods, String picUris,DjReqestParam param, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		djGoodsService.saveGoods(goods);

		return "redirect:/management/goods/list";
	}

	@RequestMapping(value = "/barcode/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> validateForm(String param, Long id)
	{
		Map<Object, Object> res = new HashMap<Object, Object>();

		res.put("status", "n");

		if (null == param || param.isEmpty())
		{
			res.put("info", "该字段不能为空");
			return res;
		}

		res.put("status", "y");

		return res;
	}

	@RequestMapping(value = "/parameter/{categoryId}", method = RequestMethod.POST)
	public String parameter(@PathVariable Long categoryId, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		DjGoodsCategory bpc = djGoodsService.findCategory(categoryId);

		if (null != bpc) {
			Long paramCatId = bpc.getParamCategoryId();

			if (null != paramCatId) {
				map.addAttribute("param_list", djParameterService.findParameterByCategoryId(paramCatId));
			}
		}

//		// 查找产品列表
//		map.addAttribute("product_list",bmProductService.findProductByCategoryId(categoryId));
//
//		// 查找品牌
//		map.addAttribute("brand_list",bmBrandService.findBrandByCategoryId(categoryId));

		return "/management/goods/goods_param_list";
	}

	@RequestMapping(value = "/category/list")
	public String categoryList(DjReqestParam param,Long typeId,ModelMap map)
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

		// 默认商品分类
		if (null == typeId)
		{
			typeId = 0l;
		}

		map.addAttribute("typeId", typeId);
		map.addAttribute("category_list",djGoodsService.findCategoryAll());

		return "/management/goods/goods_category_list";
	}

	@RequestMapping(value = "/category/edit")
	public String categoryEdit(Long id,Long parentId,Integer typeId,DjReqestParam param, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		DjGoodsCategory category = null;

		if (null != id) {
			category = djGoodsService.findCategory(id);
			map.addAttribute("category", category);
		}

		DjGoodsCategory parentCat = null;

		if (null != parentId) {
			parentCat = djGoodsService.findCategory(parentId);
			map.addAttribute("parentCat", parentCat);
		}

		if (null != parentCat) {
			typeId = parentCat.getTypeId();
		} else if (null != category) {
			typeId = category.getTypeId();
		}

		if (null == typeId) {
			typeId = 0;
		}

		map.addAttribute("typeId", typeId);

		map.addAttribute("category_list",djGoodsService.findCategoryAll());
		map.addAttribute("param_category_list", djParameterService.findCategoryAll());

		return "/management/goods/goods_category_edit";
	}

	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public String categorySave(DjGoodsCategory cat,
			String VIEWSTATE,
			ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		djGoodsService.saveCategory(cat);

		return "redirect:/management/goods/category/list";
	}

	@RequestMapping(value = "/category/type/{typeId}")
	public String typeGoodsCategory(@PathVariable Integer typeId, Long parentId, ModelMap map)
	{

		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != parentId) {
			map.addAttribute("parentCat", djGoodsService.findCategory(parentId));
		}

		map.addAttribute("category_list",
				djGoodsService.findCategoryAll());

		return "/management/type_goods_category";
	}

	@RequestMapping(value = "/categoryGoods/type/{typeId}")
	public String typeGoodsCategoryGoods(@PathVariable Integer typeId, Long goodsId, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != goodsId)
		{
			map.addAttribute("goods", djGoodsService.findGoods(goodsId));
		}
		map.addAttribute("category_list", djGoodsService.findCategoryAll());

		return "/management/type_goods_categoryGoods";
	}

	@RequestMapping(value = "/categoryProduct/type/{typeId}")
	public String typeGoodsCategoryProduct(@PathVariable Integer typeId, Long productId, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		map.addAttribute("category_list",djGoodsService.findCategoryAll());

		return "/management/type_goods_categoryProduct";
	}

	@RequestMapping(value = "/categoryBrand/type/{typeId}")
	public String typeGoodsCategoryBrand(@PathVariable Integer typeId, Long brandId, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		map.addAttribute("category_list", djGoodsService.findCategoryAll());

		return "/management/type_goods_categoryBrand";
	}

	@RequestMapping(value="/dialog")
	public String dialog(ModelMap map)
	{
		map.addAttribute("category_list",djGoodsService.findCategoryAll());
		return "/management/dialog/dialog";
	}

	/**
	 * 保存分类排序号
	 * 
	 * @param ids
	 * @param chkIdx
	 * @param sortIds
	 */
	private void btnSaveCategory(Long[] ids, Double[] sortIds)
	{
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1)
		{
			return;
		}

		for (int idx = 0; idx < ids.length; idx++)
		{
			if (sortIds.length > idx)
			{
				DjGoodsCategory cat = djGoodsService.findCategory(ids[idx]);

				if (null != cat)
				{
					cat.setSortId(sortIds[idx]);
					djGoodsService.saveCategory(cat);
				}
			}
		}
	}

	/**
	 * 保存商品排序号
	 * 
	 * @param ids
	 * @param chkIdx
	 * @param sortIds
	 */
	private void btnSaveGoods(Long[] ids, Double[] sortIds)
	{
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1)
		{
			return;
		}

		for (int idx = 0; idx < ids.length; idx++)
		{
			if (sortIds.length > idx)
			{
				DjGoods cat = djGoodsService.findGoods(ids[idx]);

				if (null != cat)
				{
					cat.setSortId(sortIds[idx]);
					djGoodsService.saveGoods(cat);
				}
			}
		}
	}

	/**
	 * 删除分类及下面的所有子分类
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteCategory(Long[] ids, Integer[] chkIdx)
	{
		if (null == ids || null == chkIdx || ids.length < 1
				|| chkIdx.length < 1)
		{
			return;
		}

		for (int idx : chkIdx)
		{
			if (idx >= 0 && ids.length > idx)
			{
				djGoodsService.deleteCategory(ids[idx]);
			}
		}
	}

	/**
	 * 删除商品
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteGoods(Long[] ids, Integer[] chkIdx)
	{
		if (null == ids || null == chkIdx || ids.length < 1 || chkIdx.length < 1)
		{
			return;
		}

		for (int idx : chkIdx)
		{
			if (idx >= 0 && ids.length > idx)
			{
				djGoodsService.deleteGoods(ids[idx]);
			}
		}
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id, Model model)
	{
		if (null != id)
		{
			model.addAttribute("goods", djGoodsService.findGoods(id));
		}
	}
}
