package mjc.example.healthplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class RecommendActivity extends AppCompatActivity {

    TextView skipText,BMIText;
    float userBMI;
    float userHeight,userWeight;
    Integer userAge;
    String userGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);
        SharedPreferences share = getSharedPreferences("userInfo",MODE_PRIVATE);

        //----------------------------BMI 계산----------------------------------------

        userHeight = Float.parseFloat(share.getString("userHeight","0"));
        userWeight = Float.parseFloat(share.getString("userWeight","0"));
        userBMI = userWeight /((userHeight/100)*(userHeight/100));
        userBMI = Math.round(userBMI * 10) / 10.0f;

        BMIText = findViewById(R.id.BMIText);
        BMIText.setText("당신의 BMI : " + String.valueOf(userBMI));

        //---------------------------BMI에 따른 체중 기준 가져오기------------------------

        userAge = Integer.parseInt(share.getString("userAge","0"));
        userGender = share.getString("userGender","NoneGender");
        String userClass = BMIClass.getBMIClass(userBMI,userAge,userGender);

        //------------BMI 기준에 따른 출력 : 문구 -----------
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
            list.setLayoutResource(R.layout.arm_item);
        }
        else if (userClass.equals("과체중")) {
            list.setLayoutResource(R.layout.chest_item);
        }
        else if (userClass.equals("저체중")) {
            list.setLayoutResource(R.layout.leg_item);
        }
        else if(userClass.equals("정상")) {
            list.setLayoutResource(R.layout.leg_item);
        }
        else {
            list.setLayoutResource(R.layout.back_item);
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

        listupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);


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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                //BMIRequest BMIRequest = new BMIRequest(userID);
                //RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                //queue.add(validteRequest);
            }
        });



    }
}
