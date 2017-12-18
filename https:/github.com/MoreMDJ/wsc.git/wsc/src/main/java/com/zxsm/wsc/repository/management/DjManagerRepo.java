package com.zxsm.wsc.repository.management;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.management.DjManager;

/**
 * TdManager 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjManagerRepo extends PagingAndSortingRepository<DjManager, Long>, JpaSpecificationExecutor<DjManager> 
{
	DjManager findFirstByUsernameAndIsEnableTrue(String username);
	
	DjManager findByUsername(String username);
}
