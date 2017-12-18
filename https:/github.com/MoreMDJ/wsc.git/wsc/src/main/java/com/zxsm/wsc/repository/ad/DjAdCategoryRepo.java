package com.zxsm.wsc.repository.ad;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.ad.DjAdCategory;

/**
 * DjAdCategory 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjAdCategoryRepo extends PagingAndSortingRepository<DjAdCategory, Long>, JpaSpecificationExecutor<DjAdCategory> 
{
    DjAdCategory findByTitle(String title);
}
