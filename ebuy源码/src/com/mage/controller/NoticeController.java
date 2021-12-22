package com.mage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mage.service.NoticeService;
import com.mage.service.impl.NoticeServiceImpl;
import com.mage.util.NavUtil;
import com.mage.vo.Notice;

/**
 * Servlet implementation class NoticeController
 */
@WebServlet("/noticeServlet")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService noticeService = new NoticeServiceImpl();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取从前台传过来的用户点击的公告，通过ID来获取
		int noticeId = Integer.parseInt(request.getParameter("noticeId"));
		Notice notice  = noticeService.getNoticeById(noticeId);
		String navCode = NavUtil.getNavCode("交大公告");
		//把公告标题和内容显示
		String mainPage = "notice/noticeDetails.jsp";
		request.setAttribute("notice",notice );
		request.setAttribute("navCode",navCode );
		request.setAttribute("mainPage",mainPage );
		request.getRequestDispatcher("/noticeMain.jsp").forward(request, response);
	}

}
