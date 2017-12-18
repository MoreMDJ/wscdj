package com.zxsm.wsc.service.management;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.management.DjSystemConfig;
import com.zxsm.wsc.repository.management.DjSystemConfigRepo;
/**
 * DjManager 服务类
 * 
 * @author maeing
 *
 */

@Service
@Transactional
public class DjSystemConfigService
{

	@Autowired
	DjSystemConfigRepo sysRepo;
	
	/**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            sysRepo.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(DjSystemConfig e)
    {
        if (null != e)
        {
            sysRepo.delete(e);
        }
    }
    
    public void delete(List<DjSystemConfig> entities)
    {
        if (null != entities)
        {
            sysRepo.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public DjSystemConfig findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return sysRepo.findOne(id);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public DjSystemConfig save(DjSystemConfig e)
    {
        
        return sysRepo.save(e);
    }
    
    public List<DjSystemConfig> findClikKeyValue()
    {
    	return sysRepo.findByConfigKeyContaining("k_wx_");
    }
    
    /** 
     * 保存分销配置
     * @param key
     * @param value
     * @return
     */
    public DjSystemConfig savaDistribution(String key,String value)
    {
    	if(StringUtils.isBlank(key))
    		return null;
    	
    	DjSystemConfig systemConfig = sysRepo.findByConfigKey(key);
    	if(systemConfig == null)
    		systemConfig = new DjSystemConfig(2, key, value, Byte.valueOf("2"), new Date(), 1L);
    	
    	systemConfig.setConfigValue(value);
    	
    	return sysRepo.save(systemConfig);
    	
    }
    /** 
     * 保存微信配置
     * @param key
     * @param value
     * @return
     */
    public DjSystemConfig savaWX(String key,String value)
    {
    	if(StringUtils.isBlank(key))
    		return null;
    	if(StringUtils.isBlank(value))
    		return null;
    	
    	DjSystemConfig systemConfig = sysRepo.findByConfigKey(key);
    	if(systemConfig == null)
    		systemConfig = new DjSystemConfig(1, key, value, Byte.valueOf("2"), new Date(), 1L);
    	
    	systemConfig.setConfigValue(value);
    	
    	return sysRepo.save(systemConfig);
    	
    }
    
    
    public List<DjSystemConfig> save(List<DjSystemConfig> entities)
    {
        return (List<DjSystemConfig>) sysRepo.save(entities);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<DjSystemConfig> findAll(Iterable<Long> ids)
    {
        return (List<DjSystemConfig>) sysRepo.findAll(ids);
    }
    
    public DjSystemConfig findByConfigKey(String key)
    {
    	if(StringUtils.isBlank(key))
    		return null;
    	return sysRepo.findByConfigKey(key);
    }
    
    
    
	
}
