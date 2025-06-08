package mjc.example.healthplanner.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ListRequest extends JsonObjectRequest {
    private static final String URL = "http://192.168.0.18:8080/ListUp.jsp";

    public ListRequest(String userID, List<String> exerciseIds, List<String> setCountList, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, createRequestBody(userID, exerciseIds,setCountList), listener, errorListener);
    }

    private static JSONObject createRequestBody(String userID, List<String> exerciseIds,List<String> setCountList) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            JSONArray jsonArray = new JSONArray();
            for (String id : exerciseIds) {
                jsonArray.put(id);
            }
            json.put("exerciseIds", jsonArray);

            JSONArray jsonArray2 = new JSONArray();
            for (String setCount : setCountList) {
                jsonArray2.put(setCount);
            }
            json.put("setCountList", jsonArray2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}