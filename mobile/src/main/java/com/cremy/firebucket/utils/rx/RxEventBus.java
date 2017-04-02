package com.cremy.firebucket.utils.rx;

import com.cremy.firebucket.di.scope.ApplicationScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * This class allows to use RxJava as a simple EventBus such as Otto or EventBus
 * It allows to emit different kind of objects as it's a generic bus
 * @param <T>
 */
@ApplicationScope
public class RxEventBus<T> {
  private final Subject<T> subject;

  @Inject
  public RxEventBus() {
    this(PublishSubject.<T>create());
  }

  public RxEventBus(Subject<T> subject) {
    this.subject = subject;
  }

  public <E extends T> void post(E event) {
    subject.onNext(event);
  }

  public Observable<T> observe() {
    return subject;
  }

  public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
    return subject.ofType(eventClass);//pass only events of specified type, filter all other
  }
}