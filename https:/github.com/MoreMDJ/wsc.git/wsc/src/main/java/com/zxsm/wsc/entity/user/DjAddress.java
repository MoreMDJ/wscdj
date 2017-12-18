package com.zxsm.wsc.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;


/**
 * 地址
 * 
 * 定义了对地址的管理
 * 
 * @author maeing
 *
 */

@Entity
@Table
public class DjAddress
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 用户ID
	@Column
	private Long uid;
	
	// 省
	@Column(length = 50)
	private String province;
	
	// 市
	@Column(length = 50)
	private String city;
	
	// 区
	@Column(length = 50)
	private String district;
	
	// 详细地址
	@Column(length = 150)
	private String address;

	// 收货人姓名
	@Column(length = 50)
	private String acceptName;
	
	// 收货人移动电话
	@Column(length = 20)
	private String telphone;
	
	// 收货人邮箱
    @Column(length = 50)
    private String email;
	
	// 是否为默认地址
	@Column
	private Boolean isDefault;
	
	// 排序号
    @Column
    private Double sortId;
    
    private String fullAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getFullAddress()
	{
		if(StringUtils.isNotBlank(province))
		{
			if(province.contains("市"))
			{
				return province + (StringUtils.isBlank(district) ? "" : district) + (StringUtils.isBlank(address)?"":address);
			}
			else
				return province + (StringUtils.isBlank(city) ? "" : city) +(StringUtils.isBlank(district) ? "" : district) + (StringUtils.isBlank(address)?"":address);
		}
		
		return fullAddress;
	}
    
}
