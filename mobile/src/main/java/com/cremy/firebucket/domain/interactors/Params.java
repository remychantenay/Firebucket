package com.cremy.firebucket.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cremy.firebucket.domain.interactors.base.BaseUseCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Class backed by a Map, used to pass parameters to {@link BaseUseCase} instances.
 */
public class Params {
  public static final Params EMPTY = Params.create();

  private final Map<String, Object> parameters = new HashMap<>();

  private Params() {}

  public static Params create() {
    return new Params();
  }

  public void putInt(@NonNull String key,
                     @NonNull int value) {
    parameters.put(key, value);
  }

  public int getInt(@Nullable String key,
                    @Nullable int defaultValue) {
    final Object object = parameters.get(key);
    if (object == null) {
      return defaultValue;
    }
    try {
      return (int) object;
    } catch (ClassCastException e) {
      return defaultValue;
    }
  }

  public void putString(@NonNull String key,
                        @NonNull String value) {
    parameters.put(key, value);
  }

  public String getString(@NonNull String key,
                          @Nullable String defaultValue) {
    final Object object = parameters.get(key);
    if (object == null) {
      return defaultValue;
    }
    try {
      return (String) object;
    } catch (ClassCastException e) {
      return defaultValue;
    }
  }

  public void putLong(@NonNull String key,
                      @NonNull long value) {
    parameters.put(key, value);
  }

  public long getLong(@NonNull String key,
                        @Nullable long defaultValue) {
    final Object object = parameters.get(key);
    if (object == null) {
      return defaultValue;
    }
    try {
      return (long) object;
    } catch (ClassCastException e) {
      return defaultValue;
    }
  }
}