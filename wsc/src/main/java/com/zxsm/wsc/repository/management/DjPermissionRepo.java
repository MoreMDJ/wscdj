package com.zxsm.wsc.repository.management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.management.DjPermission;

public interface DjPermissionRepo extends PagingAndSortingRepository<DjPermission, Long>, JpaSpecificationExecutor<DjPermission>
{
//	List<DjPermission> findByActionTypeContainingAndNavName(String navName,String actionType);
	@Query(value = "select * from dj_permission where role_id= ?1 and nav_name = ?2 and action_type like ?3",nativeQuery = true)
	List<DjPermission> exists(Long roleId,String navName,String actionType);
}
