package com.zone.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.Admin;
import com.zone.bean.User;
import com.zone.service.AdminService;
import com.zone.service.ServiceFactory;
import com.zone.service.UserService;

public class AdminLogin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		if ((request.getSession().getAttribute("currentAdmin") != null)) {
			String message = "Sorry,you have login already!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		} else {
			String idS = request.getParameter("id");
			String password = request.getParameter("password");
			if (idS != null && password != null && (!(idS.trim().equals(""))) && (!(password.trim().equals("")))) {
				Integer id = 0;
				try {
					id = Integer.parseInt(idS);
					// 默认password不含非法字符
				} catch (Exception e) {
					String message = "Sorry,your ID is illegal !";
					request.setAttribute("message", message);
					request.getRequestDispatcher("admin.jsp").forward(request, response);
				}
				if ((id != null) && (id != 0)) {
					AdminService adminServiceImp = (AdminService) ServiceFactory.getServiceFactory()
							.getServiceImp(AdminService.class);
					Admin admin = adminServiceImp.login(id,password);
					if (admin != null) {
						request.removeAttribute("message");
						request.getSession().setAttribute("currentAdmin", admin);
						//将管理员信息以用户信息方式呈现在personal页面上
						User userA  = new User();
						userA.setId(admin.getId());
						userA.setPassword(admin.getPassword());
						//将多余字段填充默认信息并且默认最多一个用户和一个管理员同时登录
						userA.setName("ADMIN");
						userA.setCount(new BigDecimal(0));
						List<com.zone.bean.User> personal = new ArrayList<com.zone.bean.User>(2);
						if (request.getSession().getAttribute("personal") != null) {
							personal = (List<com.zone.bean.User>)request.getSession().getAttribute("personal");
							personal.add(userA);
						} else {
							personal.add(userA);
						}
						request.getSession().setAttribute("personal", personal);
						request.getRequestDispatcher("personal.jsp").forward(request, response);
					} else {
						String message = "Sorry,this account isn't exist!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("admin.jsp").forward(request, response);
					}
				} else {
					String message = "Sorry,your ID is illegal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("admin.jsp").forward(request, response);
				}
			} else {
				String message = "Sorry,your message is illegal!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("admin.jsp").forward(request, response);
			}
		}
	}
}
