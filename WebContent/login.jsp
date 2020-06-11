<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
</head>
<body style="background-image:url(image/repeat-y.jpg);background-repeat:repeat-y;background-position:top;background-size:100%">

	<h1><p align="center" style="margin-top:10%;color:rgb(255,20,20)">Login Page</p></h1>
	
	<table align="center" style="border:transparent;margin-bottom:0px">
			<tr style="border:transparent">
				<td><a href="index.jsp" style="text-decoration:none">index</a></td>
				<td>&nbsp</td>
				<td><a href="ProductServlet" style="text-decoration:none">products</a></td>
				<td>&nbsp</td>
				<td><a href="login.jsp" style="text-decoration:none;color:rgb(255,20,20)">login</a></td>
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
	
	<hr width="70%" align="center" style="margin-top:0px"><p align="center">

	<center>	
	<form action="LoginServlet" method="post">
		<p><input type="text" name="id" style="width:200px;height:30px" placeholder=" ID"></p>
		<p><input type="password" name="password" style="width:200px;height:30px" placeholder=" Password"></p>
		<p><input type="submit" value="Login" style="width:100px;height:30px">
		<input type="button" value="Register" onclick="window.location.href='register.jsp'" style="width:100px;height:30px"></p>
	</form>
	<%String message = ""; %>
		<%if(request.getAttribute("message")!=null){
			message = (String)request.getAttribute("message");
			request.removeAttribute("message");
			} %>
		<br>
		<span style="color:red"><%=message %></span>
	</center>
</div>

</body>
</html>