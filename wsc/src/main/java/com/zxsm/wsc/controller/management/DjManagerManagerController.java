package com.zxsm.wsc.controller.management;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.DjEnums;
import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.entity.management.DjManager;
import com.zxsm.wsc.entity.management.DjManagerRole;
import com.zxsm.wsc.service.management.DjManagerService;
import com.zxsm.wsc.service.management.DjNavigationMenuService;

/**
 * 后台品牌管理控制器
 * 
 * @author Shawn
 */

@Controller
@RequestMapping(value = "/management/manager")
public class DjManagerManagerController extends DjAdminLevel
{

	@Autowired
	DjManagerService djManagerService;

	@Autowired
	DjNavigationMenuService djNavigationMenuService;
	
	private static final String kNavName = "channel_manager_list";

	@RequestMapping(value = "/list")
	public String manageList(Integer page,
			Integer size,
			String __EVENTTARGET,
			String __EVENTARGUMENT,
			String __VIEWSTATE,
			Long categoryId,
			Integer[] listChkIdx,
			Long[] listId,
			Long[] sortId,
			ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null == page || page < 0)
		{
			page = 0;
		}

		if (null == size || size <= 0)
		{
			size = DjKeys.pageSize;
		}

		// 保存排序号
		if ("btnSave".equalsIgnoreCase(__EVENTTARGET))
		{
			btnSaveManager(listId, sortId);
		}
		// 删除管理员
		if ("btnDelete".equalsIgnoreCase(__EVENTTARGET))
		{
			btnDeleteManager(listId, listChkIdx);
		}
		// 翻页
		else if ("btnPage".equalsIgnoreCase(__EVENTTARGET))
		{
			if (null == __EVENTARGUMENT || __EVENTARGUMENT.isEmpty())
			{
				__EVENTARGUMENT = "0";
			}

			page = Integer.parseInt(__EVENTARGUMENT);
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("categoryId", categoryId);

		map.addAttribute("manager_page", djManagerService.findManagerAll(page, size));
		if(CheckAdminLevel(kNavName, DjEnums.ActionEnum.View))
			return "/management/manager/manager_list";
		else
			return URL_NORight;
	}

