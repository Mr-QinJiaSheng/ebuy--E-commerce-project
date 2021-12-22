package com.mage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mage.service.NewsService;
import com.mage.service.impl.NewServiceImpl;
import com.mage.util.NavUtil;
import com.mage.vo.News;

@WebServlet("/newsServlet")
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsService newsService =new NewServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取从前台传过来的用户点击的公告，通过ID来获取
		int newsId = Integer.parseInt(request.getParameter("newsId"));
		News news = newsService.getNewsById(newsId);
		String navCode = NavUtil.getNavCode("交大新闻");
		String mainPage = "news/newsDetails.jsp";
		request.setAttribute("news",news );
		request.setAttribute("navCode",navCode );
		request.setAttribute("mainPage",mainPage );
		request.getRequestDispatcher("/newsMain.jsp").forward(request, response);
	}

}
