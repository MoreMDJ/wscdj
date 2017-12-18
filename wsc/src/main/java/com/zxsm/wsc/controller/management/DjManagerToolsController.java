package com.zxsm.wsc.controller.management;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.common.UtilsTools.DjTools;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.management.DjManager;
import com.zxsm.wsc.entity.management.DjManagerRole;
import com.zxsm.wsc.entity.management.DjNavigationMenu;
import com.zxsm.wsc.service.goods.DjGoodsService;
import com.zxsm.wsc.service.management.DjManagerService;
import com.zxsm.wsc.service.management.DjNavigationMenuService;

/**
 * 后台工具控制器
 * 
 * @author maeing
 */
@Controller
@RequestMapping(value = "/management/tools")
public class DjManagerToolsController extends DjAdminLevel
{
	@Autowired
	DjNavigationMenuService djNavigationMenuService;
	
	@Autowired
	DjManagerService djManagerService;
	
	@Autowired
	DjGoodsService djGoodsService;
	
	@Value("${imagePath}")
	private String rootPath;

	/**
	 * AJAX请求响应函数
	 * 
	 * @param action
	 * @param time
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/ajax")
	public String tools(String action, String time, ModelMap map, HttpSession session) {
		
		if (!isManagerLogin())
		{
			return URL_RedirectLogin;
		}
		
		DjManager manager = getManagerInfo();
		DjManagerRole role = null;
		
		if (null != manager && null != manager.getRoleId())
		{
			role = djManagerService.findRole(manager.getRoleId());
		}
		
		// 获取导航栏
		if ("get_navigation_list".equalsIgnoreCase(action))
		{
			List<DjNavigationMenu> rootMenuList = djNavigationMenuService.findRootMenuEnabled();

			if (rootMenuList.size() > 0)
			{
				
				// 按权限清理菜单
				if (null != role && null != role.getPermission())
				{
					Iterator<DjNavigationMenu> it = rootMenuList.iterator();
				    while (it.hasNext())
				    {
				    	DjNavigationMenu menu = (DjNavigationMenu) it.next();
				    	
				    	if (!role.getPermission().contains("["+menu.getId()+"]"))
				    	{
				    		it.remove();
				    	}
				    }
				}
				// 一级菜单
				map.addAttribute("root_menu_list", rootMenuList);
				
				for (int idx = 0; idx < rootMenuList.size(); idx++)
				{
					DjNavigationMenu menu = rootMenuList.get(idx);
					List<DjNavigationMenu> childList = djNavigationMenuService.findChildMenuEnabled(menu.getId());

					if (childList.size() > 0)
					{
						// 按权限清理菜单
						if (null != role && null != role.getPermission())
						{
							Iterator<DjNavigationMenu> it = childList.iterator();
						    while (it.hasNext())
						    {
						    	DjNavigationMenu menu2 = (DjNavigationMenu) it.next();
						    	
						    	if (!role.getPermission().contains("["+menu2.getId()+"]"))
						    	{
						    		it.remove();
						    	}
						    }
						}
						
						// 二级菜单
						map.addAttribute("root_" + idx + "_menu_list", childList);
						for (int childIdx = 0; childIdx < childList.size(); childIdx++)
						{
							DjNavigationMenu childMenu = childList
									.get(childIdx);
							List<DjNavigationMenu> grandChildList = djNavigationMenuService
									.findChildMenuEnabled(childMenu.getId());

							if (grandChildList.size() > 0)
							{
								// 按权限清理菜单
								if (null != role && null != role.getPermission())
								{
									Iterator<DjNavigationMenu> it = grandChildList.iterator();
								    while (it.hasNext())
								    {
								    	DjNavigationMenu menu3 = (DjNavigationMenu) it.next();
								    	
								    	if (!role.getPermission().contains("["+menu3.getId()+"]"))
								    	{
								    		it.remove();
								    	}
								    }
								}
								
								// 三级菜单
								map.addAttribute("root_" + idx + "_" + childIdx + "_menu_list", grandChildList);
							}
						}
					}
				}
			}
		}
		return "/management/sidebar";
	}
	
	@RequestMapping(value = "/check")
	@ResponseBody
	public Map<String, String> Check(String action,String param,String param1)
	{
		switch (action)
		{
		case "manager_conform":
			return managerCheck(param);
			
		case "navigation_conform":
			return djNavigationMenuService.navigationNameCheck(param,param1);
		case "goodsNo_conform":
			return goodsNoCheck(param,param1);
		case "goodsSku_conform":
			return goodsSku(param,param1);
			
		default:
			return DjTools.mapN("无效验证!");
		}
	}
	
	public Map<String, String> managerCheck(String username)
	{
		if(djManagerService.findByUsername(username) == null)
			return DjTools.mapY("此用户名可用！");
		
		return DjTools.mapN("用户名已被占用，请更换!");
	}
	
	public Map<String, String> goodsNoCheck(String no,String oldNo)
	{
		if (StringUtils.isBlank(no))
			return DjTools.mapN("该商品编号不能为空");
		
		List<DjGoods> goodsNo = djGoodsService.findByGoodsNo(no);
		if(oldNo == null)
		{
			if(goodsNo == null || goodsNo.size() == 0)
				return DjTools.mapY("该商品编号可以使用");
			else
				return DjTools.mapN("该商品编号已存在");	
		}
		
		else if(oldNo.equals(no))
		{
			if(goodsNo != null && goodsNo.size() > 1)
				return DjTools.mapN("该商品编号已存在");
			else
				return DjTools.mapY("该商品编号可以使用");
		}
		else
		{
			if(goodsNo == null || goodsNo.size() == 0)
				return DjTools.mapY("该商品编号可以使用");
			else
				return DjTools.mapN("该商品编号已存在");	
		}
		
	}
	
	
	/**
	 * 商品sku验证
	 * @param no
	 * @param oldNo
	 * @return
	 */
	public Map<String, String> goodsSku(String no,String oldNo)
	{
		if (StringUtils.isBlank(no))
			return DjTools.mapN("该商品货号不能为空");
		
		List<DjGoods> goodsNo = djGoodsService.findBySku(no);
		if(oldNo == null)
		{
			if(goodsNo == null || goodsNo.size() == 0)
				return DjTools.mapY("该商品货号可以使用");
			else
				return DjTools.mapN("该商品货号已存在");	
		}
		
		else if(oldNo.equals(no))
		{
			if(goodsNo != null && goodsNo.size() > 1)
				return DjTools.mapN("该商品货号已存在");
			else
				return DjTools.mapY("该商品货号可以使用");
		}
		else
		{
			if(goodsNo == null || goodsNo.size() == 0)
				return DjTools.mapY("该商品货号可以使用");
			else
				return DjTools.mapN("该商品货号已存在");	
		}
		
	}

