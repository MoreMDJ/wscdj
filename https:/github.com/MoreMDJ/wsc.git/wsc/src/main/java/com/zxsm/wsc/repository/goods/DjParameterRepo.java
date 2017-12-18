package com.zxsm.wsc.repository.goods;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.goods.DjParameter;

/**
 * DjParameter 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjParameterRepo extends
		PagingAndSortingRepository<DjParameter, Long>,
		JpaSpecificationExecutor<DjParameter> 
{
	Page<DjParameter> findByCategoryId(long categoryId, Pageable page);
	Page<DjParameter> findByCategoryTreeContaining(String categoryId, Pageable page);
	List<DjParameter> findByCategoryTreeContainingOrderBySortIdAsc(String categoryId);
	Page<DjParameter> findByCategoryIdAndTitleContaining(long categoryId, String keywords, Pageable page);
	Page<DjParameter> findByTitleContaining(String keywords, Pageable page);
	
	int countByCategoryTreeContaining(String categoryId);
}
