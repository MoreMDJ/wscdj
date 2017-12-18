package com.zxsm.wsc.repository.management;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.management.DjManagerRole;

/**
 * BmManagerRole 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjManagerRoleRepo extends PagingAndSortingRepository<DjManagerRole, Long>, JpaSpecificationExecutor<DjManagerRole> 
{
	Page<DjManagerRole> findByCategoryId(long categoryId, Pageable page);
	
	Page<DjManagerRole> findByCategoryIdAndTitleContaining(long categoryId, String keywords, Pageable page);
	
	Page<DjManagerRole> findByTitleContaining(String keywords, Pageable page);

}
