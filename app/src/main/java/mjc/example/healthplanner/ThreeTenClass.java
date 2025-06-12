package mjc.example.healthplanner;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class ThreeTenClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //ThreeTen 라이브러리 초기화
        AndroidThreeTen.init(this);
    }
}