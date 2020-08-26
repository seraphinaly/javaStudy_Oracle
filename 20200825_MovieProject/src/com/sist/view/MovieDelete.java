package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.MovieDAO;

@WebServlet("/MovieDelete")
public class MovieDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//MovieDelete?no="+vo.getNo()
	//<input type=text name=id>
	//Get(노출:공개) <a>, response.sendRedirect()
	//Post(노출X:데이터가 너무 길거나 보안 필요 시) <form>
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no=request.getParameter("no");
		String mno=request.getParameter("mno");
		//DAO 전송
		MovieDAO dao=new MovieDAO();
		//삭제 처리
		dao.replyDelete(Integer.parseInt(no));
		//MovieDetail로 이동
		response.sendRedirect("MovieDetail?no="+mno);
	}

}
