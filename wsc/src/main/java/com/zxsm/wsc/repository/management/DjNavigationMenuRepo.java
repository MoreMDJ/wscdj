package com.zxsm.wsc.repository.management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.management.DjNavigationMenu;

/**
 * DjNavigationMenu 实体数据库操作接口
 * 
 * @author maeing
 *
 */
public interface DjNavigationMenuRepo extends PagingAndSortingRepository<DjNavigationMenu, Long>, JpaSpecificationExecutor<DjNavigationMenu> 
{
    List<DjNavigationMenu> findByParentIdAndIsEnableTrueOrderBySortIdAsc(Long parentId);
    
    List<DjNavigationMenu> findByIsEnableTrueOrderBySortIdAsc();
    
    List<DjNavigationMenu> findByParentIdOrderBySortIdAsc(Long parentId);
    
    List<DjNavigationMenu> findByName(String name);
}
