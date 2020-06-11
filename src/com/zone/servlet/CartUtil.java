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

/**
 * Servlet implementation class CartUtil
 */
public class CartUtil extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public CartUtil() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//返回信息
		String message = "";
		//获得当前用户
		com.zone.bean.User user = (com.zone.bean.User) request.getSession().getAttribute("currentUser");
		//用户不为空
		if (user!=null&&user.getId()!=null) {
			//获得所有商品
			List<com.zone.bean.Product> productsOfSession = (List<com.zone.bean.Product>)request.getSession().getAttribute("products");
			//商品不为空
			if (productsOfSession!=null&&productsOfSession.size()>0) {
				//商品ID
				Integer currentId = new Integer(0);
				//增加或减少
				String currentCommand = new String();
				try {
					//从前端传回的字符串中取出商品ID和增减指令
					String[] strs = request.getParameter("change").split("_");
					currentId = Integer.parseInt(strs[0]);
					currentCommand = strs[1];
				} catch (Exception e) {
					message = "Error,product isn't exist!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("cart.jsp").forward(request, response);
				}
				//获得购物车中商品
				List<com.zone.bean.Product> productsOfCart = (List<com.zone.bean.Product>) request.getSession().getAttribute("cart");
				if (productsOfCart!=null && productsOfCart.size()>0) {
					//标志位-购物车中商品是否存在
					//默认不存在
					boolean flag = false;
					for (int i = 0; i < productsOfCart.size(); i++) {
						//drop指令
						if ((productsOfCart.get(i).getId()==currentId)&&(currentCommand.equals("drop"))) {
							flag = true;
							//比对购物车中商品与session中商品
							for (int j = 0; j < productsOfSession.size(); j++) {
								if (productsOfCart.get(i).getId()==productsOfSession.get(j).getId()) {
									int countRecover = productsOfCart.get(i).getCount()+productsOfSession.get(j).getCount();
									productsOfCart.remove(i);
									productsOfSession.get(j).setCount(countRecover);
									message = "Drop Success !";
									//取消购物车中当前商品后，购物车为空则移除购物车
									if (productsOfCart.size()<1) {
										request.getSession().removeAttribute("cart");
										message += " Empty !";
									}
								}
							}
							//减少
						}else if ((productsOfCart.get(i).getId()==currentId)&&(currentCommand.equals("less"))) {
							flag = true;
							for (int j = 0; j < productsOfSession.size(); j++) {
								if (productsOfCart.get(i).getId()==productsOfSession.get(j).getId()) {
									int countOfCart = productsOfCart.get(i).getCount();
									int countOfSession = productsOfSession.get(j).getCount();
									if (countOfCart>1) {
										productsOfCart.get(i).setCount(countOfCart-1);
										productsOfSession.get(j).setCount(countOfSession+1);
										message = "'"+productsOfSession.get(j).getName()+"'"+" -1 !";
									} else {
										message = "Sorry,"+"'"+productsOfSession.get(j).getName()+"'"+" could not be less!";
									}
								}
							}
							//增加
						}else if ((productsOfCart.get(i).getId()==currentId)&&(currentCommand.equals("more"))) {
							flag = true;
							if (productsOfSession.size()>0) {
								for (int j = 0; j < productsOfSession.size(); j++) {
									if (productsOfCart.get(i).getId()==productsOfSession.get(j).getId()) {
										int countOfCart = productsOfCart.get(i).getCount();
										int countOfSession = productsOfSession.get(j).getCount();
										if (countOfSession>0) {
											productsOfSession.get(j).setCount(countOfSession-1);
											productsOfCart.get(i).setCount(countOfCart+1);
											message = "'"+productsOfSession.get(j).getName()+"'"+" +1 !";
										}else {
											message = "Sorry,"+"'"+productsOfSession.get(j).getName()+"'"+" could not be more!";
										}
									}
								}
							} else {
								flag = false;
								message = "Sorry,products is empty!";
							}
						}
					}
					if (!flag) {
						message = "Sorry,this product isn't exist in your cart!";
					}
				} else {
					message = "Sorry,products in cart is empty!";
				}
			} else {
				message = "Sorry,products in session is empty!";
			}
		} else {
			message = "Sorry,you have not login yet!";
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
