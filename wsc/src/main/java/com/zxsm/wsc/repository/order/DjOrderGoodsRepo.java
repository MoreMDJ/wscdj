package com.zxsm.wsc.repository.order;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.order.DjOrderGoods;

/**
 * DjOrderGoods 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjOrderGoodsRepo extends PagingAndSortingRepository<DjOrderGoods, Long>,JpaSpecificationExecutor<DjOrderGoods> 
{
	@Query(value = "SELECT sum(og.quantity) FROM dj_order_goods og WHERE og.activity_id IN ?1 AND og.goods_id=?2",nativeQuery = true)
	Integer limitNumberOfActivity(Long[] ids,Long gid);
	
	@Query(value = "SELECT sum(og.quantity) FROM dj_order_goods og WHERE og.activity_id = ?1 AND og.goods_id=?2",nativeQuery = true)
	Integer limitNumberOfActivity(Long aid,Long gid);
	
	@Query(value = "SELECT o.goods_id FROM dj_order_goods o WHERE o.activity_id = ?1 GROUP BY o.goods_id",nativeQuery = true)
	Long[] limitGidOfActivity(Long aid);
	
	@Query(value = "SELECT o.goods_id FROM dj_order_goods o WHERE o.roder_id = ?1 GROUP BY o.goods_id",nativeQuery = true)
	Long[] gidByOrderId(Long oid);
}
