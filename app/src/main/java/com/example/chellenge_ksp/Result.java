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
import com.example.chellenge_ksp.Network.LoadUsers;
import com.example.chellenge_ksp.Network.NextRound;
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
    private int block = 0;
    private int count_player;
    private int user_id;
    private int user_end;
    private int game_id;
    private int check;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_result_game);

        db = new DatabaseHandler(this);

        Intent intent = getIntent();
        String in = intent.getStringExtra("check");
        check = Integer.parseInt(in);

        List<User> userList = db.getAllUser();

        for (User u : userList){
            user_id = u.get_id();
        }

        List<User_game> user_games = db.getAllUser_game();

        count_player = user_games.size();

        int flag = 0;

        for (User_game ug : user_games){
            game_id = ug.getId_game();
            if (ug.getEnd_round() == 1){
                if (ug.getUser_id() == user_id){
                    user_end = 1;
                }
                flag++;
            }else{
                if (ug.getUser_id() == user_id){
                    user_end = 0;
                }
            }
        }

        if (flag != count_player && user_end == 1){
            block = 1;
        }

        buildRecyclerView();

        start = findViewById(R.id.start);
        if (block == 1){
            start.setText("К играм");
        }else if (check == 0){

            List<User_game> userGames = db.getAllUser_game();

            for (User_game ug : userGames){
                NextRound nextRound = new NextRound(game_id, ug.getUser_id());
                nextRound.execute();

                result = 0;
                while (result == 0){
                    result = nextRound.getCount();
                }

            }

            db.deleteAllUSer_game();
            LoadUsers loadUsers = new LoadUsers(game_id, db);
            loadUsers.execute();

            result = 0;
            while (result == 0){
                result = loadUsers.getCount();
            }

            if (result == 1){
                buildRecyclerView();
            }
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (block == 0) {
                    db.deleteAllQuestion();
                    LoadQuestion loadQuestion = new LoadQuestion("123", db);

                    loadQuestion.execute();

                    result = 0;
                    while (result == 0) {
                        result = loadQuestion.getCount();
                    }

                    if (result == 1) {
                        openGame();
                    }
                }else{
                    openActivityGame();
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
    }

    public void openActivityGame(){
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
        finish();
    }
}
