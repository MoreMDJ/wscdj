package com.zxsm.wsc.repository.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.activity.DjUserLimitGoods;


public interface DjUserLimitGoodsRepo extends PagingAndSortingRepository<DjUserLimitGoods, Long>, JpaSpecificationExecutor<DjUserLimitGoods> 
{
	
	@Query(value="SELECT c.number FROM Dj_User_Limit_Goods c WHERE c.acti_Id IN ?1 AND c.uid = ?2 AND c.gid= ?3",nativeQuery = true)
	Integer findBuyQuantity(Long[] aid,Long uid,Long gid);
	
	
	@Query(value="SELECT COUNT(1) FROM Dj_User_Limit_Goods u WHERE u.acti_Id = ?1 AND u.gid in ?2 AND u.uid = ?3",nativeQuery = true)
	Integer findBuyCatQuantity();
	
	@Query(value="SELECT u.gid FROM Dj_User_Limit_Goods u WHERE u.uid = ?1 AND u.acti_Id = ?2 GROUP BY u.gid",nativeQuery = true)
	Long[] findGidByUidAndActivityId(Long uid,Long aid);
	
}
