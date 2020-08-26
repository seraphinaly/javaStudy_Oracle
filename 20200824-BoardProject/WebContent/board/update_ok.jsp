<%@page import="com.sist.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- insert_ok: 전송받은 데이터를 받아서 오라클 연결만 시켜주는 파일 --> 
<%
	try{
		//데이터를 받는 경우(한글 포함) => 한글변환(디코딩)
		request.setCharacterEncoding("UTF-8");		
	}catch(Exception ex){}
	//input type의 name값
	String name=request.getParameter("name");
	String subject=request.getParameter("subject");
	String content=request.getParameter("content");
	String pwd=request.getParameter("pwd");
	String no=request.getParameter("no");
	
	//데이터 모아서 DAO로 전송
	BoardVO vo=new BoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	vo.setNo(Integer.parseInt(no));
	
	//DAO연결 => oracle INSERT
	BoardDAO dao=new BoardDAO();
	boolean bCheck=dao.boardUpdate(vo);
	
	if(bCheck==true){
		response.sendRedirect("detail.jsp?no="+no);
	}else{
%>		
		<script>
		alert("비밀번호가 틀립니다.");
		history.back();
		</script>
<%		
	}
%>