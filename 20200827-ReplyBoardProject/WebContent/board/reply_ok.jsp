<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
	//값 받아서 오라클에 추가
	try{
		request.setCharacterEncoding("UTF-8");
	}catch(Exception ex){}
	//목록으로 이동
	String name=request.getParameter("name");
	String subject=request.getParameter("subject");
	String content=request.getParameter("content");
	String pwd=request.getParameter("pwd");
	String pno=request.getParameter("pno");//원래 글 번호
	String strPage=request.getParameter("page");
	
	ReplyBoardVO vo=new ReplyBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//DAO 연결
	ReplyBoardDAO dao=new ReplyBoardDAO();
	
	//답변 메소드 호출
	dao.boardReplyInsert(Integer.parseInt(pno),vo);

	//목록으로 이동(response)
	response.sendRedirect("list.jsp?page="+strPage);
%>    

<!-- DB만 연결하고 보여줄 것이 없음, HTML코딩 X -->

