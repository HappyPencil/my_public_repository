<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Details page</title>
</head>
<body style="background-image:url(image/repeat-y.jpg);background-repeat:repeat-y;background-position:top;background-size:100%">

	<h1><p align="center" style="margin-top:10%;color:rgb(225,25,225)">Details Page</p></h1>
	
	<table align="center" style="border:transparent;margin-bottom:0px">
			<tr style="border:transparent">
				<td><a href="index.jsp" style="text-decoration:none">index</a></td>
				<td>&nbsp</td>
				<td><a href="ProductServlet" style="text-decoration:none;color:rgb(255,25,255)">products</a></td>
				<td>&nbsp</td>
				<td><a href="login.jsp" style="text-decoration:none">login</a></td>
				<td>&nbsp</td>
				<td><a href="register.jsp" style="text-decoration:none">register</a></td>
				<td>&nbsp</td>
				<td><a href="personal.jsp" style="text-decoration:none">personal</a></td>
				<td>&nbsp</td>
				<td><a href="cart.jsp" style="text-decoration:none">cart</a></td>
				<td>&nbsp</td>
				<td><a href="admin.jsp" style="text-decoration:none">admin</a></td>
			</tr>
	</table>
	
	<hr width="70%" align="center" style="margin-top:0px">

	<c:forEach items="${currentDetailList}" var="currentDetail">
	<table align="center" style="border:transparent;margin-bottom:10px;margin-top:10px">
		<tr>
			<td rowspan="6" align="right" valign="middle">&nbsp<%-- ${sessionScope.productPictures.picture} --%></td>
			<td align="left" valign="middle" colspan="4" ><a href="DetailServlet" style="text-decoration:none;color:rgb(100,0,255)">
			<b>${currentDetail.name}</b></a></td>
		</tr>
		<tr>
			<td align="right" valign="middle">ID:</td><td align="left" valign="middle" style="text-decoration:none;color:rgb(100,0,255)">
			${currentDetail.id}</td>
		</tr>
		<tr>
			<td align="right" valign="middle">PRICE:$</td><td align="left" valign="middle" style="text-decoration:none;color:rgb(100,0,255)">
			${currentDetail.price}</td>
			<td colspan="2" align="right" valign="middle">
				<form action="AddCartServlet" method="get">
					<input type="text" name="currentProductID" value="${currentDetail.id}" style="visibility: hidden;width:1px">
					<input type="submit" value="Mark ">
				</form>
			</td>
		</tr>
		<tr>
			<td align="right" valign="middle">COUNT:</td><td align="left" valign="middle" style="text-decoration:none;color:rgb(100,0,255)">
			${currentDetail.count}</td>
			<td colspan="3" align="right" valign="middle">
				<form action="AddOrderServlet" method="get">
					<input type="text" name="buy" value="${currentDetail.id}" style="visibility: hidden;width:1px">
					<input type="submit" value=" Buy ">
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="6" align="center" valign="top">${currentDetail.detail}</td>
		</tr>
	</table>
	</c:forEach>
	<center>
		<%String message = ""; %>
		<%if(request.getAttribute("message")!=null){
			message = (String)request.getAttribute("message");
			request.removeAttribute("message");
			} %>
		<br>
		<span style="color:red"><%=message %></span>
	</center>

</body>
</html>