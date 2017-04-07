package com.cremy.firebucket.firebase;

import android.support.annotation.NonNull;

import com.cremy.firebucket.external.ConfigInterface;
import com.cremy.firebucket.external.FirebucketConfig;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by remychantenay on 28/03/2017.
 */

public class FirebaseRemoteConfigHelper implements ConfigInterface {

    private FirebaseRemoteConfig instance;

    public FirebaseRemoteConfigHelper(FirebaseRemoteConfig instance) {
        this.instance = instance;
    }

    /**
     * Allows to set the default values before the server values are fetched
     */
    @Override
    public void setGlobalDefaultValues() {
        Map<String, Object> defaultValues = new HashMap<String, Object>();
        defaultValues.put(
                FirebucketConfig.MAINTENANCE.getKey(),
                FirebucketConfig.MAINTENANCE.getDefaultValue());
        instance.setDefaults(defaultValues);
    }

    /**
     * Allows to fetch the remote values
     * Please bear in mind that the values will be updated asynchronously
     * @param cacheTTL
     */
    @Override
    public void fetch(@NonNull long cacheTTL) {
        final Task<Void> fetch = instance.fetch(cacheTTL);
        fetch.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                instance.activateFetched();
            }
        });
    }

    @Override
    public String getString(@NonNull String key) {
        return instance.getString(key);
    }

    @Override
    public Double getDouble(@NonNull String key) {
        return instance.getDouble(key);
    }

    @Override
    public boolean getBoolean(@NonNull String key) {
        return instance.getBoolean(key);
    }
}