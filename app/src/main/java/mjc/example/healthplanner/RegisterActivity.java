package mjc.example.healthplanner;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import mjc.example.healthplanner.R;
import mjc.example.healthplanner.Request.RegisterRequest;
import mjc.example.healthplanner.Request.ValidateRequest;

public class RegisterActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    private String userGender;
    private AlertDialog dialog;
    private boolean validate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText etId = findViewById(R.id.etId);
        final EditText etPasswd = findViewById(R.id.etPasswd);
        final EditText etPasswdValid = findViewById(R.id.etPasswdValid);
        final EditText etName = findViewById(R.id.etName);
        final EditText etAge = findViewById(R.id.etAge);
        final EditText etHeight = findViewById(R.id.etHeight);
        final EditText etWeight = findViewById(R.id.etWeight);
        final Button btnValidate = findViewById(R.id.btnValidate);

        // 성별 고르는 순간 저장하기
        RadioGroup rgroupGender = findViewById(R.id.rgroupGender);
        rgroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.female)
                {
                    userGender = "female";
                }
                else if(i == R.id.male)
                {
                    userGender = "male";
                }
                else{userGender="";}
            }
        });
        // 아이디 중복버튼 클릭
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = etId.getText().toString();
                if (userID.trim().equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "아이디를입력해주세요 .", Toast.LENGTH_SHORT).show();
                    validate = false;
                    etId.setFocusable(true);
                    return;
                }
                if (validate) return;
                // 중복 처리
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean newID = jsonResponse.getBoolean("newID");
                            if (newID && userID.length() >= 5 && userID.length() <= 20) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.
                                        this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다. 사용하시겠습니까?")
                                        .setPositiveButton("확인 ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                etId.setEnabled(false);
                                                btnValidate.setEnabled(false);
                                                validate = true;
                                            }
                                        })
                                        .setNegativeButton("취소",null)
                                        .create();
                                dialog.show();
                            } else if(userID.length() < 5 || userID.length() > 20) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.
                                        this);
                                dialog = builder.setMessage("5자에서 20자 내로 입력하십시오.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.
                                        this);
                                dialog = builder.setMessage("이미 사용중인 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validteRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validteRequest);
            }
        });
        Button btnSubmit = findViewById(R.id.btnSubmit);
        // 제출 버튼 리스너
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = etId.getText().toString();
                String userPassword = etPasswd.getText().toString();
                String userPasswordValid = etPasswdValid.getText().toString();
                String userName = etName.getText().toString();
                String userAge = etAge.getText().toString();
                String userHeight = etHeight.getText().toString();
                String userWeight = etWeight.getText().toString();

                // 어느 하나라도 비어있을 때
                if (userPassword.equals("") || userName.equals("") || userPasswordValid.equals("") ||
                          userGender.equals("") || userAge.equals("") || userHeight.equals("") || userWeight.equals(""))
                {
                    Log.d("submitCheck","CHeck" + userID+"--"+userPassword+"--"+userGender+"--"+userName+"--"+userAge+"--"+userHeight+"--"+userWeight);
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모든정보를입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                // 비밀번호 미 일치 시
                if (!userPassword.equals(userPasswordValid)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.
                                        this);
                                dialog = builder.setMessage(jsonResponse.getString("message"))
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender,
                        userName, userAge,userHeight,userWeight, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}