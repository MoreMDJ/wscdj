package com.zxsm.wsc.repository.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjUCash;

/**
 * DjUCash 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjUCashRepo extends
		PagingAndSortingRepository<DjUCash, Long>,
		JpaSpecificationExecutor<DjUCash> 
{ 
	@Query("SELECT SUM(p.cash) FROM DjUCash p WHERE p.cash IS NOT NULL AND p.uid=?1")
	BigDecimal countCashByUserId(Long userId);
	
	@Query("SELECT SUM(p.cash) FROM DjUCash p WHERE p.cash IS NOT NULL AND p.cash>0 AND p.uid=?1")
	BigDecimal countCashByUserIdAndPointGreaterThanZero(Long userId);
	
	@Query("SELECT SUM(p.cash) FROM DjUCash p WHERE p.cash IS NOT NULL AND p.cash<0 AND p.uid=?1")
	BigDecimal countCashByUserIdAndPointLessThanZero(Long userId);
	
	List<DjUCash> findByUid(Long userId, Sort sort);
	
	DjUCash findByUidAndOrderNumber(Long uid,String orderNo);
	
	List<DjUCash> findByCashTimeBetweenAndUid(Date start, Date end,Long uid);
}
