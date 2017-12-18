package com.zxsm.wsc.repository.user;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjUPoint;

/**
 * DjUPoint 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjUPointRepo extends
		PagingAndSortingRepository<DjUPoint, Long>,
		JpaSpecificationExecutor<DjUPoint> 
{ 
	@Query("SELECT SUM(p.point) FROM DjUPoint p WHERE p.point IS NOT NULL AND p.uid=?1")
	Integer countPointsByUserId(Long userId);
	
	@Query("SELECT SUM(p.point) FROM DjUPoint p WHERE p.point IS NOT NULL AND p.point>0 AND p.uid=?1")
	Integer countPointsByUserIdAndPointGreaterThanZero(Long userId);
	
	@Query("SELECT SUM(p.point) FROM DjUPoint p WHERE p.point IS NOT NULL AND p.point<0 AND p.uid=?1")
	Integer countPointsByUserIdAndPointLessThanZero(Long userId);
	
	List<DjUPoint> findByUid(Long userId, Sort sort);
	
	List<DjUPoint> findByPointTimeBetweenAndUid(Date start, Date end,Long uid);
}
