package activeTime;

import java.io.*;

public class GenerateHistoGram {
	
	public void generatHisto(String inputpath,String outpath) throws Exception{
		int[][] histo=new int [24][12];
		long activities=0,push=0,opentimes=0;
				//iterate all files
		File f=new File(inputpath);
		File[] files=f.listFiles();
		System.out.println(inputpath);
		for(int i=0;i<files.length;i++){
			
			try{
				long old=0;
				int[][] histoforone=new int [24][12];
				BufferedReader br=new BufferedReader(new FileReader(files[i]));
				String[] tnames=files[i].getAbsolutePath().split("/");
				String wuid=tnames[tnames.length-1].replaceAll(".csv","");
				String line="";
				int otforone=0,acforone=0;
				while((line=br.readLine())!=null){
					
					String[] t=line.split(",");
					if(t==null||t.length<3){
						continue;
					}
					//System.out.println(line);
					if(t[3].equals("push")){
						push++;
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
					String[] tmp=t[0].split(" ");
					int hour=Integer.parseInt(tmp[1].split(":")[0]);
					int minute=Integer.parseInt(tmp[1].split(":")[1]);
					histo[hour][minute/5]++;
					histoforone[hour][minute/5]++;
				}
				save(""+acforone+"\r\n","/Users/beidou/research/kdd2014/analysisinput/histo/"+wuid+".txt");
				save(""+otforone+"\r\n","/Users/beidou/research/kdd2014/analysisinput/histo/"+wuid+".txt");
				for(int j=0;j<24;j++){
					String line1="";
					for(int k=0;k<12;k++){
						line1+=j+":"+k*5+","+histoforone[j][k]+"\r\n";
					}
					
					save(""+line1,"/Users/beidou/research/kdd2014/analysisinput/histo/"+wuid+".txt");
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		save(""+activities+"\r\n","/Users/beidou/research/kdd2014/analysisinput/allhisto.txt");
		save(""+opentimes+"\r\n","/Users/beidou/research/kdd2014/analysisinput/allhisto.txt");
		for(int j=0;j<24;j++){
			String line1="";
			for(int k=0;k<12;k++){
				line1+=j+":"+k*5+","+histo[j][k]+"\r\n";
			}
			
			save(""+line1,"/Users/beidou/research/kdd2014/analysisinput/allhisto.txt");
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
