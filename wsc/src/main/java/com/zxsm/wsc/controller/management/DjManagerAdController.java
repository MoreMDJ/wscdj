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
import com.zxsm.wsc.entity.ad.DjAd;
import com.zxsm.wsc.entity.ad.DjAdCategory;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.service.goods.DjAdService;

/**
 * 后台广告管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/management/ad")
public class DjManagerAdController extends DjAdminLevel
{

	@Autowired
	DjAdService adSvs;

	@RequestMapping(value = "/list")
	public String AdList(DjReqestParam param, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		// 保存排序号
		if ("btnSave".equalsIgnoreCase(param.EVENTTARGET)) {
			btnSaveAd(param.listId, param.sortId);
		}
		// 删除文章
		else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET)) {
			btnDeleteAd(param.listId, param.listChkIdx);
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

		map.addAttribute("param",param);
		map.addAttribute("category_list", adSvs.findCategoryAll());

		if (null == param.categoryId) {
			if (null == param.keywords || param.keywords.isEmpty()) {
				map.addAttribute("ad_page", adSvs.findAdAll(param.page, param.size));
			} else {
				map.addAttribute("ad_page",
						adSvs.searchAdAll(param.keywords, param.page, param.size));
			}
		} else {
			if (null == param.keywords || param.keywords.isEmpty()) {
				map.addAttribute("ad_page",
						adSvs.findAdByCategoryId(param.categoryId, param.page, param.size));
			} else {
				map.addAttribute("ad_page", adSvs.searchAdByCategoryId(param.categoryId, param.keywords, param.page, param.size));
			}
		}
		return "/management/ad/ad_list";
	}

	@RequestMapping(value = "/edit")
	public String categoryEdit(Long id, String __EVENTTARGET,
			String __EVENTARGUMENT, String action, ModelMap map) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		if (null != id) {
			map.addAttribute("ad", adSvs.findAd(id));
		}

		map.addAttribute("action", action);
		map.addAttribute("category_list", adSvs.findCategoryAll());

		return "/management/ad/ad_edit";
	}
	
	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			Model model) {
		if (null != id) {
			model.addAttribute("bmAd", adSvs.findAd(id));
		}
	}

	@RequestMapping(value = "/save")
	public String categorySave(DjAd bmAd, String __VIEWSTATE, ModelMap map) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		adSvs.saveAd(bmAd);

		return "redirect:/management/ad/list";
	}

	@RequestMapping(value = "/category/list")
	public String categoryList(DjReqestParam param, ModelMap map) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		// 保存排序号
		if ("btnSave".equalsIgnoreCase(param.EVENTTARGET)) {
			btnSaveCategory(param.listId, param.sortId);
		}
		// 删除分类
		else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET)) {
			btnDeleteCategory(param.listId, param.listChkIdx);
		}

		map.addAttribute("category_list", adSvs.findCategoryAll());

		return "/management/ad/ad_category_list";
	}

	@RequestMapping(value = "/category/edit")
	public String categoryEdit(Long id, ModelMap map) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != id) {
			map.addAttribute("category", adSvs.findCategory(id));
		}

		map.addAttribute("category_list", adSvs.findCategoryAll());

		return "/management/ad/ad_category_edit";
	}

	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public String categoryEdit(DjAdCategory cat, String VIEWSTATE, ModelMap map) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		adSvs.saveCategory(cat);

		return "redirect:/management/ad/category/list";
	}

	/**
	 * 保存分类排序号
	 * 
	 * @param ids
	 * @param chkIdx
	 * @param sortIds
	 */
	private void btnSaveCategory(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1
				|| sortIds.length < 1) {
			return;
		}

		for (int idx = 0; idx < ids.length; idx++) {
			if (sortIds.length > idx) {
				DjAdCategory cat = adSvs.findCategory(ids[idx]);

				if (null != cat) {
					cat.setSortId(sortIds[idx]);
					adSvs.saveCategory(cat);
				}
			}
		}
	}

	/**
	 * 保存文章排序号
	 * 
	 * @param ids
	 * @param chkIdx
	 * @param sortIds
	 */
	private void btnSaveAd(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1
				|| sortIds.length < 1) {
			return;
		}

		for (int idx = 0; idx < ids.length; idx++) {
			if (sortIds.length > idx) {
				DjAd cat = adSvs.findAd(ids[idx]);

				if (null != cat) {
					cat.setSortId(sortIds[idx]);
					adSvs.saveAd(cat);
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
	private void btnDeleteCategory(Long[] ids, Integer[] chkIdx) {
		if (null == ids || null == chkIdx || ids.length < 1
				|| chkIdx.length < 1) {
			return;
		}

		for (int idx : chkIdx) {
			if (idx >= 0 && ids.length > idx) {
				adSvs.deleteCategory(ids[idx]);
			}
		}
	}

	/**
	 * 删除文章
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteAd(Long[] ids, Integer[] chkIdx) {
		if (null == ids || null == chkIdx || ids.length < 1
				|| chkIdx.length < 1) {
			return;
		}

		for (int idx : chkIdx) {
			if (idx >= 0 && ids.length > idx) {
				adSvs.deleteAd(ids[idx]);
			}
		}
	}
}
