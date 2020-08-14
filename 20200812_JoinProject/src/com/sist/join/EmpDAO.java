package com.sist.join;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class EmpDAO{
	// ����Ŭ ����
	private Connection conn;
	// sql ���� ����Ŭ ����
	private PreparedStatement ps;
	// ����Ŭ �ּ�
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

	// ����̹� ��ġ
	public EmpDAO(){
		// ������ => �ɹ������� �ʱ�ȭ , ��Ʈ��ũ ���� ����
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	// ����
	public void getConnection(){
		try{
			conn=DriverManager.getConnection(URL,"hr","happy"); // conn hr/happy
		}catch(Exception ex){
		}
	}

	// ���� ����
	public void disConnection(){
		try{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}catch(Exception ex){
		}
	}

	public ArrayList<EmpVO> empDeptJoinData(){
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try{
			getConnection();
			String sql="SELECT empno,ename,job,hiredate,sal,dname,loc FROM emp,dept "
					 + "WHERE emp.deptno=dept.deptno";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery(); //enter �ļ� ������(executeQuery) ����� ����(resultset)
			while(rs.next()) {
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				vo.getDvo().setDname(rs.getString(6));
				vo.getDvo().setLoc(rs.getString(7));
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

}