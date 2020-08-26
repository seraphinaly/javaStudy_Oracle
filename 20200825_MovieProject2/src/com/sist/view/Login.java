package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.MovieDAO;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//화면 출력
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();

	      out.println("<html>");
	      out.println("<head>");
	      out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");

	      out.println("<style type=text/css>");
	      out.println(".row{");
	      out.println("margin:0px auto;");
	      out.println("width:300px");
	      out.println("}");
	      out.println("</style>");
	      out.println("</head>");
	      
	      out.println("<body>");
	      out.println("<div class=container>"); // row를 잡아주는 큰 틀 = Container
	      out.println("<h1 class=text-center>로그인</h1>");
	      out.println("<div class=row>");
	      
	      //Login이 갖고있는 doPost를 호출해라: 전송버튼=submit
	      out.println("<form method=post action=Login>");
	      out.println("<table class=table>");
	      
	      out.println("<tr>");
	      out.println("<td width=20% class=text-right>ID</td>");
	      out.println("<td width=75%>");
	      out.println("<input type=text name=id size=15 class=input-sm>");
	      out.println("</td>");
	      out.println("</tr>");
	      
	      out.println("<tr>");
	      out.println("<td width=20% class=text-right>Password</td>");
	      out.println("<td width=75%>");
	      out.println("<input type=password name=pwd size=15 class=input-sm>");
	      out.println("</td>");
	      out.println("</tr>");
	      
	      out.println("<tr>");
	      out.println("<td colspan=2 class=text-center>");
	      out.println("<input type=submit value=로그인 class=\"btn btn-sm btn-success\">");
	      out.println("<input type=submit value=취소 class=\"btn btn-sm btn-danger\">");
	      out.println("</td>");
	      out.println("</tr>");
	      out.println("</table>");
	      
	      out.println("</table>");
	      out.println("</form>");
	      out.println("</div>");
	      out.println("</div>");
	      out.println("</body>");
	      out.println("</html>");

	}
	/*
	 * id : 단독제어, 소환시 #
	   class : 동시제어, 소환시 .
	   name: Java에서 값 받을때 사용
	 */
	//버튼 눌렀을때 처리(로그인)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		//System.out은 관리자가 보는 용도, 사용자가 보려면 out.println
//		System.out.println("ID:"+id);
//		System.out.println("PW:"+pwd);
		MovieDAO dao=new MovieDAO();
		String result=dao.isLogin(id,pwd);
		
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		
		if(result.equals("NOID")) {
			out.println("<script>");
			out.println("alert(\"ID가 존재하지 않습니다.\");"); //함수:<>없음
			out.println("history.back();");
			out.println("</script>");
		}else if(result.equals("NOPWD")) {
			out.println("<script>");
			out.println("alert(\"비밀번호가 틀립니다.\");");
			out.println("history.back();");
			out.println("</script>");
		}else { //로그인
			//id를 서버에 저장=> 프로그램 종료시까지 저장
			HttpSession session=request.getSession();//세션 형성(브라우저 종료시 해제)
			session.setAttribute("id",id);//서버에 저장
			//기본=> 30분(시간 설정 가능)
			response.sendRedirect("MovieMain"); //이동
		}
	}
	//JSP에서는 service의 형태로 doGet/doPost 기능 제공
}
