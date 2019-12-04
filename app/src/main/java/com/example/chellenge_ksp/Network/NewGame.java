package com.example.chellenge_ksp.Network;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewGame extends AsyncTask<Void, Void, Void> {
    private String name_game;
    private int player;
    private String ques;
    private int count = 0;
    private int game_id;

    public NewGame(String name_game, int player, String ques){
        this.name_game = name_game;
        this.player = player;
        this.ques = ques;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String myURL = "http://94.141.168.185:8009/newgame?user_id=" + player + "&ques=" + ques + "&name_game=" + name_game;

            try{
                URL url = new URL(myURL);

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                String data = convertStreamToString(inputStream);

                setGame_id(Integer.parseInt(data));

                count++;
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

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
