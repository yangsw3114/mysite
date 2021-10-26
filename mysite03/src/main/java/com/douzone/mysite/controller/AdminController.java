package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.security.Auth;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	ServletContext servletContext;
	
//	@RequestMapping("/main/update")
//	public String main(SiteVo siteVo) {
//		siteService.update(siteVo);
//	}
	
	@Auth(role="ADMIN")
	@RequestMapping("")
	public String main() {
		return "admin/main";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
