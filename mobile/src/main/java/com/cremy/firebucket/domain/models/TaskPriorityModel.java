package com.cremy.firebucket.domain.models;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import com.cremy.firebucket.R;

public class TaskPriorityModel {

    /**
     * Nope, I don't want to use Enums :)
     * https://developer.android.com/training/articles/memory.html#Overhead
     */
    public final static int PRIORITY_LOW_ID = 0;
    public final static int PRIORITY_NORMAL_ID = 1;
    public final static int PRIORITY_HIGH_ID = 2;
    public final static int PRIORITY_CRUCIAL_ID = 3;

    public final static String PRIORITY_NORMAL_LABEL = "Normal";

    private int id = PRIORITY_NORMAL_ID;
    private String label = PRIORITY_NORMAL_LABEL;

    public TaskPriorityModel() {

    }

    public TaskPriorityModel(int id, String label) {
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

    public int getColor(Context context) {
        switch (this.id) {
            case PRIORITY_LOW_ID:
                return ContextCompat.getColor(context, R.color.taskPriorityLow);
            case PRIORITY_HIGH_ID:
                return ContextCompat.getColor(context, R.color.taskPriorityHigh);
            case PRIORITY_CRUCIAL_ID:
                return ContextCompat.getColor(context, R.color.taskPriorityCrucial);
            default:
                return ContextCompat.getColor(context, R.color.taskPriorityNormal);
        }
    }

    public static String getResourceLabel(Context context, int idPriority) {
        String[] labels = context.getResources().getStringArray(R.array.task_priority_labels);
        return labels[idPriority];
    }
}