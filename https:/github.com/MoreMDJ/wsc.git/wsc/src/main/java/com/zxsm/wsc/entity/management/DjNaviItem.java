package com.zxsm.wsc.entity.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 前台导航菜单
 * 
 * @author maeing
 *
 */

@Entity
public class DjNaviItem extends DjBaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    // 名称
    @Column(length = 100)
    private String title;
    
    // 类型 0: 触屏版导航菜单
    @Column
    private Long categoryId;
    
    // 图片地址
    @Column(length = 250)
    private String imgUrl;
    
    // 排序号
    @Column
    private Double sortId;

    // 是否使能
    @Column
    private Boolean isEnable;
    
    // 链接地址
    @Column(length = 250)
    private String linkUrl;
    
    // 新窗口
    @Column
    private Boolean isBlank;
    
    // 简介
    @Column
    private String summary;

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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Boolean getIsBlank() {
		return isBlank;
	}

	public void setIsBlank(Boolean isBlank) {
		this.isBlank = isBlank;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
