package com.sist.join;

import java.util.Date;

public class EmpVO {
	private String ename;
	private int empno;
	private String job;
	private Date hiredate;
	private double sal;
	private double comm;
	private int deptno;
	
	//Arraylist에 한번에 넣기 위해서 VO를 모아줌
	private DeptVO dvo = new DeptVO();

	public DeptVO getDvo() {
		return dvo;
	}

	public void setDvo(DeptVO dvo) {
		this.dvo = dvo;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	
	public int getEmpno(){
		return empno;
	}

	public void setEmpno(int empno){
		this.empno=empno;
	}

	public Date getHiredate(){
		return hiredate;
	}

	public void setHiredate(Date hiredate){
		this.hiredate=hiredate;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public double getComm() {
		return comm;
	}

	public void setComm(double comm) {
		this.comm = comm;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
}