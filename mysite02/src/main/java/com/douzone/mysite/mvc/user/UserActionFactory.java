package com.douzone.mysite.mvc.user;

import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("joinform".equals(actionName)) { //회원가입창
			action = new JoinFormAction();
		}else if("join".equals(actionName)) { // 회원정보 데이터값 삽입
			action = new JoinAction();
		}else if("joinsuccess".equals(actionName)) { //회원정보 삽입후 성공
			action = new JoinSuccessAction();
		}else if("Loginform".equals(actionName)) {  //로그인 화면
			action = new LoginFormAction();
		}else if("login".equals(actionName)) { //로그인 정보 일치하여 성공시
			action = new LoginAction();
		}else if("Logout".equals(actionName)) { //로그아웃
			action = new LogoutAction();
		}else if("updateform".equals(actionName)) { // 회원정보수정
			//action = new updateformAction();
		}
		
		else {
			action = new MainAction();
		}
		return action;
	}

}
