package com.example.chellenge_ksp;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chellenge_ksp.RecyclerViewGame.GameAdapter;
import com.example.chellenge_ksp.RecyclerViewGame.ItemGame;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private ArrayList<ItemGame> itemGameArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GameAdapter gameAdapter;

    private Button add_new_game;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        add_new_game = (Button)findViewById(R.id.new_game_add);
        add_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                final View view = getLayoutInflater().inflate(R.layout.create_game, null);
                EditText editText = (EditText) view.findViewById(R.id.textView_name_game);
                Button cancel = (Button) view.findViewById(R.id.button_cancel_game);
                Button create = (Button) view.findViewById(R.id.button_create);

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //тут отправка на сервер будет
                    }
                });
            }
        });

        buildRecyclerViewGame();
    }

    private void createListGame() {
        itemGameArrayList = new ArrayList<>();
        itemGameArrayList.add(new ItemGame("Play №1", R.drawable.back_1));
        itemGameArrayList.add(new ItemGame("Play №2", R.drawable.back_1));
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
