<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="css/bootstrap3.min.css" >
<script src="js/bootstrap.min.js"></script>

</head>
<body>
<!-- NAV start  -->
<div id="logo">
	<img src="images/logo.gif" />
</div>
<!-- 首页右侧注册与登录部分 -->
 <div class="help">
	<c:choose>
		<c:when test="${not empty currentUser}">
			<!-- 真是数据要查，要传过来！ 三目运算符-->
			<a href="shopping?oper=list" class="shopping">
			购物车(${shoppingCart.shoppingCartItems == null ? 0 : shoppingCart.shoppingCartItems.size() }件商品)</a>
			<a href="userServlet?oper=userCenter">${currentUser.userName}</a>
			<a href="javascript:logout()">注销</a>
			<a href="register.jsp">注册</a>
			<a href="comment?oper=list">留言</a> 
		</c:when>
		<c:otherwise>
			<a href="javascript:checkLogin()" class="shopping">购物车</a>
			<a href="login.jsp">登录</a>
			<a href="register.jsp">注册</a>
			<a href="javascript:checkLogin()">留言</a>
		</c:otherwise>
	</c:choose>
	<form action="productServlet" method="post"> 
		 <input type="text" name="product" autocomplete="off" /> 
		 <input type="hidden" name="oper" value="search"/>
		  <input type="submit" value="搜索"/> 
	</form> 
</div> 
<!-- 大分类的NAV -->
<div class="nav nav-pills" >
		<li><a href="index" >首页</a></li>
		<c:forEach items="${bigTypeList}" var="bType">
			<li>
			<a   href="productServlet?oper=productType&id=${bType.id}">${bType.name}</a>
			</li>
		</c:forEach>
</div>
<!-- 大分类的NAV -->
<!-- NAV 二级导航 -->
<div id="childNav">
	<div class="wrap">
		<ul class="clearfix">
			<c:forEach items="${tagList}" var="tag" varStatus="status">
				<c:choose>
						<c:when test="${status.index==0}">
							<li class="first">
							<a href="${tag.url}" target="_blank">${tag.name}</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="${tag.url}" target="_blank">${tag.name}</a>
							</li>
						</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
</div>
</body>
<script type="text/javascript">
	function logout(){
		if(confirm('您确定要退出系统吗？')){
			window.location.href="userServlet?oper=logout";
		}
	}
	
	function checkLogin(){
		if('${currentUser.userName}' == ''){
			alert("请先登录");
		}else {
			window.location.href="shopping?oper=list";
		}
	}

</script>
</html>