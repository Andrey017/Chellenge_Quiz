package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.Network.Login_user;

public class LoginActivity extends AppCompatActivity {
    private String email_us;
    private String password_us;
    private int result = 0;

    DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandler(this);

        int count = db.getUserCount();

        if (count == 1){
            openMain();
        }

        final Button loginButton = findViewById(R.id.login);
        final Button button_reg = findViewById(R.id.button_reg);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.username);
                email_us = email.getText().toString();

                EditText password = (EditText) findViewById(R.id.password);
                password_us = password.getText().toString();

                Login_user login_user = new Login_user(email_us, password_us, db);

                login_user.execute();
                while (result == 0) {
                    result = login_user.getCount();
                }

                if (result == 1){
                    openMain();
                }
            }
        });

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2(){
        Intent intent  = new Intent(this , Registration.class);
        startActivity(intent);
        finish();
    }

    public void openMain(){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();
    }
}
