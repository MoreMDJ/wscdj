package com.zxsm.wsc.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 支付方式
 * 
 * @author maeing
 *
 */

@Entity
public class DjPayType extends DjBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 支付名称
    @Column(length = 50)
    private String title;
    
    // 线上付款
    @Column
    private Boolean isOnlinePay;
    
    // 是否启用
    @Column
    private Boolean isEnable;
    
    // 排序数字
    @Column
    private Double sortId;
    
    // 显示图标
    @Column(length = 100)
    private String imgUri;
    
    // 描述说明
    @Column(length = 250)
    private String info;
    
    // 编码
    @Column(length = 50)
    private String code;

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

	public Boolean getIsOnlinePay() {
		return isOnlinePay;
	}

	public void setIsOnlinePay(Boolean isOnlinePay) {
		this.isOnlinePay = isOnlinePay;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
