package com.zone.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("search")!=null) {
			if (!(request.getParameter("search").trim().equals(""))) {
				String search = request.getParameter("search").trim();
				/*正则表达式*/
				String message = "Sorry,this function is being developed~";
				request.setAttribute("message", message);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				String message = "Empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}else {
			String message = "Empty!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("search")!=null) {
			if (!(request.getParameter("search").trim().equals(""))) {
				String search = request.getParameter("search").trim();
				/*正则表达式*/
				String message = "Sorry,this function is being developed~";
				request.setAttribute("message", message);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				String message = "Empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}else {
			String message = "Empty!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}
