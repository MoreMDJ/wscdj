package com.zxsm.wsc.service.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.entity.user.DjAddress;
import com.zxsm.wsc.repository.user.DjAddressRepo;

@Service
@Transactional
public class DjAddressService
{

	@Autowired
	DjAddressRepo addressRepo;

	/**
	 * 查找所有地址，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public List<DjAddress> findAddressByUid(long userId)
	{

		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id"));
		return addressRepo.findByUid(userId, sort);
	}
	
	public DjAddress findFirstByUidAndId(Long uid,Long id)
	{
		if(uid == null || id == null)
			return null;
		return addressRepo.findFirstByUidAndId( uid, id);
	}

	public Integer countAddressByUid(long userId)
	{

		return addressRepo.countByUid(userId);
	}

	public DjAddress findDefaultAddressByUid(long userId)
	{

		DjAddress address = addressRepo.findFirstByUidAndIsDefaultTrue(userId);

		if (null == address)
		{
			List<DjAddress> addressList = findAddressByUid(userId);

			if (null == addressList || addressList.size() == 0)
			{
				return null;
			}
			DjAddress Naddress = addressList.get(0);
			Naddress.setIsDefault(true);
			
			return addressRepo.save(Naddress);
		}

		return address;
	}

	/**
	 * 保存地址
	 * 
	 * @param e
	 *            : 要保存的地址
	 * @return
	 */
	public DjAddress saveAddress(DjAddress address)
	{
		if (null == address)
		{
			return null;
		}

		// 当前地址为默认时，其他地址改为非默认
		if (null != address.getIsDefault() && address.getIsDefault() && null != address.getUid())
		{
			DjAddress daddress = addressRepo.findFirstByUidAndIsDefaultTrue(address.getUid());
			if(daddress != null)
			{
				daddress.setIsDefault(false);
				addressRepo.save(daddress);
			}
		}

		return addressRepo.save(address);
	}

	/**
	 * 保存地址列表
	 * 
	 * @param entities
	 * @return
	 */
	public List<DjAddress> saveAddress(List<DjAddress> entities)
	{
		return (List<DjAddress>) addressRepo.save(entities);
	}

	/**
	 * 删除地址，参数检查由调用函数完成
	 * 
	 * @param id
	 *            : 地址ID
	 */
	public void deleteAddress(long id)
	{
		addressRepo.delete(id);
	}

	/**
	 * 查找地址
	 * 
	 * @param id
	 *            地址ID
	 * @return
	 */
	public DjAddress findAddress(long id)
	{
		return addressRepo.findOne(id);
	}
	
	public void defaultAddress(Long uid,Long addressId,ModelMap map,HttpSession session)
	{
		if( addressId == null)
		{
			if(session.getAttribute(DjKeys.SESSION_ORDER_ADDRESS) != null)
				map.addAttribute("address", (DjAddress)session.getAttribute(DjKeys.SESSION_ORDER_ADDRESS));
			else
			{
				//收货地址
				DjAddress defaultAddress = findDefaultAddressByUid(uid);
				if(defaultAddress != null)
				{
					session.setAttribute(DjKeys.SESSION_ORDER_ADDRESS, defaultAddress);
					map.addAttribute("address", defaultAddress);
				}
			}
		}
		else
		{
			DjAddress address = findFirstByUidAndId(uid,addressId);
			session.setAttribute(DjKeys.SESSION_ORDER_ADDRESS, address);
			map.addAttribute("address", address);
		}
	}

}
