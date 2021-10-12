package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = new UserDao().findByEmailAmdPassword(email, password);
		if(userVo == null) {
			//로그인 실패
			request.setAttribute("result", "fail"); //로그인 실패시 값 전달
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		//인증처리(세션처리)
		HttpSession session = request.getSession();
		//Servlet에서 HttpSession 객체 얻기 위하여 request.getSession() 함수를 이용한다.
		session.setAttribute("authUser", userVo);
		//세션 영역에 속성 값을 설정할 때 setAttribute() 함수를 이용한다.

		
		
		MvcUtil.redirect("/mysite02", request, response);
	}

}
