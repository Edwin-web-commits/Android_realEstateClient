package com.example.robmansions;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // Add a marker in your location and move the camera
        LatLng RoBproperties = new LatLng(-25.817884, 28.294555);
        mMap.addMarker(new MarkerOptions().position(RoBproperties).title("RoBproperties"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RoBproperties));

        LatLng RoBpropertiesB = new LatLng(-25.707931, 28.195988);
        mMap.addMarker(new MarkerOptions().position(RoBpropertiesB).title("RoBproperties"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RoBpropertiesB));

        LatLng RoBpropertiesC = new LatLng(-25.767946, 28.370291);
        mMap.addMarker(new MarkerOptions().position(RoBpropertiesC).title("RoBproperties"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RoBpropertiesC));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(RoBproperties,12.0f));
    }
}
