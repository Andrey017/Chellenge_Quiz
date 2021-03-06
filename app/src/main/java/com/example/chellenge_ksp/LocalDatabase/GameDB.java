package com.example.chellenge_ksp.LocalDatabase;

public class GameDB {
    int _id;
    int game_id;
    int user_win;
    String questions;
    int end;
    String name;
    int score;

    public GameDB(){

    }

    public GameDB(int game_id, int user_win, String questions, int end, String name, int score){
        this.game_id = game_id;
        this.user_win = user_win;
        this.questions = questions;
        this.end = end;
        this.name = name;
        this.score = score;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getUser_win() {
        return user_win;
    }

    public void setUser_win(int user_win) {
        this.user_win = user_win;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
