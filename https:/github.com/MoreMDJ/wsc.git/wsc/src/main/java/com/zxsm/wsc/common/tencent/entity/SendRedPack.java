package com.zxsm.wsc.common.tencent.entity;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.zxsm.wsc.common.tencent.common.Configure;
import com.zxsm.wsc.common.tencent.common.RandomStringGenerator;


/**
 * 红包   请求参数
 * @author MDJ
 *
 */
public class SendRedPack {
	
	private String nonce_str = RandomStringGenerator.getRandomStringByLength(32);//随机字符串，不长于32位
	
	private String sign;//详见签名生成算法
	
	private String mch_billno;//商户订单号（每个订单号必须唯一）组成：mch_id+yyyymmdd+10位一天内不能重复的数字。接口根据商户订单号支持重入，如出现超时可再调用。
	
	private String mch_id = Configure.getMchid();//微信支付分配的商户号
	
	private String wxappid = Configure.getAppid();//微信分配的公众账号ID（企业号corpid即为此appId）。
	
	private String send_name;//红包发送者名称
	
	private String re_openid;//接受红包的用户，用户在wxappid下的openid
	
	private String total_amount;//付款金额，单位分
	
	private String total_num = "1";//红包发放总人数，total_num=1
	
	private String wishing;//红包祝福语
	
	private String client_ip = Configure.getIP();//调用接口的机器Ip地址
	
	private String act_name;//活动名称
	
	private String remark;//备注信息
	
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getWxappid() {
		return wxappid;
	}
	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public String getRe_openid() {
		return re_openid;
	}
	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString(){
		return "<xml>" +
				"<sign><![CDATA["+ sign +"]]></sign>" +
				"<mch_billno><![CDATA["+ mch_billno +"]]></mch_billno>" +
				"<mch_id><![CDATA["+ mch_id +"]]></mch_id>" +
				"<wxappid><![CDATA["+ wxappid +"]]></wxappid>" +
				"<send_name><![CDATA["+ send_name +"]]></send_name>" +
				"<re_openid><![CDATA["+ re_openid +"]]></re_openid>" +
				(StringUtils.isBlank(total_amount) == true ? "" : "<total_amount><![CDATA["+ total_amount +"]]></total_amount>") +
				"<total_num><![CDATA[1]]></total_num>" +
				"<wishing><![CDATA["+ wishing +"]]></wishing>" +
				"<client_ip><![CDATA["+ client_ip +"]]></client_ip>" +
				"<act_name><![CDATA["+ act_name +"]]></act_name>" +
				"<remark><![CDATA["+ remark +"]]></remark>" +
				"<nonce_str><![CDATA["+ nonce_str +"]]></nonce_str>" +
				"</xml>";
	}
	
	public String toXML() throws IllegalAccessException
	{
		Class<? extends Object> cls = this.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields)
		{
			if (f.get(this) == null || f.get(this) == "")
			{
				throw new NullPointerException(f.getName() + "不能为空,XML生成失败！");
			}
		}
		return "<xml>" +
				"<sign><![CDATA["+ sign +"]]></sign>" +
				"<mch_billno><![CDATA["+ mch_billno +"]]></mch_billno>" +
				"<mch_id><![CDATA["+ mch_id +"]]></mch_id>" +
				"<wxappid><![CDATA["+ wxappid +"]]></wxappid>" +
				"<send_name><![CDATA["+ send_name +"]]></send_name>" +
				"<re_openid><![CDATA["+ re_openid +"]]></re_openid>" +
				"<total_amount><![CDATA["+ total_amount +"]]></total_amount>" +
				"<total_num><![CDATA[1]]></total_num>" +
				"<wishing><![CDATA["+ wishing +"]]></wishing>" +
				"<client_ip><![CDATA["+ client_ip +"]]></client_ip>" +
				"<act_name><![CDATA["+ act_name +"]]></act_name>" +
				"<remark><![CDATA["+ remark +"]]></remark>" +
				"<nonce_str><![CDATA["+ nonce_str +"]]></nonce_str>" +
				"</xml>";
	}



}
