<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order page</title>
</head>
<body style="background-image:url(image/repeat-y.jpg);background-repeat:repeat-y;background-position:top;background-size:100%">
	
	<h1><p align="center" style="margin-top:10%;color:rgb(25,100,130)">Order Page</p></h1>
	
	<table align="center" style="border:transparent;margin-bottom:0px">
			<tr style="border:transparent">
				<td><b>Please confirm your order information!</b></td>
			</tr>
	</table>
	<hr width="70%" align="center" style="margin-bottom:-10px"><p align="center"><b>ACCOUNT</b></p><hr width="70%" align="center" style="margin-top:-10px">
	
	
	<form action="CheckOutServlet" method="post">
	
	<c:forEach items="${personal}" var="user"><!-- readonly="readonly" 白色不可编辑  --><!-- disabled="disabled" 灰色不可编辑 -->
		<table align="center" style="border:transparent;margin-bottom:50px;margin-top:10px">
			<tr>
				<td align="left">USER ID :</td><td align="center"><input  disabled="disabled" type="text" name="user_id" value="${user.id}" style="background:transparent;border:0"></td>
			</tr>
			<tr>
				<td align="left">USER NAME :</td><td align="center"><input  disabled="disabled" type="text" name="user_name" value="${user.name}" style="background:transparent;border:0"></td>
			</tr>
			<tr>
				<td align="left">ACCOUNT BALANCE :</td><td align="center"><input  disabled="disabled" type="text" name="user_name" value="${user.count}" style="background:transparent;border:0"></td>
			</tr>
		</table>
	</c:forEach>
	

	<hr width="70%" align="center" style="margin-bottom:-10px"><p align="center"><b>ITEMS</b></p><hr width="70%" align="center" style="margin-top:-10px">
	
	<c:forEach items="${order}" var="product">
	<table align="center" style="border:transparent;margin-bottom:50px;margin-top:10px">
		<tr>
			<td align="left">PRODUCT ID :</td><td align="center"><input disabled="disabled" type="text" name="product_id" value="${product.id}" style="background:transparent;border:0"></td>
		</tr>
		<tr>
			<td align="left">PRODUCT NAME :</td><td align="center"><input disabled="disabled" type="text" name="product_name" value="${product.name}" style="background:transparent;border:0"></td>
		</tr>
		<tr>
			<td align="left">PRODUCT PRICE :</td><td align="center"><input disabled="disabled" type="text" name="product_price" value="${product.price}" style="background:transparent;border:0"></td>
		</tr>
		<tr>
			<td align="left">PRODUCT COUNT :</td><td align="center"><input disabled="disabled" type="text" name="product_count" value="${product.count}" style="background:transparent;border:0"></td>
		</tr>
		<tr>
			<td align="left">TOTAL PRICE :</td><td align="center"><input disabled="disabled" type="text" name="total_price" value="${product.price * product.count}" style="background:transparent;border:0"></td>
		</tr>
	</table>
	</c:forEach>
	
	<b><p align="center" style="margin-top:30px"><input type="submit" value="CHECK OUT" style="width:200px;height:40px"></p></b>
	</form>
	
	
		<center>
			<%String message = ""; %>
			<%if(request.getAttribute("message")!=null){
				message = (String)request.getAttribute("message");
				request.removeAttribute("message");
				} %>
			<br/>
		<span style="color:red"><%=message %></span><br/>
		<a href="cart.jsp" style="text-decoration:none;color:rgb(25,100,130)">back</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="products.jsp" style="text-decoration:none;color:rgb(25,100,130)">cancel</a><br/><br/><br/><br/>
		</center>

</body>
</html>