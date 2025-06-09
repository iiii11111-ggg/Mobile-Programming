package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class setCountRequest extends JsonObjectRequest {
    private static final String BASE_URL = "http://3.107.190.203:8080/HealthPlannerBackEnd/";
    private static final String URL = BASE_URL + "setCountChange.jsp";

    public setCountRequest(String userID, String exerciseId,String setCount, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(userID, exerciseId,setCount), listener, errorListener);
    }

    private static JSONObject createRequestBody(String userID, String exerciseId, String setCount) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("exerciseId", exerciseId);
            json.put("setCount", setCount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
