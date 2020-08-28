package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sound.sampled.LineListener;

public class ReplyBoardDAO{
	// ����
	private Connection conn;
	// SQL���� ����
	private PreparedStatement ps;
	// ����Ŭ �ּ� ÷��
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

	// ����̹� ���
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
		}catch(Exception ex){
		}
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

	// 1.��� ��� => ������ ������
	public ArrayList<ReplyBoardVO> boardListData(int page){
		ArrayList<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
		try{
			getConnection();
			String sql="SELECT no,subject,name,regdate,hit,group_tab,num "
					+"FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
					+"FROM (SELECT no,subject,name,regdate,hit,group_tab "
					+"FROM replyBoard ORDER BY group_id DESC,group_step ASC)) "+"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;

			ps.setInt(1,start);
			ps.setInt(2,end);

			ResultSet rs=ps.executeQuery();
			while(rs.next()){
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
		}finally{
			disConnection();
		}
		return list;
	}

	// 1-1 ��ü����
	public int boardRowCount(){
		int count=0;
		try{
			getConnection();
			String sql="SELECT COUNT(*) FROM replyBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return count;
	}

	// 2.�󼼺���
	// �Ű�����: ����ڰ� ������ ������
	public ReplyBoardVO boardDetail(int no,int type){
		ReplyBoardVO vo=new ReplyBoardVO();
		try{
			getConnection();
			// ��ȸ�� ����
			String sql="";
			if(type==1){
				sql="UPDATE replyBoard SET hit=hit+1 WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1,no);
				ps.executeUpdate();
			}
			// ������ ��ȸ�� �б�
			sql="SELECT no,name,subject,content,regdate,hit FROM replyBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,no);

			// �����
			ResultSet rs=ps.executeQuery();
			// Ŀ����ġ ����=>������ �����ġ�� Ŀ�� ����
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));

			rs.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			disConnection();
		}
		return vo;
	}

	// 3.�� �� ��� => INSERT
	public void boardInsert(ReplyBoardVO vo){
		try{
			getConnection();
			String sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id) "
					+"VALUES(rb_no_seq.nextval,?,?,?,?,"+"(SELECT NVL(MAX(group_id)+1,1) FROM replyBoard))";
			ps=conn.prepareStatement(sql);
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getSubject());
			ps.setString(3,vo.getContent());
			ps.setString(4,vo.getPwd());

			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
	}

	// 4.�亯 => SQL =>���ļ� ó��(��������)
	public void boardReplyInsert(int root,ReplyBoardVO vo){
		try{
			getConnection();

			// 1.�亯 ����� group_id, group_step, group_tab
			String sql="SELECT group_id,group_step,group_tab FROM replyBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,root);
			ResultSet rs=ps.executeQuery();

			rs.next();
			int gi=rs.getInt(1); // gi
			int gs=rs.getInt(2); // gs+1
			int gt=rs.getInt(3); // gt+1
			rs.close();

			// ���� �߻�
			sql="UPDATE replyBoard SET group_step=group_step+1 WHERE group_id=? AND group_step>?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,gi);
			ps.setInt(2,gs);
			ps.executeUpdate();

			// INSERT
			sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id,group_step,group_tab,root) "
					+"VALUES(rb_no_seq.nextval,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getSubject());
			ps.setString(3,vo.getContent());
			ps.setString(4,vo.getPwd());
			ps.setInt(5,gi);
			ps.setInt(6,gs+1);
			ps.setInt(7,gt+1);
			ps.setInt(8,root);
			ps.executeUpdate();

			// depth+1
			sql="UPDATE replyBoard SET depth=depth+1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,root);
			ps.executeUpdate();

		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
	}

	// 5.�����ϱ� => UPDATE
	public boolean boardUpdate(ReplyBoardVO vo){
		boolean bCheck=false;
		try{
			getConnection();
			String sql="SELECT pwd FROM replyBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,vo.getNo());
			ResultSet rs=ps.executeQuery();

			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();

			if(db_pwd.equals(vo.getPwd())){
				bCheck=true;
				sql="UPDATE replyBoard SET name=?,subject=?,content=? WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1,vo.getName());
				ps.setString(2,vo.getSubject());
				ps.setString(3,vo.getContent());
				ps.setInt(5,vo.getNo());
				ps.executeUpdate();
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			disConnection();
		}
		return bCheck;
	}

	// 6.���� => SQL =>���ļ� ó��(��������)
	// 7.ã�� => LIKE, REGEXP_LIKE
	public ArrayList<ReplyBoardVO> boardFindData(String fs,String ss){
		ArrayList<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
		try{
			getConnection();
			String sql="SELECT no,subject,name,regdate,hit FROM replyBoard "
					 + "WHERE "+fs+" LIKE '%"+ss+"%'";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo=new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex){

		}finally{

		}
		return list;
	}
}