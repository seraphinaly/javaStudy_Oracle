package com.sist.join;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class EmpDAO{
	// 오라클 연결
	private Connection conn;
	// sql 문장 오라클 전송
	private PreparedStatement ps;
	// 오라클 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

	// 드라이버 설치
	public EmpDAO(){
		// 생성자 => 맴버변수의 초기화 , 네트워크 서버 연결
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	// 연결
	public void getConnection(){
		try{
			conn=DriverManager.getConnection(URL,"hr","happy"); // conn hr/happy
		}catch(Exception ex){
		}
	}

	// 연결 종료
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
			ResultSet rs=ps.executeQuery(); //enter 쳐서 실행한(executeQuery) 결과를 저장(resultset)
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