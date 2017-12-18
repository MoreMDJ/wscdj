package com.zxsm.wsc.repository.health;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.health.DjHealth;

/**
 * DjGoods 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjHealthRepo extends PagingAndSortingRepository<DjHealth, Long>, JpaSpecificationExecutor<DjHealth>
{
	
//	@Modifying
//	@Query("update DjGoods set viewCount=viewCount + 1, click = click + 1 where id=?1")
//	public int updataView(Long id);
}