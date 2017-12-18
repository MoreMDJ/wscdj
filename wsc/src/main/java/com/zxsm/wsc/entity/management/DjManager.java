package com.zxsm.wsc.entity.management;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 管理员
 * 
 * @author maeing
 *
 */

@Entity
public class DjManager extends DjBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 类型
    @Column
    private Long roleId;
    
    @Column(length=20)
    private String roleTitle;
    
    // 用户名
    @Column(length=20)
    private String username;
    
    // 密码
    @Column(length=50)
    private String password;

    // 姓名
    @Column
    private String realName;
    
    // 电话
    @Column
    private String telephone;
    
    // 邮箱
    @Column
    private String email;
    
    // 本次登录IP
    @Column
    private String ip;
    
    // 上次登录IP
    @Column
    private String lastLoginIp;
    
    // 上次登录时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;
    
    // 本次登录时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;
    
    // 是否开启
    @Column
    private Boolean isEnable;
    
    // 排序号
    @Column
    private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleTitle() {
		return roleTitle;
	}

	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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
	
}
