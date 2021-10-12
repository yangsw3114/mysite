package com.douzone.mysite.mvc.guest;


import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("list".equals(actionName)) { //방명록 입장
			action = new GuestListAction();
		}else if("joinsuccess".equals(actionName)) { 
			
		}
		else {
			action = new GuestListAction();
		}
		return action;
	}

}
