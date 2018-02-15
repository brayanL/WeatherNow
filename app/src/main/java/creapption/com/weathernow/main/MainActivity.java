package creapption.com.weathernow.main;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import creapption.com.weathernow.R;
import creapption.com.weathernow.WeathernowApplication;
import creapption.com.weathernow.data.remote.api.WeatherData;
import creapption.com.weathernow.util.CommonUtils;
import creapption.com.weathernow.util.PermissionsDexter;


public class MainActivity extends AppCompatActivity implements MainActivityView {

    @BindView(R.id.permission_error_message)
    TextView permissionErrorMessage;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.precipitation)
    TextView precipitation;


    private PermissionsDexter mPermissionsDexter;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    @Inject
    MainActivityPresenter presenter;

    public static final String TAG = "PERMISSIONS";

    //life cycle activity override methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpInjection();
        initDexter();
        locationConfig();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkLocationPermissions()) {
            if (checkGooglePlayServices()) {
                startLocationUpdates();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void deniedPermanentlyPermissionMessage() {
        CommonUtils.showSimpleToastMessages(this,
                getString(R.string.denied_permission_permanently));
    }

    @Override
    public void updateMessages(int visible, @IdRes int message) {
        permissionErrorMessage.setVisibility(visible);
        permissionErrorMessage.setText(message);
    }

    @OnClick(R.id.permission_error_message)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked: permissions");
        if (!checkLocationPermissions()) {
            mPermissionsDexter.permissionAccessLocation();
        }
    }

    @Override
    public void updateUI(WeatherData weatherData) {
        temperature.setText(String.valueOf(Math.round(weatherData.getTemperature())));
        summary.setText(String.valueOf(weatherData.getSummary().replace("\"", "")));
        humidity.setText(String.valueOf(weatherData.getHumidity()));
        precipitation.setText(String.valueOf(weatherData.getPrecipProbability()));

    }

    private Boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(TAG, "checkLocationPermissions: " + permissionState);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void initDexter() {
        mPermissionsDexter = new PermissionsDexter(this);
        mPermissionsDexter.permissionAccessLocation();
    }

    private Boolean checkGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            updateMessages(R.string.error_play_services, View.VISIBLE);
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                CommonUtils.showSimpleToastMessages(this,
                        getString(R.string.error_not_available_play_services));
            }
            return false;
        } else {
            //updateMessages(R.string.permission_location_success, View.VISIBLE);
            return true;
        }
    }

    private void locationConfig() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                presenter.getWeather(mCurrentLocation);
                Log.d(TAG, "onLocationResult: " + mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());
            }
        };
    }

    /**
     * more info:https://developers.google.com/android/reference/com/google
     * /android/gms/location/LocationRequest#setInterval(long)
     */

    private void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(20000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        //check whether the current location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                setCurrentLocation();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(MainActivity.this,
                                    1000);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    private void stopLocationUpdates() {
        if (checkLocationPermissions()) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void setUpInjection() {
        WeathernowApplication app = (WeathernowApplication) this.getApplication();
        app.getMainActivityComponent(this).inject(this);
    }

    private void setCurrentLocation() {
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            //Todo: set Default Location
            Log.e("TAG1", "onSuccess Error: ", e);
        }
    }
}
