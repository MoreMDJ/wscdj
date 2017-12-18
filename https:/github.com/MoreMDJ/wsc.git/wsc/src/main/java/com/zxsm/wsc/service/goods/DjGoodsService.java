package com.zxsm.wsc.service.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.goods.DjGoodsCategory;
import com.zxsm.wsc.entity.goods.DjGoodsParameter;
import com.zxsm.wsc.repository.goods.DjGoodsCategoryRepo;
import com.zxsm.wsc.repository.goods.DjGoodsParameterRepo;
import com.zxsm.wsc.repository.goods.DjGoodsRepo;


@Service
@Transactional
public class DjGoodsService
{

	@Autowired
	DjGoodsRepo goodsRepo;

	@Autowired
	DjGoodsCategoryRepo categoryRepo;

	@Autowired
	DjGoodsParameterRepo goodsParameterRepo;
	
	@Autowired
	DjParameterService parameterService;
	
	public List<DjGoods> findAll()
	{
		return (List<DjGoods>)goodsRepo.findAll();
	}

	/**
	 * 分页查找所有商品，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjGoods> findGoods(Map<String,Object> searchmap,int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.ASC, "id")));
		
		Criteria<DjGoods> criteria = initCriteria(searchmap);
		
		return goodsRepo.findAll(criteria,pageRequest);
	}
	public List<DjGoods> findGoods(Map<String,Object> searchmap)
	{
		Criteria<DjGoods> criteria = initCriteria(searchmap);
		
		return goodsRepo.findAll(criteria);
	}
	
	public Page<DjGoods> findKillGoods(Integer status,int page,int size)
	{
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		searchMap.put(DjGoods.sSaleType, 1);
		if(status != null)
			searchMap.put(DjGoods.sStatus, status);
		return findGoods(searchMap, page, size);
	}
	
	public Page<DjGoods> findSaleTypeGoods(Integer type, int page ,int size)
	{
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		searchMap.put(DjGoods.sSaleType, type);
		searchMap.put(DjGoods.sStatus, 1);
		return findGoods(searchMap, page, size);
	}
	
	public Page<DjGoods> findSaleTypeAndCateTree(Integer type, Long cateTreeId,int page, int size)
	{
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		searchMap.put(DjGoods.sSaleType, type);
		searchMap.put(DjGoods.sCategoryIdTree,cateTreeId);
		searchMap.put(DjGoods.sStatus, 1);
		return findGoods(searchMap, page, size);
	}
	
	public List<DjGoods> findGoodsByIdIn(Long[] ids)
	{
		if(ids == null || ids.length < 1)
			return null;
		return goodsRepo.findByIdIn(ids);
	}

	/**
	 * 查找商品，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjGoods> searchGoodsAll(String keywords, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return goodsRepo.findByTitleContaining(keywords, pageRequest);
	}
	
	public List<DjGoods> findByGoodsNo(String goodsNo)
	{
		if(StringUtils.isBlank(goodsNo))
			return null;
		return goodsRepo.findByGoodsNo(goodsNo);
	}
	
	public List<DjGoods> findBySku(String sku)
	{
		if(StringUtils.isBlank(sku))
			return null;
		return goodsRepo.findBySku(sku);
	}

	/**
	 * 按分类查找商品
	 * 
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjGoods> findGoodsByCategoryId(long categoryId, int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return goodsRepo.findByCategoryIdTreeContaining("[" + categoryId + "]",pageRequest);
	}
	
	public List<DjGoods> findGoodsByCategoryId(Long categoryId)
	{
		if(categoryId == null)
			return null;
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		searchMap.put(DjGoods.sStatus, 1);
		searchMap.put(DjGoods.sCategoryId,categoryId);
		
		return findGoods(searchMap);
	}

	/**
	 * 按类型搜索商品
	 * 
	 * @param categoryId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjGoods> searchGoodsByCategoryId(long categoryId,String keywords, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return goodsRepo.findByCategoryIdAndTitleContaining(categoryId,keywords, pageRequest);
	}
	
	/**
	 * 保存商品
	 * 
	 * @param e : 要保存的商品
	 * 
	 * @return
	 */
	public DjGoods saveGoods(DjGoods e)
	{
		if(e.getClick() == null)
			e.setClick(0L);

		Long paramCategoryId = null;

		// 设置层数categoryTitle和categoryIdTree
		if (null != e.getCategoryId()) {
			DjGoodsCategory cat = categoryRepo.findOne(e.getCategoryId());

			if (null != cat) {
				e.setCategoryTitle(cat.getTitle());
				e.setCategoryIdTree(cat.getParentTree());
			}

			paramCategoryId = cat.getParamCategoryId();
		}

		// 当修改时，若切换过类型，参数数量由多变少，需要删除多余的参数
		if (null != e.getId() && null != paramCategoryId)
		{
			int count = parameterService.countParameterByCategoryId(paramCategoryId);

			int size = 0;

			if (null != e.getParamList())
			{
				size = e.getParamList().size();
			}

			if (size > count)
			{
				List<DjGoodsParameter> subList = e.getParamList().subList(count, size);
				goodsParameterRepo.delete(subList);
				e.getParamList().removeAll(subList);
			}
		}

		// 保存参数
		goodsParameterRepo.save(e.getParamList());
		
		return goodsRepo.save(e);
	}
	
