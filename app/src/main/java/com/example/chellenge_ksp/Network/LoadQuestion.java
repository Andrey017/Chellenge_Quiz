package com.example.chellenge_ksp.Network;

import android.os.AsyncTask;

import com.example.chellenge_ksp.LocalDatabase.DatabaseHandler;
import com.example.chellenge_ksp.LocalDatabase.Question;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LoadQuestion extends AsyncTask<Void, Void, Void> {
    private String ques;
    private int count;

    private int id;
    private String question;
    private int answer_true;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    DatabaseHandler db;

    public LoadQuestion(String ques, DatabaseHandler db){
        this.ques = ques;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String myURL = "http://94.141.168.185:8009/loadques?ques=" + ques;

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
                    question = jsonObject.getString("question");
                    answer_true = Integer.parseInt(jsonObject.getString("answer_true"));
                    answer1 = jsonObject.getString("answer1");
                    answer2 = jsonObject.getString("answer2");
                    answer3 = jsonObject.getString("answer3");
                    answer4 = jsonObject.getString("answer4");

                    db.addQuestion(new Question(id, question, answer_true, answer1, answer2, answer3, answer4));
                }

                count++;

                System.out.println("Load question ....");

                List<Question> questionList = db.getAllQuestion();

                for (Question q : questionList){
                    String log = "" + q.get_id() + " " + q.getId_db() + " " + q.getQuestionText() + " " + q.getRightAnswer() + " " + q.getAnswer1() + " " + q.getAnswer2() + " " + q.getAnswer3() + " " + q.getAnswer4();
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
