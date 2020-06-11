package com.zone.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;


public class Aservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print("hello");
		/*List<Product> list = (List<Product>) request.getSession().getAttribute("cart");
		list.get(0);
		response.getWriter().print(list.get(0));*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断参数id的值是否合法
		if ((request.getParameter("currentProductID")!=null)) {
			String idString = request.getParameter("currentProductID");
			Integer idInteger = 0;
			try {
				idInteger = idInteger.parseInt(idString);
			} catch (Exception e) {
				String message = "Product ID illigal!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}
			//判断session域中的products是否为空
			@SuppressWarnings("unchecked")
			List<com.zone.bean.Product> sessionList = (List<com.zone.bean.Product>)
					request.getSession().getAttribute("products");
			if ((sessionList!=null)&&(sessionList.size()>0)) {
				//循环遍历,判断商品是否存在session中
				com.zone.bean.Product product1 = new com.zone.bean.Product();
				for (int i = 0; i < sessionList.size(); i++) {
					//如果存在，则标出product1得到id以外的信息
					if (sessionList.get(i).getId()==idInteger) {
						product1 = sessionList.get(i);
					}
				}
				//判断购物车是否已创建、是否有数据
				@SuppressWarnings("unchecked")
				List<com.zone.bean.Product> cartList = (List<com.zone.bean.Product>)
						request.getSession().getAttribute("cart");
				com.zone.bean.Product product2 = new com.zone.bean.Product();
				if ((cartList!=null)&&(cartList.size()>0)) {
					//循环遍历，判断是否已添加过此商品
					for (int i = 0; i < cartList.size(); i++) {
						//如果已添加过，标出
						if (cartList.get(i).getId()==idInteger) {
							product2 = cartList.get(i);
							//如果剩余商品数大于零，执行添加购物车操作（数量变化）
							if (product1.getCount()>0) {
								Integer count1 = new Integer(product1.getCount().intValue());//避免内存脏引用
								Integer count2 = new Integer(product2.getCount().intValue());
								product1.setCount(--count1);
								product2.setCount(++count2);
								response.sendRedirect("products.jsp");
							}else {
								String message = "Lack of goods!";
								request.setAttribute("message", message);
								request.getRequestDispatcher("products.jsp").forward(request, response);
							}
						} else {
							//如果剩余商品数大于零，执行添加购物车操作（）
							if (product1.getCount()>0) {
								Integer count1 = new Integer(product1.getCount().intValue());//避免内存脏引用
								Integer count2 = new Integer(0);
								product1.setCount(--count1);
								product2.setCount(++count2);
								response.sendRedirect("products.jsp");
							}else {
								String message = "Lack of goods!";
								request.setAttribute("message", message);
								request.getRequestDispatcher("products.jsp").forward(request, response);
							}
						}
					}
				}else {
					cartList = new ArrayList<com.zone.bean.Product>();
					product2 = product1;
					Integer count1 = new Integer(product1.getCount().intValue());//避免内存脏引用
					Integer count2 = new Integer(0);
					product1.setCount(--count1);
					product2.setCount(++count2);
					request.getSession().setAttribute("cart", cartList);
					response.sendRedirect("products.jsp");
				}
			}else {
				String message = "Session of products is empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}
		}else {
			String message = "Product ID empty!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("products.jsp").forward(request, response);
		}
	}

}
