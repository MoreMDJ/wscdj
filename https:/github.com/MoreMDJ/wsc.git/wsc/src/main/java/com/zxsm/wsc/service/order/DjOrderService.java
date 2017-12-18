package com.zxsm.wsc.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Criterion;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.common.SerchTools.Search;
import com.zxsm.wsc.common.UtilsTools.DjSMSUtils;
import com.zxsm.wsc.common.UtilsTools.StringTools;
import com.zxsm.wsc.entity.common.DjOUpdateParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.management.DjSystemConfig;
import com.zxsm.wsc.entity.order.DjCartGoods;
import com.zxsm.wsc.entity.order.DjExpressType;
import com.zxsm.wsc.entity.order.DjOParam;
import com.zxsm.wsc.entity.order.DjOrder;
import com.zxsm.wsc.entity.order.DjOrderGoods;
import com.zxsm.wsc.entity.order.DjPayType;
import com.zxsm.wsc.entity.order.DjShop;
import com.zxsm.wsc.entity.user.DjAddress;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.repository.order.DjOrderGoodsRepo;
import com.zxsm.wsc.repository.order.DjOrderRepo;
import com.zxsm.wsc.service.activity.DjActivityService;
import com.zxsm.wsc.service.goods.DjGoodsService;
import com.zxsm.wsc.service.management.DjSystemConfigService;
import com.zxsm.wsc.service.user.DjAddressService;
import com.zxsm.wsc.service.user.DjUCashService;
import com.zxsm.wsc.service.user.DjUserService;

@Service
@Transactional
public class DjOrderService
{
	
	private final Logger logger = Logger.getLogger(DjOrderService.class);

	@Autowired
	DjOrderRepo orderRepo;

	@Autowired
	DjOrderGoodsRepo orderGoodsRepo;

	@Autowired
	DjGoodsService goodsService;

	@Autowired
	DjUserService userService;
	
	@Autowired
	DjAddressService addressSvs;
	
	@Autowired
	DjExpressTypeService expressTypeSvs;
	
	@Autowired
	DjPayTypeService paytypeSvs;
	
	@Autowired
	DjShopService shopSvs;
	
	@Autowired
	DjCartGoodsService cartGoodsSvs;
	
	@Autowired
	DjUCashService cashSvs;
	
	@Autowired
	DjSystemConfigService configSvs;
	
	@Autowired
	DjActivityService activitySvs;

	/**
	 * 保存订单
	 * 
	 * @param e
	 *            : 要保存的订单
	 * @return
	 */
	public DjOrder saveOrder(DjOrder e)
	{
		if (null == e)
		{
			return null;
		}

		return orderRepo.save(e);
	}

	/**
	 * 删除订单，参数检查由调用函数完成
	 * 
	 * @param id : 订单ID
	 */
	public void deleteOrder(long id)
	{
		orderRepo.delete(id);
	}

	/**
	 * 查找订单
	 * 
	 * @param id
	 *            订单ID
	 * @return
	 */
	public DjOrder findOrder(long id)
	{
		return orderRepo.findOne(id);
	}
	
