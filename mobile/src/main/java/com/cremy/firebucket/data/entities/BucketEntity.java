package com.cremy.firebucket.data.entities;

import android.support.annotation.Keep;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

@Keep
public final class BucketEntity {

    @SerializedName("tasks") private HashMap<String, TaskEntity> tasks = null;

    public BucketEntity() {
        // Default constructor required for calls to DataSnapshot.getValue(TaskModel.class)
    }

    public HashMap<String, TaskEntity> getTasks() {
        return tasks;
    }

    @Exclude
    public boolean isEmpty() {
        return (this.tasks == null || this.tasks.isEmpty());
    }

    public void setTasks(HashMap<String, TaskEntity> tasks) {
        this.tasks = tasks;
    }
}