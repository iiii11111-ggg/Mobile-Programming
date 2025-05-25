package mjc.example.healthplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView userName = findViewById(R.id.userName);
        Intent inIntent = getIntent();
        String userID = inIntent.getStringExtra("userID");
        userName.setText("환영합니다." +userID+"님!!");
    }
}