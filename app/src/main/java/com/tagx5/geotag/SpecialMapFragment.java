package com.tagx5.geotag;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.persistence.BackendlessDataQuery;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coryatwater on 6/5/16.
 */
public class SpecialMapFragment extends SupportMapFragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private GoogleMap mMap;
    public static final String TAG = "MapSuperDuperFragment";

    private Location mLastLocation;
    private LocationRequest locationRequest;
    private MarkerOptions markerOptions;
    private Marker mMarker;
    private GeoPoint point;
    private static final String EXTRA_POINT = "com.geotag.tagx5.geotag.GeographicPoint";

    ArrayList<String> hitPlayers = new ArrayList<String>();
    private List<BackendlessUser> userSet;


    private GoogleApiClient mGoogleApiClient;

    private BackendlessUser backendlessUser;
    private Double latBlaster = 30.0;
    private Double longDoink = 30.0;
    private static final String ARG_LATITUDE = "latitude";
    private static final String ARG_LONGITUDE = "longitude";
    private Button shoot;




    private ArrayList<String> playerUsername;//to do:pull information(username) from backendless so that we have a list of players in the game

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        point = new GeoPoint(0,0);
        setLocationRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater,container,savedInstanceState);
        shoot = (Button) rootView.findViewById(R.id.press_for_poop);
/*        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 5/26/16 onclick
            }
        }); */
        return rootView;
    }

    protected void setLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(5);
    }

    protected void startLocationUpdates() {
        try{

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mMarker.remove();
                    try{
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);
                    }catch (SecurityException e){
                        //// TODO: 6/5/16
                    }
                    try{
                        LatLng here = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                        Log.e("TAG", "" + mLastLocation.getLatitude() );
                        Log.e("TAG", "" + mLastLocation.getLongitude() );
                        longDoink = mLastLocation.getLongitude();
                        latBlaster = mLastLocation.getLatitude();
                        point.setX(longDoink);
                        point.setY(latBlaster);

                        Log.e("", "onCreate: " );
                        mMarker = mMap.addMarker(markerOptions.position(here).title("Marker here"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here,20f));
                    }catch(NullPointerException e){
                        //// TODO: 6/2/16
                    }

                }
            });
        }catch (SecurityException e){
            //// TODO: 6/5/16
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.8675, 151.2070 );
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(Bundle bundle) {

        try{
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            LatLng here = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            markerOptions = new MarkerOptions().position(here).title("Marker here");

            //mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
            mMarker = mMap.addMarker(markerOptions);
            Log.e("shoop", "onConnected: t" );
            startLocationUpdates();
        }catch(SecurityException e){
            //// TODO: 6/2/16
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static SpecialMapFragment newInstance(){
        SpecialMapFragment fragment = new SpecialMapFragment();
        return fragment;
    }

    private void sendResult(int resultCode,GeoPoint gp){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POINT,point);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void shoot() {
        Log.e(TAG, "shoot: a poopy ");
        //TODO: 6/2/16  connect up to a request that will check hits!
        downloadOtherUsers();
    }
    public void downloadOtherUsers(){
        BackendlessDataQuery bdq = new BackendlessDataQuery();
        LoadingCallback<BackendlessCollection<BackendlessUser>> lcbcbu = returnUsers();
        Backendless.Persistence.of(BackendlessUser.class).find(bdq, lcbcbu);

    }

    public LoadingCallback<BackendlessCollection<BackendlessUser>> returnUsers(){
        return new LoadingCallback<BackendlessCollection<BackendlessUser>>(getActivity(), "todo ") {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> response) {
                super.handleResponse(response);
                userSet = response.getData();

                for(BackendlessUser bu : userSet){
                    Log.e(TAG, "onCreateView: "+ bu.getProperty("username").toString() );
                }

            }
        };
    }
}