package com.example.chellenge_ksp.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "quiz";

    private static final String TABLE_USER = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static final String TABLE_QUESTION = "question";
    private static final String KEY_QUESTION_ID = "id";
    private static final String KEY_QUESTION_ID_DB = "id_db";
    private static final String KEY_QUESTION_TEXT = "question_text";
    private static final String KEY_RIGHT_ANSWER = "right_answer";
    private static final String KEY_ANSWER_1 = "answer_1";
    private static final String KEY_ANSWER_2 = "answer_2";
    private static final String KEY_ANSWER_3 = "answer_3";
    private static final String KEY_ANSWER_4 = "answer_4";

    private static final String TABLE_USER_GAME = "user_game";
    private static final String KEY_ID_GAME_LDB = "id_game_ldb";
    private static final String KEY_ID_GAME = "id_game";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_NAME_USER = "name_user";
    private static final String KEY_SURNAME_USER = "surname_user";
    private static final String KEY_EMAIL_USER = "email_user";
    private static final String KEY_SCORE_USER = "score_user";
    private static final String KEY_ROUND_USER = "round_user";
    private static final String KEY_END_ROUND_USER = "end_round";

    private static final String TABLE_GAME = "game";
    private static final String KEY_GAME_ID_LDB = "id_game_ldb";
    private static final String KEY_GAME_ID = "game_id";
    private static final String KEY_USER_WIN = "user_win";
    private static final String KEY_QUESTIONS = "questions";
    private static final String KEY_END = "ends";
    private static final String KEY_GAME_NAME = "game_name";
    private static final String KEY_GAME_SCORE = "game_score";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " text," + KEY_SURNAME + " text," + KEY_EMAIL + " text," + KEY_PASSWORD + " text)";

    private static final String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + " INTEGER PRIMARY KEY," + KEY_QUESTION_ID_DB + " INTEGER," + KEY_QUESTION_TEXT + " text," + KEY_RIGHT_ANSWER + " INTEGER," + KEY_ANSWER_1 + " INTEGER," + KEY_ANSWER_2 + " INTEGER," + KEY_ANSWER_3 + " text," + KEY_ANSWER_4 + " text)";

    private static final String CREATE_USER_GAME = "CREATE TABLE " + TABLE_USER_GAME + "(" + KEY_ID_GAME_LDB + " INTEGER PRIMARY KEY," + KEY_ID_GAME + " INTEGER," + KEY_ID_USER + " INTEGER," + KEY_NAME_USER + " text," + KEY_SURNAME_USER + " text," + KEY_EMAIL_USER + " text,"  + KEY_SCORE_USER + " INTEGER," + KEY_ROUND_USER + " INTGER," + KEY_END_ROUND_USER + " INTGER)";

    private static final String CREATE_GAME = "CREATE TABLE " + TABLE_GAME + "(" + KEY_GAME_ID_LDB + " INTEGER PRIMARY KEY," + KEY_GAME_ID + " INTEGER," + KEY_USER_WIN + " INTEGER," + KEY_QUESTIONS + " text," + KEY_END + " INTEGER," + KEY_GAME_NAME + " text,"  + KEY_GAME_SCORE + " INTEGER)";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_USER_GAME);
        db.execSQL(CREATE_GAME);
    }

    @Override
    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTION_ID_DB, question.getId_db());
        values.put(KEY_QUESTION_TEXT, question.getQuestionText());
        values.put(KEY_RIGHT_ANSWER, question.getRightAnswer());
        values.put(KEY_ANSWER_1, question.getAnswer1());
        values.put(KEY_ANSWER_2, question.getAnswer2());
        values.put(KEY_ANSWER_3, question.getAnswer3());
        values.put(KEY_ANSWER_4, question.getAnswer4());

        db.insert(TABLE_QUESTION, null, values);
        db.close();
    }

    @Override
    public Question getQuestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTION, new String[] {KEY_QUESTION_ID, KEY_QUESTION_TEXT, KEY_RIGHT_ANSWER, KEY_ANSWER_1, KEY_ANSWER_2, KEY_ANSWER_3, KEY_ANSWER_4}, KEY_QUESTION_ID + "=?", new String[] {String.valueOf(id)}, null, null,null, null);

        if (cursor != null){
            cursor.moveToNext();
        }

        Question question = new Question(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

        return question;
    }

    @Override
    public List<Question> getAllQuestion() {
        List<Question> questionList = new ArrayList<Question>();
        String selectQuery = "SELECT * FROM " + TABLE_QUESTION;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();

                question.set_id(Integer.parseInt(cursor.getString(0)));
                question.setId_db(Integer.parseInt(cursor.getString(1)));
                question.setQuestionText(cursor.getString(2));
                question.setRightAnswer(Integer.parseInt(cursor.getString(3)));
                question.setAnswer1(cursor.getString(4));
                question.setAnswer2(cursor.getString(5));
                question.setAnswer3(cursor.getString(6));
                question.setAnswer4(cursor.getString(7));

                questionList.add(question);
            }while (cursor.moveToNext());
        }
        return questionList;
    }

    @Override
    public void deleteAllQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();

            db.delete(TABLE_QUESTION, null, null);
        db.close();
    }

    @Override
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, user.get_id());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    @Override
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_ID, KEY_NAME, KEY_SURNAME, KEY_EMAIL, KEY_PASSWORD}, KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null,null, null);

        if (cursor != null){
            cursor.moveToNext();
        }

        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                User user = new User();

                user.set_id(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setSurname(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPassword(cursor.getString(4));

                userList.add(user);
            }while (cursor.moveToNext());
        }

        return userList;
    }

    @Override
    public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    @Override
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        return db.update(TABLE_USER, values, KEY_ID + "=?", new String[] {String.valueOf(user.get_id())});
    }

    @Override
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, KEY_ID + "=?", new String[] {String.valueOf(user.get_id())});
        db.close();
    }

    @Override
    public void deleteAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, null, null);
        db.close();
    }

    public void addUser_Game(User_game user_game){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID_GAME, user_game.getId_game());
        values.put(KEY_ID_USER, user_game.getUser_id());
        values.put(KEY_NAME_USER, user_game.getName());
        values.put(KEY_SURNAME_USER, user_game.getSurname());
        values.put(KEY_EMAIL_USER, user_game.getEmail());
        values.put(KEY_SCORE_USER, user_game.getScore());
        values.put(KEY_ROUND_USER, user_game.getRound());
        values.put(KEY_END_ROUND_USER, user_game.getEnd_round());

        db.insert(TABLE_USER_GAME, null, values);
        db.close();
    }

    @Override
    public List<User_game> getAllUser_game() {
        List<User_game> user_gameList = new ArrayList<User_game>();
        String selectQuery = "SELECT * FROM " + TABLE_USER_GAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                User_game user_game = new User_game();

                user_game.set_id(Integer.parseInt(cursor.getString(0)));
                user_game.setId_game(Integer.parseInt(cursor.getString(1)));
                user_game.setUser_id(Integer.parseInt(cursor.getString(2)));
                user_game.setName(cursor.getString(3));
                user_game.setSurname(cursor.getString(4));
                user_game.setEmail(cursor.getString(5));
                user_game.setScore(Integer.parseInt(cursor.getString(6)));
                user_game.setRound(Integer.parseInt(cursor.getString(7)));
                user_game.setEnd_round(Integer.parseInt(cursor.getString(8)));

                user_gameList.add(user_game);
            }while (cursor.moveToNext());
        }

        return user_gameList;
    }

    @Override
    public int getUser_game_count() {
        String countQuery = "SELECT * FROM " + TABLE_USER_GAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    @Override
    public void deleteAllUSer_game() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER_GAME, null, null);
        db.close();
    }

    @Override
    public void addGame(GameDB gameDB) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_GAME_ID, gameDB.getGame_id());
        values.put(KEY_USER_WIN, gameDB.getUser_win());
        values.put(KEY_QUESTIONS, gameDB.getQuestions());
        values.put(KEY_END, gameDB.getEnd());
        values.put(KEY_GAME_NAME, gameDB.getName());
        values.put(KEY_GAME_SCORE, gameDB.getScore());

        db.insert(TABLE_GAME, null, values);
        db.close();
    }

    @Override
    public List<GameDB> getAllGameDB() {
        List<GameDB> gameDBList = new ArrayList<GameDB>();
        String selectQuery = "SELECT * FROM " + TABLE_GAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                GameDB gameDB = new GameDB();

                gameDB.set_id(Integer.parseInt(cursor.getString(0)));
                gameDB.setGame_id(Integer.parseInt(cursor.getString(1)));
                gameDB.setUser_win(Integer.parseInt(cursor.getString(2)));
                gameDB.setQuestions(cursor.getString(3));
                gameDB.setEnd(Integer.parseInt(cursor.getString(4)));
                gameDB.setName(cursor.getString(5));
                gameDB.setScore(Integer.parseInt(cursor.getString(6)));

                gameDBList.add(gameDB);
            }while (cursor.moveToNext());
        }

        return gameDBList;
    }

    @Override
    public int getGameDBCount() {
        String countQuery = "SELECT * FROM " + TABLE_GAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    @Override
    public void deleteAllGameDB() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_GAME, null, null);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);

        onCreate(db);
    }
}
