package com.cremy.firebucket.firebase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cremy.firebucket.external.TaskReminderInterface;
import com.cremy.firebucket.firebase.services.ReminderService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;


public class FirebaseJobDispatcherHelper implements TaskReminderInterface {

    private FirebaseJobDispatcher jobDispatcherInstance;

    /**
     * Allows to create the {@link FirebaseJobDispatcher}
     * @param context
     * @return
     */
    public FirebaseJobDispatcher getJobDispatcherInstance(@NonNull Context context) {
        if (jobDispatcherInstance == null) {
            return new FirebaseJobDispatcher(new GooglePlayDriver(context));
        }

        return jobDispatcherInstance;
    }

    /**
     * Allows to set a task reminder using {@link Job}
     * @param context
     * @param taskId
     * @param startTimeFromNow from now, in seconds
     * @param bundle that will contains the information to work with in the job
     */
    @Override
    public void setTaskReminder(@NonNull Context context,
                                @NonNull String taskId,
                                @NonNull int startTimeFromNow,
                                @Nullable Bundle bundle) {
        FirebaseJobDispatcher jobDispatcher = getJobDispatcherInstance(context);

        Job job = jobDispatcher.newJobBuilder()
                .setService(ReminderService.class)
                .setTag(taskId)
                .setRecurring(false)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(startTimeFromNow, (startTimeFromNow+WINDOW)))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setExtras(bundle)
                .build();

        jobDispatcher.mustSchedule(job);
    }

    /**
     * Allows to cancel a reminder with a given taskId
     * @param context
     * @param taskId
     */
    @Override
    public void cancelTaskReminder(@NonNull Context context,
                                   @NonNull String taskId) {
        getJobDispatcherInstance(context).cancel(taskId);
    }

    /**
     * Allows to cancel all reminders
     * @param context
     */
    @Override
    public void cancelAllTaskReminders(@NonNull Context context) {
        getJobDispatcherInstance(context).cancelAll();
    }

}
