package com.zxsm.wsc.controller.front.employe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ep")
public class DjEIndexController {

	@RequestMapping()
	public void index() throws SQLException
	{
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   
		String dbURL = "jdbc:sqlserver://183.230.173.25; DatabaseName=zsmds";   
		String userName = "sa"; //默认用户名  
		String userPwd = "zhenshanmei"; //安装sql server 2005时的密码  
		ResultSet rs=null;    //声明数据库结果集      
		PreparedStatement pstmt=null; //声明数据库操作  
		Connection conn=null;  
		try {
			Class.forName(driverName);  
			conn = DriverManager.getConnection(dbURL, userName, userPwd);  
			System.out.println("Connection Successful!"); //如果连接成功 控制台输出Connection Successful!  
			String sql="select 日期,部门,销售员号,姓名,销售额 from app职工日销售查询";  
			pstmt=conn.prepareStatement(sql); //实例化PrepareStatement对象  
			rs=pstmt.executeQuery();  //执行查询操作  
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			int columnCount_tem = 1;
			while(columnCount_tem <= columnCount)
			{
				System.out.print(metaData.getColumnName(columnCount_tem++) + "		");
			}
			System.out.println("\n");
			while(rs.next()){
				int row = rs.getRow();
				StringBuffer LogStr = new StringBuffer();
				columnCount_tem = 1;
				while(columnCount_tem <= columnCount)
				{
					String Result_str = rs.getString(columnCount_tem++);
					LogStr.append(Result_str+"		");
				}
				System.out.println(LogStr);  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}finally{  
			rs.close();  
			pstmt.close();  
			conn.close();  
		}  
	}
	
	@RequestMapping(value = "/test")
	public String test()
	{
		return "/employe/test";
	}
}
