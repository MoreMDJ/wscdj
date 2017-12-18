package com.zxsm.wsc.service.goods;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.ad.DjAd;
import com.zxsm.wsc.entity.ad.DjAdCategory;
import com.zxsm.wsc.repository.ad.DjAdCategoryRepo;
import com.zxsm.wsc.repository.ad.DjAdRepo;

@Service
@Transactional
public class DjAdService
{

	@Autowired
	DjAdRepo adRepo;

	@Autowired
	DjAdCategoryRepo categoryRepo;

	/**
	 * 分页查找所有广告，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjAd> findAdAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return adRepo.findAll(pageRequest);
	}

	/**
	 * 查找广告，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjAd> searchAdAll(String keywords, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return adRepo.findByTitleContaining(keywords, pageRequest);
	}

	/**
	 * 按分类查找广告
	 * 
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjAd> findAdByCategoryId(long categoryId, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return adRepo.findByCategoryId(categoryId, pageRequest);
	}

	/**
	 * 根据类型标题查找开启的广告
	 * @param categoryTitle
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjAd> findAdByCategoryTitleAndIsEnabled(String categoryTitle, int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return adRepo.findByCategoryTitleAndStatusId(categoryTitle, 0, pageRequest);
	}

	/**
	 * 按类型搜索广告
	 * 
	 * @param categoryId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjAd> searchAdByCategoryId(long categoryId,String keywords, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return adRepo.findByCategoryIdAndTitleContaining(categoryId, keywords, pageRequest);
	}
	
	public List<DjAd> findByCategoryTitle(String cTitle)
	{
		if(StringUtils.isBlank(cTitle))
			return null;
		return adRepo.findByCategoryTitleAndStatusIdOrderBySortIdAsc(cTitle,0);
	}

	/**
	 * 查找所有分类
	 * 
	 * @return
	 */
	public List<DjAdCategory> findCategoryAll()
	{
		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id"));
		List<DjAdCategory> rootList = (List<DjAdCategory>) categoryRepo.findAll(sort);

		return rootList;
	}

	/**
	 * 保存分类
	 * 
	 * @param e
	 * @return
	 */
	public DjAdCategory saveCategory(DjAdCategory e)
	{
		if (null == e)
		{
			return null;
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
	public DjAd saveAd(DjAd e)
	{
		if (null == e)
		{
			return null;
		}

		// 设置层数categoryTitle, width, height
		if (null != e.getCategoryId())
		{
			DjAdCategory cat = categoryRepo.findOne(e.getCategoryId());

			if (null != cat)
			{
				e.setCategoryTitle(cat.getTitle());
				e.setWidth(cat.getWidth());
				e.setHeight(cat.getHeight());
			}
		}
		return adRepo.save(e);
	}

	/**
	 * 删除，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteAd(long id)
	{
		adRepo.delete(id);
	}

	/**
	 * 删除分类及下面的子分类，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteCategory(long id)
	{
		categoryRepo.delete(id);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public DjAd findAd(long id)
	{
		return adRepo.findOne(id);
	}

	/**
	 * 查找分类，参数检查由调用函数完成
	 * 
	 * @param catId
	 *            ：分类ID
	 * @return
	 */
	public DjAdCategory findCategory(long catId)
	{
		return categoryRepo.findOne(catId);
	}
}
