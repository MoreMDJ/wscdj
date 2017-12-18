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

import com.zxsm.wsc.entity.order.DjExpressType;
import com.zxsm.wsc.repository.order.DjExpressTypeRepo;

@Service
@Transactional
public class DjExpressTypeService
{

	@Autowired
	DjExpressTypeRepo expressTypeRepo;

	/**
	 * 分页查找所有，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjExpressType> findExpressTypeAll(int page, int size) {

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return expressTypeRepo.findAll(pageRequest);
	}
	
	/**
	 * 查找所有配送方式
	 * @return
	 */
	public List<DjExpressType> findExpressTypeAll() {

		Sort sort = new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id"));
		return (List<DjExpressType>) expressTypeRepo.findAll(sort);
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
	public Page<DjExpressType> searchExpressTypeAll(String keywords, int page, int size) {

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return expressTypeRepo.findByTitleContaining(keywords, pageRequest);
	}
	
	public DjExpressType findByTitle(String title)
	{
		if(StringUtils.isBlank(title))
			return null;
		return expressTypeRepo.findFirstByTitleAndIsEnableTrue(title);
	}
	
	public List<DjExpressType> findEnbaleExpress()
	{
		Sort sort = new Sort(Direction.ASC,"sortId");
		return expressTypeRepo.findByIsEnableTrue(sort);
	}

	/**
	 * 保存品牌
	 * 
	 * @param e : 要保存的品牌
	 * @return
	 */
	public DjExpressType saveExpressType(DjExpressType e) {
		if (null == e) {
			return null;
		}
		
		return expressTypeRepo.save(e);
	}

	/**
	 * 删除支付类型，参数检查由调用函数完成
	 * 
	 * @param id : 品牌ID
	 */
	public void deleteExpressType(long id) {
		expressTypeRepo.delete(id);
	}

	/**
	 * 查找品牌
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public DjExpressType findExpressType(long id) {
		return expressTypeRepo.findOne(id);
	}
}
