package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;

public class Main extends AppCompatActivity {

    public Button new_game;
    public Button exit;
    DatabaseHandler db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new_game = findViewById(R.id.new_game);
        exit = findViewById(R.id.exit);
        db = new DatabaseHandler(this);

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllUser();
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

}
