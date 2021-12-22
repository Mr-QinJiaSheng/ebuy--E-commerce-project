package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mage.service.NoticeService;
import com.mage.util.DBUtil;
import com.mage.vo.Notice;

public class NoticeServiceImpl implements NoticeService {
	@Override//通过公告ID获取内容
	public Notice getNoticeById(int noticeId) {
		Notice notice =null;
		Connection conn = null;
		try {
		conn = DBUtil.getConn();
//		String sql="select * from t_notice where id=" + noticeId; 原生态JDBC这么写就行
		String sql="select * from t_notice where id= ?";
		//设置绑定变量的
		Object[] obj = new Object[] {noticeId};
		QueryRunner qr = new QueryRunner();
		notice = qr.query(conn, sql, new BeanHandler<Notice>(Notice.class),obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notice;
	}
	
	
	@Override
	public List<Notice> findAll() {
		List<Notice> noticeList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_notice where id < 7";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Notice n = new Notice();
				n.setId(rs.getInt("id"));
				n.setContent(rs.getString("content"));
				n.setCreateTime(rs.getDate("createTime"));
				n.setTitle(rs.getString("title"));
				noticeList.add(n);
			}
			return noticeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

	

}
