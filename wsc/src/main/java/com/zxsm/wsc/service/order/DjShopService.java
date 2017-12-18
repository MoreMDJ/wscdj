package com.zxsm.wsc.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.entity.order.DjShop;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.repository.order.DjShopRepo;

@Service
@Transactional
public class DjShopService {

	@Autowired
	DjShopRepo shopRepo;

	/**
	 * 分页查找所有店铺，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjShop> findShopAll(String province, String city, String district, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return shopRepo.findByProvinceContainingAndCityContainingAndDistrictContaining(province, city, district, pageRequest);
	}
	
	public List<DjShop> findShopByAdress(String province, String city, String district,String address)
	{
		Criteria<DjShop> criteria = new Criteria<DjShop>();
		criteria.add(Restrictions.eq("province", province, true));
		criteria.add(Restrictions.eq("city", city, true));
		criteria.add(Restrictions.eq("district", district, true));
		criteria.add(Restrictions.eq("address", address, true));
		return shopRepo.findAll(criteria);
	}

	public Page<DjShop> findShopById(long id)
	{

		PageRequest pageRequest = new PageRequest(0, 20, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return shopRepo.findById(id, pageRequest);
	}

	public List<DjShop> findShopAll()
	{

		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id"));
		return (List<DjShop>) shopRepo.findAll(sort);
	}
	
	public Page<DjShop> findShopAll(Integer page,Integer size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return shopRepo.findAll(pageRequest);
	}

	public List<DjShop> findShopByProvinceAndCityAndDistrict(String province,
			String city, String district)
	{
		if ("省份".equalsIgnoreCase(province))
		{
			province = "";
		}

		if ("地级市".equalsIgnoreCase(city))
		{
			city = "";
		}

		if ("市、县级市".equalsIgnoreCase(district))
		{
			district = "";
		}

		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(
				Direction.DESC, "id"));
		return shopRepo
				.findByProvinceContainingAndCityContainingAndDistrictContaining(
						province, city, district, sort);
	}

	/**
	 * 查找店铺，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjShop> searchShopAll(String keywords, String province,
			String city, String district, int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return shopRepo
				.findByTitleContainingAndProvinceContainingAndCityContainingAndDistrictContaining(
						keywords, province, city, district, pageRequest);
	}

	public List<DjShop> findShopByStatus(int status)
	{

		return shopRepo.findByStatus(status);
	}

	public DjShop findShopByUsername(String username)
	{

		List<DjShop> shopList = shopRepo.findByUsernameOrderByIdDesc(username);
		if (null != shopList)
		{
			return shopList.get(0);
		}
		return null;
	}

	/**
	 * 保存店铺
	 * 
	 * @param e
	 *            : 要保存的店铺
	 * @return
	 */
	public DjShop saveShop(DjShop e)
	{
		if (null == e)
		{
			return null;
		}

		// 设置showPictures

		return shopRepo.save(e);
	}

	/**
	 * 删除店铺，参数检查由调用函数完成
	 * 
	 * @param id
	 *            : 店铺ID
	 */
	public void deleteShop(long id)
	{
		shopRepo.delete(id);
	}

	/**
	 * 查找店铺
	 * 
	 * @param id
	 *            店铺ID
	 * @return
	 */
	public DjShop findShop(Long id)
	{
		if(id == null)
			return null;
		return shopRepo.findOne(id);
	}
}
