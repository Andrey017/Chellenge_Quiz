package com.example.chellenge_ksp.Network;

import android.os.AsyncTask;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Login_user extends AsyncTask<Void, Void, Void> {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int code;
    public int count = 0;

    DatabaseHandler db;

    public Login_user(String email, String password, DatabaseHandler db){
        this.email = email;
        this.password = password;
        this.db = db;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String myURL = "http://94.141.168.185:8009/login?email=" + email + "&password=" + password;

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

                    id = Integer.parseInt(schedule.getString("id"));
                    name = schedule.getString("name");
                    surname = schedule.getString("surname");
                    email = schedule.getString("email");
                    setEmail(email);
                    password = schedule.getString("password");
                    setPassword(password);
                    code = Integer.parseInt(schedule.getString("code"));
                    setCode(code);
                    count++;
                }

                System.out.println("Inserting user...");

                db.addUser(new User(id, name, surname, email, password));

                List<User> userList = db.getAllUser();

                for (User us : userList){
                    String log = "" + us.get_id() + " " + us.getName() + " " + us.getSurname() + " " + us.getEmail() + " " + us.getPassword();
                    System.out.println(log);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (count == 1) {
            setCount(1);
        }
    }

    private String convertStreamToString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
