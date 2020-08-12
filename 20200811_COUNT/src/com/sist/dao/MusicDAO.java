package com.sist.dao;
import java.util.*;
import java.sql.*;
public class MusicDAO {
	
	//오라클 연결
	private Connection conn;
	//SQL문장 오라클로 전송
	private PreparedStatement ps;
	//오라클 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//드라이버 설치
	public MusicDAO(){
		//생성자=> 멤버변수 초기화, 네트워크 서버 연결
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	//연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	//SQL 문장전송 =>200개 데이터 요청
	public ArrayList<MusicVO> musicAllData(){
		ArrayList<MusicVO> list=new ArrayList<MusicVO>();
		try {
			//오라클 연결
			getConnection();
			//SQL 문장전송
			String sql="SELECT mno,poster,title,singer,album FROM genie_music ORDER BY mno ASC";
			ps=conn.prepareStatement(sql); //실행:executeQuery()
			//결과값 받기
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MusicVO vo=new MusicVO();
				vo.setMno(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				//200개 데이터 모아서 브라우저로 전송
				list.add(vo);
			}
			rs.close();
			//ArrayList에 값 채우기
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage()); //에러종류 확인
		}finally {
			disConnection(); //서버 종료
		}
		return list;
	}
	//상세보기
	public MusicVO musicDetailData(int mno) { 
		MusicVO vo=new MusicVO();
		try{
			getConnection();
			String sql="SELECT mno,title,singer,album,poster,key FROM genie_music "
					+ "WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			//값을 채운다
			vo.setMno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setSinger(rs.getString(3));
			vo.setAlbum(rs.getString(4));
			vo.setPoster(rs.getString(5));
			vo.setKey(rs.getString(6));
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return vo;
	}
}
