package com.zxsm.wsc.entity.user;

import com.zxsm.wsc.entity.common.DjBaseRequestParam;

public class DjUParam extends DjBaseRequestParam
{
	public String username;
	public String mobile;
	public String password;
	public String code;
	public Long msgId;
	public Long huid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public Long getHuid() {
		return huid;
	}
	public void setHuid(Long huid) {
		this.huid = huid;
	}
	
}
