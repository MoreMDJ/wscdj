package com.zxsm.wsc.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 用户分销金额
 * 
 * @author maeing
 *
 */

@Entity
public class DjUCash extends DjBaseEntity
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
	private Date cashTime;
	
	// 积分额度
	@Column(scale = 2)
	private BigDecimal cash;
	
	// 当前积分总额
	@Column(scale = 2)
	private Integer totalCash;
	
	// 涉及订单号
    @Column(length = 50)
    private String orderNumber;
    
    // 详情
    @Column(length = 10)
    private String detail;
	
    // 排序号
    @Column
    private Long sortId;
    
    // 积分类型 1 分润
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
		return cashTime;
	}

	public void setPointTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public Integer getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(Integer totalCash) {
		this.totalCash = totalCash;
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

	public DjUCash() {
		super();
	}

	
	/**
	 * 默认分润 type = 1 
	 * @param uid
	 * @param cash
	 * @param orderNumber
	 */
	public DjUCash(Long uid, BigDecimal cash, String orderNumber) {
		super();
		this.uid = uid;
		this.cash = cash;
		this.orderNumber = orderNumber;
		this.type = 1;
		this.detail = "分润";
	}

	public DjUCash(Long uid, BigDecimal cash, String orderNumber, Integer type) {
		super();
		this.uid = uid;
		this.cash = cash;
		this.orderNumber = orderNumber;
		this.type = type;
	}

	@Override
	public String toString() {
		return "DjUCash [id=" + id + ", uid=" + uid + ", cashTime=" + cashTime + ", cash=" + cash + ", totalCash="
				+ totalCash + ", orderNumber=" + orderNumber + ", detail=" + detail + ", sortId=" + sortId + ", type="
				+ type + "]";
	}
	
}
