package com.zxsm.wsc.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 开店申请
 * 
 * @author maeing
 *
 */

@Entity
public class DjUFeedback extends DjBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 用户ID
	@Column
	private Long userId;
	
	// 用户名
	@Column
	private String username;
	
	// 反馈内容
	@Column
	private String content;
	
	// 留下的手机号
	@Column
	private String mobile;
	
	// 反馈时间
	@Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date feedTime;
	
	// 处理时间
	@Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date handleTime;
	
	// 状态 0: 未审核 1: 审核
	@Column
	private Integer statusId;

	@Column
	private Double sortId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getFeedTime() {
		return feedTime;
	}

	public void setFeedTime(Date feedTime) {
		this.feedTime = feedTime;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
}
