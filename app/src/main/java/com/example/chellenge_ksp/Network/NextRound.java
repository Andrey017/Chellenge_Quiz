package com.example.chellenge_ksp.Network;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NextRound extends AsyncTask<Void, Void, Void> {
    private int game_id;
    private int user_id;
    private int count = 0;

    public NextRound(int game_id, int user_id){
        this.game_id = game_id;
        this.user_id = user_id;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String myURL = "http://94.141.168.185:8009/nextround?game_id=" + game_id + "&user_id=" + user_id;

            InputStream is = null;
            byte[] data = null;
            String resultString;

            try {
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

                    setCount(1);
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

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
