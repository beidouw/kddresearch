package create;

import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
//{"tag":"","faceset_id":"c536473362046d52eefcdc1882555156","response_code":200,"added_face":0,"faceset_name":"actor"}
public class DeleteFaceSet {

	public String name="actor";
	public static void main(String[] args) throws FaceppParseException {
		// TODO Auto-generated method stub
		
		System.out.println("\nfaceset/create");
		HttpRequests httpRequests = new HttpRequests("7a6775935b69617a2fd9bf1d7436996e", "33A7LD0SiBkx8GtABwYPNdb6U_t8xyAj ", true, true);
		JSONObject result = null;
		System.out.println(httpRequests.facesetDelete(new PostParameters().setFacesetName("actor")));

	}

}
