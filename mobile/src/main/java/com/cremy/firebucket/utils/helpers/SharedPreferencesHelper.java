package com.cremy.firebucket.utils.helpers;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SharedPreferencesHelper {

    public static String KEY_USER_TASK_AT_LEAST_ONCE = "user_task_at_least_once";

    public static boolean contains(@NonNull SharedPreferences instance,
                            @NonNull String key) {
        return instance.contains(key);
    }

    public static void putLong(@NonNull SharedPreferences instance,
                        @NonNull String key,
                        long value) {
        SharedPreferences.Editor editor = instance.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(@NonNull SharedPreferences instance,
                        @NonNull String key,
                        long defaultValue) {
        return instance.getLong(key, defaultValue);
    }

    public static void putInteger(@NonNull SharedPreferences instance,
                           @NonNull String key,
                           int value) {
        SharedPreferences.Editor editor = instance.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInteger(@NonNull SharedPreferences instance,
                          @NonNull String key,
                          int defaultValue) {
        return instance.getInt(key, defaultValue);
    }

    public static void putString(@NonNull SharedPreferences instance,
                          @NonNull String key,
                          String value) {
        SharedPreferences.Editor editor = instance.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public static void putStringSync(SharedPreferences instance,
                              String key,
                              String value) {
        SharedPreferences.Editor editor = instance.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public static void putStrings(@NonNull SharedPreferences instance,
                           @NonNull String[] keys,
                           String[] values) {
        SharedPreferences.Editor editor = instance.edit();

        final int countElement = keys.length;
        for (int i = 0; i < countElement; i++) {
            editor.putString(keys[i], values[i]);
        }
        editor.apply();
    }

    public static void removePreference(@NonNull SharedPreferences instance,
                                 @NonNull String key) {
        SharedPreferences.Editor editor = instance.edit();

        editor.remove(key);
        editor.apply();
    }

    public static void putFloat(@NonNull SharedPreferences instance,
                         @NonNull String key,
                         float value) {
        SharedPreferences.Editor editor = instance.edit();

        editor.putFloat(key, value);
        editor.apply();
    }

    public String getString(@NonNull SharedPreferences instance,
                            @NonNull String key) {
        return instance.getString(key, null);
    }

    public static void putBoolean(@NonNull SharedPreferences instance,
                           @NonNull String key,
                           boolean value) {
        SharedPreferences.Editor editor = instance.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(@NonNull SharedPreferences instance,
                              @NonNull String key) {
        return instance.getBoolean(key, false);
    }

    public void removeAll(@NonNull SharedPreferences instance) {
        SharedPreferences.Editor editor = instance.edit();
        editor.clear();
        editor.commit();
    }
}
