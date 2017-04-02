package com.cremy.firebucket.domain.interactors.bucket;

import com.cremy.firebucket.data.repositories.BucketRepository;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Implementation of {@link BaseUseCase} that represents a UseCase/Interactor
 */
public class GetBucketUseCase extends BaseUseCase {

  private final BucketRepository bucketRepository;

  @Inject
  public GetBucketUseCase(BucketRepository bucketRepository) {
    this.bucketRepository = bucketRepository;
  }

  @Override public Observable getObservable(Params params) {
    return this.bucketRepository.getBucket();
  }
}