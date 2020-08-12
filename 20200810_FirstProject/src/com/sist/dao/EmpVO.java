package com.sist.dao;


import java.util.*;
public class EmpVO {
   private int empno;
   private String ename;
   private String job;
   private String mgr;
   private Date hiredate;
   private int sal;
   private int comm;
   private int deptno;
   
   public int getEmpno() {
      return empno;
   }
   public void setEmpno(int empno) {
      this.empno = empno;
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
   public String getMgr() {
      return mgr;
   }
   public void setMgr(String mgr) {
      this.mgr = mgr;
   }
   
   public Date getHiredate() {
      return hiredate;
   }
   public void setHiredate(Date hiredate) {
      this.hiredate = hiredate;
   }
   public int getSal() {
      return sal;
   }
   public void setSal(int sal) {
      this.sal = sal;
   }
   public int getComm() {
      return comm;
   }
   public void setComm(int i) {
      this.comm = i;
   }
   public int getDeptno() {
      return deptno;
   }
   public void setDeptno(int deptno) {
      this.deptno = deptno;
   }
   
}