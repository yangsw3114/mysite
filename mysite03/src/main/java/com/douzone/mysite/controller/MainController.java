package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	//@Auth
	@RequestMapping({"","/main"})
	public String index() {
		return "main/index";
		//"/WEB-INF/views/main/index.jsp" -> "main/index"
		//view resolver 설정으로 "main/index"만 작성가능
	}
	
	//StringHttpMessageConverter
	@RequestMapping("/msg01")
	public String message01() {
		return "안녕";
	}
	
	//MappingJacksonHttpMessageConverter
	@ResponseBody
	@RequestMapping("/msg02")
	public void message02(/*HttpServletResponse resp*/) throws Exception {
//		resp.setContentType("application/json; charset=UTF-8");
//		resp.getWriter().print("{\"message\":\"Hello World\"}");
		Map<String, Object> map = new HashMap<>();
		map.put("message", "hello world");
	}

}
