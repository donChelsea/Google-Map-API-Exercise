package android.example.com.mapsexercise;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import static android.example.com.mapsexercise.MainActivity.LAT_KEY;
import static android.example.com.mapsexercise.MainActivity.LONG_KEY;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng userInputLocation;
    private FusedLocationProviderClient flpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        flpClient = LocationServices.getFusedLocationProviderClient(this);

        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                double latitude = Double.parseDouble(extras.getString(LAT_KEY));
                double longitude = Double.parseDouble(extras.getString(LONG_KEY));
                userInputLocation = new LatLng(latitude, longitude);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

        // user input
        if (userInputLocation != null) {
            try {
                mMap.addMarker(new MarkerOptions().position(userInputLocation).title("Selected location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userInputLocation));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            LatLng nyc = new LatLng(40.7128, -74.0060);
            mMap.addMarker(new MarkerOptions().position(nyc).title("Marker in NYC"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
        }

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;
        LatLng address1 = null;

        // physical addresses
        try {
            // May throw an IOException
            address = coder.getFromLocationName("16 Cooper Street, Hempstead, NY 11550", 5);
            if (address != null) {
                Address location = address.get(0);
                address1 = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(address1).title("Nassau County Parking and Traffic Violations"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            address = coder.getFromLocationName("767 5th Ave, NY, 10153", 5);
            if (address != null) {
                Address location = address.get(0);
                address1 = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(address1).title("Apple Fifth Avenue"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            address = coder.getFromLocationName("601 Biscayne Blvd, Miami, FL 33132", 5);
            if (address != null) {
                Address location = address.get(0);
                address1 = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(address1).title("American Airlines Arena"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1020);
        } else {
            mMap.setMyLocationEnabled(true);
            flpClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double lat = location.getLatitude();
                                double lng = location.getLongitude();
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(lat, lng))
                                        .title("Current location"));
                            }
                        }
                    });
        }
    }
}
