package com.mage.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.mage.vo.Product;
import com.mage.vo.ShoppingCart;
import com.mage.vo.ShoppingCartItem;
import com.mage.vo.User;

import net.sf.json.JSONObject;

@WebServlet("/shopping")
public class ShoppingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ProductService productService =  new ProductServiceImpl();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求参数
		String oper = req.getParameter("oper");
		//分发功能
		if(!"".equals(oper) && oper != null  && "list".equals(oper)) {
			ShoppingCart(req,resp);//展示购物车
		}else if(!"".equals(oper) && oper != null  && "addShopping".equals(oper)) {
			addShopping(req,resp);//添加购物车操作,从商品详情发送来的！
		}else if(!"".equals(oper) && oper != null  && "updateShopping".equals(oper)) {
			updateShopping(req,resp);//修改 + —
		}else if(!"".equals(oper) && oper != null  && "remove".equals(oper)) {
			remove(req,resp);//删除商品
		}
		
		
	}
	//删除商品
	private void remove(HttpServletRequest req, HttpServletResponse resp) {
		//获取ID
		String spid = req.getParameter("productId");
		int productId = Integer.parseInt(spid); 
		//获取session
		HttpSession session = req.getSession();
		//获取购物车
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		//获取购物车的商品
		List<ShoppingCartItem> shoppingCartItmeList = shoppingCart.getShoppingCartItems();
		//对购物车里的商品进行遍历,找了对应的商品就删除掉！
		for (int i = 0; i < shoppingCartItmeList.size(); i++) {
			if(productId ==shoppingCartItmeList.get(i).getProduct().getId()) {//两种ID相互对应了，找到了就删除！
				shoppingCartItmeList.remove(i);//把此对象删除了！
			}
		}
		//刷新
		shoppingCart.setShoppingCartItems(shoppingCartItmeList);
		session.setAttribute("shoppingCart", shoppingCart);
		//展示
		ShoppingCart(req, resp);
		
	}
	//修改+-
	private void updateShopping(HttpServletRequest req, HttpServletResponse resp) {
		//获取商品的id
		int productId = Integer.parseInt(req.getParameter("productId"));
		//获取商品数量
		int count = Integer.parseInt(req.getParameter("count"));
		//获取session
		HttpSession session = req.getSession();
		//根据id查询商品的信息
		Product product = productService.getProductById(productId);
		
		//获取作用域中购物车的信息
		ShoppingCart shoppingCart  = (com.mage.vo.ShoppingCart) session.getAttribute("shoppingCart");
		
		//获取购物车中商品集合
		List<ShoppingCartItem>  ShoppingCartItemList = shoppingCart.getShoppingCartItems();
		//遍历
		for(ShoppingCartItem sci :ShoppingCartItemList) {
			if(sci.getProduct().getId() == product.getId() ) {
				sci.setCount(count);
				break;
			}
		}
		//存储作用域			
		session.setAttribute("shoppingCart", shoppingCart);
		
		//创建JSONObject
		JSONObject result = new JSONObject();
		result.put("success",true);//key value
		resp.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = resp.getWriter();
			out.print(result.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//添加购物车操作
	private void addShopping(HttpServletRequest req, HttpServletResponse resp) {
		// 获取商品的id
		String spid = req.getParameter("productId");
		int productId =  Integer.parseInt(spid);
		//获取session对象
		HttpSession session = req.getSession();
		//根据ID查询商品的信息
		Product product = productService.getProductById(productId);
		
		//获取购物车对象的信息
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		
		//判断
		if(shoppingCart == null) {//购物车中没有任何商品
			shoppingCart = new ShoppingCart();//创建一个购物车
			//对购物车进行封装
			User currentUser = (User) session.getAttribute("currentUser");
			//存储userid
			shoppingCart.setUserId(currentUser.getId());
		}
		//获取购物车中商品的集合 
		List<ShoppingCartItem>  shoppingCartItemList = shoppingCart.getShoppingCartItems();
		boolean flag  = true;//我们要添加的商品信息在购物车中并没有的话！
		for (ShoppingCartItem scI : shoppingCartItemList) {
			//如商品ID等于你点击的ID
			if(scI.getProduct().getId() == productId) {
				scI.setCount(scI.getCount() + 1 ); //商品加1
				flag  = false;
				break;
			}
		}
		//创建购物车商品对象
		ShoppingCartItem shoppingCartItem =new ShoppingCartItem();
		if(flag) { //购物车中没有我们要添加的商品，我们就要在商品集合中创建一个记录呗
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}
		
		session.setAttribute("shoppingCart", shoppingCart);
		try {
			resp.getWriter().print("true");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//展示购物车
	private void ShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
		//获取提示信息
		String navCode = NavUtil.getNavCode("购物车");
		//存储信息
		req.setAttribute("navCode", navCode);
		//跳转融合页面
		String mainPage = "shopping/shopping.jsp";
		//存储信息
		req.setAttribute("mainPage", mainPage);
		//请求转发
		try {
			req.getRequestDispatcher("/shoppingMain.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
