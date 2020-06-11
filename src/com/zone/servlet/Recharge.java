package com.zone.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zone.bean.User;
import com.zone.service.ServiceFactory;
import com.zone.service.UserService;

/**
 * Servlet implementation class Recharge
 */
public class Recharge extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Recharge() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") != null) {
			request.getRequestDispatcher("recharge.jsp").forward(request, response);
		} else {
			String message = "You have not login yet!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (currentUser != null && currentUser.getId() != null) {
			String idStr = request.getParameter("id");
			String pwdStr = request.getParameter("password");
			String moneyStr = request.getParameter("money");
			String cardStr = request.getParameter("card");
			String keyStr = request.getParameter("key");
			Integer id = new Integer(0);
			String pwd = new String();
			Integer card = new Integer(0);
			Integer key = new Integer(0);
			BigDecimal money = new BigDecimal(0);
			//判空
			if ((idStr != null) && (pwdStr != null) && (cardStr != null) && (keyStr != null) && (moneyStr != null)
					&& (!(idStr.trim().equals(""))) && (!(pwdStr.trim().equals(""))) && (!(cardStr.trim().equals("")))
					&& (!(keyStr.trim().equals(""))) && (!(moneyStr.trim().equals("")))) {
				try {
					//尝试强转
					Integer.parseInt(idStr);
					Integer.parseInt(cardStr);
					Integer.parseInt(keyStr);
					BigDecimal.valueOf(Double.parseDouble(moneyStr));
				} catch (Exception e) {
					//统一异常处理，抛回前端
					request.setAttribute("uid", idStr);
					request.setAttribute("upwd", pwdStr);
					request.setAttribute("umoney", moneyStr);
					request.setAttribute("ucard", cardStr);
					request.setAttribute("ukey", keyStr);
					String message = "Some options are illegal!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("recharge.jsp").forward(request, response);
				}
				//如无异常，强转数据类型
				id = Integer.parseInt(idStr);
				pwd = pwdStr;
				card = Integer.parseInt(cardStr);
				key = Integer.parseInt(keyStr);
				money = BigDecimal.valueOf(Double.parseDouble(moneyStr));
				UserService Imp = (UserService) ServiceFactory.getServiceFactory().getServiceImp(UserService.class);
				User user = Imp.login(id, pwd);
				// 如果是本人操作
				if ((user != null) && (user.getId() == currentUser.getId())) {
					//验证卡号密码（待定）
					//数目大于零，小于卡内余额
					if (money.compareTo(BigDecimal.ZERO)>0 && true) {
						//更新session中信息
						currentUser.setCount(currentUser.getCount().add(money));
						//更新数据库中信息
						Imp.reset(currentUser);
						String message = "Recharge success!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("personal.jsp").forward(request, response);
					} else {
						//数目小于等于零，或大于卡内余额
						//返回充值失败信息
						request.setAttribute("uid", idStr);
						request.setAttribute("upwd", pwdStr);
						request.setAttribute("umoney", moneyStr);
						request.setAttribute("ucard", cardStr);
						request.setAttribute("ukey", keyStr);
						String message = "Recharge failed!";
						request.setAttribute("message", message);
						request.getRequestDispatcher("recharge.jsp").forward(request, response);
					}
				} else {
					String message = "Illegal user identity!";
					request.setAttribute("message", message);
					request.getRequestDispatcher("recharge.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("uid", idStr);
				request.setAttribute("upwd", pwdStr);
				request.setAttribute("umoney", moneyStr);
				request.setAttribute("ucard", cardStr);
				request.setAttribute("ukey", keyStr);
				String message = "Some options are empty!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("recharge.jsp").forward(request, response);
			}
		} else {
			String message = "You have not login yet!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
