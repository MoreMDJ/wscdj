package com.zxsm.wsc.repository.ad;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.ad.DjAd;

/**
 * DjAd 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjAdRepo extends PagingAndSortingRepository<DjAd, Long>, JpaSpecificationExecutor<DjAd> 
{
	Page<DjAd> findByCategoryId(long categoryId, Pageable page);
	Page<DjAd> findByCategoryTitleAndStatusId(String title, int statusId, Pageable page);
	Page<DjAd> findByTitleContaining(String keywords, Pageable page);
	Page<DjAd> findByCategoryIdAndTitleContaining(long categoryId, String keywords, Pageable page);
	
	List<DjAd> findByCategoryTitleAndStatusIdOrderBySortIdAsc(String cTitle,Integer statusId);
}
