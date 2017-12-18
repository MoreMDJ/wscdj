package com.zxsm.wsc.repository.management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.management.DjSystemConfig;

/**
 * TdManager 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjSystemConfigRepo extends PagingAndSortingRepository<DjSystemConfig, Long>, JpaSpecificationExecutor<DjSystemConfig> 
{
	DjSystemConfig findByConfigKey(String key);
	
	List<DjSystemConfig> findByConfigKeyContaining(String keycontain);
}
