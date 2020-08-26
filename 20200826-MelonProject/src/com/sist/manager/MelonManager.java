package com.sist.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.MelonDAO;

public class MelonManager{

	public void musicAllData() {
		MelonDAO dao=new MelonDAO();
		try{
			for(int i=3;i<=8;i++) {
				int k=i;
				Document doc=Jsoup.connect("https://www.melon.com/genre/song_list.htm?gnrCode=GN0"+i*100).get();
				Elements title=doc.select("div.ellipsis.rank01 a");
				Elements singer=doc.select("div.ellipsis.rank02 a:eq(0)");
				Elements album=doc.select("div.ellipsis.rank03 a");
				Elements poster=doc.select("div.wrap a.image_typeAll img");
				int m=1;
				for(int j=0; j<title.size(); j++){
					try{
						MelonVO vo=new MelonVO();
						System.out.println("��ȣ:"+k++);
						System.out.println("cateno:"+i);
						System.out.println("����:"+title.get(j).text());
						System.out.println("����:"+singer.get(m).text());
						System.out.println("�ٹ�:"+album.get(j).text());
						System.out.println("������:"+poster.get(j).attr("src"));
						System.out.println("===========================");
						m+=2;
						//vo�� �� ä���
						vo.setCateno(i);
						vo.setTitle(title.get(j).text());
						vo.setSinger(singer.get(m).text());
						vo.setAlbum(album.get(j).text());
						vo.setPoster(poster.get(j).attr("src"));
						 
						
						
						
						//DAO ����
						dao.melonInsert(vo);
						Thread.sleep(100);
						
					}catch(Exception ex){}
				}
				System.out.println("End...");
			}
		}catch(Exception ex){}
	}
	
	public static void main(String[] args){
		MelonManager m=new MelonManager();
		m.musicAllData();

	}

}
