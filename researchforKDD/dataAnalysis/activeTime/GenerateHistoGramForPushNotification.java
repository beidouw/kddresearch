package activeTime;

import java.io.*;

public class GenerateHistoGramForPushNotification {
	
	public void generatHisto(String inputpath,String outpath) throws Exception{
		int[][] histo=new int [24][12];
		int[][] histopush=new int [24][12];
		long activities=0,pushhit=0,opentimes=0;
				//iterate all files
		File f=new File(inputpath);
		File[] files=f.listFiles();
		System.out.println(inputpath);
		for(int i=0;i<files.length;i++){
			
			try{
				long old=0,timeinterval=0;
				String oldtype="";
				int[][] histoforone=new int [24][12];
				BufferedReader br=new BufferedReader(new FileReader(files[i]));
				String[] tnames=files[i].getAbsolutePath().split("/");
				String wuid=tnames[tnames.length-1].replaceAll(".csv","");
				String line="";
				int opentime;
				int oldhour=0;
				while((line=br.readLine())!=null){
					
					String[] t=line.split(",");
					if(t==null||t.length<3){
						continue;
					}
					String[] tmp=t[0].split(" ");
					int hour=Integer.parseInt(tmp[1].split(":")[0]);
					int minute=Integer.parseInt(tmp[1].split(":")[1]);

					long current=Long.parseLong(t[1]);
					//System.out.println(line);
					if(!t[3].equals("push")&&oldtype.equals("push")){
						pushhit++;
						timeinterval=(current-old)/60000;
						histopush[hour][minute/5]++;
						
						if(oldhour<7){
							save(timeinterval+"\r\n",outpath+"pushhitinterval_midnight.txt");
						}
						
						if(oldhour>=7&&oldhour<=10){
							save(timeinterval+"\r\n",outpath+"pushhitinterval_morning.txt");
						}
						
						if(oldhour>10&&oldhour<14){
							save(timeinterval+"\r\n",outpath+"pushhitinterval_noon.txt");
						}
						
						if(oldhour>=14&&oldhour<17){
							save(timeinterval+"\r\n",outpath+"pushhitinterval_afternoon.txt");
						}
						
						if(oldhour>=17&&oldhour<=20){
							save(timeinterval+"\r\n",outpath+"pushhitinterval_evening.txt");
						}
						
						if(oldhour>=21){
							save(timeinterval+"\r\n",outpath+"pushhitinterval_night.txt");
						}

						
						
						save(timeinterval+"\r\n",outpath+"pushhitinterval.txt");
					}
					
					
					
					oldhour=hour;
					old=current;
					oldtype=t[3];
					
					
				}
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
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
