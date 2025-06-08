package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class NameRequest extends JsonObjectRequest{

    private static final String URL = "http://192.168.0.18:8080/exerciseName.jsp";

    public NameRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(), listener, errorListener);
    }

    private static JSONObject createRequestBody() {
        JSONObject json = new JSONObject();
        return json;
    }

}
