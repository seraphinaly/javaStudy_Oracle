<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*,java.text.*"%>
<%
	//list.jsp?page=1 =>request.getParameter("page"); 1
	//list.jsp => request.getParameter("page"); null
	//list.jsp?page= =>request.getParameter("page"); ""(공백)
	String strPage=request.getParameter("page");
	if(strPage==null){
		strPage="1";
	}
/*
	request vs response
	1)request(내장객체: 미리 생성된 객체)
	  :모든 데이터는 request 안에 있음
	  =HttpServletRequest request
	  =주요기능
	   1.브라우저 정보
	     =사용자 IP
	     =사용자 Port
	   2.사용자 요청정보 (사용자가 보낸 모든 값을 받을 수 있는 기능) 
	     =단일값: getParameter("보낸 변수명")
	            list.jsp?page=1 => getParameter("page");
	            <input type=text name=no>
	            =>getParameter("no")
	     =다중값:checkbox,select
	     		=>getParameterValues()
	     
	     =한글처리: 한글=>인코딩(보낼때)=>디코딩(받을때)
	     		  setCharacterEncoding("UTF-8")
*/
	BoardDAO dao=new BoardDAO();
	int curpage=Integer.parseInt(strPage);
	int totalpage=dao.boardTotalPage();
	ArrayList<BoardVO> list=dao.boardListData(curpage);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="table.css">
</head>
<body>
   <center>
      <h1>게시판</h1>
         <table class="table_content" width=700>
         <tr>
         	<td>
         		<a href="insert.jsp">새글</a>
         	</td>
         </tr>
         </table>	
         
      <table class="table_content" width=700>
         <tr>
            <th width=10%>번호</th>
            <th width=45%>제목</th>
            <th width=15%>글쓴이</th>
            <th width=20%>날짜</th>
            <th width=10%>조회수</th>
         </tr>
         <%
            for (BoardVO vo : list) {
         %>
         <tr class="dataTr">

            <td width=10% class=tdcenter><%=vo.getNo()%></td>
            <td width=45% class=tdleft><a href="detail.jsp?no=<%=vo.getNo()%>"><%=vo.getSubject()%>
 			<%
 	           	Date date=new Date();
            	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            	String today=sdf.format(date);
            	if(today.equals(vo.getRegdate().toString())){
            	%>
            		<font color=red><sup>new</sup></font>
            	<% 
            	}
            %></td>
            <td width=15% class=tdcenter><%=vo.getName()%></td>
            <td width=20% class=tdcenter><%=vo.getRegdate()%></td>
            <td width=10% class=tdcenter><%=vo.getHit()%></td>

         </tr>
         <%
            }
         %>
         </center>
      </table>
      <table class="table_content" width=700>
         <tr>
            <td align="Left"></td>
            <td align="right">
            <a href="list.jsp?page=<%=curpage>1?curpage-1:curpage %>">이전</a>
               <%=curpage %> page / <%=totalpage %> pages
            <a href="list.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>">다음</a>
            
            </td>
         </tr>
      </table>
</body>
</html>