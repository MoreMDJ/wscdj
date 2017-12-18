package com.zxsm.wsc.service.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.user.DjUFeedback;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.repository.user.DjUFeedbackRepo;

@Service
@Transactional
public class DjUFeedbackService {

	@Autowired
	DjUFeedbackRepo feedbackRepo;

	/**
	 * 分页查找所有申请，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjUFeedback> findFeedbackAll(int page, int size) {

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return feedbackRepo.findAll(pageRequest);
	}

	/**
	 * 查找申请，参数检查由调用函数保证
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjUFeedback> searchFeedbackAll(String keywords, int page, int size) {

		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return feedbackRepo.findByMobileContaining(keywords, pageRequest);
	}
	
	/**
	 * 保存申请
	 * 
	 * @param e
	 *            : 要保存的申请
	 * @return
	 */
	public DjUFeedback saveFeedback(DjUFeedback e, DjUser user) {
		if (null == e) {
			return null;
		}
		
		e.setUserId(user.getId());
		e.setUsername(user.getMobile());
		e.setFeedTime(new Date());
		e.setStatusId(0);
		
		return feedbackRepo.save(e);
	}
	
	public DjUFeedback updateFeedback(DjUFeedback e) {
		if (null == e) {
			return null;
		}
		
		if (1 == e.getStatusId() && null == e.getHandleTime())
		{
			e.setHandleTime(new Date());
		}
		
		return feedbackRepo.save(e);
	}

	/**
	 * 删除，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteFeedback(long id) {
		feedbackRepo.delete(id);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            申请ID
	 * @return
	 */
	public DjUFeedback findFeedback(long id) {
		return feedbackRepo.findOne(id);
	}
}
