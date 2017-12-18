package com.zxsm.wsc.entity.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class DjOParam
{
	public BigDecimal  totalPrice;
	
	public Integer[] quantitys;

	public Long[] ids; //购物车Id
	
	public Long[] gid; //商品Id

	public Integer[] ckIds;
	
	public String action;
	
	public String province;
	
	public String city;
	
	public String district;
	
	public Long shopId;
	
	public Long addressId;
	
	public String deliveryType;
	
	public String payCode;
	
	public String message;
	
	public Integer typeId;

	public Integer[] getQuantitys()
	{
		return quantitys;
	}

	public void setQuantitys(Integer[] quantitys)
	{
		this.quantitys = quantitys;
	}

	public Long[] getIds()
	{
		return ids;
	}

	public void setIds(Long[] ids)
	{
		this.ids = ids;
	}
	
	public Long[] getGid() {
		return gid;
	}

	public void setGid(Long[] gid) {
		this.gid = gid;
	}

	public Integer[] getCkIds() {
		return ckIds;
	}

	public void setCkIds(Integer[] ckIds) {
		this.ckIds = ckIds;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Boolean verity()
	{
		if(this.ids == null || this.ckIds == null || this.quantitys == null)
			return false;
		
		Integer idsLength = this.ids.length;
		Integer indexLength = this.ckIds.length;
		Integer quantityLength = this.quantitys.length;
		if(idsLength >= indexLength && idsLength == quantityLength)
			return true;
		else
			return false;
	}
	
	public Boolean submitVerity()
	{
		if(StringUtils.isBlank(this.deliveryType) || StringUtils.isBlank(this.payCode))
			return false;
		
		if(this.deliveryType.contains("自提"))
		{
			if(this.shopId == null)
				return false;
		}
		else if(this.addressId == null)
		{
			return false;
		}
		
		return true;
	}
	
	public Long[] chooseIds()
	{
		List<Long> cIds = new ArrayList<>();
		for (Integer index : ckIds)
		{
			cIds.add(ids[index]);
		}
		return cIds.toArray(new Long[cIds.size()]);
	}
	
	public Long[] chooseGids()
	{
		List<Long> gIds = new ArrayList<>();
		for (Integer index : ckIds)
		{
			gIds.add(gid[index]);
		}
		return gIds.toArray(new Long[gIds.size()]);
	}
	
	/**
	 *  ids 购物车id
	 *  quantity 商品数量
	 * @return
	 */
	public Map<String,Integer> idsAndQuantity()
	{
		Map<String,Integer> quantityMap = new HashMap<String,Integer>();
		for (Integer index : ckIds)
		{
			quantityMap.put(ids[index]+"", quantitys[index]);
		}
		return quantityMap;
	}
	
	public Map<String,Integer> gidsAndQuantity()
	{
		Map<String,Integer> quantityMap = new HashMap<String,Integer>();
		for (Integer index : ckIds)
		{
			quantityMap.put(gid[index]+"", quantitys[index]);
		}
		return quantityMap;
	}

}
