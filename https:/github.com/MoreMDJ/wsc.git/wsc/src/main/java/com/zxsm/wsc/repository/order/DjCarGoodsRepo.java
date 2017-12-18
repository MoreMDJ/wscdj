package com.zxsm.wsc.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.order.DjCartGoods;

/**
 * DjCarGoods 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjCarGoodsRepo extends PagingAndSortingRepository<DjCartGoods, Long>,JpaSpecificationExecutor<DjCartGoods> 
{
	@Query(value = "SELECT new com.zxsm.wsc.entity.order.DjCartGoods(g.id, g.title, g.imgUrl, 1, g.leftNumber,g.marketPrice,g.salePrice,g.specialPrice, g.saleType, true, g.type) FROM DjGoods g WHERE g.id=?1 and g.status=1")
	DjCartGoods findCartGoodsByGId(long gid);
	
	/**
	 * 最新购物车
	 * @param uid
	 * @return
	 */
	@Query(value = "select c.id id, c.uid uid, g.id gid, g.title g_title, g.img_url g_img_url, c.quantity quantity, g.left_number left_quantity,g.market_price market_price,g.sale_price sale_price,g.special_price special_price, g.sale_Type sale_type, true is_selected ,c.init_date init_date, c.modify_date modify_date,0 price,g.type type,g.post_fee express_fee FROM dj_cart_goods c INNER JOIN dj_goods g ON g.id=c.gid WHERE c.uid=?1 and c.type=?2",nativeQuery = true)
	List<DjCartGoods> updateByUid(Long uid,Integer type);
	
	/**
	 * 返回最新购物车信息
	 * @param ids 购物车id
	 * @param uid 用户id
	 * @return
	 */
	@Query(value = "select c.id id, c.uid uid, g.id gid, g.title g_title, g.img_url g_img_url, c.quantity quantity, g.left_number left_quantity,g.market_price market_price,g.sale_price sale_price,g.special_price special_price, g.sale_Type sale_type, true is_selected ,c.init_date init_date, c.modify_date modify_date,0 price,g.type type,g.post_fee express_fee FROM dj_cart_goods c INNER JOIN dj_goods g ON g.id=c.gid WHERE c.id in ?1 and c.uid=?2",nativeQuery = true)
	List<DjCartGoods> updateByIdsAndUid(Long[] ids,Long uid);
	
	@Query(value = "select c.id id, c.uid uid, g.id gid, g.title g_title, g.img_url g_img_url, c.quantity quantity, g.left_number left_quantity,g.market_price market_price,g.sale_price sale_price,g.special_price special_price, g.sale_Type sale_type, true is_selected ,c.init_date init_date, c.modify_date modify_date,0 price,g.post_fee express_fee FROM dj_cart_goods c INNER JOIN dj_goods g ON g.id=c.gid WHERE c.id in ?1",nativeQuery = true)
	List<DjCartGoods> updateByIds(Long[] ids);
	
	List<DjCartGoods> findByUid(Long uid);
	
	DjCartGoods findTop1ByGidAndUid(Long gid,Long uid);
	
	List<DjCartGoods> findByIdInAndUid(Long[] ids,Long uid);
	
	List<Long> findGidByUid(Long uid);
	
	Long[] findGidByIdIn(Long[] ids);
}
