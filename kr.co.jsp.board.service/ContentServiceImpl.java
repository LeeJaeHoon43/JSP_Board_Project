package kr.co.jsp.board.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.jsp.board.model.BoardDAO;
import kr.co.jsp.board.model.BoardVO;

public class ContentServiceImpl implements IBoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		/*
		- contentBoard 완성시켜서 호출 후에 얻어온 객체를 request객체에 담아서 forwarding한다.
		- (board/board_content.jsp)
		*/
		long bId = Long.parseLong(request.getParameter("boardId"));
		
		/*
		 1. 조회 수 중복 방지를 위한 쿠키를 하나 생성.
		 2. 쿠키 이름과 값은 해당 글 번호를 넣어놓는다.
		 3. 다시 해당 글의 상세보기 요청이 왔을 때, 해당 글 번호로 이루어진 쿠키가 존재하는지를 확인하여, 존재한다면 쿠키의 값을 얻어오면 된다.
		 4. 쿠키의 값을 얻었을 때, (c.getValue()) 해당 값이 요청이 들어온 글 번호와 일치한다면 조회수를 올리지 않고, 쿠키의 값이 요청이 들어온 글 번호와 일치하지 않는다면 upHit()를 호출. 
		*/
		
		// 조회 수 중복 방지를 위한 쿠키를 하나 생성.
		String boardId = request.getParameter("boardId");
		Cookie hitCoo = new Cookie(boardId , boardId);
		hitCoo.setMaxAge(15);
		response.addCookie(hitCoo);
		
		// 중복 방지용 로직 쿠키 얻기.
		Cookie[] cookies = request.getCookies(); // 클라이언트에 저장된 쿠키는 쿠키 배열 타입이다.
		String bNum = "";
    	for(Cookie c : cookies) {
    		if(c.getName().equals(boardId)) {
    			bNum = c.getValue();
    		}
    	}
    	
    	// 조회수 올려주는 메서드 호출.
    	if(!bNum.equals(boardId)) {
    		BoardDAO.getInstance().upHit(bId);
    	}
    	
		BoardVO vo = BoardDAO.getInstance().contentBoard(bId);
		request.setAttribute("content_board", vo);
	}
}
