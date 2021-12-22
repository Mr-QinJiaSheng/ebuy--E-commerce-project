package com.mage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.UserService;
import com.mage.service.impl.UserServiceImpl;
import com.mage.util.DateUtil;
import com.mage.util.NavUtil;
import com.mage.vo.User;
//用户的管理器
@WebServlet("/userServlet") //控制层！
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
	private String error;
	//分发方法 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//请求分发
		String oper = request.getParameter("oper");
		//确定这个请求是一个注册的请求			
		if(!"".equals(oper) && oper != null && "register".equals(oper)) {
			register(request,response);
			//确定这个请求是检查用户名是否唯一的请求
		}else if(!"".equals(oper) && oper != null && "checkName".equals(oper)){
			checkName(request,response);
			//确定这是一个请求登录操作
		}else if(!"".equals(oper) && oper != null && "login".equals(oper)) {
			userLogin(request,response);
		}else if(!"".equals(oper) && oper != null && "logout".equals(oper)) {
			logout(request,response);
			//个人中心 
		}else if(!"".equals(oper) && oper != null && "userCenter".equals(oper)) {
			userCenter(request,response);
		}else if(!"".equals(oper) && oper != null && "userInfo".equals(oper)) {
			userInfo(request,response);
		}else if(!"".equals(oper) && oper != null && "userSave".equals(oper)) {
			userSave(request,response);
		}
	}
	
	//用户信息修改的保存方法
	private void userSave(HttpServletRequest request, HttpServletResponse response) {
		//获取session对象
		HttpSession session = request.getSession();
		//从session对象中获取当前的登录用户
		User  u = (User) session.getAttribute("currentUser");
		//具体信息
		String userName = request.getParameter("userName");
		String trueName = request.getParameter("trueName");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		Date birthday = null;
		try {
			birthday = DateUtil.formarString(request.getParameter("birthday"), "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dentityCode = request.getParameter("dentityCode");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		//构建对象
		User user =new User(userName, password, sex, birthday, dentityCode, email, mobile, address); 
		user.setTrueName(trueName);
		user.setId(u.getId());
		//体现更新
		userService.update(user);
		//更改之后该做更新sessnion中的用户了！
		session.setAttribute("currentUser", user);
		//跳转页面
		userCenter(request, response);
	}
	//修改用户信息的跳转方法
	private void userInfo(HttpServletRequest request, HttpServletResponse response) {
		//获取session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		String navCode = NavUtil.getNavCode("修改当前用户");
		String mainPage = "userCenter/userSave.jsp";
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("user", user);
		try {
			request.getRequestDispatcher("userCenter.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//跳转到个人中心的方法
	private void userCenter(HttpServletRequest request, HttpServletResponse response) {
		//写一个提示信息导航
		String navCode = NavUtil.getNavCode("个人中心");
		//页面的动态镶嵌
		String mainPage = "userCenter/userInfo.jsp";
		
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		try {//请求转发 
			request.getRequestDispatcher("/userCenter.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//注销方法
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		//清空session
		request.getSession().invalidate();
		try {
			request.getRequestDispatcher("/index").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//实现登录 这一层承上启下的！
	private void userLogin(HttpServletRequest request, HttpServletResponse response) {
		//获取请求参数准备登录
		String uname = request.getParameter("userName");
		String pwd = request.getParameter("password");
		//用户输入的验证码
		String imageCodeInput = request.getParameter("imageCode");
		//创建一个session 
		HttpSession session = request.getSession();
		//AWT生成的验证码
		String imageCode = (String) session.getAttribute("sRand");
		//创建对象了！
		User user = new User(uname,pwd);
		//比较 用户输入和系统生成是否一致！
		if(imageCodeInput.equals(imageCode)) {
			//判断用户是否注册 
			User currentUser = userService.login(user);
			if(currentUser != null) {
				session.setAttribute("currentUser", currentUser);
				try {//登录成功后跳转到主页面，有你名字的！
					request.getRequestDispatcher("/index").forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}else {
				error ="用户名或者密码错误！";
			}
		}else {
			error="验证码错误";
		}
		request.setAttribute("error", error);
		try {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//验证用户的
	private void checkName(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		try {
		boolean flag =  userService.checkName(userName);
			PrintWriter out =  response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//注册的控制层
	private void register(HttpServletRequest request, HttpServletResponse response) {
		//获取请求参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		Date birthday = null;
		try {
			birthday = DateUtil.formarString(request.getParameter("birthday"),"yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dentityCode = request.getParameter("dentityCode");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		
		//构建对象
User user = new User(userName, password, sex, birthday, dentityCode, email, mobile, address);
		userService.register(user);
		try {
			request.getRequestDispatcher("/reg-result.jsp").forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException Error");
			e.printStackTrace();
		}
		
	}

}
