package com.example.chellenge_ksp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.Network.Confirm;
import com.example.chellenge_ksp.Network.Login_user;
import com.example.chellenge_ksp.Network.NewCode;

public class LoginActivity extends AppCompatActivity {
    private String email_us;
    private String password_us;
    private int result = 0;

    DatabaseHandler db;

    private Confirm confirm;
    private NewCode newCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandler(this);

        int count = db.getUserCount();

        if (count == 1){
            openMain();
        }else {
            db.deleteAllUser();
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

                final Login_user login_user = new Login_user(email_us, password_us, db);

                login_user.execute();
                while (result == 0) {
                    result = login_user.getCount();
                }

                if (result == 1){
                    if (login_user.getCode() == 0) {
                        openMain();
                    }else{
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        final View view = getLayoutInflater().inflate(R.layout.email_conf, null);

                        final EditText code = view.findViewById(R.id.textView_conf_code);
                        final TextView info = view.findViewById(R.id.textView_info_conf);
                        final TextView info_log = view.findViewById(R.id.textView_info_login);

                        info_log.setText("Ваша почта не подтверждена");
                        info_log.setVisibility(View.VISIBLE);

                        Button cancel = view.findViewById(R.id.button_cancel_conf);
                        Button send = view.findViewById(R.id.button_conf);
                        Button inf_conf = view.findViewById(R.id.button_code_recovery);

                        builder.setView(view);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();


                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                db.deleteAllUser();
                                alertDialog.dismiss();
                            }
                        });

                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirm = new Confirm(code.getText().toString());

                                confirm.execute();
                                result = 0;
                                while (result == 0){
                                    result = confirm.getCount();
                                }

                                if (result == 1 && confirm.getConf() == 1){
                                    alertDialog.dismiss();
                                    openMain();
                                }else {
                                    info.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        inf_conf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                newCode = new NewCode(login_user.getEmail(), password_us);
                                newCode.execute();

                                result = 0;
                                while (result == 0){
                                    result = newCode.getCount();
                                }
                            }
                        });
                    }
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
