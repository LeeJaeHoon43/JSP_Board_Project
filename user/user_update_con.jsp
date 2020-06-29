<%@page import="kr.co.jsp.user.model.UserVO"%>
<%@page import="kr.co.jsp.user.model.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	/*
    		1. 폼 데이터를 가져와 처리한다.
    		2. DAO 연동을 통해 updateUser() 메서드를 호출하여 회원정보를 수정한다.
    		3. 회원 정보 수정을 성공했다면 이름에 대한 세션을 다시 제작해야 한다.
    		4. 수정 성공 시 "회원 정보가 수정되었습니다." 출력 후 마이페이지로 이동.
    		5. 수정 실패 시 "회원 정보 수정에 실패했습니다" 출력 후 마이페이지로 이동.
    	
    	*/
    	
    	request.setCharacterEncoding("utf-8");
    
    	UserVO vo = new UserVO(); // 객체 생성.
    
    	String id = (String) session.getAttribute("user_id"); // 세션에 저장되어 있는 아이디.
    	
    	// 객체로 포장.
    	vo.setId(id);
    	vo.setName(request.getParameter("name"));
    	vo.setEmail(request.getParameter("email"));
    	vo.setAddress(request.getParameter("address"));

    	if(UserDAO.getInstance().updateUser(vo)){ // 수정에 성공했으면,
    		// 이름이 변경되므로 세션에 이름도 저장되기 때문에 세션 이름값도 새로 다시 변경해야 한다.
    		session.setAttribute("user_name", request.getParameter("name")); %>
			<script>
    			alert("회원 정보가 수정되었습니다.")
    			location.href="user_mypage.jsp";
			</script>
    	<% }else { %>
    		 <script>
    			alert("회원 정보 수정에 실패했습니다.")
    			location.href="user_mypage.jsp";
			</script>
    	<% } %>
