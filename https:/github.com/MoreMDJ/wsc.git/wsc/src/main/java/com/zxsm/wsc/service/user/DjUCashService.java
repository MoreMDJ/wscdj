package com.zxsm.wsc.service.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.entity.management.DjSystemConfig;
import com.zxsm.wsc.entity.user.DjUCash;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.entity.user.DjUserRelations;
import com.zxsm.wsc.repository.user.DjUCashRepo;
import com.zxsm.wsc.service.management.DjSystemConfigService;

@Service
@Transactional
public class DjUCashService
{
	
	@Autowired
	DjUCashRepo cashRepo;
	
	@Autowired
	DjUserService userSvs;
	
	@Autowired
	DjUserRelationsService relaitonSvs;
	
	@Autowired
	DjSystemConfigService configSvs;
	
	/**
	 * 计算积分总额
	 * 
	 * @param userId
	 * @return
	 */
	public BigDecimal countCurrentCashByUserId(long userId)
	{
		BigDecimal points = cashRepo.countCashByUserId(userId);

		return (null == points) ? BigDecimal.valueOf(0) : points;
	}
	
	/**
	 * 获取已消耗积分总数
	 * @param userId
	 * @return
	 */
	public BigDecimal countSpentCashByUserId(long userId)
	{
		BigDecimal points = cashRepo.countCashByUserIdAndPointLessThanZero(userId);

		return (null == points) ? BigDecimal.valueOf(0) : points;
	}
	
	/**
	 * 获取获得总积分
	 * @param userId
	 * @return
	 */
	public BigDecimal countReceivedCashByUserId(long userId)
	{
		BigDecimal points = cashRepo.countCashByUserIdAndPointGreaterThanZero(userId);

		return (null == points) ? BigDecimal.valueOf(0) : points;
	}
	
	/**
	 * 获取积分明细
	 * @param userId
	 * @return
	 */
	public List<DjUCash> findCashByUserId(long userId)
	{
		Sort sort = new Sort(Direction.DESC, "id");
		return cashRepo.findByUid(userId, sort);
	}
	
	
	public DjUCash findByChildUidAndOrderNumber(Long uid,String orderNo)
	{
		DjUserRelations userRelations = relaitonSvs.findByUid(uid);
		if(userRelations != null)
			return cashRepo.findByUidAndOrderNumber(userRelations.getPid(),orderNo);
		return null;
	}
	
	
	public Boolean cashAddByUidAndPrice(Long uid, BigDecimal price)
	{
		//上一级
//		(uid);
		
		return true;
	}
	
	/**
	 * 分润记录(三级分销)
	 * @param uid
	 * @param cash
	 * @return
	 */
	public  Boolean cashLog(Long uid ,Double cash,String orderNumber)
	{
		//上一级
		DjUser user3 = userSvs.superiorByUid(uid);
		if(user3 != null)
		{
			DjSystemConfig levelThree = configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELTHREE);
			if(levelThree == null || StringUtils.isBlank(levelThree.getConfigValue()))
				return false;
			Double price3 = cash * Double.parseDouble(levelThree.getConfigValue());
			if(price3.compareTo(0d) > 0)
			{
				DjUCash uCash3 = new DjUCash(user3.getId(), BigDecimal.valueOf(price3), orderNumber);
				cashRepo.save(uCash3);
			}

			//上2 级
			DjUser user2 = userSvs.superiorByUid(user3.getId());
			if(user2 != null)
			{
				DjSystemConfig levelTwo = configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELTWO);
				if(levelTwo == null || StringUtils.isBlank(levelTwo.getConfigValue()))
					return false;
				Double price2 = cash * Double.parseDouble(levelTwo.getConfigValue());
				if(price2.compareTo(0d) > 0)
				{
				DjUCash uCash2 = new DjUCash(user2.getId(), BigDecimal.valueOf(price2), orderNumber);
				cashRepo.save(uCash2);
				}
				// 上3级
				DjUser user1 = userSvs.superiorByUid(user2.getId());
				if(user1 != null)
				{
					DjSystemConfig levelone = configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE);
					if(levelone == null || StringUtils.isBlank(levelone.getConfigValue()))
						return false;
					Double price1 = cash * Double.parseDouble(levelone.getConfigValue());
					if(price1.compareTo(0d) > 0)
					{
					DjUCash uCash1 = new DjUCash(user1.getId(), BigDecimal.valueOf(price1), orderNumber);
					cashRepo.save(uCash1);
					} 
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 分润 只有门店和店员可以分润
	 * @param uid
	 * @param cash
	 * @param orderNumber
	 * @return
	 */
	public Boolean cashLogLevel1(Long uid ,Double cash,String orderNumber)
	{
		//上一级
		DjUser user = userSvs.superiorByUid(uid);
		if(user != null && user.getuRole() != null && (user.getuRole() == 1 || user.getuRole() == 2))
		{
			DjSystemConfig level1 = configSvs.findByConfigKey(DjKeys.K_DISTRIBUTION_LEVELONE_ONE);
			if(level1 == null || StringUtils.isBlank(level1.getConfigValue()))
				return false;
			Double price = cash * Double.parseDouble(level1.getConfigValue());
			if(price.compareTo(0d) > 0)
			{
				DjUCash uCash = new DjUCash(user.getId(), BigDecimal.valueOf(price), orderNumber);
				cashRepo.save(uCash);
			}
		}
		return true;
	}
	
	/**
	 * 单商品提成
	 * @param uid 用户id
	 * @param costFee 提成的金额
	 * @param orderNumber 订单号
	 * @return
	 */
	public Boolean CashLogSingleGoods(Long uid,Double costFee,String orderNumber)
	{
		//上一级
		DjUser user = userSvs.superiorByUid(uid);
		if(user != null && user.getuRole() != null && (user.getuRole() == 1 || user.getuRole() == 2))
		{
			if(costFee.compareTo(0d) > 0)
			{
				DjUCash uCash = new DjUCash(user.getId(), BigDecimal.valueOf(costFee), orderNumber);
				cashRepo.save(uCash);
			}
		}
		return true;
	}
	
	/**
	 * 增加金额
	 * @param user
	 * @param point
	 * @param detail
	 * @return
	 */
	public DjUCash addPoint(DjUser user, Double cash, String detail)
	{
		DjUCash p = new DjUCash();
		
		p.setDetail(detail);
		p.setCash(BigDecimal.valueOf(cash));
		p.setUid(user.getId());
		p.setPointTime(new Date());
		
		/**
		// 升级用户
		int totalPoint = countReceivedPointsByUserId(user.getId());
		
		DjUserLevel level = userLevelService.findUserLevelByPointNeed(totalPoint);
		
		if (null != level)
		{
			user.setUserLevelId(level.getId());
			user.setUserLevelTitle(level.getTitle());
		}*/
		
		return cashRepo.save(p);
	}

}
