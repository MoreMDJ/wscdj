package com.zxsm.wsc.controller.management;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.common.SerchTools.Criterion.Operator;
import com.zxsm.wsc.common.tencent.controller.DjUserQRcodeTools;
import com.zxsm.wsc.common.SerchTools.Search;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.entity.user.DjMessage;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.entity.user.DjUserRelations;
import com.zxsm.wsc.service.user.DjMessageService;
import com.zxsm.wsc.service.user.DjUserRelationsService;
import com.zxsm.wsc.service.user.DjUserService;

/**
 * 后台用户管理控制器
 * 
 * @author maeing
 */

@Controller
@RequestMapping(value = "/management/user")
public class DjManagerUserController extends DjAdminLevel
{

	@Autowired
	private DjUserService userSvs;
	
	@Autowired
	private DjUserRelationsService relationSvs;
	
	@Autowired
	DjMessageService msgSvs;
	
	@Autowired
	private DjUserQRcodeTools qrcodeTools;

	@RequestMapping(value = "/list")
	public String articleList(DjReqestParam param, ModelMap map)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		List<Search> searchs = new ArrayList<>();
		// 保存排序号
		if ("btnSave".equalsIgnoreCase(param.EVENTTARGET))
		{
			btnSaveUser(param.listId, param.sortId);
		}
		// 删除文章
		else if ("btnDelete".equalsIgnoreCase(param.EVENTTARGET))
		{
			btnDeleteUser(param.listId, param.listChkIdx);
		}
		// 翻页
		else if ("btnPage".equalsIgnoreCase(param.EVENTTARGET))
		{
			if (null == param.EVENTARGUMENT || param.EVENTARGUMENT.isEmpty())
			{
				param.EVENTARGUMENT = "0";
			}

			param.page = Integer.parseInt(param.EVENTARGUMENT);
		}
		// 查找
		else if ("btnSearch".equalsIgnoreCase(param.EVENTTARGET))
		{
			param.page = 0;
		}
		// 组别
		if(param.statusId != null)
		{
			searchs.add(new Search(Operator.AND, DjUser.sURole, param.statusId,Operator.EQ));
		}
		
		if(StringUtils.isNoneBlank(param.keywords))
		{
			searchs.add(new Search(Operator.OR, DjUser.sRealName, param.keywords,Operator.LIKE));
			searchs.add(new Search(Operator.OR, DjUser.sUsername, param.keywords,Operator.LIKE));
			searchs.add(new Search(Operator.OR, DjUser.sMobile, param.keywords,Operator.LIKE));
			searchs.add(new Search(Operator.OR, DjUser.sNickname, param.keywords,Operator.LIKE));
		}
		
		map.addAttribute("user_page", userSvs.findUser(searchs, param.size, param.page));

		map.addAttribute("param", param);

		return "/management/user/user_list";
	}
	
	@RequestMapping(value= "/msg")
	public String msg()
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		return "/management/user/msg_edit";
	}
	
	@RequestMapping(value= "/msg/save")
	public String msgSave(DjMessage msg ,String[] mobileNumbers)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		msgCheck(msg);
		List<DjUser> users = new ArrayList<>();
		List<DjMessage> msgs = new ArrayList<>();
		
		if(mobileNumbers == null || mobileNumbers.length < 1)
			users = userSvs.findUserByStatus(DjUser.UStatus_Enable);
		else
			users = userSvs.findByUsernames(mobileNumbers);
		
		for (DjUser djUser : users)
		{
			DjMessage message = creatMessage(djUser.getId(),msg);
			msgs.add(message);
		}
		msgSvs.save(msgs);
		return "/management/user/msg_edit";
	}
	
	

