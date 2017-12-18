package com.zxsm.wsc.entity.activity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;
import com.zxsm.wsc.entity.order.DjOrderGoods;

@Entity
public class DjActivity extends DjBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 活动标题
	@Column(length = 100)
	private String title;
	
	// 活动标题
	@Column(length = 100)
	private String imgUrl;
	
	// 活动类型
	@Column(length = 100)
	private Integer type;
	
	// 商品数量
	@Column
	private Integer totalGoods;
	
	// 数量 购买种类数量
	@Column
	private Integer number;
	
	// 开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginTime;
	
	// 结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	// 限购商品
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true)
    @JoinColumn(name="activityId")
    private List<DjOrderGoods> limitGoods;
    
    // 已购商品人员
    @OneToMany
    @JoinColumn(name="actiId")
    private List<DjUserLimitGoods> buyUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTotalGoods() {
		return totalGoods;
	}

	public void setTotalGoods(Integer totalGoods) {
		this.totalGoods = totalGoods;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<DjOrderGoods> getLimitGoods() {
		return limitGoods;
	}

	public void setLimitGoods(List<DjOrderGoods> limitGoods) {
		this.limitGoods = limitGoods;
	}

	public List<DjUserLimitGoods> getBuyUser() {
		return buyUser;
	}

	public void setBuyUser(List<DjUserLimitGoods> buyUser) {
		this.buyUser = buyUser;
	}

}
