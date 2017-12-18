package com.zxsm.wsc.common.tencent.protocol.pay_protocol;

/**
 * User: rizenguo
 * Date: 2015/09/09
 * Time: 21:29
 */


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求被扫支付API返回的数据
 */
public class UnifiedOrderResData {

    //每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String device_info = "";
    private String nonce_str = "";
    private String sign = "";
    private String result_code = "";
    private String err_code = "";
    private String err_code_des = "";
    private String trade_type = "";
    private String prepay_id = "";
    private String code_url = "";
    private String return_code = "";
    private String return_msg = "";



	public String getAppid() {
		return appid;
	}



	public void setAppid(String appid) {
		this.appid = appid;
	}



	public String getMch_id() {
		return mch_id;
	}



	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}



	public String getDevice_info() {
		return device_info;
	}



	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}



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



	public String getResult_code() {
		return result_code;
	}



	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}



	public String getErr_code() {
		return err_code;
	}



	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}



	public String getErr_code_des() {
		return err_code_des;
	}



	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}



	public String getTrade_type() {
		return trade_type;
	}



	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}



	public String getPrepay_id() {
		return prepay_id;
	}



	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}



	public String getCode_url() {
		return code_url;
	}



	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}



	public String getReturn_code() {
		return return_code;
	}



	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}



	public String getReturn_msg() {
		return return_msg;
	}



	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	@Override
	public String toString() {
		return "UnifiedOrderResData [appid=" + appid + ", mch_id=" + mch_id + ", device_info=" + device_info
				+ ", nonce_str=" + nonce_str + ", sign=" + sign + ", result_code=" + result_code + ", err_code="
				+ err_code + ", err_code_des=" + err_code_des + ", trade_type=" + trade_type + ", prepay_id="
				+ prepay_id + ", code_url=" + code_url + ", return_code=" + return_code + ", return_msg=" + return_msg
				+ "]";
	}

	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
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
