package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class CorrectFaceid {
	
	public Connection con,conn2,conntest;
	public Statement stmt,stmt2;
	public ResultSet res,res2;
	public PreparedStatement ps;
	public String oldid="";
	public String basepath="/Users/beidou/research/neteasemusicdata/playlistcontent/";
	public final String sqlURL="jdbc:mysql://localhost:3306/ikanfou?useUnicode=true&characterEncoding=utf8";
	public final String user="root";
	public final String	pwd="4150484";
	public CorrectFaceid() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(sqlURL,user,pwd);
		

	}

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, FaceppParseException, JSONException{
		System.out.println("add actor");
		CorrectFaceid a=new CorrectFaceid();
		a.Add();
	}
	
	
	public void Add() throws SQLException, FaceppParseException, JSONException{
		String sql="select * from actors_new where imageurl!= ? and faceid is not null";
		ps=con.prepareStatement(sql);
		ps.setString(1, "null");
		res=ps.executeQuery();
		JSONObject result = null;
		HttpRequests httpRequests = new HttpRequests("7a6775935b69617a2fd9bf1d7436996e", "33A7LD0SiBkx8GtABwYPNdb6U_t8xyAj ", true, true);

		
		
		while(res.next()){
			//detection/detect
			String url="http://42.121.117.9/atRec/actors/"+res.getString("tvdbid")+"_"+res.getString("aid")+".jpg";
			try{
//			result = httpRequests.detectionDetect(new PostParameters().setUrl(res.getString("imageurl")));
			result = httpRequests.detectionDetect(new PostParameters().setUrl(url));
//			System.out.println(result);	
			
			System.out.println(res.getString("tvdbid")+","+res.getString("aid")+","+res.getString("imageurl"));
			System.out.println(result.getJSONArray("face").getJSONObject(0).getJSONObject("position").getJSONObject("center"));
			sql="update actors_new set faceid=? where tvdbid= ? and aid= ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, ""+result.getJSONArray("face").getJSONObject(0).getString("face_id"));
			ps.setString(2, res.getString("tvdbid"));
			ps.setString(3, res.getString("aid"));
			ps.execute();
			
			httpRequests.facesetAddFace(new PostParameters().setFacesetName("actor").setFaceId(
					result.getJSONArray("face").getJSONObject(0).getString("face_id")));
//			httpRequests.personCreate(new PostParameters().setPersonName(res.getString("tvdbid")+"_"+res.getString("aid")));
//			System.out.println(httpRequests.personAddFace(new PostParameters().setPersonName(res.getString("tvdbid")+"_"+res.getString("aid")).setFaceId(
//			result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
			}
			catch(com.facepp.error.FaceppParseException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}finally{
//				httpRequests.facesetAddFace(new PostParameters().setFacesetName("actor"));
				
				
			}
			
		}
		
	}

}
