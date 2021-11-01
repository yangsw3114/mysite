package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MainController {

	@RequestMapping({"","/main"})
	public String index() {
//		SiteVo site = servletContext.getAttribute("site");
//		if(site == null) {
//			SiteVo vo = siteService.getSite();
//			servletContext.setAttribute("site", vo);
//		}
		return "main/index";

	}
	
	//StringHttpMessageConverter
	@RequestMapping("/msg01")
	public String message01() {
		return "안녕";
	}
	
	//MappingJacksonHttpMessageConverter
	@ResponseBody
	@RequestMapping("/msg02")
	public Object message02(/*HttpServletResponse resp*/) throws Exception {
		//resp.setContentType("application/json; charset=UTF-8");
		//resp.getWriter().print("{\"message\":\"Hello World\"}");
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Hello World");
		
		return map;
	}

}
