package create;

import org.json.JSONException;
import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class SearchTrainDone {
	public static String sessionid="e9c6f72765794603a6c1367d78ed857b";
	public static void main(String[] args) throws FaceppParseException, JSONException {
		// TODO Auto-generated method stub
		
		System.out.println("analyze train process");
		HttpRequests httpRequests = new HttpRequests("7a6775935b69617a2fd9bf1d7436996e", "33A7LD0SiBkx8GtABwYPNdb6U_t8xyAj ", true, true);
		System.out.println("\ninfo/get_session");
		System.out.println(httpRequests.infoGetSession(new PostParameters().setSessionId(
				sessionid)));
	}

}
