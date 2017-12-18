package com.zxsm.wsc.service.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.entity.user.DjMessage;
import com.zxsm.wsc.repository.user.DjMessageRepo;

@Service
@Transactional
public class DjMessageService
{

	@Autowired
	private DjMessageRepo messageRepo;
	
	public DjMessage findOne(Long id)
	{
		if (null == id) 
		{
			return null;
		}
		return messageRepo.findOne(id);
	}
	
	
	public List<DjMessage> findAll()
	{
		return (List<DjMessage>) messageRepo.findAll();
	}
	
	public Page<DjMessage> findMsg(Map<String,Object> searchMap,int size,int page)
	{
		Criteria<DjMessage> criteria = initCriteria(searchMap);
		
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"id"));
		
		return messageRepo.findAll(criteria,pageRequest);
	}
	
	public Integer countMsgByUidAndType(Long uid,Integer type)
	{
		if(uid == null || type == null)
			return 0;
		
		if(type == 0)
			return messageRepo.countByUidAndIsRead(uid, false);
		
		return messageRepo.countByUidAndTypeAndIsRead(uid, type,false);
	}
	
	public List<DjMessage> findMsgByType(Long uid,Integer type)
	{
		return messageRepo.findByUidAndTypeOrderByIsReadAsc(uid,type);
	}
	
	/**
     * 删除
     * 
     * @param id 
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            messageRepo.delete(id);
        }
    }
    /**
     * 删除
     * 
     * @param e 
     */
    public void delete(DjMessage e)
    {
        if (null != e)
        {
            messageRepo.delete(e);
        }
    }
    
    public void delete(List<DjMessage> entities)
    {
        if (null != entities)
        {
            messageRepo.delete(entities);
        }
    }
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public DjMessage save(DjMessage e)
    {
        
        return messageRepo.save(e);
    }
    
    public List<DjMessage> save(List<DjMessage> entities)
    {
        return (List<DjMessage>) messageRepo.save(entities);
    }
    
    
	
	/**
	 * 获取查询规则
	 */
	public Criteria<DjMessage> initCriteria(Map<String,Object> map)
	{
		if (map == null)
			return null;
		Criteria<DjMessage> criteria = new Criteria<DjMessage>();
		Long uId = (Long) map.get(DjMessage.sUid);
		if(uId != null)
			criteria.add(Restrictions.eq(DjMessage.sUid, uId, true));
		
		Boolean isRead = (Boolean) map.get(DjMessage.sIsRead);
		if(isRead != null)
			criteria.add(Restrictions.eq(DjMessage.sIsRead, isRead, true));

		Date sendTime = (Date) map.get(DjMessage.sSendTime);
		if(sendTime != null)
			criteria.add(Restrictions.gt(DjMessage.sSendTime, sendTime, true));

		Integer type = (Integer) map.get(DjMessage.sType);
		if(type != null)
			criteria.add(Restrictions.eq(DjMessage.sType, type, true));
		
		criteria.setOrderByAsc("initDate");
		
		return criteria;
	}
	
}
