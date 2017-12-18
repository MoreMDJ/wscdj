package com.zxsm.wsc.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.user.DjUComment;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.repository.user.DjUCommentRepo;

@Service
@Transactional
public class DjUCommentService {

	@Autowired
	DjUCommentRepo userCommentRepo;

	/**
	 * 分页查找所有评论，参数检查由调用函数保证
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<DjUComment> findUserCommentAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		return userCommentRepo.findAll(pageRequest);
	}

	/**
	 * 保存评论
	 * 
	 * @param e
	 *            : 要保存的评论
	 * @return
	 */
	public DjUComment saveUComment(DjUComment comment, DjUser user)
	{
		if (null == comment)
		{
			return null;
		}

		comment.setType(0);
		comment.setIsReplied(false);
		comment.setUid(user.getId());
		comment.setNickname(user.getNickname());
		comment.setuHeadImg(user.getHeadImage());
		comment.setStatus(0);

		return userCommentRepo.save(comment);
	}

	public Integer countCommentByUid(Long uid)
	{
		if(uid == null)
			return null;
		return userCommentRepo.countByUid(uid);
	}

	public DjUComment saveUserComment(DjUComment e)
	{
		if (null == e)
		{
			return null;
		}

		return userCommentRepo.save(e);
	}

	/**
	 * 删除，参数检查由调用函数完成
	 * 
	 * @param id
	 *            ：分类ID
	 */
	public void deleteUserComment(long id)
	{
		userCommentRepo.delete(id);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            评论ID
	 * @return
	 */
	public DjUComment findUComment(long id)
	{
		return userCommentRepo.findOne(id);
	}


	public List<DjUComment> findCommentByUserId(long uid)
	{

		Sort sort = new Sort(Direction.DESC, "id");
		return userCommentRepo.findByUid(uid, sort);
	}
	
	public List<DjUComment> findEnableCommentByGid(long gid)
	{

		return userCommentRepo.findByGidAndStatus(gid, 1);
	}

	public List<DjUComment> findByTypeAndGid(int type, long gid)
	{

		Sort sort = new Sort(Direction.DESC, "id");
		return userCommentRepo.findByTypeAndGid(type, gid, sort);
	}
}
