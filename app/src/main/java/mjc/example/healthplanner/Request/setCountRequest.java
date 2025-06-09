package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class setCountRequest extends JsonObjectRequest {
    private static final String URL = "http://192.168.0.18:8080/setCountChange.jsp";

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
