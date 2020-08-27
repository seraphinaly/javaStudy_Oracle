package com.sist.dao;

import java.util.ArrayList;

public class test{

	public static void main(String[] args){
		ReplyBoardDAO dao=new ReplyBoardDAO();
		// 10°³¾¿
		ArrayList<ReplyBoardVO> list=dao.boardListData(2);
		
		for(ReplyBoardVO vo: list){
			System.out.println(vo.getName());
		}

	}

}
