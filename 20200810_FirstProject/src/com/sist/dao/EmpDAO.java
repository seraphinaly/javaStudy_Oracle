package com.sist.dao;
import java.util.*;
import java.sql.*;

public class EmpDAO {
   //����Ŭ ����
   private Connection conn;
   //sql ���� ����Ŭ ����
   private PreparedStatement ps;
   //����Ŭ �ּ�
   private final String URL ="jdbc:oracle:thin:@localhost:1521:XE";
   //����̹� ��ġ
   public EmpDAO()
   {
      //������ => �ɹ������� �ʱ�ȭ , ��Ʈ��ũ ���� ����
      try
      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      }catch (Exception e) 
      {
         System.out.println(e.getMessage());
      }
   }
   //����
   public void getConnection()
   {
      try {
         conn=DriverManager.getConnection(URL,"hr","happy");
         // conn hr/happy
      }catch (Exception e) {}
   }
   //���� ���� 
   public void disConnection()
   {
      try {
         if(ps!=null)ps.close();
         if(conn!=null)conn.close();
      }catch (Exception e) {}
   }
   //SQL ���� ���� => 200���� ������ ��û 
   public ArrayList<EmpVO> musicAllData()
   {
      ArrayList<EmpVO> list = new ArrayList<EmpVO>();
      try {
         //����Ŭ ����
         getConnection();
         //SQL ���� ����
         String sql="SELECT empno,ename,job,NVL(mgr,0),hiredate,sal,comm,deptno FROM emp";
         //String sql="SELECT mno,poster,title,singer,album "
         //      + "FROM GENIE_MUSIC ORDER BY mno ASC";
         ps=conn.prepareStatement(sql); //���� sql ���常 ������ ����
         
         //����� �ޱ�
         ResultSet rs=ps.executeQuery(); //ps�� �����ϰ� ������� rs�� �־��ش� �̸��̾�.!
         while(rs.next()) //next�� ù�ٺ��� previous�� �Ǹ������ٿ��� ����
         {
            EmpVO vo = new EmpVO();
            
            vo.setEmpno(rs.getInt(1));
            vo.setEname(rs.getString(2));
            vo.setJob(rs.getString(3));
            vo.setMgr(rs.getString(4));
            vo.setHiredate(rs.getDate(5));
            vo.setSal(rs.getInt(6));
            vo.setComm(rs.getInt(7));
            vo.setDeptno(rs.getInt(8));
            
            
//            vo.setState(rs.getString(6));
//            vo.setIdcrement(rs.getInt(7));
//            vo.setKey(rs.getString(8));
            
            //200�� ��Ƽ� ������ ����
            list.add(vo);
         }
         rs.close();
         //ArrayList �� ä���
      }catch (Exception e) {
         System.out.println(e.getMessage());
      }
      finally {
         //���� ����
         disConnection();
      }
      return list;
   }
   /*
   //�󼼺���
   public MusicVO musicDetailData(int mno)
   {
      
      EmpVO vo = new EmpVO();

      try {
         getConnection();
         String sql ="SELECT mno,title,singer,album,poster,key FROM genie_music WHERE mno="+mno;
         ps=conn.prepareStatement(sql);
         ResultSet rs= ps.executeQuery();
         rs.next();
         //���� ä���
         vo.setMno(rs.getInt(1));
         vo.setTitle(rs.getString(2));
         vo.setSinger(rs.getString(3));
         vo.setAlbum(rs.getString(4));
         vo.setPoster(rs.getString(5));
         vo.setKey(rs.getString(6));
         
         rs.close();
         
      }catch (Exception e) {
      
      }finally {
         disConnection();
      }
      return vo;   
      
   }
   */
}