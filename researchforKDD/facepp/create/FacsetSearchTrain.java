package create;

import org.json.JSONException;
import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class FacsetSearchTrain {
	public String sessionid="actor";
	public static void main(String[] args) throws FaceppParseException, JSONException {
		// TODO Auto-generated method stub
		
		System.out.println("\nfaceset/create");
		HttpRequests httpRequests = new HttpRequests("7a6775935b69617a2fd9bf1d7436996e", "33A7LD0SiBkx8GtABwYPNdb6U_t8xyAj ", true, true);
		JSONObject result = null;
		
		//recognition/train
		JSONObject syncRet = null; 
		
		System.out.println("\ntrain/Identify");
		syncRet = httpRequests.trainSearch(new PostParameters().setFacesetName("actor"));
		System.out.println(syncRet);
		System.out.println(httpRequests.getSessionSync(syncRet.getString("session_id")));
		
		
		
	}

}
