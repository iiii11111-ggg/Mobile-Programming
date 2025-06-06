package mjc.example.healthplanner;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.*;

public class PlannerActicity extends AppCompatActivity {

    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        calendarView = findViewById(R.id.calendarView);
    }
}
