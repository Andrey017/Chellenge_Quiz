package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.User;
import com.example.chellenge_ksp.LocalDatabase.User_game;
import com.example.chellenge_ksp.Network.LoadQuestion;
import com.example.chellenge_ksp.RecyclerViewResult.ItemResult;
import com.example.chellenge_ksp.RecyclerViewResult.ResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {

    private ArrayList<ItemResult> itemResultArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResultAdapter resultAdapter;

    private Button start;

    DatabaseHandler db;

    private int result = 0;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_result_game);

        db = new DatabaseHandler(this);

        buildRecyclerView();

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllQuestion();
                LoadQuestion loadQuestion = new LoadQuestion("123", db);

                loadQuestion.execute();

                while (result == 0){
                    result = loadQuestion.getCount();
                }

                if(result == 1){
                    openGame();
                }
            }
        });
    }

    private void createListResult(){
        itemResultArrayList = new ArrayList<>();

        List<User_game> user_gameList = db.getAllUser_game();

        for (User_game ug : user_gameList){
            itemResultArrayList.add(new ItemResult(ug.getName() + " " + ug.getSurname(), "" + ug.getScore()));
        }
    }

    private void buildRecyclerView(){
        createListResult();

        recyclerView = findViewById(R.id.recyclerView_Result);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        resultAdapter = new ResultAdapter(itemResultArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultAdapter);
    }

    private void openGame(){
        int score1 = 0;
        int user_id = 0;

        List<User> userList = db.getAllUser();

        for (User u : userList){
            user_id = u.get_id();
        }

        List<User_game> user_gameList = db.getAllUser_game();

        for (User_game ug : user_gameList){
            if (ug.getUser_id() == user_id){
                score1 = ug.getScore();
            }
        }

        String index = "" + 1;
        String score = "" + score1;
        Intent intent = new Intent(this, ActivityQuestion.class);
        intent.putExtra("index", index);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
