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
	//Get(����:����) <a>, response.sendRedirect()
	//Post(����X:�����Ͱ� �ʹ� ��ų� ���� �ʿ� ��) <form>
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no=request.getParameter("no");
		String mno=request.getParameter("mno");
		//DAO ����
		MovieDAO dao=new MovieDAO();
		//���� ó��
		dao.replyDelete(Integer.parseInt(no));
		//MovieDetail�� �̵�
		response.sendRedirect("MovieDetail?no="+mno);
	}

}
