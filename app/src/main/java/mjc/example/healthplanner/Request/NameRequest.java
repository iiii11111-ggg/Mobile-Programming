package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class NameRequest extends JsonObjectRequest{

    private static final String BASE_URL = "http://3.107.190.203:8080/HealthPlannerBackEnd/";
    private static final String URL = BASE_URL + "exerciseName.jsp";

    public NameRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(), listener, errorListener);
    }

    private static JSONObject createRequestBody() {
        JSONObject json = new JSONObject();
        return json;
    }

}
