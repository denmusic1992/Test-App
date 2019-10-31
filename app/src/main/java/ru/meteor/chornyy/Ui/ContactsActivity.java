package ru.meteor.chornyy.Ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Models.Contact;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class ContactsActivity extends CommonActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    /**
     * Переменные для контактов
     */
    private TextView mPhoneTextView;
    private TextView mAddressTextView;
    private TextView mEmailTextView;

    /**
     * Переменные для карты
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    // экземляр карт
    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private LatLng myPosition;
    private LatLng meteor = new LatLng(55.023107, 82.920653);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initialize(true, getString(R.string.contacts));

        //initToolbar(true, getString(R.string.contacts));

        // Если подключение к Интернету есть, то двигаем дальше
        if (isLaunchSuccessfull()) {
            getContacts();
        }
    }


    @Override
    public void initControls() {
        mPhoneTextView = findViewById(R.id.phoneTextView);
        mAddressTextView = findViewById(R.id.addressTextView);
        mEmailTextView = findViewById(R.id.emailTextView);

        CheckPermssions();
        initMap();

        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API)
                .build();

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {

                    if (location != null) {
                        myPosition = new LatLng(location.getLatitude(), location.getLongitude());

                        //test data to check map zoom
                        /*LatLng myPosition2 = new LatLng(lat, 82.923);
                        myMap.addMarker(new MarkerOptions().position(myPosition2).title("Метеор2").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        */
//                        getDistanceMeters(meteor, myPosition);
//
//                        //change in zoom depending on the distance between points
//                        setZoom(myPosition);
                        FollowByMyLocation();
                    }
                });
    }

    void getContacts() {
        // Показываем загрузку
        showDialog();

        CommonCallback<ResultApi<Contact>> callback = new CommonCallback<ResultApi<Contact>>(this) {
            @Override
            public void onSuccess(ResultApi<Contact> content) {
                Contact data = content.getContent();
                if (data != null) {
                    mPhoneTextView.setText(data.getPhone());
                    mAddressTextView.setText(data.getFullAddress());
                    mEmailTextView.setText(data.getEmail());
                }
            }
        };

        RetrofitClass.getInstance().getContactsApi().getContacts().enqueue(callback);
    }


    // проверяем разрешения на работу с геопозицией
    private void CheckPermssions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // если разрешения получены, то огонь, рисуем себя

                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

                    mMap.setMyLocationEnabled(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    // при получении карт
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // если разрешения есть, то рисуем себяшку
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
//            CameraPosition cameraPositionToMeteor = new CameraPosition.Builder()
//                    .target(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()))
//                    .zoom(17)
//                    .build();
//            CameraUpdate cameraUpdateToMeteor = CameraUpdateFactory.newCameraPosition(cameraPositionToMeteor);
//            mMap.animateCamera(cameraUpdateToMeteor);

            // Получим координаты через реверс геокодинг

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            String address = mAddressTextView.getText().toString();
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(address, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null) {
                Address meteorAddress = addresses.get(0);
                meteor = new LatLng(meteorAddress.getLatitude(), meteorAddress.getLongitude());
            }
            mMap.addMarker(new MarkerOptions().position(meteor).title("Офис \"Метеор\"").
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            if (myPosition != null)
                FollowByMyLocation();
        }
    }

    private void initMap() {
        // добавляем карту
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        myPosition = new LatLng(location.getLatitude(), location.getLongitude());
        FollowByMyLocation();
    }

    private void FollowByMyLocation() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(myPosition);
        builder.include(meteor);

        LatLngBounds bounds = builder.build();
        int padding = 120;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }
}
