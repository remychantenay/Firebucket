package com.cremy.firebucket.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by remychantenay on 28/03/2017.
 */

public class FirebaseAnalyticsHelper {

    public final static String PARAM_USER_UID = "user_uid";
    public final static String PARAM_MESSAGE = "message";

    public final static String VIEW_ONBOARDING = "view_onboarding";
    public final static String VIEW_LOGIN = "view_login";
    public final static String VIEW_REGISTRATION = "view_register";
    public final static String VIEW_BUCKET = "view_bucket";
    public final static String VIEW_CREATE_TASK = "view_create_task";

    public static void trackLoginSuccess(@NonNull FirebaseAnalytics instance,
                                         @NonNull Bundle bundle) {
        instance.logEvent("login", bundle);
    }

    public static void trackLoginFailure(@NonNull FirebaseAnalytics instance,
                                         @NonNull Bundle bundle) {
        instance.logEvent("login_fail", bundle);
    }

    public static void trackRegisterSuccess(@NonNull FirebaseAnalytics instance,
                                         @NonNull Bundle bundle) {
        instance.logEvent("register", bundle);
    }

    public static void trackRegisterFailure(@NonNull FirebaseAnalytics instance,
                                         @NonNull Bundle bundle) {
        instance.logEvent("register_fail", bundle);
    }

    public static void trackGetTagListSuccess(@NonNull FirebaseAnalytics instance,
                                            @NonNull Bundle bundle) {
        instance.logEvent("get_taglist", bundle);
    }

    public static void trackGetTagListFailure(@NonNull FirebaseAnalytics instance,
                                            @NonNull Bundle bundle) {
        instance.logEvent("get_taglist_fail", bundle);
    }

    public static void trackCreateTaskSuccess(@NonNull FirebaseAnalytics instance,
                                              @NonNull Bundle bundle) {
        instance.logEvent("task_create", bundle);
    }

    public static void trackCreateTaskFailure(@NonNull FirebaseAnalytics instance,
                                              @NonNull Bundle bundle) {
        instance.logEvent("task_create_fail", bundle);
    }

    public static void trackGetBucketSuccess(@NonNull FirebaseAnalytics instance,
                                              @NonNull Bundle bundle) {
        instance.logEvent("get_bucket", bundle);
    }

    public static void trackGetBucketFailure(@NonNull FirebaseAnalytics instance,
                                              @NonNull Bundle bundle) {
        instance.logEvent("get_bucket_fail", bundle);
    }

    public static void trackDeleteTaskSuccess(@NonNull FirebaseAnalytics instance,
                                             @NonNull Bundle bundle) {
        instance.logEvent("task_delete", bundle);
    }

    public static void trackDeleteFailure(@NonNull FirebaseAnalytics instance,
                                             @NonNull Bundle bundle) {
        instance.logEvent("task_delete_fail", bundle);
    }

    public static void trackPageView(@NonNull FirebaseAnalytics instance,
                                     @NonNull String view) {
        instance.logEvent(view, null);
    }
}
