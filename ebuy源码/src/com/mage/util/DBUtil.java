package com.mage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	static {
		try {
			//通过反射的方式加载驱动！
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("mysql初始化的失败，请重新连接");
			e.printStackTrace();
		}
	}
	//获取连接
	public static Connection getConn() {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hdebuy?useUnicode=true&characterEncoding=utf8","root","1234");
		} catch (SQLException e) {
			System.out.println("连接错误，看看时候开启数据库");
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭连接
	public static void closeAll(Connection conn ,PreparedStatement pstmt,ResultSet rs) {
		closeResult(rs);
		closeState(pstmt);
		closeConn(conn);
	}
	
	
	private static void closeConn(Connection conn) {
		if(null!=conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("conn error");
				e.printStackTrace();
			}
		}
	}

	private static void closeState(PreparedStatement pstmt) {
		if(null!=pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("pstmt error");
				e.printStackTrace();
			}
		}
	}

	//关闭结果集
	private static void closeResult(ResultSet rs) {
		if(null!=rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("rs error");
				e.printStackTrace();
			}
		}
	}
}
