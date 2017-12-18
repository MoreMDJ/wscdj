package com.zxsm.wsc.common;

import java.util.List;  

import org.junit.Assert;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.test.SpringApplicationConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.web.WebAppConfiguration;

import com.zxsm.WscApplication;
import com.zxsm.wsc.common.tencent.controller.DjUserQRcodeTools;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.employe.ECommonService;
import com.zxsm.wsc.service.user.DjUserRelationsService;
import com.zxsm.wsc.service.user.DjUserService;


@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = WscApplication.class)  
@WebAppConfiguration
public class ServiceJUnitTest {

	@Autowired
	DjUserService userSvs;
	
	@Autowired
	DjUserRelationsService relationSvs;
	
	@Autowired
	DjUserQRcodeTools toolsSvs;
	
	@Autowired
	ECommonService eSvs;
	
	@Test
	public void Test()
	{
		
//		DjUser user9 = userSvs.createBySpreadId(9L, "o6DzCjjkdQ73dec1ZZBu8b3G_M1A");
//		Long username = 18512121212L;
//		user9.setUsername((++username).toString());
//		userSvs.save(user9);
//		DjUser  user = null;
//		for (Long i = 9L ; i < 19; i++)
//		{
//			DjUser user9 = userSvs.createBySpreadId(user==null ? 9L :user.getId(), "o6DzCjjkdQ73dec1ZZBu8b3G_M1A");
//			user9.setUsername((++username).toString());
//			user = userSvs.save(user9);
//		}
//		Integer number = userSvs.findSpredByUidAndTime(1144l, "2017-06-01 00:00:01", "2017-06-07 23:59:59", 0);
//		System.out.println(number);
		DjUser djUser = userSvs.findOne(1l);
		eSvs.insertUser(djUser);
		
		
//		relationSvs.createFirstRelations(9L);
	}
}