package com.sist.dao;

/*
 * 제어문(출력), 변수(데이터 모으기), 예외처리(오라클 연결)
 * 오라클 연결 => ArrayList => add(), get(), size()
 *   => 출력 => for-each
*/
import java.sql.*; //Connection,Preparedstatemente 
import java.util.*; //ArrayList 

public class MusicDAO {
   // 연결
   private Connection conn;
   // sql문장 전송
   private PreparedStatement ps;
   // 오라클 주소 첨부
   private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
   // 연결준비
   // 1.드라이버 등록
   public MusicDAO() {
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
   }

   public void getConnection() {
      try {
         conn = DriverManager.getConnection(URL, "hr", "happy");

      } catch (Exception ex) {
      }
   }

   public void disConnection() {
      try {
         if (ps != null)
            ps.close();
         if (conn != null)
            conn.close();
      } catch (Exception ex) {
      }
   }

   // DAO(JDBC=기본셋팅)
   // 기능 => musictop200
   // 상세보기냐 목록이냐에 따라 리턴형이 달라진다.
   public ArrayList<MusicVO> musicAllData() {
      ArrayList<MusicVO> list = new ArrayList<MusicVO>();
      try {
         getConnection();
         String sql="SELECT mno,title,singer,album,poster "
                   +"FROM genie_music ORDER BY mno ASC";
         ps=conn.prepareStatement(sql);
         ResultSet rs=ps.executeQuery();
         while(rs.next())
         { 
            MusicVO vo=new MusicVO();
            vo.setMno(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setSinger(rs.getString(3));
            vo.setAlbum(rs.getString(4));
            vo.setPoster(rs.getString(5));
            
            list.add(vo);
            
         }
         rs.close();
         
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         disConnection();
      }
      return list;
   }
   
   public ArrayList<MovieVO> movieAllData() {
	      ArrayList<MovieVO> list = new ArrayList<MovieVO>();
	      try {
	         getConnection();
	         String sql="SELECT no,title,director,actor,poster"
	                   +" FROM daum_movie WHERE cateno=1 ORDER BY no ASC";
	         ps=conn.prepareStatement(sql);
	         ResultSet rs=ps.executeQuery();
	         while(rs.next())
	         { 
	            MovieVO vo=new MovieVO();
	            vo.setNo(rs.getInt(1));
	            vo.setTitle(rs.getString(2));
	            vo.setDirector(rs.getString(3));
	            vo.setActor(rs.getString(4));
	            vo.setPoster(rs.getString(5));
	            list.add(vo);
	         }
	         rs.close();
	         
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      } finally {
	         disConnection();
	      }
	      return list;
	   }
}