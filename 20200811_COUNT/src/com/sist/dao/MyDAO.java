package com.sist.dao;
import java.sql.*;
import java.util.*;
public class MyDAO {
	//연결
	private Connection conn;
	//오라클에 SQL 전송
	private PreparedStatement ps;
	//URL
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//드라이버 등록
	public MyDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	//연결(conn hr/happy)
	public void getConnection() {
		try{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex){}
	}
	
	//해제(exit)
	public void disConnection(){
		try{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex){}	
	}
	
	//JDBC(원시소스) >> DBCP >> ORM(MyBatis, Hibernate, JPA)
	//기능
	public String isLogin(String ename,int empno) {
		String result="";
		try {
			getConnection();
			//SQL 문장전송
			String sql="SELECT COUNT(*) FROM emp WHERE ename=?";
			ps=conn.prepareStatement(sql);
			//?에 값채우기
			ps.setString(1, ename);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);//0:아이디를 입력하세요 or 1:비밀번호 확인
			rs.close();
			if(count==0) {
				result="NONAME";
			}else {
				sql="SELECT empno FROM emp WHERE ename=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, ename);
				rs=ps.executeQuery();
				rs.next();//데이터가 있는 위치에 커서를 갖다놔라
				int db_empno=rs.getInt(1);
				rs.close();
				
				if(empno==db_empno) {//로그인
					result=ename;
				}else { //사번이 틀린상태
					result="NOSABUN";
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return result;
	}
}
