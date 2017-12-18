package com.zxsm.wsc.repository.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.order.DjPayType;

/**
 * BmPayType 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjPayTypeRepo extends PagingAndSortingRepository<DjPayType, Long>, JpaSpecificationExecutor<DjPayType> 
{
	Page<DjPayType> findByTitleContaining(String keywords, Pageable page);
	
	List<DjPayType> findByIsEnable(Boolean isEnable);
	
	List<DjPayType> findByIsEnableOrderBySortId(Boolean isEnable);
	
	List<DjPayType> findByIsOnlinePayFalseAndIsEnableTrue();
	
	DjPayType findFirstByCodeAndIsEnableTrue(String code);
	
}
