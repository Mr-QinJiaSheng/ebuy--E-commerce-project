package com.mage.service;

import java.util.List;

import com.mage.vo.Comment;
import com.mage.vo.PageBean;

public interface CommentService {
	//定义接口方法
	//查询留言信息的list集合方法
	List<Comment> findAllCommentList(PageBean pageBean);
	//查询留言信息的总数量
	int commentListCount();
	//保存留言
	void saveComment(Comment comment);
}
