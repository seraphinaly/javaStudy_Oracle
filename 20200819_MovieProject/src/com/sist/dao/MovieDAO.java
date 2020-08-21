package com.sist.dao;

import java.sql.*;
import java.util.*;

import com.sist.manager.MovieVO;
import com.sist.manager.NewsVO;
import com.sist.recipe.RecipeVO;

public class MovieDAO{
	// ����
	private Connection conn;
	// SQL���� ����
	private PreparedStatement ps;
	// URL
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// ����̹� ���
	public MovieDAO(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	// ����
	public void getConnection(){
		try{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex){
		}
	}
	// ����
	public void disConnection(){
		try{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex){}
	}

	// ���
	// 1.����=>INSERT,UPDATE,DELETE=>�����X(void)
	public void movieInsert(MovieVO vo){
		try{
			getConnection();
			String sql="INSERT INTO daum_movie "
					+"VALUES((SELECT NVL(MAX(no)+1,1) FROM daum_movie),?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			// ?�� ��ä���
			ps.setInt(1,vo.getCateno());
			ps.setString(2,vo.getTitle());
			ps.setString(3,vo.getPoster());
			ps.setString(4,vo.getRegdate());
			ps.setString(5,vo.getGenre());
			ps.setString(6,vo.getGrade());
			ps.setString(7,vo.getActor());
			ps.setString(8,vo.getScore());
			ps.setString(9,vo.getDirector());
			ps.setString(10,vo.getStory());
			ps.setString(11,vo.getKey());
			// ���� ���
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
	}

	public void newsInsert(NewsVO vo){
		try{
			getConnection();
			String sql="INSERT INTO daum_news VALUES(?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			// ?�� ��ä���
			ps.setString(1,vo.getTitle());
			ps.setString(2,vo.getPoster());
			ps.setString(3,vo.getLink());
			ps.setString(4,vo.getContent());
			ps.setString(5,vo.getAuthor());
			// ���� ���
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
	}

	public ArrayList<MovieVO> movieListData(int cno){
		ArrayList<MovieVO> list=new ArrayList<MovieVO>();
		try{
			getConnection();
			String sql="select poster,title,no from daum_movie where cateno=? ORDER BY no ASC";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,cno);

			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				MovieVO vo=new MovieVO();
				vo.setPoster(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setNo(rs.getInt(3));

				list.add(vo);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return list;
	}

	public ArrayList<NewsVO> newsListData(){
		ArrayList<NewsVO> list=new ArrayList<NewsVO>();
		try{
			getConnection();
			String sql="select poster,title,link,content,author from daum_news";
			ps=conn.prepareStatement(sql);

			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				NewsVO vo=new NewsVO();
				vo.setPoster(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setLink(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setAuthor(rs.getString(5));
				list.add(vo);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return list;
	}

	// ��ȭ �󼼺��� VO(��ȭ �� ���� ���� ��� ����)
	public MovieVO movieDetailData(int no){
		MovieVO vo=new MovieVO();
		try{
			// ����
			getConnection();
			// SQL ����
			String sql="SELECT * FROM daum_movie WHERE no=?";
			ps=conn.prepareStatement(sql);
			// ?��ä���
			ps.setInt(1,no);
			// ����� �޾Ƽ� ����
			ResultSet rs=ps.executeQuery();
			rs.next();// Ŀ���̵�(������ ��µ� ��ġ)
			vo.setNo(rs.getInt(1));
			vo.setCateno(rs.getInt(2));
			vo.setTitle(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setRegdate(rs.getString(5));
			vo.setGenre(rs.getString(6));
			vo.setGrade(rs.getString(7));
			vo.setActor(rs.getString(8));
			vo.setScore(rs.getNString(9));
			vo.setDirector(rs.getString(10));
			vo.setStory(rs.getString(11));
			vo.setKey(rs.getString(12));
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return vo;
	}

	// ��� ����=>INSERT, UPDATE, DELETE
	public ArrayList<ReplyVO> movieReplyData(int mno){
		ArrayList<ReplyVO> list=new ArrayList<ReplyVO>();
		try{
			getConnection();
			String sql="SELECT no,mno,id,msg,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') "
					+"FROM daum_reply WHERE mno=? ORDER BY no DESC"; // �ֽż�
			ps=conn.prepareStatement(sql);
			ps.setInt(1,mno);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ReplyVO vo=new ReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setMno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setMsg(rs.getString(4));
				vo.setDbday(rs.getString(5));
				list.add(vo);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return list;
	}

	public void movieReplyInsert(ReplyVO vo){
		try{
			getConnection();
			String sql="INSERT INTO daum_reply VALUES("+"(SELECT NVL(MAX(no)+1,1) FROM daum_reply),?,?,?,SYSDATE)";
			ps=conn.prepareStatement(sql);
			// ? ä���
			ps.setInt(1,vo.getMno());
			ps.setString(2,vo.getId());
			ps.setString(3,vo.getMsg());
			// ����
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
	}
	
	   //������ ����
	   public void recipeInsert(RecipeVO vo){
	      try {
	         getConnection();
	         String sql="INSERT INTO recipe VALUES((SELECT NVL(MAX(no)+1,1) FROM recipe),?,?,?,?)";
	         ps=conn.prepareStatement(sql);
	         
	         ps.setString(1,vo.getTitle());
	         ps.setString(2,vo.getPoster());
	         ps.setString(3, vo.getChef());
	         ps.setString(4, vo.getLink());

	         ps.executeUpdate();   
	      }catch (Exception ex) {
	         System.out.println(ex.getMessage());
	      }finally {
	         disConnection();
	      }
	   }
}
