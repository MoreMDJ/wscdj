package com.zxsm.wsc.repository.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.article.DjArticleCategory;

/**
 * DjArticleCategory 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjArticleCategoryRepo extends PagingAndSortingRepository<DjArticleCategory, Long>, JpaSpecificationExecutor<DjArticleCategory> 
{
    // 通过父类型查找
    List<DjArticleCategory> findByParentIdOrderBySortIdAsc(Long parentId);
    List<DjArticleCategory> findByParentIdIsNullOrderBySortIdAsc();
    DjArticleCategory findTop1ByTitle(String title);
}
