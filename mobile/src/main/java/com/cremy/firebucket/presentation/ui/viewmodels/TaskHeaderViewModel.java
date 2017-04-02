package com.cremy.firebucket.presentation.ui.viewmodels;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cremy.firebucket.domain.models.TaskModel;
import com.cremy.firebucket.presentation.ui.base.BaseViewModel;
import com.cremy.firebucket.utils.CustomDateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Note: We use this ViewModel with Android Data Binding
 */
public class TaskHeaderViewModel extends BaseViewModel {

    private TaskModel model;

    public TaskHeaderViewModel(TaskModel model) {
        super();
        this.model = model;
    }

    public String getDisplayedDate(@NonNull Context context) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(model.getDeadlineMs()));

        return CustomDateUtils.getDisplayDate(context, calendar);
    }
}