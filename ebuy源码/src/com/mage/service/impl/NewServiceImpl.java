package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mage.service.NewsService;
import com.mage.util.DBUtil;
import com.mage.vo.News;

public class NewServiceImpl implements NewsService {
	@Override // 通过ID来获取新闻内容
	public News getNewsById(int newsId) {
		News news = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_news where id = ? ";
			QueryRunner qr = new QueryRunner();
			Object[] obj = new Object[] { newsId };
			news = qr.query(conn, sql, new BeanHandler<News>(News.class), obj);
		} catch (SQLException e) {
		System.out.println("NewServiceImpl errror");
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}
		return news;
	}

	@Override // 查询所有新闻信息
	public List<News> findAll() {
		List<News> news = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConn();
			// 执行你当前要操作的事情！
			String sql = "select * from t_news where id < 7";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				News n = new News();
				n.setId(rs.getInt("id"));
				n.setContent(rs.getString("content"));
				n.setCreateTime(rs.getDate("createTime"));
				n.setTitle(rs.getString("title"));
				news.add(n);
			}
			return news;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

}
