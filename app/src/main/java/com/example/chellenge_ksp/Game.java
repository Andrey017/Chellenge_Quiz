package com.example.chellenge_ksp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chellenge_ksp.RecyclerViewGame.GameAdapter;
import com.example.chellenge_ksp.RecyclerViewGame.ItemGame;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private ArrayList<ItemGame> itemGameArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GameAdapter gameAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        buildRecyclerViewGame();
    }

    private void createListGame() {
        itemGameArrayList = new ArrayList<>();
        itemGameArrayList.add(new ItemGame("dsnd", R.drawable.back_1));
    }

    private void buildRecyclerViewGame(){
        createListGame();

        recyclerView = findViewById(R.id.recyclerView_Games);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        gameAdapter = new GameAdapter(itemGameArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gameAdapter);
    }
}
