package com.douzone.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	// @ResponseBody
	// @RequestMapping("/checkemail")
	@GetMapping("/checkemail")
	public JsonResult checkemail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		UserVo userVo = userService.getUser(email);
		return JsonResult.success(userVo != null);
	}
}