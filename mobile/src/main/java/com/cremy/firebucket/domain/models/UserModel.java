package com.cremy.firebucket.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by remychantenay on 25/03/2017.
 */

public class UserModel {

    private String uid = null;
    private String email = null;
    private String username = null;

    public UserModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
