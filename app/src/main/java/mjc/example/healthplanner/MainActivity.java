package mjc.example.healthplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final long SPLASH_DELAY_TIME = 3000; // 3초 (밀리세컨드 단위)
    private Handler mDelayHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView userName = findViewById(R.id.userName);
        Intent inIntent = getIntent();
        String userID = inIntent.getStringExtra("userID");
        userName.setText("환영합니다." +userID+"님!!");

        mDelayHandler = new Handler(Looper.getMainLooper());

        // 실행할 작업(Runnable) 정의
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {

                    Intent intent = new Intent(getApplicationContext(),RecommendActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        // 정의된 시간(SPLASH_DELAY_TIME) 후에 mRunnable 실행 예약
        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY_TIME);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity가 파괴될 때, Handler에 예약된 작업(Runnable)이 있다면 제거합니다.
        // 이는 메모리 누수를 방지하고, 원치 않는 시점에 Runnable이 실행되는 것을 막습니다.
        if (mDelayHandler != null && mRunnable != null) {
            mDelayHandler.removeCallbacks(mRunnable);
        }
    }


}