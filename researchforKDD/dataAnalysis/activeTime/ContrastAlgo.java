package activeTime;

import java.io.*;
import java.util.HashMap;

public class ContrastAlgo {
	
	public void generatHisto(String inputpath,String dic1,String dic2,String outpath) throws Exception{
		int[][] histo=new int [24][12];
		int[][] histopush=new int [24][12];
		long activities=0,push=0,opentimes=0;
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
		for(int i=0;i<files.length;i++){
			
			try{
				long old=0;
				int[][] histoforone=new int [24][12];
				BufferedReader br=new BufferedReader(new FileReader(files[i]));
				String[] tnames=files[i].getAbsolutePath().split("/");
				String wuid=tnames[tnames.length-1].replaceAll(".csv","");
				line="";
				int otforone=0,acforone=0,pushforone=0;
				while((line=br.readLine())!=null){
					
					String[] t=line.split(",");
					if(t==null||t.length<3){
						continue;
					}
					System.out.println(t[0]);
					String[] tmp=t[0].split(" ");
					int hour=Integer.parseInt(tmp[1].split(":")[0]);
					int minute=Integer.parseInt(tmp[1].split(":")[1]);

					
					//System.out.println(line);
					if(t[3].equals("push")){
						push++;
						pushforone++;
						histopush[hour][minute/5]++;
						
						
						continue;
					}
					activities++;
					acforone++;
					long current=Long.parseLong(t[1]);
					if((current-old)<300000){
						old=current;
						continue;
					}
					old=current;
					opentimes++;
					otforone++;
					
					
					histo[hour][minute/5]++;
					histoforone[hour][minute/5]++;
				}
				save(""+acforone+"\r\n",outpath+"histo/"+wuid+".txt");
				save(""+otforone+"\r\n",outpath+"histo/"+wuid+".txt");
				save(""+pushforone+"\r\n",outpath+"histo/"+wuid+".txt");
				for(int j=0;j<24;j++){
					String line1="";
					for(int k=0;k<12;k++){
						line1+=j+":"+k*5+","+histoforone[j][k]+"\r\n";
					}
					
					save(""+line1,outpath+"histo/"+wuid+".txt");
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		save(""+activities+"\r\n",outpath+"allhisto.txt");
		save(""+opentimes+"\r\n",outpath+"allhisto.txt");
		save(""+push+"\r\n",outpath+"allhisto.txt");
		for(int j=0;j<24;j++){
			String line1="";
			for(int k=0;k<12;k++){
				line1+=j+":"+k*5+","+histo[j][k]+"\r\n";
			}
			
			save(""+line1,outpath+"allhisto.txt");
		}
		
		for(int j=0;j<24;j++){
			String line1="";
			for(int k=0;k<12;k++){
				line1+=j+":"+k*5+","+histopush[j][k]+"\r\n";
			}
			
			save(""+line1,outpath+"allhistopush.txt");
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
