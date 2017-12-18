package com.zxsm.wsc.entity.ad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 广告
 * 
 * @author maeing
 *
 */

@Entity
public class DjAd {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    // 名称
    @Column(length = 100)
    private String title;
    
    // 副标题
    @Column(length = 250)
    private String subtitle;
    
    // 类型
    @Column
    private Long categoryId;
    
    // 类型标题
    @Column(length = 100)
    private String categoryTitle;
    
    // 宽度
    @Column
    private Integer width;
    
    // 高度
    @Column
    private Integer height;
    
    // 状态 0: 显示 1: 不显示
    @Column
    private Integer statusId;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 图片地址
    @Column(length = 100)
    private String imgUri;
    
    // 链接地址
    @Column(length = 250)
    private String linkUri;
    
    // 备注
    @Column(length = 250)
    private String mark;

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

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
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

	public String getLinkUri() {
		return linkUri;
	}

	public void setLinkUri(String linkUri) {
		this.linkUri = linkUri;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
