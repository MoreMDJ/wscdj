package com.zxsm.wsc.repository.article;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.article.DjArticle;


/**
 * DjArticle 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface DjArticleRepo extends PagingAndSortingRepository<DjArticle, Long>, JpaSpecificationExecutor<DjArticle> 
{
	Page<DjArticle> findByCategoryId(long categoryId, Pageable page);
	Page<DjArticle> findByCategoryIdAndTitleContaining(long categoryId, String keywords, Pageable page);
	Page<DjArticle> findByTitleContaining(String keywords, Pageable page);
	DjArticle findFirstByTitleAndStatusId(String title, Integer statusId);
	
	List<DjArticle> findByCategoryTitleAndStatusIdOrderBySortIdAsc(String cateTitle,Integer statusId);
}
