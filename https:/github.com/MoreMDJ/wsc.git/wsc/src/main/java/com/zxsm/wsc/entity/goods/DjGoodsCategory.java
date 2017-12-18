package com.zxsm.wsc.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 商品类型
 * 
 * @author maeing
 *
 */

@Entity
public class DjGoodsCategory extends DjBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 类型名称
	@Column(length = 100)
	private String title;
	
	// 是否显示
	@Column
	private Boolean isEnable;
	
	// 首页展示
	@Column
	private Boolean isGuide;
	
	// 父类型
	@Column
	private Long parentId;
	
	// 父类型列表
    @Column(length = 100)
    private String parentTree;

    // 层级
    @Column
    private Integer layerId;
    
    // 店铺分类 0: 商品分类  1: 服务分类
    @Column
    private Integer typeId;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 关联的参数类型ID
    @Column
    private Long paramCategoryId;
    
    // 链接地址
    @Column(length = 250)
    private String linkUri;
    
    // 图片地址
    @Column(length = 100)
    private String imgUri;
    
    // 类别介绍
    @Column(length = 250)
    private String content;

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

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsGuide() {
		return isGuide;
	}

	public void setIsGuide(Boolean isGuide) {
		this.isGuide = isGuide;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentTree() {
		return parentTree;
	}

	public void setParentTree(String parentTree) {
		this.parentTree = parentTree;
	}

	public Integer getLayerId() {
		return layerId;
	}

	public void setLayerId(Integer layerId) {
		this.layerId = layerId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Long getParamCategoryId() {
		return paramCategoryId;
	}

	public void setParamCategoryId(Long paramCategoryId) {
		this.paramCategoryId = paramCategoryId;
	}

	public String getLinkUri() {
		return linkUri;
	}

	public void setLinkUri(String linkUri) {
		this.linkUri = linkUri;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
