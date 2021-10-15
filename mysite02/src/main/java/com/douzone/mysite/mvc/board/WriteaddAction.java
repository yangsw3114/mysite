package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteaddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		HttpSession session = request.getSession();
		
		String groupno = request.getParameter("groupno");
		String orderno = request.getParameter("orderno");
		String depth = request.getParameter("depth");
		System.out.println(groupno+ "+" +orderno + "+" + depth);
		
		if(session.getAttribute("authUser") == null) {
			System.out.println("로그인부터 해주셈");
			
			MvcUtil.redirect(request.getContextPath()+"/user?a=Loginform", request, response);
			
		}else {
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			if(orderno !="" && depth !="") {
				vo.setGroup_no(Integer.parseInt(groupno));
				vo.setOrder_no(Integer.parseInt(orderno)+1);
				
				//같은 그룹넘버에 속해있는 현재 부여받은 order_no과 같거나 큰 게시판데이터들 전부다 orderNo+1
				new BoardDao().order_update(vo);
				
				vo.setDepth(Integer.parseInt(depth)+1);
				
			}else {
				vo.setOrder_no(1);
				vo.setDepth(0);
			}
			
			UserVo Session_vo = (UserVo) session.getAttribute("authUser");			
			vo.setUser_no(Session_vo.getNo());
			
			
			new BoardDao().insert(vo);
			
			MvcUtil.redirect(request.getContextPath()+"/board", request, response);
			
		}
		


	}

}
