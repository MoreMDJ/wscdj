package com.zxsm.wsc.repository.goods;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.goods.DjGoods;

/**
 * DjGoods 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjGoodsRepo extends PagingAndSortingRepository<DjGoods, Long>, JpaSpecificationExecutor<DjGoods>
{
	Page<DjGoods> findByCategoryIdTreeContaining(String categoryId, Pageable page);
	Page<DjGoods> findByCategoryIdAndTitleContaining(long categoryId, String keywords, Pageable page);
	Page<DjGoods> findByTitleContaining(String keywords, Pageable page);
	
	List<DjGoods> findByGoodsNo(String goodsNo);
	
	List<DjGoods> findBySku(String sku);
	
	List<DjGoods> findByCategoryIdOrderBySortIdAsc(Long CategoryId);
	
	/**
	 * 
	 */
	List<DjGoods> findByCategoryIdIsNull();

	Page<DjGoods> findByCategoryIdTreeContainingOrderBySortIdAsc(String catId, Pageable page);

	Page<DjGoods> findByCategoryIdIsNullOrderBySortIdAsc(Pageable page);
	
	List<DjGoods> findByIdIn(List<Long> gids, Sort sort);
	
	List<DjGoods> findByIdIn(Long[] ids);
	
	@Modifying
	@Query("update DjGoods set viewCount=viewCount + 1, click = click + 1 where id=?1")
	public int updataView(Long id);
}