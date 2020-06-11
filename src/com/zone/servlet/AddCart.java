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

public class AddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddCart() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") != null) {

			// 判断参数id的值是否合法
			if ((request.getParameter("currentProductID") != null)) {
				String idString = request.getParameter("currentProductID");
				Integer idInteger = 0;
				try {
					idInteger = idInteger.parseInt(idString);
				} catch (Exception e) {
					String message = "Product ID illigal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("products.jsp").forward(request, response);
				}
				// 判断session域中的products是否为空
				@SuppressWarnings("unchecked")
				List<com.zone.bean.Product> sessionList = (List<com.zone.bean.Product>) request.getSession()
						.getAttribute("products");
				if ((sessionList != null) && (sessionList.size() > 0)) {
					// 循环遍历,根据ID判断商品是否存在session中
					com.zone.bean.Product productOfSession = new com.zone.bean.Product();
					for (int i = 0; i < sessionList.size(); i++) {
						// 如果存在，则标出product1得到id以外的信息
						if (sessionList.get(i).getId() == idInteger) {
							productOfSession = sessionList.get(i);
						}
					}
					@SuppressWarnings("unchecked")
					List<com.zone.bean.Product> productsOfCart = (List<com.zone.bean.Product>) request.getSession()
							.getAttribute("cart");
					com.zone.bean.Product productOfCart = new com.zone.bean.Product();
					// 判断购物车是否已创建、是否有数据
					if ((productsOfCart != null) && (productsOfCart.size() > 0)) {
						// 循环遍历，设置标志位判断是否已添加过此商品
						// 默认未添加
						boolean flag = true;
						for (int i = 0; i < productsOfCart.size(); i++) {
							// 如果已添加过，标出
							if (productsOfCart.get(i).getId() == productOfSession.getId()) {
								// 设置标志位false（已经添加过）
								flag = false;
								productOfCart = productsOfCart.get(i);
								// 如果剩余商品数大于零，执行添加购物车操作（数量变化）
								if (productOfSession.getCount() > 0) {
									int countSession = productOfSession.getCount().intValue();
									int countCart = productOfCart.getCount().intValue();
									productOfSession.setCount(--countSession);
									productOfCart.setCount(++countCart);
									response.sendRedirect("products.jsp");
								} else {
									// 剩余商品数等于零
									String message = "Lack of goods!";
									request.setAttribute("message", message);
									request.getRequestDispatcher("products.jsp").forward(request, response);
								}
								break;
							}
						}
						// 如果购物车中没有添加过该商品
						if (flag) {
							// 如果剩余商品数大于零
							if (productOfSession.getCount() > 0) {
								// 添加到购物车
								int countOfSession = productOfSession.getCount().intValue();
								productOfCart = new com.zone.bean.Product(productOfSession.getId(),
										productOfSession.getName(), productOfSession.getPrice(), 1,
										productOfSession.getDetail());
								productOfSession.setCount(--countOfSession);
								productOfCart.setCount(1);
								productsOfCart.add(productOfCart);
								response.sendRedirect("products.jsp");
							} else {
								// 剩余商品数等于零
								String message = "Lack of goods!";
								request.setAttribute("message", message);
								request.getRequestDispatcher("products.jsp").forward(request, response);
							}
						}
					} else {
						// 购物车未创建
						// 剩余商品数量大于零
						if (productOfSession.getCount() > 0) {
							productsOfCart = new ArrayList<com.zone.bean.Product>();
							int countOfSession = productOfSession.getCount().intValue();
							productOfCart = new com.zone.bean.Product(productOfSession.getId(),
									productOfSession.getName(), productOfSession.getPrice(), 1,
									productOfSession.getDetail());
							productOfSession.setCount(--countOfSession);
							productsOfCart.add(productOfCart);
							request.getSession().setAttribute("cart", productsOfCart);
							response.sendRedirect("products.jsp");
						} else {
							// 剩余商品数量等于零
							String message = "Lack of goods!";
							request.setAttribute("message", message);
							request.getRequestDispatcher("products.jsp").forward(request, response);
						}
					}
				} else {
					// session中没有任何商品数据
					String message = "Session of products is empty!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("products.jsp").forward(request, response);
				}
			} else {
				// 前端传回的商品ID不合法
				String message = "Product ID empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}
		} else {
			// 用户未登录
			// request.setAttribute("currentDetail",
			// request.getParameter("currentProductID"));
			String message = "Sorry,you haven't login yet!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	/**
	 * POST
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