	public Integer updataViewCount(Long gid)
	{
		if(gid == null)
			return null;
		return goodsRepo.updataView(gid);
	}

	/**
	 * 删除商品，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：商品ID
	 */
	public void deleteGoods(long id)
	{
		goodsRepo.delete(id);
	}

	/**
	 * 查找商品
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public DjGoods findGoods(long id)
	{
		return goodsRepo.findOne(id);
	}

	/**
	 * 查找所有分类
	 * 
	 * @param typeId
	 *            分类类型
	 * 
	 * @return
	 */
	public List<DjGoodsCategory> findCategoryAll()
	{
		List<DjGoodsCategory> rootList = categoryRepo.findByParentIdIsNullOrderBySortIdAsc();
		List<DjGoodsCategory> resList = new ArrayList<DjGoodsCategory>();
		resList.addAll(rootList);

		if (rootList.size() > 0)
		{
			for (int idx = 0; idx < rootList.size(); idx++)
			{
				int index = resList.indexOf(rootList.get(idx));
				resList.addAll(index + 1,findChildCategoryRecursive(rootList.get(idx).getId()));
			}
		}
		return resList;
	}
	
	public List<DjGoodsCategory> findGuideCategory()
	{
		List<DjGoodsCategory> guideCate = categoryRepo.findByIsEnableAndIsGuideOrderBySortId(true,true);
		Iterator<DjGoodsCategory> iterator = guideCate.iterator();
		while(iterator.hasNext())
		{
			DjGoodsCategory category = iterator.next();
			if(category.getLayerId() != 2)
				iterator.remove();
		}
		return guideCate;
	}

	/**
	 * 保存分类
	 * 
	 * @param e
	 * @return
	 */
	public DjGoodsCategory saveCategory(DjGoodsCategory e)
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
			DjGoodsCategory parent = categoryRepo.findOne(e.getParentId());

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
	
	public List<DjGoodsCategory> findEnableParentCate()
	{
		return categoryRepo.findByParentIdIsNullAndIsEnableOrderBySortIdAsc(true);
	}
	

	public List<DjGoodsCategory> findByEnableCateBydParentId(Long parentId)
	{
		if(parentId == null)
			return null;
		return categoryRepo.findByIsEnableAndParentIdOrderBySortIdAsc(true, parentId);
	}


	/**
	 * 删除分类及下面的子分类，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteCategory(long id) {
		// 删除子分类
		List<DjGoodsCategory> childList = categoryRepo.findByParentIdOrderBySortIdAsc(id);

		categoryRepo.delete(childList);

		categoryRepo.delete(id);
	}

	/**
	 * 查找分类，参数检查由调用函数完成
	 * 
	 * @param catId
	 *            ：分类ID
	 * @return
	 */
	public DjGoodsCategory findCategory(long catId) {
		return categoryRepo.findOne(catId);
	}

	/**
	 * 查找根类型
	 * 
	 * @param parentId
	 * @return
	 */
	public List<DjGoodsCategory> findCategoryRoot(int typeId) {
		return categoryRepo
				.findByTypeIdAndParentIdIsNullOrderBySortIdAsc(typeId);
	}

