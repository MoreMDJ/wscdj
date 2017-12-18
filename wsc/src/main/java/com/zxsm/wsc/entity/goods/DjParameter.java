package com.zxsm.wsc.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 属性表
 * 
 * @author Sharon
 *
 */

@Entity
public class DjParameter extends DjBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 分类，方便进行查找
	@Column
	private Long categoryId;
	
	// 分类名称
	@Column
    private String categoryTitle;
	
	// 分类树
	@Column
    private String categoryTree;
	
	// 属性名称
	@Column
	private String title;
	
	// 0: 手动输入 1: 用户选择
    @Column
    private Integer inputType;
    
    // 是否是多值参数
    @Column
    private Boolean isMultiple;
    
    // 是否前台进行筛选
    @Column
    private Boolean isSearchable;
    
    // 值列表，以逗号隔开
    @Column
    private String valueList;
    
    // 排序号
    @Column
    private Double sortId;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCategoryTree() {
		return categoryTree;
	}

	public void setCategoryTree(String categoryTree) {
		this.categoryTree = categoryTree;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getInputType() {
		return inputType;
	}

	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}

	public Boolean getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(Boolean isMultiple) {
		this.isMultiple = isMultiple;
	}

	public Boolean getIsSearchable() {
		return isSearchable;
	}

	public void setIsSearchable(Boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public String getValueList() {
		return valueList;
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
}
