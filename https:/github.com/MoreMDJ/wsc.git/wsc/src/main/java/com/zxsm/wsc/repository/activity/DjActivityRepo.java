package com.zxsm.wsc.repository.activity;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.activity.DjActivity;


public interface DjActivityRepo extends PagingAndSortingRepository<DjActivity, Long>, JpaSpecificationExecutor<DjActivity> 
{
	List<DjActivity> findBybeginTimeAfterAndEndTimeBefore(Date date1,Date date2);
	
	@Query("SELECT c.id FROM DjActivity c WHERE c.beginTime < ?1 AND c.endTime > ?2 ORDER BY c.endTime ASC")
	List<Long> findIdBybeginTimeBeforeAndEndTimeAfter(Date date1,Date date2);
}
