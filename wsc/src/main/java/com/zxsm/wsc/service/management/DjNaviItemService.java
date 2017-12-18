package com.zxsm.wsc.service.management;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.management.DjNaviItem;
import com.zxsm.wsc.repository.management.DjNaviItemRepo;

@Service
@Transactional
public class DjNaviItemService {

	@Autowired
	DjNaviItemRepo repository;
	/**
	 * 分页查找所有导航栏，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjNaviItem> findItemAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		
		return repository.findAll(pageRequest);
	}

	/**
	 * 查找导航栏，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjNaviItem> searchItemAll(String keywords, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByTitleContaining(keywords, pageRequest);
	}

	/**
	 * 按分类查找导航栏
	 * 
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjNaviItem> findItemByCategoryId(long categoryId, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return repository.findByCategoryId(categoryId, pageRequest);
	}

	/**
	 * 按类型搜索导航栏
	 * 
	 * @param categoryId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjNaviItem> searchItemByCategoryId(long categoryId,String keywords, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return repository.findByCategoryIdAndTitleContaining(categoryId, keywords, pageRequest);
	}

	/**
	 * 保存导航栏
	 * 
	 * @param e : 要保存的品牌
	 * @return
	 */
	public DjNaviItem saveItem(DjNaviItem e)
	{
		if (null == e)
		{
			return null;
		}
		return repository.save(e);
	}

	/**
	 * 删除导航栏，参数检查由调用函数完成
	 * 
	 * @param id : 品牌ID
	 */
	public void deleteItem(long id)
	{
		repository.delete(id);
	}

	/**
	 * 查找导航栏
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public DjNaviItem findItem(long id)
	{
		return repository.findOne(id);
	}
	
	public List<DjNaviItem> findMobileNavitems()
	{
		return repository.findByIsEnableTrueAndCategoryIdOrderBySortIdAsc(0L);
	}
	
	/**
	 * 触屏首页导航
	 * @return
	 */
	public List<DjNaviItem> findTo10()
	{
		return repository.findTop10ByIsEnableTrueAndCategoryIdOrderBySortIdAsc(0l);
	}
	
	
	/**
	 * 触屏医生导航
	 * @return
	 */
	public List<DjNaviItem> findDoctorNavi()
	{
		return repository.findByIsEnableTrueAndCategoryIdOrderBySortIdAsc(1l);
	}
	
	public List<DjNaviItem> findByTitleAndCat(String title,Long catId)
	{
		if(StringUtils.isBlank(title) || catId == null)
			return null;
		return repository.findByTitleAndCategoryId(title, catId);
	}
	
	public DjNaviItem findDocItemByTitle(String title)
	{
		List<DjNaviItem> items = findByTitleAndCat(title, 1l);
		if(items != null && items.size() > 0)
			return items.get(0);
		return null;
	}
}

