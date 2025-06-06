package mjc.example.healthplanner.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ListRequest extends StringRequest {
    final static private String URL = "http://192.168.0.18:8080/UserLogin.jsp"; //
    private Map<String,String> parameters;
    public ListRequest(String userID,String exerciseId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("exerciseId",exerciseId);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
