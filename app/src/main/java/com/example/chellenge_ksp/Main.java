package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.User;
import com.example.chellenge_ksp.Network.Load_Game;
import com.example.chellenge_ksp.Network.Load_Old_game;

import java.util.List;

public class Main extends AppCompatActivity {

    public Button new_game;
    public Button history;
    public Button settings;
    public Button exit;
    private TextView textView_name_player;
    DatabaseHandler db;

    private int user_id;
    private String name_user;

    private int result = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new_game = findViewById(R.id.new_game);
        history = findViewById(R.id.history);
        settings = findViewById(R.id.settings);
        exit = findViewById(R.id.exit);
        textView_name_player = findViewById(R.id.name_of_player);
        db = new DatabaseHandler(this);

        List<User> userList = db.getAllUser();

        for (User u : userList){
            user_id = u.get_id();
            name_user = u.getName() + " " + u.getSurname();
        }

        textView_name_player.setText(name_user);

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllGameDB();
                db.deleteAllUSer_game();
                Load_Game load_game = new Load_Game(user_id, db);
                load_game.execute();
                while (result == 0){
                    result = load_game.getCount();
                }

                if (result == 1){
                    openGame();
                }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllUSer_game();;
                db.deleteAllGameDB();
                Load_Old_game load_old_game = new Load_Old_game(user_id, db);
                load_old_game.execute();

                result = 0;
                while (result == 0){
                    result = load_old_game.getCount();
                }

                if (result == 1){
                    openHistory();
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllUser();
                db.deleteAllUSer_game();
                db.deleteAllQuestion();
                db.deleteAllGameDB();
                openLogin();
            }
        });


    }

    public void openLogin(){
        Intent intent  = new Intent(this , LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void openGame(){
        Intent intent  = new Intent(this , Game.class);
        startActivity(intent);
        finish();
    }

    public void openHistory(){
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }

}
