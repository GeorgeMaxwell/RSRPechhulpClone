package com.example.android.rsrpechulp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Boolean isTablet;
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
        //check if the device is a tablet or phone, if its a tablet display the rsrOver button
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet) {
            Button rsrOver = (Button) findViewById(R.id.btn_over_rsr);
            rsrOver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AboutRsrActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (isTablet){
            menu.findItem(R.id.action_over_rsr).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        //id button clicked take user to about rsr page
        if (id == R.id.action_over_rsr) {
            Intent intent = new Intent(MainActivity.this, AboutRsrActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
