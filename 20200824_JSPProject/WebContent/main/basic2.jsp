<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.*" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	//제어문
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	String today=sdf.format(date);
	%>
	오늘 날짜는 <%=today %>입니다.
	<!-- out.println(today)와 같은 출력 -->
</body>
</html>