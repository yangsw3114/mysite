package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String no = request.getParameter("pageno");

		if(no == null) {
			no ="1";
		}
		
		List<BoardVo> list = new BoardDao().findAll(Integer.parseInt(no));	
		List<BoardVo> list2 = new BoardDao().findAll();
		
		List<UserVo> userlist = new UserDao().findAll();
		
		request.setAttribute("boardlist", list);
		request.setAttribute("all_board", list2);
		request.setAttribute("userlist", userlist);
		request.setAttribute("pageno", no);
		
		MvcUtil.forward("board/list", request, response);
	}

}
