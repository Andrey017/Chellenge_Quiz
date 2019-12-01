package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.chellenge_ksp.RecyclerViewResult.ItemResult;
import com.example.chellenge_ksp.RecyclerViewResult.ResultAdapter;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private ArrayList<ItemResult> itemResultArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResultAdapter resultAdapter;

    private Button start;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_result_game);

        buildRecyclerView();

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    private void createListResult(){
        itemResultArrayList = new ArrayList<>();

        itemResultArrayList.add(new ItemResult(R.drawable.back_1, "fbndbf", "2"));
        itemResultArrayList.add(new ItemResult(R.drawable.back_1, "tirit", "0"));
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
        Intent intent = new Intent(this, ActivityQuestion.class);
        startActivity(intent);
        finish();
    }
}
