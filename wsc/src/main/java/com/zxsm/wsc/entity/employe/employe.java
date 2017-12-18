package com.zxsm.wsc.entity.employe;

import javax.persistence.Column;

import com.zxsm.wsc.entity.common.DjBaseEntity;

public class employe extends DjBaseEntity
{
	@Column
	private String eNo;
	
	@Column
	private String eTel;
	
	@Column
	private String password;

	public String geteNo() {
		return eNo;
	}

	public void seteNo(String eNo) {
		this.eNo = eNo;
	}

	public String geteTel() {
		return eTel;
	}

	public void seteTel(String eTel) {
		this.eTel = eTel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
