package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.Question;
import com.example.chellenge_ksp.LocalDatabase.User;
import com.example.chellenge_ksp.LocalDatabase.User_game;
import com.example.chellenge_ksp.Network.LoadUsers;
import com.example.chellenge_ksp.Network.UpdateScore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ActivityQuestion extends AppCompatActivity {

    public TextView tv;
    public ProgressBar pb;
    public Button answer_1;
    public Button answer_2;
    public Button answer_3;
    public Button answer_4;
    public Button next_qwestion;
    public TextView textView_ques;

    private int index = 1;
    private int score = 0;

    private int user_id;
    private int game_id;

    DatabaseHandler db;

    private int result = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        db = new DatabaseHandler(this);

        List<User> userList = db.getAllUser();

        for (User u : userList){
            user_id = u.get_id();
        }

        List<User_game> user_gameList = db.getAllUser_game();

        for (User_game ug : user_gameList){
            game_id = ug.getId_game();
        }

        Intent intent = getIntent();
        String in = intent.getStringExtra("index");
        String in2 = intent.getStringExtra("score");
        index = Integer.parseInt(in);
        score = Integer.parseInt(in2);

        final Question question = db.getQuestion(index);

        tv = findViewById(R.id.timer);
        pb = findViewById(R.id.progressBar4);
        answer_1 = findViewById(R.id.answer_1);
        answer_1.setText(question.getAnswer1());
        answer_2 = findViewById(R.id.answer_2);
        answer_2.setText(question.getAnswer2());
        answer_3 = findViewById(R.id.answer_3);
        answer_3.setText(question.getAnswer3());
        answer_4 = findViewById(R.id.answer_4);
        answer_4.setText(question.getAnswer4());
        textView_ques = findViewById(R.id.textView_ques);
        textView_ques.setText(question.getQuestionText());
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
                answer_1.setEnabled(false);
                answer_2.setEnabled(false);
                answer_3.setEnabled(false);
                answer_4.setEnabled(false);

                next_qwestion.setVisibility(View.VISIBLE);
            }
        }.start();

        answer_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer_1.cancel();

                next_qwestion.setVisibility(View.VISIBLE);

                if(question.getRightAnswer() == 1) {
                    answer_1.setBackgroundResource(R.drawable.roundedbutton);
                    tv.setText("Правильный ответ!");
                    score++;
                }else {
                    answer_1.setBackgroundResource(R.drawable.roundedbutton_2);
                    tv.setText("Неправильный ответ!");
                }

                answer_1.setEnabled(false);
                answer_2.setEnabled(false);
                answer_3.setEnabled(false);
                answer_4.setEnabled(false);

                next_qwestion.setVisibility(View.VISIBLE);
            }
        });

        answer_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer_1.cancel();

                next_qwestion.setVisibility(View.VISIBLE);

                if(question.getRightAnswer() == 2) {
                    answer_2.setBackgroundResource(R.drawable.roundedbutton);
                    tv.setText("Правильный ответ!");
                    score++;
                }else {
                    answer_2.setBackgroundResource(R.drawable.roundedbutton_2);
                    tv.setText("Неправильный ответ!");
                }

                answer_1.setEnabled(false);
                answer_2.setEnabled(false);
                answer_3.setEnabled(false);
                answer_4.setEnabled(false);

                next_qwestion.setVisibility(View.VISIBLE);
            }
        });

        answer_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer_1.cancel();

                next_qwestion.setVisibility(View.VISIBLE);

                if(question.getRightAnswer() == 3) {
                    answer_3.setBackgroundResource(R.drawable.roundedbutton);
                    tv.setText("Правильный ответ!");
                    score++;
                }else {
                    answer_3.setBackgroundResource(R.drawable.roundedbutton_2);
                    tv.setText("Неправильный ответ!");
                }

                answer_1.setEnabled(false);
                answer_2.setEnabled(false);
                answer_3.setEnabled(false);
                answer_4.setEnabled(false);

                next_qwestion.setVisibility(View.VISIBLE);
            }
        });

        answer_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer_1.cancel();

                next_qwestion.setVisibility(View.VISIBLE);

                if(question.getRightAnswer() == 4) {
                    answer_4.setBackgroundResource(R.drawable.roundedbutton);
                    tv.setText("Правильный ответ!");
                    score++;
                }else {
                    answer_4.setBackgroundResource(R.drawable.roundedbutton_2);
                    tv.setText("Неправильный ответ!");
                }

                answer_1.setEnabled(false);
                answer_2.setEnabled(false);
                answer_3.setEnabled(false);
                answer_4.setEnabled(false);

                next_qwestion.setVisibility(View.VISIBLE);
            }
        });

        next_qwestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 5){
                    UpdateScore updateScore = new UpdateScore(score, user_id, game_id);

                    updateScore.execute();

                    while (result == 0){
                        result = updateScore.getCount();
                    }

                    if (result == 1){
                        result = 0;
                        db.deleteAllUSer_game();
                        LoadUsers loadUsers = new LoadUsers(game_id, db);

                        loadUsers.execute();

                        while (result == 0){
                            result = loadUsers.getCount();
                        }

                        if (result == 1){
                            openResult();
                        }
                    }
                }else {
                    openGame();
                }
            }
        });
    }

    private void openGame(){
        index++;
        String out = "" + index;
        String out2 = "" + score;
        Intent intent = new Intent(this, ActivityQuestion.class);
        intent.putExtra("index", out);
        intent.putExtra("score", out2);
        startActivity(intent);
        finish();
    }

    private void openResult(){
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
        finish();
    }
}
