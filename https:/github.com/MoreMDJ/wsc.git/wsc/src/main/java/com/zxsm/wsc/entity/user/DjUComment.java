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
 * 用户评论
 * 
 * @author maeing
 *
 */

@Entity
public class DjUComment  extends DjBaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 发表评论的用户Id
    @Column
    private Long uid;
    
    // 昵称
    @Column
    private String nickname;    

    // 订单id
    @Column
    private Long orderId;
    
    // 订单编号
    @Column(length = 100)
    private String orderNo;

    // 被评论的ID
    @Column
    private Long gid;
    
    // 评论类型 0: 商品评论 1: 文章评论 2: 爱心活动评论
    @Column
    private Integer type;
    
    // 评论标题
    @Column(length = 100)
    private String title;
    
    // 评论内容
    @Column
    private String content;
    
    // 评论标签
    @Column
    private String tags;
    
    // 评论星级
    @Column
    private Integer stars;
    
    // 回复的评论ID
    @Column
    private Long parentId;
    
    // 是否已回复
    @Column
    private Boolean isReplied;
    
    // 评论回复
    @Column
    private String reply;
    
    // 回复时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date replyTime;
    
    // 用户头像
    @Column
    private String uHeadImg;
    
    // 显示状态 1: 显示 2: 不显示
    @Column
    private Integer status;
    
    // 图片，多张图片以,隔开
    @Column
    private String showPics;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsReplied() {
		return isReplied;
	}

	public void setIsReplied(Boolean isReplied) {
		this.isReplied = isReplied;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getuHeadImg() {
		return uHeadImg;
	}

	public void setuHeadImg(String uHeadImg) {
		this.uHeadImg = uHeadImg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShowPics() {
		return showPics;
	}

	public void setShowPics(String showPics) {
		this.showPics = showPics;
	}
	
}
