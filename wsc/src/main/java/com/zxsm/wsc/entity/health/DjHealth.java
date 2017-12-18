package com.zxsm.wsc.entity.health;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;

@Entity
public class DjHealth extends DjBaseEntity {
	
	public final static Integer Type_pressure	= 0; //血压
	public final static Integer Type_sugar		= 1; //血糖
	public final static Integer Type_weight		= 2; //体重
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private Integer type; // 0 : 血压 1：血糖 2：体重
	
	@Column
	private String username;
	
	@Column
	private Long uid; // 用户id
	
	@Column
	private Long logUid; // 帮会员记录的用户id：店员 
	
	@Column
	private Double value1;
	
	@Column
	private Double value2;
	
	@Column
	private Double value3;
	
	@Column
	private Double value4;
	
	@Column
	private Double value5;
	
	@Column
	private String attribute1;
	
	@Column
	private String attribute2;
	
	@Column
	private String attribute3;
	
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date addDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getLogUid() {
		return logUid;
	}

	public void setLogUid(Long logUid) {
		this.logUid = logUid;
	}

	public Double getValue1() {
		return value1;
	}

	public void setValue1(Double value1) {
		this.value1 = value1;
	}

	public Double getValue2() {
		return value2;
	}

	public void setValue2(Double value2) {
		this.value2 = value2;
	}

	public Double getValue3() {
		return value3;
	}

	public void setValue3(Double value3) {
		this.value3 = value3;
	}

	public Double getValue4() {
		return value4;
	}

	public void setValue4(Double value4) {
		this.value4 = value4;
	}

	public Double getValue5() {
		return value5;
	}

	public void setValue5(Double value5) {
		this.value5 = value5;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
