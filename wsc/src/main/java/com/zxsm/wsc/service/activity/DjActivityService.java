package com.zxsm.wsc.service.activity;

import java.util.ArrayList;
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

import com.zxsm.wsc.common.UtilsTools.DjArrayUtils;
import com.zxsm.wsc.entity.activity.DjActivity;
import com.zxsm.wsc.entity.activity.DjUserLimitGoods;
import com.zxsm.wsc.entity.order.DjOrder;
import com.zxsm.wsc.entity.order.DjOrderGoods;
import com.zxsm.wsc.repository.activity.DjActivityRepo;
import com.zxsm.wsc.repository.activity.DjUserLimitGoodsRepo;
import com.zxsm.wsc.service.order.DjOrderService;

@Service
@Transactional
public class DjActivityService {

	@Autowired
	DjActivityRepo activityRepo;
	
	@Autowired
	DjUserLimitGoodsRepo uLimitRepo;
	
	@Autowired
	DjOrderService orderSvs;
	

	/**
	 * 查找当前正在进行的活动
	 * @param currentDate
	 * @return
	 */
	public List<DjActivity> OngoingActivity(Date currentDate)
	{
		return activityRepo.findBybeginTimeAfterAndEndTimeBefore(currentDate, currentDate);
	}
	
	public List<Long> OngoingActivityId(Date currentDate)
	{
		return activityRepo.findIdBybeginTimeBeforeAndEndTimeAfter(currentDate, currentDate);
	}
	
	/**
	 * 商品限购数量
	 * @param gid
	 * @return
	 */
	public Integer limitNumberByGid(Long gid)
	{
		List<Long> ongoingActivityId = this.OngoingActivityId(new Date());
		if(ongoingActivityId != null && ongoingActivityId.size() > 0)
		{
			Long[] strings = new Long[ongoingActivityId.size()];

			Long[] ids = ongoingActivityId.toArray(strings);
			return orderSvs.limitNumberOfActivity(ids, gid);
		}
		return null;
	}

	/**
	 * 用户还能购买此商品的个数
	 * @param uid 用id
	 * @param gid 商品id
	 * @return
	 */
	public Integer buyQuantityByUidAndGid(Long uid,Long gid)
	{
		Integer limitNumberByGid = this.limitNumberByGid(gid);
		if(limitNumberByGid != null)
		{
			List<Long> activityIds = this.OngoingActivityId(new Date());
			Integer buyQuantity = buyQuantity(activityIds.toArray(new Long[activityIds.size()]), uid, gid);
			return limitNumberByGid - buyQuantity < 0 ? 0 : limitNumberByGid - buyQuantity;
		}
		return null;
	}
	
	
	/**
	 * 查看用户是否能够再继续购买
	 * @param uid
	 * @return
	 */ 
	public String limitNumberByUid(Long uid,Long[] gids,Map<String,Integer> gidsAndQuantity)
	{
		if(uid == null || gids == null || gids.length < 1)
			return "参数错误";
		List<Long> activityIds = this.OngoingActivityId(new Date());
		if(activityIds != null && activityIds.size() > 0)
		{
			Long[] array = new Long[activityIds.size()];
			Long[] ids =activityIds.toArray(array);
			for (Long gid : gids)
			{
				Integer limitNumber = orderSvs.limitNumberOfActivity(ids, gid); // 限购数量
				if(limitNumber != null)
				{
					Integer quantity = this.buyQuantity(ids, uid, gid); 			// 已购面数量
					Integer buyquantity = gidsAndQuantity.get(gid.toString());		// 要购买数量
					if(limitNumber < quantity + buyquantity)
					{
						return gid.toString() + "+超出限购数量";
					}
				}
			}
			for (Long ads : activityIds)
			{
				DjActivity activity = this.findOne(ads);
				Integer limitNumber = activity.getNumber();
				if(limitNumber != null && limitNumber > 0)
				{
				Long[] allLimitGid = orderSvs.limitGidOfActivity(ads);		//总的限购商品id
				Long[] buyLimitGid = this.findGidByUidAndActivityId(uid, ads);//已购买的商品id
				if(buyLimitGid == null || buyLimitGid.length < 1)
				{
					ArrayList<Long> arrayList = DjArrayUtils.Intersection(gids, allLimitGid);
					if(arrayList != null)
					{
						if(arrayList.size() > limitNumber)
							return "商品限购" + limitNumber + "个";
					}
				}
				else
				{
					ArrayList<Long> complement = DjArrayUtils.complement(gids, buyLimitGid);
					ArrayList<Long> intersection = DjArrayUtils.Intersection(complement.toArray(new Long[complement.size()]), allLimitGid);
					
					if(intersection != null && intersection.size() +buyLimitGid.length > limitNumber)
						return "商品限购" + limitNumber + "个";
				}
				}
			}
		}
		return null;
	}
	
