package com.zxsm.wsc.service.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.common.SerchTools.Search;
import com.zxsm.wsc.common.UtilsTools.DateUtils;
import com.zxsm.wsc.common.UtilsTools.StringTools;
import com.zxsm.wsc.common.tencent.controller.DjUserQRcodeTools;
import com.zxsm.wsc.common.tencent.entity.UserInfoResponse;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.repository.user.DjUserRepo;

@Service
@Transactional
public class DjUserService {

	@Autowired
	private DjUserRepo userRepo;
	
	@Autowired
	private DjUserRelationsService relationSvs;
	
	@Autowired
	DjUserQRcodeTools qrTools;

	public DjUser save(DjUser user)
	{
		if (null == user)
		{
			return null;
		}
		return userRepo.save(user);
	}
	
	/**
	 * 后台或者注册时新增用户
	 * @param user
	 * @return
	 */
	public DjUser createUser(DjUser user)
	{
		if(StringUtils.isBlank(user.getNickname()))
			user.setNickname(user.getUsername());
		user.setAllPayed(0d);
		user.setTotalPoint(0);
		if(user.getMobile() == null)
		user.setMobile(user.getUsername());
		if(user.getSex() == null)
			user.setSex(3);
		
		if(user.getStatus() == null)
			user.setStatus(1);
		if(user.getuType() == null)
			user.setuType(0);
		
		if(StringUtils.isBlank(user.getHeadImage()))
			user.setHeadImage("/images/user_head_default.png");
		
		if (StringUtils.isBlank(user.getPassword()))
		{
			user.setPassword(DjKeys.DefaultPassword);
		}
		user.encryptionPassword();
		
		return save(user);
	}
	
	/**
	 * 扫描二维生成新用户 和 分销关系
	 * 
	 * @param spreadId
	 * @return
	 */
	public DjUser createBySpreadId(Long spreadId,String openId)
	{
		if(spreadId == null || StringUtils.isBlank(openId))
			return null;
		UserInfoResponse userInfoResponse = qrTools.GetUserInfoByOpenId(openId);
		if(StringUtils.isNotBlank(userInfoResponse.getErrmsg()) )
			return null;
		DjUser user = userRepo.findByUsername(openId);
		if(user != null)
			return null;
		user = userRepo.findByOpenid(openId);
		if(user != null)
			return null;
		user = new DjUser();
		user.setUsername(userInfoResponse.getOpenid());
		user.setMobile("");
		user.setPassword("123456");
		user.setStatus(1);
		user.setuType(1);
		user.setNickname(StringTools.filterEmoji(userInfoResponse.getNickname(),"*"));
		user.setHeadImage(userInfoResponse.getHeadimgurl());
		user.setSex(Integer.valueOf(userInfoResponse.getSex()) == 0 ? 3 :Integer.valueOf(userInfoResponse.getSex()));
		user.setOpenid(userInfoResponse.getOpenid());
		user = createUser(user);
		
		relationSvs.createRelations(spreadId, user.getId());
		return user;
	}
	
	/**
	 * 获取上一级用户
	 * @param uid
	 * @return
	 */
	public DjUser superiorByUid(Long uid)
	{
		if(uid == null)
			return null;
		Long pid = relationSvs.superiorByUid(uid);
		
		return findOne(pid);
	}
	
