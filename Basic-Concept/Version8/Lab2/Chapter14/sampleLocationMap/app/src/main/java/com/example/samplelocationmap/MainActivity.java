package com.example.samplelocationmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;

    MarkerOptions myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkPermission();
    }

    private void checkPermission(){
        int record = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if((record == PackageManager.PERMISSION_DENIED) ||
                (read == PackageManager.PERMISSION_DENIED) ||
                (write == PackageManager.PERMISSION_DENIED)){
            if(Build.VERSION.SDK_INT >= 24){
                requestPermissions(new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                        99);
            }
        }else{
            startLocationService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 99:
                startLocationService();
                break;
        }
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            GPSListener gps = new GPSListener();
            float d = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000, d, gps);

        }catch (SecurityException e){
            e.printStackTrace();

        }
    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            showCurrentLocation(latitude, longitude);
        }
    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 19));

        showMyLocationMarker(curPoint);
    }

    private void showMyLocationMarker(LatLng curPoint) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(curPoint);
            myLocationMarker.title("● 내 위치\n");
            myLocationMarker.snippet("● GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(curPoint);
        }
    }

}