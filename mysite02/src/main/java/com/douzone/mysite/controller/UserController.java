package com.douzone.mysite.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.mvc.user.UserActionFactory;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;
import com.douzone.web.util.MvcUtil;


public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");

		ActionFactory af = new UserActionFactory();
		Action action = af.getAction(actionName);
		action.execute(request, response);
	}
//		if("joinform".equals(action)) {
//		MvcUtil.forward("/WEB-INF/views/user/joinform.jsp", request, response);
//	}else if("Loginform".equals(action)) {
//		
//	}else if("updateform".equals(action)) {
//		
//	}else if("Logout".equals(action)) {
//		
//	}else if("join".equals(action)) {
//		
//	}else {
//		MvcUtil.redirect(request.getContextPath(), request ,response);
//	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
