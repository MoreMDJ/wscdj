package com.zxsm.wsc.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.common.UtilsTools.StringTools;
import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 
 * 用户实体类
 * 
 * @author maeing
 *
 */

public class DjUserAndRelation
{
	
	// 用户名
	private String username;
	
	// 手机号
	private String mobile;
	
	// 用户状态 1正常 2待审核 3禁用
	@Column
	private Integer status;
	
	// 真实姓名
	private String realName;
	
	// 昵称
	@Column(length = 100)
	private String nickname;

	// 用户头像
	private String headImage;
	
	private Date lastLogin;
	
	//上级用户id
	private Long pid;
	
	// 是否开启分销
	private Boolean enable;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public DjUserAndRelation(String username, String mobile, Integer status, String realName, String nickname,
			String headImage, Date lastLogin, Long pid, Boolean enable) {
		super();
		this.username = username;
		this.mobile = mobile;
		this.status = status;
		this.realName = realName;
		this.nickname = nickname;
		this.headImage = headImage;
		this.lastLogin = lastLogin;
		this.pid = pid;
		this.enable = enable;
	}

	public DjUserAndRelation() {
		super();
	}

	@Override
	public String toString() {
		return "DjUserAndRelation [username=" + username + ", mobile=" + mobile + ", status=" + status + ", realName="
				+ realName + ", nickname=" + nickname + ", headImage=" + headImage + ", lastLogin=" + lastLogin
				+ ", pid=" + pid + ", enable=" + enable + "]";
	}
	
}
