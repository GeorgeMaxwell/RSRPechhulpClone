package com.example.android.rsrpechulp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button rsrPechhulp = (Button) findViewById(R.id.btn_rsr_pecchulp);
        rsrPechhulp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }
    public void displayInformation(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.privacy_information_dialog);
        dialog.setTitle("Add City");
        dialog.setCancelable(false);

        final TextView privacyInfo = (TextView) dialog.findViewById(R.id.privacy_info_txt);
        privacyInfo.requestFocus();
        Button confirmPrivacy = (Button) dialog.findViewById(R.id.confirm_privacy_btn);
        privacyInfo.setMovementMethod(LinkMovementMethod.getInstance());

        confirmPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_privacy_info) {
            displayInformation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
