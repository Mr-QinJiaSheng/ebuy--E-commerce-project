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
	<!-- 文字nav  id #| |  class . 来设置样式-->
	<div id="#position" class="wrap">
		${navCode}
	</div>
	<div id="main" class="wrap">
		<jsp:include page="${mainPage}"/>
		<div class="clear"></div>
	</div>
	<div id="footer">
		<jsp:include page="common/footer.jsp" />
	</div>
</body>
</html>