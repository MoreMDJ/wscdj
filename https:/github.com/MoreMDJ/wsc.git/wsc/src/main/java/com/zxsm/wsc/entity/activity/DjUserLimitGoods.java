package com.zxsm.wsc.entity.activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

@Entity
public class DjUserLimitGoods extends DjBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private Long uid;
	
	@Column
	private Long gid;
	
	@Column
	private Integer number;

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

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public DjUserLimitGoods(Long uid, Long gid, Integer number) {
		super();
		this.uid = uid;
		this.gid = gid;
		this.number = number;
	}

	public DjUserLimitGoods() {
		super();
	}

}
