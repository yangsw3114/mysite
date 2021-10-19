package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping({"","/main"})
	public String index() {
		return "main/index";
		//"/WEB-INF/views/main/index.jsp" -> "main/index"
		//view resolver 설정으로 "main/index"만 작성가능
	}

}
