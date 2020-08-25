<%@page import="com.sist.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!--전송받은 데이터를 받아서
 _ok --> 
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
	//데이터 모아서 DAO로 전송
	BoardVO vo=new BoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//DAO연결 => oracle INSERT
	BoardDAO dao=new BoardDAO();
	dao.boardInsert(vo);
	//이동
	response.sendRedirect("list.jsp");
%>