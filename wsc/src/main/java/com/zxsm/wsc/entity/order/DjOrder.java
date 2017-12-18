package com.zxsm.wsc.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 订单
 *
 * 订单数据
 * 
 * @author maeing
 *
 */

@Entity
public class DjOrder extends DjBaseEntity
{
	
	public static final String sOrderNo			= "orderNo";
	public static final String sUsername		= "username";
	public static final String sPaymentTitle	= "paymentTitle";
	public static final String sIsOnlinePay		= "isOnlinePay";
	public static final String sPaymentStatus	= "paymentStatus";
	public static final String sStatus			= "status";
	public static final String sTypeId			= "typeId";
	public static final String sProvince		= "province";
	public static final String sCity			= "city";
	public static final String sDisctrict		= "disctrict";
	public static final String sAddress			= "address";
	public static final String sAcceptName		= "acceptName";
	public static final String sTelphone		= "telphone";
	public static final String sExpressStatus	= "expressStatus";
	public static final String sRemark			= "remark";
	public static final String sValidTime		= "validTime";
	public static final String sUserId			= "userId";
	public static final String sExpressTitle	= "expressTitle";	
	public static final String sShopTitle		= "shopTitle";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 订单号
	@Column(unique = true,length = 50)
	private String orderNo;

	// 用户id
	@Column
	private Long userId;
	
	@Column(length = 50)
	private String username;
	
	// 支付方式Id
	@Column
	private Long paymentId;

	// 支付方式名称
	@Column(length = 20)
	private String paymentTitle;

	// 是否在线付款
	@Column
	private Boolean isOnlinePay;
	
	// 支付状态 1未支付2已支付
	@Column
	private Integer paymentStatus;
	
	// 支付时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date paymentTime;
	
	// 订单状态 1生成订单,2确认订单,3完成订单,4取消订单,5作废订单
	@Column
	private Integer status;
	
	// 待确认 已确认(在线支付表示已付款) 已付款 待发货 待收货 完成 待评价 退货中 退货完成 
	private String statusTitle;
	
	// 1 全部订单 2生成订单,3确认订单,4待付款,5待发货,6待收货,7完成订单,8待评价,9取消订单,10作废订单
	private Integer statusCode;

	// 订单类型 1处方单,2销售单
	@Column
	private Integer typeId;

	// 邮政编码
	@Column(length = 20)
	private String postCode;

	// 拆分的收货地址分为：省 + 市 + 区县 + 详细地址
	// 省
	@Column(length = 50)
	private String province;

	// 市
	@Column(length = 50)
	private String city;

	// 区县
	@Column(length = 50)
	private String disctrict;
	
	private String fullAddress;

	// 详细地址
	@Column(length = 100)
	private String address;

	// 收货人姓名
	@Column(length = 20)
	private String acceptName;

	// 收货人联系电话
	@Column(length = 20)
	private String telphone;
	
	// 订单留言(用户留言)
	@Column(length = 250)
	private String message;

	// 配送方式名称
	@Column(length = 10)
	private String expressTitle;

	// 配送费用
	@Column(scale = 2)
	private BigDecimal expressFee;
	
	// 物流名称
	@Column(length = 50)
	private String logisticTitle;
	
	// 物流号
	@Column(length = 200)
	private String logisticNo;
	
	//自提门店名称
	@Column
	private String shopTitle;
	
	// 自提门店地址
	@Column(length = 250)
	private String shopAddress;
	
	// 自提门店Id
	@Column
	private Long shopId;
	
	// 发货状态 1未发货2已发货
	@Column
	private Integer expressStatus;

	// 是否退款
	@Column
	private Boolean isRefund;

	// 退款金额
	@Column(scale = 2)
	private BigDecimal refund;
	
