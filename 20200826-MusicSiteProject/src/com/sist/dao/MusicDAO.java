package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.sist.manager.MusicVO;

public class MusicDAO{
	// 연결
	private Connection conn;
	// SQL문장 전송
	private PreparedStatement ps;
	// 오라클 주소 첨부
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

	// 드라이버 등록
	public MusicDAO(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public void getConnection(){
		try{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex){}
	}

	public void disConnection(){
		try{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}catch(Exception ex){
		}
	}

	// 기능
	public void musicInsert(MusicVO vo){
		try{
			// 연결
			getConnection();
			// SQL문장
			String sql="INSERT INTO music VALUES("+"music_mno_seq.nextval,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			// ?에 값채우기
			ps.setInt(1,vo.getCateno());
			ps.setString(2,vo.getTitle());
			ps.setString(3,vo.getPoster());
			ps.setString(4,vo.getSinger());
			ps.setString(5,vo.getAlbum());
			// INSERT 문장 실행(자동 COMMIT)
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
	}

	// 장르
	public ArrayList<String> musicGenreAllData(){
		ArrayList<String> list=new ArrayList<String>();
		try{
			getConnection();
			String sql="SELECT genre FROM music_genre ORDER BY no";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String genre=rs.getString(1);
				list.add(genre);
			}
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return list;
	}
	/*서브쿼리
	 * SELECT ename,(SELECT~): (컬럼 대신)스칼라 서브쿼리
	 * FROM (SELECT~): 인라인뷰
	 * WHERE sal=(SELECT~): 단일행 서브쿼리, 다중행 서브쿼리
	 * 
	 * JOIN: SELECT만 사용가능
	 * SUBQUERY: DML 전체에서 사용가능
	 */
	//Music 출력
	public ArrayList<MusicVO> musicAllData(int cateno, int page){
		ArrayList<MusicVO> list=new ArrayList<MusicVO>();
		try{
			getConnection();
			//인라인뷰 → 페이징 기법
			String sql="SELECT mno,title,poster,singer,album,RANK() OVER(ORDER BY mno),num "
					+ "FROM(SELECT mno,title,poster,singer,album,rownum AS num "
					+ "FROM(SELECT mno,title,poster,singer,album "
					+ "FROM music WHERE cateno=? ORDER BY mno)) "
					+ "WHERE num BETWEEN ? AND ?";
			int rowSize=30; //rownum:시작번호 1, 중간부터 자를 수 없음
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			
			ps=conn.prepareStatement(sql);
			ps.setInt(1,cateno);
			ps.setInt(2,start);
			ps.setInt(3,end);
			
			//실행
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				MusicVO vo=new MusicVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				vo.setRank(rs.getInt(6));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return list;
	}
	
	public String musicGetGenre(int cateno) {
		String genre="";
		try{
			getConnection();
			String sql="SELECT genre FROM music_genre WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,cateno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			genre=rs.getString(1);
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return genre;
	}
}
