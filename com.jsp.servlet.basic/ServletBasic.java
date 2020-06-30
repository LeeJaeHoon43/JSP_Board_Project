package com.jsp.servlet.basic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.co.jsp.user.model.UserDAO;
import kr.co.jsp.user.model.UserVO;

// 서블릿이란 웹 페이지를 자바 클래스로만 구성하는 기법.
// 즉, jsp파일을 자바 언어로만 구성한다.

// # 서블릿 클래스를 만드는 방법.
// 1. HttpServlet 클래스를 상속.

public class ServletBasic extends HttpServlet{
	
	private UserDAO dao;
	
	// 2. 생성자 선언.
	public ServletBasic() {
		System.out.println("페이지가 생성됨!!");
	}

	// 3. HttpServlet이 제공하는 상속 메서드들을 오버라이당 한다.
	// init(), doGet(), diPost(), destory()...
	
	@Override
	public void init() throws ServletException {
		// 페이지 요청이 들어왔을 때 처음 실행할 로직을 작성하는 곳.
		// init()메서드는 컨테이터(서버)에 의해 서블릿 객체가 생성될 때 최초 1회 자동 호출된다. 객체의 생성자와 비슷한 역할.
		System.out.println("init 메서드 호출!");
		dao = UserDAO.getInstance(); // 주소값 받아오기.
	}
	
	// doGet : http get요청이 발생횄을 때 자동 호출되는 메서드.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet 메서드 호출!");
	}
	
	// doPost : http Post요청이 발생했을 때 자동 호출되는 메서드.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost 메서드 호출!");
		request.setCharacterEncoding("utf-8");
		String account = request.getParameter("account");
		UserVO userData = dao.getUserInfo(account);
		
		// 자바 클래스에서 session을 사용하는 방법.
		HttpSession session = request.getSession();
		session.setAttribute("user", userData);
		
		response.sendRedirect("/MyWeb/servlet_test/info.jsp");
	}
	
	@Override
	public void destroy() {
		// 서블릿 객체가 소멸할 때 호출하는 메서드.(객체 소멸 시 1회 호출)
		// 대부분 객체 반납이나 소멸에 사용.
		System.out.println("destory 메서드 호출!");
		dao = null; // 객체 소멸시키기.
	}
}