	public Page<DjOrder> searchOrder(List<Search> searchs,int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "modifyDate")).and(new Sort(Direction.DESC, "id")));
		Criteria<DjOrder> criteria = new Criteria<DjOrder>();
		criteria.add(Restrictions.initCriterion(searchs));
		return orderRepo.findAll(criteria, pageRequest);
	}
	
	/**
	 * 根据状态查找订单
	 * @param uid
	 * @param status
	 * @return
	 */
	public List<DjOrder> findByUidAndStatus(Long uid,Integer status)
	{
		if(uid == null || status == null)
			return null;
		Criteria<DjOrder> statusCriteria = initStatusCriteria(status, uid);
		return orderRepo.findAll(statusCriteria);
	}
	
	public Criteria<DjOrder> initStatusCriteria(Integer status,Long uid)
	{
		Criteria<DjOrder> criteria = new Criteria<DjOrder>();
		criteria.add(Restrictions.eq(DjOrder.sUserId, uid, true));
		// 1 全部
		switch (status) {
		case 1:{
			break;
		}
		// 2 待付款
		case 2:{
			criteria.add(Restrictions.eq(DjOrder.sStatus, 1, true));
			criteria.add(Restrictions.eq(DjOrder.sPaymentStatus, 1, true));
			criteria.add(Restrictions.eq(DjOrder.sIsOnlinePay, true, true));
			break;
		}
		// 3 待发货
		case 3:{
			criteria.add(Restrictions.eq(DjOrder.sStatus, 2, true));
			criteria.add(Restrictions.eq(DjOrder.sPaymentStatus, 2, true));
			criteria.add(Restrictions.notLike(DjOrder.sExpressTitle, "自提", true));
			break;
		}
		// 4 待收货
		case 4:{
			criteria.add(Restrictions.eq(DjOrder.sStatus, 2, true));
			criteria.add(Restrictions.eq(DjOrder.sExpressStatus, 2, true));
			break;
		}
		// 5 待评价
		case 5:{
			criteria.add(Restrictions.eq(DjOrder.sStatus, 3, true));
			criteria.add(Restrictions.eq(DjOrder.sExpressStatus, 2, true));
			break;
		}
		// 6 待评价
		case 6:{
			criteria.add(Restrictions.eq(DjOrder.sStatus, 2, true));
			criteria.add(Restrictions.like(DjOrder.sExpressTitle, "自提", true));
			break;
		}
		
		default:
			break;
		}
		criteria.setOrderByDesc("initDate");
		return criteria;
	}
	
	public Criteria<DjOrder> initCriteria(Map<String,Object> searchMap)
	{
		if (searchMap == null || searchMap.isEmpty())
			return null;

		Criteria<DjOrder> criteria = new Criteria<DjOrder>();

		Integer paymentStatus = (Integer) searchMap.get(DjOrder.sPaymentStatus);
		if(paymentStatus != null)
			criteria.add(Restrictions.eq(DjOrder.sPaymentStatus, paymentStatus, true));

		Integer typeId = (Integer) searchMap.get(DjOrder.sTypeId);
		if(typeId != null)
			criteria.add(Restrictions.eq(DjOrder.sTypeId, typeId, true));

		Integer status = (Integer) searchMap.get(DjOrder.sStatus);
		if(status != null)
			criteria.add(Restrictions.eq(DjOrder.sStatus, status, true));
		
		Integer expressStatus = (Integer) searchMap.get(DjOrder.sTypeId);
		if(expressStatus != null)
			criteria.add(Restrictions.eq(DjOrder.sTypeId, expressStatus, true));

		String keywords = (String )searchMap.get(DjOrder.sKeywords);
		if(StringUtils.isNotBlank(keywords))
			criteria.add(
					Restrictions.or
					(
							new Criterion[] {Restrictions.like(DjOrder.sAddress, keywords, true),
							Restrictions.like(DjOrder.sProvince, keywords, true),
							Restrictions.like(DjOrder.sDisctrict, keywords, true)}
							)
					);
		
		
		return criteria;
	}
	
	/**
	 * 判断订单是否可以进行支付
	 * @param order
	 * @return
	 */
	public Boolean isCanPayOrder(DjOrder order)
	{
		if(order == null)
			return false;
		if(order.getStatus() != 1)
			return false;
		if(order.getIsOnlinePay() == null || !order.getIsOnlinePay())
			return false;
		if(order.getPaymentStatus() != 1)
			return false;
		
		if(StringUtils.isBlank(order.getPaymentTitle()))
			return false;
		
		return true;
	}
	
	/**
	 * 订单初始化
	 * @param param
	 * @param session
	 * @return
	 */
	public DjOrder initOrder(DjOParam param,HttpSession session)
	{
		if(!param.verity())
			return null;
		
		if(!param.submitVerity())
			return null;
		
		DjUser user = (DjUser)session.getAttribute(DjKeys.SESSION_USER_USERNAME);
		if(user == null)
			return null;
		
		if(param.typeId == null)
			return null;
		
		
		//收货地址
		DjAddress address = addressSvs.findFirstByUidAndId(user.getId(), param.addressId);
		
		// 配送方式
		String deliveryType = param.deliveryType;
		DjExpressType expressType = expressTypeSvs.findByTitle(deliveryType);
		
		// 支付方式
		String payCode = param.payCode;
		DjPayType payType = paytypeSvs.findByCode(payCode);
		
		// 自提门店
		Long shopId = param.shopId;
		DjShop shop = shopSvs.findShop(shopId);
		return initBaseOrder(address, expressType, payType, shop, user,param);
		
	}
	
	/**
	 * 合并请求参数
	 * @param oParam  订单信息（收货地址，支付方式等）
	 * @param session
	 * @return
	 */
	public DjOParam paramTwoToOne(DjOParam oParam,HttpSession session)
	{
		DjOParam oGParam = (DjOParam)session.getAttribute(DjKeys.SESSION_ORDER_PARAM); //商品信息：购买数量等
		if(oGParam != null)
		{
			oParam.setTotalPrice(oGParam.getTotalPrice());
			oParam.setQuantitys(oGParam.quantitys);
			oParam.setCkIds(oGParam.ckIds);
			oParam.setIds(oGParam.ids);
			oParam.setGid(oGParam.gid);
		}
		return oParam;
	}
	
	
	/**
	 * 生成订单
	 * @param address
	 * @param expressType
	 * @param payType
	 * @param shop
	 * @param user
	 * @param oParam
	 * @return
	 */
	public DjOrder initBaseOrder(DjAddress address,DjExpressType expressType,DjPayType payType,DjShop shop,DjUser user,DjOParam oParam)
	{
		DjOrder order = new DjOrder();
		if(expressType == null || payType == null || user == null)
			return null;
		if(expressType.getTitle().contains("自提") && shop == null)
		{
			return null;
		}
		
		if(!expressType.getTitle().contains("自提") && address == null)
		{
			return null;
		}
		
		// 订单号
		order.setOrderNo(StringTools.getUniqueNoWithHeader(oParam != null ? oParam.typeId == 1 ? "C":"S" : "U"));
		
		//订单用户信息
		order.setUserId(user.getId());
		order.setUsername(user.getUsername());
		
		// 订单商品
		List<DjOrderGoods> orderGoods = orderGoodsByOParam(oParam);
		order.setOrderGoods(orderGoods);
		orderGoodsRepo.save(orderGoods);
		
		//自提门店地址
		if(expressType.getTitle().contains("自提"))
		{
			order.setShopId(shop.getId());
			order.setShopTitle(shop.getTitle());
			order.setShopAddress(shop.getFullAddress());
			// 邮费
			order.setExpressFee(BigDecimal.valueOf(0.0));
		}
		else
		{
			//订单收货信息
			order.setProvince(address.getProvince());
			order.setCity(address.getCity());
			order.setDisctrict(address.getDistrict());
			order.setAddress(address.getAddress());
			order.setAcceptName(address.getAcceptName());
			order.setTelphone(address.getTelphone());
			order.setMessage(oParam.message);
			// 邮费
			order.setExpressFee(getTotalPostFee(orderGoods));
		}
		
		// 配送信息
		order.setExpressTitle(expressType.getTitle());
		order.setExpressStatus(1);
		
		
		//支付方式
		order.setPaymentId(payType.getId());
		order.setPaymentTitle(payType.getTitle());
		order.setIsOnlinePay(payType.getIsOnlinePay());
		order.setPaymentStatus(1);
		order.setOrderAmount(getTotalSalePrice(orderGoods,order.getExpressFee()));
		
		
		//订单状态
		order.setStatus(1);
		order.setTypeId(oParam.typeId);
		
		//订单时间
		order.setOrderTime(new Date());
		
		return orderRepo.save(order);
	}
	
	 /**
     * 获取商品邮费
     * @param cGoods
     * @return
     */
    public BigDecimal getTotalPostFee(List<DjOrderGoods> cGoods)
    {
    	if(cGoods == null || cGoods.size() <= 0)
    		return BigDecimal.valueOf(0d);
    	BigDecimal total = BigDecimal.valueOf(0d);
    	for (DjOrderGoods djCartGoods : cGoods)
    	{
//    		total += djCartGoods.getQuantity() * djCartGoods.getSalePrice().doubleValue();
    		if(djCartGoods.getPostFee() != null && djCartGoods.getPostFee().compareTo(total) > 0)
    			total = djCartGoods.getPostFee();
		}
    	return total;
    }
    
    
    /**
     * 单商品提成 计算所有商品提成
     * @param cGoods
     * @return
     */
    public Double getTotalCostFee(List<DjOrderGoods> cGoods)
    {
    	if(cGoods == null || cGoods.size() <= 0)
    		return 0d;
    	Double total = 0d;
    	for (DjOrderGoods djCartGoods : cGoods)
    	{
//    		total += djCartGoods.getQuantity() * djCartGoods.getSalePrice().doubleValue();
    		if(djCartGoods.getCostFee() != null && djCartGoods.getCostFee().compareTo(0d) > 0)
    			total += djCartGoods.getCostFee();
		}
    	return total;
    }
	
	
	/**
	 * 获取订单商品
	 * @param param
	 * @return
	 */
	public List<DjOrderGoods> orderGoodsByOParam(DjOParam param)
	{
		
		List<DjGoods> goodsList = goodsService.findGoodsByIdIn(param.chooseGids());
		if(goodsList == null)
			return null;
		
		Map<String, Integer> map = param.gidsAndQuantity();
		
		List<DjOrderGoods> orderGoods = new ArrayList<DjOrderGoods>();
		for (DjGoods goods : goodsList)
		{
			DjOrderGoods oGoods = new DjOrderGoods();
			oGoods.setGoodsId(goods.getId());
			oGoods.setGoodsTitle(goods.getTitle());
			oGoods.setImgUrl(goods.getImgUrl());
			oGoods.setMarketPrice(goods.getMarketPrice());
			oGoods.setSalePrice(goods.getSalePrice());
			oGoods.setCostPrice(goods.getCostPrice());
			oGoods.setQuantity(map.get(goods.getId() + ""));
			oGoods.setIsCommented(false);
			oGoods.setPostFee(goods.getPostFee());
			oGoods.setCostRate(goods.getCostRate() == null ? 0.0 : goods.getCostRate());
			oGoods.setCostFee(oGoods.getCostRate() * oGoods.getSalePrice().doubleValue() * oGoods.getQuantity());
			
			orderGoods.add(oGoods);
		}
		return orderGoods;
	}
	
	/**
	 * 获取订单总金额
	 * @param cGoods
	 * @return
	 */
	public BigDecimal getTotalSalePrice(List<DjOrderGoods> cGoods,BigDecimal expreeFee)
    {
    	if(cGoods == null || cGoods.size() <= 0)
    		return new BigDecimal(0);
    	Double total = 0d;
    	for (DjOrderGoods djCartGoods : cGoods)
    	{
    		total += djCartGoods.getQuantity() * djCartGoods.getSalePrice().doubleValue();
		}
    	total += expreeFee.doubleValue();
    	return new BigDecimal(total);
    }
	
	/**
	 * 下单完成后，清除相应的session参数
	 * @param param
	 * @param session
	 * @param Uid
	 * @return
	 */
	public Boolean clearOrderSessionAndCartGoods(DjOParam param,HttpSession session,Long Uid)
	{
		session.removeAttribute(DjKeys.SESSION_ORDER_ADDRESS);
		session.removeAttribute(DjKeys.SESSION_ORDER_DELIVERYTYPE);
		session.removeAttribute(DjKeys.SESSION_ORDER_PARAM);
		session.removeAttribute(DjKeys.SESSION_ORDER_SHOPID);
		List<DjCartGoods> cartGoods = cartGoodsSvs.findByIdInAndUid(param.chooseIds(),Uid);
		cartGoodsSvs.delete(cartGoods);
		return true;
	}
	
	/**
	 * 订单短信通知
	 * @param uParam
	 */
	public void orderNotify(DjOUpdateParam uParam)
	{
		if(uParam == null ||  StringUtils.isBlank(uParam.getOrderNo()) || StringUtils.isBlank(uParam.getRemark()) || uParam.getRemark() == null)
				return;
		
		DjSystemConfig config = configSvs.findByConfigKey(DjKeys.K_ORDER_NOTIFY_TEL);
		if(config != null && StringUtils.isNotBlank(config.getConfigValue()))
		{
			if(uParam.getRemark().equalsIgnoreCase("abcd123"))
			{
				Map<String, String> sendSMS = DjSMSUtils.sendSMS(DjSMSUtils.getOrderContent(uParam.getOrderNo()), config.getConfigValue());
				logger.info("订单:" + uParam.getOrderNo() + " 短信已发送!");
				if(sendSMS != null)
				{
					logger.error("发送信息: " + sendSMS.toString());
				}
			}
			else if(uParam.getRemark().equalsIgnoreCase("abcd1234"))
			{
				Map<String, String> sendSMS = DjSMSUtils.sendSMS(DjSMSUtils.getOrderWait(uParam.getOrderNo()), config.getConfigValue());
				logger.info("订单:" + uParam.getOrderNo() + " 短信已发送!");
				if(sendSMS != null)
				{
					logger.error("发送信息: " + sendSMS.toString());
				}

			}	
		}
		
	}
	
	/**
	 * 修改收货信息
	 * @param uInfo
	 * @return
	 */
	public Boolean updateOrderUInfo(DjOUpdateParam uInfo)
	{
		DjOrder order = orderRepo.findByOrderNo(uInfo.orderNo);
		if(order == null || order.getStatus() != 1)
			return false;
		order.setAcceptName(uInfo.shippingName);
		order.setAddress(uInfo.detailAddress);
		order.setProvince(uInfo.province);
		order.setCity(uInfo.city);
		order.setDisctrict(uInfo.district);
		order.setTelphone(uInfo.shippingPhone);
		orderRepo.save(order);
		return true;
	}
	
	/**
	 * 管理员备注
	 * @param remark
	 * @return
	 */
	public Boolean updateOrderRemark(DjOUpdateParam remark)
	{
		DjOrder order = orderRepo.findByOrderNo(remark.orderNo);
		if(order == null)
			return false;
		order.setRemark(remark.remark);
		orderRepo.save(order);
		return true;
	}
	/**
	 * 配送费用修改
	 * @param remark
	 * @return
	 */
	public Boolean updateOrderExpressFee(DjOUpdateParam remark)
	{
		DjOrder order = orderRepo.findByOrderNo(remark.orderNo);
		if(order == null || order.getStatus() != 1)
			return false;
		
		order.setOrderAmount(new BigDecimal(order.getOrderAmount().doubleValue() - order.getExpressFee().doubleValue()));
		order.setExpressFee(remark.express);
		order.setOrderAmount(new BigDecimal(order.getOrderAmount().doubleValue() + remark.express.doubleValue()));
		orderRepo.save(order);
		return true;
	}
	
	//**
	// *   订单状态修改 
	//**
	/**
	 * 取消订单
	 * @param uParam
	 * @return
	 */
	public Boolean updateOrderCancel(DjOUpdateParam uParam)
	{
		DjOrder order = orderRepo.findByOrderNo(uParam.orderNo);
		if(order == null || order.getStatus() != 1)
			return false;
		
		order.setStatus(4);
		order.setCancelTime(new Date());
		orderRepo.save(order);
		return true;
	}
	
	/**
	 * 确认订单
	 * @param uParam
	 * @return
	 */
	public Boolean updateOrderConfirm(DjOUpdateParam uParam)
	{
		DjOrder order = orderRepo.findByOrderNo(uParam.orderNo);
		if(order == null || order.getStatus() != 1 || order.getIsOnlinePay() == true)
			return false;
		
		order.setStatus(2);
		order.setConfirmTime(new Date());
		orderRepo.save(order);
		return true;
	}
	
	/**
	 * 确认付款
	 * @param uParam
	 * @return
	 */
	public Boolean updateOrderPayment(DjOUpdateParam uParam)
	{
		if(StringUtils.isBlank(uParam.orderNo))
			return false;
		
		DjOrder order = orderRepo.findByOrderNo(uParam.orderNo);
		if(order == null || order.getStatus() != 1)
			return false;
		
		order.setStatus(2);
		order.setPaymentTime(new Date());
		order.setPaymentStatus(2);
		orderRepo.save(order);
		
		//分销记录 只有门店和会员才分润
//		cashSvs.cashLogLevel1(order.getUserId(), order.getOrderAmount().doubleValue(), order.getOrderNo());
		
		// 单商品分润提成
		cashSvs.CashLogSingleGoods(order.getUserId(), getTotalCostFee(order.getOrderGoods()), order.getOrderNo());
		
		// 记录限购商品
		activitySvs.LogLimitGoodsByOrder(order);
		
		//短信付款通知
		orderNotify(uParam);
		
		return true;
	}
	
	/**
	 * 确认发货
	 * @param remark
	 * @return
	 */
	public Boolean updateOrderExpress(DjOUpdateParam remark)
	{
		if(StringUtils.isBlank(remark.orderNo))
			return false;
		
		DjOrder order = orderRepo.findByOrderNo(remark.orderNo);
		if(order == null || order.getStatus() != 2)
			return false;
		order.setLogisticNo(remark.logisticNo);
		order.setLogisticTitle(remark.logisticTitle);
		order.setExpressTime(new Date());
		order.setExpressStatus(2);
		orderRepo.save(order);
		return true;
	}
	
	/**
	 * 
	 * 确认收货
	 * @param remark
	 * @return
	 * 
	 */
	public Boolean updateOrderReceive(DjOUpdateParam remark)
	{
		DjOrder order = orderRepo.findByOrderNo(remark.orderNo);
		if(order == null || order.getStatus() != 2)
			return false;
		
		order.setReceiveTime(new Date());
		order.setStatus(3);
		orderRepo.save(order);
		return true;
	}
	
	/**
	 * 活动下 限购的数量
	 * @param ids
	 * @param gid
	 * @return
	 */
	public Integer limitNumberOfActivity(Long[] ids,Long gid)
	{
		return orderGoodsRepo.limitNumberOfActivity(ids,gid);
	}
	
	public Integer limitNumberOfActivity(Long aid,Long gid)
	{
		return orderGoodsRepo.limitNumberOfActivity(aid,gid);
	}
	
	/**
	 * 活动下总商品个数
	 * @param aid
	 * @return
	 */
	public Long[] limitGidOfActivity(Long aid)
	{
		return orderGoodsRepo.limitGidOfActivity(aid);
	}
	
	public Long[] gidByOrderId(Long oid)
	{
		return orderGoodsRepo.gidByOrderId(oid);
	}
	
	
	public void deleteOrderGoods(List<DjOrderGoods> entities)
	{
		orderGoodsRepo.delete(entities);
	}
	
}
