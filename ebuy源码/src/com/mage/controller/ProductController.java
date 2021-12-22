package com.mage.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.ProductService;
import com.mage.service.impl.ProductServiceImpl;
import com.mage.util.NavUtil;
import com.mage.util.PageUtil;
import com.mage.util.StringUtil;
import com.mage.vo.PageBean;
import com.mage.vo.Product;

/**
 * ebuy
 * 
 * #### 1、首页实现 (高云峰  )
	文档-->我们要完成什么功能？怎么实现，用到哪个表？
	具体实行步骤：
	技术:
	
		代码  
	
			


#### 2、商品搜索 (杨松)
		代码  


#### 3、商品详情(余爽)
		代码  


#### 4、用户模块(xxx)



#### 5、购物车(xxx)



#### 6、个人中心(xxx)
 *
 */
@WebServlet("/productServlet")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductServiceImpl();

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		// 请求分发
		String oper = req.getParameter("oper");
		// 确定这个请求是商品小分类搜索的请求
		if (!"".equals(oper) && oper != null && "smallType".equals(oper)) {
			// 查询此小分类的商品
			SearchProductSmallType(req, resp);
		} else if (!"".equals(oper) && oper != null && "search".equals(oper)) {
			s_product(req, resp);
			//确定这个请求时商品一级导航点击搜索来的请求
		}else if(!"".equals(oper) && oper != null && "productType".equals(oper)) {
			searchProductBigType(req,resp);
			//商品详情
		}else if(!"".equals(oper) && oper != null && "addCard".equals(oper)) {
			addProductCard(req,resp);
			
		}
		

	}	
	//请求来自商品的详情 
	private void addProductCard(HttpServletRequest req, HttpServletResponse resp) {
		//获取请求参数
		int productId = Integer.parseInt(req.getParameter("productId"));
		//通过ID获取请求信息
		Product product =  productService.getProductById(productId);
		//文字的导航
		String navCode = NavUtil.getNavCode("商品详情");
		String mainPage = "product/productDetails.jsp";
		//添加最近浏览商品
		saveCurrentBrowse(product,req);
		//存储作用域
		req.setAttribute("mainPage",mainPage );
		req.setAttribute("navCode", navCode);
		req.setAttribute("product", product);
		//请求转发
		try {
			req.getRequestDispatcher("/productMain.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//添加最近浏览商品
	private void saveCurrentBrowse(Product product, HttpServletRequest req) {
		//获取session
		HttpSession session  = req.getSession();
		//从session中获取参数    第一次获取的时候是空的
		List<Product> currentBrowseProduct =  (List<Product>) session.getAttribute("currentBrowseProduct");
		//判断参数值
		if(currentBrowseProduct == null) {
			//添加了一个链表结构的集合，有序的 -》-》-》-》
			currentBrowseProduct = new LinkedList<Product>();
		}
		boolean flag = true;//默认的
		//for
		for(Product p: currentBrowseProduct) {
			//如果产品ID 与传进来的产品ID相同就结束此循环
			if(p.getId() == product.getId()) {
				flag = false;
				//打断循环
				break;
			}
		}
		//当flag false 给当前商品进行添加
		if(flag) {
			currentBrowseProduct.add(0,product);
		}
		//当商品达到了一定的数量我们去掉第一个商品
		if(currentBrowseProduct.size()==4) {
			currentBrowseProduct.remove(3);
		}
		//存储到作用于中
		session.setAttribute("currentBrowseProduct", currentBrowseProduct);
		
	}
	private void searchProductBigType(HttpServletRequest req, HttpServletResponse resp) {
		//获取前台传过来的ID
		int bigTypeId = Integer.parseInt(req.getParameter("id"));
		String page = req.getParameter("page");
		if(StringUtil.isEmpty(page)) {
			page="1";
		}
		//创建一个pagebean对象
		PageBean pageBean = new PageBean(Integer.parseInt(page),8);
		//查询返回数据的list集合
		List productBigType =  productService.findProductBigType(pageBean,bigTypeId);
		System.out.println("productBigType= "+productBigType);
		//total
		int total = productService.getProductCountBigType(bigTypeId);
		//生成分页代码
		String pageCode = PageUtil.getPaginactionNoParmy(
	req.getContextPath()+"/productServlet?oper=productType&id=" +bigTypeId, total, Integer.parseInt(page), 8);
		
		String mainPage = "product/productList.jsp";
		String navCode = NavUtil.getNavCode("交大同学的大分类导航");
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		req.setAttribute("pageCode", pageCode);
		req.setAttribute("productList",productBigType );
		try {
			req.getRequestDispatcher("/productMain.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// top中快速搜索框
	private void s_product(HttpServletRequest req, HttpServletResponse resp) {
		// 获取参数
		String productName = req.getParameter("product");
		// 获取分页内容
		String page = req.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		// 创建一个pagebean对象
		PageBean pageBean = new PageBean(Integer.parseInt(page), 8);
		// 查询返回数据list集合
		List productList = productService.findProductList(pageBean, productName);
		// 查询数据的总数量
		int total = productService.getProductCount(productName);
		// 生产分页代码
		String pageCode = PageUtil.getPaginactionNoParmy(
				req.getContextPath() + "/productServlet?oper=search&product=" + productName, total,
				Integer.parseInt(page), 8);
		String mainPage = "product/productList.jsp";
		String navCode = NavUtil.getNavCode("搜索按钮查询");
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		req.setAttribute("pageCode", pageCode);
		req.setAttribute("productList", productList);
		try {
			req.getRequestDispatcher("/productMain.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 查询此小分类的商品
	private void SearchProductSmallType(HttpServletRequest req, HttpServletResponse resp) {
		// 获取查询的id
		int smallType = Integer.parseInt(req.getParameter("id"));
		// 获取分页内容 开始进来的时候是0
		String page = req.getParameter("page");
		// 你是一个空的值传进来了，我们就给你一个1
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		// 分页对象 每一页显示8条记录
		PageBean pageBean = new PageBean(Integer.parseInt(page), 8);
		// 查询商品的集合
		List<Product> productList = productService.findProductListSmallType(pageBean, smallType);

		// 总记录数
		int total = productService.getProductCountSmallType(smallType);

		// 生产分页代码
		String pageCode = PageUtil.getPaginactionNoParmy(
				req.getContextPath() + "/productServlet?oper=smallType&id=" + smallType, total, Integer.parseInt(page),
				8);
		String mainPage = "product/productList.jsp";
		// 文字说明的导航
		String navCode = NavUtil.getNavCode("华东交大的同学的小分类");
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		req.setAttribute("productList", productList);
		req.setAttribute("pageCode", pageCode);
		try {
			req.getRequestDispatcher("/productMain.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