	// 订单商品
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true)
    @JoinColumn(name="roderId")
    private List<DjOrderGoods> orderGoods;

	// 商品总金额(每个商品销售价之和)
	@Column(scale = 2)
	private BigDecimal goodsAmount;

	// 订单总金额(邮费+商品价格-优惠价格） 实际应该支付的价格
	@Column(scale = 2)
	private BigDecimal orderAmount;

	// 排序号
	@Column
	private Double sortId;

	// 订单备注(管理员备注)
	@Column(length = 250)
	private String remark;

	// 是否参加了促销
	@Column
	private Boolean isPromotion;
	
	// 有效时间(超过有效时间未支付将重新计算价格)
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date validTime;

	// 下单时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	// 确认时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;

	// 取消时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cancelTime;
	
	// 发货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date expressTime;

	// 收货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date receiveTime;

	// 完成时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date completeTime;

	// 取消申请时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cancelApplyTime;

	// 退款时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date refundTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentTitle() {
		return paymentTitle;
	}

	public void setPaymentTitle(String paymentTitle) {
		this.paymentTitle = paymentTitle;
	}

	public Boolean getIsOnlinePay() {
		return isOnlinePay;
	}

	public void setIsOnlinePay(Boolean isOnlinePay) {
		this.isOnlinePay = isOnlinePay;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public String getDisctrict() {
		return disctrict;
	}

	public void setDisctrict(String disctrict) {
		this.disctrict = disctrict;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getExpressTitle() {
		return expressTitle;
	}

	public void setExpressTitle(String expressTitle) {
		this.expressTitle = expressTitle;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
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

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(Integer expressStatus) {
		this.expressStatus = expressStatus;
	}

	public Boolean getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public List<DjOrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<DjOrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Date getCancelApplyTime() {
		return cancelApplyTime;
	}

	public void setCancelApplyTime(Date cancelApplyTime) {
		this.cancelApplyTime = cancelApplyTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	
	public String getStatusTitle() {
		if(status == 4)
			return "订单已取消";
		if(isOnlinePay == true)//线上支付
		{
			if(this.paymentStatus == 1)//未支付
			{
				if(status == 1)
					return "待付款";
				if(expressTitle.contains("自提"))
				{
					if(status == 2)
						return "待取货";
					if(status == 3)
						return "待评价";
				}
				else//配送
				{
					if(status == 2&& expressStatus == 1)
						return "待发货";
					else
						return "待收货";
				}
			}
			else//已支付
			{
				if(expressTitle.contains("自提"))
				{
					if(status == 2)
						return "待取货";
					if(status == 3)
						return "待评价";
				}
				else//配送
				{
					if(status == 2&& expressStatus == 1)
						return "待发货";
					else if(status == 2&& expressStatus == 2)
						return "待收货";
					else
						return "待评价";
				}
			}
		}
		else//线下支付
		{
			if(expressTitle.contains("自提"))
			{
				if(status == 1)
					return "待确认";
				if(status == 2)
					return "待取货";
				if(status == 3)
					return "待评价";
			}
			else//配送
			{
				if(status == 1)
					return "待确认";
				
				if(status == 2&& expressStatus == 1)
					return "待发货";
				else if(status == 2&& expressStatus == 2)
					return "待收货";
				else
					return "待评价";
			}
		}
		return statusTitle == null ? "错误": statusTitle;
	}
	// 1生成订单,2已确认订单,3待发货,4待收货 5.完成, 6代收款 ,7 待付款 8待发货 9待收货，10完成订单,11待评价,12取消订单,13作废订单
	// 14待取货
	public Integer getStatusCode()
	{
		if(status == 4)
			return 12;
		
		// 订单状态 1生成订单,2确认订单,3完成订单,4取消订单,5作废订单
		if(isOnlinePay == true)//线上支付
		{
			if(this.paymentStatus == 1)//未支付
			{
				if(status == 1)
					return 7;
				if(expressTitle.contains("自提"))
				{
					if(status == 2)
						return 14;
					if(status == 3)
						return 11;
				}
				else//配送
				{
					if(status == 2&& expressStatus == 1)
						return 8;
					else
						return 9;
				}
			}
			else//已支付
			{
				if(expressTitle.contains("自提"))
				{
					if(status == 2)
						return 14;
					if(status == 3)
						return 11;
				}
				else//配送
				{
					if(status == 2&& expressStatus == 1)
						return 8;
					else if(status == 2&& expressStatus == 2)
						return 9;
					else
						return 11;
				}
			}
		}
		else//线下支付
		{
			if(expressTitle.contains("自提"))
			{
				if(status == 1)
					return 1;
				if(status == 2)
					return 14;
				if(status == 3)
					return 11;
			}
			else//配送
			{
				if(status == 1)
					return 1;
				if(status == 2&& expressStatus == 1)
					return 3;
				else if(status == 2&& expressStatus == 2)
					return 4;
				else
					return 5;
			}
		}
		return statusCode;
	}

	public String getFullAddress()
	{
		if(StringUtils.isNotBlank(province))
		{
			if(province.contains("市"))
			{
				return province + (StringUtils.isBlank(disctrict) ? "" : disctrict) + (StringUtils.isBlank(address)?"":address);
			}
			else
				return province + (StringUtils.isBlank(city) ? "" : city) +(StringUtils.isBlank(disctrict) ? "" : disctrict) + (StringUtils.isBlank(address)?"":address);
		}
		
		return fullAddress;
	}
	
	/**
	 * 支付需要的金额 单位：分
	 * @return
	 */
	public String getPayAmt()
	{
		return ((Long)Math.round(this.orderAmount.doubleValue()*100)).toString();
	}
	
}
