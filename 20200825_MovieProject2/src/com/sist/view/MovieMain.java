package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
import com.sist.manager.*;

@WebServlet("/MovieMain")
public class MovieMain extends HttpServlet{
	private static final long serialVersionUID=1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

		// 브라우저 준비 (HTML, XML)
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1 class=text-center>영화목록</h1>");
		out.println("<div class=row>");
		out.println("<a href=MovieMain?no=1 class=\"btn btn-sm btn-primary\">현재상영영화</a>");
		out.println("<a href=MovieMain?no=2 class=\"btn btn-sm btn-danger\">개봉예정영화</a>");
		out.println("<a href=MovieMain?no=3 class=\"btn btn-sm btn-info\">박스오피스(주간)</a>");
		out.println("<a href=MovieMain?no=4 class=\"btn btn-sm btn-warning\">박스오피스(월간)</a>");
		out.println("<a href=MovieMain?no=5 class=\"btn btn-sm btn-success\">박스오피스(연간)</a>");
		out.println("<a href=NewsMain class=\"btn btn-sm btn-primary\">뉴스</a>");
		out.println("</div>");
		out.println("<div class=row>");
		String no=request.getParameter("no");
		// 페이지가 없으면 1페이지 실행
		if(no==null)
			no="1";
		MovieDAO dao=new MovieDAO();
		ArrayList<MovieVO> list=dao.movieListData(Integer.parseInt(no));
		for(MovieVO vo: list){
			out.println("<div class=\"col-md-3\">");
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=MovieDetail?no="+vo.getNo()+">");
			out.println("<img src="+vo.getPoster()+" alt=\"Lights\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			String str=vo.getTitle();
			if(str.length()>18){
				str=str.substring(0,18)+"...";
			}
			out.println("<p>"+str+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}

	}

}