package com.zxsm.wsc.entity.doctor;

import com.zxsm.wsc.entity.common.DjBaseRequestParam;

public class DjDoctorParam extends DjBaseRequestParam
{
	public String cate;
	
	public String username;
	
	public String password;
	
	public String code;
	
	public Integer number;

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
