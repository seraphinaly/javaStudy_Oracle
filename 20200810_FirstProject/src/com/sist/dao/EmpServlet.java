package com.sist.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmpServlet
 */
@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=EUC-KR"); //2����Ʈ�� �ٲ㼭 �о��.
      PrintWriter out= response.getWriter();
      //������ �б�
      EmpDAO dao =new EmpDAO();
      ArrayList<EmpVO> list = dao.musicAllData();
      //s.getOutputStream()
      //���������� �޸𸮿� ��µ� html�� �о� ����.
      out.println("<html>");
      out.println("<body>");
      out.println("<center>");
      out.println("<h1>Database table <EMP> </h1>");
      out.println("<table width=1200 border=1 bordercolor=black>");
      out.println("<tr>");
      out.println("<th>���</th>");
      out.println("<th>�̸�</th>");
      out.println("<th>����</th>");
      out.println("<th>���</th>");
      out.println("<th>�Ի���</th>");
      out.println("<th>�޿�</th>");
      out.println("<th>������</th>");
      out.println("<th>�μ�</th>");
      out.println("</tr>");
      //for
      for(EmpVO vo:list)
      {
         out.println("<tr>");
         out.println("<td>"+vo.getEmpno()+"</td>");
         out.println("<td>"+vo.getEname()+"</td>");
         out.println("<td>"+vo.getJob()+"</td>");
         out.println("<td>"+vo.getMgr()+"</td>");
         out.println("<td>"+vo.getHiredate()+"</td>");
         out.println("<td>"+vo.getSal()+"</td>");
         out.println("<td>"+vo.getComm()+"</td>");
         out.println("<td>"+vo.getDeptno()+"</td>");
         out.println("</tr>");
      }
      out.println("</table>");
      out.println("</center>");
      out.println("</body>");
      out.println("</html>");
   }
}
