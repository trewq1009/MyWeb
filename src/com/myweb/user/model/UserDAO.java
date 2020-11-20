package com.myweb.user.model;

import java.sql.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myweb.util.JdbcUtil;

public class UserDAO {

	// UserDAO는 불필요하게 여러개 만들어질 필요가 없기 때문에
	// 한개의 객체만 만들어 지도록 Singleton형식으로 설계합니다.
	
	// 1. 나 자신의 객체를 생성해서 1개로 제한합니다.
	private static UserDAO instance = new UserDAO();
	
	// 2. 직접 객체를 생성할 수 없도록 생성자에도 private
	private UserDAO() {
		// 드라이버 로드
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 커넥션 풀을 얻는 작업
			InitialContext ctx = new InitialContext();	// 초기 설정 정보가 저장되는 객체
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
			
			
		} catch (Exception e) {
			System.out.println("드라이버 호출 에러");
		}
	}
	
	// 3. 외부에서 객체생성을 요구할 때 getter메서드를 통해 1번의 객체를 반환
	public static UserDAO getInstance() {
		return instance;
	}
	
	// ---------------------------------------------------------------------------------
	
	// DB연결 변수들을 상수로 선언
//	private String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
//	private String uid = "JSP";
//	private String upw = "JSP";
	
	
	private DataSource ds;
	

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	// 회원가입 메서드
	public int join(UserVO vo) {
		
		int result = 0;		// 결과를 반환할 변수
		String sql = "insert into users(id, pw, name, email, address) values(?, ?, ?, ?, ?)";
		
		try {
			// 1. conn객체 생성
			conn = ds.getConnection();
			
			// 2. pstmt객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getAddress());
			
			// 3. sql문 실행
			result = pstmt.executeUpdate();	// 성공 실패 결과를 1, 0으로 반환
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return result;
	}
	
	
	
	// 중복 검사 메서드
	public int checkId(String id) {
		int result = 0;
		
		String sql = "select * from users where id = ?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// select구문인 경우의 실행 executeQuery()
			rs = pstmt.executeQuery();
		
			
			if(rs.next()) {		// rs.next() 존재한다는 것은 중복.
				result = 1;
			} else {		// 존재하지 않는다면 중복이 아님
				result = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public int checkPw(UserVO vo, String pw) {
		String id = vo.getId();
		String sql = "select * from users where id=? and pw=?";
		int result = 0;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 1;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	public UserVO login(String id, String pw) {
		String sql = "select * from users where id=? and pw=?";
		UserVO vo = null;
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// rs에 담긴 결과를 뽑을 떄는, rs.getString(컬럼명), rs.getInt(컬럼명), rs.getTimestamp(컬럼명)
				vo = new UserVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setAddress(rs.getString("address"));
				
			} else {
				vo = null;
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return vo;
	}
	
	
	
	public int Update(UserVO vo) {
		
		String sql = "update users set pw=?, name=?, email=?, address=? where id=?";
		int result = 0;
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getAddress());
			pstmt.setString(5, vo.getId());
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	
	public int delete(String id) {
		
		String sql = "delete users where id=?";
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	
	
	
	
}
























