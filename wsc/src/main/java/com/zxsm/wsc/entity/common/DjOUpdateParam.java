package com.zxsm.wsc.entity.common;

import java.math.BigDecimal;

/**
 * 订单地址信息参数
 * @author maeing
 *
 */
public class DjOUpdateParam
{
	public String orderNo;
    public String shippingName; 
    public String province;
    public String city;
    public String district;
    public String detailAddress;
    public String shippingPhone;
    public String remark;
    public BigDecimal express;
    public String logisticTitle;
    public String logisticNo;
    
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getShippingPhone() {
		return shippingPhone;
	}
	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getExpress() {
		return express;
	}
	public void setExpress(BigDecimal express) {
		this.express = express;
	}
	public String getLogisticTitle() {
		return logisticTitle;
	}
	public void setLogisticTitle(String logisticTitle) {
		this.logisticTitle = logisticTitle;
	}
	public String getLogisticNo() {
		return logisticNo;
	}
	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}
}
