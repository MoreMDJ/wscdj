package com.zxsm.wsc.service.article;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.article.DjArticle;
import com.zxsm.wsc.entity.article.DjArticleCategory;
import com.zxsm.wsc.repository.article.DjArticleCategoryRepo;
import com.zxsm.wsc.repository.article.DjArticleRepo;

@Service
@Transactional
public class DjArticleService {

	@Autowired
	DjArticleRepo repository;

	@Autowired
	DjArticleCategoryRepo categoryRepo;
	
	
	public List<DjArticle> findByArticleCateTitle(String cateTitle)
	{
		if(StringUtils.isBlank(cateTitle))
			return null;
		return repository.findByCategoryTitleAndStatusIdOrderBySortIdAsc(cateTitle,1);
	}

	/**
	 * 分页查找所有文章，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjArticle> findArticleAll(int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return repository.findAll(pageRequest);
	}

	/**
	 * 查找文章，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjArticle> searchArticleAll(String keywords, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByTitleContaining(keywords, pageRequest);
	}

	/**
	 * 按分类查找文章
	 * 
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjArticle> findArticleByCategoryId(long categoryId, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return repository.findByCategoryId(categoryId, pageRequest);
	}

	/**
	 * 按类型搜索文章
	 * 
	 * @param categoryId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjArticle> searchArticleByCategoryId(long categoryId,String keywords, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return repository.findByCategoryIdAndTitleContaining(categoryId,
				keywords, pageRequest);
	}

	/**
	 * 查找所有分类
	 * 
	 * @return
	 */
	public List<DjArticleCategory> findCategoryAll()
	{
		List<DjArticleCategory> rootList = categoryRepo.findByParentIdIsNullOrderBySortIdAsc();
		List<DjArticleCategory> resList = new ArrayList<DjArticleCategory>();
		resList.addAll(rootList);

		if (rootList.size() > 0)
		{
			for (int idx = 0; idx < rootList.size(); idx++)
			{
				int index = resList.indexOf(rootList.get(idx));
				resList.addAll(index + 1, findChildCategoryRecursive(rootList.get(idx).getId()));
			}
		}
		return resList;
	}

	/**
	 * 保存分类
	 * 
	 * @param e
	 * @return
	 */
	public DjArticleCategory saveCategory(DjArticleCategory e)
	{
		if (null == e)
		{
			return null;
		}

		// 设置层数layerId
		if (null == e.getParentId())
		{
			e.setLayerId(1);
		}
		else
		{
			DjArticleCategory parent = categoryRepo.findOne(e.getParentId());

			if (null == parent)
			{
				e.setLayerId(1);
			}
			else
			{
				e.setLayerId(parent.getLayerId() + 1);
			}
		}

		return categoryRepo.save(e);
	}

	/**
	 * 保存文章
	 * 
	 * @param e
	 *            : 要保存的文章
	 * @return
	 */
	public DjArticle saveArticle(DjArticle e)
	{
		if (null == e)
		{
			return null;
		}

		// 设置层数categoryTitle
		if (null != e.getCategoryId())
		{
			DjArticleCategory cat = categoryRepo.findOne(e.getCategoryId());

			if (null != cat)
			{
				e.setCategoryTitle(cat.getTitle());
			}
		}

		return repository.save(e);
	}

	/**
	 * 删除文章，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteArticle(long id)
	{
		repository.delete(id);
	}

	/**
	 * 删除分类及下面的子分类，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteCategory(long id)
	{
		// 删除子分类
		List<DjArticleCategory> childList = categoryRepo.findByParentIdOrderBySortIdAsc(id);
		categoryRepo.delete(childList);

		categoryRepo.delete(id);
	}

	/**
	 * 查找文章
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public DjArticle findArticle(long id)
	{
		return repository.findOne(id);
	}
	
	/**
	 * 通过文章标题查找文章
	 * @param title
	 * @return
	 */
	public DjArticle findArticleByTitleAndShowable(String title)
	{
		return repository.findFirstByTitleAndStatusId(title, 0);
	}

	/**
	 * 查找分类，参数检查由调用函数完成
	 * 
	 * @param catId
	 *            ：分类ID
	 * @return
	 */
	public DjArticleCategory findCategory(long catId)
	{
		return categoryRepo.findOne(catId);
	}

	// 递归查找分类
	private List<DjArticleCategory> findChildCategoryRecursive(long parentId)
	{
		// 参数检查由调用函数决定
		List<DjArticleCategory> childList = categoryRepo.findByParentIdOrderBySortIdAsc(parentId);
		List<DjArticleCategory> resList = new ArrayList<DjArticleCategory>();
		resList.addAll(childList);

		if (childList.size() > 0)
		{
			for (int idx = 0; idx < childList.size(); idx++)
			{
				List<DjArticleCategory> grandChildList = findChildCategoryRecursive(childList.get(idx).getId());
				if (grandChildList.size() > 0)
				{
					int index = resList.indexOf(childList.get(idx));
					resList.addAll(index + 1, grandChildList);
				}
			}
		}

		return resList;
	}
	
	public DjArticleCategory findTop1ByTitle(String title)
	{
		if(StringUtils.isNotBlank(title))
		{
			return categoryRepo.findTop1ByTitle(title);
		}
		return null;
		
	}

}
