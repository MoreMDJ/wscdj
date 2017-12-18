package com.zxsm.wsc.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.user.DjUScan;
import com.zxsm.wsc.repository.goods.DjGoodsRepo;
import com.zxsm.wsc.repository.user.DjUScanRepo;

@Service
@Transactional
public class DjUScanService
{
	@Autowired
	private DjUScanRepo uScanRepo;

	@Autowired
	private DjGoodsRepo goodsRepo;

	/**
	 * 统计浏览总数
	 * 
	 * @param userId
	 * @return
	 */
	public int countScansByUid(long userId)
	{
		Integer scans = uScanRepo.countByUid(userId);

		return (null == scans) ? 0 : scans;
	}



	/**
	 * 添加浏览
	 * 
	 * @param userId
	 * @param priceId
	 * @return 1: 添加浏览
	 */
	public int addScanByUid(long uid, long gid)
	{
		goodsRepo.updataView(gid);
		// 没有浏览
		DjUScan scan = uScanRepo.findFirstByUidAndGid(uid, gid);
		if (null == scan)
		{
			scan = new DjUScan();
			scan.setGid(gid);
			scan.setUid(uid);
			uScanRepo.save(scan);
			return 1;
		} else {
			uScanRepo.save(scan);
			return 0;
		}
	}

	/**
	 * 查找用户的所有浏览
	 * 
	 * @param userId
	 * @return
	 */
	public List<DjGoods> findScanByUid(long userId)
	{

		Sort sort = new Sort(Direction.ASC, "id");
		List<Long> gids = uScanRepo.findGidByUid(userId, sort);

		if (gids.size() == 0)
		{
			return null;
		}

		return goodsRepo.findByIdIn(gids, sort);
	}
	
	public Boolean deleteByUidAndGid(Long uid,Long gid)
	{
		DjUScan scan = uScanRepo.findFirstByUidAndGid(uid, gid);
		if(scan != null)
		{
			uScanRepo.delete(scan);
			return true;
		}
		return false;
		
	}
}
