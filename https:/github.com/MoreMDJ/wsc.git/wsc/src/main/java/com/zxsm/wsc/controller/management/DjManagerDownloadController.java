package com.zxsm.wsc.controller.management;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxsm.wsc.common.Controllers.DjAdminLevel;
import com.zxsm.wsc.entity.common.DjDownParam;
import com.zxsm.wsc.entity.goods.DjGoods;
import com.zxsm.wsc.entity.user.DjUser;
import com.zxsm.wsc.service.goods.DjGoodsService;
import com.zxsm.wsc.service.user.DjUserService;

@Controller
@RequestMapping(value="/management/download")
public class DjManagerDownloadController extends DjAdminLevel
{
	@Autowired
	private DjGoodsService goodsSvs;
	
	@Autowired
	private DjUserService userSvs;
	
	@RequestMapping()
	public void DownData(DjDownParam param,HttpServletRequest req, HttpServletResponse res)
	{
		if (!isManagerLogin())
		{
			return ;
		}
		//获取到导出的excel
		HSSFWorkbook wb=acquireHSSWorkBook(param);


		download(wb, res,acquireFileName(param.getType()));
	}
	
	/**
	 * 根据报表类型获取报表名
	 * @param statusId
	 * @return
	 */
	private String acquireFileName(Integer statusId){
		String fileName="";
		if(statusId==0){
			fileName="商品表";
		}
		else if (statusId == 1)
		{
			fileName = "会员吸粉统计";
		}
		return fileName;
	}
	
	/**
	 * 根据报表状态 设置相应的报表
	 * @param statusId
	 * @param begin 开始时间
	 * @param end 结算时间
	 * @param diyCode 门店编号
	 * @param cityName 城市名称
	 * @param username 当前用户
	 * @return
	 */
	private HSSFWorkbook acquireHSSWorkBook(DjDownParam param){
		HSSFWorkbook wb= new HSSFWorkbook();  
		if(param.getType() == 0){//商品表
			wb=goodsWorkBook();
			return wb;
		}
		else if(param.getType()  == 1){//会员吸粉
			wb = userWorkBook(param);
			return wb;
		}
		return null;
	}
	
	
	// 会员吸粉
	private HSSFWorkbook userWorkBook(DjDownParam param)
	{
		 HSSFWorkbook wb = new HSSFWorkbook();

		 HashMap<String,Object> searchMap = new HashMap<>();
		 searchMap.put("uRole", 1);
		 List<DjUser> userShop = userSvs.findUser(searchMap);
		 
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第 1 页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,50,25,25,25};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);
	      //设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        int cellRow = 1;
	        String[] cellValues={"门店","会员","总吸粉数量","时间段吸粉量","职位"};
			cellDates(cellValues, style, row);
			
			for (DjUser user : userShop) {
				row = sheet.createRow(cellRow++);
				row.createCell(0).setCellValue(user.getRealName());
				row.createCell(1).setCellValue(user.getRealName());
				row.createCell(2).setCellValue(userSvs.findTotalSpreadByUid(user.getId(), 0));
				row.createCell(3).setCellValue(userSvs.findSpredByUidAndTime(user.getId(), param.getStartDate(), param.getEndDate(), 0));
				row.createCell(4).setCellValue("店长");
				List<DjUser> seller = userSvs.findByIdInAndStatusAndURole(user.getId());
				for (DjUser djUser : seller) {
					row = sheet.createRow(cellRow++);
					row.createCell(0).setCellValue(user.getRealName());
					row.createCell(1).setCellValue(djUser.getRealName());
					row.createCell(2).setCellValue(userSvs.findTotalSpreadByUid(djUser.getId(), 0));
					row.createCell(3).setCellValue(userSvs.findSpredByUidAndTime(djUser.getId(), param.getStartDate(), param.getEndDate(), 0));
					row.createCell(4).setCellValue("店员");
				}
			}

		 return wb;
	}
	
	private HSSFWorkbook goodsWorkBook()
	{
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
 
        // 第五步，设置值  
        List<DjGoods> goodsInOutList=goodsSvs.findAll();
        
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(goodsInOutList!=null){
        	maxSize=goodsInOutList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,50,25,18,18,13};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"货号","商品名称","规格","价格","厂家","批准文号"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				DjGoods goodsInOut= goodsInOutList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	//货号
	        	row.createCell(0).setCellValue(objToString(goodsInOut.getSku()));

	        	//商品名称
	        	row.createCell(1).setCellValue(objToString(goodsInOut.getTitle()));
	        	// 规格
	        	row.createCell(2).setCellValue(objToString(goodsInOut.getSpecification()));
	        	// 价格
	        	row.createCell(3).setCellValue(objToString(goodsInOut.getSalePrice()));
	        	// 厂家
	        	row.createCell(4).setCellValue(objToString(goodsInOut.getManufacturer()));
	        	// 批准文号
	        	row.createCell(5).setCellValue(objToString(goodsInOut.getApprovalNumber()));
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
	}
	
	/**
	 * @注释：下载
	 */
	public Boolean download(HSSFWorkbook wb, HttpServletResponse resp,String fileName) {
		String filename="table";
		try {
			filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			System.out.println("下载文件名格式转换错误！");
		}
		try {
			OutputStream os;
			try {
				os = resp.getOutputStream();
				try {
					resp.reset();
					resp.setHeader("Content-Disposition",
							"attachment; filename=" + filename +".xls");
					resp.setContentType("application/octet-stream; charset=utf-8");
					wb.write(os);
					os.flush();
				} finally {
					if (os != null) {
						os.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 字符串转换时间默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 *            需要转换的时间
	 * @param dateFormat
	 *            时间格式
	 * @return
	 */
	public Date stringToDate(String time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		if (StringUtils.isNotBlank(time)) {
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	/**
	 * 时间转换字符串默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 *            需要转换的时间
	 * @param dateFormat
	 *            时间格式
	 * @return
	 */
	public String dateToString(Date time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = null;
		if (null != time) {
			try {
				dateStr = sdf.format(time);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return dateStr;
	}
	
	/**
	 * 设置列宽
	 * @param sheet
	 * @param widths
	 */
	public void sheetColumnWidth(HSSFSheet sheet,int[] widths){
		for (int i = 0; i < widths.length; i++) {
			sheet.setColumnWidth(i , widths[i]*256);
		}
		
	}

	/**
	 * 添加导出列名
	 * 
	 * @param CellValues
	 *            列名数组
	 * @param style
	 *            样式
	 * @param style
	 *            当前行
	 * @return
	 */
	public void cellDates(String[] cellValues, HSSFCellStyle style,
			HSSFRow row) {
		HSSFCell cell = null;
		if (null != cellValues && cellValues.length > 0) {
			for (int i = 0; i < cellValues.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(cellValues[i]);
				cell.setCellStyle(style);
			}
		}
	}
	
	private String objToString(Object obj){
		if(obj==null){
			return "";
		}
		return obj.toString();
	}
		
}
