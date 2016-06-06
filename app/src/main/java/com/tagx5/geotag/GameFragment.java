package com.tagx5.geotag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

/**
 * Created by coryatwater on 6/5/16.
 */
public class GameFragment extends Fragment {

    private TextView mScore;
    private TextView mLivesRemaining;
    private int score;
    private int livesRemaining;
    public static final String TAG = "HomeFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        mScore = (TextView) rootView.findViewById(R.id.text_score);
        mLivesRemaining = (TextView) rootView.findViewById(R.id.text_lives_remaining);

        //display user's lives remaining & score
        BackendlessUser k = Backendless.UserService.CurrentUser();
        mLivesRemaining.setText("Lives Remaining: " + k.getProperty("livesRemaining"));
        mScore.setText("Score: " + k.getProperty("score"));


        Intent i = new Intent();
        i = getActivity().getIntent();


        return rootView;
    }
}
