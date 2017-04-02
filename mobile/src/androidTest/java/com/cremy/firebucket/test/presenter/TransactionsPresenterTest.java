package com.cremy.firebucket.test.presenter;

import android.content.Context;

import com.cremy.firebucket.domain.interactors.Params;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.Matchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsPresenterTest {

  private TransactionsPresenter transactionsPresenter;

  @Mock private Context mockContext;
  @Mock private TransactionsFragment mockTransactionsFragment;
  @Mock private GetTransactionsUseCase mockGetTransactionsUseCase;

  @Before
  public void setUp() {
    transactionsPresenter = new TransactionsPresenter(mockGetTransactionsUseCase);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testTransactionsPresenterInitialize() {
    given(mockTransactionsFragment.getContext()).willReturn(mockContext);

    transactionsPresenter.initialize();

    verify(mockTransactionsFragment).showLoading();
    verify(mockTransactionsFragment).showViewError();
    verify(mockGetTransactionsUseCase).execute(any(DisposableObserver.class), any(Params.class));
  }
}