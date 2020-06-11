package com.zone.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") == null
				&& request.getSession().getAttribute("currentAdmin") == null) {
			//request.getSession().removeAttribute("currentUser");
			//request.getSession().removeAttribute("currentAdmin");
			String message = "You have not login yet!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("personal.jsp").forward(request, response);
		} else {
			//默认注销登录所有账号（用户/管理员）
			request.getSession().removeAttribute("currentUser");
			request.getSession().removeAttribute("currentAdmin");
			request.getSession().removeAttribute("personal");
			request.getSession().removeAttribute("cart");
			String message = "Logout success!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("personal.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
