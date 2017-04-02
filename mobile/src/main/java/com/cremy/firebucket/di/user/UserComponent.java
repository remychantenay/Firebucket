package com.cremy.firebucket.di.user;

import com.cremy.firebucket.data.repositories.UserRepository;
import com.cremy.firebucket.data.repositories.datasource.UserDataSourceRemote;
import com.cremy.firebucket.di.app.component.AppComponent;
import com.cremy.firebucket.di.scope.ActivityScope;
import com.cremy.firebucket.domain.interactors.user.CheckUserUseCase;
import com.cremy.firebucket.domain.interactors.user.LoginUserUseCase;
import com.cremy.firebucket.domain.interactors.user.LogoutUserUseCase;
import com.cremy.firebucket.domain.interactors.user.RegisterUserUseCase;
import com.cremy.firebucket.domain.interactors.user.WriteUserUseCase;
import com.cremy.firebucket.presentation.presenters.impl.LoginPresenter;
import com.cremy.firebucket.presentation.presenters.impl.OnBoardingPresenter;
import com.cremy.firebucket.presentation.presenters.impl.RegisterPresenter;
import com.cremy.firebucket.presentation.ui.fragments.LandingFragment;
import com.cremy.firebucket.presentation.ui.fragments.LoginFragment;
import com.cremy.firebucket.presentation.ui.fragments.RegisterFragment;

import dagger.Component;

/**
 * The role of the component is to inject the dependencies in the specified targets
 * Targets must ALL be added here
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = UserModule.class
)
public interface UserComponent {

    //Fragments
    void inject(LandingFragment view);
    void inject(LoginFragment view);
    void inject(RegisterFragment view);

    // Presenters
    void inject(OnBoardingPresenter presenter);
    void inject(LoginPresenter presenter);
    void inject(RegisterPresenter presenter);

    // UseCases/Interactors
    void inject(CheckUserUseCase useCase);
    void inject(LoginUserUseCase useCase);
    void inject(LogoutUserUseCase useCase);
    void inject(RegisterUserUseCase useCase);
    void inject(WriteUserUseCase useCase);

    // Repositories
    void inject(UserRepository repository);

    // DataSources
    void inject(UserDataSourceRemote dataSource);
}