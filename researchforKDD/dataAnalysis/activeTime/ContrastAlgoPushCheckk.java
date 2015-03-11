package activeTime;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class ContrastAlgoPushCheckk {
	
	public void generatHisto(String inputpath,String dic1,String dic2,String outpath) throws Exception{
		
		Connection conn,conn2,conntest;
		Statement stmt,stmt2,stmt3;
		ResultSet res,res2;
		   
		   
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		   
		   
		   //������MySQL������
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ikanfou",
		                                      "root", "4150484");
		
		conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ikanfou",
                "root", "4150484");
                
		 //ִ��SQL���
		stmt = conn2.createStatement();
       
		stmt2 = conn2.createStatement();
		
		stmt3 = conn2.createStatement();
		
		
		
		int[][] histo=new int [24][12];
		int[][] histopush=new int [24][12];
		long activities=0,pushhit=0,opentimes=0;
				//iterate all files
		
		
		HashMap<String, String> dica=new HashMap<String, String>();
		HashMap<String, String> dicb=new HashMap<String, String>();
	
		File f=new File(dic1);
		BufferedReader r=new BufferedReader(new FileReader(f));
		String line="";
		while((line=r.readLine())!=null){
			String[] t=line.split(",");
			for(int i=0;i<t.length;i++){
				dica.put(t[i], "yes");
			}
			
		}
		
		f=new File(dic2);
		r=new BufferedReader(new FileReader(f));
		line="";
		while((line=r.readLine())!=null){
			String[] t=line.split(",");
			for(int i=0;i<t.length;i++){
				dicb.put(t[i], "yes");
			}
			
		}
		
		
		f=new File(inputpath);
		File[] files=f.listFiles();
		System.out.println(inputpath);
		HashMap<String, String> mapnews=new HashMap<String, String>();
		for(int i=0;i<files.length;i++){
			
			try{
				HashMap<String, PushItem> map=new HashMap<String, PushItem>();
				
				int[][] histoforone=new int [24][12];
				BufferedReader br=new BufferedReader(new FileReader(files[i]));
				String[] tnames=files[i].getAbsolutePath().split("\\\\");
				String wuid=tnames[tnames.length-1].replaceAll(".csv","");
				 line="";
				
				while((line=br.readLine())!=null){
					
					String[] t=line.split(",");
					if(t==null||t.length<3){
						continue;
					}
					long current=Long.parseLong(t[1]);
					long old=0,interval=0;
					int wrepost_count=0;
					
					String[] tmp=t[0].split(" ");
					int hour=Integer.parseInt(tmp[1].split(":")[0]);
					int minute=Integer.parseInt(tmp[1].split(":")[1]);
					
					if(t[3].equals("push")){
						String index="";
						if(t[4].equals("show")){
							continue;
						}
						String news="";
						if(t[4].equals("news")||t[4].equals("daily_report")){
							
							String sql="select * from app_news where id='"+t[6]+"'";
							//System.out.println(sql);
							res=stmt.executeQuery(sql);
							
							if(res.next()){
								String title=res.getString("title");
								sql="select unix_timestamp(wtime) as wtime2,wrepost_count from meiju_weibo where wid='"+res.getString("wid")+"'";
								res=stmt.executeQuery(sql);
								if(res.next()){
									old=Long.parseLong(res.getString("wtime2"))*1000;
									wrepost_count=Integer.parseInt(res.getString("wrepost_count"));
									
								}
								
								news+=t[6]+","+title.replaceAll(",", "，");
								if(mapnews.get(t[6])==null){
									mapnews.put(t[6], news);
								}
							}else{
								continue;
							}
							index+="news"+t[6];
							t[4]="news";
						}
						
						if(t[4].equals("video")){
							if(t.length<7){
								System.out.println("video parameter miss");
								continue;
							}
							String sql="select * from episodes_urls where rageeid='"+t[6]+"'";
//							System.out.println(sql);
							res=stmt.executeQuery(sql);
							if(res.next()){
								old=Long.parseLong(res.getString("timestamp"));
							}else{
								continue;
							}
							index+="video"+t[6];
							
//							System.out.println(index);
						}
						if(old>current){
							old=current;
						}
						
						if(old==0){
							interval=5;
						}else{
							interval=(current-old)/3600000;
						}
						
						PushItem p=new PushItem(old,current,interval,t[4]);
						p.pushhour=hour;
						p.pushminute=hour*60+minute;
						p.wrepost_count=wrepost_count;
						p.newsid=news;
						map.put(index, p);
						continue;
						
						
					}
					String query="";
					if(t[3].equals("NewsDetailV2")){
						query="news"+t[5].replaceAll("id = ", "").trim();
					}
					if(t[3].equals("episodedetail")){
						query="video"+t[5].replaceAll("eid = ", "").trim();
					}
					//System.out.println("query"+query);
					PushItem p=map.get(query);
					if(p==null){
						continue;
					}
					
					p.setSuccess(1);
					
					
				}
				int call=0,chit=0;
				for (PushItem p : map.values()) {  
					 call++;
					 if(p.success==1){
						 chit++;
					 }
					 
				    save(p.interval+","+p.success+","+p.wrepost_count+"\r\n","d:\\\\all"+outpath+".csv");
				    save(p.interval+","+p.success+","+p.wrepost_count+"\r\n","d:\\\\all_"+p.type+""+outpath+".csv");
//				    if(p.wrepost_count>100){
				    save(p.interval+","+p.pushminute+","+p.pushhour+","+p.success+","+p.wrepost_count+","+p.newsid+"\r\n","d:\\\\all_"+p.type+"_popular"+outpath+".csv");
//				    if(p.type.equals("news")){
//					    save(p.newsid+"\r\n","d:\\\\all_"+p.type+"_dict_"+outpath+".csv");
//				    }
//				    }
				} 
				int dicflag=1;
				if(dica.get(wuid)!=null){
					save(wuid+","+call+","+chit+"\r\n","d:\\\\result_groupa_"+outpath+".csv");
					dicflag=0;

				}
				
				if(dicb.get(wuid)!=null){
					save(wuid+","+call+","+chit+"\r\n","d:\\\\result_groupb_"+outpath+".csv");
					dicflag=0;
				}
				if(dicflag==1){
					save(wuid+","+call+","+chit+"\r\n","d:\\\\result_groupc_"+outpath+".csv");
				}
				save(wuid+","+call+","+chit+"\r\n","d:\\\\result_"+outpath+".csv");
				
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(line);
//				System.exit(-1);
			}
		}
		
		for (String p : mapnews.values()) {  
			  
		     
			   save(p+"\r\n","d:\\\\all_news_dict_"+outpath+".csv");
		    
//		    }
		} 
		save(""+pushhit+"\r\n",outpath+"allhistopushhit.txt");
		
		for(int j=0;j<24;j++){
			String line1="";
			for(int k=0;k<12;k++){
				line1+=j+":"+k*5+","+histopush[j][k]+"\r\n";
			}
			
			save(""+line1,outpath+"allhistopushhit.txt");
		}
		
		
		
		
		
		
	}
	
	public static void save(String content,String savepath) throws Exception
	{
	File nnFile= new File(savepath);
	BufferedWriter nnWriter=new BufferedWriter(new FileWriter(nnFile,true));
	nnWriter.write(content);
	nnWriter.close();

	}

}
