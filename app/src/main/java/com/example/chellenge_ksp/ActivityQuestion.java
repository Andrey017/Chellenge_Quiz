package com.example.chellenge_ksp;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;

public class ActivityQuestion extends AppCompatActivity {

    public TextView tv;
    public ProgressBar pb;
    public Button answer_1;
    public Button next_qwestion;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tv = findViewById(R.id.timer);
        pb = findViewById(R.id.progressBar4);
        answer_1 = findViewById(R.id.answer_1);
        next_qwestion = findViewById(R.id.next_question);



        final CountDownTimer timer_1 = new CountDownTimer(20000, 1000){

            @Override
            public void onTick(long l) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        "ss", Locale.getDefault());
                final String strDate = simpleDateFormat.format(l);
                tv.setText("" + strDate);
                pb.setProgress((int) (l/1000));
            }

            @Override
            public void onFinish() {
                tv.setText("Время вышло!");
            }
        }.start();

        answer_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_1.setBackgroundResource(R.drawable.roundedbutton);
                timer_1.cancel();
                next_qwestion.setVisibility(View.VISIBLE);
                tv.setText("Правильный ответ!");
            }
        });


    }
}
