package com.zone.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Watch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*response.getWriter().print("<p><center><b>Success!</b><center></p>");
		response.setHeader("refresh", "2;watch.jsp");*/
		//request.getRequestDispatcher("/WEB-INF/watch.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*response.getWriter().print("<p><center><b>Success!</b><center></p>");
		response.setHeader("refresh", "2;watch.jsp");*/
		
	}

}
