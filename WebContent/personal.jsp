<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personal page</title>
</head>
<body style="background-image:url(image/repeat-y.jpg);background-repeat:repeat-y;background-position:top;background-size:100%">
	
	<h1><p align="center" style="margin-top:10%;color:rgb(5,255,5)">Personal Page</p></h1>
	
	<table align="center" style="border:transparent;margin-bottom:0px">
			<tr style="border:transparent">
				<td><a href="index.jsp" style="text-decoration:none">index</a></td>
				<td>&nbsp</td>
				<td><a href="ProductServlet" style="text-decoration:none">products</a></td>
				<td>&nbsp</td>
				<td><a href="login.jsp" style="text-decoration:none">login</a></td>
				<td>&nbsp</td>
				<td><a href="register.jsp" style="text-decoration:none">register</a></td>
				<td>&nbsp</td>
				<td><a href="personal.jsp" style="text-decoration:none;color:rgb(5,255,5)">personal</a></td>
				<td>&nbsp</td>
				<td><a href="cart.jsp" style="text-decoration:none">cart</a></td>
				<td>&nbsp</td>
				<td><a href="admin.jsp" style="text-decoration:none">admin</a></td>
			</tr>
	</table>
	
	<hr width="70%" align="center" style="margin-top:0px">

	<div align="center">
		<c:forEach items="${sessionScope.personal}" var="user">
		<table name="Information" align="center" style="border:transparent">
			<tr ><td style="width:400px;height:30px;color:rgb(5,255,5)"  align="center" colspan="4">ALL MY INFOMATION</td></tr>
			
			<tr style="height:30px;border:transparent" align="center">
				<td style="width:200px" align="right">MY ID</td>
				<td style="width:100px;color:rgb(5,255,5)" align="left">${user.id }</td>
				<td style="width:100px" align="left">
					<input type="button" value="Logout" onclick="window.location.href='LogoutServlet'" style="width:90px;height:20px">
				</td>
			</tr>
			<tr style="height:30px;border:transparent" align="center">
				<td style="width:200px" align="right">MY NAME</td>
				<td style="width:100px;color:rgb(5,255,5)" align="left">${user.name }</td>
				<td style="width:100px" align="left">
					<input type="button" value="Rename" onclick="window.location.href='RenameServlet'" style="width:90px;height:20px">
				</td>
			</tr>
			<tr style="height:30px;border:transparent" align="center">
				<td style="width:200px" align="right">MY PASSWORD</td>
				<td style="width:100px;color:rgb(5,255,5)" align="left">${user.password }</td>
				<td style="width:100px" align="left">
					<input type="button" value="Reset" onclick="window.location.href='RenameServlet'" style="width:90px;height:20px">
				</td>
			</tr>
			<tr style="height:30px;border:transparent" align="center">
				<td style="width:200px" align="right">MY COUNT</td>
				<td style="width:100px;color:rgb(5,255,5)" align="left">${user.count }</td>
				<td style="width:100px" align="left">
					<input type="button" value="Recharge" onclick="window.location.href='RechargeServlet'" style="width:90px;height:20px">
				</td>
				
			</tr>
			
		</table>
		</c:forEach>
		
		<div align="center">
			<%String message = ""; %>
			<%if(request.getAttribute("message")!=null){
				message = (String)request.getAttribute("message");
				request.removeAttribute("message");
			} %>
			<span style="color:red"><%=message %></span>
		</div>
	</div>
<!-- pageBean -->
</body>
</html>