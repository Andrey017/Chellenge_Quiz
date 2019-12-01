package com.example.chellenge_ksp.LocalDatabase;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

interface IDatabaseHandler {
    void onCreate(SQLiteDatabase db);

    //методы для пользователя
    public void addUser(User user);
    public User getUser(int id);
    public List<User> getAllUser();
    public int getUserCount();
    public int updateUser(User user);
    public void deleteUser(User user);
    public void deleteAllUser();

    //Методы для вопроса
    public void addQuestion(Question question);
    public Question getQuestion(int id);
    public List<Question> getAllQuestion();
    public void deleteAllQuestion();

    //Методы для user_game
    public void addUser_Game(User_game user_game);
    public List<User_game> getAllUser_game();
    public int getUser_game_count();
    public void deleteAllUSer_game();

    //Методы для Game
    public void addGame(GameDB gameDB);
    public List<GameDB> getAllGameDB();
    public int getGameDBCount();
    public void deleteAllGameDB();
}
