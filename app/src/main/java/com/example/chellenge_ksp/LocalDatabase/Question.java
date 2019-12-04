package com.example.chellenge_ksp.LocalDatabase;

public class Question {
    int _id;
    int id_db;
    String question_text;
    int right_answer;
    String answer_1;
    String answer_2;
    String answer_3;
    String answer_4;

    public Question(){

    }

    public Question(int id_db, String question_text, int right_answer, String answer_1, String answer_2, String answer_3, String answer_4){
        this.id_db = id_db;
        this.question_text = question_text;
        this.right_answer = right_answer;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getRightAnswer() {
        return right_answer;
    }

    public void setRightAnswer(int right_answer) {
        this.right_answer = right_answer;
    }

    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    public String getAnswer1() {
        return answer_1;
    }

    public void setAnswer1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer2() {
        return answer_2;
    }

    public void setAnswer2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer3() {
        return answer_3;
    }

    public void setAnswer3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer4() {
        return answer_4;
    }

    public void setAnswer4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public int getId_db() {
        return id_db;
    }

    public void setId_db(int id_db) {
        this.id_db = id_db;
    }
}
