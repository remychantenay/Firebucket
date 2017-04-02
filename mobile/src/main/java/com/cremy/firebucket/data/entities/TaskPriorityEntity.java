package com.cremy.firebucket.data.entities;

import com.google.gson.annotations.SerializedName;

public class TaskPriorityEntity {

    @SerializedName("id") private int id;
    @SerializedName("label") private String label;

    public TaskPriorityEntity() {
        // Default constructor required for calls to DataSnapshot.getValue(TaskModel.class)
    }

    public TaskPriorityEntity(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}