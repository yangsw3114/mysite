package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {

	
	public boolean insert(UserVo vo) {
		Connection conn = null;
		boolean result =false;
		PreparedStatement pstmt = null;
		try {
			
			conn = getConnection();
			//3. SQL문 준비
			String sql =" insert into user values(null, ?, ?, ?, ?,now())";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩(binding)
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
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
	
	public boolean update(UserVo vo) {
		Connection conn = null;
		boolean result =false;
		PreparedStatement pstmt = null;
		try {
			
			conn = getConnection();
			//3. SQL문 준비
			String sql ="update user set name=?, password=?, gender=? where no=?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩(binding)
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			
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
	
	
	
	public UserVo findByEmailAmdPassword
	(String email, String password) throws UserRepositoryException{
		
		UserVo vo = null;
		
		//List<UserVo> result = new ArrayList<UserVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			conn = getConnection();
			
			//3. SQL문 준비
			String sql ="select no, name from user "
					+ "where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			
			//5. SQL실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				vo = new UserVo();
				
				vo.setNo(no);
				vo.setName(name);
					
				//result.add(vo);
			}

			
		}catch(SQLException e) {
			System.out.println("error_select:" + e);
			throw new UserRepositoryException(e.toString());
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
	
	public UserVo findByNo(Long no) throws UserRepositoryException {
		
		UserVo vo = null;
		
		//List<UserVo> result = new ArrayList<UserVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			conn = getConnection();
			
			//3. SQL문 준비
			String sql ="select name, email, gender from user where no=?";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setLong(1, no);

			
			
			//5. SQL실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				
				vo = new UserVo();
				
				vo.setName(name);
				vo.setEmail(email);
				vo.setGender(gender);
				
					
				//result.add(vo);
			}

			
		}catch(SQLException e) {
			System.out.println("error_select:" + e);
			throw new UserRepositoryException(e.toString());
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


	public List<UserVo> findAll() {
		
		List<UserVo> result = new ArrayList<UserVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			conn = getConnection();
			
			//3. SQL문 준비
			String sql ="select * from user;";
			pstmt = conn.prepareStatement(sql);
						
			//5. SQL실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				
				UserVo vo = new UserVo();

				vo.setNo(no);
				vo.setName(name);
				
					
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
