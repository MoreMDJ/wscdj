package com.zxsm.wsc.repository.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.management.DjNaviItem;

/**
 * DjNaviItem 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjNaviItemRepo extends PagingAndSortingRepository<DjNaviItem, Long>, JpaSpecificationExecutor<DjNaviItem> 
{
    List<DjNaviItem> findByIsEnableTrueAndCategoryIdOrderBySortIdAsc(long categoryId);
    List<DjNaviItem> findTop10ByIsEnableTrueAndCategoryIdOrderBySortIdAsc(long categoryId);
    Page<DjNaviItem> findByCategoryId(long categoryId, Pageable page);
	Page<DjNaviItem> findByCategoryIdAndTitleContaining(long categoryId, String keywords, Pageable page);
	Page<DjNaviItem> findByTitleContaining(String keywords, Pageable page);
	List<DjNaviItem> findByTitleAndCategoryId(String title,long categoryId);
}
