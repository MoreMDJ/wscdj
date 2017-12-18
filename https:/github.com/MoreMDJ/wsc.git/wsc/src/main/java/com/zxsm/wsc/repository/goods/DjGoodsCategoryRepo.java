package com.zxsm.wsc.repository.goods;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.goods.DjGoodsCategory;

/**
 * DjGoodsCategory 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjGoodsCategoryRepo extends PagingAndSortingRepository<DjGoodsCategory, Long>, JpaSpecificationExecutor<DjGoodsCategory> 
{
    List<DjGoodsCategory> findByTypeIdAndParentIdIsNullOrderBySortIdAsc(Integer typeId);
    List<DjGoodsCategory> findByParentIdIsNullOrderBySortIdAsc();
    List<DjGoodsCategory> findByTypeIdAndParentIdOrderBySortIdAsc(Integer typeId, Long parentId);
    List<DjGoodsCategory> findByParentIdOrderBySortIdAsc(Long parentId);
    DjGoodsCategory findTop1ByTitle(String title);
    DjGoodsCategory findByTitleAndIdNot(String title, Long id);
    
    List<DjGoodsCategory> findByIsEnableAndIsGuideOrderBySortId(Boolean isEnable,Boolean isGuide);
    
    List<DjGoodsCategory> findByParentIdIsNullAndIsEnableOrderBySortIdAsc(Boolean isEnable);
    
    List<DjGoodsCategory> findByIsEnableAndParentIdOrderBySortIdAsc(Boolean isEnable, Long parentId);
}
