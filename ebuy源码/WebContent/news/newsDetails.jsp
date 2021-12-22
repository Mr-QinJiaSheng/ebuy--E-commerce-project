<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="news" >
		<!-- 获取作用域对象 ，点它 的属性title -->
		<h1>${news.title}</h1>
		<div class="content"> 
			${news.content}
		</div>
	</div>
</body>
</html>