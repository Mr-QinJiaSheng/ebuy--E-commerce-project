package com.mage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.CommentService;
import com.mage.service.impl.CommentServiceImpl;
import com.mage.util.PageUtil;
import com.mage.util.StringUtil;
import com.mage.vo.Comment;
import com.mage.vo.PageBean;
import com.mage.vo.User;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentService commentService = new CommentServiceImpl();

	// 从哪到这里来的？ 答:top.jsp来的
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		// 获取请求参数名称
		String oper = req.getParameter("oper");
		// 判断请求参数内容
		if (!"".equals(oper) && oper != null && "list".equals(oper)) {
			list(req, resp);
			//确定这个请求是留言的保存方法
		}else if(!"".equals(oper) && oper != null && "save".equals(oper)) {
			save(req,resp);
		}
	}
	//留言保存方法
	private void save(HttpServletRequest req, HttpServletResponse resp) {
		//获取数据
//		String nickName = req.getParameter("nickName");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("currentUser");
		String content = req.getParameter("content");
		Comment comment = new Comment();
		comment.setNickName(u.getUserName());
		comment.setContent(content);
		comment.setCreateTime(new Date());
		//关键的数据的存储
		commentService.saveComment(comment);
		//刷新页面显示
		list(req, resp);
	}

	// 留言的控制流程方法(显示|刷新)
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		// 获取当前页码
		String page = req.getParameter("page");
		// 如果没有页，我们默认给它第一页
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		try {
			// 创建pagebean对象
			PageBean pageBean = new PageBean(Integer.parseInt(page), 4);
			// 收集结果集
			List<Comment> commentList = commentService.findAllCommentList(pageBean);
			// 查询数据的总数量
			int total = commentService.commentListCount();
			// 生成分页代码
			String pageCode = PageUtil.getPaginactionNoParmy(req.getContextPath() + "/comment?oper=list", total,
					Integer.parseInt(page), 4);
			// 存储作用域
			req.setAttribute("commentList", commentList);
			req.setAttribute("pageCode", pageCode);
			req.getRequestDispatcher("/comment.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
