package com.cremy.firebucket.domain.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;

public final class BucketModel {

    private HashMap<String, TaskModel> tasks = new HashMap<>();

    public BucketModel() {

    }

    public HashMap<String, TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<String, TaskModel> tasks) {
        this.tasks = tasks;
    }

    public boolean isEmpty() {
        return (this.tasks == null || this.tasks.isEmpty());
    }

    public ArrayList<TaskModel> toDisplayedList() {

        // We sort the list provided by Firebase (ordered by push generated id)
        ArrayList<TaskModel> orderedList = new ArrayList<>(this.tasks.values());
        Collections.sort(orderedList);
        return orderedList;
    }
}