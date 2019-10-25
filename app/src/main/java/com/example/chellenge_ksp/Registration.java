package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registration extends AppCompatActivity {

    private Button button_reg;

    String name_user;
    String email_user;
    String password_user;
    String surname_user;
    String password_user_app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        button_reg = (Button) findViewById(R.id.reg);
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.username);
                name_user = name.getText().toString();

                EditText surname = (EditText) findViewById(R.id.surname);
                surname_user = surname.getText().toString();

                EditText email = (EditText) findViewById(R.id.email);
                email_user = email.getText().toString();

                EditText password = (EditText) findViewById(R.id.password);
                password_user = password.getText().toString();

                EditText password_app = (EditText) findViewById(R.id.password_append);
                password_user_app = password_app.getText().toString();

                if (password_user.equals(password_user_app)) {
                    new SendData().execute();
                }
            }
        });
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    class SendData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String myURL = "http://94.141.168.185:8009/reg?name=" + name_user + "&surname=" + surname_user + "&email=" + email_user + "&password=" + password_user;

                InputStream is = null;
                byte[] data = null;
                String resultString;
                try{
                    URL url = new URL(myURL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    if (responseCode == 200){
                        is = connection.getInputStream();

                        byte[] buffer = new byte[8192];
                        int bytesRead;

                        while ((bytesRead = is.read(buffer)) != -1){
                            baos.write(buffer, 0, bytesRead);
                        }

                        data = baos.toByteArray();
                        resultString = new String(data, "UTF-8");

                        openLoginActivity();

                    }else{
                        connection.disconnect();
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
}
