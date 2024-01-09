package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DriverMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    final int LOCATION_REQUEST_CODE = 1;
    String TAG = "Error";
    FirebaseFirestore db;
    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapdriver);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //To check if we have all the permissions
            ActivityCompat.requestPermissions(DriverMap.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        else {
            mapFragment.getMapAsync(this);
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

        mMap.addMarker(new MarkerOptions().position(latLng).title(BusnoDriver.driverbusno).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        String PassedEmail = BusnoDriver.driveremail;
        PassedEmail = PassedEmail.replaceAll("\\.","dot");
        //Storing the latitude and longitude in firebase database using Geofire
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Logged_in_Drivers_Location");
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(PassedEmail, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error!=null)
                {
                    Toast.makeText(DriverMap.this,"Can't go Active",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(DriverMap.this,"You are Active",Toast.LENGTH_SHORT).show();
            }
        });


        db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("LATITUDE", location.getLatitude());
        data.put("LONGITUDE",location.getLongitude());
        data.put("BUS_NO",BusnoDriver.driverbusno);
        db.collection("DriversActive").document(PassedEmail).set(data);

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
            ActivityCompat.requestPermissions(DriverMap.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Logged_in_Drivers_Location");

        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(userID,new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error!=null)
                {
                    Toast.makeText(DriverMap.this,"Your location is still active",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(DriverMap.this,"You just left the Google Maps",Toast.LENGTH_SHORT).show();
            }
        });

    }
}