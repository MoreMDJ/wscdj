package com.zxsm.wsc.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjMessage;


public interface DjMessageRepo extends JpaSpecificationExecutor<DjMessage>,PagingAndSortingRepository<DjMessage, Long>
{
	
	Integer countByUidAndTypeAndIsRead(Long uid,Integer type,Boolean isRead);
	
	Integer countByUidAndIsRead(Long uid, Boolean isRead);
	
	List<DjMessage> findByUidAndTypeOrderByIsReadAsc(Long uid,Integer type);
}
