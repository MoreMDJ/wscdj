package com.zxsm.wsc.controller.front.wx;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.Controllers.DjBaseController;
import com.zxsm.wsc.entity.user.DjMessage;
import com.zxsm.wsc.entity.user.DjUParam;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.user.DjMessageService;


@Controller
@RequestMapping(value = "/wx/msg")
public class DjMsgCenterController extends DjBaseController
{
	
	@Autowired
	private DjMessageService msgSvs;
	
	@RequestMapping()
	public String uNews(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin + param.reqUrl;
		
		DjUser user = getUserInfo();
		map.addAttribute("msg1", msgSvs.countMsgByUidAndType(user.getId(),1));
		map.addAttribute("msg2", msgSvs.countMsgByUidAndType(user.getId(),2));
		map.addAttribute("msg3", msgSvs.countMsgByUidAndType(user.getId(),3));
		map.addAttribute("msg4", msgSvs.countMsgByUidAndType(user.getId(),4));
		
		
		return "/wx/user/news_center";
	}
	@RequestMapping(value = "/{typeId}")
	public String uNewsType(@PathVariable Integer typeId, ModelMap map,DjUParam param)
	{
		if(!isLogin())
			return URL_RedirectLogin;
		
		DjUser user = getUserInfo();
		map.addAttribute("msgs", msgSvs.findMsgByType(user.getId(), typeId));
		
		return "/wx/user/news_type";
	}
	@RequestMapping(value = "/detail")
	public String uNewsDetail(DjUParam param,ModelMap map)
	{
		if(!isLogin())
			return URL_RedirectLogin;
		DjMessage msg = msgSvs.findOne(param.msgId);
		
		map.addAttribute("msg",msg);
		msg.setReadTime(new Date());
		msg.setIsRead(true);
		msgSvs.save(msg);
		return "/wx/user/news_detail";
	}

}
