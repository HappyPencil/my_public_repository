package com.zone.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.Product;
import com.zone.bean.User;


public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CheckOut() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		com.zone.bean.User currentUser = (com.zone.bean.User) request.getSession().getAttribute("currentUser");
		List<com.zone.bean.Product> productsOfCart =  (List<com.zone.bean.Product>)request.getSession().getAttribute("cart");
		List<com.zone.bean.Product> productsOfOrder =  (List<com.zone.bean.Product>)request.getSession().getAttribute("order");
		List<com.zone.bean.Product> productsOfSession = (List<com.zone.bean.Product>)request.getSession().getAttribute("products");
		if (currentUser!=null && productsOfSession!=null && productsOfSession.size()>0) {
			com.zone.bean.Product productOfCart = new com.zone.bean.Product();
			com.zone.bean.Product productOfOrder = new com.zone.bean.Product();
			com.zone.bean.Product productOfSession = new com.zone.bean.Product();
			if (productsOfOrder!=null && productsOfOrder.size()>0) {
				BigDecimal totalOfOne = new BigDecimal("0");
				BigDecimal totalOfAll = new BigDecimal("0");
				BigDecimal totalOfUser = currentUser.getCount();
				//订单中商品是否存在
				boolean ifExist = false;
				for (int k = 0; k < productsOfOrder.size(); k++) {
					productOfOrder = productsOfOrder.get(k);
					for (int m = 0; m < productsOfSession.size(); m++) {
						productOfSession = productsOfSession.get(m);
						if (productOfOrder.getId()==productOfSession.getId()) {
							ifExist = true;
						}else {
							ifExist = false;
						}
					}
				}
				//如果循环之后发现不存在订单中的商品
				if (!ifExist) {
					//返回异常信息
					request.getSession().removeAttribute("order");
					message = "Danger! Illegal order!Please cacel it now!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("order.jsp").forward(request, response);
				}else {
					//如果商品存在，累加价格
					for (int i = 0; i < productsOfOrder.size(); i++) {
						//计算一件商品总价
						productOfOrder = productsOfOrder.get(i);
						totalOfOne = productOfOrder.getPrice().multiply(new BigDecimal(productOfOrder.getCount().toString()));
						//所有商品总价累加
						totalOfAll.add(totalOfOne);
					}
				}
				//用户资金充足（大于或等于，满足其中一者则扣钱）
				if (totalOfUser.compareTo(totalOfAll)==1||totalOfUser.compareTo(totalOfAll)==0) {
					//扣钱
					currentUser.setCount(currentUser.getCount().subtract(totalOfAll));
					//减少购物车中商品数量
					for (int k = 0; k < productsOfOrder.size(); k++) {
						for (int m = 0; m < productsOfCart.size(); m++) {
							for (int n = 0; n < productsOfSession.size(); n++) {
								productOfOrder = productsOfOrder.get(k);
								productOfCart = productsOfCart.get(m);
								productOfSession = productsOfSession.get(n);
								//购物车与session中均有，
								if (productOfOrder.getId()==productOfCart.getId() && productOfOrder.getId()==productOfSession.getId()) {
									//且数量大于等于订单
									if (!(productOfCart.getCount()<productOfOrder.getCount())) {
										//直接减扣购物车
										int countOfCart = productOfCart.getCount()-productOfOrder.getCount();
										productOfCart.setCount(countOfCart);
									}else {
										//且数量小于订单
										//先减扣购物车，再剪扣session
										int lessCountOfSession = productOfOrder.getCount()-productOfCart.getCount();
										productOfCart.setCount(0);
										productOfSession.setCount(lessCountOfSession);
										//持久到数据库待开发
									}
								}
							}
						}
					}
					//操作成功，返回数据
					message = "Check out success!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("order.jsp").forward(request, response);
				} else {
					message = "Please recharge!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("recharge.jsp").forward(request, response);
				}
			} else {
				message = "Empty order!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("products.jsp").forward(request, response);
			}
		} else {
			message = "Sorry,you have not login yet!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}

}
