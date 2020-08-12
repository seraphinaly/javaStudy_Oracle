package com.sist.dao;

import java.io.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		//HTML 제작
		PrintWriter out=response.getWriter();
		//response:응답(브라우저 전송), request:사용자가 보낸 데이터를 받을 때
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>Login</h1>");
		out.println("<form method=post action=LoginServlet>");//호출: form
		//호출방식: get(default), post
		out.println("<table width=250>");
		
		out.println("<tr>");//다음줄에 출력
		out.println("<td width=15% align=right>이름</td>");//라벨
		out.println("<td width=85%>");
		out.println("<input type=text name=ename size=17>");//입력창
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% align=right>사번</td>");//라벨
		out.println("<td width=85%>");
		out.println("<input type=password name=empno size=17>");//입력창
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td align=center colspan=2>");//colspan:td 2개를 합쳐줌
		out.println("<input type=submit value=로그인>");//버튼, submit:전송
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청처리
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		String ename=request.getParameter("ename");
		String empno=request.getParameter("empno");
		MyDAO dao=new MyDAO();
		String result=dao.isLogin(ename.toUpperCase(), Integer.parseInt(empno));
		 if (result.equals("NONAME")) {
	         out.println("<script>");
	         out.println("alert(\"이름이 존재하지 않습니다.\");"); 
	         out.println("history.back();");//back버튼(원상복귀)
	         out.println("</script>");
	      } else if (result.equals("NOSABUN")) {
	         out.println("<script>");
	         out.println("alert(\"사번이 틀립니다.\");"); 
	         out.println("history.back();");
	         out.println("</script>");
	      } else {
	    	 response.sendRedirect("MusicServlet"); //로그인시 화면을 이동
	      }
	   }
	}
