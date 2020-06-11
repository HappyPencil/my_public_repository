<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 类路径${pageContext.request.contextPath} -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index Page</title>
</head>

<body
	style="background-image: url(image/repeat-y.jpg); background-repeat: repeat-y; background-position: top; background-size: 100%">

	<h1>
		<p align="center" style="margin-top: 10%; color: rgb(255, 255, 65)">Index
			Page</p>
	</h1>
	<!-- <p style="margin-bottom:0px"> -->

	<table align="center" style="border: transparent; margin-bottom: 0px">
		<tr style="border: transparent">
			<td><a href="index.jsp"
				style="text-decoration: none; color: rgb(255, 255, 65)">index</a></td>
			<td>&nbsp</td>
			<td><a href="ProductServlet" style="text-decoration: none">products</a></td>
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
	<p align="center">
	<div align="center">
		<table name="Information" align="center" style="border: transparent">

			<tr style="height: 25px; border: transparent" align="center">
				<td style="width: 350px" align="center">
					<form action="SearchServlet" method="post">
						<input type="text" name="search"
							style="width: 200px; height: 20px"
							placeholder="search what you want"> <input type="submit"
							value="Search"
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
			}
		%>
		<br> <span style="color: red"><%=message%></span>
		<%
			request.removeAttribute("message");
		%>
	</div>

	
	<!-- <center>
		<button onclick="window.location.href='test1/demo_1_list.html'">button_1_test</button>
	</center>
	<center>
		<button onclick="window.location.href='test2/demo_2_list.html'">button_2_test</button>
	</center> -->

	<script type="text/javascript">
		function getXMLHttpRequest() {
			try {
				return new XMLHttpRequest();
			} catch (ajaxE) {
				try {
					return new XMLHttpRequest("Msxml2.XMLHTTP");
				} catch (ajaxE) {
					try {
						return new XMLHttpRequest("Microsoft.XMLHTTP");
					} catch (ajaxE) {
						throw ajaxE;
					}
				}
			}
		}
		function ajax() {
			var ajaxR = getXMLHttpRequest();
			ajaxR.open("post", "SearchServlet", true);
			ajaxR.send(null);
			ajaxR.onreadystatechange()
			{
				if (ajaxR.readyState == 4 && ajaxR.status == 200) {
					var text = ajaxR.responseText;

				}
			}
			;
		}
	</script>
</body>
</html>