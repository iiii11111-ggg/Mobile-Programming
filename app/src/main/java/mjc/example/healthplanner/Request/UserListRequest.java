package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserListRequest extends JsonObjectRequest {
    private static final String URL = "http://192.168.0.18:8080/UserList.jsp";

    public UserListRequest(String userID, String date, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(userID, date), listener, errorListener);
    }

    private static JSONObject createRequestBody(String userID, String date) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("date",date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
