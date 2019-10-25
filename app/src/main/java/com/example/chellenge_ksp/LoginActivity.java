package com.example.chellenge_ksp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chellenge_ksp.Main;
import com.example.chellenge_ksp.R;
import com.example.chellenge_ksp.Registration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private String email_us;
    private String password_us;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.login);
        final Button button_reg = findViewById(R.id.button_reg);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.username);
                email_us = email.getText().toString();

                EditText password = (EditText) findViewById(R.id.password);
                password_us = password.getText().toString();

                new Login().execute();
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

    class Login extends AsyncTask<Void, Void, Void>{

        String name;
        String surname;
        String email;
        String password_in;
        int count = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String myURL = "http://94.141.168.185:8009/login?email=" + email_us + "&password=" + password_us;

                try{
                    URL url = new URL(myURL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();

                    String data = convertStreamToString(inputStream);

                    JSONArray jsonArray = new JSONArray(data);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject schedule = jsonArray.getJSONObject(i);

                        name = schedule.getString("name");
                        surname = schedule.getString("surname");
                        email = schedule.getString("email");
                        password_in = schedule.getString("password");
                        count++;
                    }

                    if (count == 1) {
                        openMain();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    private String convertStreamToString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
