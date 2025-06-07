package mjc.example.healthplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mjc.example.healthplanner.Request.ListRequest;

public class RecommendActivity extends AppCompatActivity {

    TextView skipText,BMIText;
    float userBMI;
    float userHeight,userWeight;
    Integer userAge;
    String userGender,userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        SharedPreferences share = getSharedPreferences("userInfo",MODE_PRIVATE);

        //----------------------------BMI 계산----------------------------------------

        userHeight = Float.parseFloat(share.getString("userHeight","0"));
        userWeight = Float.parseFloat(share.getString("userWeight","0"));
        userBMI = userWeight /((userHeight/100)*(userHeight/100));
        userBMI = Math.round(userBMI * 10) / 10.0f;

        //---------------------------BMI에 따른 체중 기준 가져오기------------------------

        userId = share.getString("userId","Null");
        userAge = Integer.parseInt(share.getString("userAge","0"));
        userGender = share.getString("userGender","NoneGender");
        String userClass = BMIClass.getBMIClass(userBMI,userAge,userGender);

        //------------BMI 기준에 따른 출력 : 문구 -----------

        BMIText = findViewById(R.id.BMIText);
        BMIText.setText("당신의 BMI : " + String.valueOf(userBMI) + " ("+userClass+")");

        TextView congrastText = findViewById(R.id.congratsText);
        if(userClass.equals("비만"))
        {
            congrastText.setText("태풍이와도 버틸거같아요! 일반인이되어봐요 :)");
        }
        else if (userClass.equals("과체중")) {
            congrastText.setText("듬직하시네요! 조금만 운동해볼까요?");
        }
        else if (userClass.equals("저체중")) {
            congrastText.setText("바람불면 날아가겠어요... 근육을 만들어볼까요?");
        }
        else if(userClass.equals("정상")) {
            congrastText.setText("벌써 몸짱이시네요!! 건강하게 운동해봐요!");
        }
        else {
            congrastText.setText("당신은 외계인인가요?!?!");
        }
        //------------BMI 기준에 따른 출력 : 리스트 -----------
        ViewStub list = findViewById(R.id.exerciseStub);
        if(userClass.equals("비만"))
        {
            list.setLayoutResource(R.layout.item_diet);
        }
        else if (userClass.equals("과체중")) {
            list.setLayoutResource(R.layout.item_overweight);
        }
        else if (userClass.equals("저체중")) {
            list.setLayoutResource(R.layout.item_muscle);
        }
        else if(userClass.equals("정상")) {
            list.setLayoutResource(R.layout.item_normal);
        }
        View listView = list.inflate();

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

        //---------------------이대로 진행하기 시 DB에 리스트 기록-------------------------

        Button listupButton = findViewById(R.id.listupButton);
        List<String> exerciseList = new ArrayList<>();

        listupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userClass.equals("비만"))
                {
                    exerciseList.add("31");
                    exerciseList.add("32");
                    exerciseList.add("33");
                    exerciseList.add("34");
                    exerciseList.add("35");
                    exerciseList.add("36");
                }
                else if (userClass.equals("과체중")) {
                    exerciseList.add("31");
                    exerciseList.add("35");
                    exerciseList.add("7");
                    exerciseList.add("13");
                    exerciseList.add("25");
                    exerciseList.add("33");
                }
                else if (userClass.equals("저체중")) {
                    exerciseList.add("30");
                    exerciseList.add("5");
                    exerciseList.add("6");
                    exerciseList.add("13");
                    exerciseList.add("23");
                    exerciseList.add("11");
                }
                else if(userClass.equals("정상")) {
                    exerciseList.add("25");
                    exerciseList.add("5");
                    exerciseList.add("6");
                    exerciseList.add("13");
                    exerciseList.add("23");
                    exerciseList.add("31");
                }

                Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(RecommendActivity.this, "기록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RecommendActivity.this,PlannerActicity.class);
                        startActivity(intent);
                        finish();
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(RecommendActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                };
                ListRequest ListRequest = new ListRequest(userId,exerciseList,responseListener,errorListener);
                RequestQueue queue = Volley.newRequestQueue(RecommendActivity.this);
                queue.add(ListRequest);
            }
        });



    }
}
