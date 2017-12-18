package com.zxsm.wsc.service.management;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.DjEnums;
import com.zxsm.wsc.entity.management.DjManager;
import com.zxsm.wsc.entity.management.DjManagerRole;
import com.zxsm.wsc.entity.management.DjPermission;
import com.zxsm.wsc.repository.management.DjManagerRepo;
import com.zxsm.wsc.repository.management.DjManagerRoleRepo;
import com.zxsm.wsc.repository.management.DjPermissionRepo;
/**
 * DjManager 服务类
 * 
 * @author maeing
 *
 */

@Service
@Transactional
public class DjManagerService
{

	@Autowired
	DjManagerRoleRepo roleRepo;
	
	@Autowired
	DjManagerRepo managerRepo;
	
	@Autowired
	DjPermissionRepo permissionRepo;
	
	public DjPermission savePermission(DjPermission e)
	{
		return permissionRepo.save(e);
	}
	
	public Page<DjManager> findManagerAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return managerRepo.findAll(pageRequest);
	}

	/**
	 * 分页查找所有角色，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjManagerRole> findRoleAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return roleRepo.findAll(pageRequest);
	}
	
	public List<DjManagerRole> findRoleAll()
	{
		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id"));
		return (List<DjManagerRole>) roleRepo.findAll(sort);
	}
	
	public DjManagerRole findRole(Long roleId)
	{
		return roleRepo.findOne(roleId);
	}
	
	/**
	 * 搜索角色
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjManagerRole> searchRoleAll(String keywords, int page, int size) {

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return roleRepo.findByTitleContaining(keywords, pageRequest);
	}

	/**
	 * 按分类查找角色
	 * 
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjManagerRole> findRoleByCategoryId(long categoryId, int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		
		return roleRepo.findByCategoryId(categoryId, pageRequest);
	}
	
	/**
	 * 分类搜索角色
	 * @param categoryId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjManagerRole> searchRoleByCategoryId(long categoryId,String keywords, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return roleRepo.findByCategoryIdAndTitleContaining(categoryId,keywords, pageRequest);
	}

	
	/**
	 * 保存角色
	 * 
	 * @param e : 要保存的角色
	 * @return
	 */
	public DjManagerRole saveRole(DjManagerRole e)
	{
		if (null == e)
		{
			return null;
		}
		return roleRepo.save(e);
	}
	
	public Boolean Exists(Long roleId,String navName,DjEnums.ActionEnum actionType)
	{
		if(StringUtils.isBlank(actionType.toString()))
			return false;
		
		List<DjPermission> permissions = permissionRepo.exists(roleId, navName, "%" + actionType + "%");
		if(permissions == null)
			return false;
		else if (permissions.size() > 1)
			return false;
		else if (permissions.size() == 0)
			return false;
		else
			return true;
	}
	
	
	public DjManager saveManager(DjManager e)
	{
		if (null == e)
		{
			return null;
		}
		
		if (null != e.getRoleId())
		{
			DjManagerRole role = roleRepo.findOne(e.getRoleId());
			
			if (null != role)
			{
				e.setRoleTitle(role.getTitle());
			}
		}
		Date date = new Date();
		if(e.getInitDate() == null)
			e.setInitDate(date);
		
		e.setModifyDate(date);
		
		return managerRepo.save(e);
	}

	/**
	 * 删除角色，参数检查由调用函数完成
	 * 
	 * @param id : 品牌ID
	 */
	public void deleteRole(long id)
	{
		roleRepo.delete(id);
	}
	
	public void deleteManager(long id)
	{
		managerRepo.delete(id);
	}

	/**
	 * 查找角色
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public DjManagerRole findRole(long id)
	{
		return roleRepo.findOne(id);
	}
	
	public DjManager findManager(long id)
	{
		return managerRepo.findOne(id);
	}
	
	public DjManager findManagerByUsernameAndIsEnabledTrue(String username)
	{
		return managerRepo.findFirstByUsernameAndIsEnableTrue(username);
	}
	
	public DjManager findByUsername(String username)
	{
		if(StringUtils.isBlank(username))
			return null;
		return managerRepo.findByUsername(username);
	}
	
	/**
	 *  methods
	 */
	
	
	/**
	 * login
	 */
	public void managerLogin(DjManager manager,String ip)
	{
		manager.setIp(ip);
		manager.setLastLoginIp(manager.getIp());
		manager.setLastLoginTime(manager.getLoginTime());
		manager.setLoginTime(new Date());
		managerRepo.save(manager);
	}
	
}
