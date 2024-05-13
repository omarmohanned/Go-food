package com.example.gofood;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class addres extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private DatabaseReference reference;
    private EditText resname, item, price, place, phone;
    private Button location, additem, add;
    private TextView textlatlang;
    double lattitude,longitude;
    LocationManager locationManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addres);

        reference = FirebaseDatabase.getInstance().getReference();

        additem = findViewById(R.id.additem);
        place = findViewById(R.id.place);
        textlatlang = findViewById(R.id.textlatlang);
        phone = findViewById(R.id.phone);
        resname = findViewById(R.id.resname);
        item = findViewById(R.id.item);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resname.getText().toString().isEmpty()&&textlatlang.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "please fill all info", Toast.LENGTH_LONG).show();

                } else {
                    reference.child("allresallplace").child(resname.getText().toString()).setValue(resname.getText().toString());

                    reference.child("allres").child(resname.getText().toString()).setValue(resname.getText().toString());
                    reference.child("allres").child(resname.getText().toString()).child("resname").setValue(resname.getText().toString());
                    reference.child("allres").child(resname.getText().toString()).child("lon").setValue(lattitude);
                    reference.child("allres").child(resname.getText().toString()).child("lat").setValue(longitude);

                    reference.child("allres").child(resname.getText().toString()).child("phone").setValue(phone.getText().toString());
                    reference.child("allres").child(resname.getText().toString()).child("place").setValue(place.getText().toString());
                }
            }
        });
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                reference.child("allres").child(resname.getText().toString()).child(item.getText().toString()).setValue(item.getText().toString());

                reference.child("allres").child(resname.getText().toString()).child(item.getText().toString()).child("price").setValue(price.getText().toString());

            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            addres.this,
                            new String[]{permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                } else {
                    getcurrentlocation();
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getcurrentlocation();
            } else {

                Toast.makeText(getApplicationContext(), "per denied", Toast.LENGTH_LONG).show();
            }


        }
    }

    private void getcurrentlocation() {


        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(addres.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(addres.this)
                                .removeLocationUpdates(this);
                        if (locationResult!= null && locationResult.getLocations().size()>0){
                            int latestlocatinindex=locationResult.getLocations().size()-1;
                             lattitude=
                                    locationResult.getLocations().get(latestlocatinindex).getLatitude();
                             longitude=
                                    locationResult.getLocations().get(latestlocatinindex).getLongitude();
                            textlatlang.setText(lattitude+" "+longitude);


                        }
                    }
                }, Looper.getMainLooper());



        }
    }


