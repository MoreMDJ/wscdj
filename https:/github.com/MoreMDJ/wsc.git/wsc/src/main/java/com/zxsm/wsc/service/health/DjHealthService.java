package com.zxsm.wsc.service.health;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.entity.health.DjHealth;
import com.zxsm.wsc.repository.activity.DjUserLimitGoodsRepo;
import com.zxsm.wsc.repository.health.DjHealthRepo;
import com.zxsm.wsc.service.order.DjOrderService;

@Service
@Transactional
public class DjHealthService {

	@Autowired
	DjHealthRepo healtRepo;
	
	@Autowired
	DjUserLimitGoodsRepo uLimitRepo;
	
	@Autowired
	DjOrderService orderSvs;
	
	//查找
	public DjHealth findOne(Long id)
	{
		if(id == null)
			return null;
		return healtRepo.findOne(id);
	}
	
	public Page<DjHealth> findAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "initDate"));
		return healtRepo.findAll(pageRequest);
	}
	
	/**
	 * 保存
	 * @param e
	 * @return
	 */
	public DjHealth save(DjHealth e)
	{
		if( null == e)
			return null;
		
		return healtRepo.save(e);
	}

	public List<DjHealth> save(List<DjHealth> entities)
	{

		return (List<DjHealth>) healtRepo.save(entities);
	}
	
	public void delete(Long id)
	{
		healtRepo.delete(id);
	}
	
	
	public Page<DjHealth> find(Map<String,Object> searchMap,int page,int size)
	{
		Criteria<DjHealth> criteria = initCriteria(searchMap);
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "initDate"));
		return healtRepo.findAll(criteria, pageRequest);
	}
	
	public List<DjHealth> findListByUidAndTime(Long uid,Integer type,Date beginDate,Date endDate)
	{
		Criteria<DjHealth> criteria = new Criteria<DjHealth>();
		criteria.add(Restrictions.eq("type", type, true));
		criteria.add(Restrictions.eq("uid", uid, true));
		criteria.setOrderByDesc("addDate");
		if(beginDate != null)
			criteria.add(Restrictions.gte("addDate", beginDate, true));
		if(endDate != null)
			criteria.add(Restrictions.lte("addDate", endDate, true));
		return healtRepo.findAll(criteria);
	}
	
	public Criteria<DjHealth> initCriteria(Map<String,Object> searchMap)
	{
		if (searchMap == null || searchMap.isEmpty())
			return null;

		Criteria<DjHealth> criteria = new Criteria<DjHealth>();

//		String typeId = (String) searchMap.get(DjHealth.sCat);
//		if(typeId != null)
//			criteria.add(Restrictions.like(DjHealth.sCat, typeId, true));
//
//		Integer status = (Integer) searchMap.get(DjHealth.sStatus);
//		if(status != null)
//			criteria.add(Restrictions.eq(DjHealth.sStatus, status, true));
//		
//		Boolean expressStatus = (Boolean) searchMap.get(DjHealth.sIsOnline);
//		if(expressStatus != null)
//			criteria.add(Restrictions.eq(DjHealth.sIsOnline, expressStatus, true));
//
//		String keywords = (String )searchMap.get(DjHealth.sKeywords);
//		if(StringUtils.isNotBlank(keywords))
//			criteria.add(
//					Restrictions.or
//					(
//							new Criterion[] {Restrictions.like(DjHealth.sUsername, keywords, true),
//							Restrictions.like(DjHealth.sName, keywords, true),
//							Restrictions.like(DjHealth.sDiseaseKwd, keywords, true)}
//							)
//					);
		
		
		return criteria;
	}
	
	
	/**
	 * 求BMI值
	 * @param w 体重 kg
	 * @param h 身高  cm
	 * @return
	 */
	public Double healthBMI(Double w,Double h)
	{
		if(h == null || h == 0)
			return null;
		Double hM = h/100;
		
		return w/(hM*hM);
	}
	
	
	/**
	 * 体重值
	 * @param BMI
	 * @return
	 */
	public String healthLevel(Double BMI)
	{
		if(BMI == null)
			return "未知";
		if(BMI >= 18.5 && BMI <=24)
		if(BMI > 24 && BMI <=28)
			return "过重";
		if(BMI > 28 && BMI <=32)
			return "肥胖";
		if(BMI >  32)
			return "非常肥胖";
		if(BMI < 18.5)
			return "过轻";
		
		return "无";
	}
	
	/**
	 * 血糖
	 * @param s 血糖
	 * @param date 餐前后
	 * @return
	 */
	public String bloodSugar(Double s,String date)
	{
		if(s == null || date == null)
			return "未知";
		if(date.equalsIgnoreCase("餐前"))
		{
			if(s>=3.9 && s<= 6.1)
				return "正常";
			else if(s < 3.9)
				return "低血糖";
			else if(s > 6.1)
				return "高血糖";
		}
		else if(date.equalsIgnoreCase("餐后"))
		{
			if(s >= 6.7 && s<= 9.4)
				return "正常";
			else if(s < 6.7)
				return "低血糖";
			else if(s > 9.4)
				return "高血糖";
		}
		return "无";
	}
	
	/**
	 * 
	 * @param d diastolic 舒张压
	 * @param s systolic 收缩压
	 * @return
	 */
	public String Pressure(Double d, Double s)
	{
		if (d == null || s == null)
			return "未知";
		
		if(d >= 120 && d <=139 &&  s>=80 && s <= 89)
			return "正常";
		
		else if(d <= 120 || s < 80)
			return "偏低";
		
		else if(d > 139 || s > 89)
			return "偏高";
		
		return "未知";
	}
	
}
