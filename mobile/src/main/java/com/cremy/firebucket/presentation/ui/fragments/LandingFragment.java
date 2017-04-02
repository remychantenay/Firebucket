package com.cremy.firebucket.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cremy.firebucket.App;
import com.cremy.firebucket.R;
import com.cremy.firebucket.di.user.DaggerUserComponent;
import com.cremy.firebucket.di.user.UserComponent;
import com.cremy.firebucket.di.user.UserModule;
import com.cremy.firebucket.presentation.presenters.OnBoardingMVP;
import com.cremy.firebucket.presentation.presenters.impl.OnBoardingPresenter;
import com.cremy.firebucket.presentation.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LandingFragment#newInstance()} factory method to
 * create an instance of this fragment.
 */
public class LandingFragment extends BaseFragment
        implements OnBoardingMVP.View {

    public static final String TAG = LandingFragment.class.getName();

    OnShowMessageListener onShowMessageListener;
    OnChangeFragment onChangeFragment;

    @BindView(R.id.imageview_app_logo)
    ImageView imageViewAppLogo;

    @OnClick(R.id.button_cta_login)
    public void clickCtaLogin() {
        onChangeFragment.onChangeFragment(LoginFragment.TAG, getTransitionPair());
    }
    @OnClick(R.id.textview_cta_register)
    public void clickCtaRegister() {
        onChangeFragment.onChangeFragment(RegisterFragment.TAG, getTransitionPair());
    }

    @Inject
    OnBoardingPresenter presenter;
    UserComponent component;

    @Override
    public void injectDependencies() {
        if (component == null) {
            component = DaggerUserComponent.builder()
                    .appComponent(App.get(getContext()).getComponent())
                    .userModule(new UserModule(this))
                    .build();
            component.inject(this);
        }
    }

    @Override
    public void attachToPresenter() {
        this.presenter.attachView(this);
    }

    @Override
    public void detachFromPresenter() {
        this.presenter.detachView();
    }

    public LandingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TransactionsFragment.
     */
    public static LandingFragment newInstance() {
        LandingFragment fragment = new LandingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getArgs(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_onboarding, container, false);
        ButterKnife.bind(this, v);
        if (presenter.isInMaintenance()) {
            showMessage("TODO: In maintenance");
        } else {
            presenter.checkIfUserIsLogged();
        }
        return v;
    }

    @Override
    public void onAttach(Context context) {
        this.injectDependencies();
        this.attachToPresenter();
        super.onAttach(context);
        if (context instanceof OnShowMessageListener) {
            onShowMessageListener = (OnShowMessageListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShowMessageListener");
        }
        if (context instanceof OnChangeFragment) {
            onChangeFragment = (OnChangeFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChangeFragment");
        }
    }

    @Override
    public void onDetach() {
        detachFromPresenter();
        super.onDetach();
    }

    @Override
    public void getArgs(Bundle _bundle) {
        // Empty intentionally.
    }

    @Override
    public void onLandscape() {
        // Empty intentionally.
    }

    @Override
    public void onPortrait() {
        // Empty intentionally.
    }

    @Override
    public void showLoading() {
        // Empty intentionally.
    }

    @Override
    public void hideLoading() {
        // Empty intentionally.
    }

    @Override
    public void showMessage(String message) {
        if (onShowMessageListener != null) {
            onShowMessageListener.onShowMessage(message);
        }
    }

    @Override
    public void showNoNetwork() {
        // Empty intentionally.
    }

    @Override
    public void userLogged() {
        presenter.goToBucket(getContext());
        getActivity().finish();
    }

    private Pair getTransitionPair() {
        Pair<View, String> pair = Pair.create((View)this.imageViewAppLogo,
                getResources().getString(R.string.transition_logo_onboarding_to_login_and_register));
        return pair;
    }
}