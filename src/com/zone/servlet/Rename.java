package com.zone.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.User;
import com.zone.service.ServiceFactory;
import com.zone.service.UserService;

/**
 * Servlet implementation class Rename
 */
public class Rename extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rename() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser")!=null) {
			request.getRequestDispatcher("rename.jsp").forward(request, response);
		}else {
			String message = "You have not login yet!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("personal.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if (name!=null) {
			if (password!=null) {
				if (password.trim().equals("")) {
					String message = "Your password is empty!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("rename.jsp").forward(request, response);
				}else if (name.trim().equals("")) {
					String message = "Your name is empty!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("rename.jsp").forward(request, response);
				}else if (name.contains(" ")) {
					String message = "Your name with space,illegal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("rename.jsp").forward(request, response);
				}else if (password.contains(" ")) {
					String message = "Your password with space,illegal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("rename.jsp").forward(request, response);
				}else {
					User currentUser = (User) request.getSession().getAttribute("currentUser");
					currentUser.setName(request.getParameter("name"));
					currentUser.setPassword(request.getParameter("password"));
					UserService Imp = (UserService) ServiceFactory.getServiceFactory().getServiceImp(UserService.class);
					Imp.reset(currentUser);
					//request.getSession().setAttribute("currentUser", currentUser);
					String message = "Alter success!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("personal.jsp").forward(request, response);
				}
			}else {
				User currentUser = (User) request.getSession().getAttribute("currentUser");
				currentUser.setName(request.getParameter("name"));
				UserService Imp = (UserService) ServiceFactory.getServiceFactory().getServiceImp(UserService.class);
				Imp.reset(currentUser);
				//request.getSession().setAttribute("currentUser", currentUser);
				String message = "Alter success!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("personal.jsp").forward(request, response);
			}
		}else if ((name==null)&&(password!=null)) {
			User currentUser = (User) request.getSession().getAttribute("currentUser");
			currentUser.setPassword(request.getParameter("password"));
			UserService Imp = (UserService) ServiceFactory.getServiceFactory().getServiceImp(UserService.class);
			Imp.reset(currentUser);
			//request.getSession().setAttribute("currentUser", currentUser);
			String message = "Alter success!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("personal.jsp").forward(request, response);
		}else {
			String message = "Your name is illegal!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("rename.jsp").forward(request, response);
		}
		
	}

}
