package com.zxsm.wsc.repository.goods;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.goods.DjGoodsParameter;

/**
 * DjGoodsParameter 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjGoodsParameterRepo extends
		PagingAndSortingRepository<DjGoodsParameter, Long>,
		JpaSpecificationExecutor<DjGoodsParameter> 
{
}