	/**
	 * 用户已购买的限购商品的数量
	 * @param aid 活动id
	 * @param uid 用户id
	 * @param gid 商品id
	 * @return
	 */
	public Integer buyQuantity(Long[] aid,Long uid,Long gid)
	{
		Integer quantity = uLimitRepo.findBuyQuantity(aid, uid, gid);
		if(quantity ==null)
			quantity = 0;
		return quantity;
	}
	
	/**
	 *  已购买商品id
	 * @param uid
	 * @param aid
	 * @return
	 */
	public Long[] findGidByUidAndActivityId(Long uid,Long aid)
	{
		return uLimitRepo.findGidByUidAndActivityId(uid, aid);
	}
	
	//查找
	public DjActivity findOne(Long id)
	{
		if(id == null)
			return null;
		return activityRepo.findOne(id);
	}
	
	public Page<DjActivity> findAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "initDate"));
		return activityRepo.findAll(pageRequest);
	}
	
	/**
	 * 保存
	 * @param e
	 * @return
	 */
	public DjActivity save(DjActivity e)
	{
		if( null == e)
			return null;
		
		// 当修改时取出多余的商品
		for(int i=0 ;i<e.getLimitGoods().size();i++)
		{
			if(e.getLimitGoods().get(i).getPostFee()==null)
				e.getLimitGoods().remove(i);
		}
		if(e.getBuyUser() != null && e.getBuyUser().size() > 0)
		{
			saveULG(e.getBuyUser());
		}
		return activityRepo.save(e);
	}

	public List<DjActivity> save(List<DjActivity> entities)
	{

		return (List<DjActivity>) activityRepo.save(entities);
	}
	
	public DjUserLimitGoods saveULG(DjUserLimitGoods e)
	{
		if(null == e)
			return null;
		return uLimitRepo.save(e);
	}
	public List<DjUserLimitGoods> saveULG(List<DjUserLimitGoods> e)
	{
		if(null == e)
			return null;
		return (List<DjUserLimitGoods>)uLimitRepo.save(e);
	}
	
	/**
	 * 根据订单 记录用户购买限购商品
	 */
	public void LogLimitGoodsByOrder(DjOrder order)
	{
		if(order == null)
			return;
//		Long[] gid = orderSvs.gidByOrderId(order.getId());
		
		List<Long> aids = this.OngoingActivityId(new Date());
		List<DjOrderGoods> orderGoods = order.getOrderGoods();
		for (Long aid : aids)
		{
			List<DjUserLimitGoods>  limitGoods = new ArrayList<DjUserLimitGoods>();
			for (DjOrderGoods oGoods : orderGoods) 
			{
				Integer limitNumber = orderSvs.limitNumberOfActivity(aid, oGoods.getId());
				if(limitNumber != null)
				{
					if(limitNumber < oGoods.getQuantity())
					{
						limitGoods.add(new DjUserLimitGoods(order.getUserId(), oGoods.getId(), limitNumber));
					}
					else
					{
						limitGoods.add(new DjUserLimitGoods(order.getUserId(), oGoods.getId(), oGoods.getQuantity()));
					}
				}
			}
			if(limitGoods.size() > 0)
			{
				DjActivity djActivity = this.findOne(aid);
				this.saveULG(limitGoods);
				List<DjUserLimitGoods> buyUser = djActivity.getBuyUser();
				buyUser.addAll(limitGoods);
				this.save(djActivity);
			}
		}
		
	}

}
