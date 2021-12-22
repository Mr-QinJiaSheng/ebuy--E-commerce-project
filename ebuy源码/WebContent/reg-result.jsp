<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 																			
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header" class="wrap">
		<jsp:include page="common/top.jsp" />
	</div>
	<div id="register" class="wrap">
		<div class="shadow">
			<div class="box">
				<h1>欢迎注册易买网</h1>
				<ul class="steps clearfix">
					<li class="finished"><em></em>填写注册信息</li>
					<li class="last-current"><em></em>注册成功</li>
				</ul>
				<div class="msg ">
					<p>恭喜:注册成功</p>
					<p>正在进入首页...</p>
		<script type="text/javascript">
			setTimeout("location.href='${pageContext.request.contextPath}/index'",3000);
		</script>
				</div>
			</div>
		</div>
	</div>
	
<div id="footer">
	<jsp:include page="common/footer.jsp" />
</div>
</body>
</html>