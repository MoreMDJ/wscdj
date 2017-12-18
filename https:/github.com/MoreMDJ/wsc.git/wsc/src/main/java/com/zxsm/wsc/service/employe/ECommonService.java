package com.zxsm.wsc.service.employe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxsm.wsc.entity.user.DjUser;

@Service
@Transactional
public class ECommonService {
	
	private final static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   
	private final static String dbURL = "jdbc:sqlserver://183.230.173.25; DatabaseName=zsmxnyc";   
	private final static String userName = "sa"; //默认用户名  
	private final static String userPwd = "zhenshanmei"; //安装sql server 2005时的密码  
	private  static ResultSet rs=null;    //声明数据库结果集      
	private  static PreparedStatement pstmt=null; //声明数据库操作  
	public static volatile Connection conn;
	
	public static Connection connectSqlServer()
	{
		if(conn == null)
		{
			synchronized (Connection.class)
			{
				if(conn == null)
				{
					try {
						Class.forName(driverName);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						conn = DriverManager.getConnection(dbURL,userName,userPwd);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return conn;
	}
	
	
	/**
	 * 传输会员
	 * @param user
	 * @return
	 */
	public Boolean insertUser(DjUser user)
	{
		String sql = "INSERT INTO 微信会员 (记录号,姓名,性别,出生日期,移动电话) values (" +
					 user.getId() + ", " + "'微信会员' ," + user.getSex() +", "+user.getBirthday() + ", '" + user.getUsername() + "'" +
					 ");";
		System.out.println(sql);
		Connection sqlServer = connectSqlServer();
		ResultSet rs=null;    //声明数据库结果集      
		PreparedStatement pstmt=null; //声明数据库操作  
		try {
			pstmt = sqlServer.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
