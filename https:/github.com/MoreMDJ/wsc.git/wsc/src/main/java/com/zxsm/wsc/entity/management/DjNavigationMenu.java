package com.zxsm.wsc.entity.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 导航菜单栏
 * 
 * @author maeing
 *
 */

@Entity
public class DjNavigationMenu extends DjBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 名称
    @Column(length=100)
    private String name;
    
    // 标题
    @Column(length=100)
    private String title;
    
    // 图标ID
    @Column(length=250)
    private String iconUri;
    
    // 链接地址
    @Column(length=250)
    private String linkUri;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 父级菜单
    @Column
    private Long parentId;
    
    // 所在层数
    @Column
    private Integer layerId;

    // 是否开启
    @Column
    private Boolean isEnable;
    
    //备注
    @Column (length = 250)
    private String remark;
    
    @Column (length = 500)
    private String ActionType;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconUri() {
		return iconUri;
	}

	public void setIconUri(String iconUri) {
		this.iconUri = iconUri;
	}

	public String getLinkUri() {
		return linkUri;
	}

	public void setLinkUri(String linkUri) {
		this.linkUri = linkUri;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLayerId() {
		return layerId;
	}

	public void setLayerId(Integer layerId) {
		this.layerId = layerId;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActionType() {
		return ActionType;
	}

	public void setActionType(String actionType) {
		ActionType = actionType;
	}

}
