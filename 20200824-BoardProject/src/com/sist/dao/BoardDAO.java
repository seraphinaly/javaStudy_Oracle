package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO{
	// ����
	private Connection conn;
	// SQL���� ����
	private PreparedStatement ps;
	// ����Ŭ �ּ� ÷��
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

	// ����̹� ���
	public BoardDAO(){
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

	// ���
	public ArrayList<BoardVO> boardListData(int page){
		ArrayList<BoardVO> list=new ArrayList<BoardVO>();
		try {
			getConnection();
			int rowSize=10;//1�������� 10�� ���
			//BETWEEN start AND end
			int start=(page*rowSize)-(rowSize-1);
			//rownum�� 1�� �� 1�� ����
			//1������: 1������, 2������: 11������,...
			int end=page*rowSize;

			String sql="SELECT no,subject,name,regdate,hit,num "
					 + "FROM (SELECT no,subject,name,regdate,hit,rownum as num "
					 + "FROM (SELECT no,subject,name,regdate,hit "
					 + "FROM jsp_board ORDER BY no DESC)) "
					 + "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,start);
			ps.setInt(2,end);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));

				list.add(vo);
			}
			rs.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			disConnection();
		}
		return list;
	}

	//��������
	public int boardTotalPage() {
		int total=0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM jsp_board";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return total;
	}
	
	//���뺸��
	public BoardVO boardDetailData(int no) {
		BoardVO vo=new BoardVO();
		try {
			getConnection();
			//��ȸ�� ����
			String sql="UPDATE jsp_board SET hit=hit+1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,no);
			ps.executeUpdate();
			//������ �б�
			sql="SELECT no,name,subject,content,regdate,hit FROM jsp_board "
			  + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,no);
			//����� �ޱ�
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return vo;
	}
	
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO jsp_board VALUES("
					+ "jb_no_seq.nextval,?,?,?,?,SYSDATE,0)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getSubject());
			ps.setString(3,vo.getContent());
			ps.setString(4,vo.getPwd());
			//����
			ps.executeUpdate();
			}catch(Exception ex) {
			System.out.println(ex.getMessage());
			}finally {
				disConnection();
			}
	}
}

