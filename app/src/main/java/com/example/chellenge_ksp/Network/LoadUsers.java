package com.example.chellenge_ksp.Network;

import android.os.AsyncTask;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.User_game;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LoadUsers extends AsyncTask<Void, Void, Void> {
    private int game_id;
    private int count = 0;
    DatabaseHandler db;

    private int user_id;
    private String name_user;
    private String surname_user;
    private String email_user;
    private int score_user;
    private int round_user;
    private int end_round_user;

    public LoadUsers(int game_id, DatabaseHandler db){
        this.game_id = game_id;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String myURL = "http://94.141.168.185:8009/loadusergameupp?game_id=" + game_id;

            try{
                URL url = new URL(myURL);

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                String data = convertStreamToString(inputStream);

                JSONArray jsonArray = new JSONArray(data);

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    user_id = Integer.parseInt(jsonObject.getString("user_id"));
                    name_user = jsonObject.getString("name");
                    surname_user = jsonObject.getString("surname");
                    email_user = jsonObject.getString("email");
                    score_user = Integer.parseInt(jsonObject.getString("score"));
                    round_user = Integer.parseInt(jsonObject.getString("round"));
                    end_round_user = Integer.parseInt(jsonObject.getString("end_round"));

                    db.addUser_Game(new User_game(game_id, user_id, name_user, surname_user, email_user, score_user, round_user, end_round_user));
                }

                count++;

                System.out.println("Load user_game ...");

                List<User_game> user_gameList = db.getAllUser_game();

                for (User_game u : user_gameList){
                    String log = "" + u.get_id() + " " + u.getId_game() + " " + u.getUser_id() + " " + u.getName() + " " + u.getSurname() + " " + u.getEmail() + " " + u.getScore() + " " + u.getRound() + " " + u.getEnd_round();
                    System.out.println(log);
                }

                //db.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        if (count == 1){
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
}
