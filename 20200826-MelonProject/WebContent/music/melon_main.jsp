<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.util.*"%>
<% 
	MelonDAO dao=new MelonDAO();
	ArrayList<String> mList=dao.melonGenreAllData();
	
	String mode=request.getParameter("mode");
	String jsp="";
	if(mode==null) jsp="melon_home.jsp";
	else jsp="melon_music.jsp";
%>
<!DOCTYPE html>
<html>
<head>
  <title>멜론뮤직</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">SIST Music</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="melon_main.jsp">Home</a></li>
     <%
     	int i=1;
     	for(String genre:mList){
     %>	
     	<li><a href="melon_main.jsp?mode=<%=i%>"><%=genre %></a></li>
     <% 
     		i++;
     	}
     %>
      
    </ul>
  </div>
</nav>
  
<div class="container">
	<div class="row">
		<!-- include : jsp 파일을 하나로 모음, 받는건 main에서  -->
		<jsp:include page="<%=jsp %>"></jsp:include>
	</div>
</div>
</body>
</html>