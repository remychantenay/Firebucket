package com.cremy.firebucket.domain.interactors.user;

import com.cremy.firebucket.data.repositories.UserRepository;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Implementation of {@link BaseUseCase} that represents a UseCase/Interactor
 */
public class LogoutUserUseCase extends BaseUseCase {

  private final UserRepository userRepository;

  @Inject
  public LogoutUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override public Observable getObservable(Params params) {
      return this.userRepository.logoutUser();
  }
}