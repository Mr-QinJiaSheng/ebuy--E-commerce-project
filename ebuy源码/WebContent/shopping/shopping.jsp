<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.11.1.js"> </script>
</head>
<body>
	<div id="shopping">
		<form action="orderServlet?oper=save" method="post" >
			<table>
				<tr>
					<th style="text-align:center">商品名称</th>
					<th>商品单价</th>
					<th>金额</th>
					<th>购买数量</th>
					<th>操作</th>
				</tr>
				<!-- 展示已添加的商品信息 -->
				<c:forEach items="${shoppingCart.shoppingCartItems}" var="scitems">
					<tr class="productTr">
						<td class="thumb">
							<img  src="${scitems.product.proPic}"  class="imgs"/>
							<a href="productServlet?oper=addCard&productId=${scitems.product.id}">${scitems.product.name}</a>
						</td>
						<td class="price"><!-- 单价 -->
<span>￥<label class="price_" id="price_${scitems.product.id}">${scitems.product.price}</label> </span>
						</td>
						<td class="price"><!-- 单价*个数= 总价-->
<span>￥<label id="productItem_total_${scitems.product.id}">${scitems.product.price*scitems.count}</label> </span>					
						</td>
						<td class="number"><!-- 商品的数量 操作-->
						<input type="hidden" id="productId" value="${scitems.product.id}"/>
						<input type="button" class="min" value=" - "/>
		<input  class="text_box" type="text" style="width:30px;text-align:center;" value="${scitems.count}"/>
						<input type="button" class="add" value=" + "/>
						</td>
						<td class="delete">
						<a href="javascript:removeShopping(${scitems.product.id})">删除</a>
						</td>
					</tr>
				</c:forEach>
				
			</table>
			<div class="button">
				<input type="submit"  value=""/>
			</div>
		</form>
	</div>
	
	<!-- 订单内容显示 -->
	<div class="shopping_list_end">
		<ul>
			<li class="shopping_list_end_2">
				￥<lable id="product_total"></lable>
			</li>
			<li class="shopping_list_end_3">商品金额总计:</li>
		</ul>
	</div>
	
	<!-- 个人信息的显示 -->
	<div style="background-color:#cddbb8;margin-top:10px;font-size:20px;height:40px;text-align:center;">
		<div style="padding:5px">
			<b>个人信息:</b>&nbsp;&nbsp;&nbsp;&nbsp;<b>收件人:</b>${currentUser.trueName}&nbsp;&nbsp;
			<b>收货地址:</b>${currentUser.address}&nbsp;&nbsp;<b>联系电话:</b>${currentUser.mobile}
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){//1  //自动加载
		
		setTotal();//调用计量方法
		
		//商品的总价
		function setTotal(){//2
			var sub = 0;
			$(".productTr").each(function(){//3
				//获取商品的数量
				var number = $(this).find('input[class=text_box]').val();
				//获取商品的价钱
				var price = $(this).find('label[class=price_]').html();
				//计算  数量*价钱=总价钱
				sub += number * price;
			});//3
			 //把总结显示出去
			$("#product_total").html(sub);
			
		}//2
		
		
		//对 “ + ”按钮进行操作
		$(".add").click(function(){
			//获取被修改的input 也就是text_box 
			var t = $(this).parent().find('input[class=text_box]');
			t.val(parseInt(t.val())+1);//上一个count  
			//获取td隐藏域 id的值
			var product_id = $(this).parent().find('input[id=productId]').val();
			//获取对应ID的单价，目的让当前的这个商品跟够爱的数量对应加价！
			var price = $("#price_" + product_id).html();
			//加价！ 单价* 购买个数
			$("#productItem_total_" + product_id).html(price * t.val());
			//刷新
			refreshSession(product_id,t.val());
			//调用总价
			setTotal();
			
		})
		
		//对 “ - ” 按钮进行操作
		$(".min").click(function(){
			//获取被修改的input 也就是text_box 
			var t = $(this).parent().find('input[class=text_box]');
			t.val(parseInt(t.val())-1);
			if(parseInt(t.val()) < 1){
				t.val(1);
			}
			//获取td隐藏域 id的值
			var product_id = $(this).parent().find('input[id=productId]').val();
			//获取对应ID的单价，目的让当前的这个商品跟够爱的数量对应加价！
			var price = $("#price_" + product_id).html();
			//加价！ 单价* 购买个数
			$("#productItem_total_" + product_id).html(price * t.val());
			//刷新
			refreshSession(product_id,t.val());
			//调用总价
			setTotal();
		})
		
		//刷新
		function  refreshSession(productId,count){
			$.post("shopping?oper=updateShopping",{productId:productId,count:count},
				function(result){   //eval()原意 计算并执行里面的js代码;这里是将json字符串转换成json对象
					var result = eval('(' + result + ')');  //如果不加eval()js获取不到里面的属性！
					if(result.success){
						console.log(result);
					}else{
						alert("刷新Session失败了！");
					}
				}
			);
		}
		
		
	});//1
		//删除购物车中的订单
	function removeShopping(productId){
		if(confirm("您确定要删除这个商品吗？")){
			window.location.href="shopping?oper=remove&productId=" + productId;
			
		}
		
		
		
	}

</script>












</html>