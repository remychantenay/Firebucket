package com.cremy.firebucket.domain.interactors.taglist;

import com.cremy.firebucket.data.repositories.TagListRepository;
import com.cremy.firebucket.data.repositories.UserRepository;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Implementation of {@link BaseUseCase} that represents a UseCase/Interactor
 */
public class GetTagListUseCase extends BaseUseCase {

  private final TagListRepository tagListRepository;

  @Inject
  public GetTagListUseCase(TagListRepository tagListRepository) {
    this.tagListRepository = tagListRepository;
  }

  @Override public Observable getObservable(Params params) {
    return this.tagListRepository.getTagList();
  }
}