	/**
	 * 上传图片
	 * 
	 * @param action
	 * @param DelFilePath
	 * @param Filedata
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(String action,
			String DelFilePath,
			MultipartFile Filedata,
			MultipartFile file,
			MultipartFile imgFile)
	{

		Map<String, Object> res = new HashMap<String, Object>();
		
		if ("EditorFile".equalsIgnoreCase(action))
		{
			if (!isManagerLogin())
	        {
				res.put("error", 1);
				res.put("message", "请重新登录！");
				return res;
	        }
			if (null == imgFile || imgFile.isEmpty())
			{
				res.put("error", 1);
				res.put("message", "图片不存在");
				return res;
			}

			String name = imgFile.getOriginalFilename();
			// String contentType = Filedata.getContentType();

			String ext = "";

			if (-1 != name.lastIndexOf("."))
			{
				ext = name.substring(name.lastIndexOf(".")).substring(1);
			}

			try
			{
				byte[] bytes = imgFile.getBytes();

				Date dt = new Date(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String fileName = sdf.format(dt) + "." + ext;

				String uri = rootPath + "/" + fileName;

				File file1 = new File(uri);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file1));
				stream.write(bytes);
				stream.close();

				res.put("error", 0);
				res.put("url", "/images/" + fileName);

			}
			catch (Exception e)
			{
				res.put("error", 1);
			}
		}
		else if("headFile".equalsIgnoreCase(action))
		{
			res.put("status", 0);
			if (null == file || file.isEmpty())
			{
				res.put("msg", "图片不存在");
				return res;
			}

			String name = file.getOriginalFilename();
			// String contentType = Filedata.getContentType();

			String ext = "";

			if (-1 != name.lastIndexOf("."))
			{
				ext = name.substring(name.lastIndexOf(".")).substring(1);
			}

			try {
				byte[] bytes = file.getBytes();

				Date dt = new Date(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String fileName = sdf.format(dt) + "." + ext;

				String uri = rootPath + "/" + fileName;
				DjTools.createDir(rootPath);

				File file1 = new File(uri);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file1));
				stream.write(bytes);
				stream.close();

				res.put("status", 1);
				res.put("msg", "上传文件成功！");
				res.put("path", "/images/" + fileName);
				res.put("thumb", "/images/" + fileName);
				res.put("name", name);
				res.put("size", file.getSize());
				res.put("ext", ext);

			} catch (Exception e)
			{
				res.put("status", 0);
				res.put("msg", "上传文件失败！");
			}
		}
		else
		{
			res.put("status", 0);
			if (null == Filedata || Filedata.isEmpty())
			{
				res.put("msg", "图片不存在");
				return res;
			}

			String name = Filedata.getOriginalFilename();
			// String contentType = Filedata.getContentType();

			String ext = "";

			if (-1 != name.lastIndexOf("."))
			{
				ext = name.substring(name.lastIndexOf(".")).substring(1);
			}

			try {
				byte[] bytes = Filedata.getBytes();

				Date dt = new Date(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String fileName = sdf.format(dt) + "." + ext;

				String uri = rootPath + "/" + fileName;
				DjTools.createDir(rootPath);

				File file1 = new File(uri);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file1));
				stream.write(bytes);
				stream.close();

				res.put("status", 1);
				res.put("msg", "上传文件成功！");
				res.put("path", "/images/" + fileName);
				res.put("thumb", "/images/" + fileName);
				res.put("name", name);
				res.put("size", Filedata.getSize());
				res.put("ext", ext);

			} catch (Exception e)
			{
				res.put("status", 0);
				res.put("msg", "上传文件失败！");
			}
		}

		return res;
	}

	/**
	 * IE6以下版本浏览器，需要升级界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ie6update")
	public String ie6update()
	{
		return "/management/ie6update";
	}
}
