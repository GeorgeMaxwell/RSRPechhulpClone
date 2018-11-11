package com.example.android.rsrpechulp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetCompleteAddress {
    Location location;
    Geocoder geocoder;
    List<Address> addresses;
    public GetCompleteAddress(Location location, Context context){
        this.location = location;
        geocoder = new Geocoder(context, Locale.getDefault());
    }
    //uses geocoder to convert longitude and latitude to an address
    public List<Address> getAddress(){
        try {
            addresses = geocoder.getFromLocation(this.location.getLatitude(), this.location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.addresses;

    }
}
