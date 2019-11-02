package com.example.chellenge_ksp.LocalDatabase;

public class User {
    int _id;
    String name;
    String surname;
    String email;
    String password;

    public User(){

    }

    public User(int _id, String name, String surname, String email, String password){
        this._id = _id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
