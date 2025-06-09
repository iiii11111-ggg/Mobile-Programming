package mjc.example.healthplanner.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class RegisterRequest extends StringRequest {
    private static final String BASE_URL = "http://3.107.190.203:8080/HealthPlannerBackEnd/";
    private static final String URL = BASE_URL + "UserRegister.jsp"; //
    private Map<String,String> parameters;
    public RegisterRequest(String userID, String userPassword, String userGender,String userName, String userAge,
                           String userHeight,String userWeight, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge);
        parameters.put("userHeight", userHeight);
        parameters.put("userWeight", userWeight);

    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}