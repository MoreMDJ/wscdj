package com.zxsm.wsc.repository.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.order.DjOrder;


/**
 * DjOrder 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjOrderRepo extends PagingAndSortingRepository<DjOrder, Long>, JpaSpecificationExecutor<DjOrder>
{

	Page<DjOrder> findByUsernameOrderByIdDesc(String username, Pageable page);

	List<DjOrder> findByOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(Date begin, Date end);
	
	Page<DjOrder> findByUsernameAndOrderTimeAfterOrderByIdDesc(String username, Date time, Pageable page);

	Page<DjOrder> findByIdInOrderByIdDesc(List<Long> orderids, Pageable page);

	// 根据用户名查找所有的订单
	List<DjOrder> findByUsernameOrderByIdDesc(String username);
	
	DjOrder findByOrderNo(String orderNo);

	/**
	 * 根据城市名称和订单时间查询订单
	 * @return
	 */
	List<DjOrder> findByCityAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(String city,Date begin, Date end);
}
