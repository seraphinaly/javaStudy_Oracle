<!-- 상세보기 -->
<%@ page info="상세보기(2020.08.28)" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
	/* JSP:사용자가 요창한 내용을 브라우저에 출력
	    1)사용자가 보내준 데이터 받기 
		2)데이터베이스 연동(DAO)
		3)출력
		내장객체:request/response/application/session(장바구니 기능)/out
		/exception/pageContext(include)/page(자바의 this)/config(환경설정)
	*/
	String no=request.getParameter("no");
	String strPage=request.getParameter("page");//page는 내장객체명이므로 변수명 X
	
	//DAO 연결=>데이터 읽기=>DAO 상세보기
 	ReplyBoardDAO dao=new ReplyBoardDAO();
  	ReplyBoardVO vo=dao.boardDetail(Integer.parseInt(no),1);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/table.css">
</head>
<body>
   <center>
      <h1>내용보기</h1>
      <table class="table_content" width=700>
         <tr>
         	<td>
         		<a href="insert.jsp">새글</a>
         	</td>
         </tr>
         </table>	
       <table class="table_content" width=700>
         <tr>
            <th width=20%>번호</th>
            <td width=20% align="center"><%=vo.getNo() %></td>
            <th width=20%>작성일</th>
            <td width=20% align="center"><%=vo.getRegdate() %></td>
         </tr>
         <tr>
            <th width=20%>이름</th>
            <td width=20% align="center"><%=vo.getName() %></td>
            <th width=20%>조회수</th>
            <td width=20% align="center"><%=vo.getHit() %></td>
         </tr>
         <tr>
            <th width=20%>제목</th>
            <td colspan="3" align="left"><%=vo.getSubject() %></td>
         </tr>
         <tr>
            <td colspan="4" height="200" valign="top"><pre><%=vo.getContent() %></pre></td>
         </tr>
         <tr>
         <!-- **화면이동(동시에 데이터 전송)
         	 	HTML: <a>=>get
         	 		  <form>() =>get,post*
         	 	JavaScript: location.href="" =>get
         	 	            AJAX=>get,post*
         	 	Java: sendRedirect() =>get(서버에서 화면이동시 사용)
         	 	      forward() =>get
         	  
         	  **request=>화면 이동하면 전에 받은 데이터 손실	       
          -->
            <td colspan="4" align="right">
            <a href="reply.jsp?no=<%=vo.getNo()%>&page=<%=strPage%>">답변</a>
            <a href="update.jsp?no=<%=vo.getNo()%>&page=<%=strPage%>">수정</a>&nbsp;
            <a href="#">삭제</a>&nbsp;
            <a href="list.jsp?page=<%=strPage %>">목록</a></td>
         </tr>
      </table>
   </center>
</body>
</html>