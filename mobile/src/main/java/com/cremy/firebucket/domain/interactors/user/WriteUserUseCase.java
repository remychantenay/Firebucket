package com.cremy.firebucket.domain.interactors.user;

import com.cremy.firebucket.data.repositories.UserRepository;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Implementation of {@link BaseUseCase} that represents a UseCase/Interactor
 */
public class WriteUserUseCase extends BaseUseCase {

  private final UserRepository userRepository;

  public final static String PARAMS_KEY_USERNAME = "param_username";
  public final static String PARAMS_KEY_UID = "param_uid";

  @Inject
  public WriteUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override public Observable getObservable(Params params) {
    return this.userRepository.writeUserInDatabase(params.getString(PARAMS_KEY_UID, null),
            params.getString(PARAMS_KEY_USERNAME, null));
  }
}