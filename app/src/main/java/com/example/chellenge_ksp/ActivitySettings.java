package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.User;
import com.example.chellenge_ksp.Network.Login_user;
import com.example.chellenge_ksp.Network.UpdateSettings;

import java.util.List;

public class ActivitySettings extends AppCompatActivity {

    private Button update;

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText password;
    private EditText password_append;

    private int user_id;
    private String user_name;
    private String user_surname;
    private String user_email;
    private String user_password;

    private UpdateSettings updateSettings;

    private int result = 0;

    DatabaseHandler db;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_settings);

        db = new DatabaseHandler(this);

        List<User> userList = db.getAllUser();

        for (User u : userList){
            user_id = u.get_id();
            user_name = u.getName();
            user_surname = u.getSurname();
            user_email = u.getEmail();
            user_password = u.getPassword();
        }

        name = findViewById(R.id.set_username);
        name.setText(user_name);
        surname = findViewById(R.id.set_surname);
        surname.setText(user_surname);
        email = findViewById(R.id.set_email);
        email.setText(user_email);
        password = findViewById(R.id.set_password);
        password_append = findViewById(R.id.set_password_append);

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySettings.this);
                final View view = getLayoutInflater().inflate(R.layout.password_confirmation, null);

                final EditText editText_password = view.findViewById(R.id.textView_old_password);

                Button cancel = view.findViewById(R.id.button_cancel_update);
                Button update_inf = view.findViewById(R.id.button_update_information);

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                update_inf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user_password.equals(editText_password.getText().toString())){
                            user_name = name.getText().toString();
                            user_surname = surname.getText().toString();
                            user_email = email.getText().toString();

                            if (password.getText().toString().isEmpty()){
                                user_password = editText_password.getText().toString();
                            }else {
                                if (password.getText().toString().equals(password_append.getText().toString())){
                                    user_password = password.getText().toString();
                                }else{

                                }
                            }

                            updateSettings = new UpdateSettings(user_id, user_name, user_surname, user_email, user_password);
                            updateSettings.execute();

                            while (result == 0){
                                result = updateSettings.getCount();
                            }

                            if (result == 1){
                                openLogin();
                            }
                        }else {
                            //проверка на правильность паролей
                        }
                    }
                });
            }
        });
    }

    private void openLogin(){
        db.deleteAllGameDB();
        db.deleteAllQuestion();
        db.deleteAllUSer_game();
        db.deleteAllUser();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