	/**
	 * 查找指定类型的商品分类
	 * 
	 * @param typeId
	 * @return
	 */
	public List<DjGoodsCategory> findCategoryRootByTypeId(int typeId) {
		return categoryRepo
				.findByTypeIdAndParentIdIsNullOrderBySortIdAsc(typeId);
	}

	/**
	 * 根据父类型查找类型
	 * 
	 * @param parentId
	 * @return
	 */
	public List<DjGoodsCategory> findCategoryByParentId(long parentId,
			int typeId) {
		return categoryRepo.findByTypeIdAndParentIdOrderBySortIdAsc(typeId,
				parentId);
	}

	/**
	 * 根据类型和父类型查找商品分类
	 * 
	 * @param typeId
	 * @param parentId
	 * @return
	 */
	public List<DjGoodsCategory> findCategoryByTypeIdAndParentId(int typeId,
			long parentId) {
		return categoryRepo.findByTypeIdAndParentIdOrderBySortIdAsc(typeId,
				parentId);
	}

	/**
	 * 按名称查找类型
	 * 
	 * @param title
	 * @return
	 */
	public DjGoodsCategory findCategoryByTitle(String title)
	{
		return categoryRepo.findTop1ByTitle(title);
	}
	
	// 递归查找分类
	private List<DjGoodsCategory> findChildCategoryRecursive(long parentId)
	{
		// 参数检查由调用函数决定
		List<DjGoodsCategory> childList = categoryRepo.findByParentIdOrderBySortIdAsc(parentId);
		List<DjGoodsCategory> resList = new ArrayList<DjGoodsCategory>();
		resList.addAll(childList);

		if (childList.size() > 0)
		{
			for (int idx = 0; idx < childList.size(); idx++)
			{
				List<DjGoodsCategory> grandChildList = findChildCategoryRecursive(childList.get(idx).getId());
				if (grandChildList.size() > 0)
				{
					int index = resList.indexOf(childList.get(idx));
					resList.addAll(index + 1, grandChildList);
				}
			}
		}

		return resList;
	}
	
	/**
	 * 获取查询规则
	 */
	public Criteria<DjGoods> initCriteria(Map<String,Object> searchMap)
	{
		if (searchMap == null || searchMap.isEmpty())
			return null;

		Criteria<DjGoods> criteria = new Criteria<DjGoods>();

		Long categoryIdTree = (Long) searchMap.get(DjGoods.sCategoryIdTree);
		if(categoryIdTree != null)
			criteria.add(Restrictions.like(DjGoods.sCategoryIdTree, "["+ categoryIdTree +"]", true));

		Long leftNumber = (Long) searchMap.get(DjGoods.sLeftNumber);
		if(leftNumber != null)
			criteria.add(Restrictions.eq(DjGoods.sLeftNumber, leftNumber, true));
		
		Long categoryId = (Long) searchMap.get(DjGoods.sCategoryId);
		if(categoryId != null)
			criteria.add(Restrictions.eq(DjGoods.sCategoryId, categoryId, true));

		Integer status = (Integer) searchMap.get(DjGoods.sStatus);
		if(status != null)
			criteria.add(Restrictions.eq(DjGoods.sStatus, status, true));
		
		Integer saleType = (Integer) searchMap.get(DjGoods.sSaleType);
		if(saleType != null)
			criteria.add(Restrictions.eq(DjGoods.sSaleType, saleType, true));


		String keywords = (String )searchMap.get(DjGoods.sKeywords);
		if(StringUtils.isNotBlank(keywords))
			criteria.add(
					Restrictions.or
					(
							Restrictions.like(DjGoods.sTitle, keywords, true),
							Restrictions.like(DjGoods.sSubTitle, keywords, true),
							Restrictions.like(DjGoods.sTags, keywords, true),
							Restrictions.like(DjGoods.sSku, keywords, true)
							)
					);
		
		criteria.setOrderByAsc("sortId");
		String sortA = (String)searchMap.get(DjGoods.sSortAsc);
		if(StringUtils.isNotBlank(sortA))
			criteria.setOrderByAsc(sortA);
	
		String sortD = (String)searchMap.get(DjGoods.sSortDesc);
		if(StringUtils.isNotBlank(sortD))
			criteria.setOrderByDesc(sortD);
		
		
		return criteria;
	}
}
