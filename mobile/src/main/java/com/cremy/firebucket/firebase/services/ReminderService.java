package com.cremy.firebucket.firebase.services;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.cremy.firebucket.R;
import com.cremy.firebucket.presentation.ui.activities.BucketActivity;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by remychantenay on 06/04/2017.
 */

public class ReminderService extends JobService {
    public static String TAG = "ReminderService";
    public static String NOTIFICATION_GROUP_KEY = "task_reminders";

    public static String BUNDLE_KEY_TASK_TITLE = "task_title";
    public static String BUNDLE_KEY_TASK_PRIORITY = "task_priority";
    public static String BUNDLE_KEY_TASK_TAG = "task_tag";

    public static int REQUEST_CODE = 42;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob() called.");

        final String taskTitle = job.getExtras().getString(BUNDLE_KEY_TASK_TITLE);
        final String taskPriority = job.getExtras().getString(BUNDLE_KEY_TASK_PRIORITY);
        final String taskTag = job.getExtras().getString(BUNDLE_KEY_TASK_TAG);
        final String taskDetails = buildNotificationContent(taskPriority, taskTag);

        Intent intent = BucketActivity.getIntentForNotification(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification_task_reminder)
                .setContentTitle(taskTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(taskDetails))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setGroup(NOTIFICATION_GROUP_KEY)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) SystemClock.currentThreadTimeMillis(), builder.build());
        return false; // Is there still work going
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob() called.");
        return false; // Should this job be retried
    }

    /**
     * Allows to build the content for Task reminder notification
     * @param taskPriority
     * @param taskTag
     * @return
     */
    private String buildNotificationContent(String taskPriority, String taskTag) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Priority: ");
        stringBuilder.append(taskPriority);
        stringBuilder.append("\n");
        stringBuilder.append("Tag: ");
        stringBuilder.append(taskTag);

        return stringBuilder.toString();
    }
}
