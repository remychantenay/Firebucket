package com.cremy.firebucket.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.firebucket.App;
import com.cremy.firebucket.analytics.AnalyticsHelper;
import com.cremy.firebucket.di.app.component.AppComponent;
import com.cremy.firebucket.di.scope.ApplicationScope;
import com.cremy.firebucket.firebase.FirebaseAnalyticsHelper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * This module is in charge of providing dependencies to the {@link AppComponent}
 * Created by remychantenay on 23/05/2016.
 */
@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope
    public App provideApplication() {
        return app;
    }

    @Provides
    @ApplicationScope
    public Context provideContext() {
        return app;
    }

    @Provides
    @ApplicationScope
    public Gson provideGSON() {
        return new Gson();
    }

    @Provides
    @ApplicationScope
    public FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        return firebaseDatabase;
    }

    @Provides
    @ApplicationScope
    public FirebaseAuth provideFirebaseAuth() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    @Provides
    @ApplicationScope
    public AnalyticsHelper provideAnalyticsHelper(Context context) {
        AnalyticsHelper AnalyticsHelper = new FirebaseAnalyticsHelper(FirebaseAnalytics.getInstance(context));
        return AnalyticsHelper;
    }

    @Provides
    @ApplicationScope
    public FirebaseRemoteConfig provideFirebaseRemoteConfig() {
        return FirebaseRemoteConfig.getInstance();
    }

    @Provides
    @ApplicationScope
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
