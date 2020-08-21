package com.sist.view;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.MovieDAO;
import com.sist.dao.ReplyVO;
import com.sist.manager.MovieVO;

//상세보기
@WebServlet("/MovieDetail")
//톰캣(JSP엔진):JSP=>Servlet
//            텍스트용 웹서버(아파치)
public class MovieDetail extends HttpServlet{
	private static final long serialVersionUID=1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		// html로 출력
		response.setContentType("text/html;charset=EUC-KR");
		// 브라우저 전송
		PrintWriter out=response.getWriter();
		// html전송
		// request에서 ?값을 받음 MovieMain=>MovieDetail?no=1
		String no=request.getParameter("no");

		out.println("<html>");
		out.println("<head>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
//		out.println("<style type=text/css>");
//		out.println(".row{");
//		out.println("margin:0px auto;");
//		out.println("width:800px");
//		out.println("}");
//		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1 class=text-center>영화 상세</h1>");
		out.println("<div class=row>");
		out.println("<div class=col-sm-8>");
		MovieDAO dao=new MovieDAO();
		MovieVO vo=dao.movieDetailData(Integer.parseInt(no));

		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<iframe src=http://youtube.com/embed/"+vo.getKey()+" width=700 height=350></iframe>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");

		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td width=30% class=text-center rowspan=7>");
		out.println("<img src="+vo.getPoster()+" width=210 height=300>");
		out.println("</td>");
		out.println("<td width=70%>"+vo.getTitle()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=70%>감독: "+vo.getDirector()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=70%>출연: "+vo.getActor()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=70%>장르: "+vo.getGenre()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=70%>등급: "+vo.getGrade()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=70%>기타: "+vo.getRegdate()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=70%>"+vo.getScore()+"</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td colspan=2 height=200 valign=top>"+vo.getStory()+"</td>");
		out.println("</tr>");

		out.println("</table>");

//		out.println("전송받은 영화번호:"+no);
		out.println("</div>");

		out.println("<div class=col-sm-4>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<form method=post action=MovieDetail>");
		out.println("<input type=hidden name=mno value="+no+">");
		out.println("<input type=text size=30 class=input-sm name=msg>");
		out.println("<input type=submit class=\"btn btn-sm btn-primary\" value=댓글쓰기>");
		out.println("</form>");
		out.println("<td>");
		out.println("</tr>");
		out.println("</table>");
		ArrayList<ReplyVO> rList=dao.movieReplyData(Integer.parseInt(no));
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		for(ReplyVO rvo: rList){
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td class=text-left>");
			out.println(rvo.getId()+"("+rvo.getDbday()+")");
			out.println("</td>");
			out.println("<td class=text-right>");
			out.println("<a href=# class=\"btn btn-xs btn-primary\">수정</a>");
			out.println("<a href=# class=\"btn btn-xs btn-primary\">삭제</a>");

			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan=2 heignt=100 valign=top class=text-left>");
			out.println("<pre>"+rvo.getMsg()+"</pre>");
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
		}

		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");

		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try {
			request.setCharacterEncoding("EUC-KR");//한글 깨짐방지
		}catch(Exception ex) {}
		String mno=request.getParameter("mno");
		String msg=request.getParameter("msg");
		
		ReplyVO vo=new ReplyVO();
		vo.setMno(Integer.parseInt(mno));
		vo.setMsg(msg);
		vo.setId("hoohoo_"); //Session
		//DAO 전송
		MovieDAO dao=new MovieDAO();
		dao.movieReplyInsert(vo);
		//화면이동
		response.sendRedirect("MovieDetail?no="+mno);
	}

}
