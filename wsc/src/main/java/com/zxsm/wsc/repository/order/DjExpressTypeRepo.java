package com.zxsm.wsc.repository.order;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.order.DjExpressType;

/**
 * BmExpressType 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjExpressTypeRepo extends PagingAndSortingRepository<DjExpressType, Long>, JpaSpecificationExecutor<DjExpressType> 
{
    Page<DjExpressType> findByTitleContaining(String keywords, Pageable page);
    
    DjExpressType findFirstByTitleAndIsEnableTrue(String title);
    
    List<DjExpressType> findByIsEnableTrue(Sort sort);
}
