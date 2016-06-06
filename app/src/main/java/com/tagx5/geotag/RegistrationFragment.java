package com.tagx5.geotag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;

/**
 * Created by coryatwater on 6/5/16.
 */
public class RegistrationFragment extends Fragment {
    private Button register;

    private EditText firstName;
    private EditText lastName;
    private EditText userName;
    private EditText password;
    private EditText email;

    private String mFirstName;
    private String mLastName;
    private String mUsername;
    private String mPassword;
    private String mEmail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Backendless.initApp(getContext(), BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        final View rootView = inflater.inflate(R.layout.fragment_registration, container, false);


        register = (Button) rootView.findViewById(R.id.register);

        firstName = (EditText) rootView.findViewById(R.id.firstname);
        lastName = (EditText) rootView.findViewById(R.id.lastname);
        userName = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        email = (EditText) rootView.findViewById(R.id.email);

        mFirstName = firstName.getText().toString();
        mLastName = lastName.getText().toString();
        mUsername = userName.getText().toString();
        mEmail = email.getText().toString();
        mPassword = password.getText().toString();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstName = firstName.getText().toString();
                mLastName = lastName.getText().toString();
                mUsername = userName.getText().toString();
                mPassword = password.getText().toString();
                mEmail = email.getText().toString();

                LoadingCallback<BackendlessUser> regCallback = createRegCallback();
                regCallback.showDialog();
                registerUser(regCallback);
            }
        });

        return rootView;
    }

    private void registerUser(AsyncCallback<BackendlessUser> regCallBack){
        BackendlessUser newUser = new BackendlessUser();
        newUser.setEmail(mEmail);
        newUser.setPassword(mPassword);
        newUser.setProperty("firstName",mFirstName);
        newUser.setProperty("lastName", mLastName);
        newUser.setProperty("username", mUsername);

        Backendless.UserService.register(newUser,regCallBack);

    }

    private LoadingCallback<BackendlessUser> createRegCallback(){
        return new LoadingCallback<BackendlessUser>(getContext(), "Registering...\npatience is a virtue") {
            @Override
            public void handleResponse(BackendlessUser user) {
                super.handleResponse(user);

                Intent registrationResult = new Intent();
                registrationResult.putExtra(BackendlessUser.ID_KEY, user.getProperty("username").toString());
                getActivity().setResult(Activity.RESULT_OK, registrationResult);
                getActivity().finish();

            }
        };
    }

}