	/**
	 * 获取下一级用户
	 * @param pid
	 * @return
	 */
	public List<DjUser> findByPid(Long pid)
	{
		if(pid == null)
			return null;
		
		List<Long> pidRelations = relationSvs.findUidByPid(pid);
		if(pidRelations == null)
			pidRelations = new ArrayList<Long>();
		return userRepo.findByIdInAndStatus(pidRelations, 1);
	}
	/**
	 * 获取门店的店员
	 * @param pid
	 * @return
	 */
	public List<DjUser> findByIdInAndStatusAndURole(Long pid)
	{
		if(pid == null)
			return null;
		
		List<Long> pidRelations = relationSvs.findUidByPid(pid);
		if(pidRelations == null)
			pidRelations = new ArrayList<Long>();
		return userRepo.findByIdInAndStatusAndURole(pidRelations, 1,2);
	}
	
	
	/**
	 * 获取下一级用户
	 * @param pid
	 * @return
	 */
	public Page<DjUser> findByPid(Long pid,Integer page,Integer size)
	{
		
		List<Long> pidRelations = relationSvs.findUidByPid(pid);
		if(pidRelations == null)
			pidRelations = new ArrayList<Long>();
		if(page == null)
			page = 0;
		if(size == null)
			size = 20;
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "lastLoginTime").and(new Sort(Direction.DESC,"id")));
		return userRepo.findByIdInAndStatus(pidRelations, 1,pageRequest);
	}
	
	
	/**
	 * 查找所有下级会员数量
	 * @param uid
	 * @return
	 */
	public Integer findTotalSpreadByUid(Long uid,Integer number)
	{
		Integer totalt = number;
		List<Long> chiledUid = relationSvs.findUidByPid(uid);
		if(chiledUid != null && chiledUid.size() > 0)
		{
			totalt += chiledUid.size();
			for (int i = 0; i < chiledUid.size(); i++) {
				totalt = findTotalSpreadByUid(chiledUid.get(i),totalt);
			}
		}
		 return totalt;
	}
	
	public Integer findSpredByUidAndTime(Long uid,String startDate,String endDate,Integer number)
	{
		
		Date datestart;
		try {
			datestart = DateUtils.convertStringToDate("yyyy-MM-dd HH:mm:ss", startDate);
		} catch (ParseException e) {
			datestart = null;
			e.printStackTrace();
		}
		Date dateend;
		try {
			dateend = DateUtils.convertStringToDate("yyyy-MM-dd HH:mm:ss", endDate);
		} catch (ParseException e) {
			dateend = null;
			e.printStackTrace();
		}
		
		Integer totalt = number;
		List<Long> chiledUid = relationSvs.findUidByPidAndTime(uid, datestart, dateend);
		if(chiledUid != null && chiledUid.size() > 0)
		{
			totalt += chiledUid.size();
		}
		List<Long> chiledUid2 = relationSvs.findUidByPid(uid);
		
		for (int i = 0; i < chiledUid2.size(); i++) {
			totalt = findSpredByUidAndTime(chiledUid2.get(i),startDate,endDate,totalt);
		}
		return totalt;
	}
	
	/**
	 * 根据用户openid 查找用户
	 * @param openid
	 * @return
	 */
	public DjUser findByOpenid(String openid)
	{
		if(StringUtils.isBlank(openid))
			return null;
		return userRepo.findByOpenidAndStatus(openid, 1);
	}
	
	public DjUser updateUser(DjUser user)
	{
		DjUser findOne = userRepo.findOne(user.getId());
		findOne.setStatus(user.getStatus());
		findOne.setSex(user.getSex());
		findOne.setRealName(user.getRealName());
		findOne.setNickname(user.getNickname());
		findOne.setHeadImage(user.getHeadImage());
		findOne.setEmail(user.getEmail());
		findOne.setBirthday(user.getBirthday());
		findOne.setuRole(user.getuRole());
		if(StringUtils.isNotBlank(user.getPassword()))
		{
			findOne.setPassword(user.getPassword());
			findOne.encryptionPassword();
		}
		return save(findOne);
	}
	
	public void delete(Long id)
	{
		if (null != id)
		{
			userRepo.delete(id);
		}
	}

	public DjUser findOne(Long id)
	{
		if (null == id)
		{
			return null;
		}
		return userRepo.findOne(id);
	}
	
	public Page<DjUser> findAll(int size,int page)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "lastLoginTime").and(new Sort(Direction.DESC,"id")));
		
		return userRepo.findAll(pageRequest);
	}
	public Page<DjUser> findUser(List<Search> searchs,int size,int page)
	{
		
		Criteria<DjUser> criteria = new Criteria<DjUser>();
		criteria.add(Restrictions.initCriterion(searchs));
		
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "lastLoginTime").and(new Sort(Direction.DESC,"id")));
		
		return userRepo.findAll(criteria,pageRequest);
	}
	
	public List<DjUser> findUser(Map<String,Object> map)
	{
		Criteria<DjUser> criteria = initCriteria(map);
		return userRepo.findAll(criteria);
	}
	
	public List<DjUser> findUserByStatus(Integer status)
	{
		HashMap<String,Object> searchMap = new HashMap<>();
		searchMap.put("status", status);
		return findUser(searchMap);
	}
	
	public DjUser findEnableUser(String username)
	{
		if(StringUtils.isBlank(username))
			return null;
		return userRepo.findByUsernameAndStatus(username, 1);
	}
	
	public List<DjUser>	findByUsernames(String[] username)
	{
		Map<String ,Object> searchMap = new HashMap<>();
		searchMap.put("names", Arrays.asList(username));
		searchMap.put("status", 1);
		return findUser(searchMap);
		
	}
	

	/**
	 * 按username查找，自身除外
	 * 
	 * @author maeing
	 * @param username
	 * @param id
	 * @return
	 */
	public DjUser findByUsernameAndIdNot(String username, Long id) {
		if (null == username || null == id) {
			return null;
		}

		return userRepo.findByUsernameAndIdNot(username, id);
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @author maeing
	 */
	public DjUser findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return userRepo.findByUsername(username);
	}
	
	/**
	 * 根据用户名和密码查找可用的用户
	 * @param username
	 * @param password
	 * @return
	 */
	public DjUser findByUsernameAndPassword(String username,String password)
	{
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password))
			return null;
		return userRepo.findByUsernameAndPasswordAndStatus(username, password, 1);
	}
	
	/**
	 * @author maeing
	 * @注释：搜索用户
	 */
	public Page<DjUser> searchAndOrderByIdDesc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return userRepo.findByUsernameContainingOrRealNameContainingOrderByIdDesc(keywords, keywords, pageRequest);
	}

	/**
	 * 根据真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	public List<DjUser> findByRealName(String realName) {
		if (null == realName) {
			return null;
		}
		return userRepo.findByRealName(realName);
	}
	
	
	/**
	 * 用户类型
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjUser> findByUType(Integer uType,Integer page,Integer size)
	{
		if(page == null || size == null)
			return null;
		PageRequest pageRequest = new PageRequest(page, size);
		return userRepo.findByUType(uType,pageRequest);
	}
	
	/**
	 *  用户类型
	 * @param uType
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjUser> findByUType(Integer uType,String keywords,Integer page,Integer size)
	{
		if(page == null || size == null)
			return null;
		PageRequest pageRequest = new PageRequest(page, size);
		return userRepo.findByUTypeAndRealNameContaining(uType,keywords,pageRequest);
	}
	
	
	/**
	 * 获取查询规则
	 */
	public Criteria<DjUser> initCriteria(Map<String,Object> map)
	{
		if (map == null)
			return null;
		Criteria<DjUser> criteria = new Criteria<DjUser>();
		String username = (String) map.get("username");
		if(StringUtils.isNotBlank(username))
			criteria.add(Restrictions.like("username", username, true));
		
		String nickname = (String) map.get("nickname");
		if(StringUtils.isNotBlank(nickname))
			criteria.add(Restrictions.like("nickname", nickname, true));

		String realName = (String) map.get("realName");
		if(StringUtils.isNotBlank(realName))
			criteria.add(Restrictions.like("realName", realName, true));

		Integer uType = (Integer) map.get("uType");
		if(uType != null)
			criteria.add(Restrictions.eq("uType", uType, true));
		
		Integer uRole = (Integer) map.get("uRole");
		if(uRole != null)
			criteria.add(Restrictions.eq("uRole", uRole, true));
		
		Integer status = (Integer) map.get("status");
		if(status != null)
			criteria.add(Restrictions.eq("status", status, true));
		@SuppressWarnings("unchecked")
		List<String> object = (List<String>)map.get("names");
		if(object != null && object.size() > 0 )
			criteria.add(Restrictions.in("username", object, true));
		
		return criteria;
	}
	
	/** 
	 * 手机号模糊查询
	 * @param username
	 * @return
	 */
	public List<DjUser> findByUsernameContain(String username)
	{
		Criteria<DjUser> criteria = new Criteria<DjUser>();
		criteria.add(Restrictions.like("username", username, true));
		return userRepo.findAll(criteria);
	}
	
	/** 
	 * 用户名绑定
	 * @param uid
	 * @param mobile
	 * @return
	 */
	public String bindingUsername(Long uid,String mobile)
	{
		DjUser user = userRepo.findOne(uid);
		if(user == null)
			return "用户不存在";
		
		user.setUsername(mobile);
		user.setPassword("123456");
		user.encryptionPassword();
		userRepo.save(user);
		
		return "绑定成功";
	}
	
}
