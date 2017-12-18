package com.zxsm.wsc.common.tencent.protocol.pay_protocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.zxsm.wsc.common.tencent.common.Configure;
import com.zxsm.wsc.common.tencent.common.RandomStringGenerator;
import com.zxsm.wsc.common.tencent.common.Signature;

public class WCPayRequestDate {
	private String appId = "";
	private String timeStamp = "";
	private String nonceStr = "";
	private String package_d = "";
	private String signType = "MD5";
	private String paySign = "";
	
	public WCPayRequestDate(String package_d) {
		super();
		this.package_d = "prepay_id=" + package_d;
		this.nonceStr = RandomStringGenerator.getRandomStringByLength(32);
		this.appId = Configure.getAppid();
		this.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		this.paySign = Signature.getSign(toMap());
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPackage_d() {
		return package_d;
	}
	public void setPackage_d(String package_d) {
		this.package_d = package_d;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                	if(field.getName().contains("package"))
                		map.put("package", obj);
                	else
                		map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
}
