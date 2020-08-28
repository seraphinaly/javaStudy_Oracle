<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
   try{
      request.setCharacterEncoding("UTF-8");
   }catch(Exception e){}
   String name=request.getParameter("name");
   String subject=request.getParameter("subject");
   String content=request.getParameter("content");
   String pwd=request.getParameter("pwd");
   String no=request.getParameter("no");
   String strPage=request.getParameter("page");
   
   ReplyBoardVO vo = new ReplyBoardVO();
   vo.setNo(Integer.parseInt(no));
   vo.setName(name);
   vo.setSubject(subject);
   vo.setContent(content);
   vo.setPwd(pwd);
   
   //DAO 연결
   ReplyBoardDAO dao = new ReplyBoardDAO();
   //답변 메소드 호출
   boolean bCheck=dao.boardUpdate(vo);
   if(bCheck==true)
   {
      response.sendRedirect("detail.jsp?no="+no+"&page="+strPage);
   }
   else
   {
      %>
      <script>
      alert("비밀번호가 틀립니다.");
      history.back();
      </script>
      <%
   }
   
%>



