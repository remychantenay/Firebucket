package com.cremy.firebucket.presentation.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.View;

/**
 * This class must implement {@link BaseView}
 * Created by remychantenay on 18/05/2016.
 */
public abstract class BaseFragment
        extends Fragment
        implements BaseView {

    public FragmentActivity getFragmentActivity() {
        return getActivity();
    }
    public Fragment getFragment() {
        return this;
    }
    public abstract void getArgs(Bundle _bundle);

    public Context getContext() {
        return getActivity();
    }


    public interface OnShowMessageListener {
        void onShowMessage(String message);
    }

    public interface OnChangeFragment {
        void onChangeFragment(String fragmentTag,
                              @Nullable Pair<View, String> sharedElement);
    }
}