package com.cremy.firebucket.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cremy.firebucket.presentation.presenters.impl.OnBoardingPresenter;
import com.cremy.greenrobotutils.library.ui.ActivityUtils;

/**
 * LaunchScreen is just here as a Branded LaunchScreen
 * {@see https://www.google.com/design/spec/patterns/launch-screens.html}
 */
public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.next();
    }


    private void next() {
        finish();
        ActivityUtils.cancelCloseAnimation(this);
        OnBoardingActivity.startMe(this);
    }
}
