package com.zxsm.wsc.service.doctor;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.common.SerchTools.Criteria;
import com.zxsm.wsc.common.SerchTools.Criterion;
import com.zxsm.wsc.common.SerchTools.Restrictions;
import com.zxsm.wsc.entity.doctor.DjDoctor;
import com.zxsm.wsc.repository.activity.DjUserLimitGoodsRepo;
import com.zxsm.wsc.repository.doctor.DjDoctorRepo;
import com.zxsm.wsc.service.order.DjOrderService;

@Service
@Transactional
public class DjDoctorService {

	@Autowired
	DjDoctorRepo doctorRepo;
	
	@Autowired
	DjUserLimitGoodsRepo uLimitRepo;
	
	@Autowired
	DjOrderService orderSvs;
	
	//查找
	public DjDoctor findOne(Long id)
	{
		if(id == null)
			return null;
		return doctorRepo.findOne(id);
	}
	
	public Page<DjDoctor> findAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sort"));
		return doctorRepo.findAll(pageRequest);
	}
	
	/**
	 * 保存
	 * @param e
	 * @return
	 */
	public DjDoctor save(DjDoctor e)
	{
		if( null == e)
			return null;
		
		return doctorRepo.save(e);
	}

	public List<DjDoctor> save(List<DjDoctor> entities)
	{

		return (List<DjDoctor>) doctorRepo.save(entities);
	}
	
	public void delete(Long id)
	{
		doctorRepo.delete(id);
	}
	
	public List<DjDoctor> find(Map<String,Object> searchMap)
	{
		Criteria<DjDoctor> criteria = initCriteria(searchMap);
		return doctorRepo.findAll(criteria);
	}
	
	
	public Page<DjDoctor> find(Map<String,Object> searchMap,int page,int size)
	{
		Criteria<DjDoctor> criteria = initCriteria(searchMap);
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "sort"));
		return doctorRepo.findAll(criteria, pageRequest);
	}
	
	public Criteria<DjDoctor> initCriteria(Map<String,Object> searchMap)
	{
		if (searchMap == null || searchMap.isEmpty())
			return null;

		Criteria<DjDoctor> criteria = new Criteria<DjDoctor>();

		String typeId = (String) searchMap.get(DjDoctor.sCat);
		if(typeId != null)
			criteria.add(Restrictions.like(DjDoctor.sCat, typeId, true));

		Integer status = (Integer) searchMap.get(DjDoctor.sStatus);
		if(status != null)
			criteria.add(Restrictions.eq(DjDoctor.sStatus, status, true));
		
		Integer type = (Integer) searchMap.get(DjDoctor.sType);
		if(type != null)
			criteria.add(Restrictions.eq(DjDoctor.sType, type, true));
		
		Boolean expressStatus = (Boolean) searchMap.get(DjDoctor.sIsOnline);
		if(expressStatus != null)
			criteria.add(Restrictions.eq(DjDoctor.sIsOnline, expressStatus, true));

		String keywords = (String )searchMap.get(DjDoctor.sKeywords);
		if(StringUtils.isNotBlank(keywords))
			criteria.add(
					Restrictions.or
					(
							new Criterion[] {Restrictions.like(DjDoctor.sUsername, keywords, true),
							Restrictions.like(DjDoctor.sName, keywords, true),
							Restrictions.like(DjDoctor.sDiseaseKwd, keywords, true)}
							)
					);
		
		
		return criteria;
	}
	
	public DjDoctor findByUsernameAndPassword(String username,String password)
	{
		if(StringUtils.isBlank(username)||StringUtils.isBlank(password))
		{
			return null;
		}
		return doctorRepo.findByUsernameAndPassword(username, password);
	}
}
