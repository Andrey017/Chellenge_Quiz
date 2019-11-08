package com.example.chellenge_ksp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chellenge_ksp.RecyclerViewUser.Item;
import com.example.chellenge_ksp.RecyclerViewUser.UserAdapter;

import java.util.ArrayList;

public class AddUserGameActivity extends AppCompatActivity {

    private ArrayList<Item> itemArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_game);

        buildRecyclerViewUser();
    }

    private void createListUser(){
        itemArrayList = new ArrayList<>();
        itemArrayList.add(new Item("dnsnd", R.drawable.back_1));
    }

    private void buildRecyclerViewUser(){
        createListUser();

        recyclerView = findViewById(R.id.recyclerView_User);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new UserAdapter(itemArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
    }
}
