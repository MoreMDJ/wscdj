package com.zxsm.wsc.common.UtilsTools;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class DjTools
{
	/**
	 * XML转MAP
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> parserXml(HttpServletRequest request) throws Exception {  
		
	    // 将解析结果存储在HashMap中  
	    Map<String,Object> map = new HashMap<String,Object>();  
	   
	    // 从request中取得输入流  
	    InputStream inputStream = request.getInputStream();  
	    // 读取输入流  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(inputStream);  
	    // 得到xml根元素  
	    Element root = document.getRootElement();  
	    // 得到根元素的所有子节点  
	    @SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();  
	   
	    // 遍历所有子节点  
	    for (Element e : elementList)  
	        map.put(e.getName(), e.getText());  
	   
	    // 释放资源  
	    inputStream.close();  
	    inputStream = null;  
	    return map;  
	}
	
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String clientIp(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "":ip;
	}
	
	/// <summary>
    /// 获取操作权限
    /// </summary>
    /// <returns>Dictionary</returns>
    public static LinkedHashMap<String,String> AuthorDictionary()
    {
    	LinkedHashMap<String, String> dic = new LinkedHashMap<String, String>();
        dic.put("Show", "显示");
        dic.put("View", "查看");
        dic.put("Add", "添加");
        dic.put("Edit", "修改");
        dic.put("Delete", "删除");
        dic.put("Audit", "审核");
        dic.put("Reply", "回复");
        dic.put("Confirm", "确认");
        dic.put("Cancel", "取消");
        dic.put("Invalid", "作废");
        dic.put("Build", "生成");
        dic.put("Instal", "安装");
        dic.put("Unload", "卸载");
        dic.put("Back", "备份");
        dic.put("Restore", "还原");
        dic.put("Replace", "替换");
        return dic;
    }
    
    /**
     * 创建目录
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    }
    
    /**
     * ajaxurl 请求返回的map
     * @param info 错误信息
     * @return
     */
    public static Map<String ,String> mapN(String info)
    {
    	Map<String, String> res = new HashMap<String, String>();

		res.put("status", "n");
		if(StringUtils.isBlank(info))
			res.put("info", "未定义Map");
		else
			res.put("info", info);
		
		return res;
    }
    
    /**
     * ajaxurl 请求返回的map
     * @param info 成功提示
     * @return
     */
    public static Map<String ,String> mapY(String info)
    {
    	Map<String, String> res = new HashMap<String, String>();

		res.put("status", "y");
		if(StringUtils.isBlank(info))
			res.put("info", "");
		else
			res.put("info", info);
		
		return res;
    }
    
    public static PageRequest pageRequest(int page, int size,String orderType)
    {
    	 return new PageRequest(page, size, new Sort(Direction.ASC, "sortId").and(new Sort(Direction.ASC, "modifyDate")).and(new Sort(Direction.ASC, "id")));
    }
}
