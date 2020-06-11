<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Products page</title>
</head>
<body
	style="background-image: url(image/repeat-y.jpg); background-repeat: repeat-y; background-position: top; background-size: 100%">

	<h1>
		<p align="center" style="margin-top: 10%; color: rgb(255, 25, 255)">Products
			Page</p>
	</h1>

	<table align="center" style="border: transparent; margin-bottom: 0px">
		<tr style="border: transparent">
			<td><a href="index.jsp" style="text-decoration: none">index</a></td>
			<td>&nbsp</td>
			<td><a href="ProductServlet"
				style="text-decoration: none; color: rgb(255, 25, 255)">products</a></td>
			<td>&nbsp</td>
			<td><a href="login.jsp" style="text-decoration: none">login</a></td>
			<td>&nbsp</td>
			<td><a href="register.jsp" style="text-decoration: none">register</a></td>
			<td>&nbsp</td>
			<td><a href="personal.jsp" style="text-decoration: none">personal</a></td>
			<td>&nbsp</td>
			<td><a href="cart.jsp" style="text-decoration: none">cart</a></td>
			<td>&nbsp</td>
			<td><a href="admin.jsp" style="text-decoration: none">admin</a></td>
		</tr>
	</table>

	<hr width="70%" align="center" style="margin-top: 0px">

	<div align="center">
		<table name="Information" align="center" style="border: transparent">

			<tr style="height: 25px; border: transparent" align="center">
				<td style="width: 350px" align="center">
					<form action="SearchServlet" method="post">
						<input type="text" name="search"
							style="width: 200px; height: 20px"
							placeholder="serch product by id or name"> <input
							type="submit" value="Search"
							style="width: 80px; height: 25px; display: inline-block">
					</form>
				</td>
			</tr>

		</table>
		<%
			String message = "";
		%>
		<%
			if (request.getAttribute("message") != null) {
				message = (String) request.getAttribute("message");
				request.removeAttribute("message");
			}
		%>
		<br> <span style="color: red"><%=message%></span>
	</div>

	<div >
		<c:forEach items="${sessionScope.products}" var="product">
			<table
				style="border: transparent; margin-bottom: 10px; margin-top: 10px">
				<!-- align="center" -->
				<tr>
					<td rowspan="6" align="right" valign="middle">&nbsp<%-- ${sessionScope.productPictures.picture} --%></td>
					<td align="left" valign="middle" colspan="5"
						style="color: rgb(255, 25, 255)"><b>${product.name}</b></td>

				</tr>
				<tr>
					<td align="right" valign="middle">ID:</td>
					<td align="left" valign="middle"
						style="text-decoration: none; color: rgb(255, 25, 255)">${product.id}</td>
				</tr>
				<tr>
					<td align="right" valign="middle">PRICE:$</td>
					<td align="left" valign="middle"
						style="text-decoration: none; color: rgb(255, 25, 255)">${product.price}</td>
					<td colspan="3" align="right" valign="middle">
						<form action="DetailServlet" method="get">
							<input type="text" name="currentDetail" value="${product.id}"
								style="visibility: hidden; width: 1px"> <input
								type="submit" value="Detail">
						</form>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">COUNT:</td>
					<td align="left" valign="middle"
						style="text-decoration: none; color: rgb(255, 25, 255)">${product.count}</td>
					<td align="right" valign="middle">&nbsp</td>
					<td colspan="2" align="right" valign="middle">
						<form action="AddCartServlet" method="get">
							<input type="text" name="currentProductID" value="${product.id}"
								style="visibility: hidden; width: 1px"> <input
								type="submit" value="Mark ">
						</form>
					</td>
				</tr>
			</table>
		</c:forEach>
	</div>

	<div align="center">
		<table name="Information" align="center" style="border: transparent">

			<tr style="height: 25px; border: transparent" align="center">
				<td style="width: 350px" align="center">
					<form action="ProductServlet" method="post">
						<input type="text" name="pageNumber"
							style="width: 96px; height: 20px" value="${pageBean.pageID}"
							placeholder="Page"> <input type="text" name="recordCount"
							style="width: 96px; height: 20px" value="${pageBean.recordCount}"
							placeholder="Count"> <input type="submit" value="Jump"
							style="width: 80px; height: 25px; display: inline-block">
					</form>
				</td>
			</tr>

		</table>
	</div>

</body>
</html>