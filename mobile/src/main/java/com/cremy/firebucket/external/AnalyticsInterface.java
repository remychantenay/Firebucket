package com.cremy.firebucket.external;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by remychantenay on 28/03/2017.
 */

public interface AnalyticsInterface {

    public final static String PARAM_USER_UID = "user_uid";
    public final static String PARAM_MESSAGE = "message";

    public final static String VIEW_ONBOARDING = "view_onboarding";
    public final static String VIEW_LOGIN = "view_login";
    public final static String VIEW_REGISTRATION = "view_register";
    public final static String VIEW_BUCKET = "view_bucket";
    public final static String VIEW_CREATE_TASK = "view_create_task";

    void trackLoginSuccess(@NonNull Bundle bundle);

    void trackLoginFailure(@NonNull Bundle bundle);

    void trackRegisterSuccess(@NonNull Bundle bundle);

    void trackRegisterFailure(@NonNull Bundle bundle);

    void trackGetTagListSuccess(@NonNull Bundle bundle);

    void trackGetTagListFailure(@NonNull Bundle bundle);

    void trackCreateTaskSuccess(@NonNull Bundle bundle);

    void trackCreateTaskFailure(@NonNull Bundle bundle);

    void trackGetBucketSuccess(@NonNull Bundle bundle);

    void trackGetBucketFailure(@NonNull Bundle bundle);

    void trackDeleteTaskSuccess(@NonNull Bundle bundle);

    void trackDeleteFailure(@NonNull Bundle bundle);

    void trackPageView(@NonNull String view);
}
