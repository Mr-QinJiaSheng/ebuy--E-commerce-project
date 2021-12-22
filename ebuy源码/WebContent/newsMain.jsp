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
		<jsp:include page="common/top.jsp" /><!-- 这里有css样式必须有！ -->
	</div>
	
	<div id="position" class="wrap">
		<!-- 文字导航 -->
		${navCode}
	</div>

	<div id="main" class="wrap">
		<div class="lefter">
			<jsp:include page="common/left.jsp" />
		</div>
		<jsp:include page="${mainPage}" />
		<div class="spacer clear"></div>
	</div>
	<div id="footer">
		<jsp:include page="common/footer.jsp" />
	</div>
</body>
</html>