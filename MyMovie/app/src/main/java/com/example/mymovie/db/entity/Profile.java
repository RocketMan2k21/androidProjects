package com.example.mymovie.db.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "profiles")
public class Profile extends BaseObservable {

    @ColumnInfo(name = "profile_login")
    private String login;
    @ColumnInfo(name = "profile_password")
    private String password;
    @ColumnInfo(name = "profile_email")
    private String email;

    @ColumnInfo(name = "profile_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Profile(String login, String password, String email, int id) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    @Ignore
    public Profile() {

    }

    @Bindable
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }
}
