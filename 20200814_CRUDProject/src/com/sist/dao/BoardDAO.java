package com.sist.dao;
import java.util.*;
import java.sql.*;
public class BoardDAO{
	//연결
	private Connection conn;
	//SQL문장 전송
	private PreparedStatement ps;
	//URL
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//드라이버 등록
	public BoardDAO(){
		try{ 
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	//연결
	public void getConnection() {
		try{
		conn=DriverManager.getConnection(URL,"hr","happy");	
		}catch(Exception ex){}
	}
	//해제
	public void disConnection() {
	try{
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
		}catch(Exception ex){}
	}
	//기능
	//1.목록(게시판) SELECT
	public ArrayList<BoardVO> boardListData(){
		ArrayList<BoardVO> list=new ArrayList<BoardVO>();
		try {
			//연결
			getConnection();
			//SQL문장 전송
			String sql="SELECT no,subject,name,regdate,hit FROM freeboard "
					 + "ORDER BY no DESC"; //단점: 속도 늦음→INDEX
			ps=conn.prepareStatement(sql);
			//SQL 실행 후 결과값 받기
			ResultSet rs=ps.executeQuery();		
			//결과값 ArrayList에 첨부
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return list;
	}
	
	//2.내용보기 SELECT~WHERE ?no=1
	public BoardVO boardDetail(int no) {
		BoardVO vo=new BoardVO();
		try{
			getConnection();
			//조회수증가
			String sql="UPDATE freeboard SET hit=hit+1 "
					 + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);//물음표에 값을 채운다
			ps.executeUpdate();
			//내용 조회할 데이터를 가지고 온다
			sql="SELECT no,name,subject,content,regdate,hit FROM freeboard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,no);

			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return vo;
	}
	
	//3.글쓰기 INSERT
	public void boardInsert(BoardVO vo) {
		try {
			//연결
			getConnection();
			//SQL문장 전송=>실행
			String sql="INSERT INTO freeboard(no,name,subject,content,pwd) "
					 + "VALUES((SELECT NVL(MAX(no)+1,1) FROM freeboard),?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getSubject());
			ps.setString(3,vo.getContent());
			ps.setString(4,vo.getPwd());
			
			ps.executeUpdate(); //자동 COMMIT
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
	}
	//4.글수정 UPDATE
	//5.글삭제 DELETE
	//6.찾기 SELECT
}
