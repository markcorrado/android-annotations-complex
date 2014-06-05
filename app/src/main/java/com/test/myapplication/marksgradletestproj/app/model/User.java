package com.test.myapplication.marksgradletestproj.app.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by mark on 5/22/14.
 */
public class User {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String userName;

    @DatabaseField
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
