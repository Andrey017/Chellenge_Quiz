package com.example.chellenge_ksp.LocalDatabase;

public class User_game {
    int _id;
    int id_game;
    int user_id;
    String name;
    String surname;
    String email;
    int score;
    int round;
    int end_round;

    public User_game(){

    }

    public User_game(int id_game, int user_id, String name, String surname, String email, int score, int round, int end_round){
        this.id_game = id_game;
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.score = score;
        this.round = round;
        this.end_round = end_round;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getEnd_round() {
        return end_round;
    }

    public void setEnd_round(int end_round) {
        this.end_round = end_round;
    }
}
