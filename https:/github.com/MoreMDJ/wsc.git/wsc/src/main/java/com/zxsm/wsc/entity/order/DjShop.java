package com.zxsm.wsc.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 门店
 * 
 * @author maeing
 *
 */

@Entity
public class DjShop extends DjBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 名称
    @Column
    private String title;
    
    // 星级
    @Column
    private Integer stars;
    
    // 店铺类型 0: 超市 1: 服务商
    @Column
    private Integer type;
    
    // 详细地址地址
    @Column
    private String address;
    
    // 付款方式
    @Column
    private String payType;
    
    // 营业时间
    @Column
    private String openTimeSpan;
    
    // 客服电话
    @Column
    private String serviceTele;
    
    // 投诉电话
    @Column
    private String complainTele;
    
    // 省
    @Column
    private String province;
    
    // 市
    @Column
    private String city;
    
    // 区
    @Column
    private String district;
    
    private String fullAddress;
    
    // 状态 1:显示 2:不显示 3:禁用
    @Column
    private Integer status;
    
    // 排序数字
    @Column
    private Double sortId;
    
    // 经度
    @Column
    private Double longitude;
    
    // 纬度
    @Column
    private Double latitude;
    
    // 描述说明
    @Column
    private String info;
    
    // 图片地址
    @Column
    private String imgUrl;
    
    // 轮播展示图片，多张图片以,隔开
    @Column
    private String showPictures;
    
    // 登录名
    @Column
    private String username;
    
    // 登录密码
    @Column
    private String password;

    // 客服qq
    @Column
    private String qq;
    
    // 支持服务
    @Column
    private String serviceIdStr;
    
    // 月售数量
    @Column
    private Integer soldEveryMonth;
    
    // 标签
    @Column
    private String tags;

    @Column
    private Boolean isPromotion1;
    
    @Column
    private Boolean isPromotion2;
    
    @Column
    private Boolean isPromotion3;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOpenTimeSpan() {
		return openTimeSpan;
	}

	public void setOpenTimeSpan(String openTimeSpan) {
		this.openTimeSpan = openTimeSpan;
	}

	public String getServiceTele() {
		return serviceTele;
	}

	public void setServiceTele(String serviceTele) {
		this.serviceTele = serviceTele;
	}

	public String getComplainTele() {
		return complainTele;
	}

	public void setComplainTele(String complainTele) {
		this.complainTele = complainTele;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getShowPictures() {
		return showPictures;
	}

	public void setShowPictures(String showPictures) {
		this.showPictures = showPictures;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getServiceIdStr() {
		return serviceIdStr;
	}

	public void setServiceIdStr(String serviceIdStr) {
		this.serviceIdStr = serviceIdStr;
	}

	public Integer getSoldEveryMonth() {
		return soldEveryMonth;
	}

	public void setSoldEveryMonth(Integer soldEveryMonth) {
		this.soldEveryMonth = soldEveryMonth;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Boolean getIsPromotion1() {
		return isPromotion1;
	}

	public void setIsPromotion1(Boolean isPromotion1) {
		this.isPromotion1 = isPromotion1;
	}

	public Boolean getIsPromotion2() {
		return isPromotion2;
	}

	public void setIsPromotion2(Boolean isPromotion2) {
		this.isPromotion2 = isPromotion2;
	}

	public Boolean getIsPromotion3() {
		return isPromotion3;
	}

	public void setIsPromotion3(Boolean isPromotion3) {
		this.isPromotion3 = isPromotion3;
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
