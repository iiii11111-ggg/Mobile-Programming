package mjc.example.healthplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONObject;

public class RecommendActivity extends AppCompatActivity {

    TextView skipText,BMIText;
    float userBMI;
    float userHeight,userWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);
        SharedPreferences share = getSharedPreferences("userInfo",MODE_PRIVATE);

        //----------------------------BMI 계산----------------------------------------

        String getHeight = share.getString("userHeight","0");
        String getWeight = share.getString("userWeight","0");
        userHeight = Float.parseFloat(getHeight);
        userWeight = Float.parseFloat(getWeight);
        userBMI = userWeight /((userHeight/100)*(userHeight/100));
        userBMI = Math.round(userBMI * 10) / 10.0f;

        BMIText = findViewById(R.id.BMIText);
        BMIText.setText("당신의 BMI : " + String.valueOf(userBMI));

        //-----------------------건너뛰기 시 달력 이동-----------------------------------
        skipText = findViewById(R.id.skipText);
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PlannerActicity.class);
                startActivity(intent);
                finish();
            }
        });


        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean newID = jsonResponse.getBoolean("newID");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //BMIRequest BMIRequest = new BMIRequest(userID);
        //RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        //queue.add(validteRequest);


    }
}
