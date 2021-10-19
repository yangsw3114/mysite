package com.douzone.mysite.mvc.board;


import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("write".equals(actionName)) {
			action = new WriteAction();
		}else if("writeadd".equals(actionName)) { 
			action = new WriteaddAction();
		}else if("view".equals(actionName)) { 
			action = new ViewAction();
		}else if("modify".equals(actionName)) { 
			action = new ModifyAction();
		}else if("modifyclick".equals(actionName)) { 
			action = new modifyclickAction();
		}else {
			action = new ListAction();
		}
		return action;
	}

}
