package com.mage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.OrderProductService;
import com.mage.service.OrderService;
import com.mage.service.impl.OrderProductServiceImpl;
import com.mage.service.impl.OrderServiceImpl;
import com.mage.util.DateUtil;
import com.mage.util.NavUtil;
import com.mage.vo.Order;
import com.mage.vo.OrderProduct;
import com.mage.vo.Product;
import com.mage.vo.ShoppingCart;
import com.mage.vo.ShoppingCartItem;
import com.mage.vo.User;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 订单的service
	private OrderService orderService = new OrderServiceImpl();
	// 订单商品的service
	private OrderProductService orderProductService = new OrderProductServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取请求参数oper
		String oper = req.getParameter("oper");
		// 分发方法
		if (!"".equals(oper) && oper != null && "save".equals(oper)) {
			save(req, resp);
		} else if (!"".equals(oper) && oper != null && "list".equals(oper)) {
			list(req, resp);
		}

	}
	// 个人订单详情
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		// 获取session
		HttpSession session = req.getSession();
		// 获取用户
		User currentUser = (User) session.getAttribute("currentUser");
		// 获取order
		List<Order> orderList = orderService.findAll(currentUser.getId());
		// 设置作用域
		String navCode = NavUtil.getNavCode("个人中心");
		String mainPage = "userCenter/orderList.jsp";
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		req.setAttribute("orderList", orderList);
		// 请求转发
		try {
			req.getRequestDispatcher("/userCenter.jsp").forward(req, resp);		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 结算
	/**
	 * 分析 session中获取user对象 需要购物车，购物车中的商品
	 * 
	 * 创建订单对象 设置创建人，创建时间、订单状态、订单的消费总金额(计算) 设置到订单对象中去！
	 * 
	 * 保存订单呢，保存订单成功之后，再保存订单与订单商品之间的中间表做记录的！
	 */
	private void save(HttpServletRequest req, HttpServletResponse resp) {
		// 获取session
		HttpSession session = req.getSession();
		// 创建一个order对象
		Order order = new Order();
		// 获取当前登录用户
		User currentUser = (User) session.getAttribute("currentUser");
		order.setUser(currentUser);
		order.setCreateTime(new Date());
		order.setOrderNo(DateUtil.getCurrentDateStr());
		order.setStatus(1);

		// 创建购物车
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		// 获取购物车中的商品的
		List<ShoppingCartItem> ShoppingCartItemList = shoppingCart.getShoppingCartItems();

		float cost = 0;
		// 声明一个订单关联的集合
		List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
		// 获取信息
		for (ShoppingCartItem sci : ShoppingCartItemList) {
			// 获取每一个商品的信息
			Product product = sci.getProduct();
			// 创建订单关联表
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setNum(sci.getCount());
			orderProduct.setOrder(order);
			orderProduct.setProduct(product);
			// 该商品的价格*该商品的数量
			cost += product.getPrice() * sci.getCount();
			orderProductList.add(orderProduct);
		}

		// 封装订单表
		order.setCost(cost);
		order.setOrderProductList(orderProductList);
		orderService.saveOrder(order);// 保存订单
		// 中间表的记录
		for (OrderProduct op : orderProductList) {
			orderProductService.save(op);
		}

		String navCode = NavUtil.getNavCode("购物");
		String mainPage = "shopping/shopping-result.jsp";
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		// 清空购物车
		session.removeAttribute("shoppingCart");
		try {
			req.getRequestDispatcher("/shoppingMain.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
