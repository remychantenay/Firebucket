package com.cremy.firebucket.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cremy.firebucket.external.AnalyticsInterface;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by remychantenay on 28/03/2017.
 */

public class FirebaseAnalyticsHelper implements AnalyticsInterface {

    private FirebaseAnalytics instance;

    public FirebaseAnalyticsHelper(FirebaseAnalytics instance) {
        this.instance = instance;
    }

    @Override
    public void trackLoginSuccess(@NonNull Bundle bundle) {
        instance.logEvent("login", bundle);
    }

    @Override
    public void trackLoginFailure(@NonNull Bundle bundle) {
        instance.logEvent("login_fail", bundle);
    }

    @Override
    public void trackRegisterSuccess(@NonNull Bundle bundle) {
        instance.logEvent("register", bundle);
    }

    @Override
    public void trackRegisterFailure(@NonNull Bundle bundle) {
        instance.logEvent("register_fail", bundle);
    }

    @Override
    public void trackGetTagListSuccess(@NonNull Bundle bundle) {
        instance.logEvent("get_taglist", bundle);
    }

    @Override
    public void trackGetTagListFailure(@NonNull Bundle bundle) {
        instance.logEvent("get_taglist_fail", bundle);
    }

    @Override
    public void trackCreateTaskSuccess(@NonNull Bundle bundle) {
        instance.logEvent("task_create", bundle);
    }

    @Override
    public void trackCreateTaskFailure(@NonNull Bundle bundle) {
        instance.logEvent("task_create_fail", bundle);
    }

    @Override
    public void trackGetBucketSuccess(@NonNull Bundle bundle) {
        instance.logEvent("get_bucket", bundle);
    }

    @Override
    public void trackGetBucketFailure(@NonNull Bundle bundle) {
        instance.logEvent("get_bucket_fail", bundle);
    }

    @Override
    public void trackDeleteTaskSuccess(@NonNull Bundle bundle) {
        instance.logEvent("task_delete", bundle);
    }

    @Override
    public void trackDeleteFailure(@NonNull Bundle bundle) {
        instance.logEvent("task_delete_fail", bundle);
    }

    @Override
    public void trackPageView(@NonNull String view) {
        instance.logEvent(view, null);
    }
}
