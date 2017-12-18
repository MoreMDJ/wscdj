package com.zxsm.wsc.service.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.user.DjUPoint;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.repository.user.DjUPointRepo;
import com.zxsm.wsc.repository.user.DjUserRepo;

@Service
@Transactional
public class DjUPointService
{
	
	@Autowired
	DjUPointRepo userPointRepo;
	
	@Autowired
	DjUserRepo userRepo;
	
	/**
	 * 计算积分总额
	 * 
	 * @param userId
	 * @return
	 */
	public int countCurrentPointsByUserId(long userId)
	{
		Integer points = userPointRepo.countPointsByUserId(userId);

		return (null == points) ? 0 : points;
	}
	
	/**
	 * 获取已消耗积分总数
	 * @param userId
	 * @return
	 */
	public int countSpentPointsByUserId(long userId)
	{
		Integer points = userPointRepo.countPointsByUserIdAndPointLessThanZero(userId);

		return (null == points) ? 0 : points;
	}
	
	/**
	 * 获取获得总积分
	 * @param userId
	 * @return
	 */
	public int countReceivedPointsByUserId(long userId)
	{
		Integer points = userPointRepo.countPointsByUserIdAndPointGreaterThanZero(userId);

		return (null == points) ? 0 : points;
	}
	
	/**
	 * 获取积分明细
	 * @param userId
	 * @return
	 */
	public List<DjUPoint> findPointByUserId(long userId)
	{
		Sort sort = new Sort(Direction.DESC, "id");
		return userPointRepo.findByUid(userId, sort);
	}
	
	/**
	 * 增加积分
	 * @param user
	 * @param point
	 * @param detail
	 * @return
	 */
	public DjUPoint addPoint(DjUser user, int point, String detail)
	{
		DjUPoint p = new DjUPoint();
		
		p.setDetail(detail);
		p.setPoint(point);
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
		
		return userPointRepo.save(p);
	}
	
	/**
	 * 签到积分
	 * @param user
	 * @return
	 */
	public Boolean signPoint(DjUser user)
	{
		if(!isSignPoint(user.getId()))
		{
			DjUPoint p = new DjUPoint();
			DjUser djUser = userRepo.findOne(user.getId());
			p.setDetail("签到积分");
			p.setPoint(20);
			p.setUid(user.getId());
			p.setTotalPoint(djUser.getTotalPoint() + 20);
			p.setPointTime(new Date());
			p.setType(1);
			userPointRepo.save(p);
			djUser.setTotalPoint(p.getTotalPoint());
			userRepo.save(djUser);
			return true;
		}
		return false;
	}
	public Boolean isSignPoint(Long uid)
	{
		long current=System.currentTimeMillis();//当前时间毫秒数  
        long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        
        Date zeroDate = new Date(zero);
        Date nightDate = new Date(twelve);
		List<DjUPoint> pointList = userPointRepo.findByPointTimeBetweenAndUid(zeroDate, nightDate,uid);
		if(pointList == null || pointList.size() <= 0)
		{
			return false;
		}
		return true;
	}

}
