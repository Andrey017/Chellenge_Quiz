package com.example.chellenge_ksp.Network;

import android.os.AsyncTask;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.GameDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Load_Old_game extends AsyncTask<Void, Void, Void> {
    private int user_id;

    private int id;
    private int score;
    private int user_win;
    private String questions;
    private int end;
    private String name;
    public int count = 0;

    DatabaseHandler db;

    public Load_Old_game(int user_id, DatabaseHandler db){
        this.user_id = user_id;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String myURL = "http://94.141.168.185:8009/loadgameuppold?user_id=" + user_id;

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

                    id = Integer.parseInt(jsonObject.getString("id"));
                    score = Integer.parseInt(jsonObject.getString("score"));
                    user_win = Integer.parseInt(jsonObject.getString("user_win"));
                    questions = jsonObject.getString("questions");
                    end = Integer.parseInt(jsonObject.getString("end_round"));
                    name = jsonObject.getString("name_game");

                    db.addGame(new GameDB(id, user_win, questions, end, name, score));
                }

                count++;

                System.out.println("Load game ...");

                List<GameDB> gameDBList = db.getAllGameDB();

                for (GameDB ga : gameDBList){
                    String log = "" + ga.get_id() + " " + ga.getGame_id() + " " + ga.getName() + " " + ga.getQuestions() + " " + ga.getEnd() + " " + ga.getScore();
                    System.out.println(log);
                }

                db.close();

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
