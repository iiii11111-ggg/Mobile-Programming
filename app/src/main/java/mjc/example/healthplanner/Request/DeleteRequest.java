package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DeleteRequest extends JsonObjectRequest {

    private static final String BASE_URL = "http://3.107.190.203:8080/HealthPlannerBackEnd/";
    private static final String URL = BASE_URL + "RecordDelete.jsp";

    public DeleteRequest(String userID, String exerciseId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(userID, exerciseId), listener, errorListener);
    }

    private static JSONObject createRequestBody(String userID, String exerciseId) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("exerciseId", exerciseId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
