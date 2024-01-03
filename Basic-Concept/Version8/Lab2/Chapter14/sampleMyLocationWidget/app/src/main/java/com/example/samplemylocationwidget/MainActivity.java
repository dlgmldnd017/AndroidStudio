package com.example.samplemylocationwidget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
    public static SupportMapFragment mapFragment;
    public static GoogleMap map;
    MarkerOptions myLocationMarker;
    public static double lat, lont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("위치 설정");
                        builder.setMessage("GPS가 꺼져있습니다.\n" + "GPS 설정을 위해 이동하시겠습니까?");
                        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        });
                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else{
                        checkPermission();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkPermission(){
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int record = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if((camera == PackageManager.PERMISSION_DENIED) ||
                (record == PackageManager.PERMISSION_DENIED) ||
                (read == PackageManager.PERMISSION_DENIED)){
            if(Build.VERSION.SDK_INT >= 24){
                requestPermissions(new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        99);
            }
        }else {
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

        try {
            GPSListener gpsListener = new GPSListener();
            long minTime = 1000;
            float minDistance = 0;

            // 여기서 GPS 제공자를 쓰는 이유는 단말의 GPS 모듈에 대한 정보를
            // 얻어오기 위해서 사용한다.
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    100000,0, gpsListener);

        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));
        lat = latitude;
        lont = longitude;

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