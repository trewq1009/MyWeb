<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		
		<h2>아래값들을 2020년05월03일 형식으로 변경해서 출력</h2>
		
		<c:set var="TIME_A" value="2020-05-03"/>
		<c:set var="TIME_B" value="2020/05/03"/>
		<c:set var="TIME_C" value="2020-05-03 21:30:22"/>
		<c:set var="TIME_D" value="<%=new Date() %>"/>
		
		<input type="date"><br/>
		
		<fmt:parseDate var="d01" value="${TIME_A }" pattern="yyyy-MM-dd" />
		<fmt:formatDate var="v01" value="${d01 }" pattern="yyyy년MM월dd일" />
		
		${v01 }<br/>
		
		<fmt:parseDate var="d02" value="${TIME_B }" pattern="yyyy/MM/dd" />
		<fmt:formatDate var="v02" value="${d02 }" pattern="yyyy년MM월dd일"/>
		
		${v02 }<br/>
		
		<fmt:parseDate var="d03" value="${TIME_C }" pattern="yyyy-MM-dd" />
		<fmt:formatDate var="v03" value="${d03 }" pattern="yyyy년MM월dd일"/>
		
		${v03 }<br/>
		
		<fmt:formatDate var="v04" value="${TIME_D }" pattern="yyyy년MM월dd일"/>
		
		${v04 }
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	</body>
</html>



























