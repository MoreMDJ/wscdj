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

@Entity
public class DjUser extends DjBaseEntity
{
	public static final Integer USex_MAN = 1;
	public static final Integer USex_FEMALE=2;
	
	public static final Integer UStatus_Enable=1;//可以用
	public static final Integer UStatus_Disable=2;//不可用
	
	
	public static final String sUsername	= "username";
	public static final String sMobile		= "mobile";
	public static final String sStatus		= "status";
	public static final String sRealName	= "realName";
	public static final String sNickname	= "nickname";
	public static final String sURole		= "uRole";
	
	
	// 用户Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 用户名
	@NotEmpty(message = "用户名不能为空")
	@Column(nullable = false, unique = true,length = 50)
	private String username;
	
	// 手机号
	@Column(length = 20)
	private String mobile;
	
	// 密码
	@NotEmpty(message = "密码不能为空")
	@Column(nullable = false, length = 50)
	private String password;
	
	// 用户状态 1正常 2待审核 3禁用
	@Column
	private Integer status;
	
	// 用户创建 0:微信端手机号注册 1:微信二维码注册 2:永久分销二维码用户(后台注册,门店)
	@Column
	private Integer uType;
	
	// 用户角色 1：门店 2: 店员 3：会员
	@Column
	private Integer uRole;
	
	// 真实姓名
	@Column(length = 20)
	private String realName;
	
	// 昵称
	@Column(length = 100)
	private String nickname;

	// 用户头像
	@Column(length = 150)
	private String headImage;

	// 性别 1男 2 女 3保密
	@Column
	private Integer sex;

	// 生日
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	// 身份证号码
	@Column(length = 20)
	private String identity;

	// 电子邮箱
	@Column(length = 50)
	private String email;

	// 用户等级
	@Column
	private Integer uLevelId;

	// 用户等级名称
	@Column(length = 20)
	private String uLevelTitle;

	// 总消费
	@Column
	private Double allPayed;
	
	// 总积分
	@Column
	private Integer totalPoint;
	
	// SessionId
	@Column(length = 50)
	private String loginSession;
	
	// 是否登陆
	@Column
	private Boolean isLogin;
	
	// 微信openid
	@Column(length = 50)
	private String openid;
	
	// 微信二维码Url
	@Column(length = 255)
	private String qrcodeUrl;
	
	// 微信二维码更新时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date qrcodeUpdate;
	
	// 最后登录时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	
	// 最后访问时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastVisitTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getuType() {
		return uType;
	}

	public void setuType(Integer uType) {
		this.uType = uType;
	}

	public Integer getuRole() {
		return uRole;
	}

	public void setuRole(Integer uRole) {
		this.uRole = uRole;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getuLevelId() {
		return uLevelId;
	}

	public void setuLevelId(Integer uLevelId) {
		this.uLevelId = uLevelId;
	}

	public String getuLevelTitle() {
		return uLevelTitle;
	}

	public void setuLevelTitle(String uLevelTitle) {
		this.uLevelTitle = uLevelTitle;
	}

	public Double getAllPayed() {
		return allPayed;
	}

	public void setAllPayed(Double allPayed) {
		this.allPayed = allPayed;
	}

	public String getLoginSession() {
		return loginSession;
	}

	public void setLoginSession(String loginSession) {
		this.loginSession = loginSession;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public Date getQrcodeUpdate() {
		return qrcodeUpdate;
	}

	public void setQrcodeUpdate(Date qrcodeUpdate) {
		this.qrcodeUpdate = qrcodeUpdate;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	
	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	public void encryptionPassword()
	{
		this.password = StringTools.encryptionPassword(this.username, password);
	}
	
	public Boolean verityPassword(String password)
	{
		if(StringUtils.isBlank(password))
			return false;
		String passwordEnCrytion = StringTools.encryptionPassword(this.username, password);
		if(this.password.equals(passwordEnCrytion))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "DjUser [id=" + id + ", username=" + username + ", mobile=" + mobile + ", password=" + password
				+ ", status=" + status + ", uType=" + uType + ", realName=" + realName
				+ ", nickname=" + nickname + ", headImage=" + headImage + ", sex=" + sex + ", birthday=" + birthday
				+ ", identity=" + identity + ", email=" + email + ", uLevelId=" + uLevelId + ", uLevelTitle="
				+ uLevelTitle + ", allPayed=" + allPayed + ", loginSession=" + loginSession + ", isLogin=" + isLogin
				+ ", lastLoginTime=" + lastLoginTime + ", lastVisitTime=" + lastVisitTime + "]";
	}
	
}
