package com.proyecto.diario;import android.Manifest;import android.annotation.SuppressLint;import android.content.Intent;import android.content.pm.PackageManager;import android.location.Location;import android.net.Uri;import android.os.Bundle;import android.provider.Settings;import android.view.View;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.appcompat.app.AppCompatActivity;import androidx.core.app.ActivityCompat;import androidx.core.content.ContextCompat;import com.google.android.gms.common.ConnectionResult;import com.google.android.gms.common.api.GoogleApiClient;import com.google.android.gms.location.FusedLocationProviderClient;import com.google.android.gms.location.LocationServices;import com.google.android.gms.maps.CameraUpdate;import com.google.android.gms.maps.CameraUpdateFactory;import com.google.android.gms.maps.GoogleMap;import com.google.android.gms.maps.OnMapReadyCallback;import com.google.android.gms.maps.SupportMapFragment;import com.google.android.gms.maps.model.LatLng;import com.google.android.gms.maps.model.MarkerOptions;import com.google.android.material.floatingactionbutton.FloatingActionButton;import com.karumi.dexter.Dexter;import com.karumi.dexter.PermissionToken;import com.karumi.dexter.listener.PermissionDeniedResponse;import com.karumi.dexter.listener.PermissionGrantedResponse;import com.karumi.dexter.listener.PermissionRequest;import com.karumi.dexter.listener.single.PermissionListener;import com.proyecto.diario.model.Note;import io.realm.Realm;import io.realm.RealmConfiguration;import io.realm.RealmResults;public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {    GoogleMap map;    FusedLocationProviderClient fusedLocationProviderClient;    boolean isPermissionGranted;    public double currentLat;    public double currentLon;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        // Initialize Realm        Realm.init(this);        // The RealmConfiguration is created using the builder pattern.        RealmConfiguration config = new RealmConfiguration.Builder()                .allowWritesOnUiThread(true)                .build();        Realm.setDefaultConfiguration(config);        Realm.deleteRealm(config);        //map configuration        checkPermission();        initMap();        //location provider config        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);        FloatingActionButton btnPosition = findViewById(R.id.btnPosition);        FloatingActionButton btnAbout = findViewById(R.id.btnAbout);        FloatingActionButton btnNotes = findViewById(R.id.btnNotes);        btnAdd.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                //get last coordinates                currentLat = getCurrentLat();                currentLon = getCurrentLon();                //set intent with data                Intent intent = new Intent(MainActivity.this, NoteActivity.class);                intent.putExtra("lat", currentLat);                intent.putExtra("lon", currentLon);                startActivity(intent);            }        });        btnPosition.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                getCurrentLocation();            }        });        btnAbout.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));        btnNotes.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ListActivity.class)));    }    private void initMap() {        if(isPermissionGranted){            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()                    .findFragmentById(R.id.map);            assert mapFragment != null;            mapFragment.getMapAsync(this);        }    }    private void checkPermission() {        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {            @Override            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {                Toast.makeText(MainActivity.this,"Permisos Concedidos", Toast.LENGTH_SHORT).show();                isPermissionGranted = true;            }            @Override            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {                Intent intent = new Intent();                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);                Uri uri = Uri.fromParts("package", getPackageName(), "");                intent.setData(uri);                startActivity(intent);            }            @Override            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {                permissionToken.continuePermissionRequest();            }        }).check();    }    @Override    protected void onStart() {        super.onStart();        Realm realm = Realm.getDefaultInstance();        RealmResults<Note> notes = realm.where(Note.class).findAll();        realm.executeTransaction(r -> {            for (Note note : notes) {                map.addMarker(new MarkerOptions().position(note.getLocation()));            }        });    }    @SuppressLint("MissingPermission")    public void getCurrentLocation() {        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {            if(task.isSuccessful()){                Location location = task.getResult();                currentLat = location.getLatitude();                currentLon = location.getLongitude();                goToLocation(location.getLatitude(), location.getLongitude());            }        });    }    @SuppressLint("MissingPermission")    public double getCurrentLat() {        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {            if(task.isSuccessful()){                Location location = task.getResult();                currentLat = location.getLatitude();            }        });        return currentLat;    }    @SuppressLint("MissingPermission")    public double getCurrentLon() {        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {            if(task.isSuccessful()){                Location location = task.getResult();                currentLon = location.getLongitude();            }        });        return currentLon;    }    private void goToLocation(double latitude, double longitude){        LatLng latLng = new LatLng(latitude, longitude);        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);        map.moveCamera(cameraUpdate);        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);    }    @Override    public void onMapReady(@NonNull GoogleMap googleMap) {        map = googleMap;        getCurrentLocation();    }    @Override    public void onConnected(@Nullable Bundle bundle) {    }    @Override    public void onConnectionSuspended(int i) {    }    @Override    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {    }}