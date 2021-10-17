package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;


public class BoardDao {

	public boolean insert(BoardVo vo) {
		Connection conn = null;
		boolean result =false;
		PreparedStatement pstmt = null;

		try {
			
			conn = getConnection();
			
			//3. SQL문 준비
			if(vo.getGroup_no() != 0) { 
				String sql ="insert into board values(null, ?, ?, 5, now(), ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				//4. 바인딩(binding)
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				//pstmt.setInt(, vo.getHit());	//일단 임의로 삽입	
				pstmt.setInt(3, vo.getGroup_no());
				pstmt.setInt(4, vo.getOrder_no());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUser_no());
				 
				}
			else {
				String sql ="insert into board values(null, ?, ?, 5, now(), (select ifnull(max(group_no), 0)+1 from board b), ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				//4. 바인딩(binding)
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				//pstmt.setInt(, vo.getHit());	//일단 임의로 삽입	
				pstmt.setInt(3, vo.getOrder_no());
				pstmt.setInt(4, vo.getDepth());
				pstmt.setLong(5, vo.getUser_no());
 
			}
			
			//5. SQL실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;

		}catch(SQLException e) {
			System.out.println("error_insert:" + e);
		}
		finally {
			//clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(conn != null) {
				conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean order_update(BoardVo vo) {
		Connection conn = null;
		boolean result =false;
		PreparedStatement pstmt = null;
		try {
			
			conn = getConnection();
			//3. SQL문 준비
				
			//vo의 그룹넘버와 오더넘버를 가져와서 그것보다 같거나큰오버넘버들 전부다 +1
			//String sql ="update board set order_no=order_no+1 where group_no = ? and order_no in(select * from(select order_no from board where order_no >= ?) t)";
			String sql =  "update board set order_no = order_no+1 where group_no = ? and order_no >= ?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩(binding)
			pstmt.setInt(1, vo.getGroup_no());
			pstmt.setInt(2, vo.getOrder_no());


			
			//5. SQL실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;

		}catch(SQLException e) {
			System.out.println("error_update:" + e);
		}
		finally {
			//clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(conn != null) {
				conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	public List<BoardVo> findAll() {
		
		List<BoardVo> result = new ArrayList<BoardVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			conn = getConnection();
			
			//3. SQL문 준비
			String sql ="select * from board;";
			pstmt = conn.prepareStatement(sql);
						
			//5. SQL실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regdate = rs.getString(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				
				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegdate(regdate);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				
					
				result.add(vo);
			}

			
		}catch(SQLException e) {
			System.out.println("error_select:" + e);
		}
		finally {
			//clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(conn != null) {
				conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}


	public BoardVo findByNo(Long no) {
		
		BoardVo vo = null;
		
		//List<UserVo> result = new ArrayList<UserVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			conn = getConnection();
			
			//3. SQL문 준비
			String sql ="select title, contents, hit, reg_date, group_no, order_no, depth, user_no from board where no =?";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setLong(1, no);

			
			
			//5. SQL실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				int hit = rs.getInt(3);
				String reg_date = rs.getString(4);
				int group_no = rs.getInt(5);
				int order_no = rs.getInt(6);
				int depth = rs.getInt(7);
				Long user_no = rs.getLong(8);
				
				
				vo = new BoardVo();
				
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegdate(reg_date);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				
			}

			
		}catch(SQLException e) {
			System.out.println("error_select:" + e);
		}
		finally {
			//clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(conn != null) {
				conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
		
	}

	
	public Boolean delete(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn=getConnection();
			
			//3. SQL 준비
			String sql = "";
			pstmt = conn.prepareStatement(sql);
			
			//4. binding
			pstmt.setLong(1, vo.getNo());

			
			//5. SQL 실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		}catch (SQLException e) {
			System.out.println("error_delete:" + e);
		} finally {
			// clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

		
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://127.0.0.1:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
	
	
}
