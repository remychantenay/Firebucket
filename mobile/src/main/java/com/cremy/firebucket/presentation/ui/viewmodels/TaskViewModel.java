package com.cremy.firebucket.presentation.ui.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;

import com.cremy.firebucket.R;
import com.cremy.firebucket.domain.models.TaskModel;


public class TaskViewModel extends BaseObservable {

    private Context context;
    private TaskModel model;

    public TaskViewModel(Context context,
                         TaskModel model) {
        this.context = context;
        this.model = model;
    }

    public String getTaskTitle() {
        return this.model.getTitle();
    }

    public int getTaskPriorityColor() {
        return this.model.getPriority().getColor(this.context);
    }

    public String getTaskPriorityLabel() {
        return this.model.getPriority().getLabel();
    }

    public String getTaskTag() {
        return this.model.getTag();
    }

    public boolean hasTag() {
        return this.model.getTag() != null;
    }
}