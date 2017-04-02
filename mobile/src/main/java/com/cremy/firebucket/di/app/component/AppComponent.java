package com.cremy.firebucket.di.app.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.cremy.firebucket.App;
import com.cremy.firebucket.di.app.module.AppModule;
import com.cremy.firebucket.di.scope.ApplicationScope;
import com.cremy.firebucket.utils.rx.RxEventBus;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;

import dagger.Component;

/**
 * The role of the component is to inject the dependencies in the specified targets
 * Targets must ALL be added here
 */
@ApplicationScope
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {

    // Allows to inject into the App
    void inject(App app);

    Context context();
    App app();
    RxEventBus<Object> rxEventBus();
    Gson gson();
    FirebaseDatabase firebaseDatabase();
    FirebaseAuth firebaseAuth();
    FirebaseAnalytics firebaseAnalytics();
    FirebaseRemoteConfig firebaseRemoteConfig();
    SharedPreferences sharedPreferences();
}
