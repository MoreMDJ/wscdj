package com.zxsm.wsc.entity.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 管理员权限
 * 
 * @author maeing
 *
 */

@Entity
public class DjPermission extends DjBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 导航栏名称
    @Column(length=100)
    private String navName;
    
    @Column(length = 20)
    private String actionType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNavName() {
		return navName;
	}

	public void setNavName(String navName) {
		this.navName = navName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
    
}