//	@ModelAttribute
//	public void getModel(@RequestParam(value = "id", required = false) Long id,Model model) {
//		if (null != id) 
//		{
//			model.addAttribute("DjUser", djUserService.findOne(id));
//		}
//	}

	@RequestMapping(value = "/edit")
	public String userEdit(Long id,
			String __EVENTTARGET,
			String __EVENTARGUMENT,
			String action,
			ModelMap map,
			HttpSession session)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}

		if (null != id)
		{
			DjUserRelations userRelation = relationSvs.findByUid(id);
			if(userRelation != null)
			{
				DjUser supUser = userSvs.findOne(userRelation.getPid());
				map.addAttribute("relations",userRelation.getUid());
				map.addAttribute("super_username",supUser != null ? supUser.getRealName() : "无");
			}
			map.addAttribute("user", userSvs.findOne(id));
			map.addAttribute("relations",relationSvs.findByUid(id));
			map.addAttribute("relations_size", relationSvs.findUidByPid(id));
			map.addAttribute("relation_number",userSvs.findTotalSpreadByUid(id, 0));
		}

		return "/management/user/user_edit";
	}
	
	@RequestMapping(value = "/spreadimg/{uid}")
	public void spreadImg(@PathVariable Long uid,String title, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, JSONException
	{
		if(!isManagerLogin())
			return ;

		DjUser user = userSvs.findOne(uid);
		if(user != null && user.getuType() == 2)
		{
			qrcodeTools.QRcodeByUidAndResponse(user.getId(), title, request, response);
		}
	}
	
	@RequestMapping(value = "/edit/changeStore")
	public String changeStore(Long uid,ModelMap map,DjReqestParam param)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		map.addAttribute("uid", uid);
		if ("btnPage".equalsIgnoreCase(param.EVENTTARGET))
		{
			if (null == param.EVENTARGUMENT || param.EVENTARGUMENT.isEmpty())
			{
				param.EVENTARGUMENT = "0";
			}

			param.page = Integer.parseInt(param.EVENTARGUMENT);
		}
		map.addAttribute("param", param);
		if(StringUtils.isNotBlank(param.keywords))
			map.addAttribute("user_page", userSvs.findByUType(2,param.keywords,param.page,10));
		else
			map.addAttribute("user_page", userSvs.findByUType(2,param.page,10));
		
		return "/management/user/dialog_change";
	}
	
	@RequestMapping(value = "/edit/changeStore/save")
	@ResponseBody
	public Map<String, Object> changeStoresave(Long uid,Long pid,ModelMap map)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		if (!isManagerLogin())
		{
			res.put("msg", "请重新登录");
            return res;
		}
		if(uid == null || pid == null)
		{
			res.put("msg", "参数错误");
            return res;
		}
		
		DjUserRelations userRelations = relationSvs.findByUid(uid);
		userRelations.setPid(pid);
		relationSvs.save(userRelations);
		
		res.put("status", 1);
		return res;
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String userSave(DjUser djUser,
			String __VIEWSTATE,
			Boolean relationsenable,
			Long relationsid,
			Long relationsopen,
			Integer UTpye,
			ModelMap map,
			HttpSession session)
	{
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		if(djUser.getId() == null)
		{
			if(UTpye != null)
				djUser.setuType(2);
			userSvs.createUser(djUser);
		}
		else
		{
			userSvs.updateUser(djUser);
			if(relationsid != null)
				relationSvs.updateEnableById(relationsid, relationsenable);
			
			if(relationsopen != null)
				relationSvs.createFirstRelations(djUser.getId());
		}
		return "redirect:/management/user/list";
	}
	
	/**
	 * 查看分销下级
	 * @param map
	 * @param Uid
	 * @return
	 */
	@RequestMapping(value = "/distribution")
	public String distribution(ModelMap map,Long uid,DjReqestParam param)
	{
		// 翻页
		if ("btnPage".equalsIgnoreCase(param.EVENTTARGET))
		{
			if (null == param.EVENTARGUMENT || param.EVENTARGUMENT.isEmpty())
			{
				param.EVENTARGUMENT = "0";
			}

			param.page = Integer.parseInt(param.EVENTARGUMENT);
		}
		map.addAttribute("user_page", userSvs.findByPid(uid, param.page, param.size));
		map.addAttribute("uid",uid);
		
		return "/management/user/distribute_list";
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> validateForm(String param, Long id)
	{
		Map<String, String> res = new HashMap<String, String>();

		res.put("status", "n");

		if (null == param || param.isEmpty())
		{
			res.put("info", "该字段不能为空");
			return res;
		}

		if (null == id)
		{
			if (null != userSvs.findByUsername(param))
			{
				res.put("info", "已存在同名用户");
				return res;
			}
		}
		else
		{
			if (null != userSvs.findByUsernameAndIdNot(param, id))
			{
				res.put("info", "已存在同名用户");
				return res;
			}
		}

		res.put("status", "y");

		return res;
	}

	/**
	 * 用户保存
	 * 
	 * @param ids
	 * @param chkIdx
	 * @param sortIds
	 */
	private void btnSaveUser(Long[] ids, Double[] sortIds)
	{
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1)
		{
			return;
		}

		for (int idx = 0; idx < ids.length; idx++)
		{
			if (sortIds.length > idx)
			{
				DjUser cat = userSvs.findOne(ids[idx]);

				if (null != cat)
				{
//					cat.setSortId(sortIds[idx]);
					userSvs.save(cat);
				}
			}
		}
	}

	/**
	 * 用户删除
	 * 
	 * @param ids
	 * @param chkIdx
	 */
	private void btnDeleteUser(Long[] ids, Integer[] chkIdx)
	{
		if (null == ids || null == chkIdx || ids.length < 1 || chkIdx.length < 1)
		{
			return;
		}

		for (int idx : chkIdx)
		{
			if (idx >= 0 && ids.length > idx)
			{
				userSvs.delete(ids[idx]);
			}
		}
	}
	
	private void msgCheck(DjMessage msg)
	{
		if(StringUtils.isBlank(msg.getBrief()))
		{
			msg = null;
			return ;
		}
		if(StringUtils.isBlank(msg.getLinkUrl()))
			msg.setLinkUrl(null);
		if(msg.getSendTime() == null)
			msg.setSendTime(new Date());
		if(StringUtils.isBlank(msg.getContent()))
			msg.setContent(null);
		if(StringUtils.isBlank(msg.getTitle()))
			msg.setTitle("消息通知");
		
		msg.setIsRead(false);
	}
	
	private DjMessage creatMessage(Long uid,DjMessage msg)
	{
		DjMessage nMsg = new DjMessage();
		nMsg = (DjMessage) msg.clone();
		nMsg.setUid(uid);
		return nMsg;
		
	}
}
