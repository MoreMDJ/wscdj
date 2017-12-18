package com.zxsm.wsc.common.tencent.entity;

public class JsTicketResponse {
	
	private String ticket;
	private String expires_in;
	private String errcode;
	private String errmsg;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	@Override
	public String toString() {
		return "JsTicketResponse [ticket=" + ticket + ", expires_in=" + expires_in + ", errcode=" + errcode
				+ ", errmsg=" + errmsg + "]";
	}

}
