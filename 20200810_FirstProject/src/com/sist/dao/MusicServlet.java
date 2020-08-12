package com.sist.dao;

//servlet: 가벼운 서버 프로그램=>브라우저 실행
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
		// html을 브라우저에 보낼 때, charset: 디코딩/인코딩, 한글 깨지지 않게 2byte씩 읽음
		PrintWriter out = response.getWriter();
		// 데이터 읽기
		MusicDAO dao = new MusicDAO();
		ArrayList<MusicVO> list = dao.musicAllData();
		// out=s.getOutputStream()
		// 브라우저에서 메모리에 출력된 HTML을 읽음
		out.println("<html>"); // jsp에서는 out.println 생략
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>지니뮤직 Top200</h1>");
		out.println("<table width=1200 border=1 bordercolor=black>");
		out.println("<tr>");
		out.println("<th>순위</th>");
		out.println("<th></th>");
		out.println("<th>곡명</th>");
		out.println("<th>가수</th>");
		out.println("<th>앨범</th>");
		out.println("</tr>");
		for (MusicVO vo : list) {
			out.println("<tr>");
			out.println("<td>" + vo.getMno() + "</td>");
			out.println("<td><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td><a href=MusicDetail?mno="+vo.getMno()+">"+vo.getTitle() + "</td>");
			//MusicDetail로 mno값을 넘긴다: url 주소 뒤에 출력
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
