package com.zone.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.PageBean;
import com.zone.service.ProductService;
import com.zone.service.ServiceFactory;

public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if ((request.getSession().getAttribute("products")!=null)&&(request.getSession().getAttribute("pageBean")!=null)) {
			response.sendRedirect("products.jsp");
			return;
		}else {
			ProductService serviceImp = (ProductService) ServiceFactory.getServiceFactory()
					.getServiceImp(ProductService.class);
			PageBean productPageBean = serviceImp.selectProducts(com.zone.bean.Product.class,
					1, 1);
			request.getSession().setAttribute("products", (List<com.zone.bean.Product>)productPageBean.getListBean());
			request.getSession().setAttribute("pageBean", productPageBean);
			//request.setAttribute("products", productPageBean.getListBean());
			//request.setAttribute("pageBean", productPageBean);
			request.getRequestDispatcher("products.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageNumberS = new String();
		pageNumberS = request.getParameter("pageNumber");
		String recordCountS = new String();
		recordCountS = request.getParameter("recordCount");
		Integer pageNumberI = 0;
		Integer recordCountI = 0;
		//判空
		if (((pageNumberS.trim())!=null)&&((recordCountS.trim())!=null)&&(!pageNumberS.equals(""))&&(!recordCountS.equals(""))) {
			try {
				pageNumberI = Integer.parseInt(pageNumberS);
				recordCountI = Integer.parseInt(recordCountS);
			} catch (NumberFormatException e) {
				request.setAttribute("message", "Sorry,input format illigle!");
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}
			//判值
			if ((pageNumberI>0)&&(recordCountI>0)) {
				ProductService serviceImp = (ProductService) ServiceFactory.getServiceFactory()
						.getServiceImp(ProductService.class);
				PageBean productPageBean = serviceImp.selectProducts(com.zone.bean.Product.class,
						recordCountI, pageNumberI);
				request.getSession().setAttribute("products", productPageBean.getListBean());
				request.getSession().setAttribute("pageBean", productPageBean);
				//request.setAttribute("products", productPageBean.getListBean());
				//request.setAttribute("pageBean", productPageBean);
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}else {
				ProductService serviceImp = (ProductService) ServiceFactory.getServiceFactory()
						.getServiceImp(ProductService.class);
				PageBean productPageBean = serviceImp.selectProducts(com.zone.bean.Product.class,
						1, 1);
				request.getSession().setAttribute("products", productPageBean.getListBean());
				request.getSession().setAttribute("pageBean", productPageBean);
				//request.setAttribute("products", productPageBean.getListBean());
				//request.setAttribute("pageBean", productPageBean);
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}
		}else {
			ProductService serviceImp = (ProductService) ServiceFactory.getServiceFactory()
					.getServiceImp(ProductService.class);
			PageBean productPageBean = serviceImp.selectProducts(com.zone.bean.Product.class,
					1, 1);
			request.getSession().setAttribute("products", productPageBean.getListBean());
			request.getSession().setAttribute("pageBean", productPageBean);
			//request.setAttribute("products", productPageBean.getListBean());
			//request.setAttribute("pageBean", productPageBean);
			request.getRequestDispatcher("products.jsp").forward(request, response);
		}
		
	}

}
