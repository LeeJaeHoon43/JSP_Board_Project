<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%-- 
		jstl choose태그와 EL을 활용하여 90점이 넘으면,
		"당신의 학점은 A입니다."를 출력,
		80점대는 B, 70점대는 C, 60점대는 D, 나머지는 F를 출력.
	 --%>
	<c:set var="point" value="${param.point}" />
	<c:choose>
		<c:when test="${point >= 90 }">
			<b>당신의 학점은 A입니다.</b>
		</c:when>
		<c:when test="${point >= 80 }">
			<b>당신의 학점은 B입니다.</b>
		</c:when>
		<c:when test="${point >= 70 }">
			<b>당신의 학점은 C입니다.</b>
		</c:when>
		<c:when test="${point >= 60 }">
			<b>당신의 학점은 D입니다.</b>
		</c:when>
		<c:otherwise>
			<b>당신의 학점은 F입니다.</b>
		</c:otherwise>
	</c:choose>
</body>
</html>