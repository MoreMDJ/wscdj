package com.zxsm.wsc.service.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.user.DjUserRelations;
import com.zxsm.wsc.repository.user.DjUserRelationsRepo;

/**
 * DjUserRelations 服务类
 * 
 * @author maeing
 *
 */

@Service
@Transactional
public class DjUserRelationsService
{

	@Autowired
	DjUserRelationsRepo relationsRepo;
	
	/**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            relationsRepo.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(DjUserRelations e)
    {
        if (null != e)
        {
            relationsRepo.delete(e);
        }
    }
    
    public void delete(List<DjUserRelations> entities)
    {
        if (null != entities)
        {
            relationsRepo.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public DjUserRelations findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return relationsRepo.findOne(id);
    }
    
    public DjUserRelations findEnbaledByUid(Long uid)
    {
    	if(uid == null)
    		return null;
    	return relationsRepo.findTop1ByUidAndEnableTrue(uid);
    }
    
    
    
    
    
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public DjUserRelations save(DjUserRelations e)
    {
        
        return relationsRepo.save(e);
    }
    
    public List<DjUserRelations> save(List<DjUserRelations> entities)
    {
        return (List<DjUserRelations>) relationsRepo.save(entities);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<DjUserRelations> findAll(Iterable<Long> ids)
    {
        return (List<DjUserRelations>) relationsRepo.findAll(ids);
    }
    
    /**
     * 
     * 根据二维码生成分销关系
     * 
     * @param pid 父级用户id
     * @param uid 当前用户id
     * @return
     */
    public DjUserRelations createRelations(Long pid,Long uid)
    {
    	if(pid == null )
    		return null;
    	DjUserRelations pRelations = null;
    	
    	pRelations = relationsRepo.findTop1ByUidAndEnableTrue(pid);
    	if(pRelations == null || pRelations.getLevel() == null)
    	{
    		return null;
    	}
    	
    	DjUserRelations nRelations = new DjUserRelations(uid, pid, 0L, (pRelations.getLevel() + 1 > 4 ||pRelations.getLevel() + 1 == 1) ? 0 :pRelations.getLevel() + 1, true);
    	
    	return relationsRepo.save(nRelations);
    }
    
    /**
     * 查找上一级会员id
     * @param uid
     * @return 空代表无上级
     */
    public Long superiorByUid(Long uid)
    {
    	if(uid == null)
    		return null;
    	
    	Long pid = relationsRepo.findPidByUidAndEnableTrue(uid);
    	return (pid == null || pid == 0) ? null :pid;
    }
    
    /**
     * 查找下一级会员id
     * @param pid
     * @return
     */
    public List<Long> findUidByPid(Long pid)
    {
    	if(pid == null)
    		return null;
    	return relationsRepo.findUidByPidAndEnableTrue(pid);
    }
    
    public List<Long> findUidByPidAndTime(Long pid,Date startDate,Date endDate)
    {
    	if(pid == null)
    		return null;
    	if(startDate == null)
    		return null;
    	if(endDate == null)
    		endDate = new Date();
    	return relationsRepo.findUidByPidAndEnableAndTimeBetween(pid, startDate, endDate);
    }
	
    
    /**
     * 创建一级分销会员关系
     * @param uid
     * @return
     */
    public DjUserRelations createFirstRelations(Long uid)
    {
    	if(uid == null )
    		return null;
    	DjUserRelations relations = relationsRepo.findTop1ByUid(uid);
    	if(relations != null)
    		return null;
    	relations = new DjUserRelations(uid, 0L, 0L, 1, true);
    	
    	return save(relations);
    }
    
    /**
     * 根据用户id查找用户分销关系
     * @param uid
     * @return
     */
    public DjUserRelations findByUid(Long uid)
    {
    	if(uid == null)
    		return null;
    	return relationsRepo.findTop1ByUid(uid);
    }
    
    /**
     * 跟新关系 是否可用
     * @param id
     * @param enable
     * @return
     */
    public Boolean updateEnableById(Long id,Boolean enable)
    {
    	if(id == null)
    		return false;
    	if(enable == null)
    		enable = false;
    	DjUserRelations relations = relationsRepo.findOne(id);
    	if(relations!= null)
    		relations.setEnable(enable);
    	return true;
    }
    
    
    /**
     *  将用户变为一级分销用户
     *  如果是一级分销用户这返回，不修改
     *  如果是一级分销以外的用户变为一级分销用户
     *  如果不是分销用户变为一级分销用户
     *  如果是不可用的则不修改
     * @param uid
     * @return
     */
    public DjUserRelations changeTofirstRelations(Long uid)
    {
    	if(uid == null )
    		return null;
    	DjUserRelations relations = relationsRepo.findTop1ByUid(uid);
    	if(relations == null)
    		return createFirstRelations(uid);
    	
    	if(relations.getLevel() == 1)
    		return relations;
    	else if(relations.getEnable() == true)
    	{
    		relationsRepo.changeLevelByPid(uid, relations.getLevel() + 1);
    		relations.setLevel(1);
    		relations.setPid(0L);
    		return relationsRepo.save(relations);
    	}
    	return relations;
    }
}
