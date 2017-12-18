package com.zxsm.wsc.service.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.entity.management.DjNavigationMenu;
import com.zxsm.wsc.repository.management.DjNavigationMenuRepo;

/**
 * DjNavigationMenu 服务类
 * 
 * @author maeing
 *
 */

@Service
@Transactional
public class DjNavigationMenuService {

	@Autowired
	DjNavigationMenuRepo repository;

	/**
	 * 查找顶级菜单项
	 * 
	 * @param parentId 父菜单ID，为0时代报顶层菜单
	 * @return
	 */
	public List<DjNavigationMenu> findRootMenuEnabled()
	{
		return repository.findByParentIdAndIsEnableTrueOrderBySortIdAsc(0L);
	}
	
	public List<DjNavigationMenu> findRootMenu()
	{
		return repository.findByParentIdOrderBySortIdAsc(0L);
	}
	
	public List<DjNavigationMenu> findAllMenuEnabled()
	{
		List<DjNavigationMenu> rootList = this.findRootMenuEnabled();
		List<DjNavigationMenu> resList = new ArrayList<DjNavigationMenu>();
		resList.addAll(rootList);
		
		if (rootList.size() > 0)
		{
			for (int idx=0; idx < rootList.size(); idx++)
			{
				int index = resList.indexOf(rootList.get(idx));
				resList.addAll(index + 1, this.findChildMenuEnabledRecursive(rootList.get(idx).getId()));
			}
		}
		return resList;
	}
	
	public List<DjNavigationMenu> findAllMenu()
	{
		List<DjNavigationMenu> rootList = this.findRootMenu();
		List<DjNavigationMenu> resList = new ArrayList<DjNavigationMenu>();
		resList.addAll(rootList);
		
		if (rootList.size() > 0)
		{
			for (int idx=0; idx < rootList.size(); idx++)
			{
				int index = resList.indexOf(rootList.get(idx));
				resList.addAll(index + 1, this.findChildMenuRecursive(rootList.get(idx).getId()));
			}
		}
		return resList;
	}
	
	public List<DjNavigationMenu> findChildMenuEnabled(Long parentId)
	{
		return repository.findByParentIdAndIsEnableTrueOrderBySortIdAsc(parentId);
	}
	
	public DjNavigationMenu findMenu(long id)
	{
		return repository.findOne(id);
	}
	
	public void deleteMenu(long id)
	{
		repository.delete(id);
	}
	
	public DjNavigationMenu saveMenu(DjNavigationMenu e)
	{
		if (null == e)
		{
			return null;
		}
		
		// 设置layerId
		if (null == e.getParentId())
		{
			e.setLayerId(1);
		}
		else
		{
			DjNavigationMenu menu = repository.findOne(e.getParentId());
			
			if (null != menu && null != menu.getLayerId())
			{
				e.setLayerId(menu.getLayerId() + 1);
			}
			else
			{
				e.setLayerId(1);
			}
		}
		
		return repository.save(e);
	}
	
	public Map<String,String> navigationNameCheck(String name,String oldName)
	{
		
		if(name == null)
			return DjTools.mapN("该导航名不能为空！");
		
		List<DjNavigationMenu> navNames = repository.findByName(name);
		if(StringUtils.isBlank(oldName))
		{
			if(navNames == null || navNames.size() < 1)
				return DjTools.mapY("该导航别名可使用");
			else
				return DjTools.mapN("该导航别名系统已使用，请更换！");
		}
		
		else
		{
			List<DjNavigationMenu> oldNavNames = repository.findByName(oldName);
			
			if(oldNavNames != null && oldNavNames.size() > 1)
				return DjTools.mapN("该导航别使用重复，请更换！");
			else
			{
				if(navNames == null || navNames.size() < 2)
					return DjTools.mapY("该导航别名可使用");
				else
					return DjTools.mapN("该导航别名系统已使用，请更换！");
			}
		}
		
	}

	// 递归查找菜单项
	private List<DjNavigationMenu> findChildMenuRecursive(Long parentId)
	{
		// 参数检查由调用函数决定
		List<DjNavigationMenu> childList = repository.findByParentIdOrderBySortIdAsc(parentId);
		List<DjNavigationMenu> resList = new ArrayList<DjNavigationMenu>();
		resList.addAll(childList);
		
		if (childList.size() > 0)
		{
			for (int idx=0; idx < childList.size(); idx++)
			{
				List<DjNavigationMenu> grandChildList = findChildMenuRecursive(childList.get(idx).getId());
				if (grandChildList.size() > 0)
				{
					int index = resList.indexOf(childList.get(idx));
					resList.addAll(index + 1, grandChildList);
				}
			}
		}
		
		return resList;
	}
	
	private List<DjNavigationMenu> findChildMenuEnabledRecursive(Long parentId)
	{
		// 参数检查由调用函数决定
		List<DjNavigationMenu> childList = repository.findByParentIdAndIsEnableTrueOrderBySortIdAsc(parentId);
		List<DjNavigationMenu> resList = new ArrayList<DjNavigationMenu>();
		resList.addAll(childList);
		
		if (childList.size() > 0)
		{
			for (int idx=0; idx < childList.size(); idx++)
			{
				List<DjNavigationMenu> grandChildList = findChildMenuEnabledRecursive(childList.get(idx).getId());
				if (grandChildList.size() > 0)
				{
					int index = resList.indexOf(childList.get(idx));
					resList.addAll(index + 1, grandChildList);
				}
			}
		}
		
		return resList;
	}
}
