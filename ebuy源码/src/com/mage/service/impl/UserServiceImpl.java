package com.mage.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mage.service.UserService;
import com.mage.util.DBUtil;
import com.mage.vo.User;
//dao层 
public class UserServiceImpl implements UserService {
	@Override //修改用户的实体操作
	public void update(User user) {
		Connection conn = null;
		conn = DBUtil.getConn();
		String sql = "update t_user set address = ?,birthday = ?,dentitycode = ?,"
				+"email = ? , mobile = ?,password = ? ,sex = ?,status = ?,username = ?,truename = ? where id = ?";
		Object[] pars = new Object[] {//设置绑定变量
				user.getAddress(),user.getBirthday(),user.getDentityCode(),
				user.getEmail(),user.getMobile(),user.getPassword(),user.getSex(),user.getStatus(),
				user.getUserName(),user.getTrueName(),user.getId()
		};
		QueryRunner qr = new QueryRunner();		
		try {
			qr.update(conn,sql,pars);
		} catch (SQLException e) {
			System.out.println("updateuser error");
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}
	}
	@Override//用户登录
	public User login(User user) {
		User u = null;
		Connection conn = null;
		try {
		conn = DBUtil.getConn();
		String sql="select * from t_user where username= ? and password = ?";
		//设置绑定变量
		Object[] pars = new Object[] {user.getUserName(),user.getPassword()};
		QueryRunner qr = new QueryRunner();
			u = qr.query(conn, sql,new BeanHandler<User>(User.class),pars);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}	
		return u;
	}
	
	
	@Override//用户注册
	public void register(User user) {
		Connection conn =null;
		try {
		conn = DBUtil.getConn();
		String sql ="insert into t_user (address,birthday,dentitycode,"
				+"email,mobile,password,sex,status,username) values("
				+"?,?,?,?,?,?,?,?,?)";
		//设定绑定变量
		Object[] pars= new Object[] {
				user.getAddress(),user.getBirthday(),
				user.getDentityCode(),user.getEmail(),
				user.getMobile(),user.getPassword(),
				user.getSex(),user.getStatus(),
				user.getUserName()};
		QueryRunner qr = new QueryRunner();
		//执行修改添加操作！
			qr.update(conn,sql,pars);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}
	}

	@Override//验证唯一
	public boolean checkName(String userName) {
		boolean flag =  false;
		Connection conn =null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_user where username = ?";
			//QueryRunner 是dbutils 作用封装了JDBC的方法，可是用update() 和query()
			QueryRunner qr = new QueryRunner();
			Object[] obj = new Object[] {userName};
			User u = qr.query(conn, sql, new BeanHandler<User>(User.class),obj);
		if(null==u) {
			flag = true;// 代表查询这个username可以使用
		}else {
			flag = false;//代表查到了，不能继续使用！
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}


	


	

	

}
