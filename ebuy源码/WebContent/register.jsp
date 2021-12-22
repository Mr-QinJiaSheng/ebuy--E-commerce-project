<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function checkForm(){
		// var 变量  userName是变量名  ; 通过$获取ID，就相当与拿到了这个元素的value   { val() }
		var userName=$("#userName").val();
		var password=$("#password").val();
		var rePassWord=$("#rePassWord").val();
		var mobile=$("#mobile").val();
		var address=$("#address").val();
		if(userName==""){
			$("#error").html("用户名不能为空"); //向error指定的元素中添加一句话！
			return false;
		}
		if(password==""){
			$("#error").html("密码不能为空");
			return false;
		}
		if(rePassWord==""){
			$("#error").html("确认密码不能为空");
			return false;
		}
		if(rePassWord!=password){
			$("#error").html("密码要与确认密码相同");
			return false;
		}
		if(mobile==""){
			$("#error").html("手机号码不能为空");
			return false;
		}
		if(address==""){
			$("#error").html("地址不能为空");
			return false;
		}
		return true;
	}
	function checkUserName(){
		//获取你在该元素中写的值
		var userName=$("#userName").val();
		if(userName==""){
			//如果是空的，对不起用户不能空！
			$("#userErrorInfo").html("用户名不能为空 !");
			//鼠标仍然存在此输入框中
			$("#userName").focus();
			return;
		}else{
			//否者我的这个userErrorInfo 什么都不做！
			$("#userErrorInfo").html("");
		}
		
		$.post("userServlet?oper=checkName",{userName:userName},function(data){
			if(data=='true'){
				$("#userErrorInfo").html("");
			}else{
				$("#userErrorInfo").html("用户名已存在，请重新输入！");
				$("#userName").focus();
			}
		}); 
	}
</script>
</head>
<body>
<div id="header" class="wrap">
	<jsp:include page="common/top.jsp"/>
</div>

<div id="register" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>欢迎注册易买网</h1>
			<ul class="steps clearfix">
				<li class="current"><em></em>填写注册信息</li>
				<li class="last"><em></em>注册成功</li>
			</ul>													<!-- onsubmit:点击事件  -->
			<form id="regForm" method="post" action="userServlet?oper=register" onsubmit="return checkForm()">
				<table>				
					<tr>
						<td class="field">用户名(*)：</td>
						<td><input class="text" type="text" id="userName" name="userName" onblur="checkUserName()" />&nbsp;<font id="userErrorInfo" color="red"></font></td>
					</tr>
					<tr>
						<td class="field">登录密码(*)：</td>
						<td><input class="text"  type="password" id="password" name="password"   /></td>
					</tr>
					<tr>
						<td class="field">确认密码(*)：</td>
						<td><input class="text" type="password"  id="rePassWord"  name="rePassWord" /></td>
					</tr>
					
					<tr>
						<td class="field">性别(*)：</td>
						<td>
					    <input type="radio"   name="sex" value="男" checked="checked"/>男&nbsp;&nbsp;
					    <input type ="radio" name="sex" value="女"/>女					    					    
					    </td>						
					</tr>
					<tr>
						<td class="field">出生日期：</td>
						<td>
							<input  type="text"  id="birthday"  name="birthday"  class="Wdate" onClick="WdatePicker()"/>	
						</td> 
					</tr>
					<tr>
						<td class="field">身份证号：</td>
						<td><input class="text" type="text" id="dentityCode" name="dentityCode"  /></td>
					</tr>
					<tr>
						<td class="field">Email：</td>
						<td><input class="text" type="text" id="email" name="email"  /></td>
					</tr>
					<tr>
						<td class="field">手机号码(*)：</td>
						<td><input class="text" type="text" id="mobile" name="mobile" /></td>
					</tr>
					<tr>
						<td class="field">收获地址(*)：</td>
						<td><input class="text" type="text" id="address" name="address" /></td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="submit" name="submit" value="提交注册" /></label></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><font id="error" color="red"></font> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>

<div id="footer">
	<jsp:include page="common/footer.jsp"/>
</div>
</body>
</html>