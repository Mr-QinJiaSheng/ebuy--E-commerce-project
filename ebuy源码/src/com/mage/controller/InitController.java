package com.mage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.NewsService;
import com.mage.service.NoticeService;
import com.mage.service.ProductBigTypeService;
import com.mage.service.ProductService;
import com.mage.service.TagService;
import com.mage.service.impl.NewServiceImpl;
import com.mage.service.impl.NoticeServiceImpl;
import com.mage.service.impl.ProductBigTypeServiceImpl;
import com.mage.service.impl.ProductServiceImpl;
import com.mage.service.impl.TagServiceImpl;
import com.mage.vo.News;
import com.mage.vo.Notice;
import com.mage.vo.Product;
import com.mage.vo.ProductBigType;
import com.mage.vo.Tag;

/**
 * 拦截进入此servlet
 */
public class InitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//多态  降低耦合性 ，让我们的代码可以有扩展性！
	private ProductBigTypeService bigTypeService = new ProductBigTypeServiceImpl();
	//热门标签
	private TagService tagService= new TagServiceImpl();
	//新闻
	private NewsService newsService = new NewServiceImpl();
	//公告
	private NoticeService noticeService =new NoticeServiceImpl();
	//特价商品
	private ProductService prductService = new ProductServiceImpl();
	
	/**
	 * 分发器
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//查询类别信息存放到作用域中
		List<ProductBigType> bigTypeList = bigTypeService.findAllBigType();
		//查询热门标签的导航栏
		List<Tag> tagList = tagService.findAll();
		//查询新闻的
		List<News> newsList = newsService.findAll();
		//查询公告的
		List<Notice>  noticeList = noticeService.findAll();
		//查询热度的商品
		List<Product> hotProductList = prductService.findHotProductList();
		//查询商品特价的
		List<Product> specialPriceProductList = prductService.findSpecialPrice();
		
		//提示作用域 将查询的结果放到作用域中 
		HttpSession session =req.getSession();//此处是创建也是获取！ 
		session.setAttribute("newsList", newsList);
		session.setAttribute("noticeList", noticeList);
		session.setAttribute("hotProductList", hotProductList);
		session.setAttribute("specialPriceProductList", specialPriceProductList);
		
		session.setAttribute("tagList", tagList);
		session.setAttribute("bigTypeList", bigTypeList);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

}
