package com.example.project11_1;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Integer[] posterID = {R.drawable.img,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5,R.drawable.img_6,R.drawable.img_7,R.drawable.img_8,R.drawable.img_9,R.drawable.img_10,R.drawable.img_11, R.drawable.img_12,R.drawable.img_13,R.drawable.img_14,R.drawable.img_15,R.drawable.img_16,R.drawable.img_17,R.drawable.img_18,R.drawable.img_19,R.drawable.img_20,R.drawable.img,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5,R.drawable.img_6,R.drawable.img_7,R.drawable.img_8,R.drawable.img_9,R.drawable.img_10,R.drawable.img_11, R.drawable.img_12,R.drawable.img_13,R.drawable.img_14,R.drawable.img_15,R.drawable.img_16,R.drawable.img_17,R.drawable.img_18,R.drawable.img_19,R.drawable.img_20};
    View dlgView;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gv = findViewById(R.id.gridView1);


        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);


    }

    public class MyGridAdapter extends BaseAdapter{
        Context context;

        public MyGridAdapter(Context _context) {
            context = _context;
        }

        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //GridView에 출력할 ImageView를 생성 후 이미지 출력

            ImageView ivG = new ImageView(context);
            ivG.setLayoutParams(new GridView.LayoutParams(250,300));
            ivG.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ivG.setPadding(5,5,5,5);

            ivG.setImageResource(posterID[position]);

            final int pos = position;
            ivG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlgView = View.inflate(MainActivity.this,R.layout.dialog,null);
                    ivPoster = dlgView.findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(posterID[position]);


                    dlg.setTitle("영화 포스터");
                    dlg.setIcon(R.mipmap.ic_launcher);
                    dlg.setView(dlgView);
                    dlg.setNegativeButton("닫기",null);
                    dlg.show();
                }
            });

            return ivG;
        }
    }
}