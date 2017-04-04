package com.cremy.firebucket.di.user;

import android.support.v4.app.Fragment;

import com.cremy.firebucket.analytics.AnalyticsHelper;
import com.cremy.firebucket.data.repositories.UserRepository;
import com.cremy.firebucket.data.repositories.datasource.UserDataSourceRemote;
import com.cremy.firebucket.domain.interactors.user.CheckUserUseCase;
import com.cremy.firebucket.domain.interactors.user.LoginUserUseCase;
import com.cremy.firebucket.domain.interactors.user.LogoutUserUseCase;
import com.cremy.firebucket.domain.interactors.user.RegisterUserUseCase;
import com.cremy.firebucket.domain.interactors.user.WriteUserUseCase;
import com.cremy.firebucket.presentation.presenters.impl.LoginPresenter;
import com.cremy.firebucket.presentation.presenters.impl.OnBoardingPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    private Fragment fragment;

    public UserModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    OnBoardingPresenter provideOnBoardingPresenter(CheckUserUseCase checkUserUseCase,
                                                   AnalyticsHelper analyticsHelper,
                                                   FirebaseRemoteConfig firebaseRemoteConfig) {
        return new OnBoardingPresenter(checkUserUseCase,
                analyticsHelper,
                firebaseRemoteConfig);
    }

    @Provides
    LoginPresenter provideLoginPresenter(LoginUserUseCase loginUserUseCase,
                                         AnalyticsHelper analyticsHelper) {
        return new LoginPresenter(loginUserUseCase, analyticsHelper);
    }

    @Provides
    CheckUserUseCase provideCheckUserUseCase(UserRepository repository) {
        return new CheckUserUseCase(repository);
    }

    @Provides
    LoginUserUseCase provideLoginUserUseCase(UserRepository repository) {
        return new LoginUserUseCase(repository);
    }

    @Provides
    LogoutUserUseCase provideLogoutUserUseCase(UserRepository repository) {
        return new LogoutUserUseCase(repository);
    }

    @Provides
    RegisterUserUseCase provideRegisterUserUseCase(UserRepository repository) {
        return new RegisterUserUseCase(repository);
    }

    @Provides
    WriteUserUseCase provideWriteUserUseCase(UserRepository repository) {
        return new WriteUserUseCase(repository);
    }

    @Provides
    UserDataSourceRemote provideDataSource(FirebaseAuth firebaseAuth,
                                                  FirebaseDatabase firebaseDatabase) {
        return new UserDataSourceRemote(firebaseAuth, firebaseDatabase);
    }

    @Provides
    UserRepository provideRepository(UserDataSourceRemote dataSource) {
        return new UserRepository(dataSource);
    }

}