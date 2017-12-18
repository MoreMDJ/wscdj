package com.zxsm.wsc.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjUComment;

/**
 * BmUserComment 实体数据库操作接口
 * 
 * @author maeing
 *
 */

public interface DjUCommentRepo extends PagingAndSortingRepository<DjUComment, Long>, JpaSpecificationExecutor<DjUComment> 
{ 
	Integer countByUid(Long uid);
	
	List<DjUComment> findByUid(Long uid, Sort sort);
	
	List<DjUComment> findByTypeAndGid(Integer type, Long gid, Sort sort);
	
	List<DjUComment> findByGidAndStatus(Long gid,Integer status);
	
	List<DjUComment> findByGid(Long gid);
	
	Page<DjUComment> findByGid(Long gid, Pageable page);
	
}
