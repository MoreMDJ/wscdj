package com.zxsm.wsc.repository.goods;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.goods.DjParameterCategory;

/**
 * DjParameterCategory 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjParameterCategoryRepo extends
		PagingAndSortingRepository<DjParameterCategory, Long>,
		JpaSpecificationExecutor<DjParameterCategory> 
{
	// 通过父类型查找
    List<DjParameterCategory> findByParentIdOrderBySortIdAsc(Long parentId);
    List<DjParameterCategory> findByParentIdIsNullOrderBySortIdAsc();
}
