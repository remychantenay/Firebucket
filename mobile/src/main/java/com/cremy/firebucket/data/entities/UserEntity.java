package com.cremy.firebucket.data.entities;

import android.support.annotation.Keep;

import com.cremy.firebucket.domain.interactors.Params;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

/**
 * Created by remychantenay on 23/02/2017.
 */
@Keep
public class UserEntity {

    @SerializedName("uid") private String uid = null;
    @SerializedName("email") private String email = null;
    @SerializedName("password") private String password = null;
    @SerializedName("username") private String username = null;

    public UserEntity() {
        /*
         * Default constructor required for calls to DataSnapshot.getValue(UserEntity.class)
         */
    }

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
