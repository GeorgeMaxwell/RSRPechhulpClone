package com.example.android.rsrpechulp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Menu privacyInformationBtn = (Menu) findViewById(R.id.action_privacy_info);

    }
    public void displayInformation(){
        /*String privacyInformationText = "privacybeleid \n" +
                "om deze app te gebruiken, dient u het privacybeleid te accepteren\n";
        TextView privacyInformation = new TextView(this);
        privacyInformation.setText(privacyInformationText);
        privacyInformation.setVisibility(View.VISIBLE);*/
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.privacy_information_dialog);
        dialog.setCancelable(true);
        dialog.setTitle("test");
        final TextView textView = (TextView)findViewById(R.id.privacy_info_txt);
        textView.requestFocus();
        final Button button = (Button)findViewById(R.id.confirm_privacy_btn);




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
