package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecordedDateRequest extends JsonObjectRequest {
    private static final String URL = "http://192.168.0.18:8080/RecordedDate.jsp";

    public RecordedDateRequest(String userID, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(userID), listener, errorListener);
    }

    private static JSONObject createRequestBody(String userID) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
