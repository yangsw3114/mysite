package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.FileUploadException;
import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo findone() throws FileUploadException{		
		SiteVo vo = sqlSession.selectOne("site.findOne");		
		return vo;		
	}
	
	public boolean update(SiteVo vo) {
		int count = sqlSession.update("site.update", vo);
		return count==1;
	}
}
