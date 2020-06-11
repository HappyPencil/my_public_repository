package com.zone.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.User;
import com.zone.service.ServiceFactory;
import com.zone.service.UserService;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		if (request.getSession().getAttribute("currentUser")!=null) {
			String message = "Sorry,you have already login!";
			request.getSession().setAttribute("message", message);
			request.setAttribute("message", message);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}else {
			String name = request.getParameter("user_name");
			String password = request.getParameter("user_password");
			String count = request.getParameter("user_count");
			if (name!=null&&password!=null&&count!=null
					&&(!(name.trim().equals("")))
					&&(!(password.trim().equals("")))
					&&(!(count.trim().equals("")))
					) {
				BigDecimal count_bigdecimal = null;
				try {
					count_bigdecimal = BigDecimal.valueOf(Double.parseDouble(count));
				}catch (Exception e) {
					String message = "Sorry,your count is illegal!";
					request.getSession().setAttribute("message", message);
					request.setAttribute("message", message);
					request.getRequestDispatcher("register.jsp").forward(request, response);
				}
				if (count_bigdecimal!=null) {
					UserService userServiceImp = (UserService) ServiceFactory.getServiceFactory()
							.getServiceImp(UserService.class);
					User user = userServiceImp.register(name, password, count_bigdecimal);
					if (user!=null) {
						request.getSession().setAttribute("currentUser", user);
						/*Map<String, Object> userInfo = (Map<String, Object>) new HashMap<String, Object>();
						userInfo.put("id", user.getId());
						userInfo.put("name", user.getName());
						userInfo.put("password", user.getPassword());
						userInfo.put("count", user.getCount());
						request.getSession().setAttribute("userInfo", userInfo);*/
						List<com.zone.bean.User> personal = new ArrayList<com.zone.bean.User>(1);
						personal.add(user);
						request.getSession().setAttribute("personal", personal);
						String message = "Register success!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("personal.jsp").forward(request, response);
					}else {
						request.setAttribute("message", "Sorry,this name has bean registed!");
						request.getRequestDispatcher("register.jsp").forward(request, response);
					}	
				}else {
					String message = "Sorry,your count is illegal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("register.jsp").forward(request, response);
				}
			}else {
				String message = "Sorry,your message is illegal!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}
		
		
		
	}

}
