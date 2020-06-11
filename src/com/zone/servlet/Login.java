package com.zone.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.User;
import com.zone.service.BaseService;
import com.zone.service.ServiceFactory;
import com.zone.service.UserService;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		if ((request.getSession().getAttribute("currentUser")!=null)) {
			String message = "Sorry,you have login already!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			String idS = request.getParameter("id");
			String password = request.getParameter("password");
			if (idS!=null&&password!=null&&(!(idS.trim().equals("")))&&(!(password.trim().equals("")))) {
				Integer id = 0;
				try {
					id = Integer.parseInt(idS);
					//默认password不含非法字符
				} catch (Exception e) {
					String message = "Sorry,your ID is illegal !";
					request.setAttribute("message", message);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				if ((id!=null)&&(id!=0)) {
					UserService userServiceImp = (UserService)ServiceFactory.getServiceFactory()
							.getServiceImp(UserService.class);
					User user = userServiceImp.login(id, password);
					if (user!=null) {
						request.removeAttribute("message");
						request.getSession().setAttribute("currentUser", user);
						//默认一个管理员一个用户同时
						List<com.zone.bean.User> personal = new ArrayList<com.zone.bean.User>(2);
						if (request.getSession().getAttribute("personal") != null) {
							personal = (List<com.zone.bean.User>)request.getSession().getAttribute("personal");
							personal.add(user);
						} else {
							personal.add(user);
						}
						request.getSession().setAttribute("personal", personal);
						request.getRequestDispatcher("personal.jsp").forward(request, response);
					}else {
						String message = "Sorry,this account isn't exist!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				}else {
					String message = "Sorry,your ID is illegal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}else {
				String message = "Sorry,your message is illegal!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}

}
