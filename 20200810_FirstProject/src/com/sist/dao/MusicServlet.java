package com.sist.dao;

//servlet: ������ ���� ���α׷�=>������ ����
import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MusicServlet")
public class MusicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		// html�� �������� ���� ��, charset: ���ڵ�/���ڵ�, �ѱ� ������ �ʰ� 2byte�� ����
		PrintWriter out = response.getWriter();
		// ������ �б�
		MusicDAO dao = new MusicDAO();
		ArrayList<MusicVO> list = dao.musicAllData();
		// out=s.getOutputStream()
		// ���������� �޸𸮿� ��µ� HTML�� ����
		out.println("<html>"); // jsp������ out.println ����
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>���Ϲ��� Top200</h1>");
		out.println("<table width=1200 border=1 bordercolor=black>");
		out.println("<tr>");
		out.println("<th>����</th>");
		out.println("<th></th>");
		out.println("<th>���</th>");
		out.println("<th>����</th>");
		out.println("<th>�ٹ�</th>");
		out.println("</tr>");
		for (MusicVO vo : list) {
			out.println("<tr>");
			out.println("<td>" + vo.getMno() + "</td>");
			out.println("<td><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td><a href=MusicDetail?mno="+vo.getMno()+">"+vo.getTitle() + "</td>");
			//MusicDetail�� mno���� �ѱ��: url �ּ� �ڿ� ���
			out.println("<td>" + vo.getSinger() + "</td>");
			out.println("<td>" + vo.getAlbum() + "</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}
