package com.tagx5.geotag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by coryatwater on 6/5/16.
 */
public class LoginActivity extends FragmentActivity{
    public static final String TAG = "LoginActivity";
    private Button submit;
    private EditText usernameField;
    private EditText passwordField;
    private TextView makeAccount;

    private String username;
    private String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this, BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);
        setContentView(R.layout.activity_login);
        usernameField = (EditText) findViewById(R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        makeAccount = (TextView) findViewById(R.id.make_account);
        makeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });


        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "submit clicked" );
                //// TODO: 6/4/16 Backendless callback
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();
                loginCallback.showDialog();

                loginUser(username,password,loginCallback);
            }
        });
    }

    private LoadingCallback<BackendlessUser> createLoginCallback(){
        return new LoadingCallback<BackendlessUser>(this, "Registering...\npatience is a virtue") {
            @Override
            public void handleResponse(BackendlessUser user) {
                super.handleResponse(user);
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);

                startActivity(i);

            }
        };
    }

    public void loginUser(final String username, String password, AsyncCallback<BackendlessUser> loginCallback) {
        Log.e(TAG, "loginUser: "+username + password );
        Backendless.UserService.login(username, password, loginCallback);

        Backendless.Data.of( BackendlessUser.class ).find( new AsyncCallback<BackendlessCollection<BackendlessUser>>()
        {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> response) {
                Toast.makeText(LoginActivity.this, username + " you done bin logged in", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                System.out.println( "Server reported an error - " + backendlessFault.getMessage() );
            }
        } );


    }
}
