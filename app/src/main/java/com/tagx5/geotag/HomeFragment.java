package com.tagx5.geotag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;

/**
 * Created by coryatwater on 6/5/16.
 */
public class HomeFragment extends Fragment{

    private Button joinGame;
    private TextView v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Backendless.initApp(getContext(),
                BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        joinGame = (Button) rootView.findViewById(R.id.join_game_button);
        v = (TextView) rootView.findViewById(R.id.intro_message);
        v.setText("Hey there " + Backendless.UserService.CurrentUser().getProperty("username"));
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MainGameActivity.class);
                startActivity(i);
            }
        });


        return rootView;
    }
}
