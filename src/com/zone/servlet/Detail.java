package com.zone.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.Product;


public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Detail() {
        super();
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if ((request.getParameter("currentDetail")!=null)&&(request.getSession().getAttribute("products")!=null)) {
			String detailStr = (String)request.getParameter("currentDetail");
			Integer detailInt = Integer.parseInt(detailStr);
			List<com.zone.bean.Product> listSession = (List<com.zone.bean.Product>) request.getSession().getAttribute("products");
			boolean flag = true;
			for (int i = 0; i < listSession.size(); i++) {
				if (listSession.get(i).getId()==detailInt) {
					flag = false;
					com.zone.bean.Product currentDetail = listSession.get(i);
					List<com.zone.bean.Product> currentDetailList = new ArrayList<com.zone.bean.Product>();
					currentDetailList.add(currentDetail);
					request.getSession().setAttribute("currentDetailList", currentDetailList);
					request.getRequestDispatcher("details.jsp").forward(request, response);
				}
			}
			if (flag) {
				String mesage = "Empty!";
				request.setAttribute("message", mesage);
				request.getRequestDispatcher("ProductServlet").forward(request, response);
			}
		}else if ((request.getAttribute("currentDetail")!=null)&&(request.getSession().getAttribute("products")!=null)) {
			String detailStr = (String)request.getParameter("currentDetail");
			Integer detailInt = Integer.parseInt(detailStr);
			List<com.zone.bean.Product> listSession = (List<com.zone.bean.Product>) request.getSession().getAttribute("products");
			boolean flag = true;
			for (int i = 0; i < listSession.size(); i++) {
				if (listSession.get(i).getId()==detailInt) {
					flag = false;
					com.zone.bean.Product currentDetail = listSession.get(i);
					List<com.zone.bean.Product> currentDetailList = new ArrayList<com.zone.bean.Product>();
					currentDetailList.add(currentDetail);
					request.getSession().setAttribute("currentDetailList", currentDetailList);
					request.getRequestDispatcher("details.jsp").forward(request, response);
				}
			}
			if (flag) {
				String mesage = "Empty!";
				request.setAttribute("message", mesage);
				request.getRequestDispatcher("ProductServlet").forward(request, response);
			}
		} else {
			String mesage = "Empty!";
			request.setAttribute("message", mesage);
			request.getRequestDispatcher("ProductServlet").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