	@RequestMapping(value = "/edit")
	public String managerEdit(Long id, String __EVENTTARGET,
			String __EVENTARGUMENT, String action, ModelMap map,
			HttpSession session) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != id)
		{
			if(!CheckAdminLevel(kNavName, DjEnums.ActionEnum.View))
				return URL_NORight;
			DjManager manager = djManagerService.findManager(id);
			map.addAttribute("djManager", manager);
		}
		
		map.addAttribute("role_list", djManagerService.findRoleAll());

		map.addAttribute("action", action);

		return "/management/manager/manager_edit";
	}

	@RequestMapping(value = "/save")
	public String categorySave(DjManager djManager, Long[] menuIds,
			String __VIEWSTATE, ModelMap map, HttpSession session) {
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		djManagerService.saveManager(djManager);

		return "redirect:/management/manager/list";
	}
	

	@RequestMapping(value = "/role/list")
	public String roleList(Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, String keywords,
			Long categoryId, Integer[] listChkIdx, Long[] listId,
			Long[] sortId, ModelMap map, HttpSession session)
	{
		
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null == page || page < 0)
		{
			page = 0;
		}

		if (null == size || size <= 0)
		{
			size = DjKeys.pageSize;
		}

		// 删除角色
		if ("btnDelete".equalsIgnoreCase(__EVENTTARGET))
		{
			btnDeleteRole(listId, listChkIdx);
		}
		// 翻页
		else if ("btnPage".equalsIgnoreCase(__EVENTTARGET))
		{
			if (null == __EVENTARGUMENT || __EVENTARGUMENT.isEmpty())
			{
				__EVENTARGUMENT = "0";
			}

			page = Integer.parseInt(__EVENTARGUMENT);
		}
		// 查找
		else if ("btnSearch".equalsIgnoreCase(__EVENTTARGET))
		{
			page = 0;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);

		if (null == categoryId)
		{
			if (null == keywords || keywords.isEmpty())
			{
				map.addAttribute("role_page",
						djManagerService.findRoleAll(page, size));
			}
			else
			{
				map.addAttribute("role_page",
						djManagerService.searchRoleAll(keywords, page, size));
			}
		}
		else
		{
			if (null == keywords || keywords.isEmpty())
			{
				map.addAttribute("role_page", djManagerService.findRoleByCategoryId(categoryId, page, size));
			}
			else
			{
				map.addAttribute("role_page", djManagerService.searchRoleByCategoryId(categoryId, keywords, page,size));
			}
		}
		return "/management/manager/manager_role_list";
	}

	/**
	 * 角色编辑
	 * @param id
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param action
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/role/edit")
	public String roleEdit(Long id, String __EVENTTARGET, String __EVENTARGUMENT, String action, ModelMap map,HttpSession session)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != id)
		{
			map.addAttribute("role", djManagerService.findRole(id));
		}

		map.addAttribute("navigation_menu_list",djNavigationMenuService.findAllMenuEnabled());

		map.addAttribute("action", action);
		
		map.addAttribute("actionType",DjTools.AuthorDictionary());

		return "/management/manager/manager_role_edit";
	}
	
	/**
	 * 角色保存
	 * @param role
	 * @param menuIds
	 * @param __VIEWSTATE
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/role/save")
	public String roleSave(DjManagerRole role, Long[] menuIds, String __VIEWSTATE, ModelMap map, HttpSession session)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		if (null != menuIds)
		{
			String permission = "";
			for (Long mid : menuIds)
			{
				permission += "[";
				permission += mid;
				permission += "],";
			}
			
			role.setPermission(permission);
		}
		
		djManagerService.saveRole(role);
		
		return "redirect:/management/manager/role/list";
	}


	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> validateForm(String param)
	{
		Map<String, String> res = new HashMap<String, String>();

		res.put("status", "n");

		if(!isManagerLogin())
		{
			res.put("info", "请重新登录");
			return res;
		}
		
		DjManager manager = getManagerInfo();
		
		if (null == manager)
		{
			res.put("info", "该管理员已被禁用");
			return res;
		}

		if (null == param || param.isEmpty()) {
			res.put("info", "该字段不能为空");
			return res;
		}
		
		res.put("info", "传入参数错误");
		
		return res;
	}
	
	@RequestMapping(value = "/password/save")
	@ResponseBody
    public Map<Object, Object> passwordSave(String newPassword, String __EVENTTARGET,
			String __EVENTARGUMENT, String action, ModelMap map,
			HttpSession session)
	{
		Map<Object, Object> res = new HashMap<Object, Object>();
    	
    	res.put("error", 1);
    	
        String username = (String) session.getAttribute("manager");
        
        if (null == username)
        {
        	res.put("msg", "请重新登录");
            return res;
        }

		DjManager manager = djManagerService
				.findManagerByUsernameAndIsEnabledTrue(username);
		
		if (null == manager)
		{
			res.put("msg", "该用户已被禁用");
            return res;
		}

		manager.setPassword(newPassword);
		
		djManagerService.saveManager(manager);
		
		res.put("error", 0);

		return res;
	}

	@RequestMapping(value = "/password")
	public String password(Long id, String __EVENTTARGET,
			String __EVENTARGUMENT, String action, ModelMap map,
			HttpSession session) {
		String username = (String) session.getAttribute("manager");

		if (null == username) {
			return "redirect:/management/login";
		}

		DjManager manager = djManagerService
				.findManagerByUsernameAndIsEnabledTrue(username);

		map.addAttribute("manager", manager);

		return "/management/manager_password";
	}
	
	@RequestMapping(value = "/paypassword")
	public String paypassword(Long id, String __EVENTTARGET,
			String __EVENTARGUMENT, String action, ModelMap map,
			HttpSession session) {
		String username = (String) session.getAttribute("manager");

		if (null == username) {
			return "redirect:/management/login";
		}

		DjManager manager = djManagerService
				.findManagerByUsernameAndIsEnabledTrue(username);

		map.addAttribute("manager", manager);

		return "/management/manager_pay_password";
	}


	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.POST)
	public String parameter(@PathVariable Long roleId, ModelMap map, HttpSession session)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		return "/management/manager_s_list";
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			ModelMap map)
	{
		if (null != id)
		{
			map.addAttribute("bmManager", djManagerService.findManager(id));
		}

	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteRole(Long[] ids, Integer[] chkIdx) {
		if (null == ids || null == chkIdx || ids.length < 1
				|| chkIdx.length < 1) {
			return;
		}

		for (int idx : chkIdx) {
			if (idx >= 0 && ids.length > idx) {
				djManagerService.deleteRole(ids[idx]);
			}
		}
	}

	private void btnSaveManager(Long[] ids, Long[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1
				|| sortIds.length < 1) {
			return;
		}

		for (int idx = 0; idx < ids.length; idx++) {
			if (sortIds.length > idx) {
				DjManager cat = djManagerService.findManager(ids[idx]);

				if (null != cat) {
					djManagerService.saveManager(cat);
				}
			}
		}
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteManager(Long[] ids, Integer[] chkIdx) {
		if (null == ids || null == chkIdx || ids.length < 1
				|| chkIdx.length < 1) {
			return;
		}

		for (int idx : chkIdx) {
			if (idx >= 0 && ids.length > idx) {
				djManagerService.deleteManager(ids[idx]);
			}
		}
	}
}
