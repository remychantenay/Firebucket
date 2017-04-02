package com.cremy.firebucket.domain.models;

import android.content.Context;

import com.cremy.firebucket.R;
import com.cremy.firebucket.utils.CustomDateUtils;
import com.google.firebase.database.Exclude;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TaskModel implements Comparable<TaskModel> {

    private String id;
    private String title;
    private TaskPriorityModel priority = new TaskPriorityModel();
    private String deadline = CustomDateUtils.getNow();
    private long deadlineMs;
    private String tag = null;

    public TaskModel(String title) {
        this.title = title;
    }

    public TaskModel(long deadlineMs) {
        this.deadlineMs = deadlineMs;
    }

    @Override
    public int compareTo(TaskModel another) {
        return this.deadlineMs < another.getDeadlineMs() ? -1 : (this.deadlineMs == another.getDeadlineMs() ? 0 : 1);
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

    public TaskPriorityModel getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityModel priority) {
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