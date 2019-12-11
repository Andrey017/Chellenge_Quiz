package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.GameDB;
import com.example.chellenge_ksp.RecyclerViewOldGames.ItemOldGames;
import com.example.chellenge_ksp.RecyclerViewOldGames.OldGamesAdapter;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private ArrayList<ItemOldGames> itemOldGamesArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OldGamesAdapter oldGamesAdapter;

    DatabaseHandler db;

    private Button back;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = new DatabaseHandler(this);

        back = findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        buildRecyclerViewOldGame();
    }

    private void createListOldGame(){
        itemOldGamesArrayList = new ArrayList<>();

        List<GameDB> gameDBList = db.getAllGameDB();

        for (GameDB g : gameDBList){
            if (g.getEnd() == 1){
                itemOldGamesArrayList.add(new ItemOldGames(g.getName()));
            }
        }
    }

    private void buildRecyclerViewOldGame(){
        createListOldGame();

        recyclerView = findViewById(R.id.recyclerView_OLD_Games);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        oldGamesAdapter = new OldGamesAdapter(itemOldGamesArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(oldGamesAdapter);
    }

    private void openMain(){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();
    }
}
