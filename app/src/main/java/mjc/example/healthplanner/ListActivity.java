// mjc.example.healthplanner/ListActivity.java
package mjc.example.healthplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mjc.example.healthplanner.Request.DeleteRequest;
import mjc.example.healthplanner.Request.UserRecordReqeust;
import mjc.example.healthplanner.Request.setCountRequest;

// 유저 리스트 관리 화면
public class ListActivity extends AppCompatActivity {

    // UI 요소 선언
    private TextView tvDate; // 상단 날짜 표시 TextView
    private ImageView btnBack; // 상단 뒤로가기 버튼
    private ImageView calendarReturn; // 상단 캘린더 돌아가기 버튼 (ID가 calendarbutton인데, xml에서 calendarReturn으로 사용됨)
    TextView notRecord;

    private List<Exercise> allExercises;

    private RecyclerView recordRecyclerView; // 레코드 목록을 표시할 RecyclerView
    private RecordListAdapter recordListAdapter; // RecyclerView에 데이터를 연결할 어댑터

    // 하단 바 UI 요소
    private ImageView exerciseImg; // 클릭된 운동의 이미지
    private TextView exerciseName; // 클릭된 운동의 이름과 세트수
    private RadioGroup rgSelect; // 세트/시간 선택 RadioGroup
    private RadioButton radioButton; // 라디오 버튼
    private Spinner spinner; // 숫자 선택기
    private Button btnRegister; // 세트 등록 버튼
    private Button btnDeleteSelectedRecord; // 하단 "리스트 삭제" 버튼 (전체 삭제는 아님, 선택된 항목 관련 삭제)
    private TextView setExplane;
    private ConstraintLayout bottomBar;
    boolean radioCheck;


    private String userId; // SharedPreferences에서 가져올 userId
    String radioSelectText;
    String selectedExerciseId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded); // activity_recorded.xml 레이아웃 사용

        tvDate = findViewById(R.id.tv_current_date);
        btnBack = findViewById(R.id.btnBack);
        calendarReturn = findViewById(R.id.calendarReturn);
        exerciseImg = findViewById(R.id.exerciseImg);
        exerciseName = findViewById(R.id.exerciseName);
        rgSelect = findViewById(R.id.rgSelect);
        radioButton = findViewById(R.id.rbSet);
        spinner = findViewById(R.id.spinner);
        btnRegister = findViewById(R.id.btnRegister);
        recordListAdapter = new RecordListAdapter();
        recordRecyclerView = findViewById(R.id.record_recyclerview);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordRecyclerView.setAdapter(recordListAdapter);
        notRecord = findViewById(R.id.notRecord);
        setExplane = findViewById(R.id.setExplane);
        bottomBar = findViewById(R.id.bottomBar);
        radioCheck = false;

        // SharedPreferences에서 userId 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        // 인텐트 받기
        Intent intent = getIntent();
        allExercises = intent.getParcelableArrayListExtra("exerciseList");
        Exercise selectedId = intent.getParcelableExtra("selectedExercise");

        if (selectedId == null) {
            selectedId = new Exercise("dummy_id", "기본운동", "기본분류", R.drawable.selected_calendar,"99999");
        }

        // 오늘 날짜 수정하기
        tvDate.setText(getTodayFormattedDate());

        //스피너 설정
        String[] numbers = new String[60];
        for (int i = 0; i < 60; i++) {
            numbers[i] = String.valueOf(i+1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,numbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //백버튼

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //캘린더 돌아가기

        calendarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, PlannerActivity.class);
                startActivity(intent);
            }
        });

        // 화면 등록할 리스트
        List<ExerciseWithSetCount> recyclerItems = new ArrayList<>();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success"))
                    {
                        JSONArray exerciseIdArray = response.getJSONArray("exerciseIdArray");
                        JSONArray setCountArray = response.getJSONArray("setCountArray");

                        for(int i = 0; i< exerciseIdArray.length();i++){
                            for (Exercise ex : allExercises){
                                if (ex.getExerciseId().equals(exerciseIdArray.getString(i))) {
                                    recyclerItems.add(new ExerciseWithSetCount(ex,setCountArray.getString(i)));
                                    break;
                                }
                            }
                        }
                    }
                    recordListAdapter.submitList(new ArrayList<>(recyclerItems));
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ListActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        };

        UserRecordReqeust UserRecordReqeust = new UserRecordReqeust(userId, selectedId.getExerciseId(),responseListener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
        queue.add(UserRecordReqeust);

        // 리스트 삭제
        recordListAdapter.setOnDeleteClickListener((exerciseId, position) -> {

            Response.Listener<JSONObject> responseListener1 = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getBoolean("success")) {
                            Toast.makeText(ListActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        if(response.getBoolean("recordDelete")){
                            notRecord.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener1 = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(ListActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            };
            DeleteRequest DeleteRequest = new DeleteRequest(userId, exerciseId,responseListener1,errorListener1);
            RequestQueue queue1 = Volley.newRequestQueue(ListActivity.this);
            queue1.add(DeleteRequest);

            recyclerItems.remove(position);
            recordListAdapter.submitList(new ArrayList<>(recyclerItems));
        });

        // 세트 수 변경 로직
        recordListAdapter.setOnItemClickListener(exercise ->{
            bottomBar.setVisibility(View.VISIBLE);

            String name = exercise.getKoreanName();
            int imageRes = exercise.getImageResId();
            selectedExerciseId = exercise.getExerciseId();

            exerciseImg.setImageResource(imageRes);
            exerciseName.setText(exercise.getKoreanName());
        });

        // 세트 수 변경 로직 : 라디오 버튼
        rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioCheck = true;
                radioButton = findViewById(checkedId);
                radioSelectText = radioButton.getText().toString();
                Log.d("errorTag", ""+radioSelectText);
            }
        });

        // 세트 수 변경 로직 : 스피너 + 세트 등록 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!radioCheck) {
                    Toast.makeText(ListActivity.this, "세트 혹은 시간을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String setNumber;
                if(radioSelectText.equals("세트")){
                    setNumber = spinner.getSelectedItem().toString()+"세트";
                }
                else {
                    setNumber = spinner.getSelectedItem().toString()+"분";
                }
                Response.Listener<JSONObject> responseListener2 = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")) {
                                //세트수가 변경되면 어댑터의 리스너를 동작시켜 화면에도 갱신
                                recordListAdapter.updateSetCountById(selectedExerciseId,setNumber);
                                Toast.makeText(ListActivity.this, "세트수가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Response.ErrorListener errorListener2 = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ListActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                };
                setCountRequest setCountRequest = new setCountRequest(userId,selectedExerciseId,setNumber,responseListener2,errorListener2);
                RequestQueue queue2 = Volley.newRequestQueue(ListActivity.this);
                queue2.add(setCountRequest);

            }
        });

    }
    // 오늘날짜 가져오기
    public String getTodayFormattedDate() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        String dayOfWeekKorean = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = today.format(dateFormatter);

        return formattedDate + " " + dayOfWeekKorean;
    }
}