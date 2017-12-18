package com.zxsm.wsc.service.goods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.goods.DjParameter;
import com.zxsm.wsc.entity.goods.DjParameterCategory;
import com.zxsm.wsc.repository.goods.DjParameterCategoryRepo;
import com.zxsm.wsc.repository.goods.DjParameterRepo;

/**
 * 参数服务类
 * 
 * @author maeing
 *
 */

@Service
@Transactional
public class DjParameterService
{

	@Autowired
	DjParameterRepo paramRepo;

	@Autowired
	DjParameterCategoryRepo categoryRepo;

	/**
	 * 查找所有参数
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjParameter> findParameterAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC,"sortId").and(new Sort(Direction.DESC, "id")));
		return paramRepo.findAll(pageRequest);
	}

	/**
	 * 搜索所有参数
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjParameter> searchParameterAll(String keywords, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC,"sortId").and(new Sort(Direction.DESC, "id")));

		return paramRepo.findByTitleContaining(keywords, pageRequest);
	}

	/**
	 * 按类型查找参数
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjParameter> findParameterByCategoryId(long categoryId, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC,"sortId").and(new Sort(Direction.DESC, "id")));

		return paramRepo.findByCategoryTreeContaining("["+categoryId+"]", pageRequest);
	}

	/**
	 * 根据类型ID查找参数
	 * @param categoryId
	 * @return
	 */
	public List<DjParameter> findParameterByCategoryId(long categoryId)
	{

		return paramRepo.findByCategoryTreeContainingOrderBySortIdAsc("["+categoryId+"]");
	}

	/**
	 * 按类型搜索参数
	 * @param categoryId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjParameter> searchParameterByCategoryId(long categoryId, String keywords, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC,"sortId").and(new Sort(Direction.DESC, "id")));
		return paramRepo.findByCategoryIdAndTitleContaining(categoryId, keywords, pageRequest);
	}

	/**
	 * 查找所有参数类型
	 * @return
	 */
	public List<DjParameterCategory> findCategoryAll()
	{
		List<DjParameterCategory> rootList = categoryRepo.findByParentIdIsNullOrderBySortIdAsc();
		List<DjParameterCategory> resList = new ArrayList<DjParameterCategory>();
		resList.addAll(rootList);

		if (rootList.size() > 0)
		{
			for (int idx=0; idx < rootList.size(); idx++)
			{
				int index = resList.indexOf(rootList.get(idx));
				resList.addAll(index + 1, findChildCategoryRecursive(rootList.get(idx).getId()));
			}
		}
		return resList;
	}

	/**
	 * 查找参数类型, 参数检查由调用函数完成
	 * @param id
	 * @return
	 */
	public DjParameterCategory findCategory(long id)
	{
		return categoryRepo.findOne(id);
	}

	/**
	 * 查找参数
	 * @param id
	 * @return
	 */
	public DjParameter findParameter(long id)
	{
		return paramRepo.findOne(id);
	}

	/**
	 * 保存参数类型
	 * @param e
	 * @return
	 */
	public DjParameterCategory saveCategory(DjParameterCategory e)
	{
		if (null == e)
		{
			return null;
		}

		e = categoryRepo.save(e);

		// 设置层数layerId和parentTree
		if (null == e.getParentId())
		{
			e.setLayerId(1);
			e.setParentTree("[" + e.getId() + "]");
		}
		else
		{
			DjParameterCategory parent = categoryRepo.findOne(e.getParentId());

			if (null == parent)
			{
				e.setLayerId(1);
				e.setParentTree("[" + e.getId() + "]");
			}
			else
			{
				e.setLayerId(parent.getLayerId() + 1);
				e.setParentTree(parent.getParentTree() + ",[" + e.getId() + "]");
			}
		}

		return categoryRepo.save(e);
	}

	public DjParameter saveParameter(DjParameter e)
	{
		if (null == e)
		{
			return null;
		}

		// 设置层数categoryTitle和categoryTree
		if (null != e.getCategoryId())
		{
			DjParameterCategory cat = categoryRepo.findOne(e.getCategoryId());

			if (null != cat)
			{
				e.setCategoryTitle(cat.getTitle());
				e.setCategoryTree(cat.getParentTree());
			}
		}

		return paramRepo.save(e);
	}

	/**
	 * 删除参数类型及下面的所有子分类
	 * @param id
	 */
	public void deleteCategory(long id)
	{
		List<DjParameterCategory> childList = categoryRepo.findByParentIdOrderBySortIdAsc(id);
		categoryRepo.delete(childList);

		categoryRepo.delete(id);
	}

	/**
	 * 删除参数
	 * @param id
	 */
	public void deleteParameter(long id)
	{
		paramRepo.delete(id);
	}

	/**
	 * 删除参数
	 * @param entities
	 */
	public void delete(List<DjParameter> entities)
	{
		if (null != entities)
		{
			paramRepo.delete(entities);
		}
	}

	/**
	 * 计算某类参数数目
	 * @param categoryId
	 * @return
	 */
	public int countParameterByCategoryId(long categoryId)
	{  
		return paramRepo.countByCategoryTreeContaining("[" + categoryId + "]");
	}

	// 递归查找分类
	private List<DjParameterCategory> findChildCategoryRecursive(long parentId)
	{
		// 参数检查由调用函数决定
		List<DjParameterCategory> childList = categoryRepo.findByParentIdOrderBySortIdAsc(parentId);
		List<DjParameterCategory> resList = new ArrayList<DjParameterCategory>();
		resList.addAll(childList);

		if (childList.size() > 0)
		{
			for (int idx=0; idx < childList.size(); idx++)
			{
				List<DjParameterCategory> grandChildList = findChildCategoryRecursive(childList.get(idx).getId());
				if (grandChildList.size() > 0)
				{
					int index = resList.indexOf(childList.get(idx));
					resList.addAll(index + 1, grandChildList);
				}
			}
		}

		return resList;
	}
}