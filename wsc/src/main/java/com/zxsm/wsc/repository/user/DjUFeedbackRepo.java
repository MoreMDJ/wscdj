package com.zxsm.wsc.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjUFeedback;

/**
 * BmFeedback 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjUFeedbackRepo extends
		PagingAndSortingRepository<DjUFeedback, Long>,
		JpaSpecificationExecutor<DjUFeedback> 
{ 
	Page<DjUFeedback> findByMobileContaining(String mobile, Pageable page);
}
