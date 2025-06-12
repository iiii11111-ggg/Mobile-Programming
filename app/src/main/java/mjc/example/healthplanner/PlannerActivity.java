package mjc.example.healthplanner;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.*;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import mjc.example.healthplanner.Request.RecordedDateRequest;
import mjc.example.healthplanner.Request.UserListRequest;


public class PlannerActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;
    Button btnBack,btnRecord;
    TextView selectedDateText,recordText,nullText;
    int year,month,dayOfMonth,dayOfWeek;
    String dayName;
    HorizontalScrollView hsv;
    LinearLayout container;

    PlannerDecoratior.SelectedDateDecorator selectedDecorator = null;
    CalendarDay today;
    PlannerDecoratior.TodayDecorator todayDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);
        recordText = findViewById(R.id.recordText);
        nullText = findViewById(R.id.nullText);
        hsv = findViewById(R.id.hsv);
        container = findViewById(R.id.container);
        btnRecord = findViewById(R.id.btnRecord);

        PlannerDecoratior.setupCalendar(calendarView, this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        today = CalendarDay.today();
        todayDecorator = new PlannerDecoratior.TodayDecorator();

        year = today.getYear();
        month = today.getMonth();
        dayOfMonth = today.getDay();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.clear();
        calendar.set(year, month-1, dayOfMonth);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayName = getDayName(dayOfWeek);

        // ----------------------시작하자마자 적용될 디자인 ---------------
        calendarView.addDecorators(todayDecorator);
        calendarView.addDecorators(new PlannerDecoratior.DimDatesDecorator());
        selectedDateText.setText(""+today.getDate()+" "+dayName);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlannerActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });

        // -------------------------- 기록된 날까 가져오기 --------------------------------
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                        if (response.getBoolean("success")) {
                            JSONArray recordedDatesJsonArray = response.getJSONArray("recordedDates");
                            List<CalendarDay> GrayDateDecorator = new ArrayList<>();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        for (int i = 0; i < recordedDatesJsonArray.length(); i++) {
                            String dateString = recordedDatesJsonArray.getString(i);
                            try{
                                java.util.Date date = sdf.parse(dateString);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(date);

                                GrayDateDecorator.add(CalendarDay.from(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH) + 1, calendar2.get(Calendar.DAY_OF_MONTH)));
                            } catch (java.text.ParseException e) {
                                Log.e("date", "날짜 파싱 오류: " + dateString, e);
                            }
                        }
                        calendarView.addDecorator(new PlannerDecoratior.GrayDateDecorator(getApplicationContext(), GrayDateDecorator));
                            Log.d("DateParsing", "추가된 날짜: " +GrayDateDecorator.toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PlannerActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        };
        SharedPreferences share = getSharedPreferences("userInfo",MODE_PRIVATE);
        String userId = share.getString("userId","Null");

        RecordedDateRequest RecordedDateRequest = new RecordedDateRequest(userId,responseListener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(PlannerActivity.this);
        queue.add(RecordedDateRequest);


// -------------------------------------------- 달력 선택 기능 --------------------------------------------
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                recordText.setVisibility(View.INVISIBLE);
                nullText.setVisibility(View.VISIBLE);
                hsv.setVisibility(View.INVISIBLE);

                year = date.getYear();
                month = date.getMonth();
                dayOfMonth = date.getDay();
                calendar.set(year, month-1, dayOfMonth);
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                dayName = getDayName(dayOfWeek);

                if (selectedDecorator != null) {
                    calendarView.removeDecorator(selectedDecorator);
                }

                selectedDecorator  = new PlannerDecoratior.SelectedDateDecorator(date,getApplicationContext());
                calendarView.removeDecorator(todayDecorator);
                calendarView.addDecorator(selectedDecorator);

                selectedDateText.setText(""+date.getDate()+" "+dayName);
                //---------------------------------날짜 선택 시------------------------------------------
                if(date.equals(today))
                {
                    recordText.setText("오늘의 기록");
                    btnRecord.setVisibility(View.VISIBLE);
                }
                else if(date.isBefore(today))
                {
                    recordText.setText("이전날의 기록");
                    btnRecord.setVisibility(View.INVISIBLE);
                }
                else{
                    recordText.setText("기록이없음");
                    btnRecord.setVisibility(View.INVISIBLE);
                }

                Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getBoolean("found")) {
                                recordText.setVisibility(View.VISIBLE);
                                nullText.setVisibility(View.INVISIBLE);
                                hsv.setVisibility(View.VISIBLE);

                                JSONArray nameList = response.getJSONArray("nameList");
                                container.removeAllViews();
                                for(int i = 0; i < nameList.length();i++)
                                {
                                    JSONObject record = nameList.getJSONObject(i);

                                    String imageName = record.getString("exerciseName");
                                    String koreanName = record.getString("koreanName");
                                    String setCount = record.getString("setCount");

                                    LayoutInflater inflater = LayoutInflater.from(PlannerActivity.this);

                                    View itemView = inflater.inflate(R.layout.list_container, container, false);

                                    ImageView imageView = itemView.findViewById(R.id.exerciseimg);
                                    TextView textView = itemView.findViewById(R.id.exerciseName);
                                    TextView setCountText = itemView.findViewById(R.id.setCount);

                                    int imageResId = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());
                                    if(imageResId != 0){
                                        imageView.setImageResource(imageResId);
                                    } else {
                                        // 이미지가 없을 경우 기본 이미지 처리
                                        imageView.setImageResource(R.drawable.start);
                                    }
                                    textView.setText(koreanName);
                                    setCountText.setText(setCount);
                                    container.addView(itemView);
                                }
                            }else{
                                recordText.setVisibility(View.VISIBLE);
                                nullText.setVisibility(View.VISIBLE);
                                hsv.setVisibility(View.INVISIBLE);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();

                    }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(PlannerActivity.this, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                };
                String dateStr = date.getYear() + "-" +
                        String.format("%02d", date.getMonth()) + "-" +
                        String.format("%02d", date.getDay());

                UserListRequest UserListRequest = new UserListRequest(userId,dateStr,responseListener,errorListener);
                RequestQueue queue = Volley.newRequestQueue(PlannerActivity.this);
                queue.add(UserListRequest);

            }
        });
    }

    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return "일요일";
            case Calendar.MONDAY: return "월요일";
            case Calendar.TUESDAY: return "화요일";
            case Calendar.WEDNESDAY: return "수요일";
            case Calendar.THURSDAY: return "목요일";
            case Calendar.FRIDAY: return "금요일";
            case Calendar.SATURDAY: return "토요일";
            default: return "";
        }
    }
}
