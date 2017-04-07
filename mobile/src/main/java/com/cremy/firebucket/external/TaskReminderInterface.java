package com.cremy.firebucket.external;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.firebase.jobdispatcher.Job;

/**
 * Created by remychantenay on 06/04/2017.
 */

public interface TaskReminderInterface {

    public static int WINDOW = 60; // in seconds

    /**
     * Allows to set a task reminder using {@link Job}
     * @param context
     * @param taskId
     * @param startTimeFromNow from now, in seconds
     * @param bundle that will contains the information to work with in the job
     */
    void setTaskReminder(@NonNull Context context,
                         @NonNull String taskId,
                         @NonNull int startTimeFromNow,
                         @Nullable Bundle bundle);

    /**
     * Allows to cancel a reminder with a given taskId
     * @param context
     * @param taskId
     */
    void cancelTaskReminder(@NonNull Context context,
                            @NonNull String taskId);

    /**
     * Allows to cancel all reminders
     * @param context
     */
    void cancelAllTaskReminders(@NonNull Context context);
}
