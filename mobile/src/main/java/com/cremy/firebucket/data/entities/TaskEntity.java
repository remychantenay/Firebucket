package com.cremy.firebucket.data.entities;

import android.support.annotation.Keep;

import com.cremy.firebucket.utils.CustomDateUtils;
import com.google.gson.annotations.SerializedName;

@Keep
public class TaskEntity {

    @SerializedName("id") private String id;
    @SerializedName("title") private String title;
    @SerializedName("priority") private TaskPriorityEntity priority = new TaskPriorityEntity();
    @SerializedName("deadline") private String deadline = CustomDateUtils.getNow();
    @SerializedName("deadline_ms") private long deadlineMs;
    @SerializedName("tag") private String tag = null;

    public TaskEntity() {
        // Default constructor required for calls to DataSnapshot.getValue(TaskModel.class)
    }

    public TaskEntity(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskPriorityEntity getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityEntity priority) {
        this.priority = priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public long getDeadlineMs() {
        return deadlineMs;
    }

    public void setDeadlineMs(long deadlineMs) {
        this.deadlineMs = deadlineMs;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}