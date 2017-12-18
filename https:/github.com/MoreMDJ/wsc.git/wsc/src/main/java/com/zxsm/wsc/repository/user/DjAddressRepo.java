package com.zxsm.wsc.repository.user;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjAddress;

/**
 * BmAddress 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjAddressRepo extends PagingAndSortingRepository<DjAddress, Long>,JpaSpecificationExecutor<DjAddress> 
{ 
	List<DjAddress> findByUid(Long userId, Sort sort);
	Integer countByUid(Long userId);
	DjAddress findFirstByUidAndIsDefaultTrue(Long uid);
	
	DjAddress findFirstByUidAndId(Long uid,Long id);
}
