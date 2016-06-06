package com.tagx5.geotag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.backendless.Backendless;

/**
 * Created by coryatwater on 6/5/16.
 */
public class RegistrationActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this, BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);
        setContentView(R.layout.activity_registration);
        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("RegistrationFragment")== null)
            fm.beginTransaction()
                    .add(R.id.fragment_registration_container, new RegistrationFragment(), "RegistrationFragment")
                    .commit();
    }
}
