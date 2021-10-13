package com.douzone.mysite.mvc.guest;


import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("list".equals(actionName)) { //방명록 입장
			action = new GuestListAction();
		}else if("insert".equals(actionName)) { 
			action = new GuestinsertAction();
		}else if("deleteform".equals(actionName)) { 
			action = new deleteformAction();
		}
		else if("delete".equals(actionName)) { 
			action = new deleteAction();
		}
		else 
		{
			action = new GuestListAction();
		}
		return action;
	}

}
