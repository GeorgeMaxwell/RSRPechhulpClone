package com.example.android.rsrpechulp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_PHONE_CALL = 1;
    private static final float DEFAULT_ZOOM = 16f;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_arrow);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
        mFusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this);
        initMap();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkLocationPermission() && !checkLocationIsOn()) {
            finish();
        }else if (!checkLocationIsOn()) {
            buildAlertMessageNoGps();

        }else{
            createCallInfoButton();
            getDeviceLocation();

        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void createCallInfoButton() {
        final Button openCallInfo = (Button) findViewById(R.id.btn_open_call_info);
        openCallInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCallInfo.setVisibility(View.GONE);
                callRateInfo();
            }
        });
    }

    private void callRateInfo() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.call_rate_information);
        dialog.getWindow().setGravity(80);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.parseColor("#E6ace600")));
        dialog.show();
        final Button makeCall = (Button) findViewById(R.id.make_call);

       /* makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
                dialog.dismiss();
            }
        });*/

    }

    private void makeCall() {
        final String phone = "+319007788990";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        } else
            startActivity(callIntent);
    }

    private void buildAlertMessageNoGps() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.gps_confirmation_dialog);
        dialog.setTitle("GPS uitgeschakeld");
        dialog.setCancelable(false);
        final TextView confirmGpsMessage = (TextView) dialog.findViewById(R.id.gps_confirm_txt);
        confirmGpsMessage.requestFocus();
        Button confirmGpsButton = (Button) dialog.findViewById(R.id.confirm_gps_btn);
        Button denyGpsButton = (Button) dialog.findViewById(R.id.deny_gps_btn);
        confirmGpsMessage.setMovementMethod(LinkMovementMethod.getInstance());
        confirmGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
            }
        });
        denyGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();

    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private void getDeviceLocation() {

       /* try {
            if(checkLocationIsOn() == true && checkLocationPermission() == true) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            updateMarker(currentLocation);
                            //mMap.addMarker(new MarkerOptions().position(currentLatlng).title()).showInfoWindow();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }*/
    }

    public void updateMarker(Location currentLocation) {
        LatLng currentLatlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatlng, DEFAULT_ZOOM));

        List<Address> currentAddress = new GetCompleteAddress(currentLocation, this).getAddress();

        Marker currentLocationInfo = mMap.addMarker(new MarkerOptions()
                .position(currentLatlng)
                .title("Uw Locatie:")
                .snippet(currentAddress.get(0).getAddressLine(0)));

        mMap.setInfoWindowAdapter(new CustomInfoAdapter(MapsActivity.this));
        currentLocationInfo.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        currentLocationInfo.showInfoWindow();
        //mMap.addMarker(new MarkerOptions().position(currentLatlng).title("Current Location")).showInfoWindow();

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private Boolean checkLocationIsOn() {
        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            return true;
        }
        return false;
    }
}