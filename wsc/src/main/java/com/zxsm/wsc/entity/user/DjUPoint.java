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
 * 用户积分
 * 
 * @author maeing
 *
 */

@Entity
public class DjUPoint extends DjBaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	// 用户名
	@Column
	private Long uid;
	
	// 积分时间
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date pointTime;
	
	// 积分额度
	@Column
	private Integer point;
	
	// 当前积分总额
	@Column
	private Integer totalPoint;
	
	// 涉及订单号
    @Column(length = 50)
    private String orderNumber;
    
    // 详情
    @Column(length = 10)
    private String detail;
	
    // 排序号
    @Column
    private Long sortId;
    
    // 积分类型 1 签到积分
    @Column 
    private Integer type;

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

	public Date getPointTime() {
		return pointTime;
	}

	public void setPointTime(Date pointTime) {
		this.pointTime = pointTime;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
