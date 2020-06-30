package com.jsp.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.jsp.user.model.UserDAO;
import kr.co.jsp.user.model.UserVO;

@WebServlet("/join")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		UserDAO dao = UserDAO.getInstance();
		
		if(dao.confirmId(id)) {
			//자바 클래스에서 HTML이나 JS를 쓰는 방법 - PrintWriter out 객체를 사용.
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			String html;
			html = "<script>\r\n" +
						"alert(\"아이디가 중복되었습니다.!\");\r\n" +
						"location.href=\"/MyWeb/user/user_join.jsp\";\r\n" +
				   "</script>";
			out.println(html);
//			System.out.println("아이디가 중복되었습니다.");
//			return;
		} else {
			UserVO vo = new UserVO(id, pw, name, email, address);
			boolean result = dao.insertUser(vo);
			
			//자바 클래스에서 HTML이나 JS를 쓰는 방법 - PrintWriter out 객체를 사용.
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			
			String htmlCode;
			
			if(result) {
				htmlCode = "<script>\r\n" +
								"alert(\"회원가입을 축하합니다!\");\r\n" +
								"location.href=\"/MyWeb/user/user_login.jsp\";\r\n" +
							"</script>";
				out.println(htmlCode);
			} else {
				htmlCode = "<script>\r\n" +
								"alert(\"회원가입에 실패했습니다!\");\r\n" +
								"history.back();\r\n" +
							"</script>";
				out.println(htmlCode);
			}
			out.flush(); //버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다.
			out.close();	
		}
	}
}