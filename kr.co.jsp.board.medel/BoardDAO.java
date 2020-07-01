package kr.co.jsp.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO implements IBoardDAO {

	DataSource ds;

	private BoardDAO() {
		try {
			InitialContext ct = new InitialContext();
			ds = (DataSource) ct.lookup("java:comp/env/jdbc/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static BoardDAO dao = new BoardDAO();

	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// DB에 작성글을 등록하는 메서드.
	@Override
	public void regist(String writer, String title, String content) {

		String sql = "INSERT INTO my_board (writer, title, content) VALUES (?,?,?)";

		try(Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 모든 게시글 정보를 가져오는 메서드.(글 목록 리스트)
	@Override
	public List<BoardVO> listBoard() {
		
		List<BoardVO> boardList = new ArrayList<>();
		
		String sql = "SELECT * FROM my_board ORDER BY board_id DESC";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO boardlist = new BoardVO(
						rs.getLong("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("date"),
						rs.getInt("hit")
						);
				boardList.add(boardlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}

	// 글 상세보기 메서드.
	@Override
	public BoardVO contentBoard(long bId) {
		
		BoardVO vo = null;
		
		String sql = "SELECT * FROM my_board WHERE board_id=?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setLong(1, bId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new BoardVO(
						rs.getLong("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content").replace("\r\n", "<br>"),
						rs.getTimestamp("date"),
						rs.getInt("hit")
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	// 게시글 수정 메서드.
	@Override
	public void updateBoard(long bId, String title, String content) {
		
		String sql = "UPDATE my_board SET title=?, content=? WHERE board_id=?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setLong(3, bId);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 게시글 삭제 메서드.
	@Override
	public void deleteBoard(long bId) {
		
		String sql = "DELETE FROM my_board WHERE board_id=?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setLong(1, bId);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 게시글 검색 메서드.
	@Override
	public List<BoardVO> searchBoard(String category, String keyword) {
		
		List<BoardVO> articles = new ArrayList<>();
		
		String sql = "SELECT * FROM my_board WHERE " + category + " LIKE ?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, "%" + keyword + "%");
			System.out.println(pstmt);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO article = new BoardVO(
						rs.getLong("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("date"),
						rs.getInt("hit")
						);
				articles.add(article);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	// 조회수를 올려주는 메서드.
	@Override
	public void upHit(long bId) {
		
		String sql = "UPDATE my_board SET hit=hit+1 WHERE board_id=?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setLong(1, bId);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
