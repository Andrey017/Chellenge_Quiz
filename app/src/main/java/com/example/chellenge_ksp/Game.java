package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.GameDB;
import com.example.chellenge_ksp.LocalDatabase.User;
import com.example.chellenge_ksp.Network.AddUser;
import com.example.chellenge_ksp.Network.LoadUsers;
import com.example.chellenge_ksp.Network.Load_Game;
import com.example.chellenge_ksp.Network.NewGame;
import com.example.chellenge_ksp.RecyclerViewGame.GameAdapter;
import com.example.chellenge_ksp.RecyclerViewGame.ItemGame;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    private ArrayList<ItemGame> itemGameArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GameAdapter gameAdapter;

    private Button add_new_game;

    private int count_player = 2;

    DatabaseHandler db;

    private String name_game;
    private String ques;
    private int user_id;
    private int game_id;

    private int result = 0;

    private String email_palyer_2;
    private String email_palyer_3;
    private String email_palyer_4;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        db = new DatabaseHandler(this);

        List<User> userList = db.getAllUser();

        for(User u : userList){
            user_id = u.get_id();
        }

        add_new_game = (Button)findViewById(R.id.new_game_add);
        add_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                final View view = getLayoutInflater().inflate(R.layout.create_game, null);

                final EditText editText_name = (EditText) view.findViewById(R.id.textView_name_game);

                Button cancel = (Button) view.findViewById(R.id.button_cancel_game);
                Button create = (Button) view.findViewById(R.id.button_create);

                final EditText editText_player_1 = (EditText)view.findViewById(R.id.textView_name_player_1);
                editText_player_1.setText("ВЫ");

                final EditText editText_player_2 = (EditText)view.findViewById(R.id.textView_name_player_2);
                final EditText editText_player_3 = (EditText)view.findViewById(R.id.textView_name_player_3);
                final EditText editText_player_4 = (EditText)view.findViewById(R.id.textView_name_player_4);

                RadioButton radioButton_2 = (RadioButton)view.findViewById(R.id.radioButton_2);
                radioButton_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText_player_2.setVisibility(View.VISIBLE);
                        editText_player_3.setVisibility(View.GONE);
                        editText_player_4.setVisibility(View.GONE);
                        count_player = 2;
                    }
                });

                RadioButton radioButton_3 = (RadioButton)view.findViewById(R.id.radioButton_3);
                radioButton_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText_player_2.setVisibility(View.VISIBLE);
                        editText_player_3.setVisibility(View.VISIBLE);
                        editText_player_4.setVisibility(View.GONE);

                        count_player = 3;
                    }
                });

                RadioButton radioButton_4 = (RadioButton)view.findViewById(R.id.radioButton_4);
                radioButton_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText_player_2.setVisibility(View.VISIBLE);
                        editText_player_3.setVisibility(View.VISIBLE);
                        editText_player_4.setVisibility(View.VISIBLE);

                        count_player = 4;
                    }
                });

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
                        name_game = editText_name.getText().toString();
                        ques = "123";

                        NewGame newGame = new NewGame(name_game, user_id, ques);
                        newGame.execute();

                        while (result == 0){
                            result = newGame.getCount();
                        }

                        if (result == 1){
                            game_id = newGame.getGame_id();
                        }

                        result = 0;

                        if(count_player == 2){
                            email_palyer_2 = editText_player_2.getText().toString();

                            AddUser addUser = new AddUser(game_id, email_palyer_2);

                            addUser.execute();
                            alertDialog.dismiss();
                        }else if (count_player == 3){
                            email_palyer_2 = editText_player_2.getText().toString();
                            email_palyer_3 = editText_player_3.getText().toString();

                            AddUser addUser = new AddUser(game_id, email_palyer_2);
                            addUser.execute();
                            AddUser addUser1 = new AddUser(game_id, email_palyer_3);
                            addUser1.execute();
                            alertDialog.dismiss();
                        }else if (count_player == 4){
                            email_palyer_2 = editText_player_2.getText().toString();
                            email_palyer_3 = editText_player_3.getText().toString();
                            email_palyer_4 = editText_player_4.getText().toString();

                            AddUser addUser = new AddUser(game_id, email_palyer_2);
                            addUser.execute();
                            AddUser addUser1 = new AddUser(game_id, email_palyer_3);
                            addUser1.execute();
                            AddUser addUser2 = new AddUser(game_id, email_palyer_4);
                            addUser2.execute();
                            alertDialog.dismiss();
                        }

                        db.deleteAllGameDB();
                        Load_Game load_game = new Load_Game(user_id, db);
                        load_game.execute();
                        while (result == 0){
                            result = load_game.getCount();
                        }

                        if (result == 1){
                            buildRecyclerViewGame();
                        }
                    }
                });
            }
        });

        buildRecyclerViewGame();
    }

    private void createListGame() {
        itemGameArrayList = new ArrayList<>();

        List<GameDB> gameDBList = db.getAllGameDB();

        for (GameDB g : gameDBList){
            itemGameArrayList.add(new ItemGame(g.getName()));
        }
    }

    private void buildRecyclerViewGame(){
        createListGame();

        recyclerView = findViewById(R.id.recyclerView_Games);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        gameAdapter = new GameAdapter(itemGameArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gameAdapter);

        gameAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println(position);

                List<GameDB> gameDBList = db.getAllGameDB();

                position++;

                for (GameDB g : gameDBList){
                    if (g.get_id() == position){
                        game_id = g.getGame_id();
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
                    openResult();
                }
            }
        });
    }

    private void openResult(){
        Intent intent = new Intent(Game.this, Result.class);
        startActivity(intent);
    }
}
