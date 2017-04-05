package com.cremy.firebucket.config;

import android.support.annotation.NonNull;

/**
 * Created by remychantenay on 05/04/2017.
 */

public interface ConfigInterface {

    void setGlobalDefaultValues();

    void fetch(@NonNull long cacheTTL);

    String getString(@NonNull String key);
    Double getDouble(@NonNull String key);
    boolean getBoolean(@NonNull String key);
}
