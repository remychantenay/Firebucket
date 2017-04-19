package com.cremy.firebucket.presentation.presenters;

import android.app.DatePickerDialog;
import android.speech.RecognitionListener;

import com.cremy.firebucket.domain.models.TagListModel;
import com.cremy.firebucket.domain.models.TaskModel;
import com.cremy.firebucket.presentation.ui.base.BaseMvpView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface CreateTaskMVP {

    interface View extends BaseMvpView, DatePickerDialog.OnDateSetListener, RecognitionListener {
        void onSuccess();
        void onFailure();

        void showMessageInvalidTaskTitle();

        void showTagList(String[] tags);
        void showTagListError();

        void initDeadlineDatePicker();
        void initReminderTimePicker();
        void startVoiceRecognition();

        boolean isReminderSet();
        Date getReminderDate();
    }

    interface Presenter {
        void getTagList();
        void onGetTagListSuccess(TagListModel tagListModel);
        void onGetTagListFailure(Throwable e);

        void onGetTagListSuccessTracking();
        void onGetTagListFailureTracking(Throwable e);

        void createTask(String title,
                        Calendar deadline,
                        String tag,
                        int idPriority);
        void onCreateTaskSuccess(TaskModel taskModel);
        void onCreateTaskFailure(Throwable e);

        void onCreateTaskSuccessTracking(TaskModel taskModel);
        void onCreateTaskFailureTracking(Throwable e);

        void setReminder(TaskModel taskModel);
    }
}
