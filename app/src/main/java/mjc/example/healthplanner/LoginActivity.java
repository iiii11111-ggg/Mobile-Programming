package mjc.example.healthplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import mjc.example.healthplanner.Request.LoginRequest;
// 로그인 화면
public class LoginActivity extends AppCompatActivity {
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        final EditText etId = findViewById(R.id.etId);
        final EditText etPasswd = findViewById(R.id.etPasswd);
        Button btnLogin = findViewById(R.id.btnLogin);

        etPasswd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnLogin.performClick();
                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = etId.getText().toString();
                final String userPasswd = etPasswd.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            { //로그인 성공 시 화면 넘어가기 전 유저의 모든 정보를 SharePreferences 객체에 저장 및 등록
                                SharedPreferences shared = getSharedPreferences("userInfo",MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("userId",jsonResponse.getString("id"));
                                editor.putString("userName",jsonResponse.getString("name"));
                                editor.putString("userGender",jsonResponse.getString("gender"));
                                editor.putString("userAge",jsonResponse.getString("age"));
                                editor.putString("userHeight",jsonResponse.getString("height"));
                                editor.putString("userWeight",jsonResponse.getString("weight"));
                                editor.apply();

                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"올바른 ID와 Password를 입력하세요",Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPasswd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
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