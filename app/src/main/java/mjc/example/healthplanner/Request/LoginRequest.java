package mjc.example.healthplanner.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class LoginRequest extends StringRequest {
    private static final String BASE_URL = "http://3.107.190.203:8080/HealthPlannerBackEnd/";
    private static final String URL = BASE_URL + "UserLogin.jsp"; //
    private Map<String,String> parameters;
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}