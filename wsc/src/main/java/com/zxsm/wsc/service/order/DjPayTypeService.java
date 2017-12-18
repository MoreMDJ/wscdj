package com.zxsm.wsc.service.order;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.order.DjPayType;
import com.zxsm.wsc.repository.order.DjPayTypeRepo;

@Service
@Transactional
public class DjPayTypeService {

	@Autowired
	DjPayTypeRepo payTypeRepo;

	/**
	 * 分页查找所有，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjPayType> findPayTypeAll(int page, int size)
	{

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return payTypeRepo.findAll(pageRequest);
	}

	/**
	 * 查找所有支付方式
	 * 
	 * @return
	 */
	public List<DjPayType> findPayTypeAll()
	{
		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id"));
		return (List<DjPayType>) payTypeRepo.findAll(sort);
	}

	/**
	 * 查找支付类型，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjPayType> searchPayTypeAll(String keywords, int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return payTypeRepo.findByTitleContaining(keywords, pageRequest);
	}
	
	public DjPayType findByCode(String code)
	{
		if(StringUtils.isBlank(code))
			return null;
		return payTypeRepo.findFirstByCodeAndIsEnableTrue(code);
	}
	
	public List<DjPayType> findEnableType()
	{
		return payTypeRepo.findByIsEnable(true);
	}
	
	
	/**
	 * 根据订单类型 返回相应的支付方式
	 * @param type
	 * @return
	 */
	public List<DjPayType> findByType(Integer type)
	{
		if(type == null || type == 2)
			return payTypeRepo.findByIsEnableOrderBySortId(true);
		else
			return payTypeRepo.findByIsOnlinePayFalseAndIsEnableTrue();
	}

	/**
	 * 保存
	 * 
	 * @param e
	 *            : 要保存
	 * @return
	 */
	public DjPayType savePayType(DjPayType e)
	{
		if (null == e) 
		{
			return null;
		}

		return payTypeRepo.save(e);
	}

	/**
	 * 删除支付类型，参数检查由调用函数完成
	 * 
	 * @param id
	 *            : 品牌ID
	 */
	public void deletePayType(long id)
	{
		payTypeRepo.delete(id);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public DjPayType findPayType(long id)
	{
		return payTypeRepo.findOne(id);
	}
}
