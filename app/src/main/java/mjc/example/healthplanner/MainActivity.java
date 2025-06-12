package mjc.example.healthplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

// 로그인 후 화면
public class MainActivity extends AppCompatActivity {

    private final long SPLASH_DELAY_TIME = 3000; // 3초 (밀리세컨드 단위)
    private Handler mDelayHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView userName = findViewById(R.id.userName);
        SharedPreferences share = getSharedPreferences("userInfo",MODE_PRIVATE);
        String username = share.getString("userName","고객님");
        userName.setText("환영합니다." +username+"님!!");

        // Looper Handler를 생성해서 기다렸다가 화면을 이동하는 UI스레드 작업
        mDelayHandler = new Handler(Looper.getMainLooper());
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
        if (mDelayHandler != null && mRunnable != null) {
            mDelayHandler.removeCallbacks(mRunnable);
        }
    }
}