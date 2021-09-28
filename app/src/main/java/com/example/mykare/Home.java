package com.example.mykare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    Button btlocation,logout;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = (Button) findViewById(R.id.logout);
        btlocation = (Button) findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Home.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission is granted
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(Home.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
        private void getLocation(){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            //initialize geoCoder
                            Geocoder geocoder = new Geocoder(Home.this,
                                    Locale.getDefault());
                            //initialize listaddress
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            //set latitude
                            textView1.setText(Html.fromHtml(
                                    "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                            + addresses.get(0).getLatitude()
                            ));
                            //set longitude
                            textView2.setText(Html.fromHtml(
                                    "<font color='#6200EE'><b>Longitude :</b><br></font>"
                                            + addresses.get(0).getLongitude()
                            ));
                            //set country name
                            textView3.setText(Html.fromHtml(
                                    "<font color='#6200EE'><b>Country :</b><br></font>"
                                            + addresses.get(0).getCountryName()
                            ));
                            //set locality
                            textView4.setText(Html.fromHtml(
                                    "<font color='#6200EE'><b>Locality :</b><br></font>"
                                            + addresses.get(0).getLocality()
                            ));
                            textView5.setText(Html.fromHtml(
                                    "<font color='#6200EE'><b>Address :</b><br></font>"
                                            + addresses.get(0).getAddressLine(0)
                            ));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


}