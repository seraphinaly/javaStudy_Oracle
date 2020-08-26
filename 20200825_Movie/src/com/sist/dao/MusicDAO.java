package com.sist.dao;

/*
 * ���(���), ����(������ ������), ����ó��(����Ŭ ����)
 * ����Ŭ ���� => ArrayList => add(), get(), size()
 *   => ��� => for-each
*/
import java.sql.*; //Connection,Preparedstatemente 
import java.util.*; //ArrayList 

public class MusicDAO {
   // ����
   private Connection conn;
   // sql���� ����
   private PreparedStatement ps;
   // ����Ŭ �ּ� ÷��
   private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
   // �����غ�
   // 1.����̹� ���
   public MusicDAO() {
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
   }

   public void getConnection() {
      try {
         conn = DriverManager.getConnection(URL, "hr", "happy");

      } catch (Exception ex) {
      }
   }

   public void disConnection() {
      try {
         if (ps != null)
            ps.close();
         if (conn != null)
            conn.close();
      } catch (Exception ex) {
      }
   }

   // DAO(JDBC=�⺻����)
   // ��� => musictop200
   // �󼼺���� ����̳Ŀ� ���� �������� �޶�����.
   public ArrayList<MusicVO> musicAllData() {
      ArrayList<MusicVO> list = new ArrayList<MusicVO>();
      try {
         getConnection();
         String sql="SELECT mno,title,singer,album,poster "
                   +"FROM genie_music ORDER BY mno ASC";
         ps=conn.prepareStatement(sql);
         ResultSet rs=ps.executeQuery();
         while(rs.next())
         { 
            MusicVO vo=new MusicVO();
            vo.setMno(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setSinger(rs.getString(3));
            vo.setAlbum(rs.getString(4));
            vo.setPoster(rs.getString(5));
            
            list.add(vo);
            
         }
         rs.close();
         
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         disConnection();
      }
      return list;
   }
   
   public ArrayList<MovieVO> movieAllData() {
	      ArrayList<MovieVO> list = new ArrayList<MovieVO>();
	      try {
	         getConnection();
	         String sql="SELECT no,title,director,actor,poster"
	                   +" FROM daum_movie WHERE cateno=1 ORDER BY no ASC";
	         ps=conn.prepareStatement(sql);
	         ResultSet rs=ps.executeQuery();
	         while(rs.next())
	         { 
	            MovieVO vo=new MovieVO();
	            vo.setNo(rs.getInt(1));
	            vo.setTitle(rs.getString(2));
	            vo.setDirector(rs.getString(3));
	            vo.setActor(rs.getString(4));
	            vo.setPoster(rs.getString(5));
	            list.add(vo);
	         }
	         rs.close();
	         
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      } finally {
	         disConnection();
	      }
	      return list;
	   }
   
	// ���
	public ArrayList<MovieVO> movieListData(int page){
		ArrayList<MovieVO> list=new ArrayList<MovieVO>();
		try {
			getConnection();
			int rowSize=10;//1�������� 10�� ���
			//BETWEEN start AND end
			int start=(page*rowSize)-(rowSize-1);
			//rownum�� 1�� �� 1�� ����
			//1������: 1������, 2������: 11������,...
			int end=page*rowSize;

			String sql="SELECT no,poster,title,director,actor "
					 + "FROM (SELECT no,poster,title,director,actor as num "
					 + "FROM (SELECT no,poster,title,director,actor "
					 + "FROM daum_movie ORDER BY no DESC)) "
					 + "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,start);
			ps.setInt(2,end);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				MovieVO vo=new MovieVO();
				vo.setNo(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setDirector(rs.getString(4));
				vo.setActor(rs.getString(5));

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
	public int movieTotalPage() {
		int total=0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM daum_movie";
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
}