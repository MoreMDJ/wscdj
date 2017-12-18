package com.zxsm.wsc.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.DjKeys;
import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjReqestParam;
import com.zxsm.wsc.service.management.DjSystemConfigService;

/**
 * 后台广告管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/management/setting")
public class DjManagerSettingController extends DjAdminLevel
{
    
    @Autowired
    DjSystemConfigService configSvs;
    
    @RequestMapping(value = "/wxsetting")
    public String setting(ModelMap map,DjReqestParam param)
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
        
        map.addAttribute("keywords", configSvs.findByConfigKey(DjKeys.K_WX_AUTOREPLY).getConfigValue());
        
        map.addAttribute("keyvalue", configSvs.findClikKeyValue());
        
        return "/management/manager/wx_setting";
    }
    
    @RequestMapping(value="/wxsetting/save")
    public String save(ModelMap map,DjReqestParam param, String[] wxKey, String wxValue[])
    {
    	if (!isManagerLogin())
        {
        	return URL_RedirectLogin;
        }
    	saveKeyAndValue(wxKey, wxValue);
    	configSvs.savaWX(DjKeys.K_WX_AUTOREPLY ,param.keywords);
    	map.addAttribute("keywords", configSvs.findByConfigKey(DjKeys.K_WX_AUTOREPLY).getConfigValue());
    	map.addAttribute("keyvalue", configSvs.findClikKeyValue());
    	return "/management/manager/wx_setting";
    }
    private void saveKeyAndValue(String[] key,String[] value)
    {
    	if(key == null || value== null)
    		return ;
    	if(key.length < 1 || key.length != value.length)
    		return ;
    	
    	for (int i = 0; i < value.length; i++)
    	{
    		configSvs.savaWX(key[i], value[i]);
		}
    }
}
