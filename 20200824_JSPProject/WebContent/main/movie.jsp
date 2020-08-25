<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%
   MusicDAO dao = new MusicDAO();
//데이터받기
ArrayList<MovieVO> list = dao.movieAllData();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
   $('#keyword').keyup(function(){
      var k=$(this).val();
      $('#user-table > tbody > tr').hide();
      var temp=$('#user-table > tbody > tr > td:nth-child(5n+3):contains("'+k+'")');
            $(temp).parent().show();         
   }); 
});
      
</script>
</head>
<body>
   <center>
      <h1>현재 상영영화</h1>
      <table width=800>
      <tr>
      <td>
      <input type=text id=keyword size=20>
      </td>
      </tr>
      <table border=1 width=800 id="user-table">
         <tr>
            <th>순위</th>
            <th></th>
            <th>제목</th>
            <th>감독</th>
            <th>출연</th>
         </tr>
            <tbody>
         <%
            for (MovieVO vo: list) {
         %>

         <tr>
            <td><%=vo.getNo()%></td>
            <td>
            <img src=<%=vo.getPoster() %> width=30 height=30>
            </td>
            <td><%=vo.getTitle()%></td>
            <td><%=vo.getDirector()%></td>
            <td><%=vo.getActor()%></td>
         </tr>
         <%
            }
         %>
         </tbody>
         </table>
    </center>
</body>
</html>