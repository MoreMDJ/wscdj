package com.zxsm.wsc.repository.user;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.entity.user.DjUserAndRelation;


public interface DjUserRepo extends JpaSpecificationExecutor<DjUser>,PagingAndSortingRepository<DjUser, Long>
{

	DjUser findByUsernameAndStatus(String username,Integer status);

	DjUser findByUsername(String username);
	
	DjUser findByUsernameAndPasswordAndStatus(String username,String password,Integer status);

	DjUser findByUsernameAndIdNot(String username, Long id);

	Page<DjUser> findByUsernameContainingOrRealNameContainingOrderByIdDesc(String keywords1, String keywords2,Pageable page);
	
	List<DjUser> findByUsernameIn(List<String> username);
	
	List<DjUser> findByIdInAndStatus(List<Long> ids,Integer status);
	
	List<DjUser> findByIdInAndStatusAndURole(List<Long> ids,Integer status,Integer uRole);
	
	Page<DjUser> findByIdInAndStatus(List<Long> ids,Integer status,Pageable page);
	
	DjUser findByOpenidAndStatus(String openid,Integer status);
	
	DjUser findByOpenid(String openid);

	/**
	 * 根据真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	List<DjUser> findByRealName(String realName);
	
	@Modifying
	@Query("update DjUser d set d.sex=?2,d.realName=?3, d.nickname=?4,d.headImage=?5,d.email=?6,d.birthday=?7 where id=?8")
	int updataUser(Integer sex,String realName,String nickname,String headImage,String email,Date Birthday,Long id);
	
	@Query(value = "select u.username as username,u.mobile as mobile,u.status as status,u.real_name as realName,u.nickname as nickname,u.head_image as headImage,u.last_login_time as lastLoginTime,r.pid as pid,r.enable as enable) from Dj_User as u left join Dj_User_Relations as r on r.uid = u.id order by u.initDate DESC limit ?1 offset ?2",nativeQuery = true)
	List<DjUserAndRelation> findUserAndRelations(Integer size,Integer offset);
	
	Page<DjUser> findByUType(Integer uType,Pageable page);
	
	Page<DjUser> findByUTypeAndRealNameContaining(Integer uType,String keywords,Pageable page);
	
}
