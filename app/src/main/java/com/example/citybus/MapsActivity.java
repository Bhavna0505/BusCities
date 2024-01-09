package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    final int LOCATION_REQUEST_CODE = 1;
    String TAG = "Error";
    private SupportMapFragment mapFragment;
    boolean getBusAroundStarted = false;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //To check if we have all the permissions
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        else {
            mapFragment.getMapAsync(this);
        }
        //Getting nearby Buses location
       // getBusLocation();

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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(13.055805, 77.532962);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Home (Aditya's Residence)"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //To check all the permissions
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }
    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(latLng).title("You are Here !").icon(BitmapDescriptorFactory.fromResource(R.drawable.googleicon)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));



        //Storing the latitude and longitude in firebase database using Geofire
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Logged_in_Users_Location");
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(userID, new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error!=null)
                {
                    Toast.makeText(MapsActivity.this,"Can't go Active",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MapsActivity.this,"You are Active",Toast.LENGTH_SHORT).show();
            }
        });

        if(getBusAroundStarted == false) {
            getBusLocation();
        }


    }

    //When map is connected and the location is requested
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        mFusedLocationClient=LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //To check if we have all the permissions
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            onLocationChanged(mLastLocation);
                            //updateUI();
                        }else{
                            Log.e(TAG, "No location detected");
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                        }
                    }
                });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case LOCATION_REQUEST_CODE :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    mapFragment.getMapAsync(this);//mistake in this line
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please provide the permission",Toast.LENGTH_LONG).show();
                }
                break;


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Removing the latitude and longitude in firebase database using Geofire
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Logged_in_Users_Location");

        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(userID,new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error!=null)
                {
                    Toast.makeText(MapsActivity.this,"Your location is still active",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MapsActivity.this,"You just left the Google Maps",Toast.LENGTH_SHORT).show();
            }
        });

    }




    List<Marker> markerList = new ArrayList<Marker>();
    private void getBusLocation()
    {

        getBusAroundStarted = true;

        db = FirebaseFirestore.getInstance();
        db.collection("DriversActive")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                LatLng driverLocation = new LatLng(Double.parseDouble(document.get("LATITUDE").toString()),Double.parseDouble(document.get("LONGITUDE").toString()));
                                mMap.addMarker(new MarkerOptions().position(driverLocation).title(document.getString("BUS_NO")).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon)));

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                        else
                        {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });














/*
        DatabaseReference driverLocation = FirebaseDatabase.getInstance().getReference("Logged_in_Drivers_Location");

        GeoFire geoFire = new GeoFire(driverLocation);

        LatLng abc = new LatLng(13.063537,77.534832);
        mMap.addMarker(new MarkerOptions().position(abc).title("Hello"));


        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()) , 500.2);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {

                LatLng aabc = new LatLng(13.057208, 77.537820);
                mMap.addMarker(new MarkerOptions().position(aabc).title("Hello"));


                for(Marker mark : markerList)
                {
                    if(mark.getTag().equals(key))
                    {
                        //Used to remove duplicates. No same Driver ID must be passed
                        return;
                    }
                    LatLng driverLocation = new LatLng(location.latitude,location.longitude);
                    Marker mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLocation).title("Key").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon)));
                    mDriverMarker.setTag(key);
                    markerList.add(mDriverMarker);
                }
            }

            @Override
            public void onKeyExited(String key) {
                for(Marker mark : markerList)
                {
                    if(mark.getTag().equals(key))
                    {
                        mark.remove();
                        markerList.remove(mark);
                        return;
                    }
                }
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for(Marker mark : markerList) {
                    if (mark.getTag().equals(key)) {
                        mark.setPosition(new LatLng(location.latitude,location.longitude));
                    }
                }
            }

            @Override
            public void onGeoQueryReady() {


                LatLng aaabc = new LatLng(13.056558, 77.526172);
                mMap.addMarker(new MarkerOptions().position(aaabc).title("Hello"));

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                LatLng aaabc = new LatLng(13.056558, 77.526172);
                mMap.addMarker(new MarkerOptions().position(aaabc).title("Hello"));


            }
        });
*/
    }

}