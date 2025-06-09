package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserRecordReqeust extends JsonObjectRequest {
    private static final String URL = "http://192.168.0.18:8080/RecordList.jsp";

    public UserRecordReqeust(String userID, String addEx, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(userID,addEx), listener, errorListener);
    }
    private static JSONObject createRequestBody(String userID,String addEx) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("selectedExerciseId", addEx);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
