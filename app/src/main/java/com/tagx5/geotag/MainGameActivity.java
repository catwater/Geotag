package com.tagx5.geotag;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by coryatwater on 6/5/16.
 */
public class MainGameActivity extends FragmentActivity{


    private GoogleApiClient mGoogleApiClient;
    public static final String TAG ="GamePlayActivity";
    private com.google.android.gms.maps.MapFragment mapFragment;
    private SpecialMapFragment msdf;
    //private Button mButtonUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_game);

        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("GameFragment")== null)
            fm.beginTransaction()
                    .add(R.id.game_fragment_container, new GameFragment(), "GameFragment")
                    .commit();


        Log.e("", "onCreate: ");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        msdf = new SpecialMapFragment();
        mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if(fm.findFragmentByTag("map")== null)
            fm.beginTransaction()
                    .add(R.id.map, msdf, "MapSuperDuperFragment")
                    .commit();




    }
    public void performFunction(View v){
        msdf.shoot();
    }

}
