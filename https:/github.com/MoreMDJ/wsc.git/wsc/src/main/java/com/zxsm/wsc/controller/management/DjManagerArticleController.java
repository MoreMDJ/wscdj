package com.zxsm.wsc.controller.management;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.article.DjArticle;
import com.zxsm.wsc.entity.article.DjArticleCategory;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.service.article.DjArticleService;

/**
 * 后台广告管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value="/management/article")
public class DjManagerArticleController extends DjAdminLevel
{
    
    @Autowired
    DjArticleService articleSvs;
    
    @RequestMapping(value="/list")
    public String articleList(DjReqestParam param, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        // 保存排序号
        if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnSaveArticle(param.listId, param.sortId);
        }
        // 删除文章
        else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
        {
        	btnDeleteArticle(param.listId, param.listChkIdx);
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
        map.addAttribute("category_list", articleSvs.findCategoryAll());
        
        if (null == param.categoryId)
        {
        	if (null == param.keywords || param.keywords.isEmpty())
        	{
        		map.addAttribute("article_page", articleSvs.findArticleAll(param.page, param.size));
        	}
        	else
        	{
        		map.addAttribute("article_page", articleSvs.searchArticleAll(param.keywords, param.page, param.size));
        	}
        }
        else
        {
        	if (null == param.keywords || param.keywords.isEmpty())
        	{
        		map.addAttribute("article_page", articleSvs.findArticleByCategoryId(param.categoryId, param.page, param.size));
        	}
        	else
        	{
        		map.addAttribute("article_page", articleSvs.searchArticleByCategoryId(param.categoryId, param.keywords, param.page, param.size));
        	}
        }
        return "/management/article/article_list";
    }
    
    @RequestMapping(value="/edit")
    public String categoryEdit(Long id, String action, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        if (null != id)
        {
        	map.addAttribute("article", articleSvs.findArticle(id));
        }
        
        map.addAttribute("action", action);
        map.addAttribute("category_list", articleSvs.findCategoryAll());
        
        return "/management/article/article_edit";
    }
    
    @ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			Model model) {
		if (null != id) {
			model.addAttribute("bmArticle", articleSvs.findArticle(id));
		}
	}
    
    @RequestMapping(value="/save")
    public String categorySave(DjArticle article, String VIEWSTATE, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        articleSvs.saveArticle(article);
        
        return "redirect:/management/article/list";
    }
    
    @RequestMapping(value="/category/list")
    public String categoryList(DjReqestParam param, ModelMap map)
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
        
        map.addAttribute("category_list", articleSvs.findCategoryAll());
        
        return "/management/article/article_category_list";
    }
    
    @RequestMapping(value="/category/edit")
    public String categoryEdit(Long id, Long parentId, ModelMap map, HttpSession session)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        if (null != id)
        {
        	map.addAttribute("category", articleSvs.findCategory(id));
        }
        
        if (null != parentId)
        {
        	map.addAttribute("parentCat", articleSvs.findCategory(parentId));
        }
        
        map.addAttribute("category_list", articleSvs.findCategoryAll());
        
        return "/management/article/article_category_edit";
    }
    
    @RequestMapping(value="/category/save", method=RequestMethod.POST)
    public String categoryEdit(DjArticleCategory articleCat, String VIEWSTATE, ModelMap map)
    {
    	if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
        
        articleSvs.saveCategory(articleCat);
        
        return "redirect:/management/article/category/list";
    }
    
    /**
     * 保存分类排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveCategory(Long[] ids, Double[] sortIds)
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
        		DjArticleCategory cat = articleSvs.findCategory(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			articleSvs.saveCategory(cat);
        		}
        	}
        }
    }
    
    /**
     * 保存文章排序号
     * @param ids
     * @param chkIdx
     * @param sortIds
     */
    private void btnSaveArticle(Long[] ids, Double[] sortIds)
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
        		DjArticle cat = articleSvs.findArticle(ids[idx]);
        		
        		if (null != cat)
        		{
        			cat.setSortId(sortIds[idx]);
        			articleSvs.saveArticle(cat);
        		}
        	}
        }
    }
    
    /**
     * 删除分类及下面的所有子分类
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteCategory(Long[] ids, Integer[] chkIdx)
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
            	articleSvs.deleteCategory(ids[idx]);
            }
        }
    }
    
    /**
     * 删除文章
     * @param ids
     * @param chkIdx
     */
    private void btnDeleteArticle(Long[] ids, Integer[] chkIdx)
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
            	articleSvs.deleteArticle(ids[idx]);
            }
        }
    }
}
