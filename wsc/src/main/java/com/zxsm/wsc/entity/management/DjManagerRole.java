package com.zxsm.wsc.entity.management;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 管理员角色
 * 
 * @author maeing
 *
 */

@Entity
public class DjManagerRole extends DjBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 角色类型 0: 超级管理员 1: 系统用户
    @Column
    private Long categoryId;
    
    // 名称
    @Column(length = 20)
    private String title;
    
    // 权限 [navigation_menu_id1], [navigation_menu_id2]
    @Column
    @Lob
    private String permission;
    
    // 角色权限
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true)
    @JoinColumn(name="roleId")
    private List<DjPermission> permissions;
    
    // 排序字段
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<DjPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<DjPermission> permissions) {
		this.permissions = permissions;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

}
