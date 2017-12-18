package com.zxsm.wsc.service.order;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.order.DjCartGoods;
import com.zxsm.wsc.entity.order.DjOParam;
import com.zxsm.wsc.repository.goods.DjGoodsRepo;
import com.zxsm.wsc.repository.order.DjCarGoodsRepo;


/**
 * DjCartGoods 服务类
 * 
 * @author maeing
 *
 */

@Service
@Transactional
public class DjCartGoodsService {
    
    @Autowired
    DjCarGoodsRepo repository;
    
    @Autowired
    DjGoodsRepo goodsRepo;
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(DjCartGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<DjCartGoods> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public DjCartGoods findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<DjCartGoods> findAll(Iterable<Long> ids)
    {
        return (List<DjCartGoods>) repository.findAll(ids);
    }
    
    public List<DjCartGoods> findAll()
    {
        return (List<DjCartGoods>) repository.findAll();
    }
    
    public Page<DjCartGoods> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "initDate"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<DjCartGoods> findAllOrderBySortIdAsc()
    {
        return (List<DjCartGoods>) repository.findAll(new Sort(Direction.ASC, "initDate"));
    }
    
    public DjCartGoods findByGid(Long gid)
    {
    	if(gid == null)
    		return null;
    	return repository.findCartGoodsByGId(gid);
    }
    
    public List<DjCartGoods> findByUid(Long uid)
    {
    	if(uid == null)
    		return null;
    	return repository.findByUid(uid);
    }
    public List<DjCartGoods> updateByUid(Long uid,Integer type)
    {
    	if(uid == null)
    		return null;
    	if(type == null)
    		type = 2;
    		
    	return repository.updateByUid(uid,type);
    }
    
    public List<DjCartGoods> updateByIdsAndUid(Long[] ids,Long uid)
    {
    	if(uid == null)
    		return null;
    	return repository.updateByIdsAndUid(ids,uid);
    }
    
    public List<DjCartGoods> updateByIds(Long[] ids)
    {
    	if(ids == null || ids.length < 1)
    		return null;
    	return repository.updateByIds(ids);
    }
    
    
    public List<DjCartGoods> findByIdInAndUid(Long[] ids, Long uid)
    {
    	if(uid == null || ids == null)
    		return null;
    	return repository.findByIdInAndUid(ids,uid);
    }
    
    public List<DjGoods> findGoodsByUid(Long uid)
    {
    	if(uid == null)
    		return null;
    	List<Long> gids = repository.findGidByUid(uid);
    	Sort sort = new Sort(Direction.DESC, "initDate").and(new Sort(Direction.DESC, "sortId"));
    	return goodsRepo.findByIdIn(gids, sort);
    }
    
    public DjCartGoods findByGidAndUid(Long gid,Long uid)
    {
    	if(gid == null || uid == null)
    		return null;
    	return repository.findTop1ByGidAndUid(gid, uid);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public DjCartGoods save(DjCartGoods e)
    {
        return repository.save(e);
    }
    
    public List<DjCartGoods> save(List<DjCartGoods> entities)
    {
        
        return (List<DjCartGoods>) repository.save(entities);
    }
    
    /**
     * 获取商品总价格
     * @param cGoods
     * @return
     */
    public Double getTotalPrice(List<DjCartGoods> cGoods)
    {
    	if(cGoods == null || cGoods.size() <= 0)
    		return 0d;
    	Double total = 0d;
    	for (DjCartGoods djCartGoods : cGoods)
    	{
    		total += djCartGoods.getQuantity() * djCartGoods.getSalePrice().doubleValue();
		}
    	return total;
    }
    
    
    /**
     * 算商品邮费
     * @param cGoods
     * @return
     */
    public BigDecimal getTotalExpressFee(List<DjCartGoods> cGoods)
    {
    	if(cGoods == null || cGoods.size() <= 0)
    		return BigDecimal.valueOf(0d);
//    	Double total = 0d;
    	BigDecimal total = BigDecimal.valueOf(0d);
    	for (DjCartGoods djCartGoods : cGoods)
    	{
//    		total += djCartGoods.getQuantity() * djCartGoods.getSalePrice().doubleValue();
    		if(djCartGoods.getExpressFee() != null && djCartGoods.getExpressFee().compareTo(total) > 0)
    			total = djCartGoods.getExpressFee();
		}
    	return total;
    }
    
    /**
     * 修改购物车里的商品购买数量
     * 判断是否是同一类商品 ：处方药或者非处方药
     * @param uId
     * @param oParam
     * @return 如果非一类药品，返回null
     */
    public List<DjCartGoods> checkCartGoodsList(Long uId,DjOParam oParam)
    {
    	List<DjCartGoods> cGoodsList = updateByIdsAndUid(oParam.chooseIds(), uId);
    	Map<String, Integer> quantityMap = oParam.idsAndQuantity();
    	
    	Boolean allType = true;
    	Integer type = null;
		Iterator<DjCartGoods> iterator = cGoodsList.iterator();
		while(iterator.hasNext())
		{
			DjCartGoods next = iterator.next();
			Integer Gtype = next.getType() == null ? 2 : next.getType();
			if(type == null)
				type = Gtype;
			else
			{
				if(type != Gtype)
					allType = false;
			}
			
			Integer integer = quantityMap.get(next.getId() + "");
			next.setQuantity(integer);
		}
		if(!allType)
		{
			save(cGoodsList);
			return null;
		}
		return save(cGoodsList);
    }
}
