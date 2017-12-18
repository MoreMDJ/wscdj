package com.zxsm.wsc.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.user.DjUCollect;
import com.zxsm.wsc.repository.goods.DjGoodsRepo;
import com.zxsm.wsc.repository.user.DjUCollectRepo;

@Service
@Transactional
public class DjUCollectService
{
	@Autowired
	private DjUCollectRepo uCollectRepo;

	@Autowired
	private DjGoodsRepo goodsRepo;

	/**
	 * 统计收藏总数
	 * 
	 * @param userId
	 * @return
	 */
	public int countCollectsByUid(long uid)
	{
		Integer collects = uCollectRepo.countByUid(uid);

		return (null == collects) ? 0 : collects;
	}
	
	public void delete(Long id)
	{
		uCollectRepo.delete(id);
	}

	public Boolean deleteByUserIdAndGid(Long uid,Long gid)
	{
		DjUCollect collect = uCollectRepo.findFirstByUidAndGid(uid, gid);
		if(collect != null)
		{
			uCollectRepo.delete(collect);
			return true;
		}
		return false;
		
	}


	/**
	 * 切换收藏状态
	 * 
	 * @param userId
	 * @param priceId
	 * @return 1: 添加收藏 0: 取消收藏
	 */
	public int toggleCollectByUserId(long uid, long gid)
	{
		// 没有收藏
		DjUCollect collect = uCollectRepo.findFirstByUidAndGid(uid, gid);
		if (null == collect)
		{
			collect = new DjUCollect();
			collect.setGid(gid);
			collect.setUid(uid);
			uCollectRepo.save(collect);
			return 1;
		} else {
			uCollectRepo.delete(collect);
			return 0;
		}
	}

	/**
	 * 查找是否已收藏某商品
	 * 
	 * @param userId
	 * @param priceId
	 * @return
	 */
	public Boolean findCollectByUidAndGid(long userId, long priceId)
	{
		DjUCollect collect = uCollectRepo.findFirstByUidAndGid(userId, priceId);
		if(collect == null)
			return false;
		else
			return true;
	}
	
	

	/**
	 * 查找用户的所有收藏
	 * 
	 * @param userId
	 * @return
	 */
	public List<DjGoods> findCollectByUserId(long userId)
	{

		Sort sort = new Sort(Direction.DESC, "initDate");
		List<Long> gids = uCollectRepo.findGidByUid(userId, sort);

		if (gids.size() == 0)
		{
			return null;
		}

		return goodsRepo.findByIdIn(gids, sort);
	}
}
