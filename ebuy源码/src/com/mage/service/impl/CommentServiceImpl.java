package com.mage.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.CommentService;
import com.mage.util.DBUtil;
import com.mage.vo.Comment;
import com.mage.vo.PageBean;

//留言的实体类
public class CommentServiceImpl implements CommentService {
	@Override // 保存留言具体的实现方法  (添加)
	public void saveComment(Comment comment) {
		// 你又会了！
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "insert into t_comment (content,createtime,nickname) values(?,?,?)";
			QueryRunner qr = new QueryRunner();
			//设置绑定变量 
			Object[] obj = new Object[] {comment.getContent(),comment.getCreateTime(),comment.getNickName()};
			//执行添加
			qr.update(conn, sql, obj);
		} catch (SQLException e) {
			System.out.println(" CommentServiceImpl error");
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}
		

	}

	@Override
	public List<Comment> findAllCommentList(PageBean pageBean) {
		// 声明结果集合
		List<Comment> commentList = null;
		// 声明连接
		Connection conn = null;
		conn = DBUtil.getConn();
		String sql = "select * from t_comment order by createtime desc limit ? , ? ";
		// 设置绑定变量 第一个问号 起始页{(pageBean.getPage() - 1)* pageBean.getPagesize()}
		// 第二个问号 一页显示的记录数{pageBean.getPagesize()}
		Object[] objs = new Object[] { (pageBean.getPage() - 1) * pageBean.getPagesize(), pageBean.getPagesize() };
		QueryRunner qr = new QueryRunner();
		try {
			commentList = qr.query(conn, sql, new BeanListHandler<Comment>(Comment.class), objs);
		} catch (SQLException e) {
			System.out.println("findAllCommentList error");
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}
		return commentList;
	}

	@Override // 查询留言的信息总数量
	public int commentListCount() {
		// 声明结果集
		int count = 0;
		// 声明连接
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_comment";
			Object[] objs = new Object[] {};
			QueryRunner qr = new QueryRunner();
			List<Comment> ls = qr.query(conn, sql, new BeanListHandler<Comment>(Comment.class), objs);
			count = ls.size();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}
		return count;
	}

}
