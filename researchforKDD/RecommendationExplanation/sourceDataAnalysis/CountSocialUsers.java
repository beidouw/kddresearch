package sourceDataAnalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CountSocialUsers {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 */
	public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, Exception {
		// TODO Auto-generated method stub
		CountUsers();
	}
	
	public static void CountUsers() throws Exception, IllegalAccessException, ClassNotFoundException{
		Connection conn;
		Statement stmt,stmt2,stmt3;
		ResultSet res,res2;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ikanfou",
		                                      "root", "4150484");
	
   
		stmt = conn.createStatement();
		stmt2 = conn.createStatement();	
		stmt3 = conn.createStatement();
		
		String sql="select wuid,appBilateral from userinfo where appBilateral is not null and appBilateral!='' and accesstokensecret>"+System.currentTimeMillis();
		System.out.println(sql);
		res=stmt.executeQuery(sql);
		int friendsum=0,usersum=0,over5sum=0;
		while(res.next()){
			String[] t= res.getString("appBilateral").split(",");
			usersum++;
			friendsum+=t.length;
			if(t.length>5){
				over5sum++;
			}
			
		}
		System.out.println(usersum+","+(1.0*friendsum/usersum)+","+over5sum);
		
	}

}
