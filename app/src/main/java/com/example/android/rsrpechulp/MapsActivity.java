package com.example.android.rsrpechulp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_PHONE_CALL = 2;
    private static final float DEFAULT_ZOOM = 16f;
    private LocationManager mLocationManager;
    private Boolean mLocationPermissionsGranted = false;
    private Boolean dialogIsShowing = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_arrow);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        createCallInfoButton();
        mMap = googleMap;
        if (checkLocationPermission()) {
                getLocation();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        buildAlertMessageNoGps();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1000, this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);
    }

    private Boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)) {
                        getLocation();
                    }
                } else {
                    finish();
                }
                return;
            }
            case REQUEST_PHONE_CALL:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED){
                        makeCall();
                    }
                } else {
                    return;
                }

            }

        }
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
      //  final Dialog dialog = new Dialog(this);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create( );
       // dialog.setContentView(R.layout.call_rate_information);
        alertDialog.getWindow().setGravity(80);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //dialog.setCancelable(true);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.parseColor("#E6ace600")));
        //dialog.show();
        //Button makeCall = (Button) findViewById(R.id.make_call);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.call_rate_information, null);
        Button makeCall = (Button) promptView.findViewById(R.id.make_call);
        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(promptView);

        alertDialog.show();

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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        final AlertDialog alertDialog =  builder1.create();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.gps_confirmation_dialog, null);
        alertDialog.setTitle("GPS uitgeschakeld");
        alertDialog.setCancelable(false);
        final TextView confirmGpsMessage = (TextView) promptView.findViewById(R.id.gps_confirm_txt);
        confirmGpsMessage.requestFocus();
        confirmGpsMessage.setMovementMethod(LinkMovementMethod.getInstance());
        Button confirmGpsButton = (Button)promptView.findViewById(R.id.confirm_gps_btn);
        Button denyGpsButton = (Button) promptView.findViewById(R.id.deny_gps_btn);

        confirmGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                dialogIsShowing = false;
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
            }
        });
        denyGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                dialogIsShowing = false;
                finish();
            }
        });
        if(dialogIsShowing)
            return;
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertDialog.setView(promptView);
            alertDialog.show();
            dialogIsShowing = true;
        }

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

    @Override
    public void onLocationChanged(Location location) {

        updateMarker(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        buildAlertMessageNoGps();
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        buildAlertMessageNoGps();

    }
}