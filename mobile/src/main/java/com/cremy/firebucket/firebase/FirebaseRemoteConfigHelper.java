package com.cremy.firebucket.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by remychantenay on 28/03/2017.
 */

public class FirebaseRemoteConfigHelper {

    public enum FirebucketRemoteConfig {
        MAINTENANCE("is_maintenance", "no");

        private String key;
        private String defaultValue;

        FirebucketRemoteConfig(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    /**
     * Allows to set the default values before the server values are fetched
     */
    public static void setGlobalDefaultValues(@NonNull FirebaseRemoteConfig instance) {
        Map<String, Object> defaultValues = new HashMap<String, Object>();
        defaultValues.put(
                FirebucketRemoteConfig.MAINTENANCE.getKey(),
                FirebucketRemoteConfig.MAINTENANCE.getDefaultValue());
        instance.setDefaults(defaultValues);
    }


    /**
     * Allows to fetch the remote values
     * Please bear in mind that the values will be updated asynchronously
     * @param cacheTTL
     */
    public static void fetch(@NonNull final FirebaseRemoteConfig instance,
                             @NonNull long cacheTTL) {
        final Task<Void> fetch = instance.fetch(cacheTTL);
        fetch.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                instance.activateFetched();
            }
        });
    }

    public static String getString(@NonNull FirebaseRemoteConfig instance,
                                   @NonNull FirebucketRemoteConfig firebucketRemoteConfig) {
        return instance.getString(firebucketRemoteConfig.getKey());
    }

    public static Double getDouble(@NonNull FirebaseRemoteConfig instance,
                                   @NonNull FirebucketRemoteConfig firebucketRemoteConfig) {
        return instance.getDouble(firebucketRemoteConfig.getKey());
    }

    public static boolean getBoolean(@NonNull FirebaseRemoteConfig instance,
                                    @NonNull FirebucketRemoteConfig firebucketRemoteConfig) {
        return instance.getBoolean(firebucketRemoteConfig.getKey());
    }
}