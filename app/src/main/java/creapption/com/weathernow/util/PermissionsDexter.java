package creapption.com.weathernow.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import creapption.com.weathernow.R;
import creapption.com.weathernow.main.MainActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by boma24 on 2/7/18.
 */

public class PermissionsDexter {

    private MainActivity mainActivity;

    public PermissionsDexter(MainActivity activity) {
        this.mainActivity = activity;
    }

    public void permissionAccessLocation() {
        Dexter.withActivity(mainActivity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(listenerLocation)
                .check();
    }

    private PermissionListener listenerLocation = new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            mainActivity.updateMessages(View.VISIBLE, R.string.permission_location_success);
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            if(response.isPermanentlyDenied()){
                mainActivity.deniedPermanentlyPermissionMessage();
            }else{
                mainActivity.updateMessages(View.VISIBLE, R.string.permission_location_error);
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
            Log.d(TAG, "onPermissionRationaleShouldBeShown: ");
        }
    };
}
