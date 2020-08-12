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
		//HTML ����
		PrintWriter out=response.getWriter();
		//response:����(������ ����), request:����ڰ� ���� �����͸� ���� ��
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>Login</h1>");
		out.println("<form method=post action=LoginServlet>");//ȣ��: form
		//ȣ����: get(default), post
		out.println("<table width=250>");
		
		out.println("<tr>");//�����ٿ� ���
		out.println("<td width=15% align=right>�̸�</td>");//��
		out.println("<td width=85%>");
		out.println("<input type=text name=ename size=17>");//�Է�â
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% align=right>���</td>");//��
		out.println("<td width=85%>");
		out.println("<input type=password name=empno size=17>");//�Է�â
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td align=center colspan=2>");//colspan:td 2���� ������
		out.println("<input type=submit value=�α���>");//��ư, submit:����
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ûó��
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		String ename=request.getParameter("ename");
		String empno=request.getParameter("empno");
		MyDAO dao=new MyDAO();
		String result=dao.isLogin(ename.toUpperCase(), Integer.parseInt(empno));
		 if (result.equals("NONAME")) {
	         out.println("<script>");
	         out.println("alert(\"�̸��� �������� �ʽ��ϴ�.\");"); 
	         out.println("history.back();");//back��ư(���󺹱�)
	         out.println("</script>");
	      } else if (result.equals("NOSABUN")) {
	         out.println("<script>");
	         out.println("alert(\"����� Ʋ���ϴ�.\");"); 
	         out.println("history.back();");
	         out.println("</script>");
	      } else {
	    	 response.sendRedirect("MusicServlet"); //�α��ν� ȭ���� �̵�
	      }
	   }
	}
