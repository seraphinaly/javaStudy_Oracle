package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReplyBoardDAO{
	// 연결
		private Connection conn;
		// SQL문장 전송
		private PreparedStatement ps;
		// 오라클 주소 첨부
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

		// 드라이버 등록
		public ReplyBoardDAO(){
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
				if(ps!=null) ps.close();
				if(conn!=null) conn.close();
			}catch(Exception ex){}
		}
		
		//1.목록 출력 => 페이지 나누기
		public ArrayList<ReplyBoardVO> boardListData(int page){
			ArrayList<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
			try{
				getConnection();
				String sql="SELECT no,subject,name,regdate,hit,group_tab,num "
						 + "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
						 + "FROM (SELECT no,subject,name,regdate,hit,group_tab "
						 + "FROM replyBoard ORDER BY group_id DESC,group_step ASC)) "
						 + "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=10;
				int start=(rowSize*page)-(rowSize-1);
				int end=rowSize*page;
				
				
				ps.setInt(1,start);
				ps.setInt(2,end);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					ReplyBoardVO vo=new ReplyBoardVO();
					vo.setNo(rs.getInt(1));
					vo.setSubject(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setRegdate(rs.getDate(4));
					vo.setHit(rs.getInt(5));
					vo.setGroup_tab(rs.getInt(6));
					list.add(vo);
				}
				rs.close();
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}finally {
				disConnection();
			}
			return list;
		}

		//1-1 전체개수
		public int boardRowCount() {
			int count=0;
			try {
				getConnection();
				String sql="SELECT COUNT(*) FROM replyBoard";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				count=rs.getInt(1);
				rs.close();
			}catch(Exception ex) {
				System.out.println(ex.getMessage());
			}finally {
				disConnection();
			}
			return count;
		}
		//2.상세보기
		//3.새 글 등록 => INSERT
		public void boardInsert(ReplyBoardVO vo){
			try{
				getConnection();
				String sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id) " 
				         + "VALUES(rb_no_seq.nextval,?,?,?,?,"
						 +"(SELECT NVL(MAX(group_id)+1,1) FROM replyBoard))"; 
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setString(4, vo.getPwd());
				
				ps.executeUpdate();
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			finally{
				disConnection();
			}
		}
		//4.답변 => SQL =>합쳐서 처리(서브쿼리)
		//5.수정 => UPDATE
		//6.삭제 => SQL =>합쳐서 처리(서브쿼리)
		//7.찾기 => LIKE, REGEXP_LIKE
}
