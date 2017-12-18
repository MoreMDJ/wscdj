package com.zxsm.wsc.repository.user;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjUserRelations;

/**
 * DjUserRelations 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjUserRelationsRepo extends PagingAndSortingRepository<DjUserRelations, Long>, JpaSpecificationExecutor<DjUserRelations> 
{
	DjUserRelations findTop1ByUidAndEnableTrue(Long uid);
	
	DjUserRelations findTop1ByUid(Long uid);
	
	@Query("select r.uid from DjUserRelations r where pid = ?1 and enable=true")
	List<Long> findUidByPidAndEnableTrue(Long pid);
	
	@Query("select r.uid from DjUserRelations r where pid = ?1 and enable=true and initDate > ?2 and initDate < ?3")
	List<Long> findUidByPidAndEnableAndTimeBetween(Long pid,Date startDate,Date endDate);
	
	@Query("select r.pid from DjUserRelations r where uid = ?1 and enable=true")
	Long findPidByUidAndEnableTrue(Long uid);
	
	@Modifying
	@Query("update DjUserRelations r set r.level=?2 where r.pid=?1")
	void changeLevelByPid(Long pid,Integer level);
}
