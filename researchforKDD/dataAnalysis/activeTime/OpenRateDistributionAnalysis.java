package activeTime;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OpenRateDistributionAnalysis {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File f=new File("d:\\\\all_news_popular_v9.csv");
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		HashMap<String, IntervalHit> map=new HashMap<String, IntervalHit>();
		while((line=br.readLine())!=null){
			String[] t=line.split(",");
			IntervalHit c=map.get(t[0]);
			if(c==null){
				c=new IntervalHit();
				map.put(t[0], c);
			}
			if(t[3].equals("0")){
				c.miss++;
			}else{
				c.hit++;
			}
		}
		
		for (Map.Entry<String, IntervalHit> entry : map.entrySet()) {  
			IntervalHit i=entry.getValue();
			double ratio=1.0*i.hit/(i.hit+i.miss);
			System.out.println(i.hit+","+i.miss);
		    if((i.hit+i.miss)<20){
		    	continue;
		    }
			save(entry.getKey()+","+ratio+"\r\n","d:\\\\analysis4_v9.csv");
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
