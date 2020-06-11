package com.zone.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.Product;
import com.zone.bean.User;

public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddOrder() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser")!=null) {
			User user = (User) request.getSession().getAttribute("currentUser");
			if (request.getSession().getAttribute("products")!=null) {
				List<com.zone.bean.Product> products = (List<com.zone.bean.Product>) request.getSession().getAttribute("products");
				if ((request.getParameter("buy")!=null)&&(products.size()>0)) {
					Integer id = new Integer(0);
					try {
						id = Integer.parseInt(request.getParameter("buy"));
					} catch (NumberFormatException e) {
						String message = "Illegal format!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("AddCartServlet").forward(request, response);
					}
					boolean flag = true;
					for (int i = 0; i < products.size(); i++) {
						if (products.get(i).getId()==id) {
							flag = false;
							if (products.get(i).getCount()>0) {
								Integer id2 = products.get(i).getId();
								String name2 = products.get(i).getName();
								BigDecimal price2 = products.get(i).getPrice();
								Integer count2 = new Integer(1);
								String detail2 = new String(products.get(i).getDetail());
								com.zone.bean.Product product2 = new com.zone.bean.Product(id2,name2,price2,count2,detail2);
								List<com.zone.bean.Product> order = new ArrayList<com.zone.bean.Product>();
								order.add(product2);
								request.getSession().setAttribute("order", order);
								request.getRequestDispatcher("order.jsp").forward(request, response);
							} else {
								String message = "Sorry,lack of goods!";
								request.setAttribute("message", message);
								request.getRequestDispatcher("order.jsp").forward(request, response);
							}
							
						}
					}
					if (flag) {
						String message = "This product is not exist!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("details.jsp").forward(request, response);
					}
				} else {
					String message = "Unselected target!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("details.jsp").forward(request, response);
				}
			} else {
				String message = "Product session is empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("product.jsp").forward(request, response);
			}
		} else {
			String message = "Please login !";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		
		
	}

	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户已登录
		if (request.getSession().getAttribute("currentUser")!=null) {
			//获得当前用户
			User user = (User) request.getSession().getAttribute("currentUser");
			//购物车已创建
			if ((request.getSession().getAttribute("cart")!=null)) {
				//获得session中所有商品
				List<com.zone.bean.Product> products = (List<com.zone.bean.Product>) request.getSession().getAttribute("cart");
				//需要购买的商品ID不为空，且session不为空
				if ((request.getParameter("buy")!=null)&&(products.size()>0)) {
					//强转ID类型
					Integer id = new Integer(0);
					try {
						id = Integer.parseInt(request.getParameter("buy"));
					} catch (NumberFormatException e) {
						String message = "Illegal format!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("AddCartServlet").forward(request, response);
					}
					//设置标志位-默认商品不存在-false
					boolean flag = false;
					Product currentProduct = new Product();;
					for (int i = 0; i < products.size(); i++) {
						currentProduct = products.get(i);
						if (currentProduct.getId()==id) {
							//商品存在session中，设置标志位true
							flag = true;
							if (currentProduct.getCount()>0) {
								//剩余商品数大于零
								com.zone.bean.Product productOfOrder = new com.zone.bean.Product(currentProduct.getId(),currentProduct.getName(),currentProduct.getPrice(),currentProduct.getCount(),currentProduct.getDetail());
								List<com.zone.bean.Product> productsOfOrder = new ArrayList<com.zone.bean.Product>();
								productsOfOrder.add(productOfOrder);
								request.getSession().setAttribute("order", productsOfOrder);
								request.getRequestDispatcher("order.jsp").forward(request, response);
							} else {
								//剩余商品数不足
								String message = "Sorry,lack of goods!";
								request.setAttribute("message", message);
								request.getRequestDispatcher("order.jsp").forward(request, response);
							}
							
						}
					}
					//商品不存在
					if (!flag) {
						String message = "This product is not exist!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("details.jsp").forward(request, response);
					}
				} else {
					//未选择商品
					String message = "Unselected target!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("details.jsp").forward(request, response);
				}
			} else {
				//session为空
				String message = "Product session is empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("product.jsp").forward(request, response);
			}
		} else {
			//用户未登录
			String message = "Please login !";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
