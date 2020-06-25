package kr.co.jsp.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAO implements IUserDAO {

	// DataSource : DB에 이용되는 URL, ID, PW, DriverClass를 미리 정의해 놓고 사용하는 객체.
	DataSource ds;
	// 1. 클래스 외부에서 객체를 생성할 수 없도록 생성자에 private제한을 붙인다.
	private UserDAO() { // 싱글톤 디자인 패턴 (객체 생성을 단 하나로 제한).
		try { // 커넥션 풀 세팅.
			InitialContext ct = new InitialContext(); // InitialContext 객체 생성 : 저 객체에 context.xml에 작성한 설정 파일이 저장됨.
			ds = (DataSource) ct.lookup("java:comp/env/jdbc/mysql"); // 이니셜컨텍스트로부터 찾아서 데이터 소스로 형 변환 후 저장.
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// 2. 클래스 내부에서 자신 스스로의 객체를 단 1개만 생성.
	private static UserDAO dao = new UserDAO();
	
	// 3. 외부에서 객체 요구 시 공개된 메서드를 통해 주소값 리턴.
	public static UserDAO getInstance() {
		if(dao == null) {
			dao = new UserDAO();
		}
		return dao;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 중복 ID 여부를 검증하는 메서드.
	@Override
	public boolean confirmId(String id) {
		
		boolean flag = false;
		
		String sql = "SELECT * FROM user WHERE user_id=?";
		
		// try-with-resources문 : 자동 자원 반환.
		// try옆 ()소괄호안에 두 문장 이상을 넣을 경우 ;로 구분한다.
		// try문의 ()소괄호안에 객체를 생성하는 문장을 넣으면, 이 객체는 따로 close()를 호출하지 않아도 try블럭을 벗어나는 순간,
		// 자동적으로 close()가 호출된다.
		// 단, try-with-resources문에 의해 자동적으로 객체의 cloes()가 호출되려면 클래스가 AutoCloseable이라는 인터페이스를 구현한 것만.
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) flag = true; // 아이디가 존재한다.
			else flag = false; // 아이디가 존재하지 않는다.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 회원가입을 처리하는 메서드.
	@Override
	public boolean insertUser(UserVO vo) {
		
		boolean flag = false;
		
		String sql = "INSERT INTO user VALUES(?,?,?,?,?)";
		
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getAddress());
			
			if(pstmt.executeUpdate() == 1) flag = true; 
			else flag = false; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 로그인 유효성을 검증하는 메서드.
	@Override
	public int userCheck(String id, String pw) {
		
		int check = 0;
		// 아이디가 있는지를 조회하여 그 아이디에 값이 맵핑되어 있는 비밀번호를 얻은 후, 
		// 매개값으로 받은 비밀번호가 같은지 대조하여 return값을 조정해 준다.
		// 아이디가 없으면 -1, 비밀번호가 다른 경우는 0, 로그인 성공은 1을 리턴한다.
		
		String sql = "SELECT * FROM user WHERE user_id=?";
		
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) { // 아이디가 존재함.
				String dbPw = rs.getString("user_pw"); // DB에 저장된 비밀번호 얻음.
				if(dbPw.equals(pw)) { // DB에서 얻어온 비밀번호와 입력받은 비밀번호가 같을 경우,
					check = 1;
				} else { // 비밀번호가 다른 경우.
					check = 0;
				}
			} else { // 아이디가 존재하지 않을 경우.
				check = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}

	// 회원의 모든 정보를 얻어오는 메서드.
	@Override
	public UserVO getUserInfo(String id) {
		
		UserVO vo = null;
		
		String sql = "SELECT * FROM user WHERE user_id=?";
		
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new UserVO( // 객체 생성
						rs.getString("user_id"),
						rs.getString("user_pw"),
						rs.getString("user_name"),
						rs.getString("user_email"),
						rs.getString("user_address")
						); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	// 비밀번호를 변경하는 메서드.
	@Override
	public boolean changePassword(String id, String pw) {
		
		boolean flag = false;
		
		String sql = "UPDATE user SET user_pw=? WHERE user_id=?";
		
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, pw);
			pstmt.setString(2, id);
			
			if(pstmt.executeUpdate() == 1) flag = true;
			else flag = false;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	// 회원정보를 수정하는 메서드.
	@Override
	public boolean updateUser(UserVO vo) {
		
		boolean flag = false;
		
		String sql = "UPDATE user SET user_name=?, user_email=?, user_address=? WHERE user_id=?";
		
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getAddress());
			pstmt.setString(4, vo.getId());
			
			if(pstmt.executeUpdate() == 1) flag = true;
			else flag = false;

		} catch (Exception e) {
			e.printStackTrace();
		}	
		return flag;
	}

	// 회원 정보를 삭제하는 메서드.
	@Override
	public boolean deleteUser(String id) {
		
		boolean flag = false;
		
		String sql = "DELETE FROM user WHERE user_id=?";
		
		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			
			if(pstmt.executeUpdate() == 1) flag = true;
			else flag = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
