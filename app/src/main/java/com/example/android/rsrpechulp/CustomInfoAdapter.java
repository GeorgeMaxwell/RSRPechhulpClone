package com.example.android.rsrpechulp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Context mContext;
    private LayoutInflater inflater;

    public CustomInfoAdapter(Context context) {
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.address_information, null);
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        if(!title.equals("")){
            tvTitle.setText(title);
        }
        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.address);
        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }

        return view;
       /* rendowWindowText(marker, mWindow);
        return mWindow;*/
    }

    @Override
    public View getInfoContents(Marker marker) {
       return null;
    }
}

