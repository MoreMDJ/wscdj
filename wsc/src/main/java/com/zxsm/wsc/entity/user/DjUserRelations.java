package com.zxsm.wsc.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 三级分销关系表
 * @author maeing
 *
 */
@Entity
public class DjUserRelations extends DjBaseEntity
{
	// Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 用户id
	@Column
	private Long uid;
	
	// 上级 id ： 0代表一级
	@Column
	private Long pid;
	
	// 下级 id ： 0代表没有下级
	@Column
	private Long cid;
	
	// 分销等级 ：0:不参与分销 1:一级分销 2:二级分销 3:三级分销 4:四级、没有分润
	@Column
	private Integer level;
	
	// 是否可用 true:可用 false:不可用
	@Column
	private Boolean enable;

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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public DjUserRelations(Long uid, Long pid, Long cid, Integer level, Boolean enable) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.cid = cid;
		this.level = level;
		this.enable = enable;
	}

	public DjUserRelations() {
		super();
	}

	@Override
	public String toString() {
		return "DjUserRelations [id=" + id + ", uid=" + uid + ", pid=" + pid + ", cid=" + cid + ", level=" + level
				+ ", enable=" + enable + "]";
	}
